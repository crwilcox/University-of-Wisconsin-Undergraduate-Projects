void main()
{
    bool[10] array;

    array[0] = false;
    array[1] = true;
    array[2] = false;
    array[3] = true;

    array[4] = false;
    array[5] = true;
    array[6] = false;
    array[7] = true;

    array[8] = false;
    array[9] = true;

    int counter;
    counter = 0;
    while (counter < 5) {
        if (array[counter + counter]) {
            write -1;
        }
        else {
            write 42;
        }

        if (array[counter + counter + 1]) {
            write 42;
        }
        else {
            write -1;
        }

	counter = counter + 1;
    }
}

