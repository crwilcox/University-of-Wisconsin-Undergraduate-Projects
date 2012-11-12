int[5] get_array()
{
    int[5] arr;

    arr[0] = 21;
    arr[1] = 42;
    arr[2] = 84;
    arr[3] = 168;
    arr[4] = 336;

    return arr;
}


void main()
{
    write get_array()[0];
    write get_array()[1];
    write get_array()[2];
    write get_array()[3];
    write get_array()[4];
}
