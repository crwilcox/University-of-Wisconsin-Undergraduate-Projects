/* Checks whether Mem_Init() returns -1 
   if policy is one of the 3 defined  
*/

#include <stdio.h>
#include "mem.h"

int main()
{
    int err;

    err = Mem_Init(100, 3);

    if(err == -1)
        return 0;
    else
        return -1;
}

