/* Checks for a scenario where only BEST FIT works  
   and other policies should fail.
*/

#include <stdio.h>
#include <unistd.h>
#include "mem.h"

int main()
{
    int err, i, j;
    void *p[8];
    int sizes[8] = { 88, 488, 48, 488, 288, 1456, 188, 956};
    void **ptrs[4];


    err = Mem_Init(8192, 1); // definitely, you have enough space
    if(err == -1)
        return -1;

    for(i=0; i<8; i++) {
        p[i] = Mem_Alloc(sizes[i]);
        if(p[i] == NULL)
             return -1;
    }

    ptrs[0] = &p[0];
    ptrs[1] = &p[2];
    ptrs[2] = &p[4];
    ptrs[3] = &p[6];

    for(i=0; i<4; i++) {
       err = Mem_Free(*ptrs[i]);
       if(err == -1)
             return -1;
    }

    // now i stale the free space

    p[0]= Mem_Alloc(180);
    if(p[0] == NULL)
         return -1;


    //This should work for BEST FIT and should not for others   
    p[2]= Mem_Alloc(252);
    if(p[2] == NULL)
         return -1;

//    Mem_Dump();

    return 0;
}

