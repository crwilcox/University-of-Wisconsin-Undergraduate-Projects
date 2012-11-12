#! /bin/csh -f

echo "hello" >   in.txt
echo "this"  >>  in.txt
echo "is"    >>  in.txt
echo "a"     >>  in.txt
echo "test"  >>  in.txt

./reverse in.txt out.txt
set rc = $status
cat out.txt

exit $rc






















