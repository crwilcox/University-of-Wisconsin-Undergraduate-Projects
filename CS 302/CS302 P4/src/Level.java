///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  LevelEditorApp.java
// File:             Level.java
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
 * Level.java creates a level and allows us to move, place, and remove sprites
 * within the level.
 * 
 * <p>
 * Bugs: No Known Issues
 * 
 * @author Christopher Wilcox, Jacob Backhaus
 */
public class Level {
	private int height;
	private int width;
	private Location currLocation;
	Sprite[][] sprites;

	/**
	 * Create a blank level.
	 * 
	 * @param width
	 *            The width of the Level.
	 * @param height
	 *            The height of the Level.
	 */
	public Level(int width, int height) {
		this.height = height;
		this.width = width;
		sprites = new Sprite[height][width];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Sprite spr = new Sprite(Sprite.FLOOR);
				Location loc = new Location(i, j);
				spr.setPosition(loc);
				sprites[j][i] = spr;
			}
		}

	}

	/**
	 * Draws the Level.
	 */
	public void drawSelf() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				sprites[j][i].drawSelf();
			}
		}
	}

	/**
	 * Retrieves Height of the Level.
	 * 
	 * @return Returns the Height of the Level.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Retrieves Width of the Level.
	 * 
	 * @return Returns the Width of the Level.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the Sprite at the requested Location.
	 * 
	 * @param position
	 *            The Location of the Sprite
	 * @return Returns the Sprite
	 */
	public Sprite getSprite(Location position) {
		return sprites[position.y()][position.x()];

	}

	/**
	 * Checks for Valid Location.
	 * 
	 * @return Return true if the specified Location is in bounds for this
	 *         Level.
	 */
	public boolean isValidLocation() {
		if (currLocation.x() > width)
			return false;
		else if (currLocation.y() > height)
			return false;
		else if (currLocation.x() < 0)
			return false;
		else if (currLocation.y() < 0)
			return false;
		return true;
	}

	/**
	 * Move the Sprite from its current Location to the Location specified.
	 * 
	 * @param SpriteToMove
	 *            The Sprite to be Moved.
	 * @param newPosition
	 *            The Location the Spritw will be moved to.
	 */
	public void moveSpriteTo(Sprite SpriteToMove, Location newPosition) {
		Sprite spr = new Sprite(Sprite.FLOOR);
		Location temp = SpriteToMove.getPosition();
		SpriteToMove.setPosition(newPosition);
		sprites[temp.y()][temp.x()] = spr;
		sprites[newPosition.y()][newPosition.x()] = SpriteToMove;
		currLocation = newPosition;

	}

	/**
	 * Counts the number of specified Sprites.
	 * 
	 * @param typeToCount
	 *            The Type of Sprites to count.
	 * @return Returns the number of the specified type of Sprite.
	 */
	public int numberOf(int typeToCount) {
		int total = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (typeToCount == sprites[j][i].getType()) {
					total++;
				}
			}
		}
		return total;
	}

	/**
	 * Use the supplied PrintStream to output this Level.
	 * 
	 * @param output
	 *            The name of the output
	 */
	public void outputLevel(java.io.PrintStream output) {

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				output.print(sprites[j][i].getType());
			}
			output.println();
		}
	}

	/**
	 * Place the given Sprite at the given Location.
	 * 
	 * @param spriteToPlace
	 *            The Sprite to be Placed.
	 * @param position
	 *            The position the Sprite will be placed.
	 */
	public void placeSprite(Sprite spriteToPlace, Location position) {
		spriteToPlace.setPosition(position);
		sprites[position.y()][position.x()] = spriteToPlace;
	}

	/**
	 * Remove the Sprite at the specified Location.
	 * 
	 * @param spriteToDelete
	 *            The Sprite that is to be deleted.
	 */
	public void removeSprite(Sprite spriteToDelete) {
		spriteToDelete = new Sprite(Sprite.FLOOR);
		sprites[spriteToDelete.getPosition().x()][spriteToDelete.getPosition()
				.y()] = spriteToDelete;
	}

	/**
	 * Move the Level by one time-step.
	 */
	public void tick() {

	}
}
