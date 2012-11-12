/* 
 * simple successful call in debug mode
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
  
  // this should work unless your header > 128byes
  p = Mem_Alloc(pagesize - 256);
  if (p == NULL) 
    return -1;

  // this should work too
  err = Mem_Free(p);
  if (err == -1) 
    return -1;

  return 0;  
}

