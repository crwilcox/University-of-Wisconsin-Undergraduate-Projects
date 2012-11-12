#! /bin/bash -f

#these vars control the number of loops per test
hashItems=1000000
#these vars will control the number of threads to count up to per test...
hashThreads=20 
hashBuckets=4
runsToAvg=75

echo
echo "############################################"
echo "#####        Compile the Code         ######"
echo "############################################"
make hashTest


echo
echo "############################################"
echo "# Testing the Hash..."
echo "#"
echo "# hashitems:  $hashItems"
echo "# numthreads: $hashThreads"
echo "# numbuckets: $hashBuckets"
echo "# runstoavg:  $runsToAvg"
echo "############################################"


rm -f hashOutFile
rm -f hashInsert.out

j=1
while [ $j -le $hashThreads ]; do
	
	k=1
	while [ $k -le $runsToAvg ]; do			
		./hashTest $hashItems $j $hashBuckets >> hashOutFile
		let k=k+1		
	done

	awk '{sum+=$6} END { print "Threads = ", $2, "   Buckets = ", $4, "   Average = ",sum/NR}' hashOutFile | tee -a hashInsert.out
	rm -f hashOutFile

	let j=j+1
done
	


#cleanup the directory by running clean
echo
echo "############################################"
echo "#####    Running Make Clean to tidy    #####"
echo "############################################"
make clean
echo 
echo
