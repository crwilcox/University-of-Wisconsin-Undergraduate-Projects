/*
* File:		mysh.c
* Date:		February 3, 2010
* Author: 	Christopher Wilcox cwilcox@wisc.edu
*/

#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

/* METHOD PROTOTYPES */
void printError();
int handleUserInput(char*);


int main(int argc, char *argv[])
{
	FILE *inputFile;
	FILE *outputFile;

	char input[1000];/* 512 bytes in, 1 byte for '\n', 1 byte for '\0'.        */
	/* I gave some buffer room, as it shouldnt affect things */
	
	if(argc > 2) {printError(); exit(1);} //TOO MANY ARGUMENTS
	else if (argc == 2){//BATCH MODE
		inputFile = fopen(argv[1], "r");
		if(inputFile == NULL) {printError(); exit(1);}
	}
	else{//argc <= 1  // NORMAL OPERATION
		inputFile = stdin;
	}
	
	outputFile = stdout;

mainloop:
	while(1){
		if(inputFile == stdin) write(STDOUT_FILENO, "mysh> ", 6); //DONT PRINT FOR BATCH MODE
		fgets(input, 1000, inputFile);/* Get User Input */
		if(inputFile != stdin) {//for Batch Mode
			if(feof(inputFile)) {exit(0);}//if EOF, end batch
			write(STDOUT_FILENO, input, strlen(input));//Write the command being run
		}	
		if(strlen(input) >= 512){printError(); goto mainloop;}//ARGUMENT TOO LONG, GO BACK TO START OF LOOP, AND COMPLETE NEXT ARGUMENT
		input[strlen(input)-1] = '\0'; /* Removes '\n' from end of fgets returned value */
		
		int i;
		for(i = 0; i<strlen(input); i++){//replace all tabs with spaces to avoid errors
			if(input[i] == '\t') {	input[i] = ' ';}
		}
		
		int returnCode = handleUserInput(input);
		if( returnCode != 0){//THERE WAS AN ERROR
			printError();
			if(returnCode == 25) exit(1);
			
		}
		wait(NULL);
		
		
	}//go back to giving a prompt to the user

	return(0);/* return 0 to indicate all went well */ 
}

void printError(){
	char error_message[30] = "An error has occurred\n";
	write(STDERR_FILENO, error_message, strlen(error_message));
}

int handleUserInput(char* input){
	//returns 0 if all is well
	//returns 25 if the whole shell should die
	//any other return signifies an error
	
	/* Split User Input to Individual Items ie. A + B; C + D becomes 4 independent items. 
	*  Then Process The Commands
	*/
	char *tokenizerInput = input;
	char *semiDelim, *ptrSemiDelim;
	char *plusDelim, *ptrPlusDelim;
	char *spaceDelim, *ptrSpaceDelim;
	char *caratDelim, *ptrCaratDelim;
	char *args[20];
	char *grepArgs[4];//need the extra for error checks
	int outputFD = -1;

	//This Area splits at the ';'
	for(; ; tokenizerInput = NULL) {
		semiDelim = strtok_r(tokenizerInput, ";", &ptrSemiDelim);
		if (semiDelim == NULL){ break; }
		
		//split at the '+'
		for (; ; semiDelim = NULL) {
			plusDelim = strtok_r(semiDelim, "+", &ptrPlusDelim);
			if (plusDelim == NULL){ break; }
			
			char *p, *q;
			p = strrchr(plusDelim, '>');
			if(p-plusDelim+1 == strlen(plusDelim)){printError(); return 0;} 		  // There was no args after the '>'
			q = strrchr(plusDelim, '=');
			if(q-plusDelim+1 == strlen(plusDelim)){printError(); return 0;} 		  // There was no args after the '='
			if(p != NULL && q != NULL){printError(); return 0;}//There was both version of redirection

			//CHECK FOR REDIRECTION BEGIN
			int k = 0;
			char* caratString = NULL;
			char* redirFile = NULL;
			for(; ; k++, plusDelim= NULL){
				caratDelim = strtok_r(plusDelim, ">", &ptrCaratDelim);
				if(caratDelim == NULL) { break; }
				if(k >= 2) {printError(); return 0;}//too much input
				else if(k == 0){
					caratString = caratDelim;
				}
				else if(k == 1){
					redirFile = caratDelim;
				}
			}
			//CHECKS FOR TOO MANY ARGS... ie input > out > badcommand
			if(redirFile != NULL){
				int m = 0;
				char* redirFileCopy = redirFile;
				char *RedirSpaceDelim, *ptrRedirSpaceDelim;
				for(; ;m++, redirFileCopy = NULL){//split at the spaces, check to make sure we only have one argument after >
					RedirSpaceDelim = strtok_r(redirFileCopy, " ", &ptrRedirSpaceDelim);
					if(RedirSpaceDelim == NULL){ 
						if(m ==0){printError(); return 0;}
						break; }
					if(m == 0 ) {
						redirFile = RedirSpaceDelim;
					}
					else{ printError(); return 0;}
				}
				RedirSpaceDelim = NULL;
			}
			//CHECK FOR REDIRECTION END

			//CHECK FOR GREP BEGIN
			grepArgs[0] = NULL;
			grepArgs[1] = NULL;
			grepArgs[2] = NULL;
			//caratString is the data before >
			int n = 0;
			//char* equString = NULL;
			char* caratStringCopy = caratString;
			char *equDelim, *ptrEquDelim;
			for(; ; n++, caratStringCopy= NULL){
				equDelim = strtok_r(caratStringCopy, "=", &ptrEquDelim);
				if(equDelim == NULL) { break; }
				if(n >= 3) {printError(); return 0;}//too much input
				grepArgs[n] = equDelim;
			}
			if(grepArgs[0] != NULL) caratString = grepArgs[0];//set the command as issued by grep to the caratString
			if(grepArgs[1] != NULL){
			}
			if(grepArgs[2] != NULL){//remove misc spaces
				char *ptrGrepSpaceDelim, *grepSpaceDelim;
				int j = 0;
				char* stringToParse = grepArgs[2];
				for(; ;j++, stringToParse = NULL){//split at the spaces
					grepSpaceDelim = strtok_r(stringToParse, " ", &ptrGrepSpaceDelim);
					if(grepSpaceDelim == NULL){	break; }
					if(j==0){	grepArgs[2] = grepSpaceDelim;}
					else { printError(); return 0;}
				}
			}
			//CHECK FOR GREP END		
			
			int j = 0;
			for(; ;j++, caratString = NULL){//split at the spaces, making the arg list for execvp
				spaceDelim = strtok_r(caratString, " ", &ptrSpaceDelim);
				if(spaceDelim == NULL){	break; }
				args[j] = spaceDelim;
			}
			spaceDelim = NULL;
			args[j] = NULL;//IMPORTANT, IS NEEDED BY execvp()
			
			if(args[0] == NULL || !strcmp(args[0], " ")){}//if a blank line entered, do nothing
			else if(!strcmp("exit", args[0])) exit(0);		
			else if(!strcmp("pwd", args[0])){
				char cwdbuf[512];
				if(getcwd(cwdbuf, 512) == NULL || args[1] != NULL){ printError(); return 0;}
				else{
					write(STDOUT_FILENO, cwdbuf, strlen(cwdbuf));
					write(STDOUT_FILENO, "\n", 1);
				}
			}
			else if(!strcmp("cd",  args[0])){
				int returnValue = 0;
				if(args[1] != NULL) returnValue = chdir(args[1]);	
				else returnValue = chdir(getenv("HOME"));
				
				if (returnValue == -1){	printError(); return 0;}
			}
			else{/* START FORK AND EXEC */
				errno=0;
				pid_t cpid = fork();
				if (cpid ==0){// (rc=0) --> child					

					//IS THIS GREP?
					if(grepArgs[1] != NULL){//KEYWORD PROVIDED
						if(grepArgs[2] != NULL){//OUTFILE PROVIDED
							
							int fds[2];
							pipe(fds);
							
							pid_t grepCpid = fork();
							if(grepCpid ==0){//DO EXEC(USR)
								close(fds[0]); //CLOSE PIPE READ, WE WRITE HERE
								dup2(fds[1], STDOUT_FILENO);
								if(execvp(args[0], args) == -1) {printError();	exit(0);}	
							}
							else{//DO EXEC(GREP)
								close(fds[1]);//CLOSE PIPE WRITE, WE READ IN GREP
								//SETUP OUTPUT FILE
								outputFD = open(grepArgs[2], O_WRONLY | O_TRUNC | O_CREAT, S_IRUSR | S_IRGRP | S_IWGRP | S_IWUSR);
								if(outputFD == -1) {printError(); exit(0);}
								dup2(outputFD, STDOUT_FILENO);
								
								//RUN GREP, WITH KEYWORD IN grepArgs[1], Input is STDIN by default AND OUTPUT TO STDOUT(outputFD)
								dup2(fds[0],STDIN_FILENO);
								grepArgs[0] = "grep";
								grepArgs[2] = NULL;//signals end of arguments
								if(execvp(grepArgs[0], grepArgs) == -1) {printError(); exit(0);}
							}
						}
						else{printError(); exit(0);}//WAS MISSING THE OUTFILE
					}
					else{//no grep, just go
						if(redirFile != NULL){
							outputFD = open(redirFile, O_WRONLY | O_TRUNC | O_CREAT, S_IRUSR | S_IRGRP | S_IWGRP | S_IWUSR);
							if(outputFD == -1) {printError(); return 0;}
							dup2(outputFD, STDOUT_FILENO);
						}
						if(execvp(args[0], args) == -1) {//if -1, an error occured
							printError();
							exit(0);
						}	
						
					}
				}		
			}
		}//endsplit by +
		wait(NULL); // Wait until both a and b of a+b; finish
	}//end split by ;
	wait(NULL); // Need this second wait to  get proper handling of events
	return 0; //end of method... all is well... do not print an error
}
