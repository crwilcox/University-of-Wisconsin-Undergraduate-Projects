#! /bin/csh -f

./generate.py 2 100000 5 > in.txt
./reverse in.txt 
exit $status
