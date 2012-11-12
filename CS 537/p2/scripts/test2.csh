#! /bin/csh -f

touch results/2.out
touch results/2.err
echo 0 > results/2.status

rm -f in.txt
touch in.txt # create a new empty file
./myshell in.txt
