#! /bin/csh -f

rm -f in.txt
touch in.txt
./reverse in.txt out.txt
set s = $status
cat out.txt
exit $s




