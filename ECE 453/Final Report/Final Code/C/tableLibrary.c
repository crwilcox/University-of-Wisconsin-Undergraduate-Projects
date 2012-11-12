/*******************************************************************************
* tableLibrary.c
* Summary:	
* Authors:	Christopher Wilcox
*******************************************************************************/
#include "tableLibrary.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/time.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>
#include <unistd.h>


int fd;
int gui_input, gui_output;
char verbose = 0;
unsigned int offset = 0, data = 0;
volatile unsigned int *led; //20 words
unsigned int *sensor;//20 words
int* lastSensorVal;
int* lastLEDVal;

char GUI_Buffer[256];

//SOME USEFUL ARRAYS
int allCups[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
//break cups into rows
int row0[] = {  6,7,8,9    };
int row1[] = {   3,4,5     };
int row2[] = {    1,2      };
int row3[] = {     0       };
int row4[] = {     10      };
int row5[] = {   11, 12    };
int row6[] = {  13,14,15   };
int row7[] = { 16,17,18,19 };

int *photoThreshold;

/*int photoThreshold[] = {
    1500,   //00
    1220,   //01 -- 1420
    1800,   //02
    1025,   //03
    1500,   //04
    1000,    //05 -- changed from 1100 -- 4/29/2011
    1025,    //06 -- changed from 1175 -- 4/29/2011 -- 1070 4/30
    1350,   //07
    1400,   //08
    1500,   //09 -- 1700
    1520,   //10 - changed from 1620 4/30
    1410,   //11
    1200,   //12 -- 1350
    1400,   //13
    1100,   //14 -- changed from 1200 -- 4/29/2011
    1100,    //15 -- changed from 1000 4/30
    350,    //16  -- DANGER Tight Range -- lolz fixed. (~100 - ~600 now)
    1700,    //17
    1590,   //18
    1750,   //19
};*/


/*
int photoThreshold[] = {
    2000,   //00
    2000,   //01
    2000,   //02
    2000,   //03
    2000,   //04
    2000,    //05
    2000,    //06
    2000,   //07
    2000,   //08
    2000,   //09
    2000,   //10
    2000,   //11
    2000,   //12
    2000,   //13
    2000,   //14
    2000,    //15
    100,    //16  -- DANGER Tight Range -- lolz fixed. (~100 - ~600 now)
    2000,    //17
    2000,   //18
    2000,   //19
};
*/

/*
 * LibInit(int input_fd, int output_fd): 	
 * 		Initializes the library
 */
void LibInit(int input_fd, int output_fd, char v, int *threshold) {
    gui_input  = input_fd;
    gui_output = output_fd;//get a file descriptor set for the gui.
    verbose = v;
    photoThreshold = threshold;

    int i;

    // open the driver
#ifdef X86
    // This way we can test on x86 architectures.
    printf("x86 flag detected -- mallocing data storage\n");
    led = malloc(sizeof(unsigned int) * 40);
    sensor = led+20;
#endif
#ifndef X86
	fd = open("/dev/mem", O_RDWR|O_SYNC);
	if(!fd) {
		printf("Unable to open /dev/mem.  Ensure it exists (major=1, minor=1)\n");
		return;
	}
	//led = malloc(sizeof(unsigned int) * 40);
	led = (unsigned int *)mmap(0, MAP_SIZE, PROT_READ|PROT_WRITE, MAP_SHARED, fd, BASE_ADDRESS & ~MAP_MASK);
	sensor = (unsigned int *) led + 20; //just a marker mid memory space
#endif
	lastLEDVal = malloc(20*sizeof(int));
    lastSensorVal = malloc(20*sizeof(int));
    for(i = 0; i < 20; i++){
		lastLEDVal[i] = -1;
		lastSensorVal[i] = -1;
	}

    srand(time(NULL));
}

/*
 * CheckSensorAbs(int cupNumber):
 *      return Actual read value of a sensor
 */
int CheckSensorAbs(int cupNumber){
	int val = sensor[cupNumber];
    //fprintf(stdout, "New Sensor Switch value for cup %d: %d!\n", cupNumber, val);//debugging
	return val;
}

/*
 * CheckSensor(int cupNumber):
 * 		return 1 for CupPresent and 0 if not
 */
int CheckSensor(int cupNumber){
    int attempts = 5;
    int allTheSame = TRUE;

    int temp = sensor[cupNumber];
	int sensorVal = FALSE;
	if(temp != 0x0000FFFF && temp > photoThreshold[cupNumber]) sensorVal = TRUE;

	//printf("S%-2d: Abs: %-4d  On: %X\n", cupNumber, temp, sensorVal);
	
    //look across the last 'attempts' values.  If they all match, we consider it a valid change :)
    int i;
	for(i = 1; i < attempts && allTheSame; i++){
        int prevSensorVal = sensorVal;
        temp = sensor[cupNumber];
	    if(temp != 0x0000FFFF && temp > photoThreshold[cupNumber]) sensorVal = TRUE;
        else sensorVal = FALSE;

        if(prevSensorVal != sensorVal) allTheSame = FALSE;
        usleep(5000);
    }

    char s[80];
    //if we had consistent readings, set the last and return the new val, else just give them the old val  
    if(allTheSame){
        if(lastSensorVal[cupNumber] != sensorVal) {
            sprintf(s,"CHANGED: sensor[%d] = %d\n",cupNumber, sensor[cupNumber]); 
            debug_printf(s);
        }
        lastSensorVal[cupNumber] = sensorVal;
	    return sensorVal;
    }
    else{
	    return lastSensorVal[cupNumber];
    }
}
	 
/*
 * CheckSensorEdge(int cupNumber):
 *
 *		return == 0 if no edge and no cup
 *		       == 1 if no edge and cup
 *		       == 2 if edge and no cup
 *		       == 3 if edge and cup
 */
int CheckSensorEdge(int cupNumber){
	int tempLastSensorVal = lastSensorVal[cupNumber];
    int sensorVal = CheckSensor(cupNumber);

    int edge = tempLastSensorVal != sensorVal;

	return (edge << 1) + sensorVal;
}



/*
 * CheckAllSensors():
 *		returns all sensor states as 1/0 in an int
 *		sends data to GUI if any of the sensors have changed
 */
int CheckAllSensors(){
	int sendToGUI = FALSE;
	int AllSensors = 0;
    int i;
	for(i = 19; i >= 0; i--){
		int singleSensor = CheckSensor(i);
		AllSensors = AllSensors << 1;
		if(singleSensor != lastSensorVal[i]) sendToGUI = TRUE;
		AllSensors = AllSensors | singleSensor;
	}
	//if(sendToGUI) SendGUICommand(SENSORS, 0, AllSensors);
    SendGUICommand(SENSORS, 0, AllSensors);
	return AllSensors;
 }


/*
 * SetCupColor(int cupNumber, int color):
 * 		color codes should be encoded in 6 hex values.
 * 		sends the color to the GUI if it has changed
 */
void SetCupColor(int cupNumber, int color){
	led[cupNumber] = color; // could move this into the if...
	if(color != lastLEDVal[cupNumber]) {
        if(verbose) fprintf(stdout, "Cup:%2d:%06X\n", cupNumber, color);//DEBUGGING
        SendGUICommand(CUPS, cupNumber, color);	
    }
    lastLEDVal[cupNumber] = color;
} 


/*
 * SetCupColors(int* cupNumbers, int cupCount, int color):
 * 		sets all cups in the array to a specified color
 *		reports any changes in physical cup color to the GUI.
 */
void SetCupColors(int* cupNumbers, int cupCount, int color){
	int i;
    for(i = 0; i < cupCount; i++){
		SetCupColor(cupNumbers[i], color);
	}
}


/*
 * FullState(): 
 * 		Prints statements for ALL current cup colors and sensors...
 */
void FullState(){
	int i;
    CheckAllSensors();

	for(i = 0; i < 20; i++){
		SendGUICommand(CUPS, i, lastLEDVal[i]);//send the last LED value we have stored...
	}
}


/*
 * SendGUICommand(int op, int cup, int data):
 *      Sends the given command to the GUI over the socket
 */
void SendGUICommand(int op1, int op2, int data){
	//3 bit opcode
	//5 bit cup value
	//24 bits data
	//ex.  000 00001 11111111 00000000 00000000  would send red to cup 1

    data = data & 0x00FFFFFF;
    op2 = op2   & 0x0000001F;
    data = data | (op2 << 24) | (op1 << 29);
    
    int count = 0;
    count = write(gui_output, &data, 4);
}

/*
 * int wait(unsigned int sec, unsigned int usec):
 *      Short Function to call select for us. If there is data available, it
 *      will return 1 otherwise it will return 0 (or -1 if there was an error
 *      with the select call).
 */
char wait(unsigned int sec, unsigned int usec) {
    fd_set readfds;
    struct timeval timeout;

    timeout.tv_sec = sec;
    timeout.tv_usec = usec;

    FD_ZERO(&readfds);
    FD_SET(gui_input, &readfds);

    int ret_val = select(gui_input+1, &readfds, NULL, NULL, &timeout);
    int command;

    if(ret_val == -1) {
        perror("Select Error");
    } else if(ret_val == 1) {
        // Read / Process command
        read(gui_input, &command, 4);
        ret_val = process_command(command);
    }

    return ret_val;
}

void debug_printf(char *s) {
#ifdef DEBUG
    printf("%s", s);
#endif
}
