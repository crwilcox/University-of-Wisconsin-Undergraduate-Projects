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

########################################
# check the Stat-thread-count
########################################


# this is ugly, should customize server startup parameters also
NUM_CLIENT = 4
CGI_SPIN_TIME = 2
EXPECTED_TIME = 2
NUM_LOOPS = 1

count = 0 # number of time you passed

file1 = commands.getoutput("mktemp /tmp/p5.XXXXX")
file2 = commands.getoutput("mktemp /tmp/p5.XXXXX")


def test(cmd):
    global count
    util.info("")
    util.info("- Starting " + cmd)
    util.info("")
    util.run(cmd)
    
    commands.getoutput("rm -rf " + file1)
    commands.getoutput("rm -rf " + file2)
    os.system("./testclient localhost 2010 /output.cgi >> /dev/null &");
    time.sleep(0.3)
    os.system("./testclient localhost 2010 /testdata/file-sff-large.txt > %s &" % file1);
    time.sleep(0.3)
    os.system("./testclient localhost 2010 /testdata/file-sff-small.txt > %s &" % file2);
    
    time.sleep(10)
    #count1 = util.get_stat_thread_count(file1)
    #count2 = util.get_stat_thread_count(file2)
    count1 = util.get_stat(file1, "Stat-thread-count")
    count2 = util.get_stat(file2, "Stat-thread-count")

    print "count1 = " + str(count1)
    print "count2 = " + str(count2)

    if cmd.find("SFF") > 0:
        if count1 == 3 and count2 == 2:
            util.good("your Stat-thread-count is corect")
        else:
            util.error("Ouchs! Incorrect Stat-thread-count."
                       "Expected count1 = 3 and count2 = 2")

    if cmd.find("FIFO") > 0:
        if count1 == 2 and count2 == 3:
            util.good("your Stat-thread-count is corect")
        else:
            util.error("Ouchs! Incorrect Stat-thread-count!"
                       "Expected count1 == 2 and count2 ==3")
    
    util.kill()


util.make()


# test SFF server

test("./server 2010 1 4 SFF")
test("./server 2010 1 4 FIFO")


sys.exit(0)


