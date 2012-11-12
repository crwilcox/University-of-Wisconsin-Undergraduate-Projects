#ifndef __list_h__
#define __list_h__
#include "spinlock.h"

typedef struct __list_t{
	spinlock_t *lock;
	struct __listItem *head;
}list_t;

//using double linked list
typedef struct __listItem{
	struct __listItem *next;
	struct __listItem *prev;
	void* element;
	unsigned int key;;
}listItem;


void List_Init(list_t *l);
void List_Insert(list_t *l, void *element, unsigned int key);
void List_Remove(list_t *l, unsigned int key);
void* List_Lookup(list_t *l, unsigned int key);
void List_Dump(list_t *l);

#endif // __list_h__

