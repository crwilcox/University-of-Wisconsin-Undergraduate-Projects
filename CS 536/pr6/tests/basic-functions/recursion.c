int fibb(int n)
{
    if (n == 0) {
        return 1;
    }
    else if (n == 1) {
        return 1;
    }
    else {
        return fibb(n-1) + fibb(n-2);
    }
}

void main()
{
    write fibb(7) + fibb(7);
}

