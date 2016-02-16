/*
    DEVANSH DESAI
    CS 0449 - FALL 2015
    PROJECT 4 - poker.c
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <errno.h>

int main(void)
{
    char writeBuffer[1];
    char readBuffer[2];
    int fp;
    int ret;
    int i;
    int j;
    int continuePlaying = 1;
    char hand[10];
    int *toChange = (int *) malloc(5 * sizeof(int));
    char *changeBuffer = (char *) malloc(100 * sizeof(char));
    int changeCounter = 0;
    char temp;
    char temp2;
    int savedIndex = -1;
    int clubs = (int) malloc(sizeof(int));
    int hearts = (int) malloc(sizeof(int));
    int spades = (int) malloc(sizeof(int));
    int diamonds = (int) malloc(sizeof(int));
    int firstLoop = (int) malloc(sizeof(int));
    firstLoop = 0;

    fp = open("/dev/deck", O_RDWR);

    if (fp == EACCES)
    {
        printf("Unable to open /dev/dev.\n");
        return -1;
    }

    printf("\nCards in order:\n");
    writeBuffer[0] = '0';
    ret = write(fp, writeBuffer, 1);
    for (i = 0; i < 52; i++)
    {
        read(fp, readBuffer, 2);
        printf("%i%c ", readBuffer[0], readBuffer[1]);
    }

    printf("\n\nCards after reset and shuffled:\n");
    writeBuffer[0] = '1';
    ret = write(fp, writeBuffer, 1);
    for (i = 0; i < 52; i++)
    {
        read(fp, readBuffer, 2);
        printf("%i%c ", readBuffer[0], readBuffer[1]);
    }

    printf("\n\nStart playing...\n\n");

    // Continue shuffling and getting cards until the user wants to stop.
    while (continuePlaying)
    {
        // Reset the toChange array
        for (i = 0; i < 5; i++)
        {
            toChange[i] = -1;
        }

        writeBuffer[0] = '1';
        ret = write(fp, writeBuffer, 1);

        // Get a hand from the shuffled deck
        for (i = 0; i < 5; i++)
        {
            read(fp, readBuffer, 2);
            hand[i * 2] = readBuffer[0];
            hand[(i * 2) + 1] = readBuffer[1];
        }
        changeCounter = 0;

        printf("Your hand:\n");
        for (i = 0; i < 5; i++)
        {
            printf("%i%c ", hand[i * 2], hand[(i * 2) + 1]);
        }

        printf("\nSelect cards to be changed (Type '0' for no changes): ");
        if (firstLoop)
        {
            firstLoop = 0;
            scanf("%[^\n]s", changeBuffer);
        }
        else
        {
            scanf(" %[^\n]s", changeBuffer);
        }

        changeCounter = 0;
        for (i = 0; i < 100; i++)
        {
            temp = changeBuffer[i];
            if (temp == '\n' || changeCounter == 5)
            {
                break;
            }
            else if ((temp - '0') > 0 && (temp - '0') < 6)
            {
                toChange[changeCounter] = temp - '0';
                changeCounter++;
            }
        }

        for (i = 0; i < changeCounter; i++)
        {
            if (toChange[i] != -1)
            {
                read(fp, readBuffer, 2);
                hand[(toChange[i] - 1) * 2] = readBuffer[0];
                hand[((toChange[i] - 1) * 2) + 1] = readBuffer[1];
            }
        }

        printf("\nYour hand:\n");
        for (i = 0; i < 5; i++)
        {
            printf("%i%c ", hand[i * 2], hand[(i * 2) + 1]);
        }

        // This loop uses a modified insertion sort to go through the hand of cards and sort it.
        // A better sorting algorithm could have been used but since there are only 5 cards, it is
        // not necessary.
        j = 0;
        while (1)
        {
            temp = hand[j * 2];
            temp2 = hand[(j * 2) + 1];

            for (i = j + 1; i < 5; i++)
            {
                if (temp > hand[i * 2])
                {
                    temp = hand[i * 2];
                    temp2 = hand[(i * 2) + 1];
                    savedIndex = i;
                }
                else if (temp == hand[i * 2] && temp2 > hand[(i * 2) + 1])
                {
                    temp = hand[i * 2];
                    temp2 = hand[(i * 2) + 1];
                    savedIndex = i;
                }
            }

            if (savedIndex != -1)
            {
                hand[savedIndex * 2] = hand[j * 2];
                hand[(savedIndex * 2) + 1] = hand[(j * 2) + 1];
                hand[j * 2] = temp;
                hand[(j * 2) + 1] = temp2;
            }

            j++;
            if (j == 5)
            {
                j = 0;
                break;
            }
            savedIndex = -1;
        }

        printf("\nYour sorted hand:\n");
        for (i = 0; i < 5; i++)
        {
            printf("%i%c ", hand[i * 2], hand[(i * 2) + 1]);

            // Get count of suits in hand
            if (hand[(i * 2) + 1] == 'C')
                clubs++;
            else if (hand[(i * 2) + 1] == 'H')
                hearts++;
            else if (hand[(i * 2) + 1] == 'S')
                spades++;
            else if (hand[(i * 2) + 1] == 'D')
                diamonds++;
        }

        // Figuring out which type of hand the user has
        if (clubs == 5 || hearts == 5 || spades == 5 || diamonds == 5)
        {
            if (hand[0] + 1 == hand[2] && hand[2] + 1 == hand[4] && hand[4] + 1 == hand[6] && (hand[6] + 1 == hand[8] || hand[8] == 14))
            {
                printf("\nYou got a Straight Flush.");
            }
            else
            {
                printf("\nYou got a Flush.");
            }
        }
        else if ((hand[0] == hand[2] && hand[0] == hand[4] && hand[0] == hand[6] && hand[0] != hand[8]) || (hand[2] == hand[4] && hand[2] == hand[6] && hand[2] == hand[8] && hand[2] != hand[0]))
        {
            printf("\nYou got Four of a Kind.");
        }
        else if ((hand[0] == hand[2] && hand[0] == hand[4] && hand[6] == hand[8]) || (hand[0] == hand[2] && hand[4] == hand[6] && hand[4] == hand[8]))
        {
            printf("\nYou got a Full House.");
        }
        else if (hand[0] + 1 == hand[2] && hand[2] + 1 == hand[4] && hand[4] + 1 == hand[6] && (hand[6] + 1 == hand[8] || hand[8] == 14))
        {
            printf("\nYou got a Straight.");
        }
        else if ((hand[0] == hand[2] && hand[0] == hand[4] && hand[0] != hand[6] && hand[0] != hand[8] && hand[6] != hand[8])
        || (hand[2] == hand[4] && hand[2] == hand[6] && hand[2] != hand[0] && hand[2] != hand[8] && hand[0] != hand[8])
        || (hand[4] == hand[6] && hand[4] == hand[8] && hand[4] != hand[0] && hand[4] != hand[2] && hand[0] != hand[2]))
        {
            printf("\nYou got Three of a Kind.");
        }
        else if ((hand[0] == hand[2] && hand[6] == hand[8] && hand[0] != hand[4] && hand[0] != hand[6] && hand[6] != hand[4])
        || (hand[2] == hand[4] && hand[6] == hand[8] && hand[2] != hand[0] && hand[6] != hand[0] && hand[2] != hand[6])
        || (hand[0] == hand[2] && hand[4] == hand[6] && hand[0] != hand[4] && hand[0] != hand[8] && hand[4] != hand[8]))
        {
             printf("\nYou got Two Pair.");
        }
        else if ((hand[0] == hand[2] && hand[0] != hand[4] && hand[0] != hand[6] && hand[0] != hand[8] && hand[4] != hand[6] && hand[6] != hand[8] && hand[4] != hand[8])
        || (hand[2] == hand[4] && hand[2] != hand[0] && hand[2] != hand[6] && hand[2] != hand[8] && hand[0] != hand[6] && hand[6] != hand[8] && hand[0] != hand[8])
        || (hand[4] == hand[6] && hand[4] != hand[0] && hand[4] != hand[2] && hand[4] != hand[8] && hand[0] != hand[2] && hand[0] != hand[8] && hand[2] != hand[8])
        || (hand[6] == hand[8] && hand[6] != hand[0] && hand[6] != hand[2] && hand[6] != hand[4] && hand[0] != hand[2] && hand[0] != hand[4] && hand[2] != hand[4]))
        {
            printf("\nYou got One Pair.");
        }
        else
        {
            printf("\nYou got High Card.");
        }

        // Loop to ask if the user wants to continue playing the game.
        while (1)
        {
            printf("\n\nWould you like to play again (y/n): ");
            scanf(" %c", &temp);
            if (temp == 'y')
            {
                continuePlaying = 1;
                printf("\n");
                break;
            }
            else if (temp == 'n')
            {
                continuePlaying = 0;
                printf("\nThanks for playing! Goodbye.\n\n");
                break;
            }
            else
            {
                    printf("\n\nPlease enter a valid command.\n");
            }
        }

        clubs = 0;
        hearts = 0;
        diamonds = 0;
        spades = 0;
    }
}
