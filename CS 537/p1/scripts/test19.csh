#! /bin/csh -f

./generate.py 2 100000 5 > in.txt
cat in.txt | ./reverse 
exit $status
