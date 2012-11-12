///////////////////////////////////////////////////////////////////////////////
// Title:            Intro to Threads
// Files:            spinlock.h
// Semester:         Spring 2010
//
// Authors:          Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
//			         Christopher Wilcox - cwilcox@wisc.edu
// Course:           UW-Madison CS 537 - Introduction to Operating Systems
// Instructor:       Prof. Remzi Arpaci-Dusseau
//
//////////////////////////// 80 columns wide //////////////////////////////////
#ifndef __spinlock_t__
#define __spinlock_t__
// Structure used
typedef struct spinlock_t
{
	volatile unsigned int flag;
} spinlock_t;


//static inline uint xchg(volatile unsigned int *addr, unsigned int newval);
inline uint xchg(volatile unsigned int *addr, unsigned int newval);
void spinlock_acquire(spinlock_t *lock);
void spinlock_release(spinlock_t *lock);

#endif //__spinlock_t__
