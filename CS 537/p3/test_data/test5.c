/* Checks whether Mem_Init() returns -1 
   if sizeOfRegion is negative
*/

#include <stdio.h>
#include "mem.h"

int main()
{
    int err;

    err = Mem_Init(-20, 0);

    if (err == -1 && m_error == E_BAD_ARGS)
        return 0;
    else {
        return -1;
    }
}

