#! /bin/csh -f

# set tmpFile = `mktemp`
#echo tmpFile

set tmpFile = fileout

echo "ls -la /bin > results/fileout" > in.txt

echo "ls -la /bin > results/fileout" > results/17.out

rm -f results/${tmpFile}
rm -f results/17.${tmpFile}
ls -la /bin > results/17.${tmpFile}

rm -f results/17.err
touch results/17.err
echo 0 > results/17.status

./myshell in.txt
