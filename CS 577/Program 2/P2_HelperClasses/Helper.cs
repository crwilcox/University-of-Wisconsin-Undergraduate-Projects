using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace P2_HelperClasses
{
    public class edge
    {
        public int from, to;
        public int weight;

        public edge(int from, int to, int weight)
        {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public edge() { }
    }
    public class node
    {
        //nodes in pri que have 
        //weight
        //added by
        //node
        public int weight, addedBy, nodeNum;
        public node(int weight, int addedBy, int nodeNum)
        {
            this.weight = weight;
            this.addedBy = addedBy;
            this.nodeNum = nodeNum;
        }
    }
    public class AdjacencyList
    {
        public static List<List<edge>> Build(int[][] adjMatrix)
        {
            //make adjacency list
            List<List<edge>> adjList = new List<List<edge>>();
            for (int i = 0; i < adjMatrix.Length; i++)
            {
                List<edge> oneNode = new List<edge>();
                for (int n = 0; n < adjMatrix.Length; n++)
                {
                    edge temp = new edge();
                    temp.from = i;
                    temp.to = n;
                    temp.weight = adjMatrix[i][n];
                    if (temp.weight != 0) oneNode.Add(temp);
                }
                adjList.Add(oneNode);
            }

            return adjList;
        }

        public static void Print(List<List<edge>> al)
        {
            Console.WriteLine("BEGIN Printing Adjacency Graph");
            for (int i = 0; i < al.Count; i++)
            {
                Console.WriteLine("V: {0}", i);
                foreach (edge e in al[i]) Console.WriteLine("\tFrom: {0}  \tTo: {1}  \tWeight: {2}", e.from, e.to, e.weight);
            }
            Console.WriteLine("END Printing Adjacency Graph");
        }
    }
}
