

all: testhash

#liblist:
#	gcc -c -fpic list.c -o list.o
#	gcc -shared -o liblist.so list.o

#libhash: liblist
#	gcc -c -fpic hash.c -o hash.o
#	gcc -shared -llist -L. -o libhash.so hash.o


testhash:
	gcc testhash.c -lpthread -lhash -L. -o runTest

clean:
	rm -f core \#* *.bak *~ *.o runTest lib*.so

