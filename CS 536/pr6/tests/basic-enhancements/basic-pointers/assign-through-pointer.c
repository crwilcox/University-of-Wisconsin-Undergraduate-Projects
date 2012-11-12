void main()
{
    int x;
    int * p;

    x = -1;
    p = &x;

    *p = 42;

    write x;
}
