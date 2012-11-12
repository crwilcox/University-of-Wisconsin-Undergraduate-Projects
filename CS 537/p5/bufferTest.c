///////////////////////////////////////////////////////////////////////////////
// Title:           Project 5 - Scalable Web Server
// Files:           bufferTest.c
// Semester:        Spring 2010
//
// Authors:         Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
//                  Christopher Wilcox - cwilcox@wisc.edu
// Course:          UW-Madison CS 537 - Introduction to Operating Systems
// Instructor:      Prof. Remzi Arpaci-Dusseau
//
//////////////////////////// 80 columns wide //////////////////////////////////
#include <stdio.h>
#include <unistd.h>
#include "buffer.c"

// Buffer wrappers
void W_Buffer_Push(buffer_t *buf, request_t* request) {
	if(Buffer_Push(buf, request) != 0)
		printf("Error: failed on pushing data, buffer is full: elmn: n/a, filesize: %i\n", request->size);
}
void W_Buffer_Dump(buffer_t *buf) {
	#ifdef DEBUG
	Buffer_Dump(buf); printf("\n");
	#endif
}


int main(int argv, char *argc[])
{
	printf("Testing buffer...\n");

	// Testing of FIFO buffer
	buffer_t *myBuff = Buffer_Init(3, "FIFO", 0);
	//   Testing filling boundary conditions for buffer
	W_Buffer_Dump(myBuff);
	request_t *a = malloc(sizeof(request_t));		a->size = 0;
	request_t *b = malloc(sizeof(request_t));		b->size = 2;
	request_t *c = malloc(sizeof(request_t));		c->size = 4;
	request_t *d = malloc(sizeof(request_t));		d->size = 8;
	W_Buffer_Push(myBuff, d);
	W_Buffer_Dump(myBuff);
	W_Buffer_Push(myBuff, c);
	W_Buffer_Dump(myBuff);
	W_Buffer_Push(myBuff, b);
	W_Buffer_Dump(myBuff);
	if(Buffer_Push(myBuff, a) != 1)
		printf("Error: not rejecting element into full buffer!\n");
	if(myBuff->numElemts != 3)
		printf("Error: number of elements is wrong!\n");
	//   Emptying buffer
	int i;						
	for(i = 0; i < 3; i++)
		Buffer_Pop(myBuff);
	W_Buffer_Dump(myBuff);
	if(Buffer_Pop(myBuff) != NULL)
		printf("Error: Not rejecting pops on empty buffer!\n");
	if(myBuff->numElemts != 0)
		printf("Error: number of elements is wrong!\n");
	//   Refilling buffer in asending order to test popping in order
	W_Buffer_Push(myBuff, a);
	W_Buffer_Dump(myBuff);
	W_Buffer_Push(myBuff, b);
	W_Buffer_Dump(myBuff);
	W_Buffer_Push(myBuff, c);
	W_Buffer_Dump(myBuff);
	if(Buffer_Push(myBuff, d) != 1)
		printf("Error: not rejecting element into full buffer!\n");
	if(myBuff->numElemts != 3)
		printf("Error: number of elements is wrong!\n");
	//   popping, checking order file
	if(Buffer_Pop(myBuff)->size != 0)
		printf("Error: Not poping 1st size, FIFO!\n");
	W_Buffer_Dump(myBuff);
	if(Buffer_Pop(myBuff)->size != 2)
		printf("Error: Not poping 2nd size, FIFO!\n");
	W_Buffer_Dump(myBuff);
	if(Buffer_Pop(myBuff)->size != 4)
		printf("Error: Not poping 3rd size, FIFO!\n");
	W_Buffer_Dump(myBuff);
	if(Buffer_Pop(myBuff) != NULL)
		printf("Error: Not rejecting smallest pops on empty buffer!\n");
	if(myBuff->numElemts != 0)
		printf("Error: number of elements is wrong!\n");
	//   Freeing memory - FIFO
	free(a); free(b); free(c); free(d);
	myBuff = Buffer_Free(myBuff);
	
	
	// Testing of SFF buffer
	myBuff = Buffer_Init(4, "SFF", 0);
	//   Refilling buffer in random order to check popping smallest file
	request_t *twenty = malloc(sizeof(request_t));		twenty->size = 20;
	request_t *five = malloc(sizeof(request_t));			five->size = 5;
	request_t *ten = malloc(sizeof(request_t));				ten->size = 10;
	request_t *thirty = malloc(sizeof(request_t));		thirty->size = 30;
	W_Buffer_Push(myBuff, twenty);
	W_Buffer_Dump(myBuff);
	W_Buffer_Push(myBuff, five);
	W_Buffer_Dump(myBuff);
	W_Buffer_Push(myBuff, ten);
	W_Buffer_Dump(myBuff);
	W_Buffer_Push(myBuff, thirty);
	W_Buffer_Dump(myBuff);
	//   Checking boarder cases
	if(Buffer_Push(myBuff, twenty) != 1)
		printf("Error: not rejecting element into full buffer!\n");
	if(myBuff->numElemts != 4)
		printf("Error: number of elements is wrong!\n");
	//   emptying smallest file
	if(Buffer_Pop(myBuff)->size != 5)
		printf("Error: Not poping 1st size, SFF!\n");
	W_Buffer_Dump(myBuff);
	if(Buffer_Pop(myBuff)->size != 10)
		printf("Error: Not poping 2nd size, SFF!\n");
	W_Buffer_Dump(myBuff);
	if(Buffer_Pop(myBuff)->size != 20)
		printf("Error: Not poping 3rd size, SFF!\n");
	W_Buffer_Dump(myBuff);
	if(Buffer_Pop(myBuff)->size != 30)
		printf("Error: Not poping 4th size, SFF!\n");
	W_Buffer_Dump(myBuff);
	if(Buffer_Pop(myBuff) != NULL)
		printf("Error: Not rejecting smallest pops on empty buffer!\n");
	if(myBuff->numElemts != 0)
		printf("Error: number of elements is wrong!\n");
	//   Freeing memory - SFF
	free(twenty); free(ten); free(five); free(thirty);
	myBuff = Buffer_Free(myBuff);
	
	
	// Error checking
		//#ifndef DEBUG		// if not in debug mode, hide errors we force
		//fclose(stderr);
		//#endif
	a = malloc(sizeof(request_t));		a->size = 0;
	Buffer_Init(10, "Henry", 20);
	Buffer_Init(-2, "SFF", 2);
	// testing of changing type post push, pre pop
	buffer_t *FIFOBuff = Buffer_Init(20, "FIFO", 1);
	if(Buffer_Push(FIFOBuff, a) != 0)		printf("Error: Cannot push\n"); 
	FIFOBuff->schedalg = 4;
	if(Buffer_Pop(FIFOBuff) != NULL)					printf("Error: Corrupt buffer allows pops\n");
	//   Freeing memory - SFF
	FIFOBuff = Buffer_Free(FIFOBuff);
	// testing of changing type post push, pre pop
	FIFOBuff = Buffer_Init(20, "FIFO", 0);
	FIFOBuff->schedalg = 4;
	if(Buffer_Push(FIFOBuff, a) != 2)		printf("Error: Can push on corrupt buffer\n");
	//   Freeing memory - SFF
	FIFOBuff = Buffer_Free(FIFOBuff);
	free(a);
	
	// Testing extra problem case
	buffer_t *smallBuff = Buffer_Init(1 ,"FIFO", 0);
	a = malloc(sizeof(request_t));		a->size = 100;
	b = malloc(sizeof(request_t));		b->size = 100;
	c = malloc(sizeof(request_t));		c->size = 100;
	if(Buffer_Push(smallBuff, b) != 0)		printf("Error: Cannot push on empty buffer\n");
	if(Buffer_Push(smallBuff, b) != 1)		printf("Error: Can push on full buffer\n");
	if(Buffer_Push(smallBuff, c) != 1)		printf("Error: Can push on full buffer\n");
	// Free memory - smallbuff
	free(a); free(b); free(c);
	smallBuff = Buffer_Free(smallBuff);
	
	// Testing SFF-BS boarder cases
	buffer_t *sffbsBuff = Buffer_Init(10, "SFF-BS", 3);
	a = malloc(sizeof(request_t));							a->size = 200;	a->epoch = 2;
	b = malloc(sizeof(request_t));							b->size = 100;	b->epoch = 2;
	c = malloc(sizeof(request_t));							c->size = 300;	c->epoch = 2;	
	d = malloc(sizeof(request_t));							d->size = 250;	d->epoch = 2;
	request_t *e = malloc(sizeof(request_t));		e->size = 300;	e->epoch = 3;
	request_t *f = malloc(sizeof(request_t));		f->size = 275;	f->epoch = 3;
	request_t *g = malloc(sizeof(request_t));		g->size = 350;	g->epoch = 3;
	request_t *h = malloc(sizeof(request_t));		h->size = 325;	h->epoch = 2;
	request_t *i2 = malloc(sizeof(request_t));		i2->size = 10;		i2->epoch = 1;
	request_t *j = malloc(sizeof(request_t));		j->size = 20;		j->epoch = 1;
	// Push and check
	printf("\n\nsffbsBuff tests...\n");
	W_Buffer_Dump(sffbsBuff);
	if(Buffer_Push(sffbsBuff, a) != 0)		printf("Error: Cannot push on empty buffer,a\n");
	W_Buffer_Dump(sffbsBuff);
	if(Buffer_Push(sffbsBuff, b) != 0)		printf("Error: Cannot push, b\n");
	W_Buffer_Dump(sffbsBuff);	
	if(Buffer_Push(sffbsBuff, c) != 0)		printf("Error: Cannot push, c\n");
	W_Buffer_Dump(sffbsBuff);	
	if(Buffer_Push(sffbsBuff, d) != 0)		printf("Error: Cannot push, d\n");
	W_Buffer_Dump(sffbsBuff);	
	if(Buffer_Push(sffbsBuff, e) != 0)		printf("Error: Cannot push, e\n");
	W_Buffer_Dump(sffbsBuff);	
	if(Buffer_Push(sffbsBuff, f) != 0)		printf("Error: Cannot push, f\n");
	W_Buffer_Dump(sffbsBuff);	
	if(Buffer_Push(sffbsBuff, g) != 0)		printf("Error: Cannot push, g\n");
	W_Buffer_Dump(sffbsBuff);	
	if(Buffer_Push(sffbsBuff, h) != 0)		printf("Error: Cannot push, h\n");
	W_Buffer_Dump(sffbsBuff);	
	if(Buffer_Push(sffbsBuff, i2) != 0)		printf("Error: Cannot push, i2\n");
	W_Buffer_Dump(sffbsBuff);	
	if(Buffer_Push(sffbsBuff, j) != 0)		printf("Error: Cannot push, j\n");
	W_Buffer_Dump(sffbsBuff);	
	if(Buffer_Push(sffbsBuff, j) != 1)		printf("Error: can push full stack\n");
	if(sffbsBuff->maxElemts != sffbsBuff->numElemts)	printf("Error: max != size");
	if(Buffer_Pop(sffbsBuff)->size != 10) printf("Error: poping 1st elemn\n");
	if(Buffer_Pop(sffbsBuff)->size != 20) printf("Error: poping 2st elemn\n");	
	if(Buffer_Pop(sffbsBuff)->size != 100) printf("Error: poping 3st elemn\n");
	if(Buffer_Pop(sffbsBuff)->size != 200) printf("Error: poping 4st elemn\n");
	if(Buffer_Pop(sffbsBuff)->size != 250) printf("Error: poping 5st elemn\n");	
	if(Buffer_Pop(sffbsBuff)->size != 300) printf("Error: poping 6st elemn\n");
	if(Buffer_Pop(sffbsBuff)->size != 325) printf("Error: poping 7st elemn\n");
	if(Buffer_Pop(sffbsBuff)->size != 275) printf("Error: poping 8st elemn\n");	
	if(Buffer_Pop(sffbsBuff)->size != 300) printf("Error: poping 9st elemn\n");
	if(Buffer_Pop(sffbsBuff)->size != 350) printf("Error: poping 10st elemn\n");
	if(Buffer_Pop(sffbsBuff) != NULL)			printf("Error: pooping non-exist elmnt\n");
	if(sffbsBuff->numElemts != 0)		printf("Error: not empty stack when should be\n");	
	
	// Free memory - sffbsBuff
	free(a);free(b);free(c);free(d);free(e);free(f);free(g);free(h);free(i2);free(j);
	sffbsBuff = Buffer_Free(sffbsBuff);
	
	// Done
	printf("Testing buffer complete!\n");
	return 0;
}
