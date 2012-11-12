void main()
{
    int x;
    int y;
    int * p;
    int * q;
    
    p = &x;
    q = &x;

    if (p == q) {
        write 42;
    }
    else {
        write -1;
    }

    p = &x;
    q = &y;

    if (p == q) {
        write -1;
    }
    else {
        write 42;
    }
}
