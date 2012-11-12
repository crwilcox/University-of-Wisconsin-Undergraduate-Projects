///////////////////////////////////////////////////////////////////////////////
// Title:           Project 6 - Simple File Server
// Files:           server.c
// Semester:        Spring 2010
//
// Authors:         Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
//                  Christopher Wilcox - cwilcox@wisc.edu
// Course:          UW-Madison CS 537 - Introduction to Operating Systems
// Instructor:      Prof. Remzi Arpaci-Dusseau
//
//////////////////////////// 80 columns wide //////////////////////////////////
// INCLUDED LIBRARIES
#include <stdio.h>
#include <stdlib.h>
#include "udp.h"
#include "libfs.h"

// String Literals
#define BUFFER_SIZE 				(4096 + 4096/2)
#define ROOTINODENUM				(0)
#define MFS_DIRECTORY				(0)
#define MFS_REGULAR_FILE		(1)
#define MFS_BLOCK_SIZE			(4096)
#define PACKET_HEADER_SIZE	(2048)
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

// GLOBAL VARIABLES
int portnum;
char* fileSystemImage;
fs_t* fs;
int rootInodeNum = ROOTINODENUM;
int sd;
int ret;
	
	
/*
 * A Simple File Server Application is a program who will sit on the server, 
 * awaiting messages to handle for different file systems.
 *
 * @author Tyler Tucker, Christopher Wilcox
 *
 */


/*
 * Method used to return error to the client about a malformed packet.
 *
 * @return (CLIENT) Malformed packet error.
 */ 
void malformedPacket(int rc, int sd, struct sockaddr_in s)
{
	fprintf(stderr, "SERVER: Error: received malformed request, 1|2|3\n");
	char reply[BUFFER_SIZE];
	sprintf(reply, "%s", REQ_LKUP);
	strcat(reply, "|");
	char fail[10];
	sprintf(fail, "%s", RES_FAIL);
	strcat(reply, fail);
	// send failure message to client
	rc = UDP_Write(sd, &s, reply, BUFFER_SIZE);
}
 
 
/*
 * Parse input values and verify they are valid. If invalid, terminate server
 */
void getargs(int argc, char *argv[])
{
	if(argc != 3)
	{ fprintf(stderr, "Usage %s <portnum> <file-system-image>\n", argv[0]); exit(1); }
	portnum = atoi(argv[1]);
	if(portnum <= 0) 
	{ fprintf(stderr, "bad port value\n Usage: %s <portnum> <file-system-image>\n", argv[0]); exit(1); }
	// Check for valid file system image
	fileSystemImage = argv[2];		//get argv[2] to have a pretty name
}



/*
 *	Initializes connection with the client (just a bounce back signal)
 */
void SERVER_Init(int rc, char *buffer, struct sockaddr_in s)
{
	// Print to screen received message
	printf("SERVER:: Known message: read %d bytes, SERVER INIT\n", rc);
			
	// Construct reply
	char reply[BUFFER_SIZE];
	sprintf(reply, RES_INIT);
	if(ret == -1)
		strcat(reply, "|-1");
	rc = UDP_Write(sd, &s, reply, BUFFER_SIZE);
}


/*
 * Checks to see if the request it correct, if so, looks up a file on the FS
 * and returns either correct inode number (>0) or invalid inode number (-)
 */
void SERVER_Lookup(int rc, char *buffer, struct sockaddr_in s)
{
	// Print to screen received message
	printf("SERVER:: Known message: read %d bytes: SERVER LOOKUP\n", rc);
			
	// Get inode number and char* from the message
	char *token;
	int pinum;
	char *name;
	int done = 0;
	token = strtok(buffer, "|");  // command name -> garbage
	// Checking for error cases (malformed requests)
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 1:SERVER LOOKUP\n"); done = 1; }
	token = strtok(NULL, "|");	// Get the pinum (as a str)
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 2:SERVER LOOKUP\n"); fprintf(stderr, "SERVER: %s\n", token); done = 1; } 
	pinum = atoi(token);				// Convert the pinum to int
	name = strtok(NULL, "|");		// Get the file/folder name
	if(name == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 3:SERVER LOOKUP\n"); fprintf(stderr, "SERVER: %s\n", name); done = 1; } 
	// If pass above strtok, continue on, else spam error
	if(done == 0)								// If there were no malformed request errors
	{
		printf("  SERVER: pinum: %i, name: %s\n", pinum, name);
		// Lookup inode
		int entryInodeNum = lookupInode(fs, pinum, name);	
		printf("  SERVER: entryInodeNum = %i\n", entryInodeNum);
		
		// append char* to response
		char reply[BUFFER_SIZE];
  	sprintf(reply, RES_LKUP);
  	char temp[BUFFER_SIZE];
  	sprintf(temp, "|%i", entryInodeNum);
  	strcat(reply, temp);
		// send message to client
  	rc = UDP_Write(sd, &s, reply, BUFFER_SIZE);
	}
	else	// Server has received a malformed request, send failure notice to client
	{ malformedPacket(rc, sd, s); }
}


/*
 * Checks to see if the request it correct, if so, looks up a file on the FS
 * and returns the file stats if exist, or invalid file stat struct if it 
 * does not exist.
 *
 * @return(CLIENT) File Stat Struct if inode number is valid, else returns 
 *                 invalid File Stat Struct
 */
void SERVER_Stat(int rc, char *buffer, struct sockaddr_in s)
{
	// Print to screen received message
	printf("SERVER:: Known message: read %d bytes, SERVER STAT\n", rc);
			
	// Get inode number from the message
	char *token;
	int inum;
	int done = 0;
	// Checking for error cases (malformed requests)
	token = strtok(buffer, "|");	// command name -> garbage
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 1:SERVER STAT\n"); done = 1; }
	token = strtok(NULL, "|");		// Get the inum (as a str)
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 2:SERVER STAT\n"); fprintf(stderr, "SERVER: %s\n", token); done = 1; } 
	inum = atoi(token);						// Convert the inum to int
	// If pass above strtok, continue on, else spam error
	if(done == 0)									// If there were no malformed request errors
	{
		printf("  SERVER: inum: %i\n", inum);
		// Get stats on a given inode
		inode_t currInodeInfo = getInodeInfo(fs, inum);

		// print inode data
		printf("  SERVER: inode type: %i, size: %i, numBlocks: %i\n", currInodeInfo.type, currInodeInfo.size, currInodeInfo.blocks);
		// append char* to response
		char reply[BUFFER_SIZE];
  	sprintf(reply, RES_STAT);
	  char temp[BUFFER_SIZE];
	  sprintf(temp, "|%i", currInodeInfo.type);
	  strcat(reply, temp);
  	sprintf(temp, "|%i", currInodeInfo.size);
  	strcat(reply, temp);
  	sprintf(temp, "|%i", currInodeInfo.blocks);
  	strcat(reply, temp);
		// send message to client
  	rc = UDP_Write(sd, &s, reply, BUFFER_SIZE);
	}
	else	// Server has received a malformed request, send failure notice to client
		{ malformedPacket(rc, sd, s); }	
}


/*
 * Checks to see if the request it correct, if so, create a file on the FS.
 * If operation is a sucess, return pass message to client. If a problem occurs
 * (such as not enough room, invalid type, etc) return error.
 *
 * @return(CLIENT) PASS packet if operation is sucessful, else error packet
 *                 if there was an error.
 */
void SERVER_Creat(int rc, char *buffer, struct sockaddr_in s)
{
	// Print to screen received message
	printf("SERVER:: Known message: read %d bytes, SERVER CREAT\n", rc);
			
	// Get inode number and char* from the message
	char *token;
	int pinum;
	char *name;
	int type;
	int done = 0;
	token = strtok(buffer, "|");  // command name -> garbage
	// Checking for error cases (malformed requests)
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 1:SERVER CREAT\n"); done = 1; }
	token = strtok(NULL, "|");	// Get the pinum (as a str)
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 2:SERVER CREAT\n"); fprintf(stderr, "SERVER: %s\n", token); done = 1; } 
	pinum = atoi(token);				// Convert the pinum to int
	name = strtok(NULL, "|");		// Get the file/folder name
	if(name == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 3:SERVER CREAT\n"); fprintf(stderr, "SERVER: %s\n", name); done = 1; } 
	token = strtok(NULL, "|");	// Get the type (as astring 
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 4:SERVER CREAT\n"); fprintf(stderr, "SERVER: %s\n", token); done = 1; } 
	type = atoi(token);					// convert type to int
	// If pass above strtok, continue on, else spam error
	if(done == 0)								// If there were no malformed request errors
	{
		printf("  SERVER: pinum: %i, name: %s", pinum, name);
		if(type == MFS_DIRECTORY)
			printf(" , type: DIRECTORY\n");
		else if(type == MFS_REGULAR_FILE)
			printf(" , type: REGULAR FILE\n");
		else
			printf(" , type: UFO\n");
		// Add file to the system
		int sucess =  creatfs(fs, pinum, type, name);
																			
		// Send response if valid operation
		if(sucess == 0)
		{
			// if all good, send positive response
			char reply[BUFFER_SIZE];
  		sprintf(reply, RES_CRET);
			// send message to client
  		rc = UDP_Write(sd, &s, reply, BUFFER_SIZE);
		}
		else	// else invalid operation, return error
		{
			char reply[BUFFER_SIZE];
  		sprintf(reply, RES_CRET);
  		char temp[BUFFER_SIZE];
	 		sprintf(temp, "|%i", sucess);
	 		strcat(reply, temp);
			// send message to client
  		rc = UDP_Write(sd, &s, reply, BUFFER_SIZE);
		}

	}
	else	// Server has received a malformed request, send failure notice to client
		{ malformedPacket(rc, sd, s); }
}


/*
 * Checks to see if the request it correct, if so, remove a file on the FS.
 * If operation is a sucess, return pass message to client. If a problem occurs
 * (such as not empty directory, invalid message, etc) return -1.
 *
 * @return(CLIENT) PASS if sucessful operation, else PASS|-1.
 */
void SERVER_Unlink(int rc, char *buffer, struct sockaddr_in s)
{
	// Print to screen received message
	printf("SERVER:: Known message: read %d bytes, SERVER UNLINK\n", rc);
			
	// Get inode number and char* from the message
	char *token;
	int pinum;
	char *name;
	int done = 0;
	token = strtok(buffer, "|");  // command name -> garbage
	// Checking for error cases (malformed requests)
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 1:SERVER UNLINK\n"); done = 1; }
	token = strtok(NULL, "|");	// Get the pinum (as a str)
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 2:SERVER UNLINK\n"); fprintf(stderr, "SERVER: %s\n", token); done = 1; } 
	pinum = atoi(token);				// Convert the pinum to int
	name = strtok(NULL, "|");		// Get the file/folder name
	if(name == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 3:SERVER UNLINK\n"); fprintf(stderr, "SERVER: %s\n", name); done = 1; } 
	// If pass above strtok, continue on, else spam error
	if(done == 0)								// If there were no malformed request errors
	{
		printf("  SERVER: pinum: %i, name: %s\n", pinum, name);
		// Remove file from system
		int passRemoval = unlinkfs(fs, pinum, name);															
		//printf("SERVER: passRemoval: %i\n", passRemoval);
				
		// append char* to response
		char reply[BUFFER_SIZE];
  	sprintf(reply, RES_ULNK);
  	char temp[BUFFER_SIZE];
	 	sprintf(temp, "|%i", passRemoval);
	 	strcat(reply, temp);
		// send message to client
	 	rc = UDP_Write(sd, &s, reply, BUFFER_SIZE);
	}
	else	// Server has received a malformed request, send failure notice to client
		{ malformedPacket(rc, sd, s); }
}


/*
 * Checks to see if the request it correct, if so, read a file on the FS.
 * If operation is a sucess, return block of data to client. If a problem occurs
 * (such as not a file, invalid inode number, etc) return error.
 *
 * @return(CLIENT)
 */
void SERVER_Read(int rc, char *buffer, struct sockaddr_in s)
{
	// Finish SERVER Read function
	// Print to screen received message
	printf("SERVER:: Known message: read %d bytes, SERVER READ\n", rc);
			
	// Get inode number and char* from the message
	char *token;
	int inum;
	int block;
	int done = 0;

	token = strtok(buffer, "|");  // command name -> garbage
	// Checking for error cases (malformed requests)
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 1:SERVER READ\n"); done = 1; }
	token = strtok(NULL, "|");	// Get the inum (as a str)
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 2:SERVER READ\n"); fprintf(stderr, "SERVER: %s\n", token); done = 1; } 
	inum = atoi(token);					// Convert the inum to int
	token = strtok(NULL, "|");		// Get the block num (as a str)
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 3:SERVER READ\n"); fprintf(stderr, "SERVER: %s\n", token); done = 1; } 
	// If pass above strtok, continue on, else spam error
	block = atoi(token);				// Get the block num at an int
	if(done == 0)								// If there were no malformed request errors
	{
		printf("  SERVER: inum: %i, block number: %i\n", inum, block);
		// Remove file from system
		// TODO: Type warning (returns unsigned char*, but UDP expects char*)
		char data[MFS_BLOCK_SIZE];											
		int allGood = readfs(fs, inum, block, (unsigned char*)data);
		printf("  SERVER: allgood: %i\n", allGood);
		
		// If read is valid, send back data
		if((allGood == 0) && (data != NULL))
		{
			// append data to response
			char reply[BUFFER_SIZE];
  		sprintf(reply, RES_READ);
 			strcat(reply, "|0|");
 			
	 		// Use memcopy and put at end of region
	 		memcpy(&reply[PACKET_HEADER_SIZE], data, MFS_BLOCK_SIZE);
	 		
			// send message to client
	 		rc = UDP_Write(sd, &s, reply, BUFFER_SIZE);
		}	
		else // not valid read, return error
		{	
			char reply[BUFFER_SIZE];
  		sprintf(reply, RES_READ);
	 		strcat(reply, "|1");
			// send message to client
  		rc = UDP_Write(sd, &s, reply, BUFFER_SIZE);
		}	

	}
	else	// Server has received a malformed request, send failure notice to client
		{ malformedPacket(rc, sd, s); }
}


/*
 * Checks to see if the request it correct, if so, read a file on the FS.
 * If operation is a sucess, return block of data to client. If a problem occurs
 * (such as not a file, invalid inode number, etc) return error.
 *
 * @return(CLIENT)
 */
void SERVER_Write(int rc, char *buffer, struct sockaddr_in s)
{
	// Finsih SERVER Write function
	printf("SERVER:: Known message: read %d bytes, SERVER WRITE\n", rc);
	
	// Get inode number and char* from the message
	char *token;
	char *dataBlock = malloc(MFS_BLOCK_SIZE);
	int inum;
	int block;
	int done = 0;
	char *tempBuff = malloc(BUFFER_SIZE);				// Copy buffer for tokenizing
	memcpy(tempBuff, buffer, BUFFER_SIZE);			// As to not corrupt original
	memcpy(dataBlock, &buffer[PACKET_HEADER_SIZE], MFS_BLOCK_SIZE);
	
	token = strtok(tempBuff, "|");  // command name -> garbage
	// Checking for error cases (malformed requests)
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 1:SERVER WRITE\n"); done = 1; }
	token = strtok(NULL, "|");	// Get the inum (as a str)
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 2:SERVER WRITE\n"); fprintf(stderr, "SERVER: %s\n", token); done = 1; } 
	inum = atoi(token);					// Convert the inum to int
	token = strtok(NULL, "|");	// Get the block num (as a str)
	if(token == NULL)
		{ fprintf(stderr, "SERVER: Error: received malformed request 3:SERVER WRITE\n"); fprintf(stderr, "SERVER: %s\n", token); done = 1; } 
	block = atoi(token);				// Get the block num at an int
	// If pass above strtok, continue on, else spam error
	if(done == 0)								// If there were no malformed request errors
	{
		printf("  SERVER: inum: %i, block number: %i\n", inum, block);
		// Write block to system
		// TODO: Type warning (returns unsigned char*, but UDP expects char*)										
		int allGood = writefs(fs, inum, block, (unsigned char*)dataBlock);
		printf("  SERVER: allgood: %i\n", allGood);
		
		// If read is valid, send back good data
		char reply[BUFFER_SIZE];
  	sprintf(reply, RES_WRTE);
  	if(allGood != 0)			// If error, append error part
 			strcat(reply, "|-1");
		// send message to client
	 	rc = UDP_Write(sd, &s, reply, BUFFER_SIZE);
	}
	else	// Server has received a malformed request, send failure notice to client
		{ malformedPacket(rc, sd, s); }
}


/*
 * If the message is unknown (even the command portion is invalid), return an 
 * invalid message to the client
 *
 * @return (CLIENT) Invalid messag received error.
 */
void SERVER_Unknown(int rc, char *buffer, struct sockaddr_in s)
{
	printf("SERVER:: Error: Unknown message: read %d bytes\n", rc);
	char reply[BUFFER_SIZE];
	sprintf(reply, RES_INV);
	rc = UDP_Write(sd, &s, reply, BUFFER_SIZE);
}


/*
 * Main Method for the server side - Starts up and spin/waits for a message
 * to handle.
 */
int main(int argc, char *argv[])
{
	// Verify args
	getargs(argc, argv);
	// Setup server side listening port
	sd = UDP_Open(portnum);
	assert(sd > -1);
	
	// Init(Create/Load) the fs
	fs = malloc(sizeof(fs_t));
	ret = loadfs(fs, fileSystemImage);
			
	// Beginning of main spin/wait
	printf("waiting in loop\n");
	while (1) 
	{
		// Decode the sent message into a buffer
		struct sockaddr_in s;
		char buffer[BUFFER_SIZE];
		int rc = UDP_Read(sd, &s, buffer, BUFFER_SIZE);
		
		if(strncmp(buffer, REQ_INIT, 1) == 0) 			// If the request is to do an MFS_INIT()
			SERVER_Init(rc, buffer, s);
		else if(strncmp(buffer, REQ_LKUP, 1) == 0)	// If the request is to do an MFS_LOOKUP, do it!
			SERVER_Lookup(rc, buffer, s);	
		else if(strncmp(buffer, REQ_STAT, 1) == 0)	// If the request is to do a MFS_STAT, do it and return the STAT object
			SERVER_Stat(rc, buffer, s);
		else if(strncmp(buffer, REQ_CRET, 1) == 0)	// Request to create file/directory
			SERVER_Creat(rc, buffer, s);
		else if(strncmp(buffer, REQ_ULNK, 1) == 0)	// Request to remove file/directory
			SERVER_Unlink(rc, buffer, s);
		else if(strncmp(buffer, REQ_READ, 1) == 0)	// Request to read a file/directory
			SERVER_Read(rc, buffer, s);
		else if(strncmp(buffer, REQ_WRTE, 1) == 0)	// Request a block write to a file
			SERVER_Write(rc, buffer, s);
		else if (rc > 0)														// Case: Unknown message received
			SERVER_Unknown(rc, buffer, s);
		// else the sent packet is non-existent or not a valid UDP packet
	}
	return 0;
}


