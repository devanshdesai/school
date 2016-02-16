/*	DEVANSH DESAI
	CS 449 - FALL 2015
	LAB 08 - myprintf.c
*/

// I created a printChar() function specifically for Lab 08
// to easily iterate over the string to print in myprintf().

#include <unistd.h>
#include <stdarg.h>

int readInteger();
void printString(char *str);
void printInteger(int n);
void printFloat(float f);

int readInteger()
{
	// TO DO
	char num[11];
	int length = read(STDIN_FILENO, num, 11);
	int i, temp;
	int result = 0;
	for (i = 0; i < length - 1; i++)
	{
		result = result * 10 + num[i] - '0';
	}
	return result;
}

// Added a new printChar function for use in Lab 08
void printChar(char *str)
{
	if (str[0] != '\0')
		write(STDOUT_FILENO, str, 1);
}

void printString(char *str)
{
	// TO DO
	int i = 0;
	while (1)
	{
		if (str[i] != '\0')
			i++;
		else
			break;
	}
	write(STDOUT_FILENO, str, i);
}

void printInteger(int n)
{
	// TO DO
	if (n < 0)
	{
		write(STDOUT_FILENO, "-", 1);
		n *= -1;
	}
	int digits = 0;
	int temp = n;

	// Counts how many digits are in the integer.
   	while (temp != 0)
	{
	  temp /= 10;
	  digits++;
   	}
	int i, a, divisor = 10;

	// Makes divisor go to highest it can go to isolate the highest digit.
	for (i = 0; i < digits - 2; i++)
	{
		divisor *= 10;
	}

	// Go through all digits from left to right and add to char array result.
	// Write to the screen after all digits have been converted to a char.
	char result[digits];
	temp = n;
	if (digits == 1)
	{
		result[i] = temp + '0';
	}
	else
	{
		for (i = 0; i < digits; i++)
		{
			a = temp - (temp % divisor);
			a = a / divisor;
			result[i] = a + '0';
			temp = (n % divisor);
			divisor /= 10;
		}
	}
	write(STDOUT_FILENO, result, digits);
}

// This method just calls printInteger on the left half of the float from the decimal point and then the right half.
void printFloat(float f)
{
	// TO DO
	int leftSide = (int) f;
	printInteger(leftSide);
	if (f > -1 && f < 1)
		write(STDOUT_FILENO, "0", 1);
	leftSide = leftSide * 10 * 10 * 10 * 10 * 10 * 10;
	int rightSide = (int) (f * 10 * 10 * 10 * 10 * 10 * 10);
	rightSide -= leftSide;
	write(STDOUT_FILENO, ".", 1);
	if (rightSide == 0)
	{
		int i;
		for (i = 0; i < 6; i++)
		{
			write(STDOUT_FILENO, "0", 1);
		}
	}
	printInteger(rightSide);
}

void myprintf(char *format, ...)
{
	va_list ap;
	va_start(ap, format);

	while (*format != '\0')
	{
		if (*format == '%')
		{
			format++;
			if (*format == 'f')
			{
				printFloat((float) va_arg(ap, double));
			}
			else if (*format == 'i')
			{
				printInteger(va_arg(ap, int));
			}
			else if (*format == 's')
			{
				printString(va_arg(ap, char *));
			}
			format++;
		}
		else
		{
			printChar(format);
			format++;
		}
	}
}

int main(void)
{
	int x = 5, y = 9;
	char divMsg[] = "divided by";
	myprintf("%i %s %i is equal to %f.\n", x, divMsg, y, ((float) x)/y);

	return 0;
}