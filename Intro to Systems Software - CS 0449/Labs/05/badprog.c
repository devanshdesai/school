#include <stdio.h>

int findAndReturnMax(int *array1, int len, int *max)
{
	int i;

	if(!array1 || (len <= 0))
	{
		return -1;
	}

	*max = array1[0];

	for(i = 1; i < len; i++)
	{
		if(*max < array1[i])
		{
			*max = array1[i];
		}
	}

	return 0;
}

int main(void)
{
	int arr[5] = {17, 21, 44, 2, 60};

	int max = arr[0];

	if(findAndReturnMax(arr, 5, &max) != 0)
	{
		printf("strange error\n");
		return 1;
	}

	printf("max value in the array is %i\n", max);

	return 0;
}
