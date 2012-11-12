///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SteamApp.java
// File:             Opponent.java
// Semester:         Spring 2008
//
// Author:           Christopher Wilcox cwilcox@wisc.edu
// CS Login:         wilcox
// TA's Name:        Hasti: Lab Section 316
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Jacob Backhaus
//     CS Login:     backhaus
//    TA's Name:     Hasti: Lab Section 306
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          (list anyone who helped you write your program)
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * This class represents the Opponents to the user. They move around randomly.
 * If the user touches an opponent, the game is over and the user loses.
 * Opponents may be destroyed by the SteamBombs a user my drop.
 * 
 * @author Christopher Wilcox, Jacob Backhaus
 */
public class Opponent extends MovableSprite {
	private static int waitDuration = (int) (Math.random() * 80 + 35);
	private static final int IMPASSABLE = 0;
	private static final int DAMAGING = 1;
	private static final int SAFE = 2;

	/**
	 * Create a new Opponent
	 */
	public Opponent() {
		super(OPPONENT, waitDuration);
	}

	/**
	 * Choose a direction at random and attempt to move that way
	 * 
	 * @param level -
	 *            the Level to interact with
	 */
	public void performAction(Level level) {
		int direction = (int) (4 * Math.random());
		if (direction == 0)
			direction = Location.LEFT;
		else if (direction == 1)
			direction = Location.UP;
		else if (direction == 2)
			direction = Location.RIGHT;
		else if (direction == 3)
			direction = Location.DOWN;

		tryToMove(direction, level);
	}

	/**
	 * This specifies what happens to the Opponent if it tries to step on this
	 * particular type of Sprite. It has three options: it could be unable to
	 * move there, it could move there safely, or it could remove the Sprite
	 * from the game. It should return IMPASSABLE, SAFE, or DAMAGING
	 * appropriately. An Opponent can move onto FLOOR and HERO tiles, but BLOCK,
	 * WALL, GOAL and STEAM_BOMBs are immpassible. An Opponent should be deleted
	 * if it atempts to step on a STEAM_CLOUD
	 * 
	 * @param -
	 *            type - the type of the Sprite we are trying to move onto
	 * 
	 * @return s - A Value Representing IMPASSABLE, SAFE, or DAMAGING
	 */
	public int statusOfDestination(int type) {
		int s = IMPASSABLE;
		if (type == FLOOR || type == HERO) {
			s = SAFE;

		} else if (type == BLOCK || type == WALL || type == GOAL
				|| type == STEAM_BOMB) {
			s = IMPASSABLE;
		} else if (type == STEAM_CLOUD) {
			s = DAMAGING;
		}
		return s;
	}

}
