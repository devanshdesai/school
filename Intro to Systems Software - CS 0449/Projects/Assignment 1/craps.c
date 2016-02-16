/*  DEVANSH DESAI
    CS 0449 FALL 2015
    PROJECT 1 - PART 1
    craps.c
*/


#include <stdio.h>
#include <stdlib.h>
#include <time.h>


int main(void)
{
    printf("\nWelcome to the game of Craps at the CS0449 Casino!\nPlease enter your name:  ");
    char name[50];
    char playOrQuit[5];
    char yesOrNo[5];
    int dice1;
    int dice2;
    int sum;
    srand((unsigned int)time(NULL));
    scanf("%s", name);
    printf("%s, would you like to play or quit? Enter 'play' or 'quit'.  ", name);
    scanf(" %s", playOrQuit);

    // Loop to ask user if he/she wants to play or quit
    while (1)
    {
        if (strcmp(playOrQuit, "play") == 0)
        {
            printf("\n");
            break;
        }
        else if (strcmp(playOrQuit, "quit") == 0)
        {
            printf("\nGoodbye, %s!\n\n", name);
            exit(0);
        }
        else
        {
            printf("\nYou have entered an invalid command. Please enter either 'play' or 'quit'.\n");
            printf("\n%s, would you like to play or quit? Enter 'play' or 'quit'.  ", name);
            scanf(" %s", playOrQuit);
        }
    }

    // Loop that randomly rolls two dice and outputs the sum. Different conditions exist
    // according to the rules of craps.
    while (1)
    {
        dice1 = rand() % (6 - 1 + 1) + 1;
        dice2 = rand() % (6 - 1 + 1) + 1;
        sum = dice1 + dice2;
        if ((sum == 2) || (sum == 3) || (sum == 12))
        {
            printf("You have rolled %i + %i = %i\n", dice1, dice2, sum);
            printf("\nSorry, you lost!\n\n");
        }
        else if ((sum == 7) || (sum == 11))
        {
            printf("You have rolled %i + %i = %i\n", dice1, dice2, sum);
            printf("\nCongratulations, you win!\n\n");
        }
        else
        {
            printf("You have rolled %i + %i = %i (point)\n", dice1, dice2, sum);
            int again = 1;
            int point = sum;
            while (again)
            {
                dice1 = rand() % (6 - 1 + 1) + 1;
                dice2 = rand() % (6 - 1 + 1) + 1;
                sum = dice1 + dice2;
                printf("You have rolled %i + %i = %i.\n", dice1, dice2, sum);
                if (sum == point)
                {
                    printf("Congratulations, you won!\n\n");
                    again = 0;
                }
                else if (sum == 7)
                {
                    printf("Sorry, you lost!\n\n");
                    again = 0;
                }
            }
        }

        // The user can keep playing if he/she wants.
        printf("Would you like to play again?  ");
        scanf(" %s", yesOrNo);
        while (1)
        {
            if (strcmp(yesOrNo, "yes") == 0)
            {
                printf("\n");
                break;
            }
            else if (strcmp(yesOrNo, "no") == 0)
            {
                printf("\nGoodbye, %s!\n\n", name);
                exit(0);
            }
            else
            {
                printf("You have entered an invalid command. Please enter either 'yes' or 'no'.\n");
                printf("\nWould you like to play again?  ");
                scanf(" %s", yesOrNo);
                printf("\n");
            }
        }
    }
}
