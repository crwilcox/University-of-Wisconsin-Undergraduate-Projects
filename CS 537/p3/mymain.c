/*
* File:		main.c
* Date:		February 21, 2010
* Author: 	Christopher Wilcox cwilcox@wisc.edu
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

void printMemory(int* pointer, int length);

int main(int argc, char *argv[])
{
	printf("\nInitial Mem_Dump();\n");
	Mem_Dump();

	printf("\nMem_Init(4096, 1)\n");
	if( Mem_Init(4096, 1) != 0) printf("MEM_INIT FAILS"); 
	Mem_Dump();

	printf("\nMem_Init(6666, 0); (SHOULD FAIL)\n");
	if( Mem_Init(6666, 0) != 0) printf("MEM_INIT FAILS\n"); 
	Mem_Dump();

	printf("\n1: Mem_Alloc(32)\n");
	int *firstSpace 	= (int*) Mem_Alloc(32);
	Mem_Dump();

	printMemory(firstSpace-16, 48);
	
	printf("\nMem_Free(firstSpace) [32]\n");
	Mem_Free(firstSpace);
	Mem_Dump();

	printMemory(firstSpace-16, 48);
/*
	printf("\n2: Mem_Alloc(62); (SHOULD ASSIGN 64[WORD ALIGNED])\n");
	int *secondSpace 	= (int*) Mem_Alloc(62);
	Mem_Dump();
	
	printf("\n3: Mem_Alloc(128)\n");
	int *thirdSpace		= (int*) Mem_Alloc(128);
	Mem_Dump();

	printf("\n4: Mem_Alloc(16)\n");
	int *fourthSpace	= (int*) Mem_Alloc(16);
	Mem_Dump();

    printf("\n4: Mem_Alloc(3816)\n");
	int *fifthSpace    = (int*) Mem_Alloc(3816);
	Mem_Dump();
	
	printMemory(firstSpace, 40);

	printf("\n----------------------------------\n");
	printf("MEMORY SHOULD BE FULL");
	printf("\n----------------------------------\n");



	printMemory(firstSpace,40);

	printf("\nMem_Free(thirdSpace) [128]\n");
	Mem_Free(thirdSpace);
	Mem_Dump();

	printf("\nMem_Free(secondSpace) [64]\n");
	Mem_Free(secondSpace);
	Mem_Dump();

	printMemory(secondSpace-8, 40);

	printf("\nMem_Free(fourthSpace) [16]\n");
	Mem_Free(fourthSpace);
	Mem_Dump();

	printf("\nMem_Free(fifthSpace) [3816]\n");
	Mem_Free(fifthSpace);
	Mem_Dump();


	printMemory(firstSpace, 40);
*/
	}

void printMemory(int* pointer, int length){
	printf("\n#########################################################\n");
	int* ptr = (int*)(pointer) - 2;//go two byes forward, as that is where declaration info is
	int count = 0;
	for(;count < length; count = count+1){
		printf("address:  %-8p   val:%-12d 0xval:%x\n", (int*)(ptr+count), *(int*)(ptr+count), *((unsigned long*)(ptr+count)));
	}
	printf("#########################################################\n");
}
