///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SteamApp.java
// File:             Hero.java
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
 * This class represents the human player's character. It is a MovableSprite
 * that is controlled by the key board. The movement is controlled by the w, a,
 * s, and d keys. Bombs may be placed with the space bar.
 * 
 * @author Christopher Wilcox, Jacob Backhaus
 */
public class Hero extends MovableSprite {

	public static final int WAITTIME = 5;
	boolean drop = false;

	/**
	 * Creates a Hero object.
	 */
	public Hero() {
		super(HERO, WAITTIME);
	}

	/**
	 * Attempt to move in the specified direction. If the Hero is planning to
	 * drop a bomb and the move succeeds, it should do so now.
	 * 
	 * @param direction -
	 *            The direction to attempt to move in.
	 * @param theLevel -
	 *            The level to interact with
	 * 
	 * @return boolean - true if the Hero was able to move
	 */
	public boolean tryToMove(int direction, Level theLevel) {
		Location temp = getPosition();
		super.tryToMove(direction, theLevel);
		if (drop == true) {
			SteamBomb tempBomb = new SteamBomb();
			theLevel.placeSprite(tempBomb, temp);
			drop = false;
		}
		return (temp != getPosition());
	}

	/**
	 * This specifies what happens to the Hero if it tries to step on this
	 * particular type of Sprite. It has three options: it could be unable to
	 * move there, it could move there safely, or it could remove the Sprite
	 * from the game. It should return IMPASSABLE, SAFE, or DAMAGING
	 * appropriately. A Hero can move onto FLOOR and GOAL tiles, but BLOCK, WALL
	 * and STEAM_BOMBs are immpassable. A Hero should be deleted if it attempts
	 * to step on a STEAM_CLOUD or an OPPONENT
	 * 
	 * @param type -
	 *            the type of the Sprite we are trying to move onto
	 * 
	 * @return s - a variable representing IMPASSABLE, SAFE, or DAMAGING
	 */
	public int statusOfDestination(int type) {
		int s = IMPASSABLE;
		if (type == FLOOR || type == GOAL) {
			s = SAFE;

		} else if (type == BLOCK || type == WALL || type == STEAM_BOMB) {
			s = IMPASSABLE;
		} else if (type == STEAM_CLOUD || type == OPPONENT) {
			s = DAMAGING;
		}
		return s;
	}

	/**
	 * Pressing w, a, s, and d, should move the hero up, left, down or right
	 * respectively. Pressing the space bar should let the Hero know it should
	 * drop a bomb when it leaves the square (it does not actually drop the
	 * bomb)
	 * 
	 * @param keyChar -
	 *            The char representing the key pressed
	 * @param level -
	 *            The level to move around in
	 */
	public void key(char keyChar, Level level) {
		if (keyChar == 'w')
			tryToMove(Location.UP, level);
		if (keyChar == 'a')
			tryToMove(Location.LEFT, level);
		if (keyChar == 's')
			tryToMove(Location.DOWN, level);
		if (keyChar == 'd')
			tryToMove(Location.RIGHT, level);
		if (keyChar == ' ')
			drop = true;

	}

	/**
	 * Since the movement is controlled by the keys, the perform action does not
	 * need to do anything.
	 * 
	 * @param level -
	 *            The Level to ignore
	 */
	public void performAction(Level level) {
		// Since the movement is controlled by the keys,
		// the perform action does not need to do anything.
	}

}
