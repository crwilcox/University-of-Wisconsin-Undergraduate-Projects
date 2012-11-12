/* check if m_alloc have padding around allocate chunks
 * 
 *
 */

#include <stdio.h>
#include <unistd.h>
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
    
    if (isStalePadding(p[i], sizes[i]))
      return -1;
  }
  return 0;
}

