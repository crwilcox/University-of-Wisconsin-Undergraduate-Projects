///////////////////////////////////////////////////////////////////////////////
// Title:           Project 6 - Simple File Server
// Files:           client.c
// Semester:        Spring 2010
//
// Authors:         Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
//                  Christopher Wilcox - cwilcox@wisc.edu
// Course:          UW-Madison CS 537 - Introduction to Operating Systems
// Instructor:      Prof. Remzi Arpaci-Dusseau
//
//////////////////////////// 80 columns wide //////////////////////////////////

#include <stdio.h>
#include "udp.h"

#define BUFFER_SIZE (4096)
char buffer[BUFFER_SIZE];

int
main(int argc, char *argv[])
{
    int sd = UDP_Open(20000);
    assert(sd > -1);

    struct sockaddr_in addr, addr2;
    int rc = UDP_FillSockAddr(&addr, "mumble-40.cs.wisc.edu", 10000);
    assert(rc == 0);

    char message[BUFFER_SIZE];
    sprintf(message, "hello world");
    rc = UDP_Write(sd, &addr, message, BUFFER_SIZE);
    printf("CLIENT:: sent message (%d)\n", rc);
    // If there was no error on sending, readback from the port
    if (rc > 0) 
    {
			int rc = UDP_Read(sd, &addr2, buffer, BUFFER_SIZE);
			printf("CLIENT:: read %d bytes (message: '%s')\n", rc, buffer);
    }

    return 0;
}


