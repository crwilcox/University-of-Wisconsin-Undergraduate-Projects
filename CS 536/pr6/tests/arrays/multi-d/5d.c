void main()
{
    int[2][3][4][5] arr;

    int first;  // corresponds to [5]
    int second; // corresponds to [4]
    int third;  // corresponds to [3]
    int fourth; // corresponds to [2]

    first = 0;
    while (first < 5) {
	second = 0;
	while (second < 4) {
	    third = 0;
	    while (third < 3) {
		fourth = 0;
		while (fourth < 2) {
		    arr[first][second][third][fourth] = first + second + third + fourth;
		    fourth = fourth + 1;
		}
	        third = third + 1;
	    }
	    second = second + 1;
	}
	first = first + 1;
    }

    first = 0;
    while (first < 5) {
	second = 0;
	while (second < 4) {
	    third = 0;
	    while (third < 3) {
		fourth = 0;
		while (fourth < 2) {
		    write arr[first][second][third][fourth];
		    fourth = fourth + 1;
		}
	        third = third + 1;
	    }
	    second = second + 1;
	}
 	first = first + 1;
    }
}
