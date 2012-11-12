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

#####################################
# request buffer should be positive
#####################################

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
test("./server 2010 1 0 FIFO")

# test SFF server
test("./server 2010 1 -1 SFF")

# test SFF-BS
test("./server 2010 2 -2 SFF-BS 2")

sys.exit(0)


