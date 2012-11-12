#ifndef __libfs_h__
#define __libfs_h__

#include "mfs.h"

#define INODEBMSIZE			512
#define DATABLOCKBMSIZE		512
#define DATABLOCKSIZE		4096*4096

typedef struct __inode_t{
	int type;//MFS_DIRECTORY(0) or MFS_REGULAR(1)
	int size;//number of bytes in the file, if a directory, set to be number of objects in the directory
	int blocks;//number of blocks allocated to the file
	int pointers[10];//10 direct pointer to data blocks
}inode_t;

typedef struct __fs_t{
	unsigned char inodeBitmap[INODEBMSIZE];//512*8 = 4096 bits to flip
	unsigned char datablockBitmap[DATABLOCKBMSIZE];//512*8 = 4096 bits to flip
	struct __inode_t inodes[4096];//4096 inodes
	unsigned char datablocks[DATABLOCKSIZE];//4096 data blocks, each 4096 bytes in size
}fs_t;

int loadfs(fs_t* fs, char* imagename);
int creatfs(fs_t* fs, int parentInode, int type, char* name);
int writefs(fs_t* fs, int inode, int block, unsigned char* data );
int readfs(fs_t* fs, int inode, int block, unsigned char* data);
int lookupInode(fs_t* fs, int startInode, char* path);
int unlinkfs(fs_t* fs, int startInode, char* path);
inode_t getInodeInfo(fs_t* fs, int inodeNumber);
char* getParentPath(char* path);
#endif //__libfs_h__
