#!/bin/csh -f

if ($#argv != 1) then
    echo "Usage: ./test.csh <test_nr>"
    exit 1
endif

set test_nr=$1


set test_input_dir="test_data/"

setenv LD_LIBRARY_PATH .

switch($test_nr)
case 1:
    if ( -f makefile || -f Makefile || -f MAKEFILE ) then
        if ( -f README || -f Readme || -f readme ) then
           goto pass
        else
           echo "ERROR: README file not present"
           goto fail
        endif
    else
        echo "ERROR: makefile not present"
        goto fail
    endif
breaksw
case 2:
    rm -f libmem.so
    make >& /dev/null
    if (! -f libmem.so) then
           echo "ERROR: not compilable using make"
           goto fail
    endif
breaksw
case 3:
    if(! -f libmem.so) then
       echo "Please compile libmem.so before running this Test"
       goto fail
    endif
    set x=`grep -rn "malloc(" *.[ch] | wc -l`
    if( $x != 0 ) then 
       goto fail
    endif
breaksw
case 4:
case 5:
case 6:
case 7:
case 8:
case 9:
case 10:
case 11:
case 12:
case 13:
case 14:
case 15:
case 16:
case 17:
case 18:
case 19:
case 20:
case 21:
case 22:
case 23:
case 24:
case 25:
case 26:
case 27:
case 28:
case 29:
case 31:
case 32:
case 33:
    if(! -f libmem.so) then
       echo "Please compile libmem.so before running this Test"
       goto fail
    endif
    rm -f myprog
    gcc -o myprog ${test_input_dir}/test${test_nr}.c -l mem -L .
    if(! -f myprog ) then
       echo "Libmem.so -> Not a proper shared library"
       goto fail
    endif
    ./myprog 
    if($status != 0) then
       goto fail
    endif
breaksw
case 30:
    if(! -f libmem.so) then
       echo "Please compile libmem.so before running this Test"
       goto fail
    endif
    rm -f myprog
    gcc -o myprog ${test_input_dir}/test${test_nr}.c -l mem -L .
    if(! -f myprog ) then
       echo "Libmem.so -> Not a proper shared library"
       goto fail
    endif
    ./myprog > out
    if($status != 0) then
         goto fail
    endif
breaksw
default:
   echo "Invalid <test_nr> option used"
   exit 1
breaksw
endsw

pass:
   echo "Test ${test_nr}: Pass"
   exit 0
fail:
   echo "Test ${test_nr}: Fail"
   exit 1
 
