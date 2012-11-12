#rm -rf tests
#mkdir tests
#cp -r /u/t/h/thanhdo/public/share/cs537/p4/* tests/

make

## Copy files into the correct directory and run the counter test
cp *o counter.c counter.h spinlock.c spinlock.h tests/counter/
cd tests/counter
make
./runTest
make clean
cd ../..

## Copy files into the list test directory and run a frest list test
cp *o list.c list.h spinlock.c spinlock.h tests/list/
cd tests/list
make
./runTest
make clean
cd ../..

## Copy files into the hash directory and run a fresh hash test
cp *o list.c list.h hash.c hash.h spinlock.c spinlock.h tests/hash
cd tests/hash
make 
./runTest
make clean
cd ../..

## Cleanup root directory
make clean
