/**
 * A simple class for easily generating random numbers in a given range
 * @author Tim Bahls
 *
 */
public class Rnd {
	/**
	 * Return a random integer between start and end.  It might be start and it might be end
	 * @param start The lowest possible number.
	 * @param end The highest possible number.
	 * @return A random integer between start and end.
	 */
	public static int getInt(int start, int end){
		return (int)(Math.random()*(end-start+1))+start;
	}
	
	/**
	 * Return a random double between start and end.
	 * @param start The lowest possible number.
	 * @param end The highest possible number.
	 * @return A random integer between start and end.
	 */
	public static double getDouble(double start, double end){
		return Math.random()*(end-start)+start;
	}
	
}
