void set_value(int z)
{
    z = 42;
}

void foo(int x, int y)
{
    x = -1;
    y = -1;
    set_value(x);
    write y;
}

void main()
{
    int a;
    foo(a, a);
}
