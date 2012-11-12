//COMPLETION: 0%
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

/*
Part 2. (15 points) Implement a Fibonacci heap data structure. This data structure has better
amortized complexity than the binomial heap. Here is a link to a nice Java applet for animating
the operations on a Fibonacci heap and the pseudo-code for its implementation.
 */
namespace P2._2_Fibonacci_Heap
{
    class FibonacciHeap{
    static void Main(string[] args)
        {}
    }
    /// <summary>
    /// Priority queue based on binary heap,
    /// Elements with minimum priority dequeued first
    /// </summary>
    /// <typeparam name="TPriority">Type of priorities</typeparam>
    /// <typeparam name="TValue">Type of values</typeparam>
    /*
    public class FibonacciHeap<TPriority, TValue> : ICollection<KeyValuePair<TPriority, TValue>>
    {
        //http://www.cs.princeton.edu/~wayne/cs423/lectures/fibonacci-4up.pdf
        //http://www.cse.yorku.ca/~aaw/Jason/FibonacciHeapAlgorithm.html
        //http://www.cse.yorku.ca/~aaw/Jason/FibonacciHeapAnimation.html
        
        //make_heap     //O(1) a guess
        //insert(data,key) 	            Adds data to the queue, tagged with key                                             O(1)
        //findMin() -> key,data 	    Returns key,data corresponding to min-value key                                     O(1)
        //deleteMin()                   Deletes data corresponding to min-value key                                         O(log n) (amortized time)
        //decreaseKey(node) 	        Decreases the key of a node, given a pointer to the node being modified             O(1) (amortized time)    
        //delete(node) 	                Deletes data corresponding to given key, given a pointer to the node being deleted  O(log n)  
        //merge(heap1,heap2) -> heap3 	Merges two heaps into a third                                                       O(1)

        int myInt = 6;
                    
    
    }
     */
}
