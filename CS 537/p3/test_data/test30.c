/* Performance test for BEST FIT 
   policy
*/

#include "p3.h"

int main()
{
    int err, i, j;
    int pagesize=0;
    char *p[8192];
    int num_p;
    struct timeval start;
    struct timeval end;
    double usec=0;
    
    err = Mem_Init(24*4096, 0);
    if(err == -1)
        return -1;

    gettimeofday(&start, NULL);
    for(j=0; j<20; j++) {
    for(i=0; i<4096; i++) {
        p[i] = Mem_Alloc(8);
        if(p[i] == NULL)
             return -1;
    }
    for(i=0; i<4096; i++) {
        err = Mem_Free(p[4095-i]);
        if(err == -1)
             return -1;
    }
    }


    for(j=0; j<20; j++) { 
    num_p = 24 * 4096 / 128;
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
    for(i=0; i<num_p; i+=4) {
        p[i] = Mem_Alloc(240); 
        if(p[i] == NULL)
             return -1;
    }
    for(i=2; i<num_p; i+=4) {
        err = Mem_Free(p[i]);
        if(err == -1)
             return -1;
    }
    for(i=0; i<num_p; i+=4) {
        err = Mem_Free(p[i]);
        if(err == -1)
             return -1;
    }
    }

    gettimeofday(&end, NULL);

    usec =  (end.tv_sec-start.tv_sec)*1000000.0 + (end.tv_usec-start.tv_usec);

    printf("Time taken -> %0.2lf us\n", usec);

    return 0;
}

