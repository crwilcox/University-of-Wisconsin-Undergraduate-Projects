#! /bin/csh -f
# runs one test given the test and the result file
# Internal Vars:
# program: what program are we testing?
set program = cs536.P4

if ($#argv != 2) then
	echo "you must give exactly one parameters"
	echo "argv[1] is for path to test ie. tests/mytest.minc"
	echo "argv[2] is for path to reference ie. tests/mytest.expected"
else
	set input_test = $argv[1];
	set test_result = $argv[2];

	#goto bin to test code
	cd bin

	#send both out and error
	java $program < ../${input_test} >& test.out
   
    # check for differences in output
    diff -B test.out ../${test_result} >& test.out.diff
	
    @ fail = 0
	    # check if redirection are correct
	    set check = `stat -c %s test.out.diff`
	    if ($check != "0") then
			echo "TEST ${input_test}: FAILED"
			echo "** Expected:"
			cat ../${test_result} | head -10
			echo "** Got:"
			cat test.out | head -10
			echo "** Diff file (which should be empty) instead contains:"
			cat test.out.diff | head -10
			echo ""
			@ fail = $fail + 1
	   
    else
	echo "TEST ${input_test}: PASSED"
    endif
	
	rm test.out test.out.diff
endif
