using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Huffmans_Algorithm
{
    class node
    {
        public int num;
        public int leftChild;
        public int rightChild;

        public node(int num, int left, int right) { this.num = num; this.leftChild = left; this.rightChild = right; }
    }
    class frequencyItem : IComparable<frequencyItem>
    {
        public int name;
        public int frequency;

        public frequencyItem(int name, int frequency) { this.name = name; this.frequency = frequency; }

        public int CompareTo(frequencyItem obj)
        {
            return frequency.CompareTo(obj.frequency);
        }
    }
    class Program
    {
        /*
        4) Huffman's algorithm: Implement the algorithm for Huffman encoding described in section 5.2.  Your algorithm should take in a character set with associated frequencies and produce the Huffman code.  
        Extra credit 1:  Take as input a text and compute the frequencies for the characters in the text. (5 points).
                FUNCTION IS CALLED static List<KeyValuePair<char, int>> getFrequencies(string input)
        Extra credit 2:  Take in a text and translate it from the natural alphabet to binary using your Huffman code.  Then decode the text from binary back to the natural alphabet.  (5 points).
                I have two methods.  Encode and Decode.  They are used in the test
         */
        static void Main(string[] args)
        {
            Console.SetWindowSize(100, Console.WindowHeight);//measured in chars.  default is 80
            Console.WriteLine("##### HuffTest1 #####");
            test(new char[] {'a','c','l','n','p','s','v','z'}, new int[] {24,13,33,71,23,2,68,31 });
            Console.ReadLine();

            Console.WriteLine("##### HuffTest2 #####");
            test(new char[] {'d','l','n','k','b','m','c','u','q','x','t','f'}, new int[] {302,120,442,921,99,268,512,28,787,445,591,1011});
            Console.ReadLine();

            Console.WriteLine("##### HuffTest3 #####");
            test(new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}, 
                 new int[]{1,2,3,4,5,6,7,8,9,11,22,33,44,55,66,77,88,99,111,222,333,444,555,666,777,888});
            Console.ReadLine();

            Console.WriteLine("##### HuffECTest #####");
            ectest( "One day, the Energizer bunny, the Easter Bunny, and the Playboy bunny were out taking a stroll when they all spotted a squirrel. " + 
                    "But not just any squirrel. A pink squirrel with rabies. Since all three bunnies enjoyed eating rabies-flavored squirrels, they " +
                    "decided to catch it and cook it. The playboy bunny attempted to catch it first. But before the Playboy bunny was able to make it " +
                    "to the squirrel, Hugh Hefner jumped (ok, walked out with a cane) of a bush with a creepy old-man grin and started undressing the " + 
                    "Playboy bunny with his eyes. The Playboy bunny ran away. Next the Energizer bunny decided to to take a crack at the squirrel. As " +
                    "he rolled over towards the squirrel, he suddenly began to get pelted with Duracell batteries. Confused, he looked up into the trees, " + 
                    "and sure enough, the Keebler elves were throwing Duracell batteries at him! I mean, we're talking size D batteries! Afraid one of the " +
                    "batteries would damage his precious drum, the Energizer bunny sped away. The Easter bunny then realized that it was all up to him to catch " +
                    "the delicious rabies-infested squirrel. He sat and thought for a minute when he came up with a beyond-brilliant idea. He closed his eyes and " +
                    "mustered up all of his strength until PLOP! A chocolate egg popped out of him. Seriously, a chocoate egg. I know you're thinking he just took " +
                    "a twosie, but you should know by now that the Easter bunny doesn't take twosies. Just chocolate eggsies. Anyway, if there's something in this " +
                    "world that a pink squirrel with rabies simply cannot resist, it's a chocolate egg. Plus, seeing as the squirrel was on his third cocktail, it " +
                    "didn't even cross his mind that the chocolate egg could be a trap. He bounded towards the chocolate egg, with drool (and foam) spewing from his " +
                    "mouth as he ran. He reached the egg and proceeded to stuff the whole thing into his furry little cheeks. He then looked up and saw the Easter " +
                    "bunny gaping at him. He knew that bunnies loved squirrel meat, and he then realized his little life was about to be snuffed out. A foamy tear " +
                    "slid down his face. Seeing this, the Easter bunny's cold, cold heart instantly melted. Smiling, he hopped away into the sunset, leaving the pink " + 
                    "squirrel to his happy care-free life. Then a car came by and hit the squirrel. The end.");
            Console.ReadLine();
        }

        static void test(char[] letters, int[] frequencies)
        {

            /*
             * use this weighting to get a huffmann encoding.
             */
            Console.WriteLine(":::::HUFFMAN TREE::::: ");
            node[] output = Huffman(frequencies);

            foreach (node n in output)
            {
                if (n != null)
                {
                    Console.Write("Num: " + n.num);
                    Console.Write(" Left: " + n.leftChild);
                    Console.Write(" Right: " + n.rightChild);
                    Console.WriteLine();
                }
            }

            //get the codes
            string[] binCodes = new string[frequencies.Length];
            string[] codes = GetBinaryCodesFromTree(output, 2 * frequencies.Length - 2, binCodes, "");

            /*
             * print the codes
             */
            Console.WriteLine(":::::CODES:::::");
            for (int i = 0; i < codes.Length; i++)
            {
                Console.WriteLine(letters[i] + ":" + codes[i]);
            }

        }

        static void ectest(string input)
        {
            Console.WriteLine(":::::MESSAGE::::: ");
            Console.WriteLine(input);

            /*
             * Take as input a text and compute the frequencies for the characters in the text.
             */
            Console.WriteLine(":::::FREQUENCIES::::: ");
            List<KeyValuePair<char, int>> kvpFreq = getFrequencies(input);

            char[] letters = new char[kvpFreq.Count];
            int[] frequencies = new int[kvpFreq.Count];
            for (int i = 0; i < kvpFreq.Count; i++)
            {
                KeyValuePair<char, int> kvp = kvpFreq[i];
                Console.WriteLine("char:" + kvp.Key + "  count:" + kvp.Value);

                letters[i] = kvp.Key;
                frequencies[i] = kvp.Value;
            }


            /*
             * use this weighting to get a huffmann encoding.
             */
            Console.WriteLine(":::::HUFFMAN TREE::::: ");
            node[] output = Huffman(frequencies);

            foreach (node n in output)
            {
                if (n != null)
                {
                    Console.Write("Num: " + n.num);
                    Console.Write(" Left: " + n.leftChild);
                    Console.Write(" Right: " + n.rightChild);
                    Console.WriteLine();
                }
            }

            //get the codes
            string[] binCodes = new string[frequencies.Length];
            string[] codes = GetBinaryCodesFromTree(output, 2 * frequencies.Length - 2, binCodes, "");

            /*
             * print the codes
             */
            Console.WriteLine(":::::CODES:::::");
            for (int i = 0; i < codes.Length; i++)
            {
                Console.WriteLine(letters[i] + ":" + codes[i]);
            }

            /*
             * show message encoded with binary
             */
            string encoded = Encode(input, letters, codes);
            Console.WriteLine(":::::ENCODING THE MESSAGE::::: ");
            Console.WriteLine(encoded);

            /*
             * take message and unencode it
             */
            string decoded = Decode(encoded, letters, codes);
            Console.WriteLine(":::::DECODING THE MESSAGE::::: ");
            Console.WriteLine(decoded);
        }

        /*
         * procedure Huffman(f)
         * input: an array f[1...n] of frequencies
         * output: an encoding tree with n leaves
         * 
         * let H be a priority queue of intergers, ordered by f
         * for i = 1 to n: insert (H,i)
         * for k=n+1 to 2n-1:
         *      i=deletemin(H), j=deletemni(H)
         *      create a node numbered k with childeren i,j
         *      f[k] = f[i] + f[j]
         *      insert(H,k)
         *
         * Could also implement this using two arrays
         * Sort one, leave the other empty.
         * When the first one is empty, the second one (with elements) is in the
         * same case as we were when we start. now we remove from second array and add to first (maintain sorting)
         * Rinse, Repeat...
         */
        static node[] Huffman(int[] frequencies)
        {
            int n = frequencies.Length;
            int[] f = new int[2 * n];
            frequencies.CopyTo(f, 0);

            node[] T = new node[2 * n];

            //input: an array f[0...n] of frequencies
            //output: an encoding tree with n leaves

            //let H be a priority queue of intergers, ordered by f
            PQueue.PriorityQueue<int, int> H = new PQueue.PriorityQueue<int, int>();
            //for i = 1 to n: insert (H,i)
            for (int i = 0; i < n; i++) H.Enqueue(f[i], i);

            //for k=n+1 to 2n-1:
            for (int k = n; k < (2 * n) - 1; k++)
            {
                //     i=deletemin(H), j=deletemni(H)
                int i = H.DequeueValue(); int j = H.DequeueValue();
                //Console.WriteLine("i = " + i + "; j = " + j); //FOR DEBUGGING
                //     create a node numbered k with childeren i,j
                T[k] = new node(k, j, i);
                //     f[k] = f[i] + f[j]
                f[k] = f[i] + f[j];
                //     insert(H,k)
                H.Enqueue(f[k], k);
            }
            return T;
        }
        /* 
         * This method, given the tree created by huffman will give the codes for huffman 
         * This is a helper function to help create the binary numbering.
         */
        static string[] GetBinaryCodesFromTree(node[] T, int startnode, string[] binCodes, string prefix)
        {
            //if startnode is a leaf
            //add the appropriate prefix to the list for the leaf we just found and return
            if (T[startnode] == null) { binCodes[startnode] = prefix; return binCodes; }

            //go left, find the value (startnode.left, pefix + "0")
            binCodes = GetBinaryCodesFromTree(T, T[startnode].leftChild, binCodes, prefix + "0");

            //go right, find the value (startnode.right, prefix + "1")
            binCodes = GetBinaryCodesFromTree(T, T[startnode].rightChild, binCodes, prefix + "1");

            return binCodes;
        }

        /*
         * EXTRA CREDIT 1:
         * Goes through the input text and returns a list of keyvalue pairs to be used for huffman
         */
        static List<KeyValuePair<char, int>> getFrequencies(string input)
        {
            List<char> charsProcessed = new List<char>();
            List<KeyValuePair<char, int>> ret = new List<KeyValuePair<char, int>>();
            for (int i = 0; i < input.Length; i++)
            {
                char c = input[i];

                if (!charsProcessed.Contains(c))//we haven't processed this one yet, lets do it
                {
                    charsProcessed.Add(c);

                    int count = 1;
                    for (int j = i + 1; j < input.Length; j++)
                    {
                        if (input[j] == c) count++;
                    }
                    ret.Add(new KeyValuePair<char, int>(c, count));

                }
            }

            return ret;
        }

        /*
         * Extra Credit 2: 
         * Take in a text and translate it from the natural alphabet to binary using your Huffman code.  Then decode the text from binary back to the natural alphabet.  (5 points).
         */
        static string Encode(string input, char[] letters, string[] codes)
        {
            string output = String.Empty;
            foreach (char c in input)
            {
                int codeIndex = Array.IndexOf(letters, c);
                output += codes[codeIndex];
            }
            return output;
        }

        static string Decode(string input, char[] letters, string[] codes)
        {
            string output = String.Empty;

            int subStart = 0;
            for (int i = 1; i <= input.Length; i++)
            {
                int symbolIndex = Array.IndexOf(codes, input.Substring(subStart, i - subStart));
                if (symbolIndex != -1) //we found a code
                {
                    output += letters[symbolIndex];
                    subStart = i;
                }
            }

            return output;
        }

    }
}
