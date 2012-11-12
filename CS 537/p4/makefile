all: spinlock counter list hash 

spinlock: spinlock.h spinlock.c
	gcc -c -fpic spinlock.c -Wall -ggdb
	gcc -shared -o libspinlock.so spinlock.o -Wall -ggdb

list: spinlock list.h list.c
	gcc -c -fpic list.c -Wall -ggdb
	gcc -shared -o liblist.so list.o -lspinlock -L. -ggdb

counter: spinlock counter.h counter.c 
	gcc -c -fpic counter.c -Wall -ggdb
	gcc -shared -o libcounter.so counter.o -lspinlock -L. -Wall -ggdb

hash: spinlock list hash.h hash.c
	gcc -c -fpic hash.c -Wall -ggdb
	gcc -shared -o libhash.so hash.o -lspinlock -llist -L. -Wall -ggdb
 
spinlockTest: spinlock spinlockTest.c
	gcc spinlockTest.c -lspinlock -L. -lpthread  -o spinlockTest -Wall -ggdb

counterTest: spinlock counter counterTest.c
	gcc counterTest.c -lcounter -L. -lpthread -o counterTest -Wall -ggdb

listTest: spinlock list listTest.c
	gcc listTest.c -llist -L. -lpthread -o listTest -Wall -ggdb

hashTest: spinlock list hash hashTest.c
	gcc hashTest.c -llist -lhash -L. -lpthread -o hashTest -Wall -ggdb

clean:
	rm -f lib*.so *.o spinlockTest counterTest listTest hashTest
