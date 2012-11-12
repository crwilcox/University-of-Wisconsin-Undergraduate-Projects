int compute_sum_or_difference(bool compute_sum, int left, int right)
{
    int result;
    
    if (compute_sum) {
        result = left + right;
    }
    else {
        result = left - right;
    }

    return result;
}


bool surrogate_not(bool original)
{
    if (original) {
        return false;
    }
    else {
        return true;
    }
}


void main()
{
    write compute_sum_or_difference(true, 21, 21);
    write compute_sum_or_difference(surrogate_not(true), 84, 42);
}
