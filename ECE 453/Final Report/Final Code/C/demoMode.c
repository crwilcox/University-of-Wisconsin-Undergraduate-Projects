#include "tableLibrary.h"
#include "demoMode.h"
#include "colors.h"
#include <unistd.h>
#include <stdio.h>


//SetCupColors(int[] cupNumbers, int cupCount, int color); 
	
/*
 * CycleRainbow:
 *		changes all cups at the sametime through the rainbow...
 *		pause for 1 second between...
 */
void CycleRainbow(){
	debug_printf("Starting CycleRainbow\n");

	int colorOrder[] = { RED, SAFEORANGE, YELLOW, LIME, BLUE, SAFEINDIGO, FUSCHIA };
	int i;
    for(i = 0; i < 7; i++){
		SetCupColors(allCups, 20, colorOrder[i]);
		if(wait(0,1000000)) return;
	}
	debug_printf("Ending CycleRainbow\n");
}

/* CycleRainbowBlend:
 *		changes all cup colors at the same time through the rainbow
 *		unlike CycleRainbow(), this method attempts to blend the colors
 */
void CycleRainbowBlend(){
	debug_printf("Starting CycleRainbowBlend\n");
	
	int colorOrder[] ={ 	
					RED, ORANGERED, ORANGE, GOLD, 
					YELLOW, GREENYELLOW, GREEN, AQUA, 
					BLUE, NAVY, INDIGO, PURPLE, 
					VIOLET, PINK, DEEPPINK, CRIMSON 
					};
	int i;
    for(i = 0; i < 16; i++){
		SetCupColors(allCups, 20, colorOrder[i]);
		if(wait(0,500000)) return;
	}
	debug_printf("Ending CycleRainbowEnd\n");
}

/*
 * RainbowWave:
 *		cycle the rainbow through the rows on the table
 */
 void RainbowWave(){
	debug_printf("Starting RainbowWave\n");
	
	int colorList[] = { RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET, BLACK };
	int i;
	for(i = 0; i < 32; i++){
		//set them all to different colors ofthe rainbow and rotate every second
		SetCupColors(row0, 4, colorList[(i+0)%8]);
		SetCupColors(row1, 3, colorList[(i+1)%8]);
		SetCupColors(row2, 2, colorList[(i+2)%8]);
		SetCupColors(row3, 1, colorList[(i+3)%8]);
		SetCupColors(row4, 1, colorList[(i+4)%8]);
		SetCupColors(row5, 2, colorList[(i+5)%8]);
		SetCupColors(row6, 3, colorList[(i+6)%8]);
		SetCupColors(row7, 4, colorList[(i+7)%8]);
		if(wait(0,500000)) return;//sleep for half a second
	}
	debug_printf("Ending RainbowWave\n");
 }

/* 
 * Smooth Rainbow Blend
 *      smoothly transition through the rainbow.
 */
void SmoothRainbowBlend() {
    debug_printf("Starting SmoothRainbowBlend\n");
    int i;
    int r[] = {255, 255, 255,   0,   0,   0, 100, 255};
    int g[] = {  0, 100, 255, 255, 255,   0,   0,   0};
    int b[] = {  0,   0,   0,   0, 255, 255, 255, 255};

    int rowColors[] = {0, 0, 0, 0, 0, 0, 0, 0};

    unsigned int uslp = 60000;

    int done = 2;

    while(done != 0) {
        if(r[0] == 255 && g[0] == 0 && b[0] == 0)
            done--;

        for(i = 0; i < 8; i++) {
            rowColors[i] = r[i] << 16 | g[i] << 8 | b[i];

            if(r[i] == 255 && g[i] == 0 && b[i] == 0) {
                g[i] += 5;
                continue;
            }
            if(r[i] == 255 && g[i] == 255 && b[i] == 0) {
                r[i] -= 5;
                continue;
            }
            if(r[i] == 0 && g[i] == 255 && b[i] == 0) {
                b[i] += 5;
                continue;
            }
            if(r[i] == 0 && g[i] == 255 && b[i] == 255) {
                g[i] -= 5;
                continue;
            }
            if(r[i] == 0 && g[i] == 0 && b[i] == 255) {
                r[i] += 5;
                continue;
            }
            if(r[i] == 255 && g[i] == 0 && b[i] == 255) {
                b[i] -= 5;
                continue;
            }

            if(r[i] < 255 && r[i] > 0) {
                // State 1 or 4
                if(g[i] == 255) {
                    //State 1
                    r[i] -= 5;
                } else {
                    r[i] += 5;
                }
            }
            if(g[i] < 255 && g[i] > 0) {
                // State 0 or 3
                if(r[i] == 255) {
                    //State 0
                    g[i] += 5;
                } else {
                    g[i] -= 5;
                }
            }
            if(b[i] < 255 && b[i] > 0) {
                // State 2 or 5
                if(g[i] == 255) {
                    //State 2
                    b[i] += 5;
                } else {
                    b[i] -= 5;
                }
            }
        }
        if(wait(0,uslp)) return;


        SetCupColors(row0, 4, rowColors[0]);
        SetCupColors(row1, 3, rowColors[1]);
        SetCupColors(row2, 2, rowColors[2]);
        SetCupColors(row3, 1, rowColors[3]);

        SetCupColors(row4, 1, rowColors[4]);
        SetCupColors(row5, 2, rowColors[5]);
        SetCupColors(row6, 3, rowColors[6]);
        SetCupColors(row7, 4, rowColors[7]);
    }
    debug_printf("Ending SmoothRainbowBlend\n");
}


/*
 * MotionDetectionRed:
 *      make cup brighter red the more covered it is
 */
void MotionDetectionRed() {
    int i;
    int sensorVal = 0;
    int chosen_color;
    for (i = 0; i < 20; i++) {
        sensorVal = CheckSensorEdge(i);
        if (sensorVal == 3) {
            chosen_color = ( rand() % 255 << 8 | rand() % 255);
            SetCupColor(i, 0x0000FF);
        }
        else if (sensorVal == 0) {
            SetCupColor(i, 0x00FF0000);
        }
        if(wait(0,1000)) return;
    }
    if(wait(0,500000)) return;

}

/*
 * Breathing:
 *      Change colors on all cups at the same time in a breathing manner
 */
void Breathing(int delay) {
    int i; // Index for all colors
    int j; // Index for all cups

	/*int colorOrder[] ={ 	
					RED, ORANGERED, ORANGE, GOLD, 
					YELLOW, GREENYELLOW, GREEN, AQUA, 
					BLUE, NAVY, INDIGO, PURPLE, 
					VIOLET, PINK, DEEPPINK, CRIMSON 
					};*/
	int colorOrder[] ={ 0x00FF0000, 0x0000FF00, 0x000000FF, 0x00FF00FF};	
    int cupNumber[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
    delay = delay * 1000;
    int red_temp, green_temp, blue_temp;
    wait(1,0);
    for (i=0; i < 4; i++) {
        // One color to start
        // For each color
        red_temp = colorOrder[i];
        red_temp = red_temp >> 16;
        green_temp = colorOrder[i];
        green_temp = (green_temp >> 8) & 0xFF;
        blue_temp = colorOrder[i];
        blue_temp = (blue_temp) & 0xFF;

        // Inhale
        SetCupColors(cupNumber, 20, 0);
        if(wait(0,delay/2)) return;
        SetCupColors(cupNumber, 20, (red_temp/16 << 16 | green_temp/16 << 8 | blue_temp/16));
        if(wait(0,delay/2)) return;
        SetCupColors(cupNumber, 20, (2*red_temp/16 << 16 | 2*green_temp/16 << 8 | 2*blue_temp/16));
        if(wait(0,delay/2)) return;
        SetCupColors(cupNumber, 20, (3*red_temp/16 << 16 | 3*green_temp/16 << 8 | 3*blue_temp/16));
        if(wait(0,delay/2)) return;
        SetCupColors(cupNumber, 20, (4*red_temp/16 << 16 | 4*green_temp/16 << 8 | 4*blue_temp/16));
        if(wait(0,delay/2)) return;
        SetCupColors(cupNumber, 20, (5*red_temp/16 << 16 | 5*green_temp/16 << 8 | 5*blue_temp/16));
        if(wait(0,delay/2)) return;
        SetCupColors(cupNumber, 20, (6*red_temp/16 << 16 | 6*green_temp/16 << 8 | 6*blue_temp/16));
        if(wait(0,delay/2)) return;
        SetCupColors(cupNumber, 20, (7*red_temp/16 << 16 | 7*green_temp/16 << 8 | 7*blue_temp/16));
        if(wait(0,delay/2)) return;
        SetCupColors(cupNumber, 20, (8*red_temp/16 << 16 | 8*green_temp/16 << 8 | 8*blue_temp/16));
        if(wait(0,delay/2)) return;
        SetCupColors(cupNumber, 20, (9*red_temp/16 << 16 | 9*green_temp/16 << 8 | 9*blue_temp/16));
        if(wait(0,delay/2)) return;
        SetCupColors(cupNumber, 20, (10*red_temp/16 << 16 | 10*green_temp/16 << 8 | 10*blue_temp/16));
        if(wait(0,delay/2)) return;
        SetCupColors(cupNumber, 20, (11*red_temp/16 << 16 | 11*green_temp/16 << 8 | 11*blue_temp/16));
        if(wait(0,delay/3)) return;
        SetCupColors(cupNumber, 20, (12*red_temp/16 << 16 | 12*green_temp/16 << 8 | 12*blue_temp/16));
        if(wait(0,delay/3)) return;
        SetCupColors(cupNumber, 20, (13*red_temp/16 << 16 | 13*green_temp/16 << 8 | 13*blue_temp/16));
        if(wait(0,delay/3)) return;
        SetCupColors(cupNumber, 20, (15*red_temp/16 << 16 | 15*green_temp/16 << 8 | 15*blue_temp/16));

        // Exhale
        if(wait(0,delay)) return;
        SetCupColors(cupNumber, 20, (9*red_temp/16 << 16 | 9*green_temp/16 << 8 | 9*blue_temp/16));
        if(wait(0,2*delay/3)) return;
        SetCupColors(cupNumber, 20, (5*red_temp/16 << 16 | 5*green_temp/16 << 8 | 5*blue_temp/16));
        if(wait(0,2*delay/3)) return;
        SetCupColors(cupNumber, 20, 0x00000000);
        if(wait(0,2*delay/3)) return;
        if(wait(0,delay)) return;
    }
}

void DebugMode(int delay, int single) {
    int c;
    SetCupColors(allCups, 20, 0x000000);

	int colors[] ={ 0x000000, 0xAAAAAA,
					RED, ORANGERED, ORANGE, GOLD, 
					YELLOW, GREENYELLOW, GREEN, AQUA, 
					BLUE, NAVY, INDIGO, PURPLE, 
					VIOLET, PINK, DEEPPINK, CRIMSON 
					};
      
    //int colors[] = {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF, 0xFFFFFF};

    int num_colors = 18;
    int num_cups = 10;

    int i = 0;
    int j = 0;
    for(i = 1; i < num_colors; i++) {
        if(single == 1) {
            for(j = 0; j < num_cups; j++) {
                SetCupColors(allCups + j, 1, colors[i]);
                SetCupColors(allCups + 10 + j, 1, colors[i]);
                if(delay == 0) c = getc(stdin);
                if(wait(delay,0)) return;
                SetCupColors(allCups + j, 1, 0x000000);
                SetCupColors(allCups + 10 + j, 1, 0x000000);
            }
        } else {
            SetCupColors(allCups, 20, colors[i]);
            if(delay == 0) c = getc(stdin);
            if(wait(delay,0)) return;
        }
    }
}


/* Snake():
 * 		The whole table starts one color, and cup by cup it would change,
 *		as the 'snake' wandered down the table in the order 
 *		9,8,7,6,3,4,5,2,1,0,10,12,11,13,14,15,19,18,17,16
 *		when this finished, it could just start a different color
 */
void Snake(){
	int delay = 250000; // .25 seconds between
	//turned off the initialize.  It actually is cool if it just goes after and slowly gets
	//rid of what was there...
    //SetCupColors(allCups, 20, 0x000000);//Initialize the table to off

	//I chose 16 colors and tried to alternate the variety as to make the effect obvious.  
	//closer colors could be used to minimize the effect.
	int cupColors[] ={
					RED, GREEN, ORANGE, VIOLET,
				   	GREENYELLOW, DEEPPINK, GOLD, NAVY, 
					PINK, BLUE, PURPLE, ORANGERED, 
					AQUA, YELLOW
					};
	int cupOrder[] = { 
		 6,7,8,9,
		  5,4,3,
		   1,2,
		    0,
		   10,
		  11,12,
		 15,14,13,
		16,17,18,19
	};
      
    int num_colors = 14;
    int num_cups = 20;

    int i = 0;
    int j = 0;
    for(i = 0; i < num_colors; i++) {
    	for(j = 0; j < num_cups; j++) {
        	SetCupColor(cupOrder[j], cupColors[i]);
            if(wait(0,delay)) return;
    	}
    }

}

/* CountingColors():
 *  Treats the RGB value on cups as one int.  Counting from 0 to 2^24-1
 */
void CountingColors(int delay, int single){
    SetCupColors(allCups, 20, 0x000000);

    int num_cups = 10;

    int j = 0;
    int c = 0;
    for(c = 0; c < 2^24; c++) {
        if(single == 1) {
            for(j = 0; j < num_cups; j++) {
                SetCupColors(allCups + j, 1, c);
                SetCupColors(allCups + 10 + j, 1, c);
                if(delay == 0) c = getc(stdin);
                if(wait(0, delay)) return;
                SetCupColors(allCups + j, 1, 0x000000);
                SetCupColors(allCups + 10 + j, 1, 0x000000);
            }
        } else {
            SetCupColors(allCups, 20, c);
            if(delay == 0) c = getc(stdin);
            if(wait(0, delay)) return;
        }

        c++;
    }

    SetCupColors(allCups, 20, 0x000000);
}
