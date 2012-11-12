int index(int[4] arr, int i)
{
    return arr[i];
}

void main()
{
    int[4] arr;

    arr[0] = 21;
    arr[1] = 42;
    arr[2] = 84;
    arr[3] = 168;

    write index(arr, 0);
    write index(arr, 1);
    write index(arr, 2);
    write index(arr, 3);
}

