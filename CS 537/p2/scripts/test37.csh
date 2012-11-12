#! /bin/csh -f

# set tmpFile = `mktemp`
#echo tmpFile

set tmpFile = fileout
set resultDir = results/
set test = 37

echo "para 2 ${resultDir}/${tmpFile}+para 1 ${resultDir}/${tmpFile}+para 0 ${resultDir}/${tmpFile}" > in.txt
echo "para 2 ${resultDir}/${tmpFile}+para 1 ${resultDir}/${tmpFile}+para 0 ${resultDir}/${tmpFile}" > ${resultDir}/${test}.out

rm -f ${resultDir}/${tmpFile}
rm -f ${resultDir}/${test}.${tmpFile}

para 0 ${resultDir}/${test}.${tmpFile}
para 1 ${resultDir}/${test}.${tmpFile}
para 2 ${resultDir}/${test}.${tmpFile}


rm -f ${resultDir}/${test}.err
touch ${resultDir}/${test}.err

echo 0 > ${resultDir}/${test}.status

./myshell in.txt
