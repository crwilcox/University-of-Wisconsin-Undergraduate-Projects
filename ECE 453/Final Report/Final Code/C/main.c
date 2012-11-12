#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>

#include <fcntl.h>

#include <sys/socket.h>
#include <sys/time.h>
#include <sys/types.h>

#include <pthread.h>
#include <netinet/in.h>

#include "colors.h"
#include "tableLibrary.h"
#include "demoMode.h"
#include "gameMode.h"

/* pong: A program to run the Pong Board, and handle communication with a GUI
 * over a TCP/IP socket.
 *
 * To run: pong <portnum>
 *
 * Will start basic demo modes, and listening for connections on the specified
 * port.  When a new connection comes in, it will terminate any previous
 * connections, and get a state update from the board.
 *
 */

int sockfd, cur_sockfd, new_sockfd;
char mode, sub_mode;
int t2on,t2off,t1on,t1off;
int *demo_mode_args;
int *photoThreshold;

void ApplicationTerminated(){
    int gui_close = 0xFFFFFFFF;
    close(sockfd);
    write(cur_sockfd, &gui_close, 4);
    write(new_sockfd, &gui_close, 4);
    usleep(100000);
    close(cur_sockfd); 
    close(new_sockfd);
    usleep(10000);
    printf("APP TERMINATED....\n");
    exit(0);
}

void ReadCalibration() {
    FILE *calibrationData = fopen("pong_calibration.txt", "r");
    char line[80];

    int low[20];
    int high[20];

    int i=0;
    for(i = 0; i < 20; i++) {
        fgets(line, 80, calibrationData);
        photoThreshold[i] = atoi(line);
    }
}

void CalibrateBoard() {
    int sensorUncovered[20];
    int singleCovered[20];
    int sensorCovered[20];
    int singleUncovered[20];
    FILE *calibrationData = fopen("pong_calibration.txt", "w+");

    SetCupColors(allCups, 20, 0);
    printf("Please uncover all cups and stand away from the the table, then press enter.\n");
    getc(stdin);
    int i = 0;
    for(i = 0; i < 20; i++) {
        sensorUncovered[i] = CheckSensorAbs(i);
    }
    
    printf("Please cover the colored cups, then press enter. (Repeat 10x)\n");
    for(i = 0; i < 10; i++) {
        SetCupColors(allCups, 20, 0);
        SetCupColor(i+10,    0x00FF0000);
        SetCupColor(i, 0x00008000);
        getc(stdin);
        singleCovered[i] = CheckSensorAbs(i);
        singleCovered[i+10] = CheckSensorAbs(i+10);
    }

    SetCupColors(allCups+10, 10, 0x00FF0000);
    SetCupColors(allCups, 10, 0x00008000);
    printf("Please cover all cups, then enter.\n");
    getc(stdin);

    for(i = 0; i < 20; i++) {
        sensorCovered[i] = CheckSensorAbs(i);
    }

    printf("Please uncover the different colored cups, but leave all other cups covered, then press enter. (Repeat 10x)\n");
    

    for(i = 0; i < 10; i++) {
        SetCupColors(allCups+10, 10, 0x00FF0000);
        SetCupColors(allCups, 10, 0x00008000);
        SetCupColor(i+10,    0x000000FF);
        SetCupColor(i, 0x00FFFF00);
        getc(stdin);
        singleUncovered[i] = CheckSensorAbs(i);
        singleUncovered[i+10] = CheckSensorAbs(i+10);
    }

    int threshold[20];
    for(i = 0; i < 20; i++) {
        threshold[i] = ((singleCovered[i] - singleUncovered[i])/4) +
            singleUncovered[i];

        fprintf(calibrationData, "%d %2d\n", threshold[i], i);
    }

    fprintf(calibrationData, "\n** All Uncovered **\n");
    for(i = 0; i < 20; i++) {
        fprintf(calibrationData, "%02d %d\n", i, sensorUncovered[i]);
    }

    fprintf(calibrationData, "\n** All Covered **\n");
    for(i = 0; i < 20; i++) {
        fprintf(calibrationData, "%02d %d\n", i, sensorCovered[i]);
    }

    fprintf(calibrationData, "\n** Single Uncovered **\n");
    for(i = 0; i < 20; i++) {
        fprintf(calibrationData, "%02d %d\n", i, singleUncovered[i]);
    }
    
    fprintf(calibrationData, "\n** Single Covered **\n");
    for(i = 0; i < 20; i++) {
        fprintf(calibrationData, "%02d %d\n", i, singleCovered[i]);
    }
}

char process_command(int command) {
    char op1 = (command >> 29) & 0x07;
    char op2 = (command >> 24) & 0x001F;

    printf("Processing: %x %2x %6x\n", op1, op2, command & 0x00FFFFFF);

#ifdef DEBUG
    int i = 0;
    for(i = 0; i < 20; i++) {
        printf("Sensor %d: %d\n", i, CheckSensorAbs(i));
    }
#endif

    switch(op1) {
        case SENSORS:
        case CUPS:
            // These shouldn't (currently) come from the GUI
            break;
        case GAMEMODE:
            switch(op2) {
                case T1ON:
                    t1on = command & 0x00FFFFFF;
                    break;
                case T1OFF:
                    t1off = command & 0x00FFFFFF;
                    break;
                case T2ON:
                    t2on = command & 0x00FFFFFF;
                    break;
                case T2OFF:
                    t2off = command & 0x00FFFFFF;
                    break;
                case STARTGAME:
                    mode = GAMEMODE;
                    sub_mode = STARTGAME;
                    return 1;
                    break;
                case ENDGAME:
                    // Do nothing, the GUI shouldn't ever send us this.
                    break;
            }
            break;
        case DEMOMODE:
            mode = DEMOMODE;
            sub_mode = op2;
            
            switch(op2) {
                case BREATHING:
                    if(demo_mode_args) free(demo_mode_args);
                    demo_mode_args = malloc(sizeof(int));
                    demo_mode_args[0] = command & 0x00FFFFFF;
                    break;
                case COUNTINGCOLORS:
                    if(demo_mode_args) free(demo_mode_args);
                    demo_mode_args = malloc(sizeof(int)*2);
                    demo_mode_args[1] = command & 0x00000001;
                    demo_mode_args[0] = (command & 0x00FFFFFF) >> 1;
                    break;
            }

            return 1;
            break;
        case FULLSTATE:
            SendGUICommand(mode, sub_mode, 0);
            FullState();
            break;
    }
    return 0;
}

/* board_control controls what the board does. Here we need to set up something
 * to listen to the pipe from the connection manager, and to write new
 * information back to it.
 */
void board_control(int gui_in, int gui_out, char flags, int argc, char *argv[]) {
    // argc and argv have removed the program name, and any arguments parsed by
    // getopt. All that is left are the random other arguments, in the order
    // they were specified. Argc has been decremented appropriately.
    char input[80];
    int output;
    mode = DEMOMODE;
    sub_mode = CYCLERAINBOWSMOOTH;

    photoThreshold = malloc(sizeof(int) * 20);
    int data = 0;
    int ret = 0;

    if(flags & 0x02) {
        LibInit(gui_in, gui_out, flags & 0x01, photoThreshold);
        CalibrateBoard();
        close(gui_in);
        close(gui_out);
        exit(0);
    } else {
        ReadCalibration();
        LibInit(gui_in, gui_out, flags & 0x01, photoThreshold);
    }

    // Since we're going to check for data every time we go back to the main
    // loop below, we need to make sure it's non-blocking or the board will
    // hang.
    int fcntl_ret = fcntl(gui_out, F_SETFL, O_NONBLOCK);
    fcntl_ret = fcntl(gui_in, F_SETFL, O_NONBLOCK);

    int i;
    while(1) {
        switch(mode) {
            case SENSORS:
            case CUPS:
                break;
            case GAMEMODE:
                switch(sub_mode){
                    /* We shouldn't ever need these cases, commenting them
                     * out for now.
                     */
                    /*case T2OFF:
                      t2off = data;
                      break;
                      case T2ON:
                      t2on = data;
                      break;
                      case T1OFF:
                      t1off = data;
                      break;
                      case T1ON:
                      t1on = data;
                      break;*/
                    case STARTGAME:
                        GameMode(t1on, t1off, t2on, t2off);
                        break;
                }
            case DEMOMODE:
                switch(sub_mode){
                    case CYCLERAINBOW:
                        CycleRainbow();
                        break;
                    case CYCLERAINBOWBLEND:
                        CycleRainbowBlend();
                        break;
                    case RAINBOWWAVE:
                        RainbowWave();
                        break;
                    case MOTIONDETECTIONRED:
                        MotionDetectionRed();
                        break;
                    case BREATHING:
                        Breathing(demo_mode_args[0]);
                        break;
                    case CYCLERAINBOWSMOOTH:
                        SmoothRainbowBlend();
                        break;
                    case SNAKE:
                        Snake();
                        break;
                    case COUNTINGCOLORS:
                        // uSeconds delay
                        CountingColors(demo_mode_args[0], demo_mode_args[1]);
                        break;
                }
        }

        // Read / process commands (if they exist)
        ret = 0;
        ret = read(gui_in, &output, 4);
        //printf("Read %d bytes\n", ret);
        if(ret == 0) {
            // Oh snap! EOF!
            close(gui_in);
            close(gui_out);
            return;
        }
        if(ret > 0) {
            process_command(output);
        }
        output = 0;
    }

    close(gui_in);
    close(gui_out);
}

/* Connection manager 
 *
 * Accept new connections, and kill off the old ones when a new one comes in.
 * Connect the connections up to the board.
 */
void connection_manager(int in, int out, int port_num) {

    FILE *board_in  = fdopen(in, "r");
    
    // Return value from read. -1 error, 0 EOF. Otherwise, num  bytes read.
    int read_in_return; 
    int read_out_return; 
    int write_return; 

    char buffer[256]; // Buffer to hold data from streams.

    // Stuff for select calls.
    fd_set readfds; 
    int max_fd=0;


    // BEGIN socket setup magic
   // int sockfd, cur_sockfd, new_sockfd;
    socklen_t client;
    struct sockaddr_in serv_addr, cli_addr;

    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if(sockfd < 0) {
        fprintf(stderr, "ERROR: Unable to open socket\n");
        exit(1);
    }

    bzero((char*) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(port_num);

    if(bind(sockfd, (struct sockaddr *) &serv_addr,
                sizeof(serv_addr)) < 0) {
        fprintf(stderr, "ERROR: Unable to bind socket\n");
        exit(1);
    }

    listen(sockfd, 5);

    client = sizeof(cli_addr);
    // END socket setup magic

    // Wait for a connection on the socket.
    cur_sockfd = accept(sockfd, (struct sockaddr *) &cli_addr, &client);
    if (cur_sockfd < 0) {
        fprintf(stderr, "ERROR: Unable to accept connection\n");
        exit(1);
    }

    do {
        read_in_return = read(in, buffer, 256);
    } while(read_in_return > 0);

    // Okay, we have our initial connection. Mark the socket as non-blocking.
    // Further calls to accept will not block.
    int fcntl_ret = fcntl(sockfd, F_SETFL, O_NONBLOCK);
    if(fcntl_ret) {
        fprintf(stderr, "ERROR: Unable to set socket as non-blocking.\n");
        exit(1);
    }
    
    // Set the connection to non-blocking.
    // Further calls to read(cur_sockfd,...) will not block.
    fcntl_ret = fcntl(cur_sockfd, F_SETFL, O_NONBLOCK);
    if(fcntl_ret) {
        fprintf(stderr, "ERROR: Unable to set read socket as non-blocking.\n");
        exit(1);
    }

    int flags = 0;
    int sock_eof = 0;

    while(feof(board_in) == 0) {
        // Check for readable stuff: in, sockfd, cur_sockfd;
        bzero(buffer, 256);
        FD_ZERO(&readfds);
        FD_SET(in,          &readfds); //Data from board
        FD_SET(sockfd,      &readfds); //Socket accepts
        FD_SET(cur_sockfd,  &readfds); //Data from gui

        max_fd = 0;

        if(in > max_fd) max_fd = in;
        if(sockfd > max_fd) max_fd = sockfd;
        if(cur_sockfd > max_fd) max_fd = cur_sockfd;

        select(max_fd + 1, &readfds, NULL, NULL, NULL); // This will block until we have something to do.

        if(sock_eof) {
            flags = fcntl(sockfd, F_GETFL);
            flags = flags ^ O_NONBLOCK;
            // Current socket connection went away, block and get a new one.
            fcntl_ret = fcntl(sockfd, F_SETFL, flags);
            if(fcntl_ret) {
                fprintf(stderr, "ERROR: Unable to set socket as blocking.\n");
                exit(1);
            }
        }

        // If we don't have a connection, this should block until we get one.
        new_sockfd = accept(sockfd, (struct sockaddr *) &cli_addr, &client);
        
        // We blocked
        if(sock_eof && new_sockfd > 0) {
            fcntl_ret = fcntl(sockfd, F_SETFL, O_NONBLOCK);
            if(fcntl_ret) {
                fprintf(stderr, "ERROR: Unable to set socket as non-blocking. abc\n");
                exit(1);
            }
        }

        sock_eof = 0;
        if(new_sockfd > 0) {
            // Oh snap! A new client has connected. Time to kill the old one.
            //
            // If the old sockfd is at EOF, we set accept to block before, so set it back to non-blocking.

            close(cur_sockfd); // I THINK this will EOF the connection...

            // Flush the pipe from the board (new GUI might be confused by
            // whatever is in there)
            
            do {
                read_in_return = read(in, buffer, 256);
            } while(read_in_return > 0);

            // New favorite in town :)
            cur_sockfd = new_sockfd;
            new_sockfd = 0;

            // Set the socket as non-blocking
            fcntl_ret = fcntl(cur_sockfd, F_SETFL, O_NONBLOCK);
            if(fcntl_ret) { 
                fprintf(stderr, 
                    "ERROR: Unable to set read socket as non-blocking.\n");
                exit(1);
            }
            continue;
        }

        read_in_return = read(in, buffer, 4);
        while(read_in_return > 0) {
            // We have data from the board to the gui, send it!!
            write_return = write(cur_sockfd, buffer, read_in_return);
            if(write_return == -1) {
//                perror("Error writing to GUI");
            }
            bzero(buffer, 4);
            read_in_return = read(in, buffer, 4);
        }
        
        bzero(buffer, 4);
        read_out_return = read(cur_sockfd, buffer, 4);
        while(read_out_return > 0) {
            // We have data from the gui to the board, send it!!
            write_return = write(out, buffer, read_out_return);
            if(write_return == -1) {
                perror("Error writing to board");
            }
            bzero(buffer, 4);
            read_out_return = read(cur_sockfd, buffer, 4);
        }


        if(read_out_return == 0) {
            sock_eof = 1;
        }
    }
    close(in);
    close(out);
}


void SigTerm() {
    printf("SIGTERM: ");
    ApplicationTerminated();
}

void SigAbrt() {
    printf("SIGABRT: ");
    ApplicationTerminated();
}

void SigInt() {
    printf("SIGINT: ");
    ApplicationTerminated();
}

void RandomSig() {
    printf("UNKNOWN SIG! ");
    ApplicationTerminated();
}

int main(int argc, char* argv[]) {
#ifdef X86
    printf("x86 Pong -- Sensors are disabled!\n");
#endif
    int i = 0;
    for(i = 1; i < 29; i++) {
        if(i != SIGTERM && i != SIGABRT && i != SIGINT)
            signal(i, RandomSig);
    }

    signal(SIGTERM,SigTerm);
    signal(SIGABRT,SigAbrt);
    signal(SIGINT,SigInt);

    int port_num = 8332;

    // getopt is awesome, let pdickey know if we should add more to the options
    // it parses. Currently it just does the port num.
    char *optstring = ":p:vc";
    int getopt_ret = 1;
    int getopt_err = 0;
    char flags = 0;

    while(getopt_ret != -1) { //getout returns -1 when all options have been parsed.
        getopt_ret = getopt(argc, argv, optstring);
        switch(getopt_ret) {
            case 'p':
                port_num = atoi(optarg); //this is an external char* from getopt
                break;

            case 'v':
                flags |= 1;
                break;
                
            case 'c':
                flags |= 2;
                break;


            case ':':
                fprintf(stderr, "ERROR: -%c option requires an argument\n",
                        optopt);
                getopt_err = 1;
                break;

            case -1: break; // Done processing
        }
    }

    if(getopt_err) exit(1);
    
    // Done parsing arguments! Set up the pipes, and fork.
    int main_to_mgr[2];
    int mgr_to_main[2];

    // Pipe() creates a one-way communication channel.
    // int arg[0] = read end of the pipe.
    // int arg[1] = write end of the pipe.
    pipe(main_to_mgr);
    pipe(mgr_to_main);

    if(fork()) {
        // Parent process -- run the board.

        // The child will use these ends.
        close(main_to_mgr[0]);
        close(mgr_to_main[1]);

        board_control(mgr_to_main[0], main_to_mgr[1], flags, argc - optind, argv + optind);

    } else {
        // Child process -- get connections and connect 'em to the board.
        
        // Close the parent's ends of the pipes.
        close(main_to_mgr[1]);
        close(mgr_to_main[0]);

        // Child needs its input (board's output) to be non-blocking,
        // so it can read without caring if there is data there.
        int fcntl_ret = fcntl(main_to_mgr[0], F_SETFL, O_NONBLOCK);

        connection_manager(main_to_mgr[0], mgr_to_main[1], port_num);



    }

    return 0;
}

