////////////////////////////////////////////////////////////////////////////////
// Main Class File:  SteamApp.java
// File:             SteamBomb.java
// Semester:         Spring 2008
//
// Author:           Tim Bahls
// CS Login:         bahls
// Lecturer:         self
//////////////////////////// 80 columns wide ///////////////////////////////////

/**
 * The SteamBomb is an ActionSprite. It's action is exploding. It waits awhile
 * after creation and then explodes the adjacent squares.
 * 
 * @author Tim
 * 
 */
public class SteamBomb extends ActionSprite {

	/** The number of times a bomb is drawn before it detonates */
	private final static int BOMB_TIME = 55;

	/**
	 * Create a new SteamBomb. The bomb immeadiately starts couting down to
	 * zero.
	 * 
	 */
	public SteamBomb() {
		super(Sprite.STEAM_BOMB, BOMB_TIME);
	}

	/**
	 * When this is called, the SteamBomb goes off. That makes that square and
	 * the adjacent four squares hit by steam.
	 * 
	 * @param theLevel
	 */
	public void performAction(Level theLevel) {
		// First, remove the bomb and replace it with a SteamCloud
		theLevel.placeSprite(new SteamCloud(), getPosition());

		// we make a list of directions so we can loop through them safely
		int[] allDirections = { Location.UP, Location.DOWN, Location.LEFT,
				Location.RIGHT };

		// For each direction
		for (int index = 0; index < allDirections.length; index++) {
			int direction = allDirections[index];
			Location relative = Location.getRelativeLocation(getPosition(),
					direction);
			// try to turn the Sprite at that location into a steam cloud
			tryToAddSteamPuff(relative, theLevel);
		}
	}

	/**
	 * This helper method s called if the given location is hit by a burst of
	 * Steam. It deals with the consequences. If it is off the map of the Sprite
	 * is immune to Steam, nothing happens. If the Sprite at that location is a
	 * SteamBomb, it explodes. Otherwise, whatever is there is destroyed.
	 * 
	 * @param hitLocation
	 *            The Location that was hit
	 * @param theLevel
	 *            The Level to use for modifications
	 */
	private void tryToAddSteamPuff(Location hitLocation, Level theLevel) {
		// If we are outside the level,
		if (!theLevel.isValidLocation(hitLocation)) {
			return;
		}
		// If the Sprite is immune to steam, do nothing
		Sprite hitSprite = theLevel.getSprite(hitLocation);
		if (isImmune(hitSprite)) {
			return;
		}

		if (hitSprite instanceof SteamBomb) {
			// if the Sprite is a bomb, explode it.
			((SteamBomb) hitSprite).performAction(theLevel);
		} else {
			// Otherwise replace it with a SteamCloud.
			theLevel.placeSprite(new SteamCloud(), hitLocation);
		}

	}

	/**
	 * This helper method returns true if the SteamBomb can't damage this
	 * location. SteamBombs can't hurt walls or the goal, but anything else is
	 * fair game.
	 * 
	 * @param theSprite
	 *            The Sprite we are checking
	 * @return return true if the Sprite can't be hurt
	 */

	private boolean isImmune(Sprite theSprite) {
		return theSprite.getType() == Sprite.WALL
				|| theSprite.getType() == Sprite.GOAL;
	}
}
