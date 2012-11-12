# I experimented with different compiler optimizations to try and make my code perform faster.
# below you will find the results
#
# No Optimization
#	Time taken -> 30780.00 us
# -O1
#	Time taken -> 27061.00 us
# -O2
#	Time taken -> 17605.00 us
# -O3
#    Time taken -> 15067.00 us
# -O3 -funroll-loops
#    Time taken -> 14648.00 us

mem: mem.c
	gcc -Wall -c -fpic mem.c -O3 -funroll-loops
	gcc -Wall -shared -o libmem.so mem.o -O3 -funroll-loops
debug: mem.c
	gcc -Wall -c -fpic mem.c -ggdb -O3 -funroll-loops
	gcc -Wall -shared -o libmem.so mem.o -ggdb -O3 -funroll-loops
clean: mem.c
	rm -f mem.o
	rm -f libmem.so

