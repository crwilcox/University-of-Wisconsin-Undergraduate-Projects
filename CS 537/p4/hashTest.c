///////////////////////////////////////////////////////////////////////////////
// Title:            Intro to Threads
// Files:            hashTest.c
// Semester:         Spring 2010
//
// Authors:          Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
//                   Christopher Wilcox - cwilcox@wisc.edu
// Course:           UW-Madison CS 537 - Introduction to Operating Systems
// Instructor:       Prof. Remzi Arpaci-Dusseau
//
//////////////////////////// 80 columns wide //////////////////////////////////
#include <assert.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <pthread.h>
#include "spinlock.h"
#include "mythreads.h"
#include "hash.h"

// global ints
int max;
int num;
int buckets;
hash_t* myHash;
double t1;

// Method used to calculate time
double GetTime() 
{
  struct timeval t;
  int rc = gettimeofday(&t, NULL);
  assert(rc == 0);
  return t.tv_sec + t.tv_usec/1e6; // (stare here)
}


void *mythread(void *arg)
{  
  int myInt = 0xCAB005E;

  int i;
  for (i = 0; i < max/num; i++) 
  {
	 Hash_Insert(myHash, &myInt, i);
  }
  return NULL;
}


int main(int argc, char *argv[])
{
	// Get cmd line args
  if (argc != 4) 
  {
      fprintf(stderr, "usage: hashTest <hashitems> <numthreads> <numbuckets> \n");
      exit(1);
  }
  max = atoi(argv[1]);
  num = atoi(argv[2]);
  buckets = atoi(argv[3]);

  	// initialization of vars
  	pthread_t p[500];
  	assert(num < 500);   //+500 threads => system starts to malfunction
 
 
	myHash = malloc(sizeof(hash_t));
  	Hash_Init(myHash, buckets);
  	
	// Creates a number of threads used to test the lock
  	int i;
  	t1 = GetTime();
  
  	for (i = 0; i < num; i++)
	  	Pthread_create(&p[i], NULL, mythread, NULL); 

  	// join waits for the threads to finish
  	for (i = 0; i < num; i++)
	  	Pthread_join(p[i], NULL); 

  	// Record the runtime of the total execution
  	double t2 = GetTime();
  	printf("threads: %-4d buckets: %-4d time: %f \n", num, buckets, t2 - t1);
	free(myHash);
  	return 0;
}

