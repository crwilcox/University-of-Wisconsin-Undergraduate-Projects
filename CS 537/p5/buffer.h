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

#ifndef __buffer_h__
#define __buffer_h__
#include "request.h"
/*
 * A buffer is a fixed size data structure who can be used as either a fixed
 * size queue or a fixed size list.
 *
 * Each buffer has a head pointer, tail pointer and number of elements in
 * the buffer
 *
 * @author Tyler Lee Tucker, Christopher Wilcon
 *
 */

/*
 * A buffer is a list w/ a head and tail pointer, and the number of elements 
 * along with the maximum size.
 */
typedef struct __buffer_t
{
	struct __bufferItem *head;
  struct __bufferItem *tail;
  int numElemts;
  int maxElemts;
  int schedalg;
  int N;
}buffer_t;

/*
 * A buffer item is an element of a buffer. Contains a pointer to the next,
 * previous item, an element, size of request
 *
 * Implemented via fixed size doubly linked list
 */
typedef struct __bufferItem
{
	struct __bufferItem *next;
	struct __bufferItem *prev;
	request_t* request;
	int filesize;
}bufferItem;

/*
 * Various methods used to mutate and/or access the buffer elements
 */
buffer_t*		Buffer_Init(int maxElemts, char* scheduling, int N);
int					Buffer_Push(buffer_t *buf, request_t *request);
request_t*	Buffer_Pop(buffer_t *buf);
void  			Buffer_Dump(buffer_t *buf);
buffer_t*		Buffer_Free(buffer_t* buff);

#endif // __buffer_h__

