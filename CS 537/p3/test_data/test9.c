/* Checks whether Mem_Init() gets only the required 
   number of pages using mmap
*/

#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include "mem.h"

int main()
{
    int err;
    void *p;
    int pagesize = 0;
    char proc_file[50];
    char line[200];
    char buff[200];
    FILE *fp;
    int find=0;
    char *token=NULL;
    char end[20];
    char start[20];
    unsigned long long mem_start;
    unsigned long long mem_end;

    pagesize = getpagesize();    

    sprintf(proc_file,"/proc/%d/maps", getpid());
    err = Mem_Init(3*pagesize, 0);
    if(err == -1)
        return -1;
    
    fp = fopen(proc_file, "r");
    if(fp==NULL)
         return -1;
   
    find=0;
    while(fgets(line, 200, fp)!= NULL) {
        if(strstr(line, "/dev/zero") != NULL) {
            strcpy(buff, line);
            find++;
        }
    }

    if(find!=1) 
        return -1;
      
    token=strtok(buff, " ");
    strcpy(start,strtok(token,"-"));
    strcpy(end, strtok(NULL,"-"));
    mem_end = strtoll(end, (char **)NULL, 16);
    mem_start = strtoll(start, (char **)NULL, 16);
    
    if(((int)(mem_end-mem_start))!= (3*pagesize)) {
          return -1;
    }

    return 0;			
}

