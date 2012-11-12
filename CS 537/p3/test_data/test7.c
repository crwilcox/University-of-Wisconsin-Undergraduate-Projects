/* Checks whether Mem_Init() returns 0 
   on proper usage
*/

#include <stdio.h>
#include "mem.h"

int main()
{
    int err;

    err = Mem_Init(100, 0);

    if(err == 0)
        return 0;
    else
        return -1;
}

