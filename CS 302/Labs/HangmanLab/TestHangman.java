/**
 * Class to test/play the Hangman game
 * @author cs302
 */
public class TestHangman {
    
    public static void main(String [] args) {
        /*
         * It will be easiest to test if you initially do not implement
         * (or comment out) the part of the constructor that randomly 
         * chooses a word to guess.  Instead, temporarily set the word
         * to guess to some chosen value, say, "miscellaneous"
         */
    	Hangman game = new Hangman();
        
        /* To test the constructor, use the toString method
         * to see if the data members are what you expect
         */
        System.out.println(game);
        
        /*
         * Testing gameWon
         
        if (game.gameWon()) 
        	System.out.println("Game should not be won at beginning");
        
        // use mutator method to change the char array data member to
        // contain the characters for the word to guess
        System.out.println(game);
        if (!game.gameWon())
        	System.out.println("Game should be won");
        
        // use mutator method to change char array data member to 
        // contain some but not all of the characters in the word to guess
        System.out.println(game);
        if (game.gameWon()) 
        	System.out.println("Game should not be won");
        */
        
        /*
         * Testing gameLost
        
        game = new Hangman();  // start with a new game
        if (game.gameLost()) 
        	System.out.println("Game should not be lost at beginning");
        
        // use mutator method to change the number of guesses to 3
        System.out.println(game);
        if (game.gameLost())
        	System.out.println("Game should not be lost");
        	
        // use mutator method to change the number of guesses to 7
        System.out.println(game);
        if (!game.gameLost())
        	System.out.println("Game should be lost");  
 
        // use mutator method to change the number of guesses to 10
        System.out.println(game);
        if (!game.gameLost())
        	System.out.println("Game should be lost");
        */  
        
        /*
         * Do similar testing for gameOver
         */
        
        
        /*
         *  Testing handleChar
        
        game = new Hangman();  // start with a new game
        System.out.println(game);
        
        game.handleChar('a');  // check a letter in the word
        System.out.println(game);
        
        game.handleChar('q');  // check a letter not in the word
        System.out.println(game);
        
        game.handleChar('m');  // check for first letter in word
        System.out.println(game);
        
        game.handleChar('l');  // check a letter that appears more than once
        System.out.println(game);
        
        game.handleChar('s');  // check last letter in word
        System.out.println(game);
        
        game.handleChar('x');  // check another letter not in word
        System.out.println(game);
         */
        
    	/* After all the private methods have been tested
    	 * test the playGame method
    	
        game.playGame();
         */
    }
}
