#! /bin/csh -f

# set tmpFile = `mktemp`
#echo tmpFile

set tmpFile = fileout
set resultDir = results/
set test = 22

echo "> out;>out" > in.txt
echo "> out;>out" > ${resultDir}/${test}.out

rm -f ${resultDir}/${tmpFile}
rm -f ${resultDir}/${test}.${tmpFile}

#cal > ${resultDir}/${test}.${tmpFile}

rm -f ${resultDir}/${test}.err
echo "An error has occurred" > ${resultDir}/${test}.err
echo "An error has occurred" >> ${resultDir}/${test}.err
echo 0 > ${resultDir}/${test}.status

./myshell in.txt
