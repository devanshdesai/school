#include <stdio.h>
#include <string.h>

void foo(char *dest, char *src)
{
	int i = 0;

	while(src[i] != '\0')
	{
		dest[i] = src[i];
		i++;
	}
}

int main(void)
{
	char str[] = "abcdefg";
	char buffer[20];

	foo(buffer,str);

	printf("%s\n", buffer);

	return 0;
}
