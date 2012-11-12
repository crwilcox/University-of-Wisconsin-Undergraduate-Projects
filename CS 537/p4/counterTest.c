///////////////////////////////////////////////////////////////////////////////
// Title:            Intro to Threads
// Files:            counterTest.c
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
#include "counter.h"

// global ints
int max;
int num;
counter_t* myCounter;
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
  for (i = 0; i < max/num; i++) 
  {
	Counter_Increment(myCounter);
    if(i == max - 1)
    {
      //printf("    ->counterValue: %i, i: %i\n", Counter_GetValue(myCounter), i);
      //double t3 = GetTime();
      //printf("    ->time run: %f\n", t3 - t1);
    }
  }
  //printf("    ->ending counterValue: %i \n", Counter_GetValue(myCounter));
  return NULL;
}


int main(int argc, char *argv[])
{
  // Get cmd line args
  if (argc != 3) 
  {
      fprintf(stderr, "usage: counterTest <loopcount> <numthreads>\n");
      exit(1);
  }
  max = atoi(argv[1]);
  num = atoi(argv[2]);

  // initialization of vars
  pthread_t p[500];
  assert(num < 500);   //+500 threads => system starts to malfunction
  
  myCounter = malloc(sizeof(counter_t));
  Counter_Init(myCounter, 0); 
  
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
  printf("threads: %d time: %f\n", num, t2 - t1);
  
  return 0;
}

