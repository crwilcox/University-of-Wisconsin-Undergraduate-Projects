void main()
{
    int[2][3] arr;

    int second_index; // corresponds to [2]
    int first_index;  // corresponds to [3]

    first_index = 0;
    while (first_index < 3) {
	second_index = 0;
	while (second_index < 2) {
	    arr[first_index][second_index] = first_index + second_index;
	    second_index = second_index + 1;
	}
	first_index = first_index + 1;
    }

    first_index = 0;
    while (first_index < 3) {
	second_index = 0;
	while (second_index < 2) {
	    write arr[first_index][second_index];
	    second_index = second_index + 1;
	}
	first_index = first_index + 1;
    }
}
