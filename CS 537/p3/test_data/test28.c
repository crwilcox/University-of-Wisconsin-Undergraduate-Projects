/* Checks that merging of contiguous locations works
   well.
*/

#include "p3.h"

int main()
{
    int err, i, j;
    void *p[8];
    int sizes[8] = { 84, 484, 284, 484, 384, 1212, 84, 952};

    err = Mem_Init(4096, 0);
    if(err == -1)
        return -1;

    for(i=0; i<8; i++) {
        p[i] = Mem_Alloc(sizes[i]);
        if(p[i] == NULL)
             return -1;
	//	Mem_Dump();
    }

    err = Mem_Free(p[5]);
       if(err == -1)
             return -1;
    err = Mem_Free(p[1]);
       if(err == -1)
             return -1;
    err = Mem_Free(p[6]);
       if(err == -1)
             return -1;
    err = Mem_Free(p[3]);
       if(err == -1)
             return -1;
    err = Mem_Free(p[2]);
       if(err == -1)
             return -1;

       //       Mem_Dump();
    p[1]= Mem_Alloc(1256);
    if(p[1] == NULL)
         return -1;
    //    Mem_Dump();
    
    p[5] = Mem_Alloc(1288);
    if(p[5] == NULL)
         return -1;

    err = Mem_Free(p[7]);
       if(err == -1)
             return -1;
    err = Mem_Free(p[5]);
       if(err == -1)
             return -1;
    err = Mem_Free(p[4]);
       if(err == -1)
             return -1;
       //       Mem_Dump();
    p[4]= Mem_Alloc(2680);
    if(p[4] == NULL)
         return -1;

    err = Mem_Free(p[0]);
       if(err == -1)
             return -1;
    err = Mem_Free(p[1]);
       if(err == -1)
             return -1;
       //       Mem_Dump();
    p[0]= Mem_Alloc(1328);
    if(p[0] == NULL)
         return -1;
    

    return 0;
}

