/*
* File:		reverse.c
* Date:		January 22, 2010
* Author: 	Christopher Wilcox cwilcox@wisc.edu
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>

#define START_SIZE	25
#define LINEMAX	    10	

int main(int argc, char *argv[])
{
	FILE *inputFile;
	FILE *outputFile;
	char line[LINEMAX];
	int totalLines = 16;
	int i = 0;
	char **lineArray;
	int firstGets = 1;

/* HANDLE USER INPUT & OPEN INPUT */
	if(argc == 1){/*No Args, Input to Stdin, Output to Stdout*/
		inputFile = stdin;
	}
	else if(argc == 2){ /*Input Specified, Output to Stdout*/
		inputFile = fopen(argv[1], "r");
	}
	else if(argc == 3){/*Input Specified, Output Specified*/
		struct stat status_buf1;
		struct stat status_buf2;
		stat(argv[1], &status_buf1);
		stat(argv[2], &status_buf2);
		
		if(status_buf1.st_ino == status_buf2.st_ino && status_buf1.st_dev == status_buf2.st_dev)		 
		{	
			fprintf(stderr, "Input and output file must differ\n");
			exit(1);
		}
		inputFile = fopen(argv[1], "r");
	}
	else{ /* More than Two Arguments */
		fprintf(stderr, "Usage: reverse <input> <output>\n");
		exit(1);
	}
	
	if(inputFile == NULL){ /* There was an open file error */
		fprintf(stderr, "Error: Cannot open file '%s'\n", argv[1]);
		exit(1);
	}
	
	
/*PROCESS INPUT*/	
	lineArray = (char**)malloc(sizeof(char**) * totalLines);	
	if (lineArray == NULL) {
		fprintf(stderr, "Malloc failed\n");
		exit(1);
	}
	
	while (fgets(line, LINEMAX, inputFile) != NULL)
	{
		if(i >= totalLines) { /* expands array if insufficient space */
			totalLines *= 2;
			lineArray = (char**)realloc(lineArray, sizeof(char**) * totalLines);
			if (lineArray == NULL) {
				fprintf(stderr, "Malloc failed\n");
				exit(1);
			}
		}
		if(firstGets){
			lineArray[i]=(char*) malloc(sizeof(char) * (strlen(line)+1));
			if (lineArray[i] == NULL) {
				fprintf(stderr, "Malloc failed\n");
				exit(1);
			}
			firstGets = 0;
			strcpy(lineArray[i], line);
		}
		else
		{
			lineArray[i] = (char*) realloc(lineArray[i], sizeof(char*) * ( strlen(line)+ 1 + strlen(lineArray[i])));
			strcpy(lineArray[i]+strlen(lineArray[i]), line);
		}	

		if(line[strlen(line)-1] == '\n' || feof(inputFile)){ 
		/* we have reached the end of a line or the file, increment i, and move onto the next line */
			i++;		
			firstGets = 1; /* set firstGets back to true as we will be getting another line */
		}
	}
	i--;	/* the variable 'i' was one beyond the last element */
	fclose(inputFile);
	
	
	
/*OUTPUT INFO TO SCREEN OR FILE*/	
	/* set the outputFile accordingly */
	if(argc == 3){outputFile = fopen(argv[2], "w");} /* output was specified */
	else{outputFile = stdout;}
	
	if(outputFile == NULL){
		fprintf(stderr, "Error: Cannot open file '%s'\n", argv[2]);
		exit(1);
	}
	
	for (; i>=0; i--)
	{
		fprintf(outputFile, "%s", lineArray[i]);
	}
	
	fclose(outputFile);
	free(lineArray);


	/* return 0 to indicate all went well */ 
	return(0);
}


