#! /bin/csh -f

echo "cd" > in.txt
echo "pwd" >> in.txt

echo "cd" > results/7.out
echo "pwd" >> results/7.out
echo /afs/cs.wisc.edu$HOME >> results/7.out
rm -f results/7.err
touch results/7.err
echo 0 > results/7.status

#(./myprog in.txt > results/7.out) >& results/7.err
#echo $status > results/7.status

./myshell in.txt
