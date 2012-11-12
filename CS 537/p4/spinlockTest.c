///////////////////////////////////////////////////////////////////////////////
// Title:            Intro to Threads
// Files:            myLockTester.c
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

// global ints
int max;
int num;
volatile int balance = 0;
spinlock_t *balanceLock;
double t1;


// Method used to calculate time
double GetTime() 
{
  struct timeval t;
  int rc = gettimeofday(&t, NULL);
  assert(rc == 0);
  return t.tv_sec + t.tv_usec/1e6; // (stare here)
}


// Lock the global counter value and increment it
void *mythread(void *arg)
{     
  int i;
  spinlock_acquire(balanceLock);
  for (i = 0; i < max; i++) 
  {
    //we would be doing a counter increment instead, but similar
    //spinlock_acquire(balanceLock); // Bad: could lockup when doing 4loopcalc
		balance++;
    if(i == max - 1)
    {
      //printf("    ->balance: %i, i: %i\n", balance, i);
//      double t3 = GetTime();
      //printf("    ->time run: %f\n", t3 - t1);
    }
		//spinlock_release(balanceLock); // Bad: could lockup when doing 4loopcalc
  }
  spinlock_release(balanceLock); 
  return NULL;
}


int main(int argc, char *argv[])
{
  // Get cmd line args
  if (argc != 3) 
  {
      fprintf(stderr, "usage: main-first <loopcount> <numthreads>\n");
      exit(1);
  }
  max = atoi(argv[1]);
  num = atoi(argv[2]);

  // initialization of vars
  balanceLock = malloc(sizeof(spinlock_t));
  spinlock_release(balanceLock);           //make sure it is 0 to begin

  	// Basic test - Create a lock and initialize it
  	spinlock_t *lock1 = malloc(sizeof(spinlock_t));
  	spinlock_release(lock1);
  	assert(lock1    ->flag == 0);
	
  	// Basic test - Lock it
  	spinlock_acquire(lock1);
  	assert(lock1    ->flag == 1);
		
  	// Multi-thread test - locking/unlocking
  	pthread_t p[500];
  	assert(num < 500);   //+500 threads => system starts to malfunction
  
  	// Creates a number of threads used to test the lock
  	int i;
  	t1 = GetTime();
  	// DEBUG - does lock actually work?
  	// spinlock_acquire(balanceLock);
  	for (i = 0; i < num; i++)
	  	Pthread_create(&p[i], NULL, mythread, NULL); 

  	// join waits for the threads to finish
  	for (i = 0; i < num; i++)
	  	Pthread_join(p[i], NULL); 

	// Record the runtime of the total execution
  	double t2 = GetTime();
  	printf("threads: %d time: %f\n", num, t2 - t1);
  	return 0;
}

