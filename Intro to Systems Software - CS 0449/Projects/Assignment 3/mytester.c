#include "mymalloc.h"
#include <stdio.h>

int main()
{
    printList();

    void *test = my_firstfit_malloc(100);
    printList();

    char *test1 = (char *) my_firstfit_malloc(sizeof(char));
    *test1 = 'm';
    printList();

    my_free(test);
    printList();

    int *test2 = (int *) my_firstfit_malloc(sizeof(int));
    *test2 = 25;
    printList();

    my_free(test2);
    printList();

    float *test3 = (float *) my_firstfit_malloc(sizeof(float));
    *test3 = 3.14159;
    printList();

    my_free(test1);
    my_free(test3);

    int i;
    for (i = 0; i < 100; i++)
    {
        int *j = (int *) my_firstfit_malloc(sizeof(int));
        *j = i;
    }

    printList();
}
