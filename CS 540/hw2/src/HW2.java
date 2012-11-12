///////////////////////////////////////////////////////////////////////////////
// Title:            HW2
// Files:            HW2.java
// Semester:         Fall 2011
//
// Author:           Christopher Wilcox (chris@crwilcox.com)
// CS Login:         wilcox
// Lecturer's Name:  Jerry Zhu
//
// Description:      implement in Java the decision tree construction algorithm 
//                   given in Figure 18.5 on page 702 of the textbook. The main 
//                   class must be called HW2.java 
///////////////////////////////////////////////////////////////////////////////
public class HW2 {

	public static void main(String[] args) {
		if (args.length != 4) {
			System.out
					.println("USAGE:\n"
							+ "java HW2 <modeFlag> <trainFilename> <tuneFilename> <testFilename>\n"
							+ "modeFlag is a number from 0 to 4.  Parts (a) to (d) have details\n"
							+ "0 : print mutual info for each possible question at root node\n"
							+ "1 : print decision tree\n"
							+ "2 : print predictions on the test set\n"
							+ "3 : print pruned decision tree\n"
							+ "4 : print predictions on the test set using pruned tree\n"
							+ "trainFilename - the file containing the training set\n"
							+ "tuneFilename - the file containing the tuning set\n"
							+ "testFilename - the file containing the test set\n");
		} else {
			// Process Arguments and Direct To Correct Part of Program
			int modeFlag = Integer.parseInt(args[0]);
			String trainFilename = args[1];
			String tuneFilename = args[2];
			String testFilename = args[3];

			DecisionTree dt = new DecisionTree(trainFilename, tuneFilename,
					testFilename);

			switch (modeFlag) {
			case 0:
				// 0 - print mutual info for each possible question at root node
				// only needs training set
				dt.PrintMutualInformation();
				break;
			case 1:
				// 1 - print decision tree
				// only needs training set
				dt.PrintDecisionTree();
				break;
			case 2:
				// 2 - print predictions on the test set
				// needs training and test sets
				dt.PrintPredictionsOnTestSet();
				break;
			case 3:
				// 3 - print pruned decision tree
				// need (ALL 3) training test and tuning sets
				dt.PrintPrunedDecisionTree();
				break;
			case 4:
				// 4 - print predictions on the test set using pruned tree
				// need (ALL 3) training test and tuning sets
				dt.PrintPredictionsOnTestSetUsingPrunedTree();
				break;
			default:
				// we shouldn't be here...
				System.err
						.println("We should not be here.  An incorrect mode must have beene entered...");
				System.exit(1);
				break;
			}
		}
	}
}
