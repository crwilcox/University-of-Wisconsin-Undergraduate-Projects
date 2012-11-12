/* 
 if Mem_Alloc check for corrupted free space
*/

#include <stdio.h>
#include <unistd.h>
#include "mem.h"
#include "common.h"

int main()
{
  int err, i, j;
  void *p[6];
  int sizes[6] = {1024, 1024, 1024, 1024, 1024, 1024};
  void **ptrs[3];
  
  err = Mem_Init(10240, 1); // 40k
  if(err == -1)
    return -1;
  
  for(i=0; i<6; i++) {
    p[i] = Mem_Alloc(sizes[i]);
    if(p[i] == NULL)
      return -1;
  }

  int pad = PADDING_SIZE;
  int cnt = 0; // number of 'correct' free chunk
  for (i = 0; i < 3; i++) {
    ptrs[i] = &p[2*i];
    err = Mem_Free(*ptrs[i]);
    if (err == -1) 
      return -1;
    
    // check if Mem_Free fill freespace with DEADBEEF pattern
    // assuming that you don't use more than 32bytes for your header
    // of free chunk
    if (isCorrectFreeSpace(*ptrs[i]-(pad >> 1), sizes[2*i] + pad + (pad >> 1)))
      cnt++;
  }
  

  if (cnt == 3) 
    return 0;
  else 
    return -1;
}

