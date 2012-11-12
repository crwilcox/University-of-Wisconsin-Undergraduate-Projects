#! /bin/csh -f

echo "cd /bin" > in.txt
echo "pwd" >> in.txt

echo "cd /bin" > results/8.out
echo "pwd" >> results/8.out
echo "/bin" >> results/8.out

rm -f results/8.err
touch results/8.err
echo 0 > results/8.status

./myshell in.txt
