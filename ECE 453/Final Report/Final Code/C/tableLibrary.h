/*******************************************************************************
* tableLibrary.h
* Summary:	
* Authors:	Christopher Wilcox
*******************************************************************************/

#ifndef __tableLibrary_h__
#define __tableLibrary_h__

#include <stdio.h>

//OPCODE1 for GUI DATA
#define SENSORS		0x0
#define CUPS		0x1
#define GAMEMODE	0x2
#define DEMOMODE	0x3
#define FULLSTATE	0x4
#define OP5			0x5
#define OP6			0x6
#define GUI_QUIT	0x7  //Sig from the board to the gui, makes the gui disconnect.

//OPCODE2 for GUI DATA
//SENSORS			0x0
//CUPS				0x1
//GAMEMODE			0x2
#define STARTGAME 	0x1F 	
#define T2ON		0x03
#define T2OFF 		0x02
#define T1ON 		0x01
#define T1OFF		0x00
#define ENDGAME		0x04

//DEMOMODE			0x3
#define CYCLERAINBOW		0x01
#define CYCLERAINBOWBLEND	0x02
#define RAINBOWWAVE			0x03
#define MOTIONDETECTIONRED	0x04
#define BREATHING			0x05
#define CYCLERAINBOWSMOOTH	0x06
#define SNAKE				0x07
#define COUNTINGCOLORS		0x08
#define DEMOMODE9			0x09

#define DEMOMODE31			0x1F
//OP4				0x4
//OP5				0x5
//OP6				0x6
//OP7				0x7

#define BASE_ADDRESS 0xD3000000
#define MAP_SIZE 4096UL
#define MAP_MASK (MAP_SIZE - 1)
/*
 * Dark: 0xD300 (54016)
 * Light:0x6A00 (27136)
 * I am going to use a cut off between this (40500)
 */
//#define SENSOR_TRIP 40500
#define SENSOR_TRIP 600
//#define SENSOR_TRIP 1550
//#define SENSOR_TRIP 1750
#define TOTAL_TEAMS 2

#define FALSE			0
#define TRUE			1


//SOME USEFUL ARRAYS
int allCups[20];
//break cups into rows
int row0[4];
int row1[3];
int row2[2];
int row3[1];
int row4[1];
int row5[2];
int row6[3];
int row7[4];

/*
 * LibInit(int input_fd, int output_fd, char verbose): 	
 * 		Initializes the library
 * 		Verbose flag enables extra debug statements.
 */
void LibInit(int input_fd, int output_fd, char verbose, int *threshold);

/*
 * CheckSensor(int cupNumber):
 * 		return 1 for CupPresent and 0 if not
 */
int CheckSensor(int cupNumber);
	 
/*
 * CheckSensorEdge(int cupNumber):
 *
 *		return == 0 if no edge and no cup
 *		       == 1 if no edge and cup
 *		       == 2 if edge and no cup
 *		       == 3 if edge and cup
 */
int CheckSensorEdge(int cupNumber);

/*
 * CheckSensorAbs(int cupNumber):
 *      return Actual read value of a sensor
 */
int CheckSensorAbs(int cupNumber);
/*
 * CheckAllSensors():
 *		returns all sensor states as 1/0 in an int
 *		sends data to GUI if any of the sensors have changed
 */
int CheckAllSensors();

 
/*
 * SetCupColor(int cupNumber, int color):
 * 		color codes should be encoded in 6 hex values.
 * 		sends the color to the GUI if it has changed
 */
void SetCupColor(int cupNumber, int color);

/*
 * SetCupColors(int* cupNumbers, int cupCount, int color):
 * 		sets all cups in the array to a specified color
 *		reports any changes in physical cup color to the GUI.
 */
void SetCupColors(int* cupNumbers, int cupCount, int color);

/*
 * FullState(): 
 * 		Prints statements for ALL current cup colors and sensors...
 */
void FullState();
/*
 * SendGUICommand(int op, int cup, int data):
 *      Sends the given command to the GUI over the socket
 */
//3 bit opcode
//5 bit cup value
//24 bits data
//ex.  000 00001 11111111 00000000 00000000  would send red to cup 1
void SendGUICommand(int op1, int op2, int data);

/*
 * char wait(unsigned int sec, unsigned int usec):
 *       Short Function to call select for us. If there is data available, it
 *       will return 1 otherwise it will return 0 (or -1 if there was an error
 *       with the select call).
 */
char wait(unsigned int sec, unsigned int usec);

/*
 * void debug_printf(char *s):
 *      Prints the passed string if pong is compiled with the debug flag
 */
void debug_printf(char *s);

#endif // __tableLibrary_h__
