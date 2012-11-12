UPDATED
=======
- 5/3 4:30pm: the script expect 5 seconds of timeout. So if your timeout 
interval differ than 5 seconds, you may fail some test.
- 5/3 2pm: make sure to set the LD_LIBRARY_PATH to "."
- 5/3 9am: test 40 is updated (add some extra sleep time to allow server start
and die cleanly)
- 5/1, 5pm: test scripts need udp.c to compile test client sucessfully. 
Test 32, 37 are updated
- 5/1, 10:30pm: test 26 is updated (make initialization for inodes[15] and
 inodes[16]

HOW TO RUN
==========

1. copy all thing in this directory to your working directory

2. the test expects server name and port passed by MFS_SERVER and MFS_PORT
i.e you have to create these environment variables and set correct values.
For example: put the following lines in your bashrc, and source your bash file
export MFS_SERVER=localhost
export MFS_PORT=10003

3. to run all test at once, use
   
   $ ./run-test.py

   for run a specfific test, use
  
   $ ./run-test.py [test number (1-46)]

// ******************************************************* //
// * *                  IMPORTANT                          //
// * What if a particular test case have some problem??? * //
// ******************************************************* //
Well, in that case, PLEASE do the following:
1. Check if you have the most up-to-date version of test case
(usually, i will update my test if some student pointing out a bug, 
so the way to do it is to look in the test script location (in my
public folder, and do a diff to see if you have the same test case)
Also, remember to check this README frequently and see if something has been 
updated to the test script.

2. see if testclient is compiled successfuly

3. check if your MFS_SERVER and MFS_PORT is configure correctly:
To do this:
$ echo $MFS_SERVER
(your server name should appear corectly)
$ echo $MFS_PORT

4. (THIS IS IMPORTANT, that help you debug your code, and program correctly)
Look in to the test code (i.e if you test test X, you should look into 
./testcases/testx.c) and see what it realy does, and what it expects.
Try to write a small client that does similar thing and run the test 
separately (w/o the test framework).

5. Finally, if you try the above tools but it does not work, 
please email 537-help@cs.wisc.edu, and provide the any information that you 
think that would help us to figure out the problem.


TEST OUTLINE
============

Basic
-----
1) - README or README.txt
2 - Makefile or makefile
3 - make generate server and libmfs.so


MFS interface
-------------
4- MFS_Lookup: invalid pinum
5- MFS_Lookup: name does not exists
6- MFS_Lookup: lookup . at ROOT 
7- MFS_Lookup: lookup .. at ROOT
8- MFS_Lookup: a directory
9- MFS_Lookup: a file

10- MFS_Stat: inum does not exist
11- MFS_Stat: for new dir
12- MFS_Stat: for dir having some entries
13- MFS_Stat: stat for an empy file
14- MFS_Stat: stat for non-empty file

15- MFS_Write: invalid inum
16- MFS_Write: invalid block
17- MFS_Write: not a regular file
18- MFS_Write: write single block
19- MFS_Write: write multiple blocks 
20- MFS_Write: write to a block that extend the file size

21- MFS_Write: overwrite a block
22- MFS_Read: invalid num
23- MFS_Read: invalid block 
24- MFS_Read: invalid block 
25- MFS_Read: invalid block 
26- MFS_Read: a block in directory
27- MFS_Read: a block normal file

28- MFS_Creat: invalid pinum
29- MFS_Creat: creat new a directory
30- MFS_Creat: creat new a file
31- MFS_Creat: creat existed file
32- MFS_Creat: creat existed directory
33- MFS_Creat: creat hierachical of dir (say 3 level)

34- MFS_Unlink: invalid pinum
35- MFS_Unlink: directory is not empty
36- MFS_Unlink: name is not existed
37- MFS_Unlink: an empty directory
38- MFS_Unlink: a file

Combination
-----------
39- A slighty complex test


Server: special case
------
40
- Server: startup non-existing fsimage, see if it creates the image correctly
- Server: startup with existing fsiamge, double check it it load it correctly

Client timout
-------------
- Check if client timeout and retry correctly with each MFS_XYZ
41+ lookup
42+ write
43+ read
44+ stat
45+ creat
46+ unlink

