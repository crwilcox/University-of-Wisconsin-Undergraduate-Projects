/**
 * An object of the Die class represents a die with a given number of sides.
 *
 * Bugs: allows a die with a non-positive number of sides to be created.
 *
 * @author Rebecca Hasti, hasti@cs.wisc.edu
 *         copyright 2001, 2005-7, all rights reserved
 **/
public class Die {

    private int numSides; // number of sides the die has
    private int sideUp;   // value showing on the "top" side
    public static final int DEFAULT_NUM_SIDES = 6; // default number of sides

    /**
     * Constructs a die with n sides.
     * @param n the number of sides
     **/
    public Die(int numSides) {
        this.numSides = numSides;
        sideUp = 1;
    }

    /**
     * Constructs a die with the default number of sides.
     **/
    public Die() {
        numSides = DEFAULT_NUM_SIDES;
        sideUp = 1;
    }

    /** 
     * This method simulates the rolling of a die.  A new side up is
     * computed using a random number generator.
     **/
    public void roll() {
        sideUp = (int) Math.floor(Math.random() * numSides + 1);
    }

    /**
     * Returns the current side up on the die.
     * @return the current side up
     **/
    public int getSideUp() {
        return sideUp;
    }
} // end class Die
