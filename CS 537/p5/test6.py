#!/usr/bin/env python
import os
import sys
import commands
import time
import thread
from threading import Thread


import util

###############################################
# test if you use lock to protect
# your bound buffer correctly.
# if your server crash or if  you see
# a long hanging, there is a good chance
# that your bounded buffer is not well protected
################################################


# this is ugly, should customize server startup parameters
NUM_CLIENT = 10



def test(cmd):
    util.info("")
    util.info("- Starting " + cmd)
    util.info("")
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
    util.info("Elapsed time (in seconds): " + str(end-start))

    if util.is_server_alive(cmd) == -1:
        util.error("Ouch! Server is dead!"
                   " Your bounded buffered may not be well protected");

    

class testit(Thread):
    def __init__ (self,clientname):
        Thread.__init__(self)
        self.clientname = clientname

    def run(self):
        clientcmd = "./testclient localhost 2010 /testdata/testfile.txt"
        util.info(self.clientname + ": " + clientcmd)
        response = commands.getoutput(clientcmd)
        if response.find("hey this is a test file") == -1:
            util.error(self.clientname + 
                       ":ouchs! client can not get the right file")
        print self.clientname + ":Client got expected response"


util.make()

# test FIFO server
test("./server 2010 3 4 FIFO")

# test SFF server
test("./server 2010 3 4 SFF")

# test SFF-BS
test("./server 2010 3 4 SFF-BS 2")
util.info("")
util.info("##########################################################")
util.info("GOOD! if you see this message, your server does not crash!")
util.info("##########################################################")
util.info("")
sys.exit(0)


