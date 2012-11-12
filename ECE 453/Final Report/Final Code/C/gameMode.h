/*******************************************************************************
* gameMode.h
* Summary:	
* Authors:	Christopher Wilcox
*******************************************************************************/

#ifndef __gameMode_h__
#define __gameMode_h__

/* 
 * GameMode(int p1_cup_on, int p1_cup_off, int p2_cup_on, int p2_cup_off, int seconds):
 *		Run the game mode for a certain number of seconds...  Set the colors of the cups
 */
void GameMode(int p1_cup_on, int p1_cup_off, int p2_cup_on, int p2_cup_off);
void GameMode2();

/*
 * SetScore(int team_number, int score):
 *      Set the score for team `team_number` to the score
 *      `score`
 */
void SetScore(int team_number, int score) ;

/*
 * GetScore(int team_number):
 *      Return the current teams score as an integer
 */
int GetScore(int team_number) ;


/*
 * IncScore(int team_number):
 *      Increment the score for team `team_number`
 */
void IncScore(int team_number);

/*
 * DecScore(int team_number):
 *      Decrement the score for team `team_number`
 */
void DecScore(int team_number) ;


#endif // __gameMode_h__
