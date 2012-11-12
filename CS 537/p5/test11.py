#!/usr/bin/env python
import os
import sys
import commands
import time
import thread
import threading
import re
from threading import Thread


import util

#########################################
# test the stats for thread
#########################################


# this is ugly, should customize server startup parameters
NUM_CLIENT = 2
CGI_SPIN_TIME = 2

res_list = []
lock = threading.Lock()

def test(cmd):
    global res_list
    print ""
    print "Starting " + cmd
    util.run(cmd)
    
    clientlist = []
    res_list = []

    for i in range(0, NUM_CLIENT):
        client = testit("Client-" + str(i))
        clientlist.append(client)
        client.start()
    
    for client in clientlist:
        client.join()

    if util.is_server_alive(cmd) == -1:
        util.error("Ouch! Server is dead!"
                   " Your bounded buffered may not be well protected");

    sum = 0; # this sum of the thread id

    for res in res_list:
        print res
        # do sanity check
        lines = res.split("\n")
        thread_info = []
        for line in lines:
            if line.find("Stat-thread") >= 0:
                thread_info.append(line)
        
        
        # check thread id
        header, field, value = thread_info[0].split(":")
        if field.find("Stat-thread-id") < 0: 
            util.error("Uh oh! Stat-thread-id should appear in the order indicated"
                       "in the spec")
        if int(value) > 1 or int(value) < 0:
            util.error("Ouchs! Thread id should be from 0 to N-1 (N is no of threads)")
        sum = sum + int(value)

        # check thread count
        header, field, value = thread_info[1].split(":")
        if field.find("Stat-thread-count") < 0:
            util.error("Ouchs! Stat-thread-count should appear right after Stat-thread-id")
        if int(value) != 1:
            util.error("Ouchs! Stat-thread-count should be 1")
        
        # check thread static
        header, field, value = thread_info[2].split(":")
        if field.find("Stat-thread-static") < 0:
            util.error("Ouchs! Stat-thread-static should appear right after "
                       "Stat-thread-count")
        if int(value) != 0:
            util.error("Ouchs! Stat-thread-static should be 0")

        # check thread dynamic
        header, field, value = thread_info[3].split(":")
        if field.find("Stat-thread-dynamic") < 0:
            util.error("Ouchs! Stat-thread-dynamic should appear right after "
                       "Stat-thread-static")
        if int(value) != 1:
            util.error("Ouchs! Stat-thread-dynamic should be 1")

    if sum != (NUM_CLIENT-1) * NUM_CLIENT / 2:
        util.error("Ouchs! Stat-thread-id is assigned incorrectly")

    
    print ""
    print "GOOD! you passed this test"
    print ""
        

    

class testit(Thread):
    def __init__ (self,clientname):
        Thread.__init__(self)
        self.clientname = clientname

    def run(self):
        clientcmd = "./testclient localhost 2010 /output.cgi"
        print self.clientname + ": " + clientcmd
        response = commands.getoutput(clientcmd)
        lock.acquire()
        res_list.append(response)
        lock.release()

util.make()

# test FIFO server
test("./server 2010 2 2 FIFO")

# test SFF server
test("./server 2010 2 2 SFF")

# test SFF-BS
test("./server 2010 2 2 SFF-BS 2")

sys.exit(0)


