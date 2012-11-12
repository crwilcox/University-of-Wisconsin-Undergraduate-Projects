/*
 * Queue class
 *
 * This doubly linked Queue holds Process objects.
 */

#include <iostream>
#include "Queue.h"

using namespace std;

// Queue
// Constructs an empty queue.
Queue::Queue ()
{
  head = NULL;
  tail = NULL;
}

// copy constructor for Queue
Queue::Queue (const Queue & Old_queue)
{
	//make sure head and tail are null
	head = NULL;
	tail = NULL;
	//iterate through Old_queue, and replicate the data
	//to be used by our copy
	Node* node = Old_queue.head;
	while(node != NULL){
		enqueue(node->proc);
		node = node->next;
	}
}

// destructor for Queue
// deletes the processes left in the queue and the nodes they were held by
Queue::~Queue()
{
	Node* iter;
	for(iter=head; iter!=NULL; ){
		Node* temp = iter->next;
		delete iter->proc;
		delete iter;
		iter = temp;
	}

}


// enqueue
// Add the given Process object pointed to by procptr to the
// tail of the queue.
void
Queue::enqueue (Process * procptr)
{
	Node* myNode = new Node();
	myNode->proc = procptr;
	myNode->next = NULL; //The Tail shoul have no next item
	myNode->prev = tail;//the old tail is our previous
	if(tail != NULL) tail->next = myNode;//the old tails next is myNode
	tail = myNode;//move tail to myNode
	if(head == NULL) head = myNode;	
}


// Dequeues the head of the queue, and returns a pointer to 
// the Process object dequeued.  If the queue is empty,
// then there is nothing to dequeue, so the pointer will be null.
Process *
Queue::dequeue ()
{
	Node* myNode = head;
	if (head == NULL) return NULL;//nothing to dequeue
 	Process* returnPointer = myNode->proc;
	head = myNode->next;//Move head to next item
	if(head != NULL) head->prev = NULL;//removes the old head as the prev item
	
	delete myNode; 
	
	return returnPointer;
}

// print
// Prints out the contents of the queue, from head to tail.
void
Queue::print () const 
{
  Node *nodeptr;

  nodeptr = head;
  while (nodeptr != NULL)
    {
      nodeptr->proc->print ();
      nodeptr = nodeptr->next;
      cout << endl;
    }
}
