void main()
{
    int[10] arr;
    arr[3] = 42;
    int * p;
    p = &(arr[3]);
    write *p;
}
