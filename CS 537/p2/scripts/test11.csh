#! /bin/csh -f

echo "ls        -la        /bin   " > in.txt
echo "  pwd   " >> in.txt
echo "	cal	" >> in.txt

echo "ls        -la        /bin   " > results/11.out
ls -la /bin >> results/11.out
echo "  pwd   " >> results/11.out
pwd >> results/11.out
echo "	cal	" >> results/11.out
cal >> results/11.out

rm -f results/11.err
touch results/11.err

echo 0 > results/11.status

./myshell in.txt
