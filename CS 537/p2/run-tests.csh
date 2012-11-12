#! /bin/csh -f

set resultDir = results/

# set this to correct folder
#set scriptDir = /u/t/h/thanhdo/public/share/cs537/p2/testing/scripts/
set scriptDir = scripts/

if (! -f results) then
    mkdir results
endif

rm -rf para
if (! -f para.c) then
    echo "ERROR: missing para.c"
    exit 1
else 
    gcc -Wall -o para para.c
endif

setenv PATH ${PATH}:.


#gcc -o myshell myshell.c

if ($#argv == 1) then
    set list = $argv
    if ($list > 44) then
	echo "test ${list}: does not exist"
	exit 1
    endif
else
    set list = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44"
else
    echo "usage: run-tests.csh [whichTest]"
    exit 1
endif

@ PASS = 0
@ FAIL = 0
@ TAKEN = 0
foreach test ($list)
    set tmpFile = `mktemp`

    rm -f ${resultDir}/fileout
    # create the correct output
    
    # this is how you run the particular test
    (${scriptDir}/test${test}.csh > ${tmpFile}.out) >& ${tmpFile}.err
    set testStatus = $status

    # check for differences in output
    diff ${tmpFile}.out ${resultDir}/${test}.out >& ${tmpFile}.out.diff
    diff ${tmpFile}.err ${resultDir}/${test}.err >& ${tmpFile}.err.diff


    @ fail = 0
    # check for redirection
    if (-f ${resultDir}/${test}.fileout) then
	if (! -f ${resultDir}/fileout) then
	    echo "TEST ${test}: Bad redirection output (or not found)"
	    echo "** Expected:"
	    cat ${resultDir}/${test}.fileout | head -10
	    echo "** Got:"
	    echo "${resultDir}/fileout is not created"
	    @ fail = $fail + 1
	else    
	    diff ${resultDir}/fileout ${resultDir}/${test}.fileout >& ${tmpFile}.fileout.diff
	    # check if redirection are correct
	    set check = `stat -c %s ${tmpFile}.fileout.diff`
	    if ($check != "0") then
		echo "TEST ${test}: bad redirection output"
		echo "** Expected:"
		cat ${resultDir}/${test}.fileout  | head -10
		echo "** Got:"
		cat ${resultDir}/fileout | head -10
		echo "** Diff file (which should be empty) instead contains:"
		cat ${tmpFile}.fileout.diff | head -10
		echo ""
		@ fail = $fail + 1
	    endif
        endif
    endif

    

   

    
    # check if standard output, standard error are correct
    foreach type ("out" "err")
	set check = `stat -c %s ${tmpFile}.${type}.diff`
	if ($check != "0") then
	    echo "TEST ${test}: bad std${type} output"
	    echo "** Expected:"
	    cat ${resultDir}/${test}.${type}  | head -10
	    echo "** Got:"
	    cat ${tmpFile}.${type} | head -10
	    echo "** Diff file (which should be empty) instead contains:"
	    cat ${tmpFile}.${type}.diff | head -10
	    echo ""
	    @ fail = $fail + 1
	endif
    end

    # what return code should the program be giving us?
    set expectedStatus = `cat ${resultDir}/${test}.status`
    if ($testStatus != $expectedStatus) then
	echo "TEST ${test}: bad status"
	echo "** Expected return code: $expectedStatus"
	echo "** Got return code     : $testStatus"
	@ fail = $fail + 1
    endif

    if ($fail != 0) then
	echo "TEST ${test}: FAILED"
	@ FAIL = $FAIL + 1
    else
	echo "TEST ${test}: PASSED"
	@ PASS = $PASS + 1
    endif

    # clean up...
    # rm -f ${tmpFile}.*
    # rm -f ${resultDir}/fileout
    echo ""
end

if ($#argv != 1) then
    echo "Summary:"
    echo "$PASS test(s) passed."
    @ grade = $PASS * 5
    echo "Total: $grade / 225"
endif


