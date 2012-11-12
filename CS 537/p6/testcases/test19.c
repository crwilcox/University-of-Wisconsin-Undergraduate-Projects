#include "mfs.h"
#include <stdlib.h>
#include <string.h>

int main(int argc, char* argv[]) {
  MFS_Init(argv[2], atoi(argv[1]));

  if (MFS_Creat(0, MFS_REGULAR_FILE, "file.txt") == -1)
	  return -1;

  int inode = MFS_Lookup(0, "file.txt");
  if (inode == -1)
	  return -1;

  char buf[MFS_BLOCK_SIZE];
  memset(buf, 99, MFS_BLOCK_SIZE);
  int i = 0;
  for (;i < 10;i++){
  if (MFS_Write(inode, buf, i) == -1)
	  return -1;
  }

  char result[MFS_BLOCK_SIZE];
  memset(result, 98, MFS_BLOCK_SIZE);
  int j = 0;
  for (;j < 10;j++){
  if (MFS_Read(inode, result, j) == -1)
	  return -1;
  if (memcmp(result, buf, MFS_BLOCK_SIZE) != 0)
	  return -1;
  }

  return 0;

}

