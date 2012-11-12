///////////////////////////////////////////////////////////////////////////////
// Title:           Project 5 - Scalable Web Server
// Files:           buffer.c
// Semester:        Spring 2010
//
// Authors:         Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
//                  Christopher Wilcox - cwilcox@wisc.edu
// Course:          UW-Madison CS 537 - Introduction to Operating Systems
// Instructor:      Prof. Remzi Arpaci-Dusseau
//
//////////////////////////// 80 columns wide //////////////////////////////////
// Included Libraries
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "buffer.h"
#include "request.h"

#define FIFO    0
#define SFF     1
#define SFFBS   2

/*
* A buffer is a fixed size data structure who can be used as either a fixed
* size queue or a fixed size list.
*
* Each buffer has a head pointer, tail pointer, thenumber of requests in
* the buffer and the buffer type (FIFO/SFF/SFFBS)
*
* @author Tyler Lee Tucker, Christopher Wilcon
*
*/

// Helper/Wrapper Methods
void* Malloc(size_t size)
{
	void* retVal = malloc(size);
	if(retVal == NULL)
	{ printf("Error: Cannot malloc\n");  exit(1); }
	return retVal;
}


/*
* Initializes the buffer by setting it to being empty, and setting the number
* of requests to zero
*
* *buff - Pointer to the buffer to initialize
* 
* @return - A buffer_t pointer if valid args, NULL if invalid.
*/
buffer_t* Buffer_Init(int maxElemts, char* scheduling, int N)
{
	buffer_t *buff = Malloc(sizeof(buffer_t));
	buff->head = NULL;
	buff->tail = NULL;	
	buff->numElemts = 0;
	buff->maxElemts = maxElemts;
	
	if(!strcmp("FIFO", scheduling))        buff->schedalg = FIFO;
	else if(!strcmp("SFF", scheduling))    buff->schedalg = SFF;
	else if(!strcmp("SFF-BS", scheduling)) buff->schedalg = SFFBS;
	else
	{
		fprintf(stderr, "Bad Argument for Scheduling\n Choose from FIFO, SFF and SFF-BS\n NOTE: SFF-BS requires an entry for N\n");
		free(buff);
		return NULL;
	}
	
	// If in SFFBS, initialize N, else set to 0
	if(buff->schedalg == SFFBS)		buff->N = N;
	else													buff->N = 1;
	
	if(maxElemts <= 0 || buff->N <= 0)
	{
		fprintf(stderr, "Bad Argument for Scheduling, maxElmts&N gt zero\n");
		free(buff);
		return NULL;
	}

	return buff;
}


/*
* Pushes an request onto the buffer, as long at it is not full. Position on
* the buffer depends on what sorting algorithm was chosen on buffer ini.
*
* *buff - Buffer to add an request to
* *request - Element to add
*
* @return - 0 if added sucessfully, 1 if buffer is full (failed to add), 
*           2 if buffer is invalid/corrupt, 3 if a place cannot be found (error)
*
*/
int Buffer_Push(buffer_t *buff, request_t *request)
{
	if(request == NULL)		return 2;
	int filesize = request->size;
	int epoch = 0;
	if(buff->schedalg == SFFBS)		epoch = request->epoch;
	else													request->epoch = 0;
	
	//printf("#### THE PUSHED OBJECT HAS FILESIZE: %d\n", filesize);
			
	// If full, return 1
	if((buff == NULL) || (buff->numElemts == buff->maxElemts))
		return 1;
	if(!(buff->schedalg >= 0 && buff->schedalg <= 2))
		return 2;
		
	// if head is NULL, initialize item as first item in list
	// If head is not initialize, initialize
	if(buff->head == NULL)
	{
		// Make a new head
		bufferItem *newHead = Malloc(sizeof(bufferItem));
		newHead->next = NULL;
		newHead->prev = NULL;
		newHead->request = request;
		newHead->filesize = filesize;
		// Update buffer
		buff->head = newHead;
		buff->tail = newHead;
		buff->numElemts = 1;
		return 0;
	}
	// Else if FIFO, add new item at the end of the buffer
	else if(buff->schedalg == FIFO)
	{
		// Add request into end of buffer
		bufferItem *newItem = Malloc(sizeof(bufferItem));
		newItem->next = NULL;
		newItem->prev = buff->tail;
		newItem->request = request;
		newItem->filesize = filesize;
		buff->tail->next = newItem;
		buff->tail = newItem;
		buff->numElemts++;
		return 0;
	}
	
	// Else if SFF, linearly search though the list and insert at appriopiate place
	else if(buff->schedalg == SFF || buff->schedalg == SFFBS)
	{
		// Traverse the list with a currPtr
		bufferItem *beginOfEpoch = buff->head;
		bufferItem *endOfEpoch = NULL;
		bufferItem *currItem = NULL;
		
		// Create new item prior to traversing
		bufferItem *newItem = Malloc(sizeof(bufferItem));
		newItem->request = request;
		newItem->filesize = filesize;
		
		//list is sorted: head-->small and tail-->big
		// Step one: find beginning of epoch by pointer currItem at it
		while(beginOfEpoch->request->epoch < epoch)
		{
			if(beginOfEpoch->next == NULL)
			{
				// insert item at end of buffer, since epoch is new to buffer
				buff->tail->next = newItem;
				newItem->prev = buff->tail;
				newItem->next = NULL;
				buff->tail = newItem;
				buff->numElemts++;			
				return 0;
			}
			else  // increment the pointer to the next item
				beginOfEpoch = beginOfEpoch->next;
		}
		// Now, find the end of the Epoch
		endOfEpoch = beginOfEpoch;
		while(endOfEpoch->request->epoch == epoch && endOfEpoch != buff->tail)
		{
			// increment the pointer to the next item
			endOfEpoch = endOfEpoch->next;
		}
		if(endOfEpoch != buff->tail) // want endOfEpoch to point at last item
			endOfEpoch = endOfEpoch->prev;
		
		// Now to check file size within the epoch
		currItem = beginOfEpoch;
		// Checking the case if the new file is smaller than the 1st epoch item
		if(currItem->request->size >= filesize)
		{
			// If so, new item is the smallest, make it into the 1st curr epoch item
			// in the case the newly added item is the smallest and the new head
			if(currItem->prev == NULL)
			{
				newItem->prev = NULL;
				buff->head = newItem;
			}
			else  // if not a new head but still the smallest, update prev item
			{
				currItem->prev->next = newItem;
				newItem->prev = currItem->prev;
			}
			currItem->prev = newItem;
			newItem->next = currItem;
			buff->numElemts++;
			return 0;
		}
		
		// Checking the case if the new file is bigger than the tail
		else if(endOfEpoch->request->size <= filesize)
		{
			// If we are looking at the tail, point to null
			if(endOfEpoch == buff->tail)
			{
				newItem->next = NULL;
				buff->tail = newItem;
			}
			else // if not tail, update next item appropiately
			{
				newItem->next = endOfEpoch->next;
				endOfEpoch->next->prev = newItem;
			}
			endOfEpoch->next = newItem;
			newItem->prev = endOfEpoch;
			buff->numElemts++;			
			return 0;
		}
		printf("5\n");
		// Else place it in the middle, so traverse and look
		while(currItem->next != NULL && currItem != endOfEpoch->next)
		{
			// If it fits between two requests, we are done!
			if(currItem->filesize <= filesize && currItem->next->filesize >= filesize)
			{
				printf("I am doing something weird...\n");
			  newItem->prev = currItem;
			  newItem->next = currItem->next;
			  currItem->next->prev = newItem;
			  currItem->next = newItem;
				buff->numElemts++;
				return 0;
			}
			else  // Else keep searching
				currItem = currItem->next;
		}
		fprintf(stderr, "Error: SFF/SFFBS Push cannot find a place to add new item.\n");
		return 3; // Should never reach here
	}
	
	// Else return failure, gracefully
	// Shouldn't ever get here
	fprintf(stderr, "Buffer_Push failed, bad sorting algorithm choice?\n");
	return 2;
}


/*
* Pops an request off the front end of the buffer.
* Items are already ordered with the proper schedalg
*
* *buff - Buffer to pop from
*
* @return - bufferItem pointer if sucessful, NULL if not
*/
request_t* Buffer_Pop(buffer_t *buff)
{
	// If empty or buffer does not exist
	if(buff == NULL || (buff->numElemts == 0))
		return NULL;
	// Check to make sure scheduing type is in range
	if(!(buff->schedalg >= 0 && buff->schedalg <= 2))
		return NULL;
	// If there is only 1 request left, return it and null the head
	if(buff->numElemts == 1)
	{
		request_t *returnPtr = buff->head->request;
		buff->numElemts = buff->numElemts--;
		free(buff->head);
		buff->head = NULL;
		buff->tail = NULL;
		return returnPtr;
	}
	// Else Remove the head, and update buffer data
	bufferItem *currItem = buff->head;
	buff->head = buff->head->next;
	buff->head->prev = NULL;
	buff->numElemts = buff->numElemts--;
	request_t *returnPtr = currItem->request;
	
	
	//Need to age all other items in the buffer to say that this one was
	//popped while they sat
	bufferItem *iter = buff->head;
	while(iter != NULL){
		//update age
		iter->request->age = iter->request->age + 1;
		iter = iter->next;
	}
	
	
	free(currItem);
	return returnPtr;
}


/*
* Prints out the content of the buffer, along with current size and max
*/
void Buffer_Dump(buffer_t *buff)
{
	if(buff == NULL || buff->numElemts == 0)
	{
		if(buff == NULL)
			printf("\n--->Buffer not alloc<---\n\n");
		else
			printf("\n--->Buffer is empty<---\n\n");
		return;
	}
	// Traverse the buffer, priting in order
	int i;
	bufferItem *currItem = buff->head;
	printf("Used # of Elements: %i, ", buff->numElemts);
	printf("-> Max # of Elements: %i\n", buff->maxElemts);
	for(i = 0; i < buff->numElemts; i++)
	{ 
		if(currItem == NULL || currItem->request == NULL)
		{
			printf("Error, item or request is NULL, terminating...\n");
			exit(1);
		}
		printf("-> #%i: epoch: %i, filesize: %i\n", i, currItem->request->epoch, currItem->filesize);
		currItem = currItem->next;
	}
}


/*
* Frees the memory the buffer holds in memory, to be leak-free.
*
* Note: If a copy of a reference to the memory still exists, the data will
*       be invalid, to avoid attepts at using bad pointers.
*
* @return - A null pointer.
*/
buffer_t* Buffer_Free(buffer_t* buff)
{
	// If list is NULL or empty, free buffer and call it a day
	if(buff == NULL || buff->numElemts == 0 || buff->head == NULL) {
		if(buff == NULL)	return NULL;
		else							{ free(buff);	return NULL;	}
	}
	// If there is only 1 item, free it.
	if(buff->head->next == NULL)		
	{
		free(buff->head);
		free(buff);
		return NULL;
	}
	// If there are more than 1 request, traverse and free
	bufferItem *currItem = buff->head;
	bufferItem *nextItem = buff->head->next;
	while(currItem != NULL)
	{ 
		free(currItem);
		currItem = nextItem;
		if(currItem == NULL)	nextItem = currItem;
		else 									nextItem = currItem->next;
	}
	free(buff);  
	return NULL;
}

