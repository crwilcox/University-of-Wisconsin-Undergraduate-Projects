/* 
 * simple merging
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
  int sizes[6];
  void **ptrs[5];
  int pagesize;

  pagesize = getpagesize(); // normaly 4k

  for (i = 0; i < 6; i++) 
    sizes[i] = pagesize >> 2; // 1k 

  err = Mem_Init(pagesize << 1, 1); // definitely enough, 8k
  if(err == -1)
    return -1;
  
  //  Mem_Dump();

  for(i=0; i<6; i++) {
    p[i] = Mem_Alloc(sizes[i]);
    if(p[i] == NULL)
      return -1;
  }
  
  //  Mem_Dump();

  for (i=0; i < 5; i++) {
    if (i >= 2)
      ptrs[i] = &p[i+1];
    else
      ptrs[i] = &p[i];
    err = Mem_Free(*ptrs[i]);
    if (err == -1) return -1;    
  }
  
  // your mem should have 2 free chunks at this time
  
  // now stale the first free chunk
  memset(p[0] + (pagesize >> 4), 0xBB, 4);
  
  // ask for 3k, should not work
  void *tmp = Mem_Alloc(3 * (pagesize >> 2));
  if (tmp != NULL ) {
    printf("ERROR: you should not allocate 3 * %d bytes.\n", pagesize >> 2);
    printf("m_error = %s.", merrorToString(m_error));
    return -1;
  }
  return 0;
}

