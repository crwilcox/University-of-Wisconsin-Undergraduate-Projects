#!/usr/bin/env python
import os
import sys
import commands
import time
import thread
from threading import Thread


import util

#########################################
# test if you have fixed size pool of 
# worker threads
#########################################


# this is ugly, should customize server startup parameters
NUM_CLIENT = 3
CGI_SPIN_TIME = 2
EXPECTED_TIME = 3


def test(cmd):
    print ""
    print "Starting " + cmd
    util.run(cmd)
    
    start = time.time()
    
    clientlist = []

    for i in range(0, NUM_CLIENT):
        client = testit("Client-" + str(i))
        clientlist.append(client)
        client.start()
    
    for client in clientlist:
        client.join()


    end = time.time()
    print "Elapsed time (in seconds): " + str(end-start)
    if end - start < EXPECTED_TIME:
        util.error("your server does not have *fixed* size of worker pool")
    

class testit(Thread):
    def __init__ (self,clientname):
        Thread.__init__(self)
        self.clientname = clientname

    def run(self):
        clientcmd = "./testclient localhost 2010 /output.cgi"
        print self.clientname + ": " + clientcmd
        response = commands.getoutput(clientcmd)
        if response.find("Welcome to the CGI program") == -1:
            util.error(self.clientname + 
                       ":ouchs! client can not get the right file")
        print self.clientname + ":Client got expected response"


util.make()

# test FIFO server
test("./server 2010 2 3 FIFO")

# test SFF server
test("./server 2010 2 3 SFF")

# test SFF-BS
test("./server 2010 2 3 SFF-BS 2")

util.good("your server has fixed size of worker pool! congrats...!")

sys.exit(0)


