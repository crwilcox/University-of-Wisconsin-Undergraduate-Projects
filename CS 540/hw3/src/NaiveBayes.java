import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

///////////////////////////////////////////////////////////////////////////////
// Main Class File:  hw3.java
// File:             NaiveBayes.java
// Semester:         Fall 2011
//
// Author:           chris@crwilcox.com
// CS Login:         wilcox
// Lecturer's Name:  Jerry Zhu
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * This class builds and represents a naive bayes classifier
 * 
 * @author Christopher Wilcox
 */
public class NaiveBayes {
	public float p_english;
	public float p_spanish;
	public float p_japanese;

	// These are the conditional probabilitys P(c_i | alphabet letter)
	// 0 is A, 26 is Z, 27 is ' '
	public float[] englishConditional = new float[27];
	public float[] spanishConditional = new float[27];
	public float[] japaneseConditional = new float[27];

	/**
	 * Builds the Naive Bayes Classifier
	 * 
	 * @param trainset_directory The location of the training set files
	 */
	public NaiveBayes(String trainset_directory){
		// Build the Naive Classifier
		// Using all the characters in the training data, build a Naive Bayes 
		// classifier for the three languages.  Implement the classifier using 
		// a log-likelihood formulation of Naive Bayes.
		
		File englishDir = new File(trainset_directory + "/English");
		File japaneseDir = new File(trainset_directory + "/Japanese");
		File spanishDir = new File(trainset_directory + "/Spanish");

		/* COMPUTING PRIOR PROBABILITIES */
		// Count the number of English documents in the training set:
		float nEnglish = englishDir.listFiles().length;
		
		// Count the number of Spanish documents in the training set:
		float nSpanish = spanishDir.listFiles().length;
		
		// Count the number of Japanese documents in the training set:
		float nJapanese = japaneseDir.listFiles().length;
		
		// Compute the total number of training documents:
		float nTotal = nEnglish + nSpanish + nJapanese;
		
		// Compute the prior probability for English:
		p_english = nEnglish / nTotal;
		// Compute the prior probability for Spanish:
		p_spanish = nSpanish / nTotal;
		// Compute the prior probability for Japanese:
		p_japanese = nJapanese / nTotal;

		/* COMPUTING CONDITIONAL LIKELIHOODS */
		// ENGLISH
		fillConditionalProb(englishConditional, englishDir);
		// SPANISH
		fillConditionalProb(spanishConditional, spanishDir);
		// JAPANESE
		fillConditionalProb(japaneseConditional, japaneseDir);
		
		/* FROM PROBABILITIES TO LOG PROBABILITIES */
		// Convert all probabilities to log probabilities to avoid underflow 
		// problems. Use the natural logarithm (log(x) in Java).  Apply the log 
		// function to all probabilities
	}
	
	/**
	 * Fills the conditional probabilities for each language
	 * @param conditionalTable the table to store these probabilities
	 * @param trainDir the directory containing the samples
	 */
	private void fillConditionalProb(	float[] conditionalTable,
										File trainDir) 
	{
		// Let nCharLanguage be the total number of characters (including 
		// multiple occurrences of the same unique character, including spaces) 
		// contained in all training documents, 
		// For each of the 27 unique characters, ci, compute conditional prob:
		// P(ci | Language) = countLanguage(ci) / nCharLanguage,
		// where countLetter(ci) is the number of times character ci occurs 
		// in all documents in the training set.   
		
		// GET OVERALL COUNT OF ALL LETTERS IN THIS TRAINING DIRECTORY
		float nCharLanguage = (float) getTotalLetters(trainDir);
				
		// FILL IN THE CONDITIONAL TABLE
		for(int i = 0; i < 26; i++){
			char currentLetter = (char) ('a' + i);
			conditionalTable[i] = 
					countLetter(currentLetter, trainDir) / nCharLanguage;
		}
		conditionalTable[26] = 
				countLetter(' ', trainDir) / nCharLanguage;
	}

	/**
	 * Gets the total letter count of all files in a directory
	 * @param trainDir directory containing samples
	 * @return a count of total letters
	 */
	private int getTotalLetters(File trainDir) {
		int totalCharacterCount = 0;
		for(File file : trainDir.listFiles()){
			try{
				// open file
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				
				while(br.ready()){
					// read a character and increment our counter if it is 
					// a valid character (a-z, A-Z, ' ')
					char c = (char) br.read();
					if(String.valueOf(c).matches("[A-Za-z ]")){
						totalCharacterCount++;
					}
				}
			}
			catch(FileNotFoundException e){
				System.err.println("Failed to Find File: " + file.toString());
			}
			catch(IOException ioe){
				System.err.println(ioe.getMessage());
			}
		}
		
		return totalCharacterCount;
	}

	/**
	 * Count instances of a specific character occurring across all of the
	 * test files
	 * @param c character to search for
	 * @param trainDir directory containing the training files
	 * @return count representing occurrences of a specific character
	 */
	private int countLetter(char c, File trainDir) {
		int totalCharacterCount = 0;
		for(File file : trainDir.listFiles()){
			try{
				// open file
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				
				while(br.ready()){
					// read a character and increment our counter
					// if this character matches the one we are looking for
					if((char)br.read() == c){
						totalCharacterCount++;
					}
				}
			}
			catch(FileNotFoundException e){
				System.err.println("Failed to Find File: " + file.toString());
			}
			catch(IOException ioe){
				System.err.println(ioe.getMessage());
			}
		}
		
		return totalCharacterCount;
	}

	/**
	 * String representation of NaiveBayes.
	 * 
	 * @return the String representation of NaiveBayes Classifier
	 */
	public String toString(){
		// Print P(English), P(Spanish) and P(Japanese), as well as the 
		// conditional probabilitiesP(c| English), P(c | Spanish), 
		// and P(c | Japanese) for all 27 characters c
		/* PRIOR */
		String returnString = String.format(
				"P(English) = %s\n"  +
				"P(Spanish) = %s\n"  +
				"P(Japanese) = %s\n" +
				"\n",
				Float.toString(p_english), 
				Float.toString(p_spanish), 
				Float.toString(p_japanese) 
				);
		
		/* ENGLISH CONDITIONAL */
		returnString += 
				fillToStringConditional(englishConditional, "English");
		/* SPANISH CONDITIONAL */
		returnString += 
				fillToStringConditional(spanishConditional, "Spanish");
		/* JAPANESE CONDITIONAL */
		returnString += 
				fillToStringConditional(japaneseConditional, "Japanese");
		
		return returnString;
	}
	
	/**
	 * Helper function for toString.  This helps to fill the Probabilities
	 * of specific letters based on the language
	 * 
	 * @param probabilityTable the corresponding array for this language
	 * @param conditionedOn a string representing the language this is
	 *                      is conditioned on
	 * @return the addition to toString
	 */
	private String fillToStringConditional(	float[] probabilityTable, 
											String conditionedOn)
	{
		String returnString = "";
		for(int i = 0; i < 26; i++){
			char currentLetter = (char) ('a' + i);
			returnString += String.format(
					"P(%c|%s) = %s\n",
					currentLetter, 
					conditionedOn, 				
					Float.toString(probabilityTable[i]) 
					);
		}
		returnString += 
				String.format(
						"P(%c|%s) = %s\n\n",
						' ', 
						conditionedOn, 				
						Float.toString(probabilityTable[26]) 
						);
		
		return returnString;
	}
}
