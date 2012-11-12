#!/usr/bin/env python
import os
import sys
import commands

import util

################################################################
# test if server work well with correct argument
# this is a simple test
################################################################

def main():
    # 1. check if make generate an executable server file
    util.make()

    # 2. ok you got an excutable server, run it
    print ""
    print "Starting ./server 2010 1 1 FIFO"
    util.run("./server 2010 1 1 FIFO")

    clientcmd = "./testclient localhost 2010 /testdata/testfile.txt"
    print "Client: " + clientcmd
    response = commands.getoutput(clientcmd)
    if response.find("hey this is a test file") == -1:
        util.error("ouchs! client can not get the right file")
    print "Client got expected response"


    print ""
    print "Starting server 2010 1 1 SFF"
    util.run("./server 2010 1 1 SFF")
    print "Client: " + clientcmd
    response = commands.getoutput(clientcmd)
    if response.find("hey this is a test file") == -1:
        util.error("ouchs! client can not get the right file")
    print "Client got expected response"
 
    print ""
    print "Starting server 2010 1 1 SFF-BS"
    util.run("./server 2010 1 1 SFF-BS 1")
    print "Client: " + clientcmd
    response = commands.getoutput(clientcmd)
    if response.find("hey this is a test file") == -1:
        util.error("ouchs! client can not get the right file")
    print "Client got expected response"

    util.good(" Your server worked with simple requests")

    sys.exit(0)
    
if __name__ == "__main__":
    main()
