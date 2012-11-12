#! /bin/csh -f

./generate.py 2 100000 5 > in.txt
./reverse in.txt out.txt
set rc = $status
cat out.txt
exit $rc







