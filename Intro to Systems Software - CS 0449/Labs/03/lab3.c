/* DEVANSH DESAI
   CS 449 - LAB 03
   09/28/2015 */

   #include <stdio.h>



   void strcopy(char dest[], const char src[])
   {
   	   int i = 0;
   	   while ((dest[i] = src[i]) != '\0')
       {
   		      i++;
   	 }
   }

   void strconcat(char *dest, const char *src)
   {
   	   size_t i,j;
   	    for (i = 0; dest[i] != '\0'; i++)
   		       ;
   	    for (j = 0; src[j] != '\0'; j++)
   		   dest[i + j] = src[j];
   	    dest[i + j] = '\0';
   }

   void strrev(char *str)
   {
       int length = 0,i = 0;

        while(str[i++]!='\0')
            length++;

        for(i = 0; i<length/2; i++)
        {
            str[length] = str[i];
            str[i] = str[length-i-1];
            str[length-i-1] = str[length];
        }

        str[length]='\0';
   }

   void substring(char *dest, int start, int end, char *src)
   {
   	int count = 0;
   	while(src[count] != '\0')
    {
   		count++;
   	}

   	if(end > count) end = count;
   	if(start < 0) start = 0;
   	if(end < 0) end = 0;

   	int difference = end - start;

   	int i;
   	if(end < start)
    {
   		dest = '\0';
   	}
   	else{


   	for(i = start; i < end + 1; i++ )
    {
   		dest[i - start] = src[i];
   	}
   	dest[i - start] = '\0';
   	}
   }

   	int main(void)
    {

   		char str1[] = "HELLO ";
   		char str2[] = "WORLD!";
   		char buffer1[100];
   		char buffer2[100];

   		printf("%s\n", str1);
   		strcopy(buffer1, str2);
   		printf("%s %s\n", buffer1, str2 );
   		strconcat(buffer1, str2);
   		printf("%s\n", buffer1 );
   		strrev(str2);
   		printf("%s\n", str2);
   		substring(buffer2, 2, 4, str1);
   		printf("%s\n", buffer2);
   	}
