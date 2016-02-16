// PROPERTY OF DEVANSH DESAI
// UNIVERSITY OF PITSBURGH - CS 0445 03/21/2015
// This file was not written by me. It is strictly from the CS 0445 class
// from the University of Pittsburgh.

/** Sorts equally spaced entries of an array into ascending order.
@param a an array of Comparable objects
@param first the integer index of the first array entry to
consider; first >= 0 and < a.length
@param last the integer index of the last array entry to
consider; last >= first and < a.length
@param space the difference between the indices of the
entries to sort */
public class ShellSort
{
      private static <T extends Comparable<? super T>>
            void incrementalInsertionSort(T[] a, int first, int last, int space)
            {
                  int unsorted, index;
                  for (unsorted = first + space; unsorted <= last;
                        unsorted = unsorted + space)
                        {
                              T nextToInsert = a[unsorted];
                              for (index = unsorted - space; (index >= first) && (nextToInsert.compareTo(a[index]) < 0);
                              index = index - space)
                              {
                                    a[index + space] = a[index];
                              } // end for
                              a[index + space] = nextToInsert;
                        } // end for
                  } // end incrementalInsertionSort

      public static <T extends Comparable<? super T>>
            void shellSort(T[] a, int first, int last)
            {
                  int n = last - first + 1; // number of array entries
                  for (int space = n / 2; space > 0; space = space / 2)
                  {
                        for (int begin = first; begin < first + space; begin++)
                        {
                        incrementalInsertionSort(a, begin, last, space);
                        }
                  } // end for
            } // end shellSort
}
