/**
  The <tt>DoublePoint</tt> class is used to represent two-dimensional points.
*/
public class DoublePoint
{
	private double myX;    // the x coordinate for this point
	private double myY;    // the y coordinate for this point

	/**
	 * Constructs and initializes a <tt>DoublePoint</tt> with the specified coordinates.
	 * @param x the x coordinate for the point
	 * @param y the y coordinate for the point.
	 */
	public DoublePoint(double x, double y)
	{
		myX = x;
		myY = y;
	}
	
	/**
	 * Constructs and initializes a <tt>DoublePoint</tt> with coodinates (0, 0).
	 */
	public DoublePoint() 
	{
		myX = 0.0;
		myY = 0.0;
	}
	
	/**
	 * Returns the x coordinate of this <tt>DoublePoint</tt>.
	 * @return the x coordinate of this <tt>DoublePoint</tt>
	 */
	public double getX()
	{
		return myX;
	}
	
	/**
	 * Returns the y coordinate of this <tt>DoublePoint</tt>.
	 * @return the y coordinate of this <tt>DoublePoint</tt>
	 */
	public double getY()
	{
		return myY;
	}
	
	/**
	 * Sets the location of this <tt>DoublePoint</tt> to the specified coordinates.
	 * @param x the new x coordinate to which to set this point's x coordinate
	 * @param y the new y coordinate to which to set this point's y coordinate
	 */
	public void setLocation(double x, double y)
	{
		myX = x;
		myY = y;
	}
	
	/**
	 * Returns a <tt>String</tt> that represents the value of this <tt>DoublePoint</tt>.
	 * @return a string representation of this point
	 */
	public String toString() 
	{
		return "(" + myX + "," + myY + ")";
	}
	
}

	