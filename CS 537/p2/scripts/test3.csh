#! /bin/csh -f

#touch results/3.out
touch results/3.err
echo "" > results/3.out
echo 0 > results/3.status

echo "" > in.txt
./myshell in.txt
