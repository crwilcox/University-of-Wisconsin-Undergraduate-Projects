/* Checks whether Mem_Init() returns -1 
   if used again after a successful call
*/

#include <stdio.h>
#include "mem.h"

int main()
{
    int err;

    err = Mem_Init(100, 0);

    if(err == -1)
        return -1;

    err = Mem_Init(100, 0);
    if(err==-1)
        return 0;
    else
        return -1;

}

