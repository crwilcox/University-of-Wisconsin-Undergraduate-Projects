import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

///////////////////////////////////////////////////////////////////////////////
// Main Class File:  hw3.java
// File:             ConfusionMatrix.java
// Semester:         Fall 2011
//
// Author:           chris@crwilcox.com
// CS Login:         wilcox
// Lecturer's Name:  Jerry Zhu
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * This class accepts a naive bayes classifier and given a test directory
 * builds a confusion matrix between the naive bayes classifier and the test
 * directory data set
 * 
 * @author Christopher Wilcox
 */
public class ConfusionMatrix {
	int pEngEng, pEngSpa, pEngJap;
	int pSpaEng, pSpaSpa, pSpaJap;
	int pJapEng, pJapSpa, pJapJap;
	NaiveBayes naiveBayes;
	
	/**
	 * Builds the Confusion Matrix from a naive bayes classifier
	 * and a test directory
	 * 
	 * @param naiveBayes the build naive bayes classifier
	 * @param testset_directory the location of the test set
	 */
	public ConfusionMatrix(NaiveBayes naiveBayes, String testset_directory){
		File englishDir = new File(testset_directory + "/English");
		File japaneseDir = new File(testset_directory + "/Japanese");
		File spanishDir = new File(testset_directory + "/Spanish");
		this.naiveBayes = naiveBayes;
		
		/* CLASSIFYING A TEST DOCUMENT */

		// Consider a new document, doc, from the test set. Suppose it contains 
		// the sequence of 
		// characters c1, c2, ..., ck 
		// (note: the same character may occur multiple times). 
		// Compute the posterior probabilities 
		// (where a is the common denominator in Bayess rule, 
		// which can be ignored for ranking the three languages):
		// P(English | doc) = P(English | c1, c2, , ck)
		//     = a P(c1, c2, , ck | English) P(English) 
		//     = a P(English) P(c1 | English) P(c2 | English)P(ck | English)
		// P(Spanish | doc) = P(Spanish | c1, c2, , ck)
		//     = a P(c1, c2, , ck | Spanish) P(Spanish) 
		//     = a P(Spanish) P(c1 | Spanish) P(c2 | Spanish)P(ck | Spanish)
		// P(Japanese | doc) = P(Japanese | c1, c2, , ck)
		//     = a P(c1, c2, , ck | Japanese) P(Japanese) 
		//     = a P(Japanese) P(c1 | Japanese) P(c2 | Japanese)P(ck | Japanese)
		// Using log probabilities, the product becomes a sum:
		// log P(English | doc) = log a + log P(English) + log P(c1 | English)
		// + log P(c2 | English) + + log P(ck | English)
		// and similarly for Spanish and Japanese.
		// Classify the document as English if
		// log P(English | doc) > log P(Spanish| doc) 
		// and log P(English | doc) > log P(Japanese| 
		//		doc).  Similarly for the other two languages.
		
		// go through each language of test documents, 
		// setting pEngLANGUAGE, pSpaLANGUAGE, pJapLANGUAGE
		fillConfusionTableOneLanguage("english", englishDir);
		fillConfusionTableOneLanguage("spanish", spanishDir);
		fillConfusionTableOneLanguage("japanese", japaneseDir);
	}

	/**
	 * Helper method for constructor
	 * @param language string representation of current language
	 * @param langDir the test directory for this language
	 */
	private void fillConfusionTableOneLanguage(String language,
			File langDir) {
		for(File file: langDir.listFiles()){
			double p_english = Math.log(naiveBayes.p_english);
			double p_spanish = Math.log(naiveBayes.p_spanish);
			double p_japanese = Math.log(naiveBayes.p_japanese);
			
			// now we iterate over characters and update p_english, p_spanish
			// and p_japanese appropriately
			try{
				// open file
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				
				while(br.ready()){
					// read a character and increment our counter if it is 
					// a valid character (a-z, A-Z, ' ')
					char c = (char) br.read();
					if( String.valueOf(c).matches("[A-Za-z ]")){
						// we have a valid character, update probabilities
						int arrIndex = (int)(c - 'a'); 
						if(c == ' ') arrIndex = 26;
						
						p_english += 
								Math.log(
									naiveBayes.englishConditional[arrIndex]
								);
						p_spanish += 
								Math.log(
									naiveBayes.spanishConditional[arrIndex]
								);
						p_japanese += 
								Math.log(
									naiveBayes.japaneseConditional[arrIndex]
								);
					}
				}
			}
			catch(FileNotFoundException e){
				System.err.println("Failed to Find File: " + file.toString());
			}
			catch(IOException ioe){
				System.err.println(ioe.getMessage());
			}
			
			// now we need to find the largest one, updating the
			// appropriate count
			// ENGLISH
			if(p_english > p_spanish && p_english > p_japanese){
				if(language.equals("english")) this.pEngEng++;
				if(language.equals("spanish")) this.pEngSpa++;
				if(language.equals("japanese")) this.pEngJap++;

			}
			// SPANISH
			else if(p_spanish > p_english && p_spanish > p_japanese){
				if(language.equals("english")) this.pSpaEng++;
				if(language.equals("spanish")) this.pSpaSpa++;
				if(language.equals("japanese")) this.pSpaJap++;
			}
			// JAPANESE
			else if(p_japanese > p_english && p_japanese > p_spanish){
				if(language.equals("english")) this.pJapEng++;
				if(language.equals("spanish")) this.pJapSpa++;
				if(language.equals("japanese")) this.pJapJap++;
			}
		}
	}
	
	/**
	 * String representation of ConfusionMatrix.
	 * 
	 * @return the String representation of ConfusionMatrix
	 */
	public String toString(){
		return String.format(
				"\t\t\ttrue English\ttrue Spanish\ttrue Japanese\n" +
			    "Predicted English\t    %s\t\t     %s\t\t     %s\n" +
			    "Predicted Spanish\t    %s\t\t     %s\t\t     %s\n" +
			    "Predicted Japanese\t    %s\t\t     %s\t\t     %s\n",
			    
				pEngEng, pEngSpa, pEngJap,
				pSpaEng, pSpaSpa, pSpaJap,
				pJapEng, pJapSpa, pJapJap
				);
	}
}
