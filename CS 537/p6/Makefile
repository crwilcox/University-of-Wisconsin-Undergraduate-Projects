##############################################################################/
## Title:           Project 6 - Simple File Server
## Files:           Makefile
## Semester:        Spring 2010
##
## Authors:         Tyler Lee Tucker - Tyler_DOT_LTucker_AT_gmail_DOT_com
##                  Christopher Wilcox - cwilcox@wisc.edu
## Course:          UW-Madison CS 537 - Introduction to Operating Systems
## Instructor:      Prof. Remzi Arpaci-Dusseau
##
############################ 80 columns wide ##################################
CC   = gcc
OPTS = -Wall -ggdb
#OPTS = -DDEBUG -Wall -ggdb

all: libfs mfs server client

# test executables	
test: all test.c
	$(CC) $(OPTS) test.c -lfs -L. -o test
	
mfsTester: mfs mfsTester.c
	$(CC) $(OPTS) mfsTester.c -lmfs -L. -o mfsTester
	
# this generates the target executables
server: libfs server.o udp.o 
	$(CC) -lfs -L. -o server server.o udp.o 

client: client.o udp.o
	$(CC) -o client client.o udp.o 

mfs: mfs.c mfs.h udp.o
	$(CC) $(OPTS) -c -fpic mfs.c udp.c
	$(CC) $(OPTS) -shared -o libmfs.so mfs.o udp.o

libfs: libfs.c libfs.h
	$(CC) $(OPTS) -c -fpic libfs.c
	$(CC) $(OPTS) -shared -o libfs.so libfs.o

# this is a generic rule for .o files 
%.o: %.c 
	$(CC) $(OPTS) -c $< -o $@

clean:
	rm -f *.o *.so server client mfsTester mfs test
