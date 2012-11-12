///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            LevelEditorApp.java
// Files:            Level.java, LevelEditor.java, Location.java, Sprite.java
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
 * LevelEditorApp.java implements the LevelEditor Class and allows the user to
 * create a playable level.
 * 
 * <p>
 * Bugs: No Known Issues
 * 
 * @author Christopher Wilcox, Jacob Backhaus
 */
public class LevelEditorApp extends java.lang.Object {

	public static void main(java.lang.String[] args) {

		LevelEditor levEd = new LevelEditor(12, 10, "level.txt");
		Window game = new Window(levEd);
		game.showWindow("level.txt");

	}

}
