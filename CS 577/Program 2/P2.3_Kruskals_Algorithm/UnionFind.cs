using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace P2._3_Kruskals_Algorithm
{
    class UnionFind
    {
        //includes ranking and tree compression for performance increases...

        List<listElement> nodes;

        public UnionFind(){
            nodes = new List<listElement>();
        }

        public void MakeSet(int element){
            //parent set to itself.  This will indicate a root element
            listElement e = new listElement();
            e.parent = element;
            e.rank = 0;
            nodes.Add(e);
        }

        //finds what set A belongs to 
        public int Find(int element)
        {
            //Compression added to make future finds faster
            if (nodes[element].parent == element) return element; //set to be its own parent if it is the top
            else
            {
                nodes[element].parent = Find(nodes[element].parent);
                return (nodes[element].parent);
            }
        }

        //merge A's set with B's set
        public void Union(int x, int y)
        {
            //method should merge and attempt to keep tree as balanced as possible without rebuilding
            int xRoot = Find(x);
            int yRoot = Find(y);

            if (xRoot == yRoot) return; //same set, no union necessary

            // union the sets
            if (nodes[xRoot].rank < nodes[yRoot].rank)
            {
                nodes[xRoot].parent = yRoot;
            }

            else if (nodes[xRoot].rank < nodes[yRoot].rank)
            {
                nodes[yRoot].parent = xRoot;
            }

            else //same rank
            {
                nodes[yRoot].parent = xRoot;
                nodes[xRoot].rank += 1;
            }
        }

    }
    public class listElement
    {
        public int parent;
        public int rank;

        public listElement(int parent, int rank)
        {
            this.parent = parent;
            this.rank = rank;
        }
        public listElement(){}
    }
}
