/**
  A line represented by its slope and y-intercept.
*/
public class Line
{
	private double mySlope;       // the slope for this line
	private double myYIntercept;  // the y-intercept for this line

	/**
	 * Constructs a line with the given slope and y-intercept.
	 * @param slope
	 * @param yIntercept
	 */
	public Line(double slope, double yIntercept)
	{
		mySlope = slope;
		myYIntercept = yIntercept;
	}
	
	/**
	 * Returns the slope of the line.
	 * @return the slope of the line
	 */
	public double getSlope()
	{
		return mySlope;
	}
	
	/**
	 * Returns the y-intercept of the line.
	 * @return the y-intercept of the line
	 */
	public double getYIntercept()
	{
		return myYIntercept;
	}

		
	/**
	 * Given an x value, calculates the corresponding y value so that
	 * the point (x, y) is on the line.
	 * @param xVal the x value 
	 * @param yVal the y value
	 * @return the y value such that (x, y) is on the line 
	 */
	
	public double calculateY(double xVal)
	{
		double yVal;
		yVal=((mySlope * xVal) + myYIntercept);
		return (yVal);
	}
	
	/**
	 * given a point1 and point2, calculates mySlope and myYIntercept
	 * @param point1
	 * @param point2
	 */
	public Line(DoublePoint point1, DoublePoint point2)
	{
		mySlope = ((point1.getY() - point2.getY()) / (point1.getX() - point2.getX()));
		myYIntercept = (point1.getY() - (mySlope*point1.getX()));
	}
}