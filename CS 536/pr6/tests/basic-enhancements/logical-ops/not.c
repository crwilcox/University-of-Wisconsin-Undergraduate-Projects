void main()
{
    bool b;
    b = true;

    if (!b) {
        write -1;
    }
    else {
        write 42;
    }

    b = false;
    
    if (!b) {
        write 42;
    }
    else {
        write -1;
    }
}

