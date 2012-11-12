///////////////////////////////////////////////////////////////////////////////
// Title:            Intro to Threads
// Files:            counter.c
// Semester:         Spring 2010
//
// Authors:          Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
//                   Christopher Wilcox - cwilcox@wisc.edu
// Course:           UW-Madison CS 537 - Introduction to Operating Systems
// Instructor:       Prof. Remzi Arpaci-Dusseau
//
//////////////////////////// 80 columns wide //////////////////////////////////
#include <stdio.h>
#include <stdlib.h>
#include "counter.h"
#include "spinlock.h"

/*
 * Initializes a thread-safe counter.
 *
 * *c:    Pointer to the counter to be initialized
 * value: initial balance of the counter
 */
void Counter_Init(counter_t *c, int value)
{
  spinlock_acquire((spinlock_t*)&c->balanceLock);
  c->balance = value;
  c->balanceLock = malloc(sizeof(spinlock_t));
  spinlock_release((spinlock_t*)&c->balanceLock);
}

/*
 * Increments a pointer in a thread-safe manner
 *
 * *c: Pointer to the counter to be incremented by 1
 */
void Counter_Increment(counter_t *c)
{
  // Lockdown the current counter
  spinlock_acquire((spinlock_t*)&c->balanceLock);
  c->balance = c->balance + 1;
  // Unlock it post-increment
  spinlock_release((spinlock_t*)&c->balanceLock);
}

/*
 * Decrements a pointer in a thread-safe manner
 *
 * *c: Pointer ot the counter to be decremented by 1
 */
void Counter_Decrement(counter_t *c)
{
  // Lockdown the current counter
  spinlock_acquire((spinlock_t*)&c->balanceLock);
  c->balance = c->balance - 1;
  // Unlock it post-decrement
  spinlock_release((spinlock_t*)&c->balanceLock);
}

/*
 * Returns the current value of the counter.
 *
 * *c: Pointer to the Counter of which to return the current coutner value
 * TODO: When we get a value, since we are reading, should we invoke lck/unlck?
 */
int Counter_GetValue(counter_t *c)
{
  return (c->balance);
}
