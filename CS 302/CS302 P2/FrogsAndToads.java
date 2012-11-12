///////////////////////////////////////////////////////////////////////////////
// ALL STUDENTS COMPLETE THESE SECTIONS
// Title: FrogsAndToads
// Files: Game.java, GameTester.java, Pad.java, P2.jar
// Semester: Spring 2008
//
// Author: Christopher Wilcox cwilcox@wisc.edu
// CS Login: wilcox
// TA's Name: Hasti Lec 1: Lab 316
//
// PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner: Jacob Backhaus
// CS Login: backhaus
// TA's Name: Hasti Lec 1: Lab 306
//
// STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits: (list anyone who helped you write your program)
//////////////////////////// 80 columns wide //////////////////////////////////
/**
* The FrogsAndToads class is the Frogs and Toads application class. This class
* creates an instance of the Game and handles all the user input for
* the game.
*/
import java.util.Scanner;
public class FrogsAndToads {
	/**
	* Entry point for playing Frogs and Toads
	*/
	public static void main (String[] args) {
	// Declare and initialize local variables,
		int gameSize;
		int ampRow;
		int ampColumn;
	// Create necessary objects and show greeting texts.
		
	// Get game size and verify it is legal.
		Scanner size = new Scanner(System.in);
		System.out.print("What size would you like to play ");
		System.out.println("(an odd number from 3 to 11)?");
		gameSize=size.nextInt();
		if ((gameSize%2 != 1)&&(gameSize>11))
		{
			System.out.print("The Value You Entered Is Even and Out Of");
			System.out.println(" Range. Please Use An Odd Value From 3 to 11");
			System.exit(1);//1 is used to indicate an abnormal program exit
		}
		if (gameSize%2 != 1)
		{
			System.out.println("The Value You Entered Is Even. Use Only Odds");
			System.exit(1);//1 is used to indicate an abnormal program exit
		}
		if (gameSize>11)
		{
			System.out.println("The Board You Entered Is Out Of Range");
			System.exit(1);//1 is used to indicate an abnormal program exit
		}

	// Create game object
		Game game = new Game(gameSize);
		
	//The statements inside this while loop are repeated
	//until no moves are possible anymore.
		
	while(game.moveStillPossible()) {
		
	//display board
	game.print();
	
	//prompt user for next move
	Scanner row = new Scanner(System.in);
	System.out.print("In which row is the amphibian you want to move?");
	ampRow=row.nextInt();
	Scanner column = new Scanner(System.in);
	System.out.print("In which column is the amphibian you want to move?");
	ampColumn=column.nextInt();
	
	//process user input
	if (game.validMove(ampRow, ampColumn)){
	game.moveAmphibian(ampRow, ampColumn);}
	else{
		System.out.println("Invalid Move - Please Try Again");}
	
	
	}
	//print board
	game.print();
	//print closing message
	if (game.solved()){
	System.out.println("Congratulations! You Have Beat the Game!");
	System.out.println("YOU WIN!!!");
	}
	else
	{
		System.out.println("You Have Exhausted All Remaining Moves");
		System.out.println("You Lose The Game...");
	}
	}
}
