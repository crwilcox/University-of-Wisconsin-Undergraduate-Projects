#include "mfs.h"
#include <stdlib.h>
#include <string.h>

//
// MFS_Creat: create an hierachical of dir
//

int main(int argc, char* argv[]) {
  MFS_Init(argv[2], atoi(argv[1]));

  // /dir1
  int rc;
  rc = MFS_Creat(0, MFS_DIRECTORY, "dir1");
  if (rc == -1) return -1;

  int inum1 = MFS_Lookup(0, "dir1");
  if (inum1 <= 0) return -1;

  // /dir1/dir2
  rc = MFS_Creat(inum1, MFS_DIRECTORY, "dir2");
  if (rc == -1) return -1;

  int inum2 = MFS_Lookup(inum1, "dir2");
  if (inum2 == -1) return -1;
  
  rc = MFS_Creat(inum2, MFS_REGULAR_FILE, "file3");
  if (rc == -1) return -1;

  int inum3 = MFS_Lookup(inum2, "file3");
  if (rc == -1) return -1;

  MFS_Stat_t m1, m2, m3;
  rc = MFS_Stat(inum1, &m1);
  if (rc == -1) return -1;

  rc = MFS_Stat(inum2, &m2);
  if (rc == -1) return -1;

  rc = MFS_Stat(inum3, &m3);
  if (rc == -1) return -1;

  if (m3.type != MFS_REGULAR_FILE || m3.size != 0 || m3.blocks != 0 ||
      m2.type != MFS_DIRECTORY || m2.size != 3 * sizeof(MFS_DirEnt_t) || m2.blocks != 1 || 
      m1.type != MFS_DIRECTORY || m1.size != 3 * sizeof(MFS_DirEnt_t) || m1.blocks != 1) return -1;
  
  return 0;
      
}
