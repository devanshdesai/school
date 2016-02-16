/*
    DEVANSH DESAI
    CS 1550 - SPRING 2017
    PROJECT 1 - 01/25/2016
    driver.c

    This program uses the library.c functions to intialize and use the graphics display.
    This is implemented through a memory map. For more information, see the library.c file.
*/

#include <stdio.h>

typedef unsigned short color_t;
void init_graphics();
void exit_graphics();
char getkey();
void sleep_ms(long ms);
void clear_screen();
void draw_pixel(int x, int y, color_t color);
void draw_rect(int x1, int y1, int height, int width, color_t color);
void fill_rect(int x1, int y1, int height, int width, color_t color);
void draw_text(int x, int y, const char *string, color_t color);
void draw_character(int x, int y, color_t color, int ascii);


int main(int argc, char** argv)
{
    printf("\n\nPress 0 to draw a rectangle.\nPress 1 to draw a filled rectangle.\n");
    printf("Press 2 to draw a string.\n\nUse 'WSAD' to move the image around.\n\n");
    printf("Press 9 to quit now or 'q' when a shape is on the screen.\n\n");

    char key;
    int input;
    int x = (640-20)/2;
    int y = (480-20)/2;

    scanf("%d", &input);
    if (input == 0)
    {
        const char *string = "hi";
        init_graphics();
        clear_screen();
        draw_rect(x, y, 150, 75, 30720);
        draw_text(y + 80, x + 35, string, 20);

        key = getkey();
        while (key != 'q')
        {
            if (key == 'w')
            {
                y -= 20;
            }
            else if (key == 's')
            {
                y += 20;
            }
            else if (key == 'a')
            {
                x -= 20;
            }
            else if (key == 'd')
            {
                x += 20;
            }

            clear_screen();
            draw_rect(x, y, 150, 75, 30720);
            draw_text(y + 80, x + 35, string, 20);
            sleep_ms(50);

            key = getkey();
        }

        clear_screen();
        exit_graphics();
    }
    else if (input == 1)
    {
        init_graphics();
        clear_screen();
        fill_rect(x, y, 150, 75, 30720);

        key = getkey();
        while (key != 'q')
        {
            if (key == 'w')
            {
                y -= 20;
            }
            else if (key == 's')
            {
                y += 20;
            }
            else if (key == 'a')
            {
                x -= 20;
            }
            else if (key == 'd')
            {
                x += 20;
            }

            clear_screen();
            fill_rect(x, y, 150, 75, 30720);
            sleep_ms(50);

            key = getkey();
        }

        clear_screen();
        exit_graphics();
    }
    else if (input == 2)
    {
        const char *string = "CS 1550 - GRAPHICS LIBRARY";
        init_graphics();
        clear_screen();
        draw_text(x, y, string, 30720);

        key = getkey();
        while (key != 'q')
        {
            if (key == 'w')
            {
                x -= 20;
            }
            else if (key == 's')
            {
                x += 20;
            }
            else if (key == 'a')
            {
                y -= 20;
            }
            else if (key == 'd')
            {
                y += 20;
            }

            clear_screen();
            draw_text(x, y, string, 30720);
            sleep_ms(50);

            key = getkey();
        }

        clear_screen();
        exit_graphics();
    }
    else if (input == 9)
    {
        clear_screen();
        exit_graphics();
    }
    else
    {
        printf("\n\nPlease enter a valid command.\n\n");
    }

	return 0;
}
