#include <stdio.h>

struct node
{
    int data;
    struct node *next;
};

struct stack
{
    struct node *firstNode;
};

void push(struct stack *aStack, int data)
{
    struct node *newNode = (struct node *) malloc(sizeof(struct node));
    newNode->data = entry;
    newNode->next = aStack->firstNode;
    aStack->firstNode = newNode;
}
int main(void)
{
    struct stack *myStack;
    myStack =
}
