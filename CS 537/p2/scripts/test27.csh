#! /bin/csh -f

# set tmpFile = `mktemp`
#echo tmpFile

set tmpFile = fileout
set resultDir = results/
set test = 27

echo "ls -la /bin =ps= ${resultDir}/${tmpFile}" > in.txt
echo "ls -la /bin =ps= ${resultDir}/${tmpFile}" > ${resultDir}/${test}.out

rm -f ${resultDir}/${tmpFile}
rm -f ${resultDir}/${test}.${tmpFile}


ls -la /bin | grep ps > ${resultDir}/${test}.${tmpFile}

rm -f ${resultDir}/${test}.err
touch ${resultDir}/${test}.err

echo 0 > ${resultDir}/${test}.status

./myshell in.txt
