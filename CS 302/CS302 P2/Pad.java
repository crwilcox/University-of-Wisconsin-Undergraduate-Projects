///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  FrogsAndToads.java
// File:             Pad.java
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

/**
 * The Pad Class is used to represent individual lily pads in the pond. Each pad
 *  keeps track of its starting state and its current status (where each state
 *  is VACANT, TOAD, or FROG).
 *
 * Bugs: none known
 *
 * @author       Christopher Wilcox & Jacob Backhaus
 */

public class Pad {
	/**
	 * Symbolic constant representing a lily pad with a frog.
	 */
	public static final int FROG=1;
	/**
	 * Symbolic constant representing a lily pad with a toad.
	 */
	public static final int TOAD=2;
	/**
	 * Symbolic constant representing a vacant lily pad (no toad and no frog).
	 */
	public static final int VACANT=3;

	private int state;
	private int initState;
	private int finalState;
	private String pad;

	/**
	 * Creates a new lily pad with a given initial state 
	 * (the state when the game begins). If the given state is invalid
	 * (not VACANT, TOAD, or FROG) then the constructor assumes that the 
	 * intended status of the lily pad is VACANT.
	 * 
	 * @param startState The Beginning State of the Pad
	 */
	public Pad(int startState){ 
		initState = startState;
		state=startState;
	}
	
	/**
	 * Returns the current sate of the lily pad; 
	 * must be VACANT, TOAD, or FROG.
	 * 
	 * @return  the current state of the lily pad
	 */
	public int getStatus(){
		return state;
	}
	
	/**
	 * Changes the current state of lily pad. I fthe status is invalid
	 * (i.e., not VACANT, TOAD, or FROG), then the setStatus sets the status
	 * of the lily pad to VACANT.
	 * 	
	 * @param status The State of the Lily Pad
	 */
	public void setStatus(int status){
		if (status != FROG && status != TOAD && status != VACANT)
			status = VACANT;
		state = status;
	}
	
	/**
	 * This method restores the lily pad to the state it was in when
	 * originally constructed.
	 */
	public void reset(){
		setStatus(initState);
	}
	
	/**
	 *Returns true if this lily pad is in the correct state 
	 * for the solved puzzle and false otherwise.
	 *  
	 * @return  True if The Current State is the Solved State
	 */
	public boolean isSolved(){
		
		if (initState == FROG)
			finalState = TOAD;
		if (initState == TOAD)
			finalState = FROG;
		if (initState == VACANT)
			finalState = VACANT;
		
		return (state == finalState);	
	}
	

	/**
	 * Returns the string representation of the lily pad. This is used to 
	 * display the lily pad on the console.  It should return "#" for a TOAD
	 * lily pad, "%" for a FROG lily pad, and "O" (i.e., the upper-case letter 
	 * that sounds like "oh") for a VACANT lily pad.
	 */
	public java.lang.String toString(){
		
		if (state==FROG)
			pad= "%";
		if (state == TOAD)
			pad = "#";
		if (state == VACANT)
			pad = "O";
		return pad;
	}
	
	
}
