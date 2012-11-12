#include "mfs.h"
#include <stdlib.h>

int main(int argc, char* argv[]) {
  MFS_Init(argv[2], atoi(argv[1]));

  if (MFS_Lookup(0, "bin") != -1)
	  return -1;
  if (MFS_Creat(0, MFS_DIRECTORY, "bin") != 0)
	  return -1;
	int rc = MFS_Lookup(0, "bin");
	
  if (rc == -1 || rc == 0)
	  return -1;
  return 0;

}

