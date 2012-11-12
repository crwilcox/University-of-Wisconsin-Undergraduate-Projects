echo PASS
cat testout.txt| grep PASSED | wc -l
echo FAIL
cat testout.txt | grep FAILED |wc -l



