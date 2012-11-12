///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            StopWatchApp.java
// Files:            StopWatch.java, Timer.java
// Semester:         Spring 2008
//
// Author:           Christopher Wilcox cwilcox@wisc.edu
// CS Login:         wilcox
// TA's Name:        Lec. 001-Hasti --- Lab #316
//
//////////////////////////// 80 columns wide //////////////////////////////////


/**
 *creates on StopWatch object that
 *<p>-uses 1000 as the base for the day allowing it to handle days up to 999
 *<p>-uses 24 as the base for the hour allowing it to handle hours up to 23
 *<p>-uses 60 as the base for the minute allowing it to handle minutes up to 59
 *<p>-uses 60 as the base for the second allowing it to handle seconds up to 59
*/

public class StopWatchApp {
	
	public static void main (String[] args) {
		
		Timer days = new Timer(1000, 3);
		Timer hours = new Timer(24, 12);
		Timer minutes = new Timer(60, 34);
		Timer seconds = new Timer(60, 45);
		
		StopWatch stopwatch1 = new StopWatch(days, hours, minutes, seconds);
		stopwatch1.printTime();//line 1:prints start time
		
		stopwatch1.tick(1);
		stopwatch1.printTime();//line 2:prints 1 seconds after start time
		
		stopwatch1.tick(38-1);//Ticks stopwatch value-previous moves
		stopwatch1.printTime();//line 3:prints 38 seconds after start time
		
		stopwatch1.tick(183-38);//Ticks stopwatch value-previous moves
		stopwatch1.printTime();//line 4:prints 183 seconds after start time

		stopwatch1.tick(10059-183);//Ticks stopwatch value-previous moves
		stopwatch1.printTime();//line 5:prints 10,059 seconds after start time
	
		stopwatch1.tick(302059-10059);//Ticks stopwatch value-previous moves
		stopwatch1.printTime();//line 6:prints 302,059 seconds after start time
		
	}
}