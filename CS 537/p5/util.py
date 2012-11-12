#!/usr/bin/env python
import os
import sys
import commands
import time

def info(message):
    print "%s" % message
    sys.stdout.flush()
    
def error(message):
    kill()
    print "ERROR: %s\n" % message
    sys.stdout.flush()
    sys.exit(1)

def make():
    """make sure a make file exists, and make generate server."""
    if not (os.path.exists("Makefile") or os.path.exists("makefile")):
        error("missing Makefile or makefile")

    # delete server if any and do a make clean
    # commands.getoutput("make clean")
    commands.getoutput("rm -rf server")

    # now do a make
    commands.getoutput("make")
    if not os.path.exists("server"):
        error("make does not generate an excutable server file")
    """
    if not os.path.exists("testclient"):
        error("make does not generate an excutable testclient file")
    if not os.path.exists("output10.cgi"):
        error("make does not generate an excutable output10.cgi file")        
    #    sys.exit(0)
    """
def kill():
    commands.getoutput("killall -9 server")
    time.sleep(0.5)



#########################################
# i need to provide a *raw* commands here
# e.g ./server 2010 2 4 FIFO
# any redirection is done by this method
#########################################
def run(cmd):
    """ run a server with given parameters."""
    kill()
    os.system(cmd + " > /dev/null &")
    # check if server actually run
#    ps = commands.getoutput("ps -a")
    time.sleep(1)
    ps = commands.getoutput("ps -ax")
    if ps.find(cmd) == -1:
        error("Server can not start when runing the command: " + cmd)

    time.sleep(1)
    # If You reach here, your server is up

def run2(cmd):
    """ run a server with given parameters."""
    kill()
    os.system(cmd + " > /dev/null &")

    time.sleep(1)

# check if the server is still alive
# return -1 if server is dead
def is_server_alive(cmd):
#    ps = commands.getoutput("ps -ax")
    ps = commands.getoutput("ps -ax")
    return ps.find(cmd)
    
def good(message):
    print ""
    print "#########################################"
    print "GOOD! " + message
    print "#########################################"
    print ""


def get_stat_thread_count(response):
    if not os.path.exists(response):
        error("Oh! The reponse file %s does not exists!"
              "Check to see what cause the problem")

    # OK the the response file do exist
    # grep the Stat-thread-count info
    line = commands.getoutput("cat %s | grep \"Stat-thread-count\"" % response)
    if line.find("Stat-thread-count") < 0:
        error("Your header does not contain Stat-thread-count")

    # get the field and return
    header, field, value = line.split(":")

    #/    return int(value)
    return float(value)
    


def get_stat_thread_static(response):
    if not os.path.exists(response):
        error("Oh! The reponse file %s does not exists!"
              "Check to see what cause the problem")

    # OK the the response file do exist                                                                                                                                            
    # grep the Stat-thread-static info                                                                                                                                             
    line = commands.getoutput("cat %s | grep \"Stat-thread-static\"" % response)
    if line.find("Stat-thread-static") < 0:
        error("Your header does not contain Stat-thread-static")

    # get the field and return                                                                                                                                                     
    header, field, value = line.split(":")
#    return int(value)
    return float(value)

# response is a file
def get_stat(response, stat):
    if not os.path.exists(response):
        error("Oh! The reponse file %s does not exists!"
              "Check to see what cause the problem")

    # OK the the response file do exist                                                                                                                                                # grep the stat                                                                                                                                                                
    line = commands.getoutput("cat %s | grep \"%s\"" % (response, stat))
    if line.find(stat) < 0:
        error("Your header does not contain %s" % stat)
    # get the field and return                                                                                                                                                     
    header, field, value = line.split(":")

    return float(value)
#    return int(value)

# response is not a file, it is output...                                                                                                                                          
def get_stat2(res, stat):
    lines = []
    lines = res.split("\n")
    found = True
    for line in lines:
        if line.find(stat) > 0:
            header, field, value = line.split(":")
#            return int(value)
            return float(value)
    error("Your header does not contain %s" % stat)
