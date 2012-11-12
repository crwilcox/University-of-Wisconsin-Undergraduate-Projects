///////////////////////////////////////////////////////////////////////////////
// Title:            Intro to Threads
// Files:            counter.h
// Semester:         Spring 2010
//
// Authors:          Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
//                   Christopher Wilcox - cwilcox@wisc.edu
// Course:           UW-Madison CS 537 - Introduction to Operating Systems
// Instructor:       Prof. Remzi Arpaci-Dusseau
//
//////////////////////////// 80 columns wide //////////////////////////////////

#ifndef __counter_h__
#define __counter_h__
#include "spinlock.h"

typedef struct __counter_t 
{
  // your definition of counter_t goes here
  volatile int balance;  // TODO: verify use of volatile
  spinlock_t *balanceLock;
} counter_t;

void Counter_Init(counter_t *c, int value);
int  Counter_GetValue(counter_t *c);
void Counter_Increment(counter_t *c);
void Counter_Decrement(counter_t *c);

#endif 

