#! /bin/csh -f

# set tmpFile = `mktemp`
#echo tmpFile

set tmpFile = fileout
set resultDir = results/
set test = 18
echo "   cal >	${resultDir}/${tmpFile}" > in.txt
echo "   cal >	${resultDir}/${tmpFile}" > ${resultDir}/${test}.out

rm -f ${resultDir}/${tmpFile}
rm -f ${resultDir}/${test}.${tmpFile}
cal > ${resultDir}/${test}.${tmpFile}

rm -f ${resultDir}/${test}.err
touch ${resultDir}/${test}.err
echo 0 > ${resultDir}/${test}.status

./myshell in.txt
