/* Stress test on different ways of allocations 
   and frees
*/

#include "p3.h"

int main()
{
    int err, i, j;
    int pagesize=0;
    char *p[250];
    int num_p;
    
    err = Mem_Init(5*4096, 0);
    if(err == -1)
        return -1;

    /* First Pass */
    /*Allocation and Free of big chunk */
    p[0] = Mem_Alloc(5*4096-16);
    for(i=0; i< 5*4096-16; i++) {
          p[0][i] = i;
    }
    err = Mem_Free(p[0]);
    if(err == -1)
        return -1;

    /*Second Pass */
    /*Allocation and Free of small chunks */
    num_p = 5*4096/128;
    for(i=0; i<num_p; i++) {
        p[i] = Mem_Alloc(112);
        if(p[i] == NULL)
             return -1;
    }
    for(i=0; i<num_p; i++) {
        err = Mem_Free(p[i]);
        if(err == -1)
             return -1;
    }

    /*Third Pass */
    for(i=0; i<num_p; i++) {
        p[i] = Mem_Alloc(112);
        if(p[i] == NULL)
             return -1;
    }
    /*Free in the reverse order */
    for(i=0; i<num_p; i++) {
        err = Mem_Free(p[num_p-i-1]);
        if(err == -1)
             return -1;
    }
       
    /*Fourth Pass */
    for(i=0; i<num_p; i++) {
        p[i] = Mem_Alloc(112);
        if(p[i] == NULL)
             return -1;
    }
    for(i=0; i<num_p; i+=4) {
        err = Mem_Free(p[i]);
        if(err == -1)
             return -1;
    }
    for(i=3; i<num_p; i+=4) {
        err = Mem_Free(p[i]);
        if(err == -1)
             return -1;
    }
    for(i=1; i<num_p; i+=4) {
        err = Mem_Free(p[i]);
        if(err == -1)
             return -1;
    }
    for(i=2; i<num_p; i+=4) {
        err = Mem_Free(p[i]);
        if(err == -1)
             return -1;
    }

    

    return 0;
}

