///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  FrogsAndToads.java
// File:             Game.java
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


public class Game {
	private int size;
	private int vacRow;
	private int vacColumn;
	private Pond instance;

	/**
	 * Creates a new game with the given size 
	 * (the size is both the width and the height of the grid of lily pads).
	 * 
	 * @param size The Length of Each Side of The Pond.
	 */
	public Game(int size) {
		instance=new Pond(size);
		this.size = size;
		vacColumn = ((size / 2) + 1);
		vacRow = ((size / 2) + 1);
	}
	/**
	 * Returns the Pond object associated with this Game object.
	 * 
	 * @return This Games Pond Object..
	 */
	public Pond getPond() {
		return instance;

	}

	/**
	 *Moves the amphibian at given location to the vacant lily pad.
	 * 
	 * @param row The current row of the Amphibian.
	 * @param column The current column of the Amphibian.
	 */
	public void moveAmphibian(int row, int column) {
		Pad occupiedPad = instance.getPad(row, column);
		int typePadOccupied = occupiedPad.getStatus();

		Pad emptyPad = instance.getPad(vacRow, vacColumn);
		int typePadEmpty = emptyPad.getStatus();

		vacRow = row;
		vacColumn = column;

		emptyPad.setStatus(typePadOccupied);
		occupiedPad.setStatus(typePadEmpty);

	}

	/**
	 * Indicates whether or not a legal move is still possible.
	 * 
	 * @return True if Move is Still Possible and False Otherwise.
	 */
	public boolean moveStillPossible() {
		if(validMove(vacRow+2, vacColumn)
				|| validMove(vacRow+1, vacColumn)
				|| validMove(vacRow-2, vacColumn)
				|| validMove((vacRow-1), vacColumn)
				|| validMove(vacRow, vacColumn+2)
				|| validMove(vacRow, vacColumn+1)
				|| validMove(vacRow, vacColumn-2)
				|| validMove(vacRow, vacColumn-1))
			return true;
		else 
			return false;
	}

	/**
	 * Prints the game board 
	 *
	 */
	public void print() {
		instance.printPond();
	}

	/**
	 * Indicates whether or not the puzzle has been solved.
	 * 
	 * @return True if Puzzle Solved, False Otherwise.
	 */
	public boolean solved() {
		boolean answer = instance.isSolved();
		return answer;
	}

	/**
	 * Returns true if the given coordinates represent a Pad that has an 
	 * amphibian that can make a legal move. 
	 * 
	 * @param row The Current Row of the Amphibian
	 * 
	 * @param column The Current Column of the Amphibian
	 * 
	 * @return true if the given coordinates represent a Pad that has an 
	 * amphibian that can make a legal move.
	 */
	public boolean validMove(int row, int column) {
		if (row < 1 || row > size || column < 1 || column > size)
			return false;
		
		
		Pad refPad = instance.getPad(row, column);
		int typePad = refPad.getStatus();

		if (typePad == Pad.VACANT)
			return false;

		if (typePad == Pad.FROG) {
			if ((row + 2 <= size)
					&&(instance.getPad(row + 1, column).getStatus() 
							== Pad.TOAD)
					&&(instance.getPad(row + 2, column).getStatus() 
							== Pad.VACANT)
			)
				return true;
			if ((row + 1 <= size)
					&&(instance.getPad(row + 1, column).getStatus() 
							== Pad.VACANT))
				return true;
			
			if ((column + 1 <= size)
					&&(instance.getPad(row, column + 1).getStatus() 
							== Pad.VACANT))
				return true;
			if ((column + 2 <= size)
					&&(instance.getPad(row, column + 1).getStatus() 
							== Pad.TOAD)
					&& (instance.getPad(row, column + 2).getStatus() 
							== Pad.VACANT))
				return true;
		}

		if (typePad == Pad.TOAD) {
			if ((row - 2 >= 1)
					&&(instance.getPad(row - 1, column).getStatus() 
							== Pad.FROG)
					&& (instance.getPad(row - 2, column).getStatus() 
							== Pad.VACANT))
				return true;
			if ((row - 1 >= 1)
					&&(instance.getPad((row - 1), column).getStatus() 
							== Pad.VACANT)
					)
				return true;
			if ((column - 1 >= 1)
					&&(instance.getPad(row, (column - 1)).getStatus() 
							== Pad.VACANT)
					)
				return true;
			if ((column - 2 >= 1)
					&&(instance.getPad(row,(column-1)).getStatus()== Pad.FROG)
					&&(instance.getPad(row,(column-2)).getStatus()==Pad.VACANT))
				return true;
		}
			return false;
	}
}
