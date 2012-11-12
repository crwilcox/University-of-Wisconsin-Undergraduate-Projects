///////////////////////////////////////////////////////////////////////////////
// Title:           Intro to Threads
// Files:           spinlock.c
// Semester:		Spring 2010
//
// Authors:         Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
//					Christopher Wilcox - cwilcox@wisc.edu
// Course:          UW-Madison CS 537 - Introduction to Operating Systems
// Instructor:      Prof. Remzi Arpaci-Dusseau
//
//////////////////////////// 80 columns wide //////////////////////////////////
#include <stdlib.h>
#include "spinlock.h"

/*
 * Atomic instruction used to implement the locking fucntion. If locked,
 * creates a spin/wait loop which will return once the lock is unlocked
 * by the thread using it.
 *
 * *lock - Pointer to the lock that will be locked.
 */
void spinlock_acquire(spinlock_t *lock)
{
	while(xchg(&(lock->flag), 1) == 1)
		; //spin wait (do nothing)
}

/*
 * Releases the lock via atomic instruction. If lock is already unlocked, does
 * nothing.
 *
 * *lock: Pointer to the lock which will be freed (released/unlocked)
 */
void spinlock_release(spinlock_t *lock)
{
  xchg(&(lock->flag), 0);
}


/*
 * This code will take in an address of a lock, and set it to the specified
 * value.  It will then return the old (before you wrote) value.
 *
 * Used to atomically switch values between registers and memory - key for 
 * implementing locks.
 */
//static inline uint
inline uint
xchg(volatile unsigned int *addr, unsigned int newval)
{
	uint result;
	asm volatile("lock; xchgl %0, %1" : "+m" (*addr), "=a" (result) : "1" (newval) : "cc");
	return result;
}


