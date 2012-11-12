#! /bin/csh -f

echo "cat run-tests.csh  ;;; pwd ;;; who;;;" > in.txt

echo "cat run-tests.csh  ;;; pwd ;;; who;;;" > results/16.out
cat run-tests.csh >> results/16.out
pwd >> results/16.out
who  >> results/16.out

rm -f results/16.err
touch results/16.err
echo 0 > results/16.status

./myshell in.txt
