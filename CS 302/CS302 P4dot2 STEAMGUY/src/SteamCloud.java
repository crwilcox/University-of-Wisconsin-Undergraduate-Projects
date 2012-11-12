///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SteamApp.java
// File:             SteamCloud.java
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
 * This class represents a SteamCloud. It is a type of ActionSprite, so it is
 * also a type of Sprite. The SteamCloud appears when a bomb goes off, stays for
 * awhile and then disapates. That means the action for this ActionSprite is
 * deleting itself.
 * 
 * @author Christopher Wilcox, Jacob Backhaus
 */
public class SteamCloud extends ActionSprite {

	private static final int CLOUD_TIME = 55;

	/**
	 * Create a new SteamCloud object. It should start waiting immediately.
	 */
	public SteamCloud() {
		super(Sprite.STEAM_CLOUD, CLOUD_TIME);
	}

	/**
	 * Deletes this SteamCloud from the Level
	 * 
	 * @param theLevel -
	 *            the Level to delete the SteamCloud from
	 */
	public void performAction(Level theLevel) {

		theLevel.removeSprite(this);
	}
}
