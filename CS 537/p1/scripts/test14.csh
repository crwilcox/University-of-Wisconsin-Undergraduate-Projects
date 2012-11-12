#! /bin/csh -f

./generate.py 1 10 1000000 > in.txt
./reverse in.txt out.txt
set rc = $status
cat out.txt
exit $rc





