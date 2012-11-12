/* Checks for a scenario where only BEST FIT fails to allocate memory  
   and other policies work.
*/

#include <stdio.h>
#include <unistd.h>
#include "mem.h"

int main()
{
    int err, i, j;
    void *p[8];
    int sizes[8] = { 84, 484, 44, 484, 384, 1452, 852, 184};
    void **ptrs[4];

    err = Mem_Init(4096, 0);
    if(err == -1)
        return -1;

    for(i=0; i<8; i++) {
        p[i] = Mem_Alloc(sizes[i]);
        if(p[i] == NULL)
             return -1;
    }
//    Mem_Dump();
    
    ptrs[0] = &p[0];
    ptrs[1] = &p[2];
    ptrs[2] = &p[4];
    ptrs[3] = &p[7];

    for(i=0; i<4; i++) {
       err = Mem_Free(*ptrs[i]);
       if(err == -1)
             return -1;
//       Mem_Dump();
    }

    p[0]= Mem_Alloc(120);
    if(p[0] == NULL)
         return -1;

    p[2]= Mem_Alloc(240);
    if(p[2] == NULL)
         return -1;

//    Mem_Dump();
    //This should not work for BEST FIT and should work for others   
    p[4]= Mem_Alloc(180);
    if(p[4] != NULL)
         return -1;
    else
         return 0;

}

