///////////////////////////////////////////////////////////////////////////////
// Title:           Project 6 - Simple File Server
// Files:           mfs.c
// Semester:        Spring 2010
//
// Authors:         Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
//                  Christopher Wilcox - cwilcox@wisc.edu
// Course:          UW-Madison CS 537 - Introduction to Operating Systems
// Instructor:      Prof. Remzi Arpaci-Dusseau
//
//////////////////////////// 80 columns wide //////////////////////////////////
// INCLUDED LIBRARIES
#include "mfs.h"
#include "udp.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <unistd.h>
#include <time.h>
#include <errno.h>
#include <string.h>

// String Literals
#define CLIENT_PORT 	(45678)		// What port to send/receive on the client
#define BUFFER_SIZE 	(4096 + 4096/2)		// In bits
#define TIMEOUTFACTOR (25)			// In nanoseconds
#define MFS_DIRECTORY    (0)
#define MFS_REGULAR_FILE (1)
#define MFS_BLOCK_SIZE   (4096)
#define PACKET_HEADER_SIZE	(2048)
#define true  (1)
#define false (0)
// Messages
#define REQ_INIT 	("I")
#define REQ_LKUP	("L")
#define REQ_STAT	("S")
#define REQ_WRTE	("W")
#define	REQ_READ	("R")
#define REQ_CRET	("C")
#define REQ_ULNK	("U")
#define RES_INV		("Invalid Request")
#define RES_FAIL	("Failure")
#define RES_INIT	("I|PASS")
#define RES_LKUP	("L|PASS")
#define RES_STAT	("S|PASS")
#define RES_WRTE	("W|PASS")
#define	RES_READ	("R|PASS")
#define RES_CRET	("C|PASS")
#define RES_ULNK	("U|PASS")

// Global Variables
int clientSockFD = -1;
int serverSockFD = -1;
struct sockaddr_in addr;
struct sockaddr_in addr2;
int fsInit = false;


/*
 * MFS is an interface for the file system.
 *
 * NOTE: This file is a client library compiled to libmfs.so
 *
 * @author Tyler Lee Tucker, Christopher Wilcox
 */
 
 
/*
 * Sends a signal, and wait for the response.
 *
 */
int getResp(int clientSockFD, struct sockaddr_in *addr2, char *receivedMessage)
{
	struct timespec wait;
	wait.tv_sec = 0;
	wait.tv_nsec = TIMEOUTFACTOR;
	
	// Sit and read from the port, if no packet is found, return error
	int i;
	for(i = 0; i <= 10; i++)
	{
		#ifdef DEBUG  // Debugging info - timeout
		printf("CLIENT: Checking for packet, loop %i\n", i);
		#endif
		if(UDP_Read(clientSockFD, addr2, receivedMessage, BUFFER_SIZE) == -1)
		{
			// If reached final loop and no read, means message was not received in
			// the timeout period
			if(i == 10)
			{
				//printf("CLIENT: Error: timeout reached, loop %i, timeout: %u s, %u ns\n", i, (unsigned int)wait.tv_sec, (unsigned int)wait.tv_nsec);
				//printf("CLIENT: Error: timeout reached.\n");
				return -1;
			}
			#ifdef DEBUG
			printf("CLIENT: Packet not found on loop %i, sleeping for %u s, %u ns\n", i, (unsigned int)wait.tv_sec, (unsigned int)wait.tv_nsec);
			#endif
			sleep(wait.tv_sec);
			nanosleep(&wait, NULL);
			// Overflow protected time incrementer
			int nsecOverflow = wait.tv_nsec * 10;
			if(nsecOverflow / 10 != wait.tv_nsec)
			{
				if(wait.tv_sec == 0)
				{ wait.tv_sec = 1; wait.tv_nsec = 2500000000; }// insures that once we hit seconds, still work
				else
					wait.tv_sec = wait.tv_sec + 4;
			}
			else
				wait.tv_nsec = nsecOverflow;
		}
		else // Message was received, return success
		{
			printf("CLIENT: Message recieved on loop %i\n", i); return 0;
		}
	} 
	fprintf(stderr, "Returning from subroutine sendSigResp in non-standard way\n");
	return -1;
}


/*
 * Sends a signal, and if a response is not heard from, resend signal 
 * until a response is got.
 *
 */ 
void sendMesgGetResp(char *messageToSend, int serverSockFD, struct sockaddr_in *addr, 
                     int clientSockFD, struct sockaddr_in *addr2, 
                     char *receivedMessage, char* type)
{
	// Loops until a message is heard back from. In the event the timeout is 
	// reached, resend the signal, and wait until a message is received
	while(1)
	{
  	serverSockFD = UDP_Write(clientSockFD, addr, messageToSend, BUFFER_SIZE);
  	// Print the sent message to screen
  	printf("CLIENT:: sent message: size %d bytes, %s\n", serverSockFD, type);

  	if(serverSockFD > 0) //if message was sent, wait for response, else try to resend it
			if(getResp(clientSockFD, addr2, receivedMessage) == 0) // wait for response, if never get it resend message
				break;  // if message is received, return
		printf("CLIENT: Error: Timeout reached, resending message...\n");
	}
	// If received NULL/Responce invalid, null the message
	if((receivedMessage == NULL) | (strncmp(receivedMessage, RES_INV, 15) == 0))
		receivedMessage = NULL;
	return;
}


/*
 * MFS_Init() takes a host name and port number and uses 
 * those to find the server exporting the file system.
 * 
 * @return: 0 if sucessful, -1 if error
 */
int MFS_Init(char *hostname, int port)
{
	// Check args
	if(hostname == NULL || port < 0)
		{ fprintf(stderr, "Error: Null hostname or negative port\n"); exit(1); }
		
	// Open a standard port (non-blocking) on the client system
	clientSockFD = UDP_Open(CLIENT_PORT);
	assert(clientSockFD > -1);
	if(fcntl(clientSockFD, F_SETFL, O_NONBLOCK) != 0)
	{
		fprintf(stderr, "CLIENT: Error: cannot change client socket to Non-blocking, errno: %i\n", errno);
		fprintf(stderr, "CLIENT: Error: %s\n", strerror(errno));
		//exit(1); // TODO: States failure but works...
	}
	if(!(clientSockFD > -1)) // Error checking
	{ fprintf(stderr, "CLIENT: Error: client socket not accesable\n"); return -1; }
	
	// Create a connection to the server
	serverSockFD = UDP_FillSockAddr(&addr, hostname, port);
	if(!(serverSockFD == 0))  // Error checking
	{ fprintf(stderr, "CLIENT: Error: server socket not accesable\n"); return -1; }
	
	// Sending a message to retrieve ini okay from the Server, for the current FS
	char messageToSend[BUFFER_SIZE];
	char messageReceived[BUFFER_SIZE];
	sprintf(messageToSend, REQ_INIT);
	sendMesgGetResp(messageToSend, serverSockFD, &addr, clientSockFD, &addr2, messageReceived, "MFS_INIT");
	
	// Decode received message
	if(messageReceived == NULL)	// If message was NULL, means invalid request or bad packet
		{ fprintf(stderr, "CLIENT: Error: server has returned invalid request error\n"); return -1; }
	if(strncmp(messageReceived, RES_INIT, 6) != 0)	// Means request is intact but not what we wanted
		{ fprintf(stderr, "CLIENT: Error: received non-vaild response\n"); return -1; }
	if(strcmp(messageReceived, RES_INIT) != 0)	// if initialization fails (|-1), report failure
		{ fprintf(stderr, "CLIENT: Error: received failure response\n"); return -1; }
	// Request sucessfull, FS is now initialized
	fsInit = true;
	return 0;
}


/*
 * MFS_Lookup() takes the parent inode number (which should be the inode number of a directory) 
 * and looks up the entry name in it. The inode number of name is returned. 
 * 
 * @return: Success: return inode number of name, Failure: return -1. 
 * Failure modes: invalid pinum, name does not exist in pinum.
 */
int MFS_Lookup(int pinum, char *name)
{	
	// Check to see if fs has been init
	if(fsInit == false)
		{ fprintf(stderr, "CLIENT: Error: File System not initialized.\n"); return -1; }
	if((pinum > 4095) | (pinum < 0))
	{
		fprintf(stderr, "CLIENT: Error: Invalid parent indo number.\n");
		return -1;
	}
	// Construct message to send
	char messageToSend[BUFFER_SIZE];
	char messageReceived[BUFFER_SIZE];
	sprintf(messageToSend, REQ_LKUP);
	// cat info onto it
	char pinumStr[BUFFER_SIZE];
	sprintf(pinumStr, "|%d|%s", pinum, name);
	strcat(messageToSend, pinumStr);
	
	// Send message
	sendMesgGetResp(messageToSend, serverSockFD, &addr, clientSockFD, &addr2, messageReceived, "MFS_LOOKUP");
	
	// Check response - Make sure the response is of the form of a lookup response
	if(messageReceived == NULL)	// If message was NULL, means invalid request or bad packet
		{ fprintf(stderr, "CLIENT: Error: server has returned invalid request error\n"); return -1; }
	if(strncmp(messageReceived, RES_LKUP, 1) != 0)	// Message was not the form of what we expected
		{ fprintf(stderr, "CLIENT: Error: received non-vaild response\n"); return -1; }
	if(strncmp(messageReceived, RES_LKUP, 6) != 0)	// Message was corrupted
		{ fprintf(stderr, "CLIENT: Error: packet corruption or not expected packet\n"); return -1; }
	
	// Decode response and determine if there is an error
	char *token;
	token = strtok(messageReceived, "|");	// Ignore first part, is cmd name
	if(token == NULL)
		{ fprintf(stderr, "CLIENT: Error: received malformed response 1\n"); return -1;	} 
	token = strtok(NULL, "|");						// Ignore second part, is PASS
	if(token == NULL)
	{
		fprintf(stderr, "CLIENT: Error: received malformed response 2\n");
		fprintf(stderr, "CLIENT: %s\n", token);
		return -1;		
	} 
	token = strtok(NULL, "|");						// Inode value, parse if not null
	if(token == NULL)
	{
		fprintf(stderr, "CLIENT: Error: received malformed response 3\n");
		fprintf(stderr, "CLIENT: %s\n", token);
		return -1;		
	} 
	int inodeVal = atoi(token);
	printf("CLIENT: received inode value: '%i'\n", inodeVal);
	return inodeVal;
}


/*
 * MFS_Stat() returns some information about the file specified by inum. 
 * Success: 0, Failure: -1
 * Failure modes: inum does not exist.
 */
int MFS_Stat(int inum, MFS_Stat_t *m)
{
	// Check to see if fs has been init
	if(fsInit == false)
		{ fprintf(stderr, "CLIENT: Error: File System not initialized.\n"); return -1; }
	if((inum > 4095) | (inum < 0))
	{
		fprintf(stderr, "CLIENT: Error: Invalid parent inode number.\n");
		return -1;
	}
	// Construct message to send
	char messageToSend[BUFFER_SIZE];
	char messageReceived[BUFFER_SIZE];
	sprintf(messageToSend, REQ_STAT);
	// cat info onto it
	char pinumStr[BUFFER_SIZE];
	sprintf(pinumStr, "|%d", inum);
	strcat(messageToSend, pinumStr);
	// Send message
	sendMesgGetResp(messageToSend, serverSockFD, &addr, clientSockFD, &addr2, messageReceived, "MFS_STAT");
	
	// Check response
	if(messageReceived == NULL)	// If message was NULL, means invalid request or bad packet
		{ fprintf(stderr, "CLIENT: Error: server has returned invalid request error\n"); return -1; }
	if(strncmp(messageReceived, RES_STAT, 1) != 0)	// Message was not of the expected form
		{ fprintf(stderr, "CLIENT: Error: received non-vaild response:\n"); return -1; }
	if(strncmp(messageReceived, RES_STAT, 6) != 0)	// Message was corrupted, most likely
		{ fprintf(stderr, "CLIENT: Error: packet corruption or not expected packet\n"); return -1; }
	
	// Decode response and determine if there is an error
	char *token;
	token = strtok(messageReceived, "|");	// command info, ignore
	if(token == NULL)
	{ fprintf(stderr, "CLIENT: Error: received malformed response 0\n"); return -1; } 
	token = strtok(NULL, "|");		// pass info, ignore
	if(token == NULL)
	{ fprintf(stderr, "CLIENT: Error: received malformed response 1\n"); return -1; } 
	token = strtok(NULL, "|");		// type info
	if(token == NULL)
	{ fprintf(stderr, "CLIENT: Error: received malformed response 2\n"); fprintf(stderr, "CLIENT: %s\n", token); return -1;	} 
	m->type = atoi(token);				// collecting type info
	token = strtok(NULL, "|");		// size info
	if(token == NULL)
	{ fprintf(stderr, "CLIENT: Error: received malformed response 3\n"); fprintf(stderr, "CLIENT: %s\n", token); return -1;	} 
	m->size = atoi(token);				// collecting size info
	token = strtok(NULL, "|");		// block info
	if(token == NULL)
	{ fprintf(stderr, "CLIENT: Error: received malformed response 3\n"); fprintf(stderr, "CLIENT: %s\n", token); return -1;	} 
	m->blocks = atoi(token);			// collecting block info
	// If all properties are -1, means invalid inode number was given
	if((m->type == -1) && (m->blocks == -1) && (m->size == -1))
	{	
		fprintf(stderr, "CLIENT: Error: Invalid inode number\n");
		m = NULL; 
		return -1; 
	}
	// Else sucessfully received message and content.
	printf("CLIENT: received MFS_Stat_t.\n");
	return 0;
}


/*
 * MFS_Write() writes a block of size 4096 bytes at the block offset specified by block. 
 * 
 * Success: 0, Failure: -1
 * Failure modes: invalid inum, invalid block, not a regular file (you can't write to directories).
 */
int MFS_Write(int inum, char *buffer, int block)
{
	
	// Check to see if fs has been init
	if(fsInit == false)
		{ fprintf(stderr, "CLIENT: Error: File System not initialized.\n"); return -1; }
	if((inum > 4095) | (inum < 0))
		{ fprintf(stderr, "CLIENT: Error: Invalid indo number.\n"); return -1; }
	if(buffer == NULL)
		{ fprintf(stderr, "CLIENT: Error: Buffer is null.\n"); return -1; }	
	// Construct message to send
	char messageToSend[BUFFER_SIZE];
	char messageReceived[BUFFER_SIZE];
	sprintf(messageToSend, REQ_WRTE);
	
	// cat info onto it, include data to write
	char inumStr[BUFFER_SIZE];
	sprintf(inumStr, "|%i|%i|", inum, block);
	strcat(messageToSend, inumStr);
	// Limit buffer to buffSize
	memcpy(&messageToSend[PACKET_HEADER_SIZE], buffer, MFS_BLOCK_SIZE);

	// Send message
	sendMesgGetResp(messageToSend, serverSockFD, &addr, clientSockFD, &addr2, messageReceived, "MFS_WRITE");
	
	// Check response - Make sure the response is of the form of a lookup response
	if(messageReceived == NULL)	// If message was NULL, means invalid request or bad packet
		{ fprintf(stderr, "CLIENT: Error: server has returned invalid request error\n"); return -1; }
	if(strncmp(messageReceived, RES_WRTE, 1) != 0)	// Message was not the form of what we expected
		{ fprintf(stderr, "CLIENT: Error: received non-vaild response\n"); return -1; }
	if(strcmp(messageReceived, RES_WRTE) != 0)			// If error part included spit out error
		{ fprintf(stderr, "CLIENT: Write failed\n"); return -1; }
		
	// All is well
	return 0;
}


/*
 * MFS_Read() reads a block specified by block into the buffer from file specified by inum . 
 * The routine should work for either a file or directory; 
 * directories should return data in the format specified by MFS_DirEnt_t. 
 * 
 * Success: 0, Failure: -1
 * Failure modes: invalid inum, invalid block.
 */
int MFS_Read(int inum, char *buffer, int block)
{
	// Check to see if fs has been init
	if(fsInit == false)
		{ fprintf(stderr, "CLIENT: Error: File System not initialized.\n"); return -1; }
	if((inum > 4095) | (inum < 0))
		{ fprintf(stderr, "CLIENT: Error: Invalid indo number.\n"); return -1; }
	// Construct message to send
	char messageToSend[BUFFER_SIZE];
	char messageReceived[BUFFER_SIZE];
	sprintf(messageToSend, REQ_READ);
	
	// cat info onto it
	char inumStr[BUFFER_SIZE];
	sprintf(inumStr, "|%i|%i", inum, block);
	strcat(messageToSend, inumStr);

	// Send message
	sendMesgGetResp(messageToSend, serverSockFD, &addr, clientSockFD, &addr2, messageReceived, "MFS_READ");
	
	// Check response - Make sure the response is of the form of a lookup response
	if(messageReceived == NULL)	// If message was NULL, means invalid request or bad packet
		{ fprintf(stderr, "CLIENT: Error: server has returned invalid request error\n"); return -1; }
	if(strncmp(messageReceived, RES_READ, 1) != 0)	// Message was not the form of what we expected
		{ fprintf(stderr, "CLIENT: Error: received non-vaild response\n"); return -1; }
	if(messageReceived[7] == '1')			// If received not found, spit out error
		{ fprintf(stderr, "CLIENT: received file not found\n"); return -1; }

	// Decode responc
	memcpy(buffer, &messageReceived[PACKET_HEADER_SIZE], MFS_BLOCK_SIZE);

	char *token;
	token = strtok(messageReceived, "|");	// Ignore first part, is cmd name
	if(token == NULL)
		{ fprintf(stderr, "CLIENT: Error: received malformed response 1\n"); return -1;	} 
	token = strtok(NULL, "|");						// Ignore second part, is PASS
	if(token == NULL)
	{
		fprintf(stderr, "CLIENT: Error: received malformed response 2\n");
		fprintf(stderr, "CLIENT: %s\n", token);
		return -1;		
	} 
	
	// All is well
	return 0;
}


/*
 * MFS_Creat() makes a file ( type == MFS_REGULAR_FILE) or directory ( type == MFS_DIRECTORY) 
 * in the parent directory specified by pinum of name name . 
 * Success: 0, Failure: -1
 *
 * Failure modes: pinum does not exist. If name already exists, return success (think about why).
 * 				  we return success because it will allow this server to be idempotent...
 */
int MFS_Creat(int pinum, int type, char *name)
{
	// Check to see if fs has been init
	if(fsInit == false)
		{ fprintf(stderr, "CLIENT: Error: File System not initialized.\n"); return -1; }
	if((pinum > 4095) | (pinum < 0))
	{
		fprintf(stderr, "CLIENT: Error: Invalid parent inode number.\n");
		return -1;
	}
	if((type != MFS_DIRECTORY) && (type != MFS_REGULAR_FILE))
	{
		fprintf(stderr, "CLIENT: Error: Invalid file type.\n");
		return -1;
	}
	// Construct message to send
	char messageToSend[BUFFER_SIZE];
	char messageReceived[BUFFER_SIZE];
	sprintf(messageToSend, REQ_CRET);
	// cat info onto it
	char tempStr[BUFFER_SIZE];
	sprintf(tempStr, "|%d|%s|%d", pinum, name, type);
	strcat(messageToSend, tempStr);

	// Send message
	sendMesgGetResp(messageToSend, serverSockFD, &addr, clientSockFD, &addr2, messageReceived, "MFS_CREAT");
	
	// Check response
	if(messageReceived == NULL)	// If message was NULL, means invalid request or bad packet
		{ fprintf(stderr, "CLIENT: Error: server has returned invalid request error\n"); return -1; }
	if(strncmp(messageReceived, RES_CRET, 1) != 0)	// Message was not of the expected form
		{ fprintf(stderr, "CLIENT: Error: received non-vaild response\n"); return -1; }
	if(strncmp(messageReceived, RES_CRET, 6) != 0)	// Message was corrupted, most likely
		{ fprintf(stderr, "CLIENT: Error: packet corruption or not expected packet\n"); return -1; }
	if(strcmp(messageReceived, RES_CRET) == 0)			// If sucessful, should return C|PASS only
		return 0;		// If packet arrived non-corrupt for pass, all done!
	return -1;		// Else error!
}


/*
 * MFS_Unlink() removes the file or directory name from the directory specified by pinum . 
 * Success: 0, Failure: -1
 * Failure modes: pinum does not exist, directory is NOT empty. 
 * Note that the name not existing is NOT a failure by our definition (think about why this might be).
 * 		This is not a failure because of idempotent reasons... it may have been previously removed... 
 * 		if that is the case, it is no big deal, just continue on...  We wish to allow the user to 
 * 		request the same operation multiple times without wrecking themselves
 */
int MFS_Unlink(int pinum, char *name)
{
	// Check to see if fs has been init
	if(fsInit == false)
		{ fprintf(stderr, "CLIENT: Error: File System not initialized.\n"); return -1; }
	if((pinum > 4095) | (pinum < 0))
	{
		fprintf(stderr, "CLIENT: Error: Invalid parent indo number.\n");
		return -1;
	}
	// Construct message to send
	char messageToSend[BUFFER_SIZE];
	char messageReceived[BUFFER_SIZE];
	sprintf(messageToSend, REQ_ULNK);
	// cat info onto it
	char pinumStr[BUFFER_SIZE];
	sprintf(pinumStr, "|%d|%s", pinum, name);
	strcat(messageToSend, pinumStr);
	
	// Send message
	sendMesgGetResp(messageToSend, serverSockFD, &addr, clientSockFD, &addr2, messageReceived, "MFS_UNLINK");
	
	// Check response
	if(messageReceived == NULL)	// If message was NULL, means invalid request or bad packet
		{ fprintf(stderr, "CLIENT: Error: server has returned invalid request error\n"); return -1; }
	if(strncmp(messageReceived, RES_ULNK, 1) != 0) // Message was not as expected
		{ fprintf(stderr, "CLIENT: Error: received non-vaild response\n"); return -1; }
	if(strncmp(messageReceived, RES_ULNK, 6) != 0) // Message was most likely corrupt
		{ fprintf(stderr, "CLIENT: Error: packet corruption or not expected packet\n"); return -1; }
	
	// Decode response and determine if there is an error
	char *token;
	token = strtok(messageReceived, "|");  // cmd
	if(token == NULL)
		{ fprintf(stderr, "CLIENT: Error: received malformed response 1\n"); return -1; } 
	token = strtok(NULL, "|");	//pass
	if(token == NULL)
	{
		fprintf(stderr, "CLIENT: Error: received malformed response 2\n");
		fprintf(stderr, "CLIENT: %s\n", token);
		return -1;		
	} 
	token = strtok(NULL, "|");	// passRemoval as a str
	if(token == NULL)
	{
		fprintf(stderr, "CLIENT: Error: received malformed response 3\n");
		fprintf(stderr, "CLIENT: %s\n", token);
		return -1;		
	} 
	int passRemoval = atoi(token);	//passRemoval as an int
	printf("CLIENT: received passed removal value: '%i'\n", passRemoval);
	return passRemoval;
}


