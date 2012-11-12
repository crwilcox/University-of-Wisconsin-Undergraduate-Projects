void main()
{
    int[10][10] arr;
    arr[3][3] = 42;
    int * p;
    p = &(arr[3][3]);
    write *p;
}
