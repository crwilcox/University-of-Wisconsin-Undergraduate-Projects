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
    if (true && legal(true)) {
        write 42;
    }
    else {
        write -1;
    }

    if (true && legal(false)) {
        write -1;
    }
    else {
        write 42;
    }

    if (false && illegal(true)) {
        write -1;
    }
    else {
        write 42;
    }
}
