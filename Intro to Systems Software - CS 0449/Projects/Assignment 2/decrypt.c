/*  DEVANSH DESAI
    CS 0449 FALL 2015
    PROJECT 2 - PART 1
    decrypt.c
*/

#include <stdio.h>

int main(int argc, char *argv[])
{
    if (argc != 2)
    {
        printf("You have not entered a valid file name. Program will exit.\n\n");
        exit(0);
    }

    FILE *string;

    string = fopen(argv[1], "rb");
    if (string == NULL)
    {
        printf("This file does not exist in the same directory as decrypt.c\n\n");
        exit(0);
    }

    // Read in all of the file and store bytes into buffer
    fseek(string, 0, SEEK_END);
    long filelen = ftell(string);
    rewind(string);
    unsigned char *buffer = (unsigned char *) malloc( (filelen+1) * sizeof(unsigned char));
    fread(buffer, filelen, 1, string);
    fclose(string);

    int i = 0;
    int stringCounter = 0;

    // Goes through the entire file searching for a string
    while (i < filelen)
    {
        // Checks if the current byte is an acceptable ASCII character
        while ((buffer[i] >= 0x20 && buffer[i] <= 0x7E) || buffer[i] == 0xA)
        {
            stringCounter++;
            i++;
        }
        // If a string of length 4 has been found, print it. Otherwise, reset the string counter and continue.
        if (stringCounter >= 4)
        {
            i -= stringCounter;
            int temp = i;
            for (i; i < (stringCounter + temp); i++)
            {
                printf("%c", buffer[i]);
            }
        }
        else if (stringCounter == 0)
        {
            i++;
        }

        stringCounter = 0;
    }
}
