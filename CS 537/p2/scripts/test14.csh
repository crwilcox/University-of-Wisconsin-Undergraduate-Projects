#! /bin/csh -f

echo "ls -la /bin; cal; who;" > in.txt

echo "ls -la /bin; cal; who;" > results/14.out
ls -la /bin >> results/14.out
cal >> results/14.out
who >> results/14.out

rm -f results/14.err
touch results/14.err
echo 0 > results/14.status

./myshell in.txt


