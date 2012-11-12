void main()
{
    bool b;
    bool c;
    
    b = true;
    c = true;

    if (b && c) {
        write 42;
    }
    else {
        write -1;
    }


    b = false;
    c = true;

    if (b && c) {
        write -1;
    }
    else {
        write 42;
    }


    b = true;
    c = false;

    if (b && c) {
        write -1;
    }
    else {
        write 42;
    }


    b = false;
    c = false;

    if (b && c) {
        write -1;
    }
    else {
        write 42;
    }

}

