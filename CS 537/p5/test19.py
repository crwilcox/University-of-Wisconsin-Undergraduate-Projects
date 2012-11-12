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
# check request-age
##########################################


file1 = commands.getoutput("mktemp /tmp/p5.XXXXX")

def test(cmd):
    util.info("")
    util.info("- Starting " + cmd)
    util.info("")
    util.run(cmd)
    
    commands.getoutput("rm -rf " + file1)

    os.system("./testclient localhost 2010 /output.cgi >> /dev/null &");
    time.sleep(0.5)
    os.system("./testclient localhost 2010 /testdata/file3.txt >> /dev/null &")
    time.sleep(0.2)
    os.system("./testclient localhost 2010 /testdata/file2.txt >> /dev/null &")
    time.sleep(0.2)
    res = commands.getoutput("./testclient localhost 2010 /testdata/file1.txt")

    age = util.get_stat2(res, "Stat-req-age")

    print "age = " + str(age)

    if cmd.find("SFF") > 0 and cmd.find("-BS") < 0:
        if age == 0:
            util.good("your Stat-req-age is corect")
        else:
            util.error("Expected age = 0")

    if cmd.find("FIFO") > 0:
        if age == 2:
            util.good("your Stat-req-age is corect")
        else:
            util.error("Expected age = 2")

    if cmd.find("SFF-BS") > 0:
        if age == 1:
            util.good("your Stat-req-age is corect")
        else:
            util.error("Expected age = 1")
    
    util.kill()


util.make()


# test SFF server
test("./server 2010 1 4 FIFO")
test("./server 2010 1 4 SFF")
test("./server 2010 1 4 SFF-BS 2")

sys.exit(0)


