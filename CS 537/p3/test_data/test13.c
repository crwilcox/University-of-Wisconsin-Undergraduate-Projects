/* Checks whether Mem_Free() returns -1 on  
   NULL ptr
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

    p = Mem_Alloc(100);
    if(p==NULL)
         return -1;

    err = Mem_Free(NULL);
    if(err == -1)
         return 0;
    else 
         return -1;
}

