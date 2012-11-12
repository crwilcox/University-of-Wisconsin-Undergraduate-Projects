/* Checks whether Mem_Init() returns -1 
   if sizeOfRegion is zero
*/

#include <stdio.h>
#include "mem.h"

int main()
{
  int err;
  
  err = Mem_Init(0, 0);
  
  if(err == -1 && m_error == E_BAD_ARGS)
    return 0;
  else {
    //printf("\n");
    if (err != -1) 
      printf("ERROR: bad return value, expected -1 but got %d.\n", err);
    if (m_error != E_BAD_ARGS) 
      printf("ERROR: bad m_error value, expected E_BAD_ARGS.\n");      
    return -1;
  }
}

