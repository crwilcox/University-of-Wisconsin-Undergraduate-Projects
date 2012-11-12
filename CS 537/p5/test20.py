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
# check arrival time
# IMPORTANT IMPORTANT: we expect the arrival time 
# NOT in microseconds
# but in miliseconds
##########################################


file1 = commands.getoutput("mktemp /tmp/p5.XXXXX")

def test(cmd):

    print "##########################################"
    print "# check arrival time"
    print "# IMPORTANT IMPORTANT: we expect the arrival time "
    print "# NOT NOT microseconds"
    print "# but IN MILIseconds"
    print "##########################################"
    
    util.info("")
    util.info("- Starting " + cmd)
    util.info("")
    util.run(cmd)
    
    commands.getoutput("rm -rf " + file1)

    res1 = commands.getoutput("./testclient localhost 2010 /testdata/file1.txt");
    time.sleep(1)
    res2 = commands.getoutput("./testclient localhost 2010 /testdata/file1.txt")
    time.sleep(1)
    res3 = commands.getoutput("./testclient localhost 2010 /testdata/file1.txt")
    time.sleep(1)
    res4 = commands.getoutput("./testclient localhost 2010 /testdata/file1.txt")

    arr1 = util.get_stat2(res1, "Stat-req-arrival")
    arr2 = util.get_stat2(res2, "Stat-req-arrival")
    arr3 = util.get_stat2(res3, "Stat-req-arrival")
    arr4 = util.get_stat2(res4, "Stat-req-arrival")
                          
    print "arrival 1 = " + str(arr1)
    print "arrival 2 = " + str(arr2)
    print "arrival 3 = " + str(arr3)
    print "arrival 4 = " + str(arr4)

#    s = 1000000 # 1 second
    s = 1000 # IN MILIseconds, i.e 1 seconds

    passed = arr1 + s <= arr2
    passed = passed and arr2 + s <= arr3
    passed = passed and arr3 + s <= arr4
    
    if passed:
        util.good("Congrats! You passed the final test")
    else:
        util.error("Come on! this is the final one... go go go !")

    util.kill()


util.make()


# test SFF server
test("./server 2010 1 4 FIFO")
test("./server 2010 1 4 SFF")
test("./server 2010 1 4 SFF-BS 2")

sys.exit(0)


