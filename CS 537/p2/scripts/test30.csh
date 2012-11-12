#! /bin/csh -f

# set tmpFile = `mktemp`
#echo tmpFile

set tmpFile = fileout
set resultDir = results/
set test = 30

echo "ls -la /bin =ps= foo > bar" > in.txt
echo "ls -la /bin =ps= foo > bar" > ${resultDir}/${test}.out

rm -f ${resultDir}/${tmpFile}
rm -f ${resultDir}/${test}.${tmpFile}

echo "An error has occurred" > ${resultDir}/${test}.err
echo 0 > ${resultDir}/${test}.status

./myshell in.txt
