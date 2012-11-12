#! /bin/csh -f
# runs one test given the test and the result file
# Internal Vars:
# program: what program are we testing?
set program = cs536.Compiler

if ($#argv != 1) then
	echo "you must give exactly one parameters"
	echo "argv[1] is for path to test ie. tests/mytest.minc"
else
	set input_test = $argv[1];

	#goto bin to test code
	cd bin
	
	#send both out and error
	#get the name of the test.  Chop off the .in
	set test = `echo $input_test | awk '{print substr($0,0,index($0,".c")-1) }'`
	#get the relative file name as opposed to the full thing
	set relTest = `basename $test`
	
	#test command for project 6
	java $program < ../${test}.c >& ${relTest}.s
	if( -e ../${test}.in ) then
		spim -file ${relTest}.s < ../${test}.in > ${relTest}.out
	else
		spim -file ${relTest}.s > ${relTest}.out

	endif

	# add a newline because spim doesnt output one
	echo '' >> ${relTest}.out
	
	diff -B ${relTest}.out ../${test}.expected >& test.out.diff
   

    @ fail = 0
	    # check if redirection are correct
	    set check = `stat -c %s test.out.diff`
	    if ($check != "0") then
			echo "TEST ${test}: FAILED"
			echo "** Expected:"
			cat ../${test}.expected | head -30
			echo "** Got:"
			cat ${relTest}.out | head -30
			echo "** Diff file (which should be empty) instead contains:"
			cat test.out.diff | head -30
			echo ""
			@ fail = $fail + 1
	   
    else
	echo "TEST ${test}: PASSED"
    endif
	
	cp test.out.diff ${relTest}.diff	
#	rm ${relTest}.out test.out.diff ${relTest}.s
	rm test.out.diff
endif
