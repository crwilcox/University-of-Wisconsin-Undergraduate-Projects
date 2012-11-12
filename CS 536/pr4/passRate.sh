./runAllTests.sh > run.txt
echo PASS
cat run.txt| grep PASSED | wc -l
echo FAIL
cat run.txt | grep FAILED |wc -l



