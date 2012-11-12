void foo(int x, int y)
{
    y = -1;
    x = 42;
    write y;
}

void main()
{
    int a;
    foo(a, a);
}

