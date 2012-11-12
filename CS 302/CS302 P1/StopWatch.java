///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  StopWatchApp.java
// File:             StopWatch.java
// Semester:         Spring 2008
//
// Author:           Christopher Wilcox cwilcox@wisc.edu
// CS Login:         wilcox
// TA's Name:        Lec. 001-Hasti --- Lab #316
//
//////////////////////////// 80 columns wide //////////////////////////////////


/**
 *   A StopWatch
 *   
 *   @author Christopher Wilcox
 */


public class StopWatch
{

	private Timer days;
	private Timer hours;
	private Timer minutes;
	private Timer seconds;
	
	
	/**
	 * Creates a StopWatch with 4 provided timers 
	 * <p>-These timers keep track of days, hrs, min, and sec
	 * 
	 * @param day the day according to the timer
	 * @param hour the hour according to the timer
	 * @param minute the minute according to the timer
	 * @param second the second according to the timer
	 */
	public StopWatch(Timer day, Timer hour, Timer minute, Timer second)
	{	
		days=day;
		hours=hour;
		minutes=minute;
		seconds=second;
	}
	
	
	
	
	/**
	 * increments the StopWatch by s seconds and updates data members
	 */
	public void tick(int s)
	{
		int tempMinutes;
		tempMinutes = seconds.increment(s);//adds s seconds 
		
		int tempHours;
		tempHours = minutes.increment(tempMinutes);//adds tempMinutes minutes 
		
		int tempDays;
		tempDays = hours.increment(tempHours);//adds tempHours hours 
		
		days.increment(tempDays);//adds tempdays days to count
	}
	
	
	
	
	
	/**
	 * gives the day the timer is on
	 * 
	 * @return days a reference to the Timer object keeping track of days
	 */
	public Timer getDayTimer()
	{
		return days;
	}
	
	
	
	
	/**
	 * gives the hour the timer is on
	 * 
	 * @return days a reference to the Timer object keeping track of hours
	 */
	public Timer getHourTimer()
	{
		return hours;
	}
	
	
	
	
	/**
	 * gives the minute the timer is on
	 * 
	 * @return minutes a reference to the Timer object keeping track of minutes
	 */
	public Timer getMinuteTimer()
	{
		return minutes;
	}
	
	
	
	
	/**
	 * gives the second the timer is on
	 * 
	 * @return seconds a reference to the Timer object keeping track of seconds
	 */
	public Timer getSecondTimer()
	{
		return seconds;
	}
	
	
	
	
	/**
	 * prints the current time of the StopWatch in (format: 23d 5h 3m 0s)
	 */
	public void printTime()
	{
		System.out.print(days.getValue());//prints days, d, and a space
		System.out.print("d");
		System.out.print(" ");
		
		System.out.print(hours.getValue());//prints hours, h, and a space
		System.out.print("h");
		System.out.print(" ");
		
		System.out.print(minutes.getValue());//prints minutes, m, and a space
		System.out.print("m");
		System.out.print(" ");
		
		System.out.print(seconds.getValue());//prints seconds, s, and a space
		System.out.print("s");
		System.out.println();
				
	}
	
}