import java.util.Random;
import java.util.Scanner;

public class Hangman {
	

    // DECLARE DATA MEMBERS HERE AND INITIALIZE THE ARRAY OF WORDS

private static String[] words = {"two", "three", "four", "bus", "farva", "hangman", "class"};

private String chosenWord;

private char[] correct;

private char[] incorrect = new char[7];

private int guesses=0;




public String toString(){
	
}

    /**
     * constructor
     */
    public Hangman() {
    	Random randomWord=new Random();
    	int wordNumber= randomWord.nextInt(words.length);
    	chosenWord= words[wordNumber];
    	correct = new char[chosenWord.length()]; 
    	for(int x=0; x<chosenWord.length(); x++){
    		correct[x]='_';}
    }
    
    //////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    //////////////////////////////////////////////////////////////////////

    /**
     * playGame
     *
     * Play one game of hangman until
     *  the user wins (guesses all of the letters in the secret word)
     *  or loses (guesses 7 incorrect letters):
     *     draw the current Hangman
     *     print the correct guesses so far
     *     print the incorrect guesses so far
     *     ask the user to guess a letter
     *     read and process the guess
     */
    public void playGame() {
      // FILL IN CODE HERE
    	Scanner user= new Scanner(System.in);
    	
    	
    }
    
    //////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    //////////////////////////////////////////////////////////////////////

    /**
     * gameWon
     *
     * Return true if the user has won the game;
     * otherwise, return false.
     *
     * @return true if the user has won, false otherwise
     */
     private boolean gameWon() {
     	for(int x=0; x<chosenWord.length(); x++){
     		if (correct[x]=='_'){
     			return false;
     		}
     	}
     	return true; 
     	}
    
    /**
     * gameLost
     *
     * Return true if the user has lost the game;
     * otherwise, return false.
     *
     * @return true if the user has lost, false otherwise
     */
     private boolean gameLost() {
    	 if(guesses>=7) return true;
    	 return false;  
     }
    
     
    /**
     * gameOver
     *
     * Return true if the user has either won or lost the game;
     * otherwise, return false.
     *
     * @return true if the user has won or lost, false otherwise
     */
     private boolean gameOver() {
    	return(gameWon() || gameLost());
     }
    
    /**
     * handleGuess
     *
     * If the guessed letter (parameter ch) is in the secret word
     * then add it to the array of correct guesses and tell the user
     *      that the guess was correct;
     * otherwise, add the guessed letter to the array of incorrect guesses
     *      and tell the user that the guess was wrong.
     *
     * @param ch the guessed letter
     */
    private void handleGuess(char ch) {
      // FILL IN CODE HERE
    	for(int x=0; x<chosenWord.length(); x++){
    		if(chosenWord.charAt(x)==ch){
    			correct[x]=ch;}
    	}
    }
    
    /**
     * printHangman
     *
     * Print the Hangman that corresponds to the given number of
     * wrong guesses so far.
     *
     * @param numWrong number of wrong guesses so far
     */
     private void printHangman(int numWrong) {
       System.out.println("  ____");
       System.out.println("  |  |");

       if (numWrong > 0) {
           System.out.println("  |  O");
           if (numWrong == 2) {
             System.out.println("  |  |");
           } else if (numWrong == 3) {
             System.out.println("  | \\|");
           } else if (numWrong >= 4) {
             System.out.println("  | \\|/");
           }
           if (numWrong == 5) {
             System.out.println("  | /");
           } else if (numWrong == 6) {
             System.out.println("  | / \\");
           }
       }
       for (int k = 6 - numWrong; k > 0; k--) {
           System.out.println("  |");
       }
       System.out.println("__|__");
       System.out.println();
     }
}

