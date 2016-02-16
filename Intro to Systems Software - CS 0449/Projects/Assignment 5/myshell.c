/*
    DEVANSH DESAI
    CS 0449 - FALL 2015
    PROJECT 5 - 12/16/2015
    myshell.c
*/

#include <stdio.h>
#include <errno.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>

char *delim = " \n\t";

int countArgs(char *input)
{
  char temp[200];
  char *c;
  int counter = 0;
  strcpy(temp, input);

  c = strtok(temp, delim);
  while(c != NULL)
  {
    counter = counter + 1;
    c = strtok(NULL, delim);
  }
  return counter;
}

int main()
{
    char input[200];
    char *currArg;
    char **parsedInput;
    int argCount;
    int i;
    int pid;

    while (1)
    {
        printf("DMD113's Shell > ");
        fgets(input, 200, stdin);
        argCount = countArgs(input);
        parsedInput = malloc(sizeof(char *) * (argCount + 1));

        currArg = strtok(input, delim);
        i = 0;
        while (currArg != NULL)
        {
            parsedInput[i] = (char *) malloc(sizeof(char) * strlen(currArg));
            strcpy(parsedInput[i], currArg);
            currArg = strtok(NULL, delim);
            i++;
        }
        parsedInput[argCount] = NULL;

        // Continue if the user does not enter anything
        if (parsedInput[0] == NULL)
        {
            continue;
        }
        // Exit the shell if the user enters 'exit'
        else if (strcmp(parsedInput[0], "exit") == 0)
        {
            exit(0);
        }
        // Change the director if the user enteres 'cd'
        else if (strcmp(parsedInput[0], "cd") == 0)
        {
            if (chdir(parsedInput[1]) == -1)
            {
                perror("DMD113's Shell");
            }
        }
        // Create a new process and try to run that
        else
        {
            pid = fork();
            if (pid != 0)
            {
                wait(NULL);
            }
            else
            {
                if (strstr(input, ">>") != NULL)
                {
                    freopen(parsedInput[argCount - 1], "a", stdout);
                    free(parsedInput[argCount - 1]);
                    parsedInput[argCount - 1] = NULL;
                }
                else if (strstr(input, ">") != NULL)
                {
                    freopen(parsedInput[argCount - 1], "w", stdout);
                    free(parsedInput[argCount - 1]);
                    parsedInput[argCount - 1] = NULL;
                }
                else if (strstr(input, "<") != NULL)
                {
                    freopen(parsedInput[argCount - 1], "r", stdout);
                    free(parsedInput[argCount - 1]);
                    parsedInput[argCount - 1] = NULL;
                }
                execvp(parsedInput[0], parsedInput);
                exit(0);
            }
        }
    }
}
