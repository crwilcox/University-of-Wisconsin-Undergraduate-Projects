/* Checks for behaviour using successive Mem_Alloc()s and Mem_Free()s  
   for BEST FIT
*/

#include <stdio.h>
#include <unistd.h>
#include "mem.h"

int main()
{
    int err, i, j;
    void *p[6];
    int pagesize = 0;
    int nr_iterations = 5;
    int size;

    pagesize = getpagesize();    

    err = Mem_Init(2 * pagesize, 0);
    if(err == -1)
        return -1;

    for(j=0; j< nr_iterations; j++) {
            size = pagesize/32;
	    for(i=0; i<6; i++) {
	         p[i] =  Mem_Alloc(size);
	         if(p[i] == NULL)
	               return -1;
	         err = Mem_Free(p[i]);
	         if(err != 0)
	               return -1;
                 size = size * 2;
	    }
    }    

    return 0;
}

