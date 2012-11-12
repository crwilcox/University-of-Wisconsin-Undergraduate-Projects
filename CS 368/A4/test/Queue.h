#ifndef QUEUE_H
#define QUEUE_H

#include "Process.h"

/*
 * Queue class
 *
 * This doubly linked Queue holds Process objects.
 */
class Queue {

  public:
    
    Queue();
    // Constructs an empty queue.

    Queue(const Queue &);
    //Copy constructor

    void enqueue(Process *procptr);
    // Add the given Process object pointed to by procptr to the
    // tail of the queue.

    Process *dequeue();
    // Dequeues the head of the queue, and returns a pointer to
    // the Process object dequeued.  If the queue is empty, 
    // then there is nothing to dequeue, so the pointer will be null.

    void print() const;
    // Prints out the contents of the queue, from head to tail.
    // Note that this implementation presumes that by following the next
    // field, we iterate from the head of the queue towards the tail end.

  private:

    // Since Nodes will only be used within the Queue class,
    // they are private.
    struct Node {    
      Process *proc;
      Node *next;
      Node *prev;
    };

    Node *head; // pointer to node at the head of the queue
    Node *tail; // pointer to node at the tail of the queue
};

#endif
