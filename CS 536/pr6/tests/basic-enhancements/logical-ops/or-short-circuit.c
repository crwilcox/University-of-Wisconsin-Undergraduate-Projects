bool legal(bool b)
{
    return b;
}

bool illegal(bool b)
{
    write -1;
    return b;
}


void main()
{
    if (false || legal(true)) {
        write 42;
    }
    else {
        write -1;
    }

    if (false || legal(false)) {
        write -1;
    }
    else {
        write 42;
    }

    if (true || illegal(true)) {
        write 42;
    }
    else {
        write -1;
    }
}
