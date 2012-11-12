################################################################################
#
#		 CS 368 - Spring 2010 - Karen Miller
#
#		 Christopher Wilcox
#		 cwilcox AT wisc.edu
# 
################################################################################
main: main.cpp Process.o Queue.o
	g++ -o main main.cpp Process.o Queue.o -ggdb

Process.o: Process.cpp Process.h
	g++ -c Process.cpp -ggdb

Queue.o: Queue.cpp Queue.h
	g++ -c Queue.cpp -ggdb

purify: main.cpp Process.o Queue.o
	purify g++ -g -o main main.cpp Process.o Queue.o

clean:
	rm -f *.o main *_pure_*.o
