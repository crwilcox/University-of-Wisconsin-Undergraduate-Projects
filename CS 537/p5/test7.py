#!/usr/bin/env python
import os
import sys
import commands
import time
import thread
from threading import Thread


import util

###############################################
# test if you use conditional variables
# rather than spin wait for synchronization
###############################################


# this is ugly, should customize server startup parameters also
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


# check the source, look for any conditional synchronization primitives
# this is ugly, but i can not think a good way to test this
# other then this approach and doing a stretch test

grepresult = commands.getoutput("cat *.c | grep pthread_cond_wait")
if grepresult.find("pthread_cond_wait") == -1:
    util.error("ouchs! you don't use conditional variables for synchronization")

grepresult = commands.getoutput("cat *.c | grep pthread_cond_signal")
if grepresult.find("pthread_cond_signal") == -1:
    util.error("ouchs! you don't use conditional variables for synchronization")

# if reach here, you pass the grep phase, now come the stretch test.
# it is a good idea if i capture the latency here, and compare it to a spinwait
# algorithm, but i have no baseline. 

util.info("")
util.info("##########################################################")
util.info("GOOD! you do use conditional variables (hopefully).")
util.info("Now check if you use them correctly.")
util.info("If you see your server crashes or hang, it is not good")
util.info("##########################################################")
util.info("")


util.make()

# test FIFO server
test("./server 2010 3 4 FIFO")

# test SFF server
test("./server 2010 3 4 SFF")

# test SFF-BS
test("./server 2010 3 4 SFF-BS 2")
util.info("")
util.info("##########################################################")
util.info("GOOD! Ah, finally you got here, you are a true hacker")
util.info("##########################################################")
util.info("")
sys.exit(0)


