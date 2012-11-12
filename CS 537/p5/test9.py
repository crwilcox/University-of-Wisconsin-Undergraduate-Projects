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
# check if you implement SFF correctly
########################################


# this is ugly, should customize server startup parameters also
NUM_CLIENT = 4
CGI_SPIN_TIME = 2
EXPECTED_TIME = 2
NUM_LOOPS = 1

count = 0 # number of time you passed

#file1 = "/tmp/file1"
#file2 = "/tmp/file2"
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
#    os.system("./testclient localhost 2010 /testdata/file-sff-large.txt > /tmp/file1 &");
    os.system("./testclient localhost 2010 /testdata/file-sff-large.txt > %s &" % file1);
#   time.sleep(0.1)
    os.system("./testclient localhost 2010 /output.cgi >> /dev/null &");
#   time.sleep(0.1)
#    os.system("./testclient localhost 2010 /testdata/file-sff-small.txt > /tmp/file2 &");
    os.system("./testclient localhost 2010 /testdata/file-sff-small.txt > %s &" % file2);


    time.sleep(3 *  CGI_SPIN_TIME)
    time.sleep(5) # this doesn't hurt
    if util.is_server_alive(cmd) == -1:
        util.error("Ouch! Server is dead!"
                   " Your bounded buffered may not be well protected");

    time1 = os.path.getmtime(file1)
    time2 = os.path.getmtime(file2)
   
    print time1
    print time2

    passed = time1 > time2

    if passed:
        print ""
        print "#####################################"
        print "GOOD! you implement SFF correctly"
        print "#####################################"
        print ""
        count = count + 1
    else:
        print ""
        print "#####################################"
        print "Oh oh! ERROR ERROR!"
        print "SFF is not implemented correctly"
        print "#####################################"
        print ""
        sys.exit(-1)

    util.kill()


util.make()


# test SFF server
for i in range(0, NUM_LOOPS):
    print ""
    print "#############################"
    print "- %s iteration" % str(i)
    print "#############################"
    print ""
    test("./server 2010 1 4 SFF")


sys.exit(0)


