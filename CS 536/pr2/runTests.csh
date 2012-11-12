#! /bin/csh -f

# You need to set these three variables to the correct paths
# program: the name of the program you rare testing
# testDir: the directory that the .in and .expected files are in
# testOut: the directory that will be cleared and used to store 
#          your .out files and diffs in
set program = cs536.P2
set testDir = tests
set testOut = testout

cd $testDir
set list = `ls | grep "\.in"`

cd ..

rm -rf $testOut
mkdir $testOut

#build code
build.sh
#goto bin to test code
cd bin

@ PASS = 0
@ FAIL = 0
@ TAKEN = 0
foreach name ($list)
	#get the name of the test.  Chop off the .in
	set test = `echo $name | awk '{print substr($0,0,index($0,".in")-1) }'`
    
	java $program < ../${testDir}/${test}.in > test.out
   
    # check for differences in output
    diff test.out ../${testDir}/${test}.expected >& test.out.diff
	
	#copies the output to the test directory for looking later
	cp test.out ../${testOut}/${test}.out	
	cp test.out.diff ../${testOut}/${test}.diff	

    @ fail = 0
	    # check if redirection are correct
	    set check = `stat -c %s test.out.diff`
	    if ($check != "0") then
			echo "TEST ${test}: FAILED"
			echo "** Expected:"
			cat ../${testDir}/${test}.expected | head -10
			echo "** Got:"
			cat test.out | head -10
			echo "** Diff file (which should be empty) instead contains:"
			cat test.out.diff | head -10
			echo ""
			@ fail = $fail + 1
	    endif
    endif

    if ($fail != 0) then
	@ FAIL = $FAIL + 1
    else
	echo "TEST ${test}: PASSED"
	@ PASS = $PASS + 1
    endif
	@ TAKEN = $TAKEN + 1
end

if ($#argv != 1) then
    echo "Summary:"
    echo "$PASS/$TAKEN tests passed"
endif

rm test.out test.out.diff
