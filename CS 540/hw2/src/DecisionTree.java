///////////////////////////////////////////////////////////////////////////////
// Main Class File:  HW2.java
// File:             DecisionTree.java
// Semester:         Fall 2011
//
// Author:           chris@crwilcox.com
// CS Login:         wilcox
// Lecturer's Name:  Jerry Zhu
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class DecisionTree {
	boolean DEBUG = false;
	String trainFilename, tuneFilename, testFilename;

	ArrayList<String[]> columnNames = new ArrayList<String[]>();
	String[] decision;
	ArrayList<String[]> trainEntries = new ArrayList<String[]>();
	ArrayList<String[]> tuneEntries = new ArrayList<String[]>();
	ArrayList<String[]> testEntries = new ArrayList<String[]>();

	// PUBLIC METHODS
	/**
	 * Constructor for Decision Tree
	 * 
	 * @param trainFilename
	 *            Training Data Set File Name
	 * @param tuneFilename
	 *            Tuning Data Set File Name
	 * @param testFilename
	 *            Testing Data Set File Name
	 */
	public DecisionTree(String trainFilename, String tuneFilename,
			String testFilename) {
		this.trainFilename = trainFilename;
		this.tuneFilename = tuneFilename;
		this.testFilename = testFilename;

		trainEntries = ReadInData(trainFilename);
		tuneEntries = ReadInData(tuneFilename);
		testEntries = ReadInData(testFilename);
	}

	/**
	 * MODE0 0 : print mutual info for each possible question at root node only
	 * needs training set
	 */
	public void PrintMutualInformation() {
		// from training data, we need a list of attributes (for now, make
		// mutual info 0 for all. they will be updated.
		ArrayList<DTAttribute> attributes = new ArrayList<DTAttribute>();
		for (int i = 0; i < columnNames.size(); i++) {
			attributes.add(new DTAttribute(i, columnNames.get(i)[0], 0));
		}
		GetMutualInformation(attributes, trainEntries);

		// now try to print the information
		for (int i = 0; i < attributes.size(); i++) {
			// get title
			System.out.printf("%s ", attributes.get(i).ColumnName);
			// print mutual information value
			System.out.printf("%1.18f\n", attributes.get(i).MutualInfo);
		}
	}

	/**
	 * MODE1 1 : print decision tree (needs training set)
	 */
	public void PrintDecisionTree() {
		ArrayList<DTAttribute> attributes = new ArrayList<DTAttribute>();
		for (int i = 0; i < columnNames.size(); i++) {
			// columnames.get(i)[0] gets the name other fields will have
			// possible entries
			attributes.add(new DTAttribute(i, columnNames.get(i)[0], 0));
		}
		// we don't need a parent_examples. it starts with the parent. The
		// recursion will fill this
		DTTree resultTree = DecisionTreeLearning(trainEntries, attributes,
				new ArrayList<String[]>());

		// now print the tree
		resultTree.PrintTree();
	}

	/**
	 * MODE2 2 : print predictions on the test set (needs training and test
	 * sets)
	 */
	public void PrintPredictionsOnTestSet() {
		/*
		 * Ignore the class label of each example in the test set (when we test
		 * your program, we might put ? or some random string there). When
		 * modeFlag=2, classify each example in the test set using your tree.
		 * Output ONLY the class label of each example, one per line. DO NOT
		 * produce any other text with the hand-in version of your program when
		 * modeFlag=2 (so that we can automatically grade your output). Make
		 * sure you have exactly the same number of lines of output as the
		 * number of test examples. Note that we may use a different test set
		 * than the one provided to you.
		 */
		ArrayList<DTAttribute> attributes = new ArrayList<DTAttribute>();
		for (int i = 0; i < columnNames.size(); i++) {
			// columnames.get(i)[0] gets the name other fields will have
			// possible entries
			attributes.add(new DTAttribute(i, columnNames.get(i)[0], 0));
		}
		// we don't need a parent_examples. it starts with the parent. The
		// recursion will fill this
		DTTree decisionTree = DecisionTreeLearning(trainEntries, attributes,
				new ArrayList<String[]>());

		int changed = 0;
		for (int i = 0; i < testEntries.size(); i++) {
			// foreach entry, predict the outcome using the tree
			String outcome = PredictOutcome(decisionTree, testEntries.get(i));

			if (DEBUG) {
				System.out.printf("%-5s EXP: %s\n", outcome,
						testEntries.get(i)[0]);
				if (!outcome.equals(testEntries.get(i)[0]))
					changed++;
			} else {
				System.out.println(outcome);
			}

		}
		if (DEBUG)
			System.out.printf("%d of %d Changed From The Expected", changed,
					testEntries.size());
	}

	/**
	 * MODE3 3 : print pruned decision tree. (Need ALL 3 data sets)
	 */
	public void PrintPrunedDecisionTree() {
		/*
		 * When modeFlag=3 or modeFlag=4, use the tuning set to prune the
		 * decision tree. Build the decision tree T as before using the whole
		 * training set. After the tree is built, perform pruning. If
		 * modeFlag=3, print the final pruned tree. If modeFlag=4, use the final
		 * pruned tree to classify the test set. Again, print only one class
		 * label per line.
		 */
		DTTree prunedTree = GetPrunedDecisionTree();
		prunedTree.PrintTree();
	}

	/**
	 * MODE4 4 : print predictions on the test set using pruned tree (need ALL 3
	 * data sets)
	 */
	public void PrintPredictionsOnTestSetUsingPrunedTree() {
		DTTree prunedTree = GetPrunedDecisionTree();

		int changed = 0;
		for (int i = 0; i < testEntries.size(); i++) {
			// foreach entry, predict the outcome using the tree
			String outcome = PredictOutcome(prunedTree, testEntries.get(i));

			if (DEBUG) {
				System.out.printf("%-5s EXP: %s\n", outcome,
						testEntries.get(i)[0]);
				if (!outcome.equals(testEntries.get(i)[0]))
					changed++;
			} else {
				System.out.println(outcome);
			}
		}
		if (DEBUG){
			System.out.printf("%d of %d Changed From The Expected", changed,
					testEntries.size());
			System.out.println("Tree Used:");
			prunedTree.PrintTree();
		}
	}

	// PRIVATE METHODS
	/**
	 * Given the entry list given, this method computes the entropy
	 * 
	 * @param list
	 *            a list of data entries. element [0] of each String[] is used
	 *            to find entropy
	 * @return the entropy as a double
	 */
	private float GetEntropy(ArrayList<String[]> list) {
		float positive = 0;
		float negative = 0;
		// get positive/negative breakdown
		for (int i = 0; i < list.size(); i++) {
			// if decision 0
			if (list.get(i)[0].equals(decision[0])) {
				positive++;
			} else if (list.get(i)[0].equals(decision[1])) {
				negative++;
			} else {
				System.err
						.println("Something went wrong finding the entropy.  Check for typos in the input file.");
			}
		}

		// now calculate entropy
		float total = positive + negative;
		// entropy = [-(positive/tot)log_2 (pos/tot)] - [(neg/tot)log_2
		// (negative/tot)]
		float posTot = positive / total;
		float negTot = negative / total;
		float logPosTot = (float)Math.log(posTot) / (float)Math.log(2);
		float logNegTot = (float)Math.log(negTot) / (float)Math.log(2);

		// if either of these is 0, the entropy is also 0
		if (negative == 0 || positive == 0) {
			return 0;
		} else {
			if (logPosTot == Double.NEGATIVE_INFINITY)
				logPosTot = 0;
			if (logNegTot == Double.NEGATIVE_INFINITY)
				logNegTot = 0;

			float posSide = posTot == 0 ? 0 : -((posTot) * logPosTot);
			float negSide = negTot == 0 ? 0 : -((negTot) * logNegTot);
			
			return posSide + negSide;
		}
	}

	/**
	 * This Algorithm builds the decision tree based on the given attributes and
	 * examples
	 * 
	 * @param examples
	 *            Data in the form of String[]. [0] is the classification, [1-n]
	 *            are attributes
	 * @param attributes
	 *            Attributes are used to decide the best way to find the
	 *            classification
	 * @param parentExamples
	 *            Used in recursion, for first call in, just leave this empty.
	 * @return A Decision Tree that allows you to predict the classification of
	 *         an element
	 */
	private DTTree DecisionTreeLearning(ArrayList<String[]> examples,
			ArrayList<DTAttribute> attributes,
			ArrayList<String[]> parentExamples) {
		// function DECISION-TREE-LEARNING(examples, attributes,
		// parent-examples) returns a tree
		// if examples is empty than return PLURALITY-VALUE(parent_examples)
		if (examples.size() == 0) {
			return PluralityValue(parentExamples);
		}
		// else if all examples have the same classification then return the
		// classification
		else if (GetEntropy(examples) == 0.0) {
			// find classification of one, it will be the same as all of them
			if (examples.get(0)[0].equals(decision[0])) {
				return new DTTree(decision[0], null, -1, null);
			} else {
				return new DTTree(decision[1], null, -1, null);
			}
		}
		// else if attributes is empty then return PLURALITY-VALUE(examples)
		else if (attributes.size() == 0) {
			return PluralityValue(examples);
		}
		// else
		else {
			// A <- argmax IMPORTANCE(a, examples), so pick the attribute that
			// is most important of examples
			attributes = GetMutualInformation(attributes, examples);
			DTAttribute A = FindMostImportantAttribute(attributes);

			// tree <- a new decision tree with root test A
			DTTree tree = new DTTree(A.ColumnName, null, A.ColumnNumber,
					new ArrayList<DTTree>());
			// foreach value v_k in A
			for (int k = 1; k < columnNames.get(A.ColumnNumber).length; k++) {
				// exs <- e : e E examples and e.A = v_k // exs is the e of
				// examples were e.A is = vK. where this attribute is this value
				ArrayList<String[]> exs = new ArrayList<String[]>();
				for (int i = 0; i < examples.size(); i++) {
					// compare the value in this example element with the
					// reference vk element
					if (examples.get(i)[A.ColumnNumber + 1].equals(columnNames
							.get(A.ColumnNumber)[k])) {
						exs.add(examples.get(i));
					}
				}

				// subtree <- DECISION-TREE-LEARNING(exs, attributes - A,
				// examples)
				// form attributes - a
				ArrayList<DTAttribute> modAttributes = new ArrayList<DTAttribute>();
				modAttributes.addAll(attributes);
				// lets try and remove A from the modAttributes
				for (int j = 0; j < modAttributes.size(); j++) {
					if (modAttributes.get(j).ColumnNumber == A.ColumnNumber) {
						modAttributes.remove(j);
					}
				}
				DTTree subtree = DecisionTreeLearning(exs, modAttributes,
						examples);
				// add a branch to tree with label (A = v_k) and subtree subtree
				subtree.Choice = columnNames.get(A.ColumnNumber)[k];
				tree.Children.add(subtree);
			}
			// return tree
			return tree;

		}
	}

	/**
	 * Gets the mutual information/information gain of data set
	 * 
	 * @param attributes
	 *            attributes of the data set
	 * @param examples
	 *            data set
	 * @return Attributes complete with mutual information for each
	 */
	private ArrayList<DTAttribute> GetMutualInformation(
			ArrayList<DTAttribute> attributes, ArrayList<String[]> examples) {
		// first we want the mutual information for these few things

		// H(Decision)
		float entropy = (float) GetEntropy(examples);

		// H(Survived | Feature)
		// get subset of data given this feature being known (could be any
		// number of choices)
		// H(Decision|Feature) = P(Feature_i)H(Decision|Feature_i)
		// this is actually a sum over the values

		// for each feature/attribute (columnName), find the information gain
		for (int attributesIter = 0; attributesIter < attributes.size(); attributesIter++) {
			if (attributes.get(attributesIter).ColumnName.equals("Rain")) {
				System.out.print("");
			}
			// Separate Data into possible values of that trait.
			// we need to get the entropy of each and then do a weighted sum
			float weightedSum = 0;
			int attributeColumn = attributes.get(attributesIter).ColumnNumber;
			for (int attributeValueIter = 1; attributeValueIter < columnNames
					.get(attributeColumn).length; attributeValueIter++) {
				// now we look at each possible value of each trait
				ArrayList<String[]> subEntries = new ArrayList<String[]>();
				// select out of full data, entries that have features == to our
				// chosen value

				String featureVal = columnNames.get(attributeColumn)[attributeValueIter];
				for (int k = 0; k < examples.size(); k++) {
					if (examples.get(k)[attributeColumn + 1].equals(featureVal)) {
						subEntries.add(examples.get(k));
					}
				}

				// now we need to get the entropy of these sub entries. we want
				// to take (subEntries size / entries size) H(subentries)
				weightedSum += ((float) subEntries.size() /  (float)examples
						.size()) * (float)GetEntropy(subEntries);

			}
			// we now have H(Decision | Feature) in weighted sum
			// record total information gain in table
			attributes.get(attributesIter).MutualInfo = entropy - weightedSum;
		}

		// need to clean attributes. Take anything that is negative or NaN and
		// set it to 0
		for (int i = 0; i < attributes.size(); i++) {
			if (attributes.get(i).MutualInfo < 0
					|| attributes.get(i).MutualInfo == Double.NaN
					|| attributes.get(i).MutualInfo == Double.NEGATIVE_INFINITY)
				attributes.get(i).MutualInfo = 0;
		}

		return attributes;
	}

	/**
	 * Helper method to find the highest valued information gain of a list of
	 * attributes
	 * 
	 * @param attributes
	 *            Attributes List
	 * @return the Most Important Attribute
	 */
	private DTAttribute FindMostImportantAttribute(
			ArrayList<DTAttribute> attributes) {

		Collections.sort(attributes, new DTAttributeComparator());
		/*
		 * if(DEBUG){ for(int i = 0; i < attributes.size(); i++){
		 * System.out.printf("%-6s %f\n", attributes.get(i).ColumnName,
		 * attributes.get(i).MutualInfo); } System.out.println(); }
		 */
		return attributes.get(0);
	}

	/**
	 * Gets the plurality value of a data set
	 * 
	 * @param examples
	 *            a data set
	 * @return the Plurality Value in the form of a DTTree (this is used to be a
	 *         child node of a tree)
	 */
	private DTTree PluralityValue(ArrayList<String[]> examples) {

		int c0 = 0;
		int c1 = 0;
		for (int i = 0; i < examples.size(); i++) {
			if (examples.get(i)[0].equals(decision[0])) {
				c0++;
			} else {// was other decision
				c1++;
			}
		}
		// if the positive answer is equal or better return that
		if (c0 >= c1) {
			return new DTTree(decision[0], null, -1, null);
		} else {
			return new DTTree(decision[1], null, -1, null);
		}
	}

	/**
	 * Given a decision tree and an entry for that tree, predict the outcome
	 * 
	 * @param decisionTree
	 *            a Decision Tree
	 * @param entry
	 *            an entry of the same form as the Decision Tree
	 * @return the outcome in the form of a String
	 */
	private String PredictOutcome(DTTree decisionTree, String[] entry) {
		// walk the tree. Ignore entry[0] since it is just the classification,
		// or possibly gibberish
		// when we get to a node that has no children (check for null and then
		// size == 0.
		// now we can just print the attributeName.
		DTTree iterTree = decisionTree;
		while (iterTree.Children != null && iterTree.Children.size() > 0) {
			// to account for the classification at 0, add 1 to AttributeNumber
			int lookAtColumn = iterTree.AttributeNumber + 1;
			// we need to look over the children of the iterTree. When we find
			// the one that equals the value
			// in our sample entry, change the iterTree
			int childToTake = -1;
			for (int i = 0; i < iterTree.Children.size(); i++) {
				if (iterTree.Children.get(i).Choice.equals(entry[lookAtColumn])) {
					childToTake = i;
				}
			}
			iterTree = iterTree.Children.get(childToTake);
		}

		return iterTree.attributeName;
	}

	/**
	 * Used by Modes 3 and 4 to get the pruned tree
	 * 
	 * This is how you perform one iteration of pruning: For each internal node
	 * i in the trained tree T, collapse the subtree at node i into a leaf node.
	 * You need to gather all training examples that were under that subtree,
	 * perform a majority vote among those examples, and use the majority vote
	 * label as the predicted label for the new leaf node. This results in a
	 * tree T_(-i). Compare the accuracies of trees T, T_-1,...,T_-m on the
	 * tuning set, where m is the number of internal nodes (note: when you
	 * construct T-2, you start from T again, not T-1). Replace T with the best
	 * tree among these. Perform multiple iterations of pruning as described
	 * above. Each iteration starts with the best tree created at the previous
	 * iteration. Iterate until the tuning accuracy no longer increases.
	 * 
	 * @returns The best pruned decision tree
	 */
	private DTTree GetPrunedDecisionTree() {
		ArrayList<DTAttribute> attributes = new ArrayList<DTAttribute>();
		for (int i = 0; i < columnNames.size(); i++) {
			// columnames.get(i)[0] gets the name other fields will have
			// possible entries
			attributes.add(new DTAttribute(i, columnNames.get(i)[0], 0));
		}
		// we don't need parent_examples. it starts with the parent. The
		// recursion will fill this
		DTTree decisionTree = DecisionTreeLearning(trainEntries, attributes,
				new ArrayList<String[]>());

		DTTree bestTree;
		DTTree newBestTree = new DTTree(decisionTree);
		double bestTreeAcc = 0;
		double newBestTreeAcc = 0;
		int count = 0;
		do {
			bestTree = newBestTree;
			newBestTree = GetBestNodeRemovedPrunedTree(new DTTree(bestTree),
					trainEntries);
			count++;

			bestTreeAcc = GetTreeAccuracy(bestTree, tuneEntries);
			newBestTreeAcc = GetTreeAccuracy(newBestTree, tuneEntries);

			if (DEBUG) {
				System.out.println("Best Tree:");
				System.out.println(bestTreeAcc);
				bestTree.PrintTree();
				System.out.println("New Best Tree:");
				System.out.println(newBestTreeAcc);
				newBestTree.PrintTree();
				System.out.printf("Continue: %s\n",
						bestTreeAcc <= newBestTreeAcc ? "Yes" : "No");
			}
		} while (bestTreeAcc <= newBestTreeAcc);

		return bestTree;
	}

	/**
	 * This is how you perform one iteration of pruning: For each internal node
	 * i in the trained tree T, collapse the subtree at node i into a leaf node.
	 * You need to gather all training examples that were under that subtree,
	 * perform a majority vote among those examples, and use the majority vote
	 * label as the predicted label for the new leaf node. This results in a
	 * tree T_(-i). Compare the accuracies of trees T, T_-1,..., T_-m on the
	 * tuning set, where m is the number of internal nodes (note: when you
	 * construct T-2, you start from T again, not T-1). Replace T with the best
	 * tree among these.
	 * 
	 * @param bestTree
	 *            The Current best tree
	 * @param trainingEntries
	 *            the list of training entries corresponding to the tree
	 * @return the Best Node Removed Pruned Tree
	 */
	private DTTree GetBestNodeRemovedPrunedTree(DTTree bestTree,
			ArrayList<String[]> trainingEntries) {

		if (DEBUG)
			bestTree.PrintTree();
		bestTree.ClearAllCollapsed();
		ArrayList<DTTree> prunedTrees = new ArrayList<DTTree>();

		ArrayList<String[]> localEntries = trainingEntries;
		// make a copy of the best tree for us to mess up (add it to the
		// prunedTreeList)
		prunedTrees.add(bestTree);

		DTTree bestTreeIter = prunedTrees.get(0);
		// find a node that (has children and is not collapsed), mark it as
		// collapsed on the bestTree.
		while (!bestTreeIter.collapsed && bestTreeIter.Children != null
				&& bestTreeIter.Children.size() > 0) {
			// we need to make sure we have a subset of the data. As we go
			// through the dataset, lets keep filtering the overall DataSet.
			// find a child that is not collapsed that has children
			int lastNonCollapsedChild = -1;
			for (int i = 0; i < bestTreeIter.Children.size(); i++) {
				// if we find a child that has children (an interior node) and
				// it is not itself collapsed
				// we then mark it as the next item to go to...
				if (!bestTreeIter.Children.get(i).collapsed
						&& bestTreeIter.Children.get(i).Children != null
						&& bestTreeIter.Children.get(i).Children.size() > 0) {
					lastNonCollapsedChild = i;
				}
			}

			// if all of the children are collapsed (-1 returned), and
			// bestTreeIter is not, this is as far down as we can go
			if (lastNonCollapsedChild == -1) {
				// we can now use the data we have to collapse this node
				// mark collapsed, pop one of these onto the list
				bestTreeIter.collapsed = true;
				// duplicates the last member of the list for next loop
				prunedTrees.add(new DTTree(
						prunedTrees.get(prunedTrees.size() - 1)));
				// now collapse the data (get a majority vote to replace this
				// with.
				bestTreeIter.Children = null;// get rid of the nodes after it
				bestTreeIter.attributeName = GetMajorityVote(localEntries);
				bestTreeIter.Choice = bestTreeIter.Choice;// NO-OP. Choice stays
															// the same
				bestTreeIter.AttributeNumber = -1;
				// move bestTree iter to the head of the copied tree
				bestTreeIter = prunedTrees.get(prunedTrees.size() - 1);
				// replace local entry list with the trainEntry list so we can
				// start over
				localEntries = trainingEntries;
			}
			// else, the children are not all collapsed, we should keep going...
			else {
				// update the local dataset for this search to where we are
				// putting bestTreeIter
				// bestTreeIter.AttributeNumber will tell us what column to use
				// to separate the data. we can also use choice
				ArrayList<String[]> temp = new ArrayList<String[]>();
				for (int i = 0; i < localEntries.size(); i++) {
					String test1 = localEntries.get(i)[bestTreeIter.AttributeNumber + 1];
					String test2 = bestTreeIter.Children
							.get(lastNonCollapsedChild).Choice;
					if (test1.equals(test2)) {
						temp.add(localEntries.get(i));
					}
				}

				localEntries = temp;

				// update best tree iter to best tree
				// iter.child(lastNonCollapsedChilde)
				bestTreeIter = bestTreeIter.Children.get(lastNonCollapsedChild);
			}
		}

		// The last tree in the prunedTree list is an unmodified version of the
		// original.
		// Remove it to stop infinite looping
		prunedTrees.remove(prunedTrees.size() - 1);

		/* FINDING THE BEST OF THE FOUND PRUNED TREES */
		// now we need to see which of the prunedTrees is most accurate. Iterate
		// through using most accurate.
		// pull out the one that is most accurate
		if (prunedTrees.size() == 0) {
			System.err
					.println("AT MINIMUM, THE BASE TREE SHOULD BE IN HERE... SOMETHING IS WRONG");
		}

		DTTree bestPrunedTree = prunedTrees.get(0);

		// if(DEBUG) System.out.println("+++++++");
		for (int i = 0; i < prunedTrees.size(); i++) {
			/*
			 * if(DEBUG){ prunedTrees.get(i).PrintTree();
			 * System.out.println("---"); }
			 */
			if (GetTreeAccuracy(prunedTrees.get(i), trainingEntries) > GetTreeAccuracy(
					bestPrunedTree, trainingEntries)) {
				bestPrunedTree = prunedTrees.get(i);
			}
		}
		/*
		 * if(DEBUG) { System.out.println("+++");
		 * System.out.println("Selected:"); bestPrunedTree.PrintTree();
		 * System.out.println("+++++++"); }
		 */

		return bestPrunedTree;
	}

	/**
	 * Given a list of examples/localEntries, return a majority vote one column
	 * (String[0])
	 * 
	 * @param localEntries
	 *            a list of examples to find a majority in
	 * @return majority vote as the string title.
	 */
	private String GetMajorityVote(ArrayList<String[]> localEntries) {
		int dec0 = 0;
		int dec1 = 0;

		for (int i = 0; i < localEntries.size(); i++) {
			if (localEntries.get(i)[0].equals(decision[0])) {
				dec0++;
			} else {// decision[1]
				dec1++;
			}
		}
		if (dec0 >= dec1)
			return decision[0];
		else
			return decision[1];
	}

	/**
	 * This method gets the accuracy of a given tree against test entries
	 * 
	 * @param root
	 *            the root of the tree to test
	 * @param testEntries
	 *            the test entries that determine accuracy
	 * @return double percentage of accuracy
	 */
	private double GetTreeAccuracy(DTTree root, ArrayList<String[]> testEntries) {

		int same = 0;
		for (int i = 0; i < testEntries.size(); i++) {
			// foreach entry, predict the outcome using the tree
			String outcome = PredictOutcome(root, testEntries.get(i));

			if (outcome.equals(testEntries.get(i)[0])) {
				same++;
			}

		}
		double ret = (double) same / testEntries.size();
		/*
		 * if(DEBUG){ System.out.println("TESTING TREE FOR ACCURACY:");
		 * root.PrintTree(); System.out.println("ACCURACY: " + ret);
		 * System.out.println(); }
		 */

		return ret;
	}

	/**
	 * Used to read in datafiles from their text format into ArrayList<String[]>
	 * for this program. This also adjusts the decisions global and the
	 * columnNames global.
	 * 
	 * @param dataFileName
	 *            the name of the data file ie test.txt
	 * @return the datafile in a format of ArrayList<String[]>
	 */
	private ArrayList<String[]> ReadInData(String dataFileName) {
		ArrayList<String[]> readEntries = new ArrayList<String[]>();
		ArrayList<String[]> readColumnNames = new ArrayList<String[]>();
		String[] readDecision = new String[2];

		try {
			BufferedReader in = new BufferedReader(new FileReader(dataFileName));
			String line;
			while ((line = in.readLine()) != null) {
				String[] splitLine = line.split(",");

				if (splitLine[0].startsWith("//")) {
					;// ignore this line as it is a comment
				} else if (splitLine[0].equals("%%")) {// binary decision
					if (splitLine.length != 3) {
						System.err.println("THIS IS NOT POSSIBLE");
					}
					readDecision = new String[] { splitLine[1], splitLine[2] };
				} else if (splitLine[0].equals("##")) {
					// column name and possible values
					// first item of array is the name,
					// the rest are possible values
					String[] addItem = new String[splitLine.length - 1];
					for (int i = 0; i < addItem.length; i++) {
						addItem[i] = splitLine[i + 1];
					}
					readColumnNames.add(addItem);
				} else {// just a standard entry can just add this entry.
						// The column names will tell us how the values work.
						// the first entry will be the decision.
						// The following will be the columns
					readEntries.add(splitLine);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.columnNames = readColumnNames;
		this.decision = readDecision;
		return readEntries;
	}

}
