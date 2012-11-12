#! /bin/csh -f

echo "pwd" > in.txt

echo "pwd" > results/4.out
pwd >> results/4.out

rm -f results/4.err
touch results/4.err

echo 0 > results/4.status


./myshell in.txt
