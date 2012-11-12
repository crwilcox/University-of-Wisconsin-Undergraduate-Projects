#! /bin/csh -f
# run tests in a single directory
# You need to set these three variables to the correct paths
# testDir: the directory that the .in and .expected files are in
# referenceExt: the extension of the result
# testExt: the extension of the test
#set referenceExt = ".minc.reference"
set referenceExt = ".expected"
set testExt = ".c"

if ($#argv != 1) then
echo "you must give exactly one parameters"
echo "parameter is for testdir"
else
set testDir = $argv[1];

pushd .
cd $testDir
# TODO: THIS LINE NEEDS TO CHANGE WITH TESTEXT
# as it relies on the value of the extension to do the grep
set list = `ls *.c | grep ".c"`

popd

@ TAKEN = 0
foreach name ($list)
	./runSingleTest.csh ${testDir}${name}    
end

endif
