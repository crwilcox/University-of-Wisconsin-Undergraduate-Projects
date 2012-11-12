#!/bin/bash
cd src
make

java HW2 0 ../testcases/Titanic/train.txt ../testcases/Titanic/tune.txt ../testcases/Titanic/test.txt > ../testcases/Titanic/mode0.out
java HW2 1 ../testcases/Titanic/train.txt ../testcases/Titanic/tune.txt ../testcases/Titanic/test.txt > ../testcases/Titanic/mode1.out
java HW2 2 ../testcases/Titanic/train.txt ../testcases/Titanic/tune.txt ../testcases/Titanic/test.txt > ../testcases/Titanic/mode2.out
java HW2 3 ../testcases/Titanic/train.txt ../testcases/Titanic/tune.txt ../testcases/Titanic/test.txt > ../testcases/Titanic/mode3.out
java HW2 4 ../testcases/Titanic/train.txt ../testcases/Titanic/tune.txt ../testcases/Titanic/test.txt > ../testcases/Titanic/mode4.out

java HW2 0 ../testcases/mushroom/train.txt ../testcases/mushroom/tune.txt ../testcases/mushroom/test.txt > ../testcases/mushroom/mode0.out
java HW2 1 ../testcases/mushroom/train.txt ../testcases/mushroom/tune.txt ../testcases/mushroom/test.txt > ../testcases/mushroom/mode1.out
java HW2 2 ../testcases/mushroom/train.txt ../testcases/mushroom/tune.txt ../testcases/mushroom/test.txt > ../testcases/mushroom/mode2.out
java HW2 3 ../testcases/mushroom/train.txt ../testcases/mushroom/tune.txt ../testcases/mushroom/test.txt > ../testcases/mushroom/mode3.out
java HW2 4 ../testcases/mushroom/train.txt ../testcases/mushroom/tune.txt ../testcases/mushroom/test.txt > ../testcases/mushroom/mode4.out

echo STARTING Titanic TESTS
echo Mode 0
diff ../testcases/Titanic/mode0.out ../testcases/Titanic/mode0.expected

echo Mode 1
diff ../testcases/Titanic/mode1.out ../testcases/Titanic/mode1.expected

echo Mode 2
diff ../testcases/Titanic/mode2.out ../testcases/Titanic/mode2.expected

echo Mode 3
diff ../testcases/Titanic/mode3.out ../testcases/Titanic/mode3.expected

echo Mode 4
diff ../testcases/Titanic/mode4.out ../testcases/Titanic/mode4.expected


echo STARTING mushroom TESTS
echo Mode 0
diff ../testcases/mushroom/mode0.out ../testcases/mushroom/mode0.expected

echo Mode 1
diff ../testcases/mushroom/mode1.out ../testcases/mushroom/mode1.expected

echo Mode 2
diff ../testcases/mushroom/mode2.out ../testcases/mushroom/mode2.expected

echo Mode 3
diff ../testcases/mushroom/mode3.out ../testcases/mushroom/mode3.expected

echo Mode 4
diff ../testcases/mushroom/mode4.out ../testcases/mushroom/mode4.expected

make clean
