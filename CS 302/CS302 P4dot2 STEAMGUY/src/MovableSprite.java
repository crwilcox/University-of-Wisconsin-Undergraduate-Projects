////////////////////////////////////////////////////////////////////////////////
// Main Class File:  SteamApp.java
// File:             MovableSprite.java
// Semester:         Spring 2008
//
// Author:           Tim Bahls
// CS Login:         bahls
// Lecturer:         self
//////////////////////////// 80 columns wide ///////////////////////////////////

/**
 * This class represents a special type of ActionSprite that can move itself
 * around the Level. So that nothing can move around too fast, the MovableSprite
 * can't move unless it is readyToAct() according to the ActionSprite method.
 * This Sprite starts waiting each time it moves and cannot move again until the
 * counter has reached zero.<p>
 * Instead of a method that directly moves the Sprite, there is a
 * tryToMoveMethod that looks at the Level and tries to determine what should
 * happen if this Sprite tries to move in that way. There are three things that
 * might happen.
 * <ul>
 * <li>If the Sprite we are moving onto is IMPASSIBLE to this Sprite, nothing
 * happens.
 * <li> If the Sprite is SAFE, this MovableSprite is moved onto that tile.
 * <li> If the Sprite is DAMAGING, the MovableSprite that tried to move there is
 * deleted.
 * </ul>
 * 
 * @author Tim
 * 
 */
public class MovableSprite extends ActionSprite {

	/** Symbolic constant that represents an obstacle that can't be stepped on */
	public static final int IMPASSABLE = 0;
	/** Symbolic constant that represents a Sprite that is harmful */
	public static final int DAMAGING = 1;
	/** Symbolic constant that represents a Sprite that may be stepped on */
	public static final int SAFE = 2;

	/**
	 * Create a new MovableSprite with the given type that can move once every
	 * waitDuration ticks.
	 * 
	 * @param spriteType
	 *            The type of the Sprite we desire
	 * @param waitDuration
	 *            The length of time after each move this Sprite must rest.
	 */
	public MovableSprite(int spriteType, int waitDuration) {
		super(spriteType, waitDuration);
	}

	/**
	 * Attempt to move this MovableSprite in the specified direction on the
	 * specified Level. There are three things that might happen.
	 * <ul>
	 * <li>If the Sprite we are moving onto is IMPASSIBLE to this Sprite,
	 * nothing happens.
	 * <li> If the Sprite is SAFE, this MovableSprite is moved onto that tile.
	 * <li> If the Sprite is DAMAGING, the MovableSprite that tried to move
	 * there is deleted.
	 * </ul>
	 * 
	 * @param direction
	 *            The direction to try moving in
	 * @param level
	 *            The Level to move inside
	 * @return true if the Sprite managed to move out of this square
	 */
	public boolean tryToMove(int direction, Level level) {
		if (!readyToAct()) {
			return false;
		}
		
		//Get the location we are getting to.  Quit if it's not in bounds.
		Location destination = Location.getRelativeLocation(getPosition(),
				direction);
		if (!level.isValidLocation(destination)) {
			return false;
		}
		
		//Get the action plan for the type of Sprite we're trying to step on
		Sprite s = level.getSprite(destination);
		int code = statusOfDestination(s.getType());

		//Enact the plan by moving, not moving, or dying.
	
		if (code == IMPASSABLE) {
			return false;
		} else if (code == DAMAGING) {
			level.removeSprite(this);
			return true;
		} else {
			level.moveSpriteTo(this, destination);
			
			//start waiting before we can move again.
			startWaiting();
			return true;
		}

	}

	/**
	 * This method should be overridden by all subclasses. It specifies what
	 * happens to the MovableSprite if it tries to step on this particular type
	 * of Sprite. It has three options: it could be unable to move there, it
	 * could move there safely, or it could remove the Sprite from the game. It
	 * should return IMPASSIBLE, SAFE, or DAMAGING appropriately.
	 * 
	 * @param type
	 *            the type of the Sprite we are trying to move onto
	 * @return IMPASSIBLE, SAFE, or DAMAGING
	 */
	public int statusOfDestination(int type) {
		//All MovableSprites should override this method.
		throw new UnsupportedOperationException("The statusOfDestination()"
				+ " method was not overridden properly.");
	}

}
