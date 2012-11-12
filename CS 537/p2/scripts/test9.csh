#! /bin/csh -f

echo "ls -la /bin" > in.txt

echo "ls -la /bin" > results/9.out
ls -la /bin >> results/9.out
rm -f results/9.err
touch results/9.err
echo 0 > results/9.status

./myshell in.txt
