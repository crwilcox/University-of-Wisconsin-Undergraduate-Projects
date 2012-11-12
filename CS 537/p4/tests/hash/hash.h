#ifndef __hash_h__
#define __hash_h__
#include "spinlock.h"
#include "list.h"

typedef struct __hash_t {
    // definition of hash_t: Below are the items we need/may need
	unsigned int numbuckets;
	
	// list of All Buckets (a list of lists)
	list_t** buckets;
} hash_t;

void Hash_Init(hash_t *h, int numbuckets);
void Hash_Insert(hash_t *h, void *element, unsigned int key);
void Hash_Remove(hash_t *h, unsigned int key);
void* Hash_Lookup(hash_t *h, unsigned int key);

#endif // __hash_h__

