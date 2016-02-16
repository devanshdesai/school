#include <stdio.h>
#include "mymalloc.h"

typedef struct node
{
    struct node *prevNode;
    struct node *nextNode;
    void *address;
    int used;
    int offset;
} Node;

Node *first();
Node *coalesce(Node *firstNode, Node *secondNode);

static Node *currNode;
static Node firstNode;
static Node lastNode;

void *my_firstfit_malloc(int size)
{
    if (firstNode.nextNode == NULL)
    {
        Node *temp_node = (Node *) sbrk(sizeof(Node));
        void *mem_ptr = (void *) sbrk(size);
        temp_node->address = mem_ptr;
        temp_node->offset = size;
        temp_node->used = 1;
        temp_node->nextNode = &lastNode;
        temp_node->prevNode = &firstNode;

        firstNode.nextNode = temp_node;
        firstNode.prevNode = NULL;
        lastNode.prevNode = temp_node;
        lastNode.nextNode = NULL;

        return (void *) temp_node->address;
    }
    void *returnVal = (void *) ((void *)first(size)) + sizeof(Node);
    return returnVal;
}

Node *first(int size)
{
    currNode = &firstNode;
    Node *return_ptr;
    Node *newNode;
    int newSize;
    while (currNode != &lastNode)
    {
        currNode = currNode->nextNode;
        if (currNode->offset >= size && currNode->used == 0)
        {
            // Not enough room
            if (currNode->offset - size < sizeof(Node))
            {
                currNode->used = 1;
                return currNode;
            }
            // This checks if there is enough room to allocate new.
            else if ((currNode->offset - size) > sizeof(Node))
            {
                newSize = currNode->offset - sizeof(Node) - size;
                currNode->offset = size;
                currNode->used = 1;

                // Placing the new Node object and pointing it to the left over memory allocation
                newNode = (Node *) (((void *) currNode) + sizeof(Node) + size);
                newNode->address = (Node *) (((void *) newNode) + sizeof(Node));
                newNode->offset = newSize;
                newNode->used = 0;
                // Placing the new Node object correctly in the LL
                newNode->prevNode = currNode;
                newNode->nextNode = currNode->nextNode;
                currNode->nextNode = newNode;
                newNode->nextNode->prevNode = newNode;

                return currNode;
            }
            else
            {
                currNode->used = 1;
                return currNode;
            }
        }
    }

    // End of the while loop means program has reached the heap
    // Will have to use sbrk and increase the size of the heap to continue.
    currNode = lastNode.prevNode;
    return_ptr = (Node *) sbrk(sizeof(Node));
    return_ptr->address = (void *) sbrk(size);
    return_ptr->nextNode = &lastNode;
    return_ptr->prevNode = currNode;
    return_ptr->used = 1;
    return_ptr->offset = size;

    lastNode.prevNode = return_ptr;
    currNode->nextNode = return_ptr;

    return return_ptr;

}

void my_free(void *ptr)
{
    int size;
    currNode = ptr - sizeof(Node);

    if (currNode->prevNode == &firstNode && currNode->nextNode == &lastNode)
    {
        size = (sizeof(Node) + currNode->offset) * -1;
        firstNode.nextNode = NULL;
        lastNode.prevNode = NULL;
        sbrk(size);
    }
    else if (currNode->prevNode == &firstNode && currNode->nextNode->used == 0)
    {
        currNode = coalesce(currNode, currNode->nextNode);
        currNode->used = 0;
    }
    else if (currNode->prevNode->used == 1 && currNode->nextNode == &lastNode)
    {
        size = (sizeof(Node) + currNode->offset) * -1;
        lastNode.prevNode = currNode->prevNode;
        currNode->prevNode->nextNode = &lastNode;
        sbrk(size);
    }
    else if (currNode->prevNode->used == 0 && currNode->nextNode->used == 1)
    {
        coalesce(currNode->prevNode, currNode);
    }
    else if (currNode->prevNode->used == 1 && currNode->nextNode->used == 0)
    {
        coalesce(currNode, currNode->nextNode);
    }

    else if (currNode->prevNode->used == 0 && currNode->nextNode->used == 0)
    {
        coalesce(currNode->prevNode, coalesce(currNode, currNode->nextNode));
    }
    else if (currNode->prevNode->used == 1 && currNode->nextNode->used == 1)
    {
        currNode->used = 1;
    }

}

// This function will combine firstNode and secondNode.
Node *coalesce(Node *firstNode, Node *secondNode)
{
    firstNode->used = 0;
    firstNode->offset += secondNode->offset + sizeof(Node);
    secondNode->nextNode->prevNode = firstNode;
    firstNode->nextNode = secondNode->nextNode;

    return firstNode;
}

// This will simply print the memory LL
void printList()
{
    printf("\nbrk at %d\n\n", sbrk(0));
    currNode = &firstNode;
    if (currNode->nextNode == NULL)
    {
        printf("\nERROR: List is empty.\n");
    }
    else
    {
        printf("->");
        while (currNode->nextNode != &lastNode)
        {
            currNode = currNode->nextNode;
            printf("\n[%d, %d, %d, contents: %d]\n-> ", currNode->used, currNode->address, currNode->offset, *((int *) (currNode->address)));
        }
    }
    printf("\n\n");

}
