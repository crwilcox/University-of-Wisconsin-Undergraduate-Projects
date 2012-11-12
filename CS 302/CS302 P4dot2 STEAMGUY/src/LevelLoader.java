///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SteamApp.java
// File:             LevelLoader.java
// Semester:         Spring 2008
//
// Author:           Christopher Wilcox cwilcox@wisc.edu
// CS Login:         wilcox
// TA's Name:        Hasti: Lab Section 316
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Jacob Backhaus
//     CS Login:     Backhaus
//    TA's Name:     Hasti: Lab Section 306
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          (list anyone who helped you write your program)
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.Scanner;
import java.util.*;
import java.io.*;

/**
 * This utility class is responsible for loading a Level from a file.
 * 
 * @author Christopher Wilcox, Jacob Backhaus
 */
public class LevelLoader {

	/**
	 * Constructor for LevelLoader Class
	 */
	public LevelLoader() {
		// this is blank, since loadLevel is static, we don't use this
	}

	/**
	 * Load the level from the file with the given name. The Level should use
	 * the Hero that was passed in. The format is the same as the format from
	 * Level's outputLevel method. In order to work, the various Sprite should
	 * be created with the most specific type. For example, use new SteamBomb(),
	 * not new Sprite(Sprite.STEAM_BOMB).
	 * 
	 * @param fileName -
	 *            the name of the file we wish to use
	 * @param heroToUse -
	 *            the Hero object to use
	 * @return tempLevel - The Level we have built
	 * @throws java.io.IOException
	 */
	public static Level loadLevel(java.lang.String fileName, Hero heroToUse)
			throws java.io.IOException {
		int goals = 0;
		int hero = 0;
		Level tempLevel;

		try {
			Scanner stdin = new Scanner(new File(fileName));

			int loadWidth = stdin.nextInt();
			int loadHeight = stdin.nextInt();
			if (loadWidth <= 0 || loadHeight <= 0)
				throw new IOException("Your Width or Height Must Be >0");

			tempLevel = new Level(loadWidth, loadHeight);
			Sprite x = null;
			for (int i = 0; i < loadHeight; i++) {
				for (int k = 0; k < loadWidth; k++) {
					int v = stdin.nextInt();
					if (v > 7 || v < 0)
						throw new IOException(
								"Sprite Type Given Does Not Exist");

					switch (v) {
					case 0:
						x = new Sprite(0);// FLOOR
						break;
					case 1:
						x = new Sprite(1);// WALL
						break;
					case 2:
						x = new Sprite(2);// BLOCK
						break;
					case 3:
						x = heroToUse;// HERO
						hero++;
						break;
					case 4:
						x = new Opponent();// OPPONENT
						break;
					case 5:
						x = new Sprite(5);// GOAL
						goals++;
						break;
					case 6:
						x = new SteamCloud();// STEAM_CLOUD
						break;
					case 7:
						x = new SteamBomb();// STEAM_BOMB
						break;
					}

					Location y = new Location(k, i);
					tempLevel.placeSprite(x, y);
				}

			}
			if (stdin.hasNext())
				throw new IOException(
						"There are too many Numbers in this level.");
		} catch (FileNotFoundException fnfe) {
			throw new IOException("File Was Not Found");
		} catch (NumberFormatException nfe) {
			throw new IOException(
					"Given File Contains Something That is Not A Number");
		} catch (InputMismatchException nfe) {
			throw new IOException("Error Encountered With Input");
		} catch (NoSuchElementException nsee) {
			throw new IOException("Input is Exhausted...");
		}

		if (hero != 1)
			throw new IOException(
					"The Number of Heros in This Game are Not Equal to 1...");
		if (goals < 1)
			throw new IOException("This Game Has No Goals...");

		return tempLevel;
	}
}
