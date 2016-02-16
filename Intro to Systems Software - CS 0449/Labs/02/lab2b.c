/*    DEVANSH DESAI
      CS 449 - LAB 02b
      09/22/2015
*/

#include <stdio.h>

int main()
{
      int items;
      float cost = 0;
      printf("\nHow many items do you wish to enter? ");
      scanf("%i", &items);
      int i;
      for (i = 1; i <= items; i++)
      {
            printf("Enter the price of the item #%d: ", i);
            float temp;
            scanf("%f", &temp);
            cost = cost + temp;
      }
      double tax = cost * 0.07;
      printf("\n\nYou have entered %i items.\n", items);
      printf("The sub-total is $%.2f.\n", cost);
      printf("The tax is $%.2f.\n", tax);
      cost += tax;
      printf("The total is $%.2f.\n\n", cost);

      return 0;
}
