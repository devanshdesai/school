/*	DEVANSH DESAI
	CS 449 - FALL 2015
	LAB 09 - lab9.c
*/

#include <stdio.h>
#include <string.h>

struct record
{
	char name[100];
	int id;
};

int isIntEqual(void *first, void *second);
int isFloatEqual(void *first, void *second);
int isStringEqual(void *first, void *second);
int isStructRecordEqual(void *first, void *second);
int getFrequencyOf(void *item, void *array, int numEl, int size, int (*isEqual)(void *, void *));

// Return 1 if both integers are equal. Otherwise, return 0.
int isIntEqual(void *first, void *second)
{
	// TO DO
	int f = *(int *) first;
	int s = *(int *) second;
	if (f == s)
	{
		return 1;
	}

	return 0;

}

// Return 1 if both floats are equal. Otherwise, return 0.
int isFloatEqual(void *first, void *second)
{
	// TO DO
	float f = *(float *) first;
	float s = *(float *) second;
	if (f == s)
	{
		return 1;
	}

	return 0;
}

// Return 1 if both string are equal. Otherwise, return 0.
int isStringEqual(void *first, void *second)
{
	// TO DO
	char *a = *(char **) first;
	char *b = *(char **) second;

	if (strcmp(a, b) == 0)
	{
		return 1;
	}

	return 0;
}

// Return 1 if both struct record are equal (first's name
// is equal to second's name and first'id is equal to second's id.
// Otherwise, return 0.
int isStructRecordEqual(void *first, void *second)
{
	// TO DO
	struct record a = *(struct record *) first;
	struct record b = *(struct record *) second;
	char *name1 = a.name;
	char *name2 = b.name;

	if (isStringEqual(&name1, &name2) == 1 && isIntEqual(&a.id, &b.id) == 1)
	{
		return 1;
	}

	return 0;
}

// Return the frequency of an given item in a given array.
int getFrequencyOf(void *item, void *array, int numEl, int size, int (*isEqual)(void *, void *))
{
	// TO DO
	int i, counter = 0;

	for (i = 0; i < numEl; i++)
	{
		void *temp = array + (i * size);
		if (isEqual(temp, item))
		{
			counter++;
		}
	}

	return counter;
}

int main(void)
{
	int intArray[] = {1,3,4,3,4,7,9,3,4,6,6,3,5,5,8,4,2,4,5,6,2,3,4,5,1}; // 25 elements
	int intItem = 4;
	float floatArray[] = {3.5, 2.6, 3.2, 7.1, 4.8, 3.3, 2.6, 2.2, 6.9, 2.5}; // 10 elements
	float floatItem = 2.6;
	char *strArray[] = {"abc", "bca", "cba", "abc", "bbc", "acb", "abc", "cbb", "aba", "abc"}; // 10 elements
	char str[] = "abc";
	char *strItem = str;
	struct record recordArray[] = {{"John", 1234}, {"Jack", 2345}, {"Jim", 3345}, {"Jack", 2345}, {"Julia", 4321}, {"Jack", 3333}}; // 6 elements
	struct record recordItem = {"Jack", 2345};

	printf("The frequency of 4 in intArray is %i\n", getFrequencyOf(&intItem, intArray, 25, sizeof(int), isIntEqual));
	printf("The frequency of 2.6 in intArray is %i\n", getFrequencyOf(&floatItem, floatArray, 10, sizeof(float), isFloatEqual));
	printf("The frequency of abc in strArray is %i\n", getFrequencyOf(&strItem, strArray, 10, sizeof(char *), isStringEqual));
	printf("The frequency of Jack(2345) in recordArray is %i\n", getFrequencyOf(&recordItem, recordArray, 6, sizeof(struct record), isStructRecordEqual));

	return 0;
}
