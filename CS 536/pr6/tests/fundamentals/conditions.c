void main()
{
    // < tests
    if (1<2) {
        write 42;
    }
    else {
        write -1;
    }

    if (2<2) {
        write -1;
    }
    else {
        write 42;
    }

    if (3<2) {
        write -1;
    }
    else {
        write 42;
    }


    // <= tests
    if (1<=2) {
        write 42;
    }
    else {
        write -1;
    }

    if (2<=2) {
        write 42;
    }
    else {
        write -1;
    }

    if (3<=2) {
        write -1;
    }
    else {
        write 42;
    }
    


    // > tests
    if (2>1) {
        write 42;
    }
    else {
        write -1;
    }

    if (2>2) {
        write -1;
    }
    else {
        write 42;
    }

    if (2>3) {
        write -1;
    }
    else {
        write 42;
    }


    // >= tests
    if (2>=1) {
        write 42;
    }
    else {
        write -1;
    }

    if (2>=2) {
        write 42;
    }
    else {
        write -1;
    }

    if (2>=3) {
        write -1;
    }
    else {
        write 42;
    }
    


    // == tests
    if (1 == 2) {
        write -1;
    }
    else {
        write 42;
    }

    if (2 == 2) {
        write 42;
    }
    else {
        write -1;
    }

    if (2 == 3) {
        write -1;
    }
    else {
        write 42;
    }


    // != tests
    if (1 != 2) {
        write 42;
    }
    else {
        write -1;
    }

    if (2 != 2) {
        write -1;
    }
    else {
        write 42;
    }

    if (2 != 3) {
        write 42;
    }
    else {
        write -1;
    }
}

    
