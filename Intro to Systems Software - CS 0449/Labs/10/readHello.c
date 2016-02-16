#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <errno.h>

int main(void)
{
	char buffer[100];
	int fp;
	int i;
	int returnValue;
	
	fp = open("/dev/hello", O_RDWR);

	if(fp == EACCES)
	{
		printf("Unable to open /dev/hello.\n");
		return -1;
	}

	returnValue = read(fp, buffer, 14);

	if(returnValue != 14)
	{
		printf("Error reading\n");
		return -1;
	}
	buffer[14] = '\0';

	printf("%s", buffer);

	close(fp);

	return 0;
}

