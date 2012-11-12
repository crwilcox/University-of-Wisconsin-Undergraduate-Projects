#! /bin/csh -f

# set tmpFile = `mktemp`
#echo tmpFile

set tmpFile = fileout
set resultDir = results/
set test = 41

echo "   ;;;   +++   +   ;;;    ;;;  ++ ++" > in.txt
echo "   ;;;   +++   +   ;;;    ;;;  ++ ++" > ${resultDir}/${test}.out

rm -f ${resultDir}/${tmpFile}
rm -f ${resultDir}/${test}.${tmpFile}

rm -f ${resultDir}/${test}.err
touch ${resultDir}/${test}.err

echo 0 > ${resultDir}/${test}.status

./myshell in.txt
