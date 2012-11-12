#! /bin/bash -f

#these vars control the number of loops per test 
lockLoops=1000000

#these vars will control the number of threads to count up to per test...
lockThreads=5

runsonavg=25

echo
echo "############################################"
echo "#####        Compile the Code         ######"
echo "############################################"
make spinlockTest

echo
echo "############################################"
echo "# Testing the Locking Mechanism..."
echo "#"
echo "# loopcount:  $lockLoops"
echo "# numthreads: $lockThreads"
echo "# runsonavg:  $runsonavg"
echo "############################################"

rm -f spinlockOut
rm -f spinlockTest.out

i=1 
while [ $i -le $lockThreads ]; do
	j=1
	while [ $j -le $runsonavg ]; do
    	./spinlockTest $lockLoops $i >> spinlockOut	
		let j=j+1
	done

	awk '{sum+=$4} END { print "Threads = ", $2, "   Average = ",sum/NR}' spinlockOut | tee -a spinlockTest.out

	rm -f spinlockOut
	let i=i+1
done

#cleanup the directory by running clean
echo
echo "############################################"
echo "#####    Running Make Clean to tidy    #####"
echo "############################################"
make clean
echo
echo

