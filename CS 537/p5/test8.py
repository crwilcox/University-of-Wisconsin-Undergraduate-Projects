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
# check if you implement FIFO correctly
########################################


# this is ugly, should customize server startup parameters also
NUM_CLIENT = 4
CGI_SPIN_TIME = 2
EXPECTED_TIME = 2
NUM_LOOPS = 1

count = 0 # number of time you passed

#tmpfile = "/tmp/p5.result"
#file1 = "/tmp/file1"
#file2 = "/tmp/file2"
#file3 = "/tmp/file3"

tmpfile = commands.getoutput("mktemp /tmp/p5.XXXXX")
file1 = commands.getoutput("mktemp /tmp/p5.XXXXX")
file2 = commands.getoutput("mktemp /tmp/p5.XXXXX")
file3 = commands.getoutput("mktemp /tmp/p5.XXXXX")

expected = []
got = []
lock = threading.Lock()

def test(cmd):
    global expected
    global got
    global count

    util.info("")
    util.info("- Starting " + cmd)
    util.info("")
    util.run(cmd)
    
    expected = ['MAGIC:a', 'MAGIC:bb', 'MAGIC:ccc']

    commands.getoutput("rm -rf %s" % tmpfile)
    commands.getoutput("rm -rf " + file1)
    commands.getoutput("rm -rf " + file2)
    commands.getoutput("rm -rf " + file3)

    os.system("./testclient localhost 2010 /output10.cgi >> /dev/null &");
    time.sleep(1)
#    os.system("./testclient localhost 2010 /testdata/file1.txt > /tmp/file1 &");
    os.system("./testclient localhost 2010 /testdata/file1.txt > %s &" % file1);
    time.sleep(1)
    os.system("./testclient localhost 2010 /output.cgi >> /dev/null &");
    time.sleep(1)
#    os.system("./testclient localhost 2010 /testdata/file2.txt > /tmp/file2 &");
    os.system("./testclient localhost 2010 /testdata/file2.txt > %s &" % file2);
    time.sleep(1)
    os.system("./testclient localhost 2010 /output.cgi >> /dev/null &");
    time.sleep(1)
#    os.system("./testclient localhost 2010 /testdata/file3.txt > /tmp/file3 &");
    os.system("./testclient localhost 2010 /testdata/file3.txt > %s &" % file3);



    time.sleep(10 +  2 * CGI_SPIN_TIME + 2);

    if util.is_server_alive(cmd) == -1:
        util.error("Ouch! Server is dead!"
                   " Your bounded buffered may not be well protected");

    time1 = os.path.getmtime(file1)
    time2 = os.path.getmtime(file2)
    time3 = os.path.getmtime(file3)
   
    res1 = commands.getoutput("cat " + file1);
    res2 = commands.getoutput("cat " + file2);
    res3 = commands.getoutput("cat " + file3);
    
    print res1
    print res2
    print res3
    
    print expected[0]
    pos1 = res1.find(expected[0])
    print expected[1]
    pos2 = res2.find(expected[1])
    print expected[2]
    pos3 = res3.find(expected[2])

    print time1
    print time2
    print time3

    print pos1
    print pos2
    print pos3

    passed = time1 < time2 and time2 < time3 
    passed = passed and pos1 > 0 and pos2 > 0 and pos3 > 0

    if passed:
        print ""
        print "#####################################"
        print "GOOD! you passed this iteration"
        print "There may be more..."
        print "#####################################"
        print ""
        count = count + 1
    else:
        print ""
        print "#####################################"
        print "Oh oh! ERROR ERROR!"
        print "FIFO is not implemented correctly"
        print "#####################################"
        print ""
        sys.exit(-1)

    util.kill()

class testit(Thread):
    def __init__ (self,clientname, id):
        Thread.__init__(self)
        self.clientname = clientname
        self.id = id

    def run(self):
        clientcmd = "./testclient localhost 2010 "
        if self.id == 0:
            file = "/output.cgi"
        else:
            file = "/testdata/file%s.txt" % str(self.id)

        clientcmd = clientcmd + file + " >> /tmp/file" + str(self.id) + " &"
        util.info(self.clientname + ": " + clientcmd)

        os.system(clientcmd)
        if self.id != 0:
            os.system("./testclient localhost 2010 /output.cgi > /dev/null &")


util.make()


# test SFF server
for i in range(0, NUM_LOOPS):
    print ""
    print "#############################"
    print "- %s iteration" % str(i)
    print "#############################"
    print ""
    test("./server 2010 1 7 FIFO")

util.good("great! FIFO is done correctly")

sys.exit(0)


