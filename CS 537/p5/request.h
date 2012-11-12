#ifndef __REQUEST_H__
#define __REQUEST_H__

#include "cs537.h"

typedef struct __worker_t{
	int id;
	int count;
	int staticReq;
	int dynamicReq;
	pthread_t thread;
} worker_t;

typedef struct __request_t{
    int connfd;
    int size;
    int is_static;
    char method[MAXLINE];
    char uri[MAXLINE];
    char version[MAXLINE];
    char filename[MAXLINE];
    char cgiargs[MAXLINE];
    int epoch;
	//statistics stuff
	unsigned int arrival;
	unsigned int dispatch;
	unsigned int read;
	unsigned int complete;
	unsigned int age;	
	//need this pointer as we need to return this each request
	worker_t* threadstats;
} request_t;



void requestHandle(request_t* thisRequest);
int requestParseURI(char *uri, char *filename, char *cgiargs);

#endif
