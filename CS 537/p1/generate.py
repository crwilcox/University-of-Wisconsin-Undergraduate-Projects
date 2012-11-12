#! /bin/env python

import sys
if len(sys.argv) != 4:
    print 'usage: generate.py <seed> <length of line> <number of lines to generate>'
    sys.exit(1)
seed   = int(sys.argv[1])
length = int(sys.argv[2])
num    = int(sys.argv[3])

import random

random.seed(seed)
for i in range(0, num):
    outString = ''
    for j in range(0, length):
        x = str(int(10 * random.random()))
        if len(x) == 1:
            outString = outString + x
    print outString
