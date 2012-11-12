#! /bin/csh -f

echo ";;;;;;" > in.txt

echo ";;;;;;" > results/13.out
rm -f results/13.err
touch results/13.err
echo 0 > results/13.status

./myshell in.txt
