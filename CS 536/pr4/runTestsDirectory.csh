#! /bin/csh -f
# run tests in a single directory
# You need to set these three variables to the correct paths
# testDir: the directory that the .in and .expected files are in
# referenceExt: the extension of the result
# testExt: the extension of the test
set program = cs536.P4
set referenceExt = ".minc.reference"
set testExt = ".minc"

if ($#argv != 1) then
echo "you must give exactly one parameters"
echo "parameter is for testdir"
else
set testDir = $argv[1];

set testOut = testout

pushd .
cd $testDir
# TODO: THIS LINE NEEDS TO CHANGE WITH TESTEXT
# as it relies on the value of the extension to do the grep
set list = `ls *.minc | grep ".minc"`

popd

rm -rf $testOut
mkdir $testOut

@ TAKEN = 0
foreach name ($list)
	#get the name of the test.  Chop off the .in
	set test = `echo $name | awk '{print substr($0,0,index($0,".minc")-1) }'`
	./runSingleTest.csh ${testDir}${test}${testExt} ${testDir}${test}${referenceExt}    
end

endif
