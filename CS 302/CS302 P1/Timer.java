///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:   StopWatchApp.java
// File:             Timer.java
// Semester:         Spring 2008
//
// Author:           Christopher Wilcox cwilcox@wisc.edu
// CS Login:         wilcox
// TA's Name:        Lec. 001-Hasti --- Lab #316
//
//////////////////////////// 80 columns wide //////////////////////////////////


/**
  *A Timer Class
  *
  *@author Christopher Wilcox
 */

public class Timer
{ 
	private int value;  //creates a location to store initial Values in
	private int numberBase; //Provides a location to store the Base
	
	/**
	 * Creates a timer with the given base whose initial value is 0
	 * 
	 * @param base (base-1) is the number the location can go up to 
	 */
	public Timer(int base)
	{
		value=0;
		numberBase=base;
	}
	
	
	/**
	 * Creates a timer with the given base whose initial value is start
	 * 
	 * @param base (base-1) is the number the location can go up to 
	 * @param start the starting number of a value
	 */
	public Timer(int base, int start)
	{
		int rangeValue= start%base;//start is put into range of base
		value=rangeValue;//gives value of start
		numberBase=base;
	}
	
	
	/**
	 * gets Value
	 * 
	 * @return value returns the value stored in the variable value
	 */
	public int getValue()
	{
		return value;
	}
	
	
	/**
	 * Increments the timer value by 1 and returns the number of cycles
	 * 
	 * @return cycles returns the number of times a column cycles
	 */
	public int increment()
	{
		value = value + 1;
		int cycles = value/numberBase;
		value =  value%numberBase;
		return cycles;
	}
	
	
	/**
	 * Increments the timer value by inc 
	 * 
	 * @return cycles returns the number of times a column cycles
	 */
	public int increment(int inc)
	{
		value = value +  inc;
		int cycles = value/numberBase;
		value =  value%numberBase;
		return cycles;
	}
}