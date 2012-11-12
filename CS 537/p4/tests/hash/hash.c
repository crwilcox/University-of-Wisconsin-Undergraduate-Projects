#include <stdio.h>
#include <stdlib.h>
#include "list.h"
#include "hash.h"

/*
 * Helper method used to get the correct bucket for a given key and hast table. 
 * 
 * *h - Pointer to the hash table to initialize 
 * key - Unique ID for element
 */
list_t* getBucket(hash_t *h, int key)
{
	int currBucketNum = key % (h->numbuckets);
	list_t* currBucketList = (list_t*) h->buckets[currBucketNum];
	return currBucketList;
}


/*
 * Initialize a hash table with a specified number of buckets,
 * and initialize each bucket to have a list.
 * 
 * *h - Pointer to the hash table to initialize
 * numbuckets - Number of buckets for current hast table
 */
void Hash_Init(hash_t *h, int numbuckets)
{
	h->numbuckets = numbuckets;
	
	//malloc the bucketlist as to make room for numbuckets worth of listItem objects
	h->buckets = malloc(numbuckets * sizeof(list_t*));
	
	//initialize each listItem as it should be initialized to start
	int i;
	for(i=0; i < numbuckets; i++)
	{
		list_t* listi = malloc(sizeof(list_t));
		List_Init(listi);
		h->buckets[i] = listi;
	}
}


/*
 * Inserts an element into the hash by figuring out which bucket should contain 
 * the element and add in the element. 
 * 
 * *h - Pointer to the hash table to insert to
 * *element - Pointer to the void element to add
 * key - Unique ID for element
 */
void Hash_Insert(hash_t *h, void *element, unsigned int key)
{
	list_t *currBucketList =  getBucket(h, key);
	List_Insert(currBucketList, element, key);
}


/*
 * Find and remove the object specified by a given key from the bucket
 * 
 * *h - Pointer to the hash table to remove element from
 * key - Unique ID for element to remove
 */
void Hash_Remove(hash_t *h, unsigned int key)
{
	list_t *currBucketList =  getBucket(h, key);
	List_Remove(currBucketList, key);
}



/*
 * Lookup and return an element in the hash table, if it exists.
 *  
 * *h - Pointer to the hash table to look through
 * key - Unique ID for element to find
 */
void* Hash_Lookup(hash_t *h, unsigned int key)
{
	list_t *currBucketList =  getBucket(h, key);
	return (List_Lookup(currBucketList, key));
}

