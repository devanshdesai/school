/*
    DEVANSH DESAI
    CS 1550 - SPRING 2017
    PROJECT 1 - 01/25/2016
    library.c
*/

#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <sys/ioctl.h>
#include <sys/mman.h>
#include <linux/fb.h>
#include <termios.h>
#include <fcntl.h>
#include "iso_font.h"

int fd;
unsigned long xlen, ylen, size;
unsigned short *fb_ptr;
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

// This will initialize the graphics display.
void init_graphics()
{
    struct fb_var_screeninfo vinfo;
    struct fb_fix_screeninfo finfo;
    struct termios termsettings;

    fd = open("/dev/fb0", O_RDWR);
    ioctl(fd, FBIOGET_VSCREENINFO, &vinfo);
    ioctl(fd, FBIOGET_FSCREENINFO, &finfo);
    xlen = vinfo.xres_virtual;
    ylen = vinfo.yres_virtual;
    size = finfo.line_length;
    fb_ptr = (unsigned short *) mmap(NULL, xlen * size, PROT_WRITE, MAP_SHARED, fd, 0);

    // This will retreive the terminal settings, turn off canonical and echo mode, and save the settings.
    ioctl(STDIN_FILENO, TCGETS, &termsettings);
    termsettings.c_lflag &= ~ICANON;
    termsettings.c_lflag &= ~ECHO;
    ioctl(STDIN_FILENO, TCSETS, &termsettings);
}

// This will clean everything up before the program quits.
void exit_graphics()
{
    struct termios termsettings;

    munmap(fb_ptr, ylen * size);

    // This will retreive the terminal settings, turn on canonical and echo mode, and save the settings.
    ioctl(STDIN_FILENO, TCGETS, &termsettings);
    termsettings.c_lflag |= ICANON;
    termsettings.c_lflag |= ECHO;
    ioctl(STDIN_FILENO, TCSETS, &termsettings);

    close(fd);
}

// This function uses the ANSI escape code "\033[2J" to clear the screen.
void clear_screen()
{
    write(1, "\033[2J", 8);
}

// This functions uses the non-blocking syscall select to read an input from the user.
char getkey()
{
    fd_set fds;
    struct timeval timev;
    char input;
    int r;

    FD_ZERO(&fds);
    FD_SET(0, &fds);
    timev.tv_sec = 5;
    timev.tv_usec = 0;
    r = select(STDIN_FILENO+1, &fds, NULL, NULL, &timev);

    if (r > 0)
    {
        read(0, &input, sizeof(input));
    }

    return input;
}

// This function lets the program sleep for the given number of milliseconds.
void sleep_ms(long ms)
{
    struct timespec timesp;
    timesp.tv_sec = 0;
    timesp.tv_nsec = ms * 1000000;
    nanosleep(&timesp, NULL);
}

// This the method that does the actual work of draw a single pixel on the screen.
// This is done through pointer arithmetic in the memory map.
void draw_pixel(int x, int y, color_t color)
{
    if (x < 0 || x >= xlen || y < 0 || y >= ylen)
    {
        return;
    }

    unsigned long vaddress = (size / 2) * y;
    unsigned long haddress = x;
    unsigned short *pixel_ptr = fb_ptr + vaddress + haddress;
    *pixel_ptr = color;
}

// This function draws the outline of a specified rectangle with the top left pixel at (x1, y1).
void draw_rect(int x1, int y1, int height, int width, color_t color)
{
    int x, y;
    for (x = x1; x < x1 + width; x++)
    {
        for (y = y1; y < y1 + height; y++)
        {
            if (x == x1 || x == x1 + (width - 1) || y == y1 || y == y1 + (height - 1))
            {
                draw_pixel(x, y, color);
            }
        }
    }
}

// This function fills in a specified rectangle with the top left pixel at (x1, y1).
void fill_rect(int x1, int y1, int height, int width, color_t color)
{
    int x, y;
    for (x = x1; x < x1 + width; x++)
    {
        for (y = y1; y < y1 + height; y++)
        {
            draw_pixel(x, y, color);
        }
    }
}

// This function draws a colored string at a certain location specified by the user on the display.
// It uses the information in the iso_font.h file to draw each character.
void draw_text(int x, int y, const char *string, color_t color)
{
    const char *temp;
    int shift = 0;
    for (temp = string; *temp != '\0'; temp++)
    {
        draw_character(x, y+shift, color, *temp);
        shift += 8;
    }
}

// This function is a helper function for draw_text(). It goes into the iso_font.h file
// to get the information.
void draw_character(int x, int y, color_t color, int ascii)
{
    int bit, i, j;
    for (i = 0; i < 16; i++)
    {
        for (j = 0; j < 16; j++)
        {
            bit = ((iso_font[ascii * 16 + i] & 1 << j) >> j);
            if (bit == 1)
            {
                draw_pixel(y+j, x+i, color);
            }
        }
    }
}
