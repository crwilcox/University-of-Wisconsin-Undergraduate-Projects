#include <stdio.h>
#include <stdlib.h>
#include "libfs.h"
#include <string.h>

int
main(int argc, char *argv[])
{
	//printf("SIZEOF INODE_t %d\n", (int)sizeof(inode_t));
	//printf("SIZEOF fs_t  %d\n", (int)sizeof(fs_t));
	//printf("SIZEOF MFS_DirEnt_t %d\n", (int)sizeof(MFS_DirEnt_t));
	fs_t* fs;
	fs = malloc(sizeof(fs_t));
	loadfs(fs, "myTestImage.image");
	
	printf("### TESTING lookupInode ###\n");
	int inode;
	
	if((inode = lookupInode(fs, 0, ".")) != 0){
	   	printf("FAILED lookup(inode=0, name='.')\n");
		printf("    inode received= %d\n", inode);
	}
	else printf("PASSED lookup(inode=0, name='.')\n");

	if((inode = lookupInode(fs, 0, "..")) != 0){
		printf("FAILED lookup(inode=0, name='..')\n");
		printf("    inode received= %d\n", inode);
	}
	else printf("PASSED lookup(inode=0, name='..')\n");


	printf("\n\n### TESTING THE getParentPath Function ###\n");
    printf("getParentPath(\"aaa/bbb/ccc/\"): %s\n", getParentPath("aaa/bbb/ccc/"));
	printf("getParentPath(\"aaa/bbb/ccc/ddd\"): %s\n", getParentPath("aaa/bbb/ccc/ddd"));
	printf("getParentPath(\"aaa/bbb/ccc/ddd/eee/\"): %s\n", getParentPath("aaa/bbb/ccc/ddd/eee/"));
	printf("getParentPath(\"aaa/bbb/cccddd/eee/fff\"): %s\n", getParentPath("aaa/bbb/ccc/ddd/eee/fff"));


    return 0;
}


