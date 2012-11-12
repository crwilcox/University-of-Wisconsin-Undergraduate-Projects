//COMPLETION: 100%
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using P2_HelperClasses;

/*
 Part 4. (20 points) Implement Boruvka’s algorithm for building minimum spanning trees. This
was the first algorithm given for the problem. It dates to 1926 and was developed to help design
an efficient electricity network for Moravia. There is a modern description of the algorithm
included on Kevin Wayne’s slides (link above).
 */

namespace P2._4_Boruvkas_Algorithm
{
    class Boruvka
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Boruvka Algorithm");
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
            List<edge> ret = boruvka(AdjacencyList.Build(graph));
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

        static List<edge> boruvka(List<List<edge>> G)
        {
            /*
            make a list L of n trees, each a single vertex
            while (L has more than one tree)
                for each T in L, find the smallest edge connecting T to G-T
                add all those edges to the MST
                (causing pairs of trees in L to merge)
             */

            List<edge> mst = new List<edge>();

            //make a list L of n trees, each a single vertex
            List<List<int>> L = new List<List<int>>();
            for (int i = 0; i < G.Count; i++) { List<int> t = new List<int>(); t.Add(i); L.Add(t); }
    
            //while (L has more than one tree)
            while (L.Count > 1)
            {
                List<edge> S = new List<edge>();
                //    for each T in L, find the smallest edge connecting T to G-T
                foreach(List<int> T in L){ 
                   edge smallestEdge = null;
                   foreach(int i in T){
                        List<edge> Edges = G[i].OrderBy(x => x.weight).ToList();
                        foreach(edge e in Edges){
                            if ((!T.Contains(e.to)) && (smallestEdge == null || e.weight < smallestEdge.weight)) { smallestEdge = e; }        
                        }   
                   }
                    S.Add(smallestEdge);
                }

                 //    add all those edges to the MST
                 //    (causing pairs of trees in L to merge)
                List<int> nodesToRemove = new List<int>();
                for(int i = 0; i < S.Count; i++){
                    //we  will merge the trees
                    int t1 = -1;
                    int t2 = -1;
                    for(int j = 0; j < L.Count; j++){
                        if(L[j].Contains(S[i].from)) t1 = j;
                        if(L[j].Contains(S[i].to)) t2 = j;
                    }

                    if(t1 == t2)
                    {
                        //t1 and t2 are in the same mess.  This means this node was a duplicate 
                        //we have already merged the trees, so just remove this node from S so it 
                        //isn't added to the mst twice
                        nodesToRemove.Add(i);
                    }
                    else{
                        //take these two indexes and use them to merge the two trees in L
                        if(t1 < t2){
                            L[t1].AddRange(L[t2]);
                            L.RemoveAt(t2);
                        }
                        else{
                            L[t2].AddRange(L[t1]);
                            L.RemoveAt(t1);
                        }
                    }
                }

                //remove any duplicate nodes before adding them to the mst...
                nodesToRemove.Sort();
                for( int i = nodesToRemove.Count - 1; i >= 0; i--){
                    S.RemoveAt(nodesToRemove[i]);
                }
                //add all those edges to the MST
                mst.AddRange(S);

            }
            return mst;
        }
    }
}
