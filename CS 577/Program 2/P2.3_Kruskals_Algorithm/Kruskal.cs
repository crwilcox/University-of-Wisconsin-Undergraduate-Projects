//COMPLETION: 100%
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using P2_HelperClasses;

/*
 Part 3. (20 points) Implement Kruskal’s algorithm for building minimum spanning trees.
http://en.wikipedia.org/wiki/Kruskal%27s_algorithm
This is algorithm requires maintaining a sorted list of edges. A variety of data structures are
possible. Often a heap is used. But, for this part of the assignment you do not need to
implement a heap data structure. If you are programming in Java, you may use the heap structure
provided. You can also simply presort the edges and then maintain a list (or array) in sorted
order.
However, to ensure that you don’t introduce cycles you will need to implement a Union-Find data
structure. These are also called disjoint set data structures. Here is a link with more information:
http://en.wikipedia.org/wiki/Disjoint-set_data_structure
 */

namespace P2._3_Kruskals_Algorithm
{

    class Kruskal
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Kruskal Algorithm");
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
            runTest(graph, 39);

            Console.WriteLine("MST1");
            graph = new int[][]{
                new int[] {0,1,0,0,1},
                new int[] {1,0,1,0,0},
                new int[] {0,1,0,1,0},
                new int[] {0,0,1,0,1},
                new int[] {1,0,0,1,0}
            };
            runTest(graph, 4);

            Console.WriteLine("MST2");
            graph = new int[][] {
                new int[] {0,2,8,9,0,0},
                new int[] {2,0,6,0,0,0},
                new int[] {8,6,0,0,0,0},
                new int[] {9,0,0,0,3,4},
                new int[] {0,0,0,3,0,7},
                new int[] {0,0,0,4,7,0}
            };
            runTest(graph, 24);

            Console.WriteLine("MST3");
            graph = new int[][] {
                new int[] {0,4,4,4,4},
                new int[] {4,0,3,6,7},
                new int[] {4,3,0,4,1},
                new int[] {4,6,4,0,5},
                new int[] {4,7,1,5,0}
            };
            runTest(graph, 12);

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
            runTest(graph, 16);

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
            runTest(graph, 4456);
            Console.ReadLine();
        }

        static void runTest(int[][] graph, int expectedWeight)
        {
            List<edge> ret = kruskal(AdjacencyList.Build(graph));
            Console.WriteLine("RETURNS:");
            int totWeight = 0;
            foreach (edge e in ret)
            {
                totWeight += e.weight;
                Console.WriteLine("From: {0}  To: {1} Weight: {2}", e.from, e.to, e.weight);
            }
            Console.WriteLine("TotalWeight: {0}", totWeight);
            if (totWeight != expectedWeight) Console.WriteLine("FAIL FAIL FAIL... HERP DERP");
            else Console.WriteLine("PASSED");
            Console.WriteLine("- - - - - - - - - - - - - - - - - - - - - - - -");
        }

        static List<edge> kruskal(List<List<edge>> al)
        {
            /* ALGORITHM FROM BOOK 
             * Figure 5.4 from text pg 132
             * procedure kruskal (G,w)
             * Input:   A connected undirected graph G = (V, E) with edge weights w_e
             * Output:  A minimum spanning tree defined by the edges X
             * 
             * for all u in V:
             *  makeset (u)
             * X = {}
             * sort the edges E by weight
             * for all edges {u,v} in E, increasing order of weight:
             *  if find(u) != find(v)
             *      add edge {u, v} to X
             *      union(u,v)
             */

            
            //  for all u in V:
            //      makeset (u)
            UnionFind C = new UnionFind();
            for (int i = 0; i < al.Count; i++)
            {
                C.MakeSet(i);
            }

            // X = {}.  This will ultimately contain the edges of the MST
            List<edge> X = new List<edge>();

            //sort the edges E by weight  
            //Initialize a priority queue Q to contain all edges in G, using the weights as keys.
            P2_HelperClasses.PriorityQueue<int, edge> Q = new P2_HelperClasses.PriorityQueue<int, edge>();
            foreach (List<edge> edgeList in al)
            {
                foreach (edge e in edgeList)
                {
                    //if statement used to assure we dont add an edge twice (to,from may be flopped, but the same edge)
                    if (e.to > e.from) Q.Enqueue(e.weight, e);
                }
            }

            //while X has fewer than n-1 edges do
            while (X.Count < al.Count - 1)
            {
                // edge u,v is the minimum weighted route from u to v
                edge uv = Q.DequeueValue();

                //if find(u) != find(v)
                if (C.Find(uv.from) != C.Find(uv.to))
                {
                    //Add edge (v,u) to X
                    X.Add(uv);
                    //Merge C(v) and C(u) into one cluster, that is, union C(v) and C(u)
                    C.Union(uv.from, uv.to);
                }
            } //16    endwhile

            //17    return tree T
            return X;
        }
    }
}
