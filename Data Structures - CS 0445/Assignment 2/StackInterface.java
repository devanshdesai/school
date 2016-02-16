// PROPERTY OF DEVANSH M. DESAI
// UNIVERSITY OF PITTSBURGH - CS 0445 2/7/15

// This file is an interface that lists all of the methods available to the stack data structure.

public interface StackInterface<T> 
{
	public void push(T newEntry);
	
	public T pop();
	
	public T peek();
	
	public boolean isEmpty();
	
	public void clear();
}
