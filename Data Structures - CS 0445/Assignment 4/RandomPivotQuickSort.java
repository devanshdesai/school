// PROPERTY OF DEVANSH DESAI
// UNIVERSITY OF PITSBURGH - CS 0445 03/21/2015
// This file was not written by me. It is strictly from the CS 0445 class
// from the University of Pittsburgh. I only added in lines 44-47.



import java.util.*;

public class RandomPivotQuickSort
{
	public static <T extends Comparable<? super T>>
		   void quickSort(T[] array, int n)
	{
		quickSort(array, 0, n-1);
	} // end quickSort

	public static <T extends Comparable<? super T>>
		   void quickSort(T[] array, int first, int last)
	{
		if (first < last)
		{
			int pivotIndex;

			// create the partition: Smaller | Pivot | Larger
			pivotIndex = partition(array, first, last);
			quickSort(array, first, pivotIndex-1);
			quickSort(array, pivotIndex+1, last);

			// sort subarrays Smaller and Larger

		} // end if
	}  // end quickSort

	private static <T extends Comparable<? super T>>
	        int partition(T[] a, int first, int last)
	{
		int pivotIndex;

        	Random rand = new Random();
		pivotIndex = rand.nextInt((last-first) + 1) + first;
		swap(a, pivotIndex, last);
		pivotIndex = last;
		T pivot = a[pivotIndex];

		// determine subarrays Smaller = a[first..endSmaller]
		//                 and Larger  = a[endSmaller+1..last-1]
		// such that elements in Smaller are <= pivot and
		// elements in Larger are >= pivot; initially, these subarrays are empty

		int indexFromLeft = first;
		int indexFromRight = last - 1;

		boolean done = false;
		while (!done)
		{
			// starting at beginning of array, leave elements that are < pivot;
			// locate first element that is >= pivot
			while (a[indexFromLeft].compareTo(pivot) < 0)
				indexFromLeft++;

			// starting at end of array, leave elements that are > pivot;
			// locate first element that is <= pivot

			while (a[indexFromRight].compareTo(pivot) > 0 && indexFromRight > first)
				indexFromRight--;

			// Assertion: a[indexFromLeft] >= pivot and
			//            a[indexFromRight] <= pivot.

			if (indexFromLeft < indexFromRight)
			{
				swap(a, indexFromLeft, indexFromRight);
				indexFromLeft++;
				indexFromRight--;
			}
			else
				done = true;
		} // end while

		// place pivot between Smaller and Larger subarrays
		swap(a, pivotIndex, indexFromLeft);
		pivotIndex = indexFromLeft;

		// Assertion:
		// Smaller = a[first..pivotIndex-1]
		// Pivot = a[pivotIndex]
		// Larger  = a[pivotIndex + 1..last]

		return pivotIndex;
	}  // end partition

	private static void swap(Object [] a, int i, int j)
	{
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	} // end swap
}
