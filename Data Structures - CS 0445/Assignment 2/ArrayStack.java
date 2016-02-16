// PROPERTY OF DEVANSH M. DESAI
// UNIVERSITY OF PITTSBURGH - CS 0445 2/7/15

// This file is a class that utilizes the StackInterface to implement an ArrayStack data structure.
// All of the methods are defined in this file.

import java.util.Arrays;


public class ArrayStack<T> implements StackInterface<T>
{
	
	private T[] stack;
	private int topIndex;
	private static final int DEFAULT_INITIAL_CAPACITY = 50;
	
	// This is the default constructor for creating an ArrayStack with a capacity of 50.
	public ArrayStack()
	{
		this(DEFAULT_INITIAL_CAPACITY);
	}
	
	
	// The user can specify his/her desired capacity with this constructor.
	public ArrayStack(int initialCapacity)
	{
		@SuppressWarnings("unchecked")
		T[] tempStack = (T[]) new Object[initialCapacity];
		stack = tempStack;
		topIndex = -1;
	}
	
	
	// The push method allows the user to add a compatible object to the ArrayStack, given
	// that there is enough room.
	public void push(T newEntry)
	{
		ensureCapacity();
		topIndex++;
		stack[topIndex] = newEntry;
	}
	
	
	// This method verifies if the ArrayStack is full. If it is, it copies the stack to another
	// newly created stack with a doubled size.
	private void ensureCapacity()
	{
		if (topIndex == stack.length - 1)
		{
			stack = Arrays.copyOf(stack, 2 * stack.length);
		}
	}
	
	
	// This pop method removes an object off of the stack and returns it, given that the stack
	// is not empty.
	public T pop()
	{
		T top = null;
		
		if(!isEmpty())
		{
			top = stack[topIndex];
			stack[topIndex] = null;
			topIndex--;
		}
	
		return top;
	}
	
	
	// The peek method allows the user to obtain the object on the top of the stack without removing it.
	public T peek()
	{
		T top = null;
		
		if (!isEmpty())
		{
			top = stack[topIndex];
		}
		
		return top;
	}
	
	
	// This method checks if the stack is empty. It returns true if it is empty and false if it is not.
	public boolean isEmpty()
	{
		if (topIndex < 0)
		{
			return true;
		}
		else
		{
		return false;
		}
	}
	
	
	// This method clears the entire stack. All indexes in the ArrayStack are set to null.
	public void clear()
	{
		if (topIndex >= 0)
		{
			for (int i = 0; i < (topIndex + 1); i++)
			{
				stack[i] = null;
			}
		}
	}
}
