void main()
{
    int counter;
    int factorial;

    factorial = 1;
    counter = 0;

    while (counter < 3) {
        counter = counter + 1;

        int inner_counter;
        inner_counter = 0;
	int old_factorial;
	old_factorial = factorial;
        while (inner_counter < counter) {
            factorial = factorial + old_factorial;
            inner_counter = inner_counter + 1;
        }
    }

    factorial = factorial - 3;
    
    write factorial + factorial;
}


    
    
    
