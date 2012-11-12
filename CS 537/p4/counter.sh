#! /bin/bash -f

#these vars control the number of loops per test
counterLoops=1000000
#these vars will control the number of threads to count up to per test...
counterThreads=20
runsToAvg=1

echo
echo "############################################"
echo "#####        Compile the Code         ######"
echo "############################################"
make counterTest

echo
echo "############################################"
echo "# Testing the Counter...      " 
echo "#"
echo "# loopcount:  $counterLoops " 
echo "# numthreads: $counterThreads "  
echo "# runstoavg:  $runsToAvg "
echo "############################################"
rm -f counterTest.out
rm -f counterOut

i=1
while [ $i -le $counterThreads ]; do
    jk=1
    while [ $jk -le $runsToAvg ]; do
	./counterTest $counterLoops $i >> counterOut
	let jk=jk+1
    done
    awk '{sum+=$4} END { print "Threads = ", $2, "   Average = ",sum/NR}' counterOut | tee -a counterTest.out
    rm -f counterOut
    let i=i+1
done
#./counterTest $counterLoops $counterThreads


#cleanup the directory by running clean
echo
echo "############################################"
echo "#####    Running Make Clean to tidy    #####"
echo "############################################"
make clean
echo
echo

