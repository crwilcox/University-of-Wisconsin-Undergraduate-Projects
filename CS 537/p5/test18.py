#!/usr/bin/env python
import os
import sys
import commands
import time
import thread
import threading
from threading import Thread
from stat import *

import util

##########################################
# check stat-req-read/dispatch/complete
##########################################


file1 = commands.getoutput("mktemp /tmp/p5.XXXXX")

def test(cmd):
    util.info("")
    util.info("- Starting " + cmd)
    util.info("")
    util.run(cmd)
    
    commands.getoutput("rm -rf " + file1)
    util.info("")
    util.info("- Sending ./testclient localhost 2010 /testdata/file1.txt")

    res = commands.getoutput("./testclient localhost 2010 /testdata/file1.txt")
    arrival = util.get_stat2(res, "Stat-req-arrival")
    dispatch = util.get_stat2(res, "Stat-req-dispatch")
    read = util.get_stat2(res, "Stat-req-read")
    complete = util.get_stat2(res, "Stat-req-complete")
    print ""
    print "dispatch = " + str(dispatch)
    print "read = " + str(read)
    print "complete = " + str(complete)


    if dispatch >= 0 and read >=0 and complete >= 0 and dispatch + read <= complete:
        util.good("You passed this test")
    else:
        util.error("Expected dispatch >= 0 and read >=0 and complete >= 0 and" 
                   " dispatch + read <= complete:")

util.make()



test("./server 2010 2 4 FIFO")
test("./server 2010 2 4 SFF")
test("./server 2010 2 4 SFF-BS 2")

sys.exit(0)


