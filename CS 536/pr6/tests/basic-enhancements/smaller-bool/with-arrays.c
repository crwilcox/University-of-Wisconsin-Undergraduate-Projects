// This will cause a stack overflow if the size of bool is too big. (The
// exact number of boolceptions depends on the simulator).

void boolception(int depth_to_go)
{
    if (depth_to_go == 0) {
        return;
    }
    else {
        boolception(depth_to_go - 1);
    }

    bool[1000] x1;
}


void main()
{
    boolception(100);
}
