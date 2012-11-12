#include <iostream>
using namespace std;

  int main(void) {
	
	int a[25] = {0,1,4,9,16,25,36,49,64,81};
	int *p =a;
	int i;
	for(i=0; i<25; i++){
		cout << *p ;
		p++;
	}

	  }

