#include <stdio.h>
#include <stdlib.h>

struct list
{
	int numberOfEntries;
	struct node *firstNode;
};

struct node
{
	int data;
	struct node *next;
};

/* Allocates a chunk of memory the same size as struct list,
 * initializes the numberOfEntries to 0, initialize the fistNode
 * to NULL, and returns the address of type pointer to struct list.
 */
struct list *constructList()
{
	// TO DO
	struct list *myList = (struct list *) malloc(sizeof(struct list));
	myList->numberOfEntries = 0;
	myList->firstNode = NULL;
	return myList;
}

/* Add newEntry to the end of of the given aList.
 */
void add(struct list *aList, int newEntry)
{
	// TO DO
	struct node *currentNode = aList->firstNode;
	struct node *newNode = (struct node *) malloc(sizeof(struct node));
	newNode->data = newEntry;
	newNode->next = NULL;
	if (aList->firstNode == NULL)
	{
		aList->firstNode = newNode;
	}
	else
	{
		while ((currentNode->next) != NULL)
		{
			currentNode = currentNode->next;
		}
		currentNode->next = newNode;
	}
	aList->numberOfEntries++;
}

/* Returns the address of the node associate with the
 * given index of the given aList.
 */
struct node *getNodeAt(struct list *aList, int index)
{
	// TO DO
	struct node *currentNode;
	currentNode = aList->firstNode;
	int i;
	for (i = 0; i < index; i++)
	{
		currentNode = currentNode->next;
	}
	return currentNode;
}

/* Returns the data at the given index of the given aList.
 * DO NOT MODIFY THIS FUNCTION.
 */
int get(struct list *aList, int index)
{
	return getNodeAt(aList, index)->data;
}

/* Removes an entry at the given index from the give aList,
 * and returns the data from the removed index.
 */
int removeEntry(struct list *aList, int index)
{
	// TO DO
	if (aList->numberOfEntries < index)
		return 0;
	else if (index < 0)
		return 0;

	struct node *currentNode;
	currentNode = aList->firstNode;
	int i;
	int num;
	if (index == 0)
	{
		aList->firstNode = (aList->firstNode)->next;
		num = currentNode->data;
		free(currentNode);
	}
	else if (index == aList->numberOfEntries - 1)
	{
		for (i = 0; i < index - 1 ; i++)
		{
			currentNode = currentNode->next;
		}
		num = (currentNode->next)->data;
		free(currentNode->next);
		currentNode->next = NULL;
	}
	else
	{
		for (i = 0; i < index - 1; i++)
		{
			currentNode = currentNode->next;
		}
		num = (currentNode->next)->data;
		struct node *temp = currentNode->next;
		currentNode->next = (currentNode->next)->next;
		free(temp);
	}
	printf("%i", num);
	aList->numberOfEntries--;
	return num;
}

/* Print information and contents of a given aList.
 * DO NOT MODIFY THIS FUNCTION.
 */
void printList(struct list *aList)
{
	struct node *currentNode;
	currentNode = aList->firstNode;
	printf("The given list contains %i entries: ", aList->numberOfEntries);
	while(currentNode != NULL)
	{
		printf("%i ", currentNode->data);
		currentNode = currentNode->next;
	}
	printf("\n");
}

/* This is a simple test program. Note that you should write
 * your own test program when you are implementing above functions.
 * When you think that all your functions work correctly, use this
 * main function.
 */
int main(void)
{
	struct list *myList;
	int i;

	myList = constructList();

	for(i = 0; i < 5; i++)
	{
		printf("Adding %i\n", i);
		add(myList, i);
		printList(myList);
	}

	printf("\n");

	printf("Remove index 0 (first node on the link chain)\n");
	i = removeEntry(myList,0);
	printf("The function removeEntry returns %i.\n", i);
	printList(myList);

	printf("Remove index 3 (last node on the link chain)\n");
	i = removeEntry(myList,3);
	printf("The function removeEntry returns %i.\n", i);
	printList(myList);

	printf("Remove index 1 (middle node on the link chain)\n");
	i = removeEntry(myList,1);
	printf("The function removeEntry returns %i.\n", i);
	printList(myList);

	return 0;
}
