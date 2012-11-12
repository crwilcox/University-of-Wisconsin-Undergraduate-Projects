///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  LevelEditorApp.java
// File:             LevelEditor.java
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
import java.io.*;

/**
 * LevelEditor.java allows us to Edit & Save the level through mouse clicks and
 * keystrokes.
 * 
 * <p>
 * Bugs: No Known Issues
 * 
 * @author Christopher Wilcox, Jacob Backhaus
 */
public class LevelEditor implements WindowObject {
	public static final int NUMBER_OF_SPRITE_TYPES = 8;
	private Level currLevel;
	private String filename;

	/**
	 * Constructor: Create a new LevelEditor that holds a blank Level.
	 * 
	 * @param width
	 *            The width of the level.
	 * @param height
	 *            The height of the level.
	 * @param filename
	 *            The name of the file.
	 */
	public LevelEditor(int width, int height, java.lang.String filename) {
		this.filename = filename;
		currLevel = new Level(width, height);
	}

	/**
	 * Tells the Level to draw itself.
	 */
	public void draw() {
		currLevel.drawSelf();
	}

	/**
	 * This returns the Level that this LevelEditor is editing.
	 */
	public Level getLevel() {
		return currLevel;
	}

	/**
	 * If the key pressed is the s key, the Level is saved to a file with
	 * filename this Level was given when it was created.
	 */
	public void key(char keyChar) {
		if (keyChar == 's')
			try {
				PrintStream output = new PrintStream(filename);

				currLevel.outputLevel(output);
				// code to print information to the file goes here
				// note that because output is a PrintStream object,
				// we can use print(...) and println(...) just like
				// we do with System.out

				System.out.println("Saving succeeded.");
				output.close();
			} catch (FileNotFoundException e) {
				System.out.println("Saving failed.");
			}
	}

	/**
	 * Changes the type of the Sprite at the given Location.
	 */
	public void mouse(Location clickLocation) {

		int s = currLevel.getSprite(clickLocation).getType();
		if (s > 6)
			s = -1;
		Sprite temp = new Sprite(s + 1);
		currLevel.placeSprite(temp, clickLocation);
	}

	/**
	 * Since the LevelEditor does not automatically change as time passes, this
	 * method does nothing.
	 */
	public void tick() {

	}
}
