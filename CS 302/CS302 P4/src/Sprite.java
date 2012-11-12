///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  LevelEditorApp.java
// File:             Sprite.java
// Semester:         Spring 2008
//
// Author:           Christopher Wilcox cwilcox@wisc.edu	
// CS Login:         wilcox
// TA's Name:        Hasti - Lecture 1
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Jacob Backhaus 
//     CS Login:     backhaus
//    TA's Name:     Hasti - Lecture 1
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          (list anyone who helped you write your program)
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * Sprite.java allows the user to create and manipulate sprite objects.
 * 
 * <p>
 * Bugs: No Known Issues
 * 
 * @author Christopher Wilcox, Jacob Backhaus
 */
public class Sprite {
	public static final int BLOCK = 2;
	public static final int FLOOR = 0;
	public static final int GOAL = 5;
	public static final int HERO = 3;
	public static final int OPPONENT = 4;
	public static final int STEAM_BOMB = 7;
	public static final int STEAM_CLOUD = 6;
	public static final int WALL = 1;
	private int type = 0;
	private Location position;

	/**
	 * Create a Sprite with the specified type.
	 * 
	 * @param spriteType
	 *            The Type of Sprite.
	 */
	public Sprite(int spriteType) {
		type = spriteType;
	}

	/**
	 * Change the position of this Sprite to the specified Location.
	 * 
	 * @param newPosition
	 *            The New Position for the Sprite.
	 */
	public void setPosition(Location newPosition) {
		position = newPosition;
	}

	/**
	 * Get the location of this Sprite.
	 * 
	 * @return Returns the position of the Sprite.
	 */
	public Location getPosition() {
		return position;
	}

	/**
	 * Tell the Sprite to draw itself using the Painter class.
	 */
	public void drawSelf() {
		Painter.draw(type, position);
	}

	/**
	 * Get the type of this Sprite.
	 * 
	 * @return Returns the Type of the Sprite.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Move the Sprite as necesary.
	 * 
	 * @param level
	 *            The current level.
	 */
	public void tick(Level level) {

	}

}
