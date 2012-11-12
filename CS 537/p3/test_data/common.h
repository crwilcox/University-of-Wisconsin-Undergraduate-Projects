#include <string.h>

#define PADDING_PATTERN (0xABCDDCBA)
#define PADDING_SIZE (64) // at each size
#define FREESPACE_PATTERN (0xDEADBEEF)


int isStalePadding(void *p, int datasize) {
  int *iter = (void *) p - PADDING_SIZE;

  int i;
  // first end padding                            
  for (i = 0; i < (int) (PADDING_SIZE / 4); i++) {
    if (*(iter+i) != PADDING_PATTERN)
      return 1; // free space is corrupted 
  }

  // second end padding 
  iter = (void*) p + datasize;
  for (i = 0; i < (int) (PADDING_SIZE / 4); i++) {
    if (*(iter+i) != PADDING_PATTERN)
      return 1; // free space is corrupted
  }

  return 0;
}

//**************************************************************
// check if the memory region starting from p wit size datasize
// is filled with DEADBEEF
// **************************************************************
int isCorrectFreeSpace(void *p, int size) {
  int * iter;
  int loop = (int) size>>2;
  for (iter = (int*) p; iter < (int*) p + loop; iter++)
    if ((*iter) != FREESPACE_PATTERN)
      return 0;
  return 1;
}



char* merrorToString (int m_error) {
  switch (m_error) {
  case 1: 
    return strdup("E_NO_SPACE");
  case 2: 
    return strdup("E_CORRUPT_FREESPACE");
  case 3:
    return strdup("E_PADDING_OVERWRITTEN");
  case 4:
    return strdup("E_BAD_ARGS");
  case 5:
    return strdup("E_BAD_POINTER");
  default:
    return strdup("E_UNDEFINED");
  }
}
