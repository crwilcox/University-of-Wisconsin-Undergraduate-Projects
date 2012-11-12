#! /bin/bash

echo "Cleaning of directory..."
make clean
echo
echo "Building of bufferTest"
echo
make bufferTest
echo
echo "Running bufferTest..."
echo
# non-debug, only major errors
#./bufferTest
# debug/verbose mode
./bufferTest 0
echo
echo "Cleaning of directory..."
echo
make clean
