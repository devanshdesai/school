// PROPERTY OF DEVANSH DESAI
// UNIVERSITY OF PITSBURGH - CS 0445 03/21/2015
import java.util.*;
import java.io.*;

public class Assig4
{
      private int arraySize;
      private int arrayTrials;
      private String outputFileName;
      private Integer[] randomData;
      private Integer[] ascendingData;
      private Integer[] descendingData;
      private Integer[] copiedData;
      private long totalTimeSimpleRandom = 0;
      private long totalTimeMed5Random = 0;
      private long totalTimeMed10Random = 0;
      private long totalTimeMed20Random = 0;
      private long totalTimeRandPivRandom = 0;
      private long totalTimeMergeRandom = 0;
      private long totalTimeShellRandom = 0;
      private long totalTimeSimpleAscending = 0;
      private long totalTimeMed5Ascending = 0;
      private long totalTimeMed10Ascending = 0;
      private long totalTimeMed20Ascending = 0;
      private long totalTimeRandPivAscending = 0;
      private long totalTimeMergeAscending = 0;
      private long totalTimeShellAscending = 0;
      private long totalTimeSimpleDescending = 0;
      private long totalTimeMed5Descending = 0;
      private long totalTimeMed10Descending = 0;
      private long totalTimeMed20Descending = 0;
      private long totalTimeRandPivDescending = 0;
      private long totalTimeMergeDescending = 0;
      private long totalTimeShellDescending = 0;

      public Assig4(int aS, int aT, String name)
      {
    	  	arraySize = aS;
    	  	arrayTrials = aT;
    	  	outputFileName = name;

            randomData = new Integer[arraySize];
            ascendingData = new Integer[arraySize];
            descendingData = new Integer[arraySize];


            // Putting ascending integers into the ascendingData array
            for (int a = 0; a < arraySize; a++)
            {
                  ascendingData[a] = new Integer(a);
            }

            // Putting descending integers into the descendingData array
            for (int b = 0; b < arraySize; b++)
            {
                  descendingData[arraySize - 1 - b] = new Integer(b);
            }

            // Creates an array in which sorting algorithms can copy the original
            // data files that all arrays use.
            copiedData = new Integer[arraySize];
      }

      public void randomDataTest()
      {
          	Random rand = new Random();
            // Trials for randomized data
            for (int a = 0; a < arrayTrials; a++)
            {
                  // Puting random integers into the randomData array
                  for (int b = 0; b < arraySize; b++)
                  {
                        randomData[b] = new Integer(rand.nextInt(100000000));
                  }
                  // Random data - Simple QuickSort with copiedData[last] as pivot
                  copiedData = randomData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Simple QuickSort\nArray Size: " + arraySize + "\nData Configuration: Random\nData: ");
                        for (int d = 0; d < arraySize; d++)
                        {
                              System.out.print(copiedData[d] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeSimpleRandom = System.nanoTime();
                  SimpleQuickSort.quickSort(copiedData, arraySize);
                  totalTimeSimpleRandom += (System.nanoTime()) - startTimeSimpleRandom;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int e = 0; e < arraySize; e++)
                        {
                              System.out.print(copiedData[e] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeSimpleRandom/1000000000.0);
                        System.out.println(" seconds\n");
                  }
                  // Random data - Median of Three QuickSort with base case array size <5
                  copiedData = randomData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Median of Three (5)\nArray Size: " + arraySize + "\nData Configuration: Random\nData: ");
                        for (int g = 0; g < arraySize; g++)
                        {
                              System.out.print(copiedData[g] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeMed5Random = System.nanoTime();
                  MedianQuickSort.quickSort(5, copiedData, arraySize);
                  totalTimeMed5Random += (System.nanoTime()) - startTimeMed5Random;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int h = 0; h < arraySize; h++)
                        {
                              System.out.print(copiedData[h] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeMed5Random/1000000000.0);
                        System.out.println(" seconds\n");
                  }
                  // Random data - Median of Three QuickSort with base case array size <10
                  copiedData = randomData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Median of Three (10)\nArray Size: " + arraySize + "\nData Configuration: Random\nData: ");
                        for (int j = 0; j < arraySize; j++)
                        {
                              System.out.print(copiedData[j] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeMed10Random = System.nanoTime();
                  MedianQuickSort.quickSort(10, copiedData, arraySize);
                  totalTimeMed10Random += (System.nanoTime()) - startTimeMed10Random;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int k = 0; k < arraySize; k++)
                        {
                              System.out.print(copiedData[k] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeMed10Random/1000000000.0);
                        System.out.println(" seconds\n");
                  }
                  // Random data - Median of Three QuickSort with base case array size <20
                  copiedData = randomData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Median of Three (20)\nArray Size: " + arraySize + "\nData Configuration: Random\nData: ");
                        for (int m = 0; m < arraySize; m++)
                        {
                              System.out.print(copiedData[m] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeMed20Random = System.nanoTime();
                  MedianQuickSort.quickSort(20, copiedData, arraySize);
                  totalTimeMed20Random += (System.nanoTime()) - startTimeMed20Random;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int n = 0; n < arraySize; n++)
                        {
                              System.out.print(copiedData[n] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeMed20Random/1000000000.0);
                        System.out.println(" seconds\n");
                  }
                  // Random data - Random Pivot Quicksort with base case array size < 5
                  copiedData = randomData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Random Pivot (5)\nArray Size: " + arraySize + "\nData Configuration: Random\nData: ");
                        for (int o = 0; o < arraySize; o++)
                        {
                              System.out.print(copiedData[o] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeRandPivRandom = System.nanoTime();
                  RandomPivotQuickSort.quickSort(copiedData, arraySize);
                  totalTimeRandPivRandom += (System.nanoTime()) - startTimeRandPivRandom;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int p = 0; p < arraySize; p++)
                        {
                              System.out.print(copiedData[p] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeRandPivRandom/1000000000.0);
                        System.out.println(" seconds\n");
                  }
                  // Random data - MergeSort
                  copiedData = randomData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: MergeSort\nArray Size: " + arraySize + "\nData Configuration: Random\nData: ");
                        for (int r = 0; r < arraySize; r++)
                        {
                              System.out.print(copiedData[r] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeMergeRandom = System.nanoTime();
                  MergeSort.mergeSort(copiedData, arraySize);
                  totalTimeMergeRandom += (System.nanoTime()) - startTimeMergeRandom;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int s = 0; s < arraySize; s++)
                        {
                              System.out.print(copiedData[s] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeMergeRandom/1000000000.0);
                        System.out.println(" seconds\n");
                  }
                  // Random data - ShellSort
                  copiedData = randomData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: ShellSort\nArray Size: " + arraySize + "\nData Configuration: Random\nData: ");
                        for (int t = 0; t < arraySize; t++)
                        {
                              System.out.print(copiedData[t] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeShellRandom = System.nanoTime();
                  ShellSort.shellSort(copiedData, 0, arraySize-1);
                  totalTimeShellRandom += (System.nanoTime()) - startTimeShellRandom;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int u = 0; u < arraySize; u++)
                        {
                              System.out.print(copiedData[u] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeShellRandom/1000000000.0);
                        System.out.println(" seconds\n");
                  }

            }
      }










      public void ascendingDataTest()
      {
            // Ascending data - Simple QuickSort with copiedData[last] as pivot
            for (int a = 0; a < arrayTrials; a++)
            {
                  copiedData = ascendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Simple QuickSort\nArray Size: " + arraySize + "\nData Configuration: Sorted\nData: ");
                        for (int c = 0; c < arraySize; c++)
                        {
                              System.out.print(copiedData[c] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeSimpleAscending = System.nanoTime();
                  SimpleQuickSort.quickSort(copiedData, arraySize);
                  totalTimeSimpleAscending += (System.nanoTime()) - startTimeSimpleAscending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int d = 0; d < arraySize; d++)
                        {
                              System.out.print(copiedData[d] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeSimpleAscending/1000000000.0);
                        System.out.println(" seconds\n");
                  }
            }
            // Ascending data - Median of Three QuickSort with base case array size <5
            for (int e = 0; e < arrayTrials; e++)
            {
                  copiedData = ascendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Median of Three (5)\nArray Size: " + arraySize + "\nData Configuration: Sorted\nData: ");
                        for (int g = 0; g < arraySize; g++)
                        {
                              System.out.print(copiedData[g] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeMed5Ascending = System.nanoTime();
                  MedianQuickSort.quickSort(5, copiedData, arraySize);
                  totalTimeMed5Ascending += (System.nanoTime()) - startTimeMed5Ascending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int h = 0; h < arraySize; h++)
                        {
                              System.out.print(copiedData[h] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeMed5Ascending/1000000000.0);
                        System.out.println(" seconds\n");
                  }
            }
            // Ascending data - Median of Three QuickSort with base case array size <10
            for (int i = 0; i < arrayTrials; i++)
            {
                  copiedData = ascendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Median of Three (10)\nArray Size: " + arraySize + "\nData Configuration: Sorted\nData: ");
                        for (int k = 0; k < arraySize; k++)
                        {
                              System.out.print(copiedData[k] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeMed10Ascending = System.nanoTime();
                  MedianQuickSort.quickSort(10, copiedData, arraySize);
                  totalTimeMed10Ascending += (System.nanoTime()) - startTimeMed10Ascending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int l = 0; l < arraySize; l++)
                        {
                              System.out.print(copiedData[l] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeMed10Ascending/1000000000.0);
                        System.out.println(" seconds\n");;
                  }
            }
            // Ascending data - Median of Three QuickSort with base case array size <20
            for (int m = 0; m < arrayTrials; m++)
            {
                  copiedData = ascendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Median of Three (20)\nArray Size: " + arraySize + "\nData Configuration: Sorted\nData: ");
                        for (int o = 0; o < arraySize; o++)
                        {
                              System.out.print(copiedData[o] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeMed20Ascending = System.nanoTime();
                  MedianQuickSort.quickSort(20, copiedData, arraySize);
                  totalTimeMed20Ascending += (System.nanoTime()) - startTimeMed20Ascending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int p = 0; p < arraySize; p++)
                        {
                              System.out.print(copiedData[p] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeMed20Ascending/1000000000.0);
                        System.out.println(" seconds\n");
                  }
            }
            // Ascending data - Random Pivot Quicksort with base case array size < 5
            for (int z = 0; z < arrayTrials; z++)
            {
                  copiedData = ascendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Random Pivot (5)\nArray Size: " + arraySize + "\nData Configuration: Sorted\nData: ");
                        for (int q = 0; q < arraySize; q++)
                        {
                              System.out.print(copiedData[q] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeRandPivAscending = System.nanoTime();
                  RandomPivotQuickSort.quickSort(copiedData, arraySize);
                  totalTimeRandPivAscending += (System.nanoTime()) - startTimeRandPivAscending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int r = 0; r < arraySize; r++)
                        {
                              System.out.print(copiedData[r] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeRandPivAscending/1000000000.0);
                        System.out.println(" seconds\n");
                  }
            }
            // Ascending data - MergeSort
            for (int r = 0; r < arrayTrials; r++)
            {
                  copiedData = ascendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: MergeSort\nArray Size: " + arraySize + "\nData Configuration: Sorted\nData: ");
                        for (int s = 0; s < arraySize; s++)
                        {
                              System.out.print(copiedData[s] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeMergeAscending = System.nanoTime();
                  MergeSort.mergeSort(copiedData, arraySize);
                  totalTimeMergeAscending += (System.nanoTime()) - startTimeMergeAscending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int t = 0; t < arraySize; t++)
                        {
                              System.out.print(copiedData[t] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeMergeAscending/1000000000.0);
                        System.out.println(" seconds\n");
                  }
            }
            // Ascending data - ShellSort
            for (int u = 0; u < arrayTrials; u++)
            {
                  copiedData = ascendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: ShellSort\nArray Size: " + arraySize + "\nData Configuration: Sorted\nData: ");
                        for (int v = 0; v < arraySize; v++)
                        {
                              System.out.print(copiedData[v] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeShellAscending = System.nanoTime();
                  ShellSort.shellSort(copiedData, 0, arraySize-1);
                  totalTimeShellAscending += (System.nanoTime()) - startTimeShellAscending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int w = 0; w < arraySize; w++)
                        {
                              System.out.print(copiedData[w] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeShellAscending/1000000000.0);
                        System.out.println(" seconds\n");
                  }
            }
      }







      public void descendingDataTest()
      {
            // Descending data - Simple QuickSort with copiedData[last] as pivot
            for (int a = 0; a < arrayTrials; a++)
            {
                  copiedData = descendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Simple QuickSort\nArray Size: " + arraySize + "\nData Configuration: Reverse Sorted\nData: ");
                        for (int b = 0; b < arraySize; b++)
                        {
                              System.out.print(copiedData[b] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeSimpleDescending = System.nanoTime();
                  SimpleQuickSort.quickSort(copiedData, arraySize);
                  totalTimeSimpleDescending += (System.nanoTime()) - startTimeSimpleDescending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int c = 0; c < arraySize; c++)
                        {
                              System.out.print(copiedData[c] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeSimpleDescending/1000000000.0);
                        System.out.println(" seconds\n");
                  }
            }
            // Descending data - Median of Three QuickSort with base case array size <5
            for (int d = 0; d < arrayTrials; d++)
            {
                  copiedData = descendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Median of Three (5)\nArray Size: " + arraySize + "\nData Configuration: Reverse Sorted\nData: ");
                        for (int e = 0; e < arraySize; e++)
                        {
                              System.out.print(copiedData[e] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeMed5Descending = System.nanoTime();
                  MedianQuickSort.quickSort(5, copiedData, arraySize);
                  totalTimeMed5Descending += (System.nanoTime()) - startTimeMed5Descending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int f = 0; f < arraySize; f++)
                        {
                              System.out.print(copiedData[f] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeMed5Descending/1000000000.0);
                        System.out.println(" seconds\n");
                  }
            }
            // Descending data - Median of Three QuickSort with base case array size <10
            for (int g = 0; g < arrayTrials; g++)
            {
                  copiedData = descendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Median of Three (10)\nArray Size: " + arraySize + "\nData Configuration: Reverse Sorted\nData: ");
                        for (int h = 0; h < arraySize; h++)
                        {
                              System.out.print(copiedData[h] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeMed10Descending = System.nanoTime();
                  MedianQuickSort.quickSort(10, copiedData, arraySize);
                  totalTimeMed10Descending += (System.nanoTime()) - startTimeMed10Descending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int i = 0; i < arraySize; i++)
                        {
                              System.out.print(copiedData[i] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeMed10Descending/1000000000.0);
                        System.out.println(" seconds\n");
                  }
            }
            // Descending data - Median of Three QuickSort with base case array size <20
            for (int j = 0; j < arrayTrials; j++)
            {
                  copiedData = descendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Median of Three (20)\nArray Size: " + arraySize + "\nData Configuration: Reverse Sorted\nData: ");
                        for (int k = 0; k < arraySize; k++)
                        {
                              System.out.print(copiedData[k] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeMed20Descending = System.nanoTime();
                  MedianQuickSort.quickSort(20, copiedData, arraySize);
                  totalTimeMed20Descending += (System.nanoTime()) - startTimeMed20Descending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int l = 0; l < arraySize; l++)
                        {
                              System.out.print(copiedData[l] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeMed20Descending/1000000000.0);
                        System.out.println(" seconds\n");
                  }
            }
            // Descending data - Random Pivot Quicksort with base case array size < 5
            for (int z = 0; z < arrayTrials; z++)
            {
                  copiedData = descendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: Random Pivot (5)\nArray Size: " + arraySize + "\nData Configuration: Reverse Sorted\nData: ");
                        for (int q = 0; q < arraySize; q++)
                        {
                              System.out.print(copiedData[q] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeRandPivDescending = System.nanoTime();
                  RandomPivotQuickSort.quickSort(copiedData, arraySize);
                  totalTimeRandPivDescending += (System.nanoTime()) - startTimeRandPivDescending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int r = 0; r < arraySize; r++)
                        {
                              System.out.print(copiedData[r] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeRandPivDescending/1000000000.0);
                        System.out.println(" seconds\n");
                  }
            }
            // Descending data - MergeSort
            for (int s = 0; s < arrayTrials; s++)
            {
                  copiedData = descendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: MergeSort\nArray Size: " + arraySize + "\nData Configuration: Reverse Sorted\nData: ");
                        for (int t = 0; t < arraySize; t++)
                        {
                              System.out.print(copiedData[t] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeMergeDescending = System.nanoTime();
                  MergeSort.mergeSort(copiedData, arraySize);
                  totalTimeMergeDescending += (System.nanoTime()) - startTimeMergeDescending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int u = 0; u < arraySize; u++)
                        {
                              System.out.print(copiedData[u] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeMergeDescending/1000000000.0);
                        System.out.println(" seconds\n");
                  }
            }
            // Descending data - ShellSort
            for (int v = 0; v < arrayTrials; v++)
            {
                  copiedData = descendingData.clone();
                  if (arraySize <= 20)
                  {
                        System.out.print("Algorithm: ShellSort\nArray Size: " + arraySize + "\nData Configuration: Reverse Sorted\nData: ");
                        for (int w = 0; w < arraySize; w++)
                        {
                              System.out.print(copiedData[w] + " ");
                        }
                        System.out.println();
                  }
                  long startTimeShellDescending = System.nanoTime();
                  ShellSort.shellSort(copiedData, 0, arraySize-1);
                  totalTimeShellDescending += (System.nanoTime()) - startTimeShellDescending;
                  if (arraySize <= 20)
                  {
                        System.out.print("Sorted Data: ");
                        for (int x = 0; x < arraySize; x++)
                        {
                              System.out.print(copiedData[x] + " ");
                        }
                        System.out.println();
                        System.out.print("Time: ");
                        System.out.printf("%.9f", totalTimeShellDescending/1000000000.0);
                        System.out.println(" seconds\n");
                  }
            }
      }

      public void outputResults()
      {
            double size = (double) arrayTrials;
            PrintWriter writer;
            while (true)
            {
            	try
            	{
            		writer = new PrintWriter(outputFileName, "UTF-8");
            		break;
            	}
            	catch (IOException e)
            	{
            		System.out.println("Problem: " + e);
            	}
            }
            writer.println("PROPERTY OF DEVANSH DESAI\nUNIVERSITY OF PITTSBURGH - CS 0445\n\n\nOUTPUT OF ASSIG4.JAVA\n");

            writer.println("Algorithm: Simple QuickSort");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Random");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeSimpleRandom/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: Median of Three (5)");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Random");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeMed5Random/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: Median of Three (10)");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Random");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeMed10Random/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: Median of Three (20)");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Random");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeMed20Random/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: Random Pivot (5)");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Random");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeRandPivRandom/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: MergeSort");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Random");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeMergeRandom/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: ShellSort");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Random");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeShellRandom/size)/1000000000.0);
            writer.println(" seconds\n________________________________________________\n");
            writer.println("Algorithm: Simple QuickSort");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeSimpleAscending/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: Median of Three (5)");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeMed5Ascending/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: Median of Three (10)");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeMed10Ascending/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: Median of Three (20)");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeMed20Ascending/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: Random Pivot (5)");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeRandPivAscending/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: MergeSort");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeMergeAscending/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: ShellSort");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeShellAscending/size)/1000000000.0);
            writer.println(" seconds\n________________________________________________\n");
            writer.println("Algorithm: Simple QuickSort");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Reverse Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeSimpleDescending/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: Median of Three (5)");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Reverse Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeMed5Descending/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: Median of Three (10)");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Reverse Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeMed10Descending/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: Median of Three (20)");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Reverse Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeMed20Descending/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: Random Pivot (5)");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Reverse Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeRandPivDescending/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: MergeSort");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Reverse Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeMergeDescending/size)/1000000000.0);
            writer.println(" seconds\n");
            writer.println("Algorithm: ShellSort");
            writer.println("Array Size: " + arraySize);
            writer.println("Order: Reverse Sorted");
            writer.println("Number of Trials: " + arrayTrials);
            writer.print("Average Time Per Trial: ");
            writer.printf("%.9f", (totalTimeShellDescending/size)/1000000000.0);
            writer.println(" seconds");

            writer.close();
      }








      public static void main(String args[])
      {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Please enter the size of the arrays to be tested:");
            int arraySize = keyboard.nextInt();
            System.out.println("Please enter the number of trials for each array test:");
            int arrayTrials = keyboard.nextInt();
            System.out.println("Please enter the name of the text file to output to:");
            boolean correct = false;
            String fileName = "";
            while(!correct)
            {
                 fileName = keyboard.next();
                 int length = fileName.length();
                 String extension = fileName.substring(length - 4);
                 if (extension.equals(".txt"))
                 {
                       correct = true;
                 }
                 else
                 {
                       System.out.println("Please enter a valid text file name.");
                 }
           }

            Assig4 test = new Assig4(arraySize, arrayTrials, fileName);
            test.randomDataTest();
            test.ascendingDataTest();
            test.descendingDataTest();
            test.outputResults();
            keyboard.close();

            System.out.println("Sorting results are in '" + fileName + "' in the root directory.");

      }
}
