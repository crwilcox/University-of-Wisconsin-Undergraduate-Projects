void main()
{
    // < tests
    if (1.0<2.0) {
        write 42;
    }
    else {
        write -1;
    }

    if (2.0<2.0) {
        write -1;
    }
    else {
        write 42;
    }

    if (3.0<2.0) {
        write -1;
    }
    else {
        write 42;
    }


    // <= tests
    if (1.0<=2.0) {
        write 42;
    }
    else {
        write -1;
    }

    if (2.0<=2.0) {
        write 42;
    }
    else {
        write -1;
    }

    if (3.0<=2.0) {
        write -1;
    }
    else {
        write 42;
    }
    


    // > tests
    if (2.0>1.0) {
        write 42;
    }
    else {
        write -1;
    }

    if (2.0>2.0) {
        write -1;
    }
    else {
        write 42;
    }

    if (2.0>3.0) {
        write -1;
    }
    else {
        write 42;
    }


    // >= tests
    if (2.0>=1.0) {
        write 42;
    }
    else {
        write -1;
    }

    if (2.0>=2.0) {
        write 42;
    }
    else {
        write -1;
    }

    if (2.0>=3.0) {
        write -1;
    }
    else {
        write 42;
    }
    


    // == tests
    if (1.0 == 2.0) {
        write -1;
    }
    else {
        write 42;
    }

    if (2.0 == 2.0) {
        write 42;
    }
    else {
        write -1;
    }

    if (2.0 == 3.0) {
        write -1;
    }
    else {
        write 42;
    }


    // != tests
    if (1.0 != 2.0) {
        write 42;
    }
    else {
        write -1;
    }

    if (2.0 != 2.0) {
        write -1;
    }
    else {
        write 42;
    }

    if (2.0 != 3.0) {
        write 42;
    }
    else {
        write -1;
    }
}
