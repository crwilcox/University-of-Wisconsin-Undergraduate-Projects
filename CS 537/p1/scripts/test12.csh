#! /bin/csh -f

echo "hello"  > in.txt
echo "this"  >> in.txt
echo "is"    >> in.txt
echo "a"     >> in.txt
echo -n "test"  >> in.txt

./reverse in.txt 
exit $status

