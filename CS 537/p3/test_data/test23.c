/* 
 * if Mem_Alloc check for corrupted free space 
 * on the way it traverses the free list
 */

#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include "mem.h"
#include "common.h"

int main()
{
  int err, i, j;
  void *p[6];
  int sizes[6] = {512, 1024, 512, 1024, 2048, 4096};
  void **ptrs[3];
  
  err = Mem_Init(40960, 1); // 40k
  if(err == -1)
    return -1;
  
  for(i=0; i<6; i++) {
    p[i] = Mem_Alloc(sizes[i]);
    if(p[i] == NULL)
      return -1;
  }

  // now go head and make some free chunk
  for (i = 0; i < 3; i++) {
    ptrs[i] = &p[2*i];
    err = Mem_Free(*ptrs[i]);
    if (err == -1) 
      return -1;
  }
  //  Mem_Dump();
  
  // stale the first two free chunk
  // assuming your book keeping info in each free chunk < 128 bytes
  memset(*ptrs[0] + 128, 0xBB, 4);
  memset(*ptrs[1] + 128, 0xBB, 4);

  // allocated new chunk, make sure 
  void *tmp = Mem_Alloc(1024);
  
  if (tmp == NULL && m_error == E_CORRUPT_FREESPACE)
    {
      return 0;
    }
  else return -1;
}

