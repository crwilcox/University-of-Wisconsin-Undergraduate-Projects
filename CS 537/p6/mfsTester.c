///////////////////////////////////////////////////////////////////////////////
// Title:           Project 6 - Simple File Server
// Files:           mfsTester.c
// Semester:        Spring 2010
//
// Authors:         Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
//                  Christopher Wilcox - cwilcox@wisc.edu
// Course:          UW-Madison CS 537 - Introduction to Operating Systems
// Instructor:      Prof. Remzi Arpaci-Dusseau
//
//////////////////////////// 80 columns wide //////////////////////////////////
// INCLUDED LIBRARIES
#include "mfs.h"
#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <string.h>

#define BUFFER_SIZE				(4096 + 4096/2)		// In bits
#define MFS_BLOCK_SIZE		(4096)
#define MFS_DIRECTORY			(0)
#define MFS_REGULAR_FILE	(1)

int main(int argc, char *argv[])
{
	printf("\n>mfsTester: Testing of MFS...\n");
	// Check the args
	if(argc != 3)
	{ printf(">Usage: %s [hostname] [port]\n", argv[0]); exit(1); }
	
	/*// Trying a bad call to a non-init fs
	if(MFS_Lookup(0, "bob.txt") == 0)
		printf(">mfsTester: Error: MFS_Lookup() passed before FS init.\n\n");
	else
		printf(">mfsTester: MFS_Lookup() before MFS_INIT() acted as intended (by not working), passed!\n\n");
	*/	
	// Call a server and init fs
	if(MFS_Init(argv[1], atoi(argv[2])) != 0)
		printf(">mfsTester: Error: MFS_INIT() failed\n\n");
	else
		printf(">mfsTester: MFS_Init(%s, %s) passed!\n\n", argv[1], argv[2]);
	/*
	// Testing read on a non-existent file
	char rawData[MFS_BLOCK_SIZE];
	if (MFS_Read(52, rawData, 5) != -1)
		printf(">mfsTester: Error: MFS_Read() returned data (FAIL)\n\n");
	else
		printf(">mfsTester: Error: MFS_Read() returned file non found, (Passed!)\n\n");
	
	// Check Lookup by looking up path of root
	if(MFS_Lookup(0, "bob.txt") < 0)
		printf(">mfsTester: MFS_Lookup(0, \"bob.txt\") returned not a file (passed test!)\n\n");
	else
		printf(">mfsTester: Error: MFS_Lookup() returned a inode (FAIL!)\n\n");
		
	// Check Stat by looking up stat of non-exitent file
	MFS_Stat_t *bobStatPtr = malloc(sizeof(MFS_Stat_t));
	if(MFS_Stat(200, bobStatPtr) != 0)
	{
		printf("mfsTester: MFS_Stat() returned not a file (passed test!)\n\n");
	}
	else
	{
		printf("mfsTester: ERROR: MFS_Stat() returned stats for nonfile (FAIL)\n\n");
	}
	
	
	
	// Check Unlink command
	char rawData[MFS_BLOCK_SIZE];
	if(MFS_Unlink(0, "bob.txt") != 0)
		printf("mfsTester: Error: MFS_Unlink() failed\n\n");
	else
		printf(">mfsTester: MFS_Unlink(0, \"bob.txt\") passed!\n\n");
	// Test creating a new file in root
	if(MFS_Creat(0, MFS_REGULAR_FILE, "bob.txt") != 0)
	{
		printf("mfsTester: Error: MFS_Creat() cannot create a file in root (FAIL)\n\n");
	}
	else
		printf("mfsTester: MFS_Creat() created a file in root (passed!)\n\n");	
	
	// Testing stat on a newly created file
	int bobInode = MFS_Lookup(0, "bob.txt");
	if(bobInode <= 0)
		printf(">mfsTester: Error: MFS_Lookup() didn't returned a inode (FAIL!)\n\n");
	else
		printf(">mfsTester: MFS_Lookup returned an inode file (passed test!)\n\n");
	MFS_Stat_t *bobStatPtr = malloc(sizeof(MFS_Stat_t));	
	if(MFS_Stat(bobInode, bobStatPtr) == 0)
		printf("mfsTester: MFS_Stat() returned stats for a file (passed test!)\n\n");
	else
		printf("mfsTester: ERROR: MFS_Stat() did not returned stats for a file (FAIL)\n\n");
	
	// Testing write on file bob.txt, by reading it first, writing it, then readings it
	char recData[MFS_BLOCK_SIZE];
	if(MFS_Read(bobInode, rawData, 0) != 0)
		printf(">mfsTester: Error: MFS_Read() didn't return data (FAIL)\n\n");
	else
		printf(">mfsTester: MFS_Read() returned data: %s, (Passed!)\n\n", rawData);
	sprintf(rawData, "Is is any wonder that I don't have too much time on my hands?");
	if(MFS_Write(bobInode, rawData, 0) != 0)
		printf(">mfsTester: Error: MFS_Write() didn't write data (FAIL)\n\n");	
	else
		printf(">mfsTester: MFS_Write() wrote data correctly (Passed!)\n\n");
	sprintf(rawData, "Nobody here but us characters!");
	
	if(MFS_Read(bobInode, recData, 0) != 0)
		printf(">mfsTester: Error: MFS_Read() didn't return data (FAIL)\n\n");
	else
		printf(">mfsTester: MFS_Read() returned data: %s, (Passed!)\n\n", recData);
	
	
	// T14
	MFS_Stat_t stat;
  if (MFS_Creat(0, MFS_REGULAR_FILE, "file.txt") == -1)
	  printf(">mfsTester: ERROR: t14: 1\n\n");
  int inode = MFS_Lookup(0, "file.txt");
  if (inode == -1 || inode == 0)
	  printf(">mfsTester: ERROR: t14: 2\n\n");
  char buf[MFS_BLOCK_SIZE];
  memset(buf, 99, MFS_BLOCK_SIZE);
  if (MFS_Write(inode, buf, 0) == -1)
	  printf(">mfsTester: ERROR: t14: 3\n\n");

  if (MFS_Stat(inode, &stat) != 0)
	  printf(">mfsTester: ERROR: t14: 4\n\n");
  if (stat.type != MFS_REGULAR_FILE)
	  printf(">mfsTester: ERROR: t14: 5\n\n");
  if (stat.size !=  MFS_BLOCK_SIZE)
	  printf(">mfsTester: ERROR: t14: stat.size != MFS_BLOCK_SIZE (%i != %i)\n\n", stat.size, MFS_BLOCK_SIZE);
  if (stat.blocks != 1)
		printf(">mfsTester: ERROR: t14: stat.block != 1 (%i != 1)\n\n", stat.blocks);
	
	
	// T18
	if (MFS_Creat(0, MFS_REGULAR_FILE, "file.txt") == -1)
	  	  printf(">mfsTester: ERROR: t18: 1");

  int inode = MFS_Lookup(0, "file.txt");
  if (inode == -1 || inode == 0)
	  	  printf(">mfsTester: ERROR: t18: 2");

  char buf[MFS_BLOCK_SIZE];
  memset(buf, 99, MFS_BLOCK_SIZE);
  //printf("\n\n%s\n\n",buf);
  if (MFS_Write(inode, buf, 0) == -1)
	  	  printf(">mfsTester: ERROR: t18: 3");

  char result[MFS_BLOCK_SIZE];
  memset(result, 98, MFS_BLOCK_SIZE);
  if (MFS_Read(inode, result, 0) == -1)
	  	  printf(">mfsTester: ERROR: t18: 4");
	//printf("\n\nRARW:%s:ENDRARW\n\n", result);  	  
  if (memcmp(result, buf, MFS_BLOCK_SIZE) != 0)
	  	  printf(">mfsTester: ERROR: t18: 5\n");
	
	
	//T20
	/*
	MFS_Stat_t stat;
	  
  if (MFS_Creat(0, MFS_REGULAR_FILE, "file.txt") == -1)
	  printf(">mfsTester: ERROR: t20: 0\n");

  int inode = MFS_Lookup(0, "file.txt");
  if (inode == -1)
	  	  printf(">mfsTester: ERROR: t20: 0\n\n");

  char buf[MFS_BLOCK_SIZE];
  memset(buf, 99, MFS_BLOCK_SIZE);
  if (MFS_Write(inode, buf, 0) == -1)
	  	  printf(">mfsTester: ERROR: t20: 1\n\n");
  if (MFS_Stat(inode, &stat) == -1) 		printf(">mfsTester: ERROR: t20: 9\n\n");
  if (stat.type != MFS_REGULAR_FILE)		printf(">mfsTester: ERROR: t20: 10\n\n");
	printf(">mfsTester: stat.blocks: %i\n\n", stat.blocks);
	
  if (MFS_Write(inode, buf, 2) == -1)
	  	  printf(">mfsTester: ERROR: t20: 2\n\n");
  if (MFS_Stat(inode, &stat) == -1) 		printf(">mfsTester: ERROR: t20: 11\n\n");
  if (stat.type != MFS_REGULAR_FILE)		printf(">mfsTester: ERROR: t20: 12\n\n");
	printf(">mfsTester: stat.blocks: %i\n\n", stat.blocks);
	
  char result[MFS_BLOCK_SIZE];
  memset(result, 98, MFS_BLOCK_SIZE);
  if (MFS_Read(inode, result, 0) == -1)
	  	  printf(">mfsTester: ERROR: t20: 3\n\n");
  if (memcmp(result, buf, MFS_BLOCK_SIZE) != 0)
	  	  printf(">mfsTester: ERROR: t20: 4\n\n");

  memset(result, 98, MFS_BLOCK_SIZE);
  if (MFS_Read(inode, result, 2) == -1)
	  	  printf(">mfsTester: ERROR: t20: 5\n\n");

  if (memcmp(result, buf, MFS_BLOCK_SIZE) != 0)
	  	  printf(">mfsTester: ERROR: t20: 6\n\n");

  //MFS_Stat_t stat;
  if (MFS_Stat(inode, &stat) == -1) 		printf(">mfsTester: ERROR: t20: 7\n\n");
  if (stat.type != MFS_REGULAR_FILE)		printf(">mfsTester: ERROR: t20: 8\n\n");
  if (stat.blocks != 3)									printf(">mfsTester: ERROR: t20: 9, stat.blocks != 3 (%i != 3)\n\n", stat.blocks);
	*/
	
//##########################################################################################	

//T26
		char* dirs[] = {"dir0", "dir1", "dir2", "dir3", "dir4", "dir5", "dir6",
		"dir7", "dir8", "dir9", "dir10", "dir11", "dir12", "dir13", ".", ".."};
	int inodes[16];	
	int i;
	for (i = 0; i < 14; i++) 
	{
  	if (MFS_Creat(0, MFS_DIRECTORY, dirs[i]) == -1)	
	  	printf(">mfsTester: ERROR: t26: 0\n\n");
		inodes[i] = MFS_Lookup(0, dirs[i]);
		if (inodes[i] <= 0) 
			printf(">mfsTester: ERROR: t26: 1\n\n");
	}
	inodes[14] = MFS_Lookup(0, ".");
	if (inodes[14] < 0) 
		printf(">mfsTester: ERROR: t26: 2\n\n");
	inodes[15] = MFS_Lookup(0, "..");
	if (inodes[15] < 0) 
		printf(">mfsTester: ERROR: t26: 3\n\n");

	// double check if any inode num is reused
	int j;
	for (i = 0; i < 13; i++)
		for (j = i+1; j < 14; j++)
		if (inodes[i] == inodes[j]) 
			printf(">mfsTester: ERROR: t26.4: inode numbers being reused, inodes %d = %d    inode %d = %d\n\n", i, inodes[i], j, inodes[j]);

	// reread the dir block	
	
  char buf[MFS_BLOCK_SIZE];
  memset(buf, 0, MFS_BLOCK_SIZE);
  if (MFS_Read(0, buf, 1) == 0)
	  printf(">mfsTester: ERROR: t26: 5\n\n");
  if (MFS_Read(0, buf, 0) == -1)
	  printf(">mfsTester: ERROR: t26: 6\n\n");
	
	// sanity check
	int b[16];
	char* dirs2[16];
	int inodes2[16];
	memset(b, 0, 16);
	MFS_DirEnt_t* e;
	for (i = 0; i < 16; i ++) 
	{
		e = buf + i * sizeof(MFS_DirEnt_t);
		inodes2[i] = e->inum;
		dirs2[i] = strdup(e->name);
	}
 	for (i = 0; i < 16; i ++) 
 	{
		int fi = 0;
		int fn = 0;
		for (j = 0; j < 16; j++) 
			if (inodes[i] == inodes2[j]) {
				fi = 1;
				break;
			}
		for (j = 0; j < 16; j ++)
			if (strcmp(dirs[i], dirs2[j]) == 0) {
				fn = 1;
				break;
			}
			if (!fi || !fn) 
				printf(">mfsTester: ERROR: t26.7: Failing sanity check.\n\n");
	}

//##########################################################################################
//BEGIN TEST37
//
// MFS_Unlink: empty directory
//
/*
  MFS_Init(argv[1], atoi(argv[2]));

  // /dir1
  int rc;
  rc = MFS_Creat(0, MFS_DIRECTORY, "dir1");
  if (rc == -1) fprintf(stderr, "ERROR 0\n");

  int inum = MFS_Lookup(0, "dir1");
  if (inum <=0) fprintf(stderr, "ERROR 1\n");
	printf("inum = %d\n", inum);
	
	rc = MFS_Creat(0, MFS_REGULAR_FILE, "whatever");
	if (rc == -1) fprintf(stderr, "ERROR 2\n");
	
	int inum3 = MFS_Lookup(0, "whatever");
	printf("inum3 = %d\n", inum3);
	if (inum3 <= 0 || inum3 == inum) fprintf(stderr, "ERROR 3\n");

	rc = MFS_Creat(0, MFS_DIRECTORY, "dir2");
	if (rc == -1) fprintf(stderr, "ERROR 4\n");

	int inum2 = MFS_Lookup(0, "dir2");
	if (inum2 <=0 || inum2 == inum || inum3 == inum2) fprintf(stderr, "ERROR 5\n");

	rc = MFS_Unlink(0, "whatever");
	if (rc == -1) fprintf(stderr, "ERROR 6\n");

  rc = MFS_Unlink(0, "dir1");
  if (rc == -1) fprintf(stderr, "ERROR 7\n");

  rc = MFS_Lookup(0, "dir1");
  if (rc >= 0) fprintf(stderr, "ERROR 8\n");

	rc = MFS_Lookup(0, "dir2");
	if (rc < 0) fprintf(stderr, "ERROR 9\n");
	
	MFS_Stat_t m;	  
  rc = MFS_Stat(inum, &m);
  if (rc == 0) fprintf(stderr, "ERROR 10\n");

	rc = MFS_Stat(0, &m);
	if (rc == -1) fprintf(stderr, "ERROR 11\n");
	printf("m.size = %d\n", m.size);

// sanity check
	if (m.size != 3 * sizeof(MFS_DirEnt_t)) fprintf(stderr, "ERROR 12\n");
	char buf[MFS_BLOCK_SIZE];
	if ((rc = MFS_Read(0, buf, 0)) < 0) fprintf(stderr, "ERROR 13\n");
	int i;
	MFS_DirEnt_t* e;
	int fd2, fc, fp;
	fd2 = fc = fp = 0;
	for (i = 0; i < 5; i++) {



		e = buf + i * sizeof(MFS_DirEnt_t);
		printf("e.inum = %d\n", e->inum);	
		if (e->inum >= 0) {
			if (strcmp(e->name, ".") == 0) fc = 1;
			if (strcmp(e->name, "..") == 0) fp = 1;
			if (strcmp(e->name, "dir2") == 0) fd2 = 1;
		}	
	}

	if (!fc || !fp || !fd2) fprintf(stderr, "ERROR 14\n");
//END TEST37
//##########################################################################################	
*/
	printf(">mfsTester: Testing of MFS is Complete!\n\n");
	return 0;
}
