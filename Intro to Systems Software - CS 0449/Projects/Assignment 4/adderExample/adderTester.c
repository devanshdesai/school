#include <stdio.h>	// printf, scanf, etc
#include <sys/types.h>	// open()
#include <sys/stat.h>	// open()
#include <fcntl.h>	// open()
#include <unistd.h>	// read(), write(), and close()
#include <errno.h>	// EACCES

int main()
{
	int x;			// first operand
	int y;			// second operand
	int result;		// result
	int returnValue;	// return value from a syscall
	int fp;			// file pointer
	char buffer[2];		// buffer for read() and write

	printf("Enter an integer (0 - 128): ");
	scanf("%i", &x);

	printf("Enter another integer (0 - 128): ");
	scanf("%i", &y);

	fp = open("/dev/adder", O_RDWR);

	if(fp == EACCES)
	{
		printf("Unable to open /dev/adder\n");
		return -1;
	}

	buffer[0] = (char) x;
	buffer[1] = (char) y;

	returnValue = write(fp, buffer, 2);
	if(returnValue != 2)
	{
		printf("Cannot write to device file");
		return -1;
	}

	returnValue = read(fp, buffer, 1);
	if(returnValue != 1)
	{
		printf("Cannot read from device file");
		return -1;
	}

	result = (unsigned int) buffer[0];

	printf("Result = %i\n", result);

	close(fp);

	return 0;
}
