#! /bin/bash -f

# build the current project
make clean
make

# this script will start the webserver, and then have the clients try to grab 
# the page/pages... we can use this to get an idea of if things are threading
# as well as how they are being scheduled

#uses hostname command to get the current machine
MACHINE=$(hostname)
PORT=45678
THREADS=2
BUFFERS=4
SCHEDALG=FIFO
CLIENTCALLS=20
CGICALLS=20

echo $MACHINE
echo $PORT

#start the server executable
#this will later have the full version
# prompt> server [portnum] [threads] [buffers] [schedalg] [N (for SFF-BS only)]
#		server 5003 8 16 SFF
./server $PORT $THREADS $BUFFERS $SCHEDALG&
echo "####################################################"
echo
echo

./client $MACHINE $PORT /output.cgi &	./client $MACHINE $PORT /output.cgi &	./client $MACHINE $PORT /output.cgi


#make client requests
# Usage: client <host> <port> <filename>
#i=0;
#while [ $i -le $CLIENTCALLS ]; do
#	./client $MACHINE $PORT /
#	let i=i+1
#done

#j=0;
#while [ $j -le $CGICALLS ]; do
	#call output.cgi and return it
#	let j=j+1
#done

#kill the server job
killall -9 server
