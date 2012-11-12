int index(int[4][4] arr, int i, int j)
{
    return arr[i];
}

void main()
{
    int[4] arr;

    arr[0][0] = 21;
    arr[1][1] = 42;
    arr[2][2] = 84;
    arr[3][3] = 168;

    write index(arr, 0, 0);
    write index(arr, 1, 1);
    write index(arr, 2, 2);
    write index(arr, 3, 3);
}

