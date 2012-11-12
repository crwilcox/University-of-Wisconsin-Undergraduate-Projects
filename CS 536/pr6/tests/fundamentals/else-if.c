void main()
{
    if (true) {
        write 42;
    }
    else if (true) {
        write -1;
    }
    else {
        write -1;
    }

    if (false) {
        write -1;
    }
    else if (true) {
        write 42;
    }
    else {
        write -1;
    }

    if (false) {
        write -1;
    }
    else if (false) {
        write -1;
    }
    else {
        write 42;
    }
    
}
