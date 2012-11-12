#! /bin/csh -f

# set tmpFile = `mktemp`
#echo tmpFile

set tmpFile = fileout
set resultDir = results/
set test = 25

echo "pwd; ls -la /bin > ${resultDir}/${tmpFile}" > in.txt
echo "pwd; ls -la /bin > ${resultDir}/${tmpFile}" > ${resultDir}/${test}.out

rm -f ${resultDir}/${tmpFile}
rm -f ${resultDir}/${test}.${tmpFile}

pwd >> ${resultDir}/${test}.out

ls -la /bin > ${resultDir}/${test}.${tmpFile}

rm -f ${resultDir}/${test}.err
touch ${resultDir}/${test}.err

echo 0 > ${resultDir}/${test}.status

./myshell in.txt
