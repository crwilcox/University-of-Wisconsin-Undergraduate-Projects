#include <stdio.h>
#include <stdlib.h>
#include "list.h"

#define TRUE 1
#define FALSE 0


/*
 * List_Init: Initializes the list by setting it to being empty, and saying it is not locked
 */
void 
List_Init(list_t *l){
	spinlock_acquire((spinlock_t*)&l->lock);
	l->head = NULL;
	spinlock_release((spinlock_t*)&l->lock);
}


/*
 * List_Insert: Inserts an item at the head of the list
 */
void 
List_Insert(list_t *l, void *element, unsigned int key){
	if(l!=NULL){
		//free space for the listItem
		listItem* item = malloc(sizeof(listItem));
		//fill in the item
		item->element = element;
		item->key = key;

		//HERE IS THE CRITICAL PIECE
	spinlock_acquire((spinlock_t*)&l->lock);
		//set the prev, next of the added node so it is the head
		//and so its next item is the previous head
		item->prev = NULL;
		item->next = l->head;
		//if this isnt the first item, make the old items previous the new item
		if(l->head != NULL) l->head->prev = item; 		
		//make the head of the list the new item
		l->head = item;

		spinlock_release((spinlock_t*)&l->lock);
	}
}


/*
 * List_Remove: removes the item as specified by the key
 */
void 
List_Remove(list_t *l, unsigned int key){
	spinlock_acquire((spinlock_t*)&l->lock);
	
	if(l == NULL || l->head == NULL) return; //the list or the head was NULL, just exit this
	
	listItem *iter;
	for(iter = l->head; iter != NULL; iter = iter->next)
	{
		if(iter->key == key)
		{
			//spinlock_acquire((spinlock_t*)&l->lock);
			//now we can remove the found item
			//take this out of the loop
			
			if(iter->prev == NULL && iter->next != NULL) l->head = iter->next;//head is being removed, change head
			if(iter->next == NULL && iter->prev == NULL){ l->head = NULL;}//This was the last item, nullify head
			
			if(iter->next != NULL) { iter->next->prev = iter->prev; }
			if(iter->prev != NULL) { iter->prev->next = iter->next; }
			
			
			free(iter);

			spinlock_release((spinlock_t*)&l->lock);
			//return as we have removed the node
			return;
		}
	}
	
	spinlock_release((spinlock_t*)&l->lock);
	return;//found nothing to remove
}


/*
 * List_Lookup: finds the item specified by the key, and returns a void pointer to the item->element
 * Returns NULL if you are bad
 */
void*
List_Lookup(list_t *l, unsigned int key){
	if(l == NULL || l->head == NULL) return NULL; //the list or the head was NULL, Return Null
	
	spinlock_acquire((spinlock_t*)&l->lock);
	listItem *iter;
	for(iter = l->head; iter != NULL; iter = iter->next)
	{
		if(iter->key == key)
		{
			void* returnval = iter->element;
			spinlock_release((spinlock_t*)&l->lock);
			return returnval;
		}
	}

	spinlock_release((spinlock_t*)&l->lock);
	return NULL; // the requested key was not in the list, return NULL
}



void
List_Dump(list_t *l)
{
    /*
     * This is just a debugging routine for your own use. 
     * Have it print the list to the screen.
     */

    printf("LIST: \n");


    spinlock_acquire((spinlock_t*)&l->lock);
    listItem *iter;
    for(iter = l->head; iter != NULL; iter = iter->next)
    {   
        if(l == NULL){ printf("INVALID LIST"); break;}
        else{
            printf("Address:%-10p  IterPrev:%-10p  ", iter, iter->prev);
			printf("IterNext:%-10p | Element Addr:%-10p ",iter->next, iter->element);
            printf("Element Content:%-5d Element Key:%-5d\n", *(int*)(iter->element), iter->key);
        }
    }


    spinlock_release((spinlock_t*)&l->lock);
    printf("\n");
}



