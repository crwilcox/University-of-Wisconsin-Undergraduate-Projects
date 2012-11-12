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
# policy should be corect
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
test("./server 2010 1 2 FFFIFO")

# test SFF server
test("./server 2010 2 2 SSF")

# test SFF-BS
test("./server 2010 2 2 SSF-BS 2")


sys.exit(0)


