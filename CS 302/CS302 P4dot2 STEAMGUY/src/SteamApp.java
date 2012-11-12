///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            SteamApp.java
// Files:            ActionSprite.java, SteamCloud.java, Opponent.java, 
//					 Hero.java, SteamGame.java, SteamApp.java, 
//					 LevelLoader.java
//					 Below Three Classes Written By Tim Bahls:
//					 LevelEditorApp.java, MovableSprite.java,
//					 SteamBomb.java
//
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
 * This application launches a Window that contains a SteamGame.
 * 
 * @author Christopher Wilcox, Jacob Backhaus
 */
import java.io.*;
import java.util.*;

public class SteamApp extends java.lang.Object {

	/**
	 * The entry point for the Steam Game application
	 */
	public static void main(java.lang.String[] args) {

		Scanner stdin = new Scanner(System.in);
		System.out.println("Please Enter The Name of Your Level (ex. level.txt)");
		String in= stdin.next();

		try {
			SteamGame sg = new SteamGame(in);
			Window game = new Window(sg);
			game.showWindow(in);

		} catch (IOException ioe) {
			System.out.print(ioe.getMessage());

		}
	}
}
