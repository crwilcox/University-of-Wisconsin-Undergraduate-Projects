///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  LevelEditorApp.java
// File:             Location.java
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
 * Location.java allows a user to give objects locations.
 * 
 * <p>
 * Bugs: No Known Issues
 * 
 * @author Christopher Wilcox, Jacob Backhaus
 */
public class Location {
	public static final int UP = 1;
	public static final int DOWN = 3;
	public static final int RIGHT = 2;
	public static final int LEFT = 0;
	private int xCoord;
	private int yCoord;

	/**
	 * This creates a Location at the position specified.
	 * 
	 * @param x
	 *            User Provided x Coordinate.
	 * @param y
	 *            User Provided y Coordinate.
	 */
	public Location(int x, int y) {
		xCoord = x;
		yCoord = y;
	}

	/**
	 * Get the x coordinate.
	 * 
	 * @return returns the x coordinate.
	 */
	public int x() {
		return xCoord;
	}

	/**
	 * Get the y coordinate.
	 * 
	 * @return returns the y coordinate.
	 */
	public int y() {
		return yCoord;
	}

	/**
	 * Returns a brand new Location object that is adjacent to baseLocation in
	 * the direction specified by the direction parameter.
	 * 
	 * @param baseLocation
	 *            Starting Location.
	 * @param direction
	 *            The direction in which movement is happening.
	 * @return Returns the Relative Location.
	 */
	public static Location getRelativeLocation(Location baseLocation,
			int direction) {
		int y = 0;
		int x = 0;
		
		if (direction == UP) {
			y = baseLocation.y() + 1;
			x = baseLocation.x();
		} else if (direction == DOWN) {
			y = baseLocation.y() - 1;
			x = baseLocation.x();
		} else if (direction == LEFT) {
			y = baseLocation.y();
			x = baseLocation.x() - 1;
		} else if (direction == RIGHT) {
			y = baseLocation.y();
			x = baseLocation.x() + 1;
		}
		Location relLocation = new Location(x, y);
		
		return relLocation;
	}
}
