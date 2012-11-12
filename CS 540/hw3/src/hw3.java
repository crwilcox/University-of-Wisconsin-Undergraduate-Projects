///////////////////////////////////////////////////////////////////////////////
// Title:            hw3
// Files:            hw3.java
// Semester:         Fall 2011
//
// Author:           Christopher Wilcox (chris@crwilcox.com)
// CS Login:         wilcox
// Lecturer's Name:  Jerry Zhu
//
// Description:      implement a Naive Bayes classifier that classifies a 
//					 document as English, Spanish, or Japanese  all 
//					 written with the 26 lower case letters and space.
///////////////////////////////////////////////////////////////////////////////
/**
 * Main class for hw3
 * 
 * @author Christopher Wilcox
 */
public class hw3 {

	/**
	 * The main entry point for hw3
	 * @param args Should contain a trainset_directory as [0] and 
	 * 			   testset_directory as [1]
	 */
	public static void main(String[] args) {		
		/* Validate Argument Length and set variables for Directory paths */
		if(args.length != 2){
			System.err.println(
					"Usage: java hw3 trainset_directory testset_directory"
					);
			System.exit(1);
		}
		
		final String TRAINSET_DIRECTORY = args[0];
		final String TESTSET_DIRECTORY = args[1];

		/* Run Code */
		// 1. Using all the characters in the training data, build a Naive Bayes 
		// classifier for the three languages.  Implement your classifier using 
		// a log-likelihood formulation of Naive Bayes.
		NaiveBayes nb = new NaiveBayes(TRAINSET_DIRECTORY);
		
		// 2. Print P(English), P(Spanish) and P(Japanese), as well as the 
		// conditional probabilities P(c | English), P(c | Spanish), and
		// P(c | Japanese) for all 27 characters c.
		System.out.println(nb.toString());
		
		// 3. Evaluate the performance of your classifier on the test set using 
		// a confusion matrix. A confusion matrix summarizes the types of errors 
		// your classifier makes, as shown in Table 1. The columns are the true 
		// language a document is in, and the rows are the classified outcome of 
		// that document.  The cells are the number of test documents in that 
		// situation. For example, the cell with row = English and 
		// column = Spanish contains the number of test documents that are 
		// really Spanish, but misclassified as English by your classifier
		ConfusionMatrix cm = new ConfusionMatrix(nb, TESTSET_DIRECTORY);
		System.out.println(cm.toString());
	}

}
