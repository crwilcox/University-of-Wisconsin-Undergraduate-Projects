/*******************************************************************************
* demoMode.h
* Summary:	
* Authors:	Christopher Wilcox
*******************************************************************************/

#ifndef __demoMode_h__
#define __demoMode_h__

/*
 * CycleRainbow:
 *		changes all cups at the sametime through the rainbow...
 */
void CycleRainbow();
 
/* CycleRainbowBlend:
 *		changes all cup colors at the same time through the rainbow
 *		unlike CycleRainbow(), this method attempts to blend the colors
 */
void CycleRainbowBlend();
 
/*
 * RainbowWave:
 *		cycle the rainbow through the rows on the table
 */
void RainbowWave();

/* 
 * Smooth Rainbow Blend
 *      smoothly transition through the rainbow.
 */
void SmoothRainbowBlend();

/*
 * MotionDetectionRed:
 *      make cup brighter red the more covered it is
 */
void MotionDetectionRed();

/*
 * Breathing:
 *      Change colors on all cups at the same time in a breathing manner
 */
void Breathing();

/*
 * DebugMode:
 *      Change colors slowly, delay specified on the command line.
 */
void DebugMode(int delay, int single);

/* Snake: 
 *		The Whole table starts one color, and cup by cup it would change,
 *		as the 'snake' wandered down the table in the order 
 *		9,8,7,6,3,4,5,2,1,0,10,12,11,13,14,15,19,18,17,16
 *		when this finished, it could just start a different color
 */
void Snake();

/* CountingColors():
 *  Treats the RGB value on cups as one int.  Counting from 0 to 2^24-1
 */
void CountingColors(int delay, int single);

/*
 * Other DemoModes Yet to implement:
 *
 *
 *
 * Pong: (Chris)
 *		Have the table be all black (off), two of the four cups in each back row could be on.
 *		light up a cup of the rest that bounces off the back paddles (like pong).
 *		when the end fails to catch it, have to whole table flash white or red
 *
 * Random: (Chris)
 *		Have the table take on random colors, and change them from time to time
 *
 * Spinning Rainbow: (Paul)
 *		Paul had an idea to have the table make a color wheel, and have that rotate.
 *		The cups would show a portion of the wheel as it turned
 *		http://www.interiordesignreviews.com/wp-content/uploads/2010/12/Color-wheel-Interior-Design.jpg
 *		using that wheel, when one side had green and green yellow showing, the other would have 
 *		firebrick and red.  They would spin and then show different colors
 */ 

 
 /*
 9 8 7 6
  5 4 3
   2 1
    0
	
    10
  11  12
 13 14 15
16 17 18 19
*/

#endif // __demoMode_h__
