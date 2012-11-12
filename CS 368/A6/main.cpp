/*
 * CS 368 - Assignment 5
 * Christopher Wilcox (cwilcox AT wisc DOT edu)
 *
 * Functions ADDED to this program:
 * File:
 * 		Process.cpp
 *      	-Added a destructor
 *      		currently the destructor is empty as the 
 *      		compiler should handle the two integers
 *      		that make up the Process object
 *      Process.h
 *          - added a prototype for ~Process()
 *      Queue.cpp
 *          -Added a destructor
 *          	This destructor deletes all nodes in the process
 *          	as well as the process pointed to in the node
 *       Queue.h
 *          - added a prototype for ~Queue()
 * Functions MODIFIED in this program:
 * File:
 *       main.cpp
 *          - main()
 * 				modified to prevent memory leaks.  There were multiple
 *              instances of a new Process without a delete of the Process 
 *       Process.cpp
 *		 	- No Functions Modified	
 *       Queue.cpp
 *       	- dequeue()
 *          	modified this to delete the removed node as 
 *          	to avoid leaking memory
 *
 */


/* main.cpp
 * Program to test the Queue and Process classes.
 */
#include <iostream>
#include "Queue.h"		// which itself includes "Process.h"

using namespace std;

int
main ()
{
  Process *procptr;
  Queue q1;


  // make some processes and enqueue them
  q1.enqueue (new Process (1));
  cout << "after 1st enqueue:" << endl;
  q1.print ();

  q1.enqueue (new Process (2));
  cout << "after 2st enqueue:" << endl;
  q1.print ();

  q1.enqueue (new Process (3));
 
  cout << "after 3rd enqueue:" << endl;
  q1.print ();

  // now dequeue and print, then enqueue and print
  procptr = q1.dequeue ();
  if (procptr == NULL)
    {
      cout << "unexpected NULL pointer after dequeue" << endl;
    }
  else
    {
      cout << "ID dequeued:";
      procptr->print ();
      cout << endl;
	  delete procptr;
    }

  q1.enqueue (new Process (4));
  cout << "after enqueue of value 4:" << endl;
  q1.print ();
  // now there are 3 items in the queue

  procptr = q1.dequeue ();
  if (procptr == NULL)
    {
      cout << "unexpected NULL pointer after dequeue" << endl;
    }
  else
    {
      cout << "ID dequeued:";
      procptr->print ();
      cout << endl;
      delete procptr;
	}
  // now there are 2 items in the queue
  procptr = q1.dequeue ();
  if (procptr == NULL)
    {
      cout << "unexpected NULL pointer after dequeue" << endl;
    }
  else
    {
      cout << "ID dequeued:";
      procptr->print ();
      cout << endl;
	  delete procptr;
    }
  // now there are 1 items in the queue
  procptr = q1.dequeue ();
  if (procptr == NULL)
    {
      cout << "unexpected NULL pointer after dequeue" << endl;
    }
  else
    {
      cout << "ID dequeued:";
      procptr->print ();
      cout << endl;
	  delete procptr;
    }
  // now there are 0 items in the queue
  procptr = q1.dequeue ();
  if (procptr == NULL)
    {
      cout << "expected NULL pointer due to empty queue" << endl;
    }
  else
    {
      cout << "ID dequeued:";
      procptr->print ();
      cout << endl;
	  delete procptr;
    }

  procptr = new Process (5);
  procptr->addCPUTime (12);	// mild test of Process field modification
  q1.enqueue (procptr);
  cout << "checking that can add again to the empty queue" << endl;
  cout << "after enqueue of value 5:" << endl;
  q1.print ();

  q1.enqueue (new Process (6));
  cout << "after enqueue of value 6:" << endl;
  q1.print ();

  procptr = q1.dequeue ();
  if (procptr == NULL)
    {
      cout << "unexpected NULL pointer after dequeue" << endl;
    }
  else
    {
      cout << "ID dequeued:";
      procptr->print ();
      cout << endl;
	  delete procptr;
    }



cout << "########################START TEST OF COPY CONTRUCTOR ##############################"<< endl;
	//SETUP q2 with 4 items and print it
	Queue q2;
	
	q2.enqueue(new Process (1));
	q2.enqueue(new Process (2));
	q2.enqueue(new Process (3));
	q2.enqueue(new Process (4));

	cout << "q2 Queue" << endl;
	q2.print();

	//make a copy of q2 to q3
	Queue q3 = q2;

	// print a copy of both q2 and q3
	cout << "A COPY OF q2 is now at q3" << endl;
	cout << "q2" << endl;
	q2.print();
	cout << "q3" << endl;
	q3.print();

	// dequeue all but one from q2
  procptr = q2.dequeue ();
  if (procptr == NULL)
    {
      cout << "unexpected NULL pointer after dequeue" << endl;
    }
  else
    {
		delete procptr;
    }
  procptr = q2.dequeue ();
  if (procptr == NULL)
    {
      cout << "unexpected NULL pointer after dequeue" << endl;
    }
  else
    {
		delete procptr;
    }
  procptr = q2.dequeue ();
  if (procptr == NULL)
    {
      cout << "unexpected NULL pointer after dequeue" << endl;
    }
  else
    {
		delete procptr;
    }
  // print both q2 and q3
	cout << "After dequeuing from q2 3 times, this is what is in q2 and q3" << endl;
	cout << "q2" << endl;
	q2.print();
	cout << "q3" << endl;
	q3.print();
	
	// print both q2 and q3
	q2.enqueue(new Process(5));
	q2.enqueue(new Process(6));
	q3.enqueue(new Process(7));
	q3.enqueue(new Process(8));
	
	cout << "After enqueueing (5,6) to q2 and (7,8) to q3,  this is what is in q2 and q3" << endl;
	cout << "q2" << endl;
	q2.print();
	cout << "q3" << endl;
	q3.print();

	return 0;
}
