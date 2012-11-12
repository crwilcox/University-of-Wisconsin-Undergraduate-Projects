#ifndef __list_h__
#define __list_h__


typedef struct __list_t {
  // define you own here
} list_t;

void List_Init(list_t *l);
void List_Insert(list_t *l, void *element, unsigned int key);
void List_Remove(list_t *l, unsigned int key);
void* List_Lookup(list_t *l, unsigned int key);

#endif // __list_h__

