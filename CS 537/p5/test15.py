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

###############################################
# number of argument should be correct
###############################################

def test(cmd):

    print ""
    print "- Starting " + cmd
    util.run2(cmd)
    
    if util.is_server_alive(cmd) != -1:
        util.error("Ouch! Server is still alive!")

    print ""
    print "GOOD! ..."
    print ""
        
util.make()

# test FIFO server
test("./server 2010 1 FIFO")
test("./server 1 2 FIFO")
# test SFF server
test("./server 2010 2 SFF")
test("./server 2010 2 1")
test("./server 1 SFF")
# test SFF-BS
test("./server 2010 2 2 SSF-BS")


sys.exit(0)


