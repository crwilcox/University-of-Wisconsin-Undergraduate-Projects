#! /bin/csh -f

echo "pwd /my/favourite/dir" > in.txt

echo "An error has occurred" > results/5.err
echo "pwd /my/favourite/dir" > results/5.out
echo 0 > results/5.status

#(./myprog in.txt > results/5.out) >& results/5.err


echo $status > results/5.status

./myshell in.txt
