/**
 * A class representing a single UFO object that can move, fire, and be hit.
 * 
 */
public class UFO {

	public static final int SIMPLE_SAUCER = 0;

	// the type of this UFO
	private int uFOType;
	// the current x position of this UFO
	private int xPosition;
	// the current y position of this UFO
	private int yPosition;
	private boolean movement;
	private int hits=2;
	private int ticks;
	public static final int FAST_SHIP = 1;
	public static final int SUPER_SAUCER = 2;

	/**
	 * Constructs an UFO object given its type and initial position
	 * 
	 * @param startX
	 *            The initial x coordinate
	 * @param startY
	 *            The initial y coordinate
	 * @param uFOType
	 *            An int representing the type of UFO this is.
	 */
	public UFO(int startX, int startY, int uFOType) {
		xPosition=startX;
		yPosition=startY;
		this.uFOType=uFOType;
	}

	/**
	 * Get an int represented the type for this UFO
	 * 
	 * @return The int representing the type of this UFO
	 */
	public int getUFOType() {
		return this.uFOType;
	}

	/**
	 * Get the current x coordinate of the center of this UFO.
	 * 
	 * @return The current x coordinate of the center of this UFO.
	 */
	public int getXPosition() {
		return xPosition;
	}

	/**
	 * Get the current y coordinate of the center of this UFO.
	 * 
	 * @return The current y coordinate of the center of this UFO.
	 */
	public int getYPosition() {
		return yPosition;
	}

	/**
	 * Updates the position of the UFO for the next time it is redrawn.
	 * 
	 * @param defender
	 *            The Defender object. May safely be ignored, but some UFO types
	 *            may use this information.
	 */
	public void takeOneStep(Defender defender) {
		int var=(SpaceGame.UFO_SIZE/2);
		
	if (xPosition>=(SpaceGame.getMaximumX()-var)){
		movement=true;}
	
	if (xPosition<=var){
		movement=false;}
		
	if (movement==true){
			xPosition-=3;}

	if (movement==false){
			xPosition+=3;}
			}

	

	/**
	 * Return true if this UFO fires this during this tick.
	 * 
	 * @param defender
	 *            The Defender object. May safely be ignored, but some UFO types
	 *            may use this information.
	 * @return Whether or not this UFO shoots on this tick
	 */
	public boolean shootsThisTurn(Defender defender) {
		ticks+=1;
		if (ticks>=100){
			ticks=0;
			return true;
		}
		else return false;
	}

	/**
	 * Return the bullet this UFO is about to fire.
	 * 
	 * @param defender
	 *            The Defender object. May safely be ignored, but some UFO types
	 *            may use this information.
	 * @return The bullet this UFO is about to fire.
	 */
	public Laser fireWeapon(Defender defender) {
		Laser l=new Laser(xPosition, yPosition, 0, 4);
		return l;
	}

	/**
	 * Returns whether this UFO intersects this Laser
	 * 
	 * @param theLaser
	 * @return true if this UFO intersects this Laser
	 */
	public boolean hitByLaser(Laser theLaser) {
		int xPosLas=theLaser.getXPosition();
		int yPosLas=theLaser.getYPosition();
		int var=(SpaceGame.UFO_SIZE/2);
		if ( (  (xPosLas<=xPosition+var)&&(xPosLas>=xPosition-var)  ) &&  
			(  (yPosLas<=yPosition+var)&&(yPosLas>=yPosition-var) ) ){
			return true;
		}
		else return false;
	}

	/**
	 * Returns true if this UFO has been destroyed.
	 * 
	 * @return true if the UFO has been destroyed.
	 */
	public boolean removeMeFromGame() {
		if (hits<=0){
			return true;
		}
		else return false;
	}

	/**
	 * Updates the Object to record that it has been hit by a Laser. This method
	 * is called every time the UFO is struck by a Laser.
	 */
	public void wasHit() {
			hits-=1;
	}

}
