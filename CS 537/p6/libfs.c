#include "libfs.h"
#include "mfs.h"
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/*
 * this library is meant to do most of the heavy lifting.  it will deal
 * with the filesystem and all that... what it will enable is us to separate the server, 
 * which should really just be handling requests, and the lifting and modifying of the 
 * filesystem... the goal is that this will keep it cleaner, and make coding easier
 */
#define FALSE 0
#define TRUE 1

char* pathToDisk = NULL;

/* HELPER FUNCTIONS */
//function to set a bit of the inodeBitmap
void setInodeBitmap(fs_t* fs, int bitNumber, int newValue){
	if(bitNumber > 4095 || bitNumber < 0) return;//4096 total bits, so bit number must be 0-4095
	if(newValue > 1 || newValue < 0) return;//new value must be 0 or 1
	//8 bits per byte, 512 bytes
	int byte = bitNumber/8;//divide bitnumber by 8 to find the correct byte to look at
	int bit = bitNumber%8;//mod the bitnumber by 8 to find which bit in the byte to look at
	//need to clear the bit to a 0, and then set it to the appropriate bit...
	int clearValue = ~(1 << bit);// make the pattern, for example 1111011, and clear a bit
	int setValue = newValue << bit;
	fs->inodeBitmap[byte] = fs->inodeBitmap[byte] & clearValue;//clears our bit
	fs->inodeBitmap[byte] = fs->inodeBitmap[byte] | setValue;//sets the bit	
}
//function to flip a bit of the datablockBitmap
void setDatablockBitmap(fs_t* fs, int bitNumber, int newValue){
	if(bitNumber > 4095 || bitNumber < 0) return;//4096 total bits, so bit number must be 0-4095
	if(newValue > 1 || newValue < 0) return;//new value must be 0 or 1
	//8 bits per byte, 512 bytes
	int byte = bitNumber/8;//divide bitnumber by 8 to find the correct byte to look at
	int bit = bitNumber%8;//mod the bitnumber by 8 to find which bit in the byte to look at
	//need to clear the bit to a 0, and then set it to the appropriate bit...
	int clearValue = ~(1 << bit);// make the pattern, for example 1111011, and clear a bit
	int setValue = newValue << bit;
	fs->datablockBitmap[byte] = fs->datablockBitmap[byte] & clearValue;//clears our bit
	fs->datablockBitmap[byte] = fs->datablockBitmap[byte] | setValue;//sets the bit	
}

//helper for the findFreeInode and findFreeDatablock methods
//dig through trying to find which of the 8 bits is open
int findFreeBit(unsigned char myByte){
	int i;
	for(i=0;i<8;i++){
		//AND a position with 00000001-to-1000000 
		//if at any point this is 0, we found a blank space... return i
		if(  (myByte & (0x01 << i)) == 0  ) return i;
	}
	return -1;
}

int isBitSet(unsigned char* bitArray, int bitNumber){
	unsigned char test = 0x1;
	test = test << bitNumber;
	if ((*bitArray & test) == 0) return FALSE;
	return TRUE;
}

//finds a free inode and returns the int for it
int findFreeInode(fs_t* fs){
	//if a char that is pointed to is not equal to 0xFF, then there is at least one free inode, then we can find it...
	int i;
	for(i=0;i<511;i++){
		if((fs->inodeBitmap[i] != 0xFF)){
			int bitOffset = findFreeBit(fs->inodeBitmap[i]);
			if(bitOffset == -1) {fprintf(stderr, "SOMEHOW GOT HERE?"); return -1;}
			//fprintf(stderr, "   found bit offset: %d, current i: %d\n", bitOffset, i);	
			int freeInode = (i*8) + bitOffset;
			return freeInode;
		}
		//fprintf(stderr, "inodeBitmap[%d] is full\n", i);
	}
	return -1;
}

//finds a free data block and returns an int for it
int findFreeDatablock(fs_t* fs){
	//if a char that is pointed to is not equal to 0xFF, then there is at least one free datablock, then we can find it...
	int i;
	for(i=0;i<511;i++){
		if(fs->datablockBitmap[i] != 0xFF){
			int bitOffset = findFreeBit(fs->datablockBitmap[i]);
			if(bitOffset == -1) {fprintf(stderr, "SOMEHOW GOT HERE?"); return -1;}
			
			int freeBlock = i + bitOffset;
			return freeBlock;
		}
	}
	return -1;
}
void initializeDirectory(MFS_DirEnt_t* directory, int currentInode, int parentInode){
	//each block is 4096 and each MFS_DirEnt_t is 256... that is 16 entries per block
	int i;
	for(i=0;i<16;i++){
		directory[i].inum = -1;
	}

	directory[0].inum = currentInode;
	strcpy(directory[0].name, ".");
	
	directory[1].inum = parentInode;
	strcpy(directory[1].name, "..");
	
	/*
	fprintf(stderr, "PRINTING AFTER initializeDirectory\n");
	for(i=0;i<16;i++){
		fprintf(stderr, "name: %15s inum: %d\n", directory[i].name, directory[i].inum);
	}
	*/


}



int generatefs(char* path){
	//this method will generate a new fs image at the path given
	FILE* file = fopen(path, "w+");

	fs_t* fs;
	fs = malloc(sizeof(fs_t));

	//initially, everything should be blanked out (0s for both bitmaps)
	int i;
	for(i=0;i<INODEBMSIZE;i++)fs->inodeBitmap[i] = 0;			//this initializes the Inode Bitmap to all 0s
	for(i=0;i<DATABLOCKBMSIZE;i++)fs->datablockBitmap[i] = 0;	//this initializes the Datablock Bitmap to all 0s
	//set all inodes to be safe values
	for(i=0;i<4096;i++){
		inode_t inode = fs->inodes[i];
		inode.type = -1;
		inode.size = 0;
		inode.blocks = 0;
		int j;
		for(j=0;j<10;j++) inode.pointers[j]=-1;
	}
	//set all data to have safe values
	for(i=0;i<(4096*4096);i++){
		fs->datablocks[i] = 0;
	}

	setInodeBitmap(fs, 0, 1);
	setDatablockBitmap(fs, 0, 1);
	fs->inodes[0].type = MFS_DIRECTORY;
	fs->inodes[0].size = 256*2;
	fs->inodes[0].blocks = 1;
	fs->inodes[0].pointers[0] = 0;

	//initialize this directory
	MFS_DirEnt_t* directory = (MFS_DirEnt_t*) &fs->datablocks[fs->inodes[0].pointers[0]*4096];
	initializeDirectory(directory, 0, 0);

	//write this to disk
	//fprintf(stderr, "before fwrite in generatefs: sizeof(fs_t)= %d\n", (int)sizeof(fs_t));
	int fwriteRet = fwrite(fs, sizeof(fs_t), 1, file);
	if(fwriteRet != 1){//TMK, the return value of fwrite is the number of elements written
		fprintf(stderr, "fwrite return should be the number of elements written... uhoh\n");
	}
	fclose(file);
	free(fs);
	return 0;//I guess it was ok if we are here	
}

/* EXTERNALLY DEFINED METHODS */

inode_t getInodeInfo(fs_t* fs, int inodeNumber){
	inode_t badBaby;
	badBaby.type = -1; badBaby.size=-1; badBaby.blocks=-1;
	if(pathToDisk == NULL) return badBaby;
	if(inodeNumber < 0 || inodeNumber > 4095) return badBaby;
	if(isBitSet(fs->inodeBitmap, inodeNumber) == FALSE) return badBaby;
	
	//TODO: Comment this out later...
/*	fprintf(stderr, "getInodeInfo --> inodeNumber: %d\n", inodeNumber);
	MFS_DirEnt_t* directory = (MFS_DirEnt_t*) &fs->datablocks[4096*fs->inodes[inodeNumber].pointers[0]];
	int k;
	for(k=0;k<16;k++){
		fprintf(stderr, "    inum: %3d   name: %-10s \n", directory[k].inum, directory[k].name);
	}
*/
	return fs->inodes[inodeNumber];
}

char* getParentPath(char* path){
	char* origPath = malloc(4096);
	strcpy(origPath, path);
	char* parentPath1 = malloc(4096);
	char* parentPath2 = malloc(4096);
	
	char* token = strtok(origPath, "/");
	strcat(parentPath1, token);
	strcat(parentPath1, "/");
	int f = 0;
	while(token != NULL){
		token = strtok(NULL, "/");
		f++;
		if(token != NULL){
			if(f%2 == 0){
				strcpy(parentPath1, parentPath2);
				strcat(parentPath1, token);
				strcat(parentPath1, "/");
			}
			if(f%2 == 1){
				strcpy(parentPath2, parentPath1);
				strcat(parentPath2, token);
				strcat(parentPath2, "/");
			}
		}
    }
    if(f%2) return parentPath2;
	else return parentPath1;
}

//method will handle writing the data structure to disk
int writeToDisk(fs_t* fs, char* path){
	FILE * file;
	file = fopen(pathToDisk, "w+");
	
	int fwriteRet = fwrite(fs, sizeof(fs_t), 1, file);
	if(fwriteRet != 1){//TMK, the return value of fwrite is the number of elements written
		fprintf(stderr, "fwrite return should be the number of elements written... uhoh\n");
		return -1;
	}
	
	if(fflush(file) !=0) return -1;

	if(fclose(file) != 0) return -1;
	return 0;
}


/*EXTERNALLY DEFINED*/

/* lookupInode, given the path, will find the inode of the file you are looking for
 * As of right now, this function only handles 16 directories (14) in a directory
 */
int lookupInode(fs_t* fs, int startInode, char* path){
	if(pathToDisk == NULL) return -1;
	//will need to separate on the '/' and walk through each directory looking for the name
	if(fs == NULL || path == NULL || startInode < 0 || startInode > 4095) return -1;
	//if starting inode is not declared, we shouldnt be searching...
	if(isBitSet(fs->inodeBitmap, startInode) == FALSE) return -1;

	char* tempPath = malloc(4096);
	strcpy(tempPath, path);	
	int currentInode = startInode;
	char* tok = strtok(tempPath, "/");
	while(tok != NULL){
	//fprintf(stderr, "tok: %s\n", tok);
		// make sure tok isnt the empty string due to // sillyness
		if(strcmp(tok, "") != 0){//if we are not dealing with the empty string case
			//fprintf(stderr, "WE ARE IN LOOKUP INODE\n");
			inode_t thisInode = fs->inodes[currentInode];
			MFS_DirEnt_t* directory = (MFS_DirEnt_t*) &fs->datablocks[4096*thisInode.pointers[0]];
			
			int tempInode = -1;
			int i;
			for(i=0;i<16;i++){
				if((directory[i].name != NULL) && (strcmp(tok, directory[i].name) == 0)){//same
					tempInode = directory[i].inum;
				}
			}
			//if we cant find the requested dir/file, return -1
			if(tempInode < 0 || tempInode > 4095) return -1;
			else currentInode = tempInode;//set currentInode to the new one on the directory/file we found
		}
		tok = strtok(NULL, "/");
		//if the object was a file, and tok != NULL, there is a problem... we cant dive into a file as a directory...
		if((fs->inodes[currentInode].type == MFS_REGULAR_FILE)&& tok != NULL) return -1;
	}
	return currentInode;
}
/* opens a fs image... if one does not exist at the given path, calls the generate function */
int loadfs(fs_t* fs, char* path){
	pathToDisk = path;
	if(pathToDisk == NULL) return -1;
	FILE * file;
	//if the path given doesnt yet have an fs image, make one
	if ( (file = fopen(path, "r")) ){
		;//file exists!
	}
	else{
		generatefs(path);//file does not exist, need to generate an image
		file = fopen(path, "r"); //now open the file
		if(file == NULL){ printf("CANT OPEN THE FILE AFTER GENERATION\n"); return -1; }
	}	

	//Populate the fs_t using the fs image that is opened under file descriptor fd
	int readReturn = fread(fs, sizeof(fs_t), 1, file);
	if(readReturn != 1) { 
		fprintf(stderr, "uhoh. bad read return value\n"); //error 
		return -1;
	}
	
	fclose(file);//close the stream

	return 0;//return 0 if successful
}

/* 
 * will create a new folder of directory
 */
int creatfs(fs_t* fs, int parentInode, int type, char* name){
	if(pathToDisk == NULL) return -1;
	
	//INSPECT AND SETUP PARENT INODE STUFF
	int inode = parentInode;
	if(inode < 0 || inode > 4095) return -1;
	if(isBitSet(fs->inodeBitmap, inode) == FALSE) return -1;
	if(fs->inodes[inode].type != MFS_DIRECTORY){fprintf(stderr, "Trying to create a file with a non directory parent"); return -1;}
	MFS_DirEnt_t* directory = (MFS_DirEnt_t*) &fs->datablocks[4096*fs->inodes[inode].pointers[0]];
	
	//check if we have already made this file... if we have, dont make a double entry
	int k;
	for(k=0;k<16;k++){
		if(directory[k].inum != -1){//directory entry here
			if(!strcmp(name, directory[k].name)) return 0;//we have already added this entry... dont do it again!!
		}
	}

	//find an unused location in the directory...
	//    unused locations should have inum of -1
	int i;
	for(i=0; i<16; i++){
		if(directory[i].inum == -1){
			//we have found our open space
			//get an inode for the new object setup
			int newInode = findFreeInode(fs);	//gets an Inode for us
			setInodeBitmap(fs, newInode, 1);
				
			fs->inodes[newInode].type = type;
			//set all pointers to -1, as they are not being used yet
			int n;
			for(n=0;n<10;n++){
				fs->inodes[newInode].pointers[n] = -1;
			}
			if(type == MFS_DIRECTORY){
				//need to find data space for inodes[inode].pointer[0] to point to 		
				int datablockNum = findFreeDatablock(fs);
				setDatablockBitmap(fs, datablockNum, 1);
				fs->inodes[newInode].pointers[0] = datablockNum;
				unsigned char* dataPtr = &fs->datablocks[4096*fs->inodes[newInode].pointers[0]];
				fs->inodes[newInode].size = 512;//256*2 (256 per entry)	
				fs->inodes[newInode].blocks = 1;
				initializeDirectory((MFS_DirEnt_t*)dataPtr, newInode, inode);
			}
			else{//(type == MFS_REGULAR_FILE)
				fs->inodes[newInode].size = 0;
				fs->inodes[newInode].blocks = 0;				
			}	
			
			directory[i].inum = newInode;
			if(!strcpy(directory[i].name, name)) return -1; //non-zero results are bad. mmkay?			
		
			fs->inodes[parentInode].size = fs->inodes[parentInode].size + 256;//adding one file/directory... size needs to go up
			//fprintf(stderr, "createfs(pinum: %d, type: %d, name: %s)  placingasInode: %d\n", parentInode, type, name, newInode);
			int returnVal = writeToDisk(fs, pathToDisk);
			return returnVal; 
		}
	}
	return -1;
}

//remove a file or folder from the directory... if a directory is not empty, fail
int unlinkfs(fs_t* fs, int startInode, char* path){
	if(pathToDisk == NULL) return -1;
	//ARE startInode things ok?
	if(startInode<0 || startInode>4095) return -1;//is the startInode valid?
	if(!isBitSet(fs->inodeBitmap, startInode)) return -1;//the starting inode isnt proper
	if(fs->inodes[startInode].type != MFS_DIRECTORY) return -1;
	
	//fprintf(stderr, "1 COMMAND CALLED: unlink(fs, %d, %s)\n", startInode, path);
	//Lookup Inode, if file is gone, maybe we already unlinked...
	int inode = lookupInode(fs, startInode, path);//lookup the inode of what we need to remove
	if(inode == -1) return 0;//file/directory is not here, or has been removed, just return 0	
	if(inode<0 || inode>4095) return -1;//is the inode returned valid?
	
	//this checks if we are trying to delete a non empty directory...
	//this is the only error case... we should return -1 in this case	
	inode_t childInode = fs->inodes[inode];
	if(childInode.type == MFS_DIRECTORY){
		int j;
		int datablock;
		for(j=0;j<10;j++){//we can have 16*10 directories, if they are allocated	
			datablock = childInode.pointers[j];//get datablock for pointer (-1 if not used)
			if(datablock >= 0 && datablock <4096){
				MFS_DirEnt_t* directory = (MFS_DirEnt_t*) &fs->datablocks[4096*datablock];
				int k;
				/*
				fprintf(stderr, "DIRECTORY LISTING: %s\n", path);
				for(k=0;k<16;k++){
					fprintf(stderr, "name: %15s inum: %d\n", directory[k].name, directory[k].inum);
				}
				*/
				for(k=0;k<16;k++){
					if(directory[k].inum != -1) {
						// THE . AND .. ENTRIES DONT COUNT
						if(strcmp(".", directory[k].name) && strcmp("..", directory[k].name)){//an entry other than . and .. is present, return -1
							return -1;
						}
					}
				}
			}
		}
	}

	//TAKE CARE OF THIS INODE!
	//remove the child inode and any datablocks being used	
	//iterate through the pointers array and set the bitmap of any of those to 0
	int i;
	int datablock;
	for(i=0;i<10;i++){	
		datablock = fs->inodes[inode].pointers[i];
		if(datablock >= 0 && datablock < 4096){
			setDatablockBitmap(fs, datablock, 0);
		}
	}
	//set the inode bitmap to 0 for that position	
	setInodeBitmap(fs, inode, 0);
	
	//TAKE CARE OF THE PARENT!
	//Get parent inode
	int parentInode = lookupInode(fs, startInode, getParentPath(path));
	if(parentInode < 0 || parentInode > 4095) return -1;
	//remove the child inode from this parent inode (which is a directory)
		// walk through the directory listings, if we find the child pointed to, 
	MFS_DirEnt_t* myDirectory = (MFS_DirEnt_t*) &fs->datablocks[4096*fs->inodes[parentInode].pointers[0]];
	int j;
	for(j=0;j<16;j++){//4096/256=16 directories
		//if this is the child directory
		if(myDirectory[j].inum == inode){
			//remove the listing (inum needs to be set to -1)
			myDirectory[j].inum = -1;
			fs->inodes[parentInode].size = fs->inodes[parentInode].size - 256;//one file/directory is gone now
			int returnVal = writeToDisk(fs, pathToDisk);
			return returnVal;
		}
	}
	return -1;
}

/* this method will write a block as requested, updating underlying structures. 
 * it will also need to handle the user asking for a path of a file that doesnt yet exist...
 */
int writefs(fs_t* fs, int inode, int block, unsigned char* data){
	if(pathToDisk == NULL) return -1;
	if(inode < 0 || inode > 4095) return -1;
	if(!isBitSet(fs->inodeBitmap, inode)) return -1;
	if(block < 0 || block > 9) return -1;
	if(fs->inodes[inode].type != MFS_REGULAR_FILE) return -1;	
/*	
	fprintf(stderr, "fs->inodes[inode].pointers[block]: %d\n",fs->inodes[inode].pointers[block]);
	fprintf(stderr, "fs->inodes[inode].size: %d\n",fs->inodes[inode].size);
	fprintf(stderr, "where block: %d\n", block);
	fprintf(stderr, "where inode: %d\n", inode);
*/
	//check if the pointer is valid, if it is not, we need to get a block... size may need to be updated
	if(fs->inodes[inode].blocks==0 || fs->inodes[inode].pointers[block] == -1){//must have been invalid, need to get a block
		int k;
		//fprintf(stderr, "writefs to block: %d\n", block);
		for(k=0;k<=block;k++){
			if(fs->inodes[inode].pointers[k] == -1){
				//fprintf(stderr, "  writefs: block %d was not allocated  oldblocks: %d  oldsize: %d\n", k, fs->inodes[inode].blocks, fs->inodes[inode].size);
				int foundBlock = findFreeDatablock(fs);//find a block
				setDatablockBitmap(fs, foundBlock, 1);//set block bitmap
				fs->inodes[inode].pointers[k] = foundBlock;//set pointer to block
				
				fs->inodes[inode].blocks = (fs->inodes[inode].blocks + 1);
				fs->inodes[inode].size = (fs->inodes[inode].blocks * 4096);
				//fprintf(stderr, "  writefs: block %d                    newblocks: %d  newsize: %d\n", k, fs->inodes[inode].blocks, fs->inodes[inode].size);
			}
		}
	}
	//so we can get a pointer to the block, and add data	
	unsigned char* datablock = &fs->datablocks[4096*fs->inodes[inode].pointers[block]];
	memcpy(datablock, data, 4096);//fills 4096-byte datablock with info
	
	//NEED TO WRITE THIS STRAIGHT TO DISK!
	int returnVal = writeToDisk(fs, pathToDisk);
	return returnVal;
}

/*
 * this method will read a block as requested... it should most likely complain if 
 * the data requested does not exist
 */
int readfs(fs_t*fs, int inode, int block, unsigned char* data){
	if(pathToDisk == NULL) return -1;
	if(isBitSet(fs->inodeBitmap, inode) == FALSE) return -1;
	if(inode < 0 || inode>4095) return -1;
	if(block < 0 || block>9) return -1;
		

	//check if request block is allocated...
	if(fs->inodes[inode].pointers[block] == -1) return -1;//block was not allocated...
	if(inode == 0 && block != 0 && fs->inodes[inode].pointers[block] == 0){
		fs->inodes[inode].pointers[block] = -1;		
		return -1;//block was not allocated... somehow it flipped to a 0 when it shouldnt have been, but wrong
	}

	//find the specified block via the inode
	unsigned char* datablock = &fs->datablocks[4096*fs->inodes[inode].pointers[block]];
	
	//place the data found into char* data
	memcpy(data, datablock, 4096);//fill data with datablock info (4096*datablocks are 4096 bytes in size)

	return 0;
}
