#include "mfs.h"
#include <stdlib.h>
#include <string.h>

//
// MFS_Unlink: directory is not empty
//

int main(int argc, char* argv[]) {
  MFS_Init(argv[2], atoi(argv[1]));

  // /dir1
  int rc;
  rc = MFS_Creat(0, MFS_DIRECTORY, "dir1");
  if (rc == -1) return -1;

  int inum = MFS_Lookup(0, "dir1");
  if (inum <=0) return -1;

  rc = MFS_Creat(inum, MFS_REGULAR_FILE, "file1");
  if (rc == -1) return -1;

  rc = MFS_Unlink(0, "dir1");
  if (rc == 0) return -1;

  return 0;
}
