

all: testcount

#libcounter:
#	gcc -c -fpic counter.c -o counter.o
#	gcc -shared -o libcounter.so counter.o

testcount:
	gcc testcount.c -lpthread -lcounter -L. -o runTest

clean:
	rm -f core \#* *.bak *~ *.o runTest lib*.so

