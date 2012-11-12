#include "tableLibrary.h"
#include "colors.h"
#include "gameMode.h"
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

int* scores;

int GetBit(int i, int bitArray){
    //Right shift by i
    bitArray = bitArray >> i;
    //mask it off and return
    return bitArray & 0x1;
}

/* 
 * GameMode(int p1_cup_on, int p1_cup_off, int p2_cup_on, int p2_cup_off):
 *		Run the game mode... Set the colors of the cups based on sensor data
 */
void GameMode(int p1_cup_on, int p1_cup_off, int p2_cup_on, int p2_cup_off){
	//start the game
	printf("p1ON: %X p1OFF: %X p2ON: %X p2OFF: %X\n", p1_cup_on, p1_cup_off, p2_cup_on, p2_cup_off);
    
    int countOne = 0;
    int countTwo = 0;
    int i;
    int sensors;
    int samples = 4;

    while(1){
		//loop check sensors, update cups with this info, loop again
        sensors = CheckAllSensors();
		//printf("sensors: %X\n", sensors);
        for(i = 0; i < 10; i++){
			if(GetBit(i, sensors)) SetCupColor(i, p1_cup_on);
			else SetCupColor(i, p1_cup_off);
		}
		for(i = 10; i < 20; i++){
			if(GetBit(i, sensors)) SetCupColor(i, p2_cup_on);
			else SetCupColor(i, p2_cup_off);
		}
		//if team 1 has won send endgame with 1
		//void SendGUICommand(int op1, int op2, int data);
		if((sensors & 0x03FF) == 0) {
            countTwo++;
        } else {
            countTwo = 0;
        }
		if(sensors >> 10 == 0 ) {
            countOne++;
        } else {
            countOne = 0;
        }

        if(countOne > samples) {
            SendGUICommand(GAMEMODE, ENDGAME, 2);
        }
        if(countTwo > samples) {
            SendGUICommand(GAMEMODE, ENDGAME, 1);
        }
        if(wait(1, 20000)) return;
	}
	

}

