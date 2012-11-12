///////////////////////////////////////////////////////////////////////////////
// Title:            Project 5 - Scalable Web Server
// Files:            server.c
// Semester:         Spring 2010
//
// Authors:          Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
//                   Christopher Wilcox - cwilcox@wisc.edu
// Course:           UW-Madison CS 537 - Introduction to Operating Systems
// Instructor:       Prof. Remzi Arpaci-Dusseau
//
//////////////////////////// 80 columns wide ////////////////////////////////
#include "cs537.h"
#include "request.h"
#include "buffer.c"
#include <pthread.h>
#include <assert.h>
#include <sys/time.h>
// 
// server.c: A very, very simple web server
//
// To run:
//  server <portnum (above 2000)>
//
// Repeatedly handles HTTP requests sent to this port number.
// Most of the work is done within routines written in request.c
//

#define FIFO    0
#define SFF     1
#define SFFBS   2

// Global Variables
buffer_t *buffer;
static pthread_cond_t fill 	= PTHREAD_COND_INITIALIZER;
static pthread_cond_t empty = PTHREAD_COND_INITIALIZER;
static pthread_mutex_t buff	= PTHREAD_MUTEX_INITIALIZER;
int issued = 0;
int epochSize = 0;


void getargs(int *port, int *threads, int *buffers, int* N, int argc, char *argv[]){
	if (argc != 5 && argc !=6){	fprintf(stderr, "Usage: %s <port> <threads> <buffers> <schedalg>\n", argv[0]); exit(1);	}
	*port = atoi(argv[1]);
	if(port == NULL || *port <= 2000){ fprintf(stderr, "Bad Port Value\nUsage: %s <port> <threads> <buffers> <schedalg> <N (for SFF-BS only)>\n", argv[0]); exit(1); }
	*threads 	= atoi(argv[2]);
	if(threads == NULL || *threads <= 0){ fprintf(stderr, "Bad Thread Value\nUsage: %s <port> <threads> <buffers> <schedalg> <N (for SFF-BS only)>\n", argv[0]); exit(1); }
	*buffers = atoi(argv[3]);
	if(buffers == NULL || *buffers <= 0){ fprintf(stderr, "Bad Buffer Value\nUsage: %s <port> <threads> <buffers> <schedalg> <N (for SFF-BS only)>\n", argv[0]); exit(1); }
	/* FIFO, SFF, or SFF-BS */
	if(!strcmp("FIFO", argv[4]) && argc==5);//do nothing, ok arg
	else if(!strcmp("SFF", argv[4]) && argc==5);//do nothing, ok arg
	else if(!strcmp("SFF-BS", argv[4]) && argc==6)
	{	//process N, if bad value, freak out
		*N = atoi(argv[5]);
		if(N == NULL || *N <= 0){ fprintf(stderr, "Bad value for N\n"); exit(1);}
	}
	else 
	{
		fprintf(stderr, "Bad Argument for <schedalg>\n Choose from FIFO, SFF and SFF-BS\n NOTE: SFF-BS requires an entry for N\n"); exit(1);
	}	
}

/*
 * ganks information from the fd, and puts it into a struct.  this way we get
 * to use the information more than once... It also means we will only need to 
 * read it one time
 */
request_t* generateElement(int connfd){
	int filesize, is_static;
	char filename[MAXLINE],  cgiargs[MAXLINE], fsBuffer[MAXLINE], method[MAXLINE], uri[MAXLINE], version[MAXLINE];
	rio_t myRio;

	Rio_readinitb(&myRio, connfd);
	Rio_readlineb(&myRio, fsBuffer, MAXLINE);

	//Line Looks like this
	//GET / HTTP/1.1
	sscanf(fsBuffer, "%s %s %s", method, uri, version);
		
	is_static = requestParseURI(uri, filename, cgiargs);
	
	//Get the filesize using the stat function
	//we need the filename as found w/ requestParseURI
	struct stat myStat;
	stat(filename, &myStat); 
	filesize = myStat.st_size;

	request_t* myRequest = malloc(sizeof(request_t));
	
	//FILL myRequest so that we can use it later
	myRequest->connfd = connfd;
	myRequest->size = filesize;
	myRequest->is_static = is_static;
	strcpy(myRequest->method, method);
	strcpy(myRequest->uri, uri);
	strcpy(myRequest->version, version);
	strcpy(myRequest->filename, filename);
	strcpy(myRequest->cgiargs, cgiargs);

	/*
	arrival
		handled here
	dispatch
		time from arrival to being in worker thread
	read 
		duration spent reading the static content
	complete 
		is the time from socket write - arrival
	age 
		will be updated on every pop of the buffer	
	*/
	//initialize all unset variables to the value of -1 so if they manage
	//to not be set, they will at least be in a predictable state
	myRequest->arrival = getTime();//use getTime, a custom function to process gettimeofday claculations...
	myRequest->dispatch = 0;
	myRequest->read = 0;
	myRequest->complete = 0;
	myRequest->age = 0;	
	
	return myRequest;
}


void* workerThreadCode(void *worker){
	worker_t *this = (worker_t*) worker;

	request_t* requestElement;
	while(1){
		pthread_mutex_lock(&buff);		
		while(buffer->numElemts == 0){
			pthread_cond_wait(&fill, &buff); //wait until we have something to do
		}	
		
		#ifdef DEBUG
		printf("#BEG WORKER THREAD # %d\n", this->id);
		#endif

		//return a struct that contains all needed vars
		//use this to request handle
		requestElement = Buffer_Pop(buffer);

		//NEED TO Set elements of worker_t
		//id 		- set in main on setup...
		//count		- will increment no matter what
		//staticReq	- will increment if  is_static
		//dynamicReq- will increment if ~is_static
		this->count = this->count + 1;
		if(requestElement->is_static) this->staticReq = this->staticReq + 1;
		else this->dynamicReq = this->dynamicReq + 1;

		requestElement->threadstats = this;	// set the threadstats pointer in the 
											//object to this thread (to display stats later)

		//NEED TO TAKE THE requestElement AND ADD TO IT ALL THE NECESSARY INFORMATION
		//Certain thread stats need to be added
		requestElement->dispatch = getTime() - (requestElement->arrival);

		pthread_cond_signal(&empty);//we  have got our object out of the queue/buffer, signal		
		pthread_mutex_unlock(&buff);
		
		//Handle the user request	
		requestHandle(requestElement);

		//close this connecting file descriptor
		Close(requestElement->connfd);
		free(requestElement);
		
		#ifdef DEBUG
		printf("#END WORKER THREAD #%d\n", this->id);
		#endif
	}
	return NULL;
}

int main(int argc, char *argv[]){
	int listenfd, connfd, port, numThreads, numBuffers, clientlen, N;
	char* schedalg = argv[4];//just making this more obvious... the calculation is handled by the buffer	

	struct sockaddr_in clientaddr;

	// Get and Check args, then create a buffer
	getargs(&port, &numThreads, &numBuffers, &N, argc, argv);
	buffer = Buffer_Init(numBuffers, schedalg, N);//argv[4] is the scheduling algorithm
	epochSize = N;
	
	worker_t workers[numThreads]; //shouldnt have more than 500 threads...
	assert(numThreads < 500);

	int i = 0;
	for(; i < numThreads; i++){
		workers[i].id = i;
		workers[i].count = 0;
		workers[i].staticReq = 0;
		workers[i].dynamicReq = 0;
		if(pthread_create(&workers[i].thread, NULL, workerThreadCode, &workers[i]) != 0){
			fprintf(stderr, "Failed to create a thread... Exiting"); exit(1);
		}
	}
	


	listenfd = Open_listenfd(port);
	while (1) {
		clientlen = sizeof(clientaddr);
		connfd = Accept(listenfd, (SA *)&clientaddr, (socklen_t *) &clientlen);
		
		
		#ifdef DEBUG
		printf("#####################################\n");
		printf("###  numElemts= %d  maxElemts = %d  ###\n", buffer->numElemts, buffer->maxElemts);
		printf("#####################################\n");
		#endif

		//lock the buffer, we are allowed to add more to it...
		pthread_mutex_lock(&buff);
		
		//if the buffer is full, dont attempt to do the below code
		while(buffer->numElemts == buffer->maxElemts)
		{
			#ifdef DEBUG
				printf("#### BUFFER IS FULL ####");
			#endif
			pthread_cond_wait(&empty, &buff);
		}
		
		request_t* myElement = generateElement(connfd);
		//set the epoch
		myElement->epoch = issued/epochSize;

		if(Buffer_Push(buffer, myElement)){
			//if we get here and the buffer is full, we did something wrong
			fprintf(stderr, "Couldn't add to buffer... Uh Oh!\n");
			exit(1);
		}
		// Update issued requests
		issued++;

		pthread_cond_signal(&fill);
		pthread_mutex_unlock(&buff);
	}

	return 0;//exit normally if we get here...
}

