//COMPLETION: 100%
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using P2_HelperClasses;
/*
Minimum Spanning Trees
The assignment deals with implementing algorithms for finding minimum spanning trees. It is multi
part and you can do those parts individually without doing the entire assignment. But, if you do all
the parts, it will count as 2 programming assignments and can give you as many (or more) points as
an exam.
Due date: If you want to do this assignment, you should have at least one part completed by
April 12th. You may have until April 28th to complete additional parts and until the end of the
semester to complete the extra credit.
To begin, here is a link to an excellent set of slides produced by Kevin Wayne at Princeton
University.
http://www.cs.princeton.edu/~wayne/cs423/lectures/mst-4up.pdf
Throughout this assignment description, I have provided links to places where you can find
additional information. In addition to these sources our textbook is a good source.


 Part 1. (20 points) Implement Prim’s algorithm for building minimum spanning trees.
http://en.wikipedia.org/wiki/Prim%27s_algorithm
Recall, that in this algorithm you will be adding the shortest edge that adds a new node to the tree.
Your algorithm should begin with the tree that contains 2 nodes connected by the shortest edge in
the graph.
The algorithm is heap based. But, you do not need to implement a heap – if you are
programming in Java, you can simply use the heap structure provided.
 */

namespace P2._1_Prims_Algorithm
{

    class Prim
    {

        static void runTest(int[][] graph, int startNode, int expectedWeight)
        {
            int ret = primPriorityQueue(graph, startNode);//start at D
            if (ret != expectedWeight) Console.WriteLine("FAIL FAIL FAIL... HERP DERP");
            else Console.WriteLine("PASSED");
            Console.WriteLine("- - - - - - - - - - - - - - - - - - - - - - - -");
        }
        static void Main(string[] args)
        {
            Console.WriteLine("Prim Algorithm");
            Console.WriteLine("--------------");
            Console.WriteLine("MyTest");
            int[][] graph = new int[][] {
                //         A  B  C  D  E  F  G
                new int[] {0, 7, 0, 5, 0, 0, 0},    //A EDGES
                new int[] {7, 0, 8, 9, 7, 0, 0},    //B
                new int[] {0, 8, 0, 0, 5, 0, 0},    //C
                new int[] {5, 9, 0, 0,15, 6, 0},    //D
                new int[] {0, 7, 5,15, 0, 8, 9},    //E
                new int[] {0, 0, 0, 6, 8, 0,11},    //F
                new int[] {0, 0, 0, 0, 9,11, 0}     //G
            };
            runTest(graph, 3, 39);

            Console.WriteLine("MST1");
            graph = new int[][]{
                new int[] {0,1,0,0,1},
                new int[] {1,0,1,0,0},
                new int[] {0,1,0,1,0},
                new int[] {0,0,1,0,1},
                new int[] {1,0,0,1,0}
            };
            runTest(graph, 2, 4);

            Console.WriteLine("MST2");
            graph = new int[][] {
                new int[] {0,2,8,9,0,0},
                new int[] {2,0,6,0,0,0},
                new int[] {8,6,0,0,0,0},
                new int[] {9,0,0,0,3,4},
                new int[] {0,0,0,3,0,7},
                new int[] {0,0,0,4,7,0}
            };
            runTest(graph, 3, 24);

            Console.WriteLine("MST3");
            graph = new int[][] {
                new int[] {0,4,4,4,4},
                new int[] {4,0,3,6,7},
                new int[] {4,3,0,4,1},
                new int[] {4,6,4,0,5},
                new int[] {4,7,1,5,0}
            };
            runTest(graph, 3, 12);

            Console.WriteLine("MST4");
            graph = new int[][]{
                new int[]  {0,2,4,1,0,0,0},
                new int[]  {2,0,0,3,9,0,0},
                new int[]  {4,0,0,2,0,5,0},
                new int[]  {1,3,2,0,7,8,4},
                new int[]  {0,9,0,7,0,0,6},
                new int[]  {0,0,5,8,0,0,1},
                new int[]  {0,0,0,4,6,1,0}
            };
            runTest(graph, 4, 16);

            Console.WriteLine("MST5");
            graph = new int[][]{
                new int[]  {0,867,849,740,621,0,802,1846,0},
                new int[]  {867,0,0,187,0,1258,0,2704,0},
                new int[]  {849,0,0,144,0,0,0,0,0},
                new int[]  {740,187,144,0,184,1090,1391,0,0},
                new int[]  {621,0,0,184,0,946,0,0,0},
                new int[]  {0,1258,0,1090,946,0,1121,0,2342},
                new int[]  {802,0,0,1391,0,1121,0,1464,1235},
                new int[]  {1846,2704,0,0,0,0,1464,0,337},
                new int[]  {0,0,0,0,0,2342,1235,337,0}
            };
            runTest(graph, 5, 4456);

            Console.ReadLine();
        }



        //returns the total weight...
        static int primPriorityQueue(int[][] w, int startNode)
        {
            /* Algorithm to use
             * make adjacency list
             * 
             * add a to pqueue (
             * while(all nodes have not been found)
             *      pop off shortest
             *          if(node is not found yet) add its children; // this is a check for duplicating nodes...
             *      add node and edge cost, etc
             */

            //make adjacency list
            List<List<edge>> adjList = AdjacencyList.Build(w);

            P2_HelperClasses.PriorityQueue<int, node> pqueue = new P2_HelperClasses.PriorityQueue<int, node>();
            List<int> foundNodes = new List<int>();
            List<edge> foundEdges = new List<edge>();
            //add a to pqueue
            pqueue.Enqueue(0, new node(0, -1, startNode));//weight is 0, added by -1(noone), startnode is added.

            //while(all nodes have not been found)
            while (foundNodes.Count != adjList.Count)
            {
                //pop off shortest
                node poppedNode = pqueue.DequeueValue();

                //if(poppedNode is not found yet) 
                if (!foundNodes.Contains(poppedNode.nodeNum))
                {
                    //add its children;
                    foreach (edge child in adjList[poppedNode.nodeNum])
                        pqueue.Enqueue(child.weight, new node(child.weight, poppedNode.nodeNum, child.to));//we build the edge list so to will be the to

                    //add node to found list
                    foundNodes.Add(poppedNode.nodeNum);
                    //add edge to found (if it is an edge and not just the start node)
                    if (poppedNode.addedBy >= 0) foundEdges.Add(new edge(poppedNode.addedBy, poppedNode.nodeNum, poppedNode.weight));
                }
            }

            //Output: the order in which the nodes were found and the edges as well as the total weight
            Console.WriteLine("Start Node: " + startNode);
            Console.Write("foundNodes: ");
            foreach (int v in foundNodes) Console.Write(v + " ");
            Console.WriteLine();

            int addedWeight = 0;
            Console.WriteLine("foundEdges: ");
            foreach (edge e in foundEdges)
            {
                addedWeight += e.weight;
                Console.WriteLine("from: " + e.from + "  to: " + e.to + "  weight: " + e.weight);
            }

            Console.WriteLine("Added Weights:" + addedWeight);
            return addedWeight;
        }

        //returns the total weight...
        static int primAdjecencyMatrix(int[][] w, int startNode)
        {
            //Input: A non-empty connected weighted graph with vertices V and edges E (the weights can be negative).
            //Initialize: Vnew = {x}, where x is an arbitrary node (starting point) from V, Enew = {}
            //Repeat until Vnew = V:
            //    Choose an edge (u, v) with minimal weight such that u is in Vnew and v is not (if there are multiple edges with the same weight, any of them may be picked)
            //    Add v to Vnew, and (u, v) to Enew
            //Output: Vnew and Enew describe a minimal spanning tree


            //Input: A non-empty connected weighted graph with vertices V and edges E (the weights can be negative).
            //Initialize: Vnew = {x}, where x is an arbitrary node (starting point) from V, Enew = {}
            List<int> vnew = new List<int>();
            List<edge> enew = new List<edge>();
            vnew.Add(startNode);

            //Repeat until Vnew = V:
            while (vnew.Count < w.Length)
            {
                edge bestEdge = new edge();
                bestEdge.weight = Int32.MaxValue;
                //    Choose an edge (u, v) with minimal weight such that u is in Vnew and v is not (if there are multiple edges with the same weight, any of them may be picked)
                foreach (int u in vnew)
                {
                    for (int v = 0; v < w.Length; v++)
                    {
                        if (w[u][v] != 0 && w[u][v] < bestEdge.weight && !vnew.Contains(v))
                        {
                            bestEdge.from = u;
                            bestEdge.to = v;
                            bestEdge.weight = w[u][v];
                        }
                    }

                }

                //    Add v to Vnew, and (u, v) to Enew
                vnew.Add(bestEdge.to);
                enew.Add(bestEdge);
            }

            //Output: Vnew and Enew describe a minimal spanning tree
            Console.Write("Vnew: ");
            foreach (int v in vnew) Console.Write(v + " ");
            Console.WriteLine();

            int addedWeight = 0;
            Console.WriteLine("Enew: ");
            foreach (edge e in enew)
            {
                addedWeight += e.weight;
                Console.WriteLine("from: " + e.from + "  to: " + e.to + "  weight: " + e.weight);
            }

            Console.WriteLine("Added Weights:" + addedWeight);
            return addedWeight;
        }
    }
}