#!/usr/bin/env python
import os
import sys
import commands
import util

################################
# this test for make file stuff
################################
def printinfo(message):
    print "INFO: %s\n" % message
    
def printerror(message):
    print "ERROR: %s\n" % message
    sys.exit(-1)


def main():
    """ Test if there is a Makefile or makefile, 
    and if make generate a server executable file

    """
    
    if not (os.path.exists("Makefile") or os.path.exists("makefile")):
        printerror("missing Makefile or makefile")

    # delete server if any and do a make clean
    commands.getoutput("make clean")
    commands.getoutput("rm -rf server")

    # now do a make
    commands.getoutput("make")
    if not os.path.exists("server"):
        printerror("make does not generate an excutable server file")

    util.good("You passed..., but this is just test 2...")
    sys.exit(0)

if __name__ == "__main__":
    main()
