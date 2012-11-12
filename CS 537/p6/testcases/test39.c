#include "mfs.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
//
// Combination test
//

int main(int argc, char* argv[]) {
  MFS_Init(argv[2], atoi(argv[1]));

  // /dir1
  int rc;
  rc = MFS_Creat(0, MFS_DIRECTORY, "dir");
  if (rc == -1) return -1;


  int pinum = MFS_Lookup(0, "dir");
  if (pinum <=0) return -1;

  int inum[100]; // inum of file 0-99

  // creating a bunch of file
  int i,j;
  char base[] = "file";
  char filename[10];
  char buf[MFS_BLOCK_SIZE];
  int numfile = 20;
  int numblock = 10;
  memset(buf, 99, MFS_BLOCK_SIZE);
  for (i = 0; i < numfile; i++) {
    sprintf(filename, "%s%d", base, i);
    //printf("filename = .%s.\n", filename);
    rc  = MFS_Creat(pinum, MFS_REGULAR_FILE, filename);
    if (rc == -1) return -1;
    inum[i] = MFS_Lookup(pinum, filename);
    if (inum[i] <= 0) return -1;
    
    for (j = 0; j < numblock; j++) {
      rc = MFS_Write(inum[i], buf, j);
      if (rc == -1) return -1;
    }

  }

  // now double check dir and file99
  MFS_Stat_t m;
  rc = MFS_Stat(pinum, &m);
  printf ("size = %d\n", m.size);
  printf ("block = %d\n", m.blocks);
  if (m.type != MFS_DIRECTORY ||
      m.size != (numfile +2) * sizeof(MFS_DirEnt_t))// ||
      //      m.blocks != (int) (102 * sizeof(MFS_DirEnt_t)) >> 12)
    return -1;  // do we need to check for the blocks

  
  
  return 0;
}
