/*  DEVANSH DESAI
    CS 0449 FALL 2015
    PROJECT 1 - PART 2
    exifview.c
*/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Struct for the APP1 header
struct header
{
    unsigned short app1;
    unsigned short app1Length;
    unsigned char exif[4];
    unsigned short nullTerm;
    unsigned char endianess[2];
    unsigned short version;
    unsigned short startOffset;
};

// Struct for each TIFF tag
struct tiff
{
    unsigned short identifier;
    unsigned short dataType;
    unsigned int items;
    unsigned int offset;
};

// Prints string at the offset in each tiff.offset location
void printStringFromOffset(FILE *img, int offset, int letterCount, int app1Location)
{
    long long int fileLocation = ftell(img);
    char *reader = malloc(letterCount * sizeof(char));

    offset = offset + (app1Location + 10);
    fseek(img, offset, SEEK_SET);

    fread(reader, sizeof(char), letterCount, img);
    printf("%s", reader);

    fseek(img, fileLocation, SEEK_SET);
    free(reader);
}

// Prints fraction at the offset in each tiff.offset location
void printFractionFromOffset(FILE *img, int offset, int app1Location)
{
    long long int fileLocation = ftell(img);
    unsigned int *numerator = malloc(sizeof(unsigned int));
    unsigned int *denominator = malloc(sizeof(unsigned int));

    offset = offset + (app1Location + 10);
    fseek(img, offset, SEEK_SET);

    fread(&numerator, sizeof(unsigned int), 1, img);
    fread(&denominator, sizeof(unsigned int), 1, img);
    printf("%i/%i", numerator, denominator);

    fseek(img, fileLocation, SEEK_SET);
}

int main(int argc, char *argv[])
{
    if (argc != 2)
    {
        printf("You have not entered a valid file name. Program will exit.\n\n");
        exit(0);
    }

    FILE *img = fopen(argv[1], "rb");
    if (img == NULL)
    {
        printf("This file does not exist in the same directory as exifview.c\n\n");
        exit(0);
    }

    unsigned short *buffer = (unsigned short *) malloc((200)*sizeof(unsigned short));
    fread(buffer, 2, 100, img);
    // Checks to see if this is a valid .jpg file
    if (buffer[0] != 0xD8FF)
    {
        printf("This file, %s, is not compatible.\n\n", argv[1]);
    }

    // Loop finds the APP1 location
    int i;
    for (i = 0; i < 100; i++)
    {
        if (buffer[i] == 0xE1FF)
        {
            break;
        }
    }
    // If the APP1 is not found, the program exits.
    if (i == 100)
    {
        printf("APP1 header not found.\n\n");
        exit(0);
    }
    // APP1 is located at i*2 because the program read in 2 bytes at a time above
    int app1Location = i*2;

    struct header h;

    // Read in the APP1 header values when the APP1 location is found
    fseek(img, app1Location, SEEK_SET);
    fread(&h, sizeof(struct header), 1, img);

    // Check for "EXIF" string
    if (!strcmp(h.exif, "EXIF"))
    {
        printf("EXIF tag not found.\n\n");
        exit(0);
    }

    // Check for endianess compatability
    if (h.endianess[0] != 'I' && h.endianess[1] != 'I')
    {
        printf("Endianess not supported.\n\n");
        exit(0);
    }

    unsigned short count;
    struct tiff t;

    fseek(img, app1Location + 10 + h.startOffset, SEEK_SET);
    fread(&count, sizeof(short), 1, img);

    int j;
    for (j = 0; j < count; j++)
    {
        // Read in TIFF tag
        fread(&t, sizeof(struct tiff), 1, img);

        // Manufacturer identifier
        if (t.identifier == 0x010F)
        {
            printf("\nManufacturer\t: ");
            printStringFromOffset(img, t.offset, t.items, app1Location);
        }
        // Camera model identifier
        else if (t.identifier == 0x0110)
        {
            printf("\nModel \t\t: ");
            printStringFromOffset(img, t.offset, t.items, app1Location);
        }
        // Pixels per unit (width) identifier
        else if (t.identifier == 0x011A)
        {
            printf("\nX Res/Unit \t: ");
            printFractionFromOffset(img, t.offset, app1Location);
        }
        // Pixels per unit (height) identifier
        else if (t.identifier == 0x011B)
        {
            printf("\nY Res/Unit \t: ");
            printFractionFromOffset(img, t.offset, app1Location);
            printf("\nRes Unit \t: Inch");
        }
        // Sub block identifier
        else if (t.identifier == 0x8769)
        {
            fseek(img, t.offset + (app1Location + 10), SEEK_SET);
            break;
        }
    }

    // Get a new count
    fread(&count, sizeof(short), 1, img);

    for (j = 0; j < count; j++)
    {
        // Read in TIFF tag
        fread(&t, sizeof(struct tiff), 1, img);

        // Width (in pixels) identifier
        if (t.identifier == 0xA002)
        {
            printf("\nWidth \t\t: ");
            printf("%i", t.offset);
            printf(" pixels");
        }
        // Height (in pixels) identifier
        else if (t.identifier == 0xA003)
        {
            printf("\nHeight \t\t: ");
            printf("%i", t.offset);
            printf(" pixels\n\n");
        }
        // ISO speed identifier
        else if (t.identifier == 0x8827)
        {
            printf("\nISO \t\t: ISO-");
            printf("%i", t.offset % 65536);
        }
        // Exposure speed identifier
        else if (t.identifier == 0x829A)
        {
            printf("\nExposure Time \t: ");
            int offset = t.offset + (app1Location + 10);
            long long int fileLocation = ftell(img);
            unsigned int *n = malloc(sizeof(unsigned int));
            unsigned int *d = malloc(sizeof(unsigned int));
            fseek(img, offset, SEEK_SET);
            fread(&n, sizeof(unsigned int), 1, img);
            fread(&d, sizeof(unsigned int), 1, img);
            fseek(img, fileLocation, SEEK_SET);
            unsigned long long num = (unsigned long long) n;
            unsigned long long denom = (unsigned long long) d;
            if (num == 10)
            {
                printf("%i/%i sec.", num/10, denom/10);
            }
            else
            {
                printf("%i/%i sec.", num, denom);
            }
        }
        // F-stop identifier
        else if (t.identifier == 0x829D)
        {
            printf("\nF-stop \t\t: ");
            int offset = t.offset + (app1Location + 10);
            long long int fileLocation = ftell(img);
            unsigned int *n = malloc(sizeof(unsigned int));
            unsigned int *d = malloc(sizeof(unsigned int));
            fseek(img, offset, SEEK_SET);
            fread(&n, sizeof(unsigned int), 1, img);
            fread(&d, sizeof(unsigned int), 1, img);
            fseek(img, fileLocation, SEEK_SET);
            unsigned long long num = (unsigned long long) n;
            unsigned long long denom = (unsigned long long) d;
            double number = (double)num/denom;
            printf("f/%.1f", number);
        }
        // Lens focal length identifier
        else if (t.identifier == 0x920A)
        {
            printf("\nFocal Length \t: ");
            int offset = t.offset + (app1Location + 10);
            long long int fileLocation = ftell(img);
            unsigned long *n = malloc(sizeof(unsigned int));
            unsigned int *d = malloc(sizeof(unsigned int));
            fseek(img, offset, SEEK_SET);
            fread(&n, sizeof(unsigned int), 1, img);
            fread(&d, sizeof(unsigned int), 1, img);
            fseek(img, fileLocation, SEEK_SET);
            unsigned long long num = (unsigned long long) n;
            unsigned long long denom = (unsigned long long) d;
            printf("%i mm", num/denom);
        }
        // Date taken identifier
        else if (t.identifier == 0x9003)
        {
            printf("\nDate Taken \t: ");
            printStringFromOffset(img, t.offset, t.items, app1Location);
        }
    }

    fclose(img);
}
