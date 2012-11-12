/* 
 * Mem_Free with stale padding
 *
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
  int sizes[6] = {88, 488, 48, 488, 1456, 188};
  void **ptrs[4];
  
  err = Mem_Init(4096, 1);
  if(err == -1)
    return -1;
  
  for(i=0; i<6; i++) {
    p[i] = Mem_Alloc(sizes[i]);
    if(p[i] == NULL)
      return -1;
    // now corrupt the the padding, at the other end
    memset(p[i] + sizes[i], 0xAA, 32);
  }

  // call mem free and expect an error return
  int corrupted = 6;
  for (i = 0; i < 6; i++) {
    err = Mem_Free(p[i]);
    if (err == -1 && m_error == E_PADDING_OVERWRITTEN) {
      corrupted--;
    }
    else {
      // goto fail
    }
  }
  
  if (corrupted == 0) return 0;
  else return -1;

  return 0;
}

