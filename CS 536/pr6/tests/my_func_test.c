void foo(int X, int Y, int Z)
{
	int A = 1;
	int B = 2;
	int C = 3;
	
	write A;
	write B; 
	write C;
}

void main()
{
	int A = 4;
	int B = 5;
	int C = 6;
	
	foo(A, B, C);
	
	write A;
	write B; 
	write C;
}