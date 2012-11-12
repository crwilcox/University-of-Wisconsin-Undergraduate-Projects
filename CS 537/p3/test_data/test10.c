/* Checks whether Mem_Alloc() returns a 
   word-aligned pointer
*/

#include <stdio.h>
#include "mem.h"

int main()
{
    int err;
    void *p=NULL;
    void *q=NULL;

    err = Mem_Init(2000, 0);

    if(err == -1)
        return -1;

    p = Mem_Alloc(2);
    if(p==NULL)
         return -1;

    q = Mem_Alloc(100);
    if(q==NULL)
         return -1;

    if((int)q%4)
         return -1;
    else
         return 0;
}

