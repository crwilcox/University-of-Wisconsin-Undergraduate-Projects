///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  FrogsAndToads.java
// File:             GameTester.java
// Semester:         Spring 2008
//
// Author:           Christopher Wilcox  -  cwilcox@wisc.edu
// CS Login:         wilcox
// TA's Name:        Hasti Lec 1 - Lab 316
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Jacob Backhaus
//     CS Login:     backhaus
//    TA's Name:     Hasti Lec 1 - Lab 306
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          (list anyone who helped you write your program)
//////////////////////////// 80 columns wide //////////////////////////////////


public class GameTester {
	public static void main (String[] args) {
		
		System.out.println("*** This is a tester for the Game class.***\n");
		int failedTests = 0;
		
		/* game1 : 3
		 * game2 : 5
		 * game3 : 7
		 */
		Game game1 = new Game(3);
		Game game2 = new Game(5);
		Game game3 = new Game(7);
		
		//*******Gives Visual Representation of Games*******
		System.out.println("Below is a representation of game1: Size=3");
		game1.print();
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Below is a representation of game2: Size=5");
		game2.print();
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Below is a representation of game3: Size=7");
		game3.print();
		System.out.println(" ");
		
		//******* Testing validMove() method*********
		//Tests for Out of Boundary
		if(game1.validMove(5,1)== true){
			System.out.println("Boundary Error #1");
			failedTests++;}
		if(game1.validMove(1,5)== true){
			System.out.println("Boundary Error #2");
			failedTests++;}
		if(game1.validMove(5,5)== true){
			System.out.println("Boundary Error #3");
			failedTests++;}
		if(game1.validMove(0,0)== true){
			System.out.println("Boundary Error #4");
			failedTests++;}
		if(game1.validMove(3,3)== true){
			System.out.println("Boundary Error #5");
			failedTests++;}

		
		//*******Uses game1**************************
		if(game1.validMove(1,1)== true){
			System.out.println("Test No. 1-1- Valid Move - Failed");
			failedTests++;}
		
		if(game1.validMove(1,3)== true){
			System.out.println("Test No. 1-2- Valid Move - Failed");
			failedTests++;}
		
		if(game1.validMove(3,1)== true){
			System.out.println("Test No. 1-3- Valid Move - Failed");
			failedTests++;}

		if(game1.validMove(3,3)== true){
			System.out.println("Test No. 1-4- Valid Move - Failed");
			failedTests++;}
		
		if(game1.validMove(2,2)== true){
			System.out.println("Test No. 1-5- Valid Move - Failed");
			failedTests++;}

		if(game1.validMove(2,1) == false){
			System.out.println("Test No. 1-6- Valid Move - Failed");
			failedTests++;}

		if(game1.validMove(1,2)== false){
			System.out.println("Test No. 1-7- Valid Move - Failed");
			failedTests++;}

		if(game1.validMove(2,3)== false){
			System.out.println("Test No. 1-8- Valid Move - Failed");
			failedTests++;}

		if(game1.validMove(3,2)== false){
			System.out.println("Test No. 1-9- Valid Move - Failed");
			failedTests++;}
		
		//*******Uses game2************************
		if(game2.validMove(1,1)== true){
			System.out.println("Test No. 2-1- Valid Move - Failed");
			failedTests++;}
		
		if(game2.validMove(1,5)== true){
			System.out.println("Test No. 2-2- Valid Move - Failed");
			failedTests++;}
		
		if(game2.validMove(5,1)== true){
			System.out.println("Test No. 2-3- Valid Move - Failed");
			failedTests++;}
		
		if(game2.validMove(5,5)== true){
			System.out.println("Test No. 2-4- Valid Move - Failed");
			failedTests++;}
		
		if(game2.validMove(1,2)== true){
			System.out.println("Test No. 2-5- Valid Move - Failed");
			failedTests++;}

		if(game2.validMove(2,3) == false){
			System.out.println("Test No. 2-6- Valid Move - Failed");
			failedTests++;}

		if(game2.validMove(3,2)== false){
			System.out.println("Test No. 2-7- Valid Move - Failed");
			failedTests++;}

		if(game2.validMove(4,3)== false){
			System.out.println("Test No. 2-8- Valid Move - Failed");
			failedTests++;}

		if(game2.validMove(3,4)== false){
			System.out.println("Test No. 2-9- Valid Move - Failed");
			failedTests++;}
		

		//*******Uses game3**************************		
		if(game3.validMove(1,1)== true){
			System.out.println("Test No. 3-1- Valid Move - Failed");
			failedTests++;}

		if(game3.validMove(1,7)== true){
			System.out.println("Test No. 3-2- Valid Move - Failed");
			failedTests++;}	
		
		if(game3.validMove(7,1)== true){
			System.out.println("Test No. 3-3- Valid Move - Failed");
			failedTests++;}
		
		if(game3.validMove(7,7)== true){
			System.out.println("Test No. 3-4- Valid Move - Failed");
			failedTests++;}
		
		if(game3.validMove(6,6)== true){
			System.out.println("Test No. 3-5- Valid Move - Failed");
			failedTests++;}
		
		if(game3.validMove(4,3) == false){
			System.out.println("Test No. 3-6- Valid Move - Failed");
			failedTests++;}
		
		if(game3.validMove(5,4)== false){
			System.out.println("Test No. 3-7- Valid Move - Failed");
			failedTests++;}
		
		if(game3.validMove(4,5)== false){
			System.out.println("Test No. 3-8- Valid Move - Failed");
			failedTests++;}
		
		if(game3.validMove(3,4)== false){
			System.out.println("Test No. 3-9- Valid Move - Failed");
			failedTests++;}
		
		//******* Testing moveAmphibian() & moveStillPossible()method*********
		game2.moveAmphibian(2,3);
		System.out.print("Below is a representation of the board after");
		System.out.println(" a run of moveAmphibian (2,3)");
		game2.print();
		if (game2.moveStillPossible()!= true){
			System.out.println("moveStillPossible Failed");
			failedTests++;}
		
		game2.moveAmphibian(2,4);
		System.out.print("Below is a representation of the board after");
		System.out.println(" a run of moveAmphibian (2,4)");
		game2.print();
		if (game2.moveStillPossible()!= true){
			System.out.println("moveStillPossible Failed");
			failedTests++;}
		
		game2.moveAmphibian(2,2);
		System.out.print("Below is a representation of the board after");
		System.out.println(" a run of moveAmphibian (2,2)");
		game2.print();
		if (game2.moveStillPossible()!= true){
			System.out.println("moveStillPossible Failed");
			failedTests++;}
		
		game2.moveAmphibian(2,3);
		System.out.print("Below is a representation of the board after");
		System.out.println(" a run of moveAmphibian (2,3)");
		game2.print();
		if (game2.moveStillPossible()!= true){
			System.out.println("moveStillPossible Failed");
			failedTests++;}
		
		game2.moveAmphibian(2,5);
		System.out.print("Below is a representation of the board after");
		System.out.println(" a run of moveAmphibian (2,5)");
		game2.print();
		if (game2.moveStillPossible()!= true){
			System.out.println("moveStillPossible Failed");
			failedTests++;}
		
		game2.moveAmphibian(3,5);
		System.out.print("Below is a representation of the board after");
		System.out.println(" a run of moveAmphibian (3,5)");
		game2.print();
		if (game2.moveStillPossible()!= true){
			System.out.println("moveStillPossible Failed");
			failedTests++;}
		
		game2.moveAmphibian(4,5);
		System.out.print("Below is a representation of the board after");
		System.out.println(" a run of moveAmphibian (4,5)");
		game2.print();
		if (game2.moveStillPossible()!= true){
			System.out.println("moveStillPossible Failed");
			failedTests++;}
			
		
		game2.moveAmphibian(5,5);
		System.out.print("Below is a representation of the board after");
		System.out.println(" a run of moveAmphibian (5,5)");
		game2.print();
		if (game2.moveStillPossible()){
			System.out.println("moveStillPossible Failed: No Moves Left");
			failedTests++;}
		
		
		

		
		//******* Testing public boolean solved() and moveStillPossible()*******
		
		game1.moveAmphibian(2,1);
		if(game1.moveStillPossible() != true){
			System.out.println("Test Failed: solved() and moveStillPossible()");
			failedTests++;}
		
		game1.moveAmphibian(2,3);
		if(game1.moveStillPossible() != true){
			System.out.println("Test Failed: solved() and moveStillPossible()");
			failedTests++;}
		
		game1.moveAmphibian(2,2);
		if(game1.moveStillPossible() != true){
			System.out.println("Test Failed: solved() and moveStillPossible()");
			failedTests++;}
		
		game1.moveAmphibian(1,2);
		if(game1.moveStillPossible() != true){
			System.out.println("Test Failed: solved() and moveStillPossible()");
			failedTests++;}
		
		game1.moveAmphibian(1,1);
		if(game1.moveStillPossible() != true){
			System.out.println("Test Failed: solved() and moveStillPossible()");
			failedTests++;}
		
		game1.moveAmphibian(1,3);
		if(game1.moveStillPossible() != true){
			System.out.println("Test Failed: solved() and moveStillPossible()");
			failedTests++;}
		
		game1.moveAmphibian(1,2);
		if(game1.moveStillPossible() != true){
			System.out.println("Test Failed: solved() and moveStillPossible()");
			failedTests++;}
		
		game1.moveAmphibian(3,2);
		if(game1.moveStillPossible() != true){
			System.out.println("Test Failed: solved() and moveStillPossible()");
			failedTests++;}
		
		game1.moveAmphibian(3,1);
		if(game1.moveStillPossible() != true){
			System.out.println("Test Failed: solved() and moveStillPossible()");
			failedTests++;}
		
		game1.moveAmphibian(3,3);
		if(game1.moveStillPossible() != true){
			System.out.println("Test Failed: solved() and moveStillPossible()");
			failedTests++;}
		
		game1.moveAmphibian(3,2);
		if(game1.moveStillPossible() != true){
			System.out.println("Test Failed: solved() and moveStillPossible()");
			failedTests++;}
		
		game1.moveAmphibian(2,2);
		if (game1.solved() != true){
			System.out.println("Solve Test Failed");
			failedTests++;
		}
		
		if (failedTests>0){
			System.out.println("Total Number of Failed Tests:  "+ failedTests);}
	
		else	
			System.out.println("Congratulations! All Tests Passed...");
	}		
}
