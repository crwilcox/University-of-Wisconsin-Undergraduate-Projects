///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SteamApp.java
// File:             ActionSprite.java
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
 * This class represents a Sprite that can interact with the level around it.
 * Since we don't want everything happening at the same time, this Sprite has a
 * built in timer. When the startWaiting() method is called, the timer counts
 * down one per tick until the timer reaches zero. Then it attempts to perform
 * whatever action is appropriate for this ActionSprite. The length of time to
 * wait is passed into the constructor.
 * 
 * @author Christopher Wilcox, Jacob Backhaus
 */
public class ActionSprite extends Sprite {

	private int waitDuration;
	private int temp;

	/**
	 * Create an Action Sprite of the given type with the given duration to
	 * wait. The Sprite should start out waiting.
	 * 
	 * @param spriteType -
	 *            the type of Sprite
	 * @param waitDuration -
	 *            The length of time this Sprite must wait to perform actions
	 */
	public ActionSprite(int spriteType, int waitDuration) {
		super(spriteType);
		this.waitDuration = waitDuration;
		temp = this.waitDuration;
	}

	/**
	 * Reset the timer to the full length of time this ActionSprite waits.
	 */
	public void startWaiting() {
		waitDuration = temp;
	}

	/**
	 * If the ActionSprite is ready to perform its action, perform that action.
	 * Otherwise, reduce the number of turns left to wait by one.
	 * 
	 * @param level -
	 *            the level
	 */
	public void tick(Level level) {
		waitDuration--;
		if (readyToAct())
			performAction(level);
	}

	/**
	 * Return true if this ActionSprite is ready to perform its action on the
	 * next time tick() is called.
	 * 
	 * @return true if this ActionSprite is ready to perform its action
	 */
	public boolean readyToAct() {
		if (waitDuration <= 0)
			return true;
		return false;
	}

	/**
	 * Return the number of times the tick() method must be called before the
	 * Sprite can perform it's action. If the Sprite can perform it's action the
	 * next time tick() is called, this should return 0.
	 * 
	 * @return timeLeft - The amount of time until this Action Sprite can
	 *         perform its action.
	 */
	public int getTimeLeft() {
		int timeLeft = temp - waitDuration;
		return timeLeft;
	}

	/**
	 * The action that this Sprite performs when the counter reaches 0. This
	 * depends on the implementation of the subclass.
	 * 
	 * @param level -
	 *            The Level with which to interact
	 */
	public void performAction(Level level) {

	}
}
