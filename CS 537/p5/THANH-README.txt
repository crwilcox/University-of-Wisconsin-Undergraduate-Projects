** Important **
=============

***REMEMBER*** when you run this test on a machine,
make sure you are the only person who run ./server
because otherwise, more than one person are trying to run 
the server binding to the same port...

Note that it is currently not automated and not completed yet.

If you have any question regarding this test, please send email to
537-help@cs.wisc.edu or thanhdo@cs.wisc.edu


** Steps **
===========

1. copy testcases folder to your working directory (YWD)

2. copy testdata folder to YWD

3. copy output10.c to YWD, add a target to your makefile
so that when we do make, output10.cgi will appear.
this is similar to the output.cgi target.

4. copy testclient.c to YWD, add a target to your makefile
so that when do make, testclient (executatle file) will appear.

5. change output.c so that output.cgi will spin the server for 2.0 seconds.
specifically, change *spinfor* to *2.0* instead of 5.0.


** To run a test **
===================

- Simply type:

$ python testcases/testXYZ.py

where XYZ is the test number, ranging from 1 to 15.
Note that we will add more test cases, specifically in the statistic
section when we do grade your exam. So make sure you get it right :)

if you see a message like:

############################
GOOD! blah blah
############################

at the end of the runing scripts, you passed that test.
otherwise, you will se some error message giving you a hint 
about what wrong with your server.



** TEST OUT LINE **
===================

Readme and Makefile
-------------------

1. README or README.txt should exists
2. Makefile should exists, do make generate an executable server

Basic server functions
---------------------

3. Server should works with simple requests
4. Multi-threaded server
5. Fixed-size pool of worker
6. Bounded buffer should be protected by lock
7. Conditional variables

Scheduling policy
-----------------

8. FIFO
9. SFF
10. SFF-BS

Statistics
----------
11. thread statistics
16. Check Stat-thread-count
17. Check Stat-thread-static and Stat-thread-dynamic
18. Check Stat-req-read/dispatch/complete
19. Check Stat-req-age
20. Check Stat-req-arrival - the time should be print in MILISECONDS

Parammeters
-----------

12. Positive number of thread
13. Positive request buffer size
14. Correct policy
15. Correct number of arguments



