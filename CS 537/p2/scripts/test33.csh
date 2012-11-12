#! /bin/csh -f

# set tmpFile = `mktemp`
#echo tmpFile

set tmpFile = fileout
set resultDir = results/
set test = 33

echo "ls -la /bin =ps= foo =hostname= bar" > in.txt
echo "ls -la /bin =ps= foo =hostname= bar" > ${resultDir}/${test}.out

rm -f ${resultDir}/${tmpFile}
rm -f ${resultDir}/${test}.${tmpFile}

echo "An error has occurred" > ${resultDir}/${test}.err
echo 0 > ${resultDir}/${test}.status

./myshell in.txt
