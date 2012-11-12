#! /bin/csh -f

echo ";" > in.txt

echo ";" > results/12.out
rm -f results/12.err
touch results/12.err
echo 0 > results/12.status

./myshell in.txt
