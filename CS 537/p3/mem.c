#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#include "mem.h"
#define HEADER 8
#define MAGICNUMBER 0xCAB005E
#define	FREESPACEPATTERN 0xDEADBEEF
#define PADDINGPATTERN 0xABCDDCBA
#define	PADDINGSIZE 64
#define TRUE 1
#define FALSE 0

//using double linked list
typedef struct listItem{
	struct listItem *next;
	struct listItem *prev;
	int size;
}listItem;


/* PROTOTYPES */
void addNode(listItem *item);
void removeNode(listItem *item);
void fillMemory(int* pointer, int length, int pattern);
int checkMemory(int* pointer, int length, int pattern);


int m_error;

//pointer to head of data structure
struct listItem *freeList;
int firstMemInit = TRUE; //1 if the first time (true), false otherwise
int debugMode = FALSE; //0 (false) unless flagged...

int 
Mem_Init(int sizeOfRegion, int debug) 
{	
	if(!firstMemInit  || sizeOfRegion <= 0 || (debug!=0 && debug!=1)){
		//if this is not the first call to Mem_Init
		//or sizeOfRegion is invalid
		m_error = E_BAD_ARGS;		
		return -1;
	}
	if (debug) debugMode = TRUE;//set debug flag appropriately
	firstMemInit = FALSE;//Set to False as we have done the first Mem_Init
	int adjustedSizeOfRegion = sizeOfRegion + (sizeOfRegion % getpagesize());

	// open the /dev/zero device
	int fd = open("/dev/zero", O_RDWR);

	// sizeOfRegion (in bytes) needs to be evenly divisible by the page size
	void *ptr = mmap(NULL, adjustedSizeOfRegion, 
	PROT_READ | PROT_WRITE, MAP_PRIVATE, fd, 0);
	if (ptr == MAP_FAILED) {
		perror("mmap");
		return -1;
	}

	//setup the global variable freelist with the Head item
	listItem *head = ptr;
	head->size = adjustedSizeOfRegion;
	head->next = NULL;
	head->prev = NULL;
	freeList = head; 

	if(debugMode){
		fillMemory((int*)(ptr+sizeof(listItem)), adjustedSizeOfRegion-sizeof(listItem), FREESPACEPATTERN);
	}

	// close the device
	
	close(fd);
	
	return 0;
}

void *
Mem_Alloc(int size) 
{	
	if(size <= 0) return NULL;
	int allocSize = size + (size % 4) + HEADER;/* word aligning memory.  Add HEADER to store region size and magic number */
	if(debugMode) allocSize += PADDINGSIZE * 2; //add 128bytes for padding if in debugmode
	
	listItem *bestFit = freeList;

	listItem *iter;
	for(iter = freeList; iter != NULL; iter = iter->next)
	{
		//if is the smallest size that meets requirement record
		//if iter is smaller and meets the requirements or bestFit doesn't meet the requirements
		if ((iter->size >= allocSize && iter->size <= bestFit->size)|| bestFit->size < allocSize)
		{
			bestFit = iter;
		}
		
	if(debugMode && !checkMemory((int*)iter+3, iter->size-sizeof(listItem), FREESPACEPATTERN)){
			m_error = E_CORRUPT_FREESPACE;
			return NULL;
		}
	}
	
	if(bestFit->size < allocSize){
		//After going through the whole list, there was no space big enough
		m_error = E_NO_SPACE;
		return NULL;
	}

	/*
	* now take the item chosen, and remove it from the list if they use all the space it has.
	* else, move the node to the beginning of the remaining free space
	*/
	if(bestFit->size == allocSize){	//WAS THE SAME AS NEEDED SPACE, REMOVE THIS NODE
		removeNode(bestFit);
	}
	else{
		//Had more space than needed, should move node out of new allocated space to 
		//beginning of the remaining free space		
		listItem *newItem = ((void*)bestFit) +  allocSize;//place the new list item at beginning of freed space
		newItem->size = bestFit->size - allocSize;
		removeNode(bestFit);
		addNode(newItem);
	}

	//FILL MEM AND GIVE USER SPACE
	if(debugMode){
		//setup areaSize as well as the protection bit(these need to be shifted down)
		int *areaSize = (int*)(bestFit) ;
		*areaSize = allocSize;
		int *protectionBit = ((int*)(bestFit))+ 1;
		*protectionBit = MAGICNUMBER;
		
		
		//fill first PADDINGSIZE bytes with PADDINGPATTERN
		fillMemory(((int*)bestFit)+2, PADDINGSIZE, PADDINGPATTERN);
		//fill last  PADDINGSIZE bytes with PADDINGPATTERN
		fillMemory((int*)bestFit + ((allocSize-PADDINGSIZE)/4), PADDINGSIZE, PADDINGPATTERN);

		return ((void*) bestFit) + HEADER + PADDINGSIZE;
	}
	else{
		//make first 4bytes of bestFit contain the size of the bestFit Area
		int *areaSize = (int*)(bestFit);
		*areaSize = allocSize;
		int *protectionBit = ((int*)(bestFit))+ 1;
		*protectionBit = MAGICNUMBER;
	
		//return the user the pointer to the to the space
		return ((void*) bestFit) + HEADER;
	}
}

int 
Mem_Free(void *ptr) 
{
	if(ptr == NULL){//DO NOTHING, INVALID OPERAND. OP FAILS
		m_error = E_BAD_POINTER;
		return -1;
	}
	int *startOfRegion;
  	if(debugMode){
	   	startOfRegion = (int*)(ptr-HEADER-PADDINGSIZE);
	}
	else{ startOfRegion = (int*)(ptr-HEADER); }

	int sizeToFree = *startOfRegion;//lets us know how much space to free
	int *protectionBit = (int*)(startOfRegion + 1);

	if ( *protectionBit != MAGICNUMBER ) {
		m_error = E_BAD_POINTER; 
		return -1;
	}
	
	listItem *newNode = (listItem*)startOfRegion;
	
	if(debugMode){
		if(!checkMemory(startOfRegion+2,PADDINGSIZE,PADDINGPATTERN)) {//FRONT PADDING MESSED UP
			m_error = E_PADDING_OVERWRITTEN;
			return -1;
		}	
		int* startOfEndPad = (int*)(((void*)startOfRegion)+sizeToFree-PADDINGSIZE);
		if(!checkMemory(startOfEndPad, PADDINGSIZE, PADDINGPATTERN)){
			m_error = E_PADDING_OVERWRITTEN;
			return -1;
		}
	}

	newNode->size = sizeToFree;

	listItem *iter;
	for(iter = freeList; iter != NULL; iter = iter->next)
	{
		if(iter->size + (void*)iter == (void*)newNode){//the area before is free
			//change beginning of region, update newNode->size, remove the old node
			int tempSize = newNode->size + iter->size;
			newNode = iter;
			newNode->size = tempSize;
			removeNode(iter);	
		}
		else if((void*)newNode + newNode->size == (void*)iter){//area after is free
			//change size of region, remove old node
			newNode->size += iter->size;
			removeNode(iter);	
		}
	}
	if(debugMode){
		fillMemory((int*)newNode+3, (newNode->size)-sizeof(listItem), FREESPACEPATTERN);
	}
	addNode(newNode);
	return 0;
}

void 
Mem_Dump()
{
	/*
	* This is just a debugging routine for your own use. 
	* Have it print the regions of free memory to the screen.
	*/

	printf("dump:\n");
	
	listItem *iter;
	for(iter = freeList; iter != NULL; iter = iter->next)
	{
		if(freeList == NULL){ printf("THERE ARE NO ITEMS IN THE FREE LIST"); break;}
		else{
			printf("Address:%-10p   Size:%-10d    IterPrev:%-10p    IterNext:%-10p\n", iter,  iter->size, iter->prev, iter->next);
			
		}
	}
	printf("\n");
}

/* 
* HELPER METHODS
*/

void
addNode(listItem *item){
	if(freeList != NULL){
		freeList->prev = item; //make the old heads previous the new node
	}
	item->next = freeList;//the head of the old freeList is our child
	item->prev = NULL;
	freeList = item; // set this to the head item
}

void
removeNode(listItem *item){
	//if this item is beginning, move the free list pointer
	//take this out of the loop
	if(item->next == NULL && item->prev == NULL){ freeList = NULL;}//THIS WAS THE LAST AND ONLY FREE NODE
	if(item == freeList){freeList = item->next; }
	
	if(item->next != NULL) { item->next->prev = item->prev; }
	if(item->prev != NULL) { item->prev->next = item->next; }
}


void
fillMemory(int* pointer, int length, int pattern){
	int count = 0;
	int modLength = length/4;
	for(;count < modLength; count++){
		*(pointer+count) = pattern;
	}
}

int
checkMemory(int* pointer, int length, int pattern){
	int count = 0;
	int modLength = length/4;
	for(;count < modLength; count++){
		if(*(pointer+count) != pattern) return FALSE;//if not the same return false
	}
	return TRUE; //no issues? return true
}


