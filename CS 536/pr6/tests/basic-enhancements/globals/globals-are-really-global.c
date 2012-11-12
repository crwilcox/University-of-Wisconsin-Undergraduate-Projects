int x;

void double_x()
{
    x = x + x;
}

void main()
{
    x = 21;
    double_x();
    write x;
}

