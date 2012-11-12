void main()
{
    int * p;
    p = null;

    if (p == null) {
        write 42;
    }
    else {
        write -1;
    }

    int x;
    p = &x;

    if (p == null) {
        write -1;
    }
    else {
        write 42;
    }
}
