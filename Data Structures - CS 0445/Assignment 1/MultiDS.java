// PROPERTY OF DEVANSH M. DESAI*
// UNIVERSITY OF PITTSBURGH - CS 0445 1/23/15

// This program creates a generic data structure that can be used to store objects such as a deck of cards. 
// Many methods are included to help with reordering, creating, and editing the data structure.

import java.util.Random;

public class MultiDS<T> implements PrimQ<T>, Reorder
{
	//Instance variables and instance array created
	private int count;
	private T[] ds;
	
	
	//Constructor for MultiDS class
	@SuppressWarnings("unchecked")
	public MultiDS(int size)
	{
		ds = (T[]) new Object[size];
	}
	
	//Method for converting MultiDS object to a string
	public String toString()
	{
		StringBuilder output = new StringBuilder();
		output.append("Contents: \n");
		for (int i = 0; i < count; i++)
		{
			output.append(ds[i] + "  ");
		}
		
		return output.toString();
	}

	//Method for adding an item to a MultiDS object
	@Override
	public boolean addItem(T item)
	{
		boolean isFull = true;
	
		if (this.full())
		{
		System.out.println("MultiDS filled to capacity.");
		isFull = false;
		return isFull;
		}
		else
		{
		ds[count] = item;
		count++;
		return isFull;
		}
	
	}

	//Method for removing an item from a MultiDS object
	@Override
	public T removeItem()
	{
		T toBeRemoved = null;
		if (!this.empty())
		{
			toBeRemoved = ds[0];
			this.shiftLeft();
			count--;
		}
	
		return toBeRemoved;
	}

	//Method for checking whether a MultiDS object is full
	@Override
	public boolean full()
	{
		boolean isFull = true;
		if (count == ds.length)
		{
			return isFull;
		}
		else
		{
			isFull = false;
			return isFull;
		}
	}

	//Method for checking whether a MultiDS object is empty
	@Override
	public boolean empty()
	{
		boolean isEmpty = true;
		if (count == 0)
		{
			return isEmpty;
		}
		else
		{
			isEmpty = false;
			return isEmpty;
		}
	}
	
	//Method for getting the amount of items in a MultiDS object
	@Override
	public int size()
	{
		return count;
	}
	
	//Method for removing all items from a MultiDS object
	@Override
	public void clear()
	{
		for (int i=0; i<count; i++)
		{
			this.removeItem();
		}
	}
	
	//Method for reversing all items in a MultiDS object
	@Override
	public void reverse()
	{
		for (int i=0; i<(count/2); i++)
		{
			T first = ds[i];
			ds[i] = ds[(count - 1) - i];
			ds[(count - 1) - i] = first;
		}
	}
	
	//Method for shifting items in a MultiDS object to the right
	@Override
	public void shiftRight()
	{
		T last = ds[count - 1];
		for (int i = count - 1; i > 0; i--)
		{
			ds[i]  = ds[i - 1];
		}
		ds[0] = last;
	}
	
	//Method for shifting items in a MultiDS object to the left
	@Override
	public void shiftLeft()
	{
		T first = ds[0];
		for (int i=1; i < count; i++)
		{
			ds[i-1] = ds[i];
		}
		ds[count-1] = first;
	}
	
	//Method for shuffling items in a MultiDS object
	@Override
	public void shuffle()
	{
		Random num = new Random();
	        for (int i = count-1; i > 0; i--) 
	        {
	        	int randomNumber = num.nextInt(count-1);
	            T temp = ds[i];
	            ds[i] = ds[randomNumber];
	            ds[randomNumber] = temp;
	        }
	}
	
}
	
	
	 