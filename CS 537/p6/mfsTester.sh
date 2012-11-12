#!/bin/bash
serverPortNum=98147
localMachine=$(hostname)

clear
echo "Testing Local Machine is $localMachine, on port $serverPortNum"
#### Making sure runaway server/mfsTester instances are not running
killall -9 server
killall -9 mfsTester

#### Testing of server and client library when both are up
echo
echo ">>>>>>>>>>>>>>>>>>>>> Making MFS Server <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
echo
make server
echo
echo ">>>>>>>>>>>>>>>>>>>>> Making MFS Tester <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
echo
make mfsTester
echo
echo ">>>>>>>>>>>>>>>>>>>>> Running MFS Server <<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
echo
./server $serverPortNum 5 &
echo
echo ">>>>>>>>>>>>>>>>>>>>> Runnin' MFS Tester <<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
echo
./mfsTester $localMachine $serverPortNum
killall -9 server
killall -9 mfsTester
rm -rf 5

#### Testing of timeout function, by creating a client and sending messages
#### before the server is up
#echo
#echo ">>>>>>>>>>>>>>>>>>>>> Testing Timeout <<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
#echo
#./mfsTester $localMachine $serverPortNum &
#echo ">>>>>>>>>>>>>>>>>>>>>Beginning sleep<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
#sleep 17
#echo ">>>>>>>>>>>>>>>>>>>>>Ending ze sleep<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
#./server $serverPortNum 5 &
#sleep 5 # requried to let server do its thing

#echo
#echo ">>>>>>>>>>>>>>>>>>>>> Cleanin' MFS Tester <<<<<<<<<<<<<<<<<<<<<<<<<<<<"
#echo
#killall -9 server
#killall -9 mfsTester
make clean

