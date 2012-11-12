///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SteamApp.java
// File:             SteamGame.java
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
 * This class is responsible for handling keyboard input, drawing the Level,
 * telling the Level to move its objects and figuring out when the game is over.
 * 
 * @author Christopher Wilcox, Jacob Backhaus
 */
public class SteamGame extends java.lang.Object implements WindowObject {
	private Level level;
	private Hero myHero;
	private int numberOfGoals;
	private boolean gameLost = false;
	private boolean gameWon = false;

	/**
	 * Create a new SteamGame. It should hold a reference to the Level specified
	 * by fileName plus a reference to the Hero from that Level. Obviously, a
	 * brand new game should not yet be won or lost. If the level fails to load,
	 * an IOException with a descriptive error message should be thrown.
	 * 
	 * @param fileName -
	 *            A String representing the name of the file from which to load
	 * @throws java.io.IOException
	 */
	public SteamGame(java.lang.String fileName) throws java.io.IOException {
		myHero = new Hero();

		level = LevelLoader.loadLevel(fileName, myHero);

		numberOfGoals = level.numberOf(Sprite.GOAL);

	}

	/**
	 * Draw everything that needs to be drawn. This should also display any
	 * end-game text
	 */
	public void draw() {
		if (gameWon) {
			level.drawSelf();
			Painter.drawText("You Won!");
		} else if (gameLost) {
			level.drawSelf();
			Painter.drawText("You Lost...");
		} else
			level.drawSelf();

	}

	/**
	 * If the game has not ended, pressing w, a, s, and d, should move the hero
	 * up, right, down or right respectively. Pressing the space bar should let
	 * the Hero know it should drop a bomb when it leaves the square. If the
	 * game is over, the keystroke should be ignored.
	 * 
	 * @param keyChar -
	 *            The char representing the key pressed
	 */
	public void key(char keyChar) {

		if (!(gameOver()))

			myHero.key(keyChar, level);

	}

	/**
	 * This method does not need to do anything. The game is controlled by
	 * keyboard input.
	 * 
	 * @param clickLocation -
	 *            The Location of a Click
	 */
	public void mouse(Location clickLocation) {
		// Method needs to do nothing. Game controlled by keyboard input.
	}

	/**
	 * If the game isn't over, this method tells its Level to move itself by one
	 * step.
	 */
	public void tick() {
		if (!gameOver()) {
			level.tick();
		}

	}

	/**
	 * Returns the Level that we are playing on .
	 * 
	 * @return level - The Level we are playing on
	 */
	public Level getLevel() {
		return level;

	}

	private boolean gameOver() {
		if (level.numberOf(Sprite.HERO) != 1) {
			gameLost = true;
			return true;
		} else if (level.numberOf(Sprite.GOAL) != numberOfGoals) {
			gameWon = true;
			return true;
		}
		return false;
	}

}
