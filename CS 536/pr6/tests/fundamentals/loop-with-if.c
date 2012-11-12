void main()
{
    int counter;
    int sum_of_evens;
    int factorial;

    factorial = 1;
    counter = 0;
    sum_of_evens = 0;

    while (counter < 6) {
        counter = counter + 1;

        bool is_even;
        is_even = false;
        int inner_counter;
        inner_counter = 0;
        while (inner_counter < counter) {
            if (inner_counter + inner_counter == counter) {
                is_even = true;
            }
            inner_counter = inner_counter + 1;
        }
        
        
        if (is_even) {
            sum_of_evens = sum_of_evens + counter;
        }
    }

    write (sum_of_evens + 30);
}


    
    
    
