/* 
 * a failed call in debug mode, but it should work in non debug mode
 */

#include <stdio.h>
#include <unistd.h>
#include "mem.h"

int main()
{
  int err;
  int pagesize = 0;
  void *p;
  
  pagesize = getpagesize();    
  
  err = Mem_Init(pagesize, 1); // this should work
  if (err == -1) 
    return -1;
  
  // this should work
  p = Mem_Alloc(pagesize - 256);
  if (p == NULL) 
    return -1;

  // this should NOT work
  err = Mem_Free(p + pagesize);
  if (err != -1)
     return -1;
  return 0;
}

