#! /bin/csh -f

echo "    cat run-tests.csh  	; pwd	;	who" > in.txt

echo "    cat run-tests.csh  	; pwd	;	who" > results/15.out
cat run-tests.csh >> results/15.out
pwd >> results/15.out
who  >> results/15.out

rm -f results/15.err
touch results/15.err
echo 0 > results/15.status

./myshell in.txt
