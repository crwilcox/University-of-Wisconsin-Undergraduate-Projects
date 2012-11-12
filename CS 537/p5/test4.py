#!/usr/bin/env python
import os
import sys
import commands
import time
import thread
from threading import Thread


import util

#####################################
# test if your server is multithread
#####################################

NUM_CLIENT = 2
CGI_SPIN_TIME = 2
EXPECTED_TIME = NUM_CLIENT * CGI_SPIN_TIME

# number of times that the client request is not successful
# this does not to be thread safe, because if you have one fail
# you not gonna pass this test
counter = 0

# i need to do this because calling util error 
# in a thread code may not make the system quit
def ERROR(message):
    util.error(message)
    sys.exit(1)

def test(cmd):
    print ""
    print "Starting " + cmd
    util.run(cmd)

    clientlist = []
    
    start = time.time()

    for i in range(0, NUM_CLIENT):
        client = testit("Client-" + str(i))
        client.setDaemon(True)
        clientlist.append(client)
        client.start()
    
    for client in clientlist:
        client.join()

    end = time.time()

    if util.is_server_alive(cmd) == -1:
        util.error("Ouch! Server is dead!"
                   " Your bounded buffered may not be well protected");

    print "Elapsed time (in seconds): " + str(end-start)
    if end - start > EXPECTED_TIME:
        util.error("your server is not multithreaded")



class testit(Thread):
    def __init__ (self,clientname):
        Thread.__init__(self)
        self.clientname = clientname

    def run(self):
        global counter
        clientcmd = "./testclient localhost 2010 /output.cgi"
        print self.clientname + ": " + clientcmd
        response = commands.getoutput(clientcmd)
        if response.find("Welcome to the CGI program") == -1:
            ERROR(self.clientname + 
                  ":ouchs! client can not get the right file")
        print self.clientname + ":Client got expected response"
#        print counter

util.make()

# test FIFO server
test("./server 2010 2 2 FIFO")

# test SFF
test("./server 2010 2 2 SFF")

# test SFF-BS
test("./server 2010 2 2 SFF-BS 2")

util.good("! your server is multi threaded")

sys.exit(0)
