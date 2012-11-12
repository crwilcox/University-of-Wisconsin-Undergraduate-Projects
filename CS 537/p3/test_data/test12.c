/* Checks whether Mem_Alloc() returns NULL if  
   size is zero
*/

#include <stdio.h>
#include <unistd.h>
#include "mem.h"

int main()
{
    int err;
    void *p;
    int pagesize = 0;

    pagesize = getpagesize();    

    err = Mem_Init(pagesize, 0);

    if(err == -1)
        return -1;

    p = Mem_Alloc(0);

    if(p==NULL)
         return 0;
    else
         return -1;
}

