#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <errno.h>

int main(void)
{
	char buffer[10];
	int fp;
	int i;
	int returnValue;

	fp = open("/dev/mytime", O_RDWR);

	if(fp == EACCES)
	{
		printf("Unable to open /dev/mytime.\n");
		return -1;
	}

	returnValue = read(fp, buffer, 9);

	if(returnValue != 9)
	{
		printf("Error reading\n");
		return -1;
	}
	buffer[9] = '\0';

	printf("%s", buffer);

	close(fp);

	return 0;
}
