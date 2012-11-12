#! /bin/bash -f

#these vars control the number of loops per test
listItems=1000000

#these vars will control the number of threads to count up to per test...
listThreads=20

listAvgRuns=1

echo
echo "############################################"
echo "#####        Compile the Code         ######"
echo "############################################"
make listTest

echo
echo "############################################"
echo "# Testing the List..."
echo "#"
echo "# listitems:  $listItems"
echo "# numthreads: $listThreads"
echo "# runstoavg:  $listAvgRuns"
echo "############################################"
rm -f listTestOut
rm -f listTest.out

i=1
while [ $i -le $listThreads ]; do

	j=1
	while [ $j -le $listAvgRuns ]; do
		./listTest $listItems $i >> listTestOut
		let j=j+1	
	done
    
   	awk '{sum+=$4} END { print "Threads = ", $2, "   Average = ",sum/NR}' listTestOut | tee -a listTest.out
	
	rm -f listTestOut
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
