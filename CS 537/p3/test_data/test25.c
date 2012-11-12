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
  
  // this should not work
  p = Mem_Alloc(pagesize - 128);
  if (p == NULL) 
    return 0;

  return -1;
}

