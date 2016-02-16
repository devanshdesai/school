
/* DEVANSH DESAI
   CS 449 - LAB 02
   09/22/2015 */
#include <stdio.h>

int main()
{
	printf("int\t\t%i bytes\n", sizeof(int));
	printf("short\t\t%i bytes\n", sizeof(short));
	printf("long\t\t%i bytes\n", sizeof(long));
	printf("long long\t%i bytes\n", sizeof(long long));
	printf("unsigned int\t%i bytes\n", sizeof(unsigned int));
	printf("char\t\t%i bytes\n", sizeof(char));
	printf("float\t\t%i bytes\n", sizeof(float));
	printf("double\t\t%i bytes\n", sizeof(double));
	printf("long double\t%i bytes\n", sizeof(long double));

	return 0;
}
