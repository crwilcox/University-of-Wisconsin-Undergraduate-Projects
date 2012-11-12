##############################################################################/
## Title:           Project 5 - Scalable Web Server
## Files:           Makefile
## Semester:        Spring 2010
##
## Authors:         Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
##                  Christopher Wilcox - cwilcox@wisc.edu
## Course:          UW-Madison CS 537 - Introduction to Operating Systems
## Instructor:      Prof. Remzi Arpaci-Dusseau
##
############################ 80 columns wide ##################################
#
# To compile, type "make" or make "all"
# To remove files, type "make clean"
#

# Makefile variables
OBJS = server.o request.o cs537.o client.o testclient.o buffer.o
TARGET = server

#PUR = purify
PUR =
CC = gcc
#CFLAGS = -DDEBUG -g -Wall -ggdb
CFLAGS = -g -Wall -ggdb

LIBS = -lpthread 

.SUFFIXES: .c .o 

# Top Level Objects
all: server client testclient output.cgi output10.cgi

# Top Level Object Dependencies
server: server.o request.o cs537.o buffer.o
	$(CC) $(CFLAGS) -o server server.o request.o cs537.o $(LIBS)

server-purify: server.o request.o cs537.o buffer.o
	$(PUR) $(CC) $(CFLAGS) -o server server.o request.o cs537.o $(LIBS)

testclient:testclient.o cs537.o
	$(CC) $(CFLAGS) -o testclient testclient.o cs537.o

client: client.o cs537.o
	$(CC) $(CFLAGS) -o client client.o cs537.o

output.cgi: output.c
	$(CC) $(CFLAGS) -o output.cgi output.c

output10.cgi: output.c
	$(CC) $(CFLAGS) -o output10.cgi output.c
	
.c.o:
	$(CC) $(CFLAGS) -o $@ -c $<

# Buffer Dependencies
buffer: buffer.h buffer.c
	$(CC) -c -fpic buffer.c -Wall -ggdb
	$(CC) -shared -o libbuffer.so buffer.o -ggdb

# Test programs
bufferTest: buffer bufferTest.c
	$(PUR) $(CC) $(CFLAGS) bufferTest.c -lbuffer -L. -lpthread -o bufferTest -Wall -ggdb

# Remove all old code
clean:
	-rm -f $(OBJS) server client output10.cgi output.cgi libbuffer.so bufferTest testclient
