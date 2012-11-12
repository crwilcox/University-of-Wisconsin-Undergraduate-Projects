#! /bin/csh -f

./generate.py 1 10 1000000 > in.txt
cat in.txt | ./reverse 
exit $status
