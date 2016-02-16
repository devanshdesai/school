// PROPERTY OF DEVANSH M. DESAI
// UNIVERSITY OF PITTSBURGH - CS 0445 2/7/15

// This file is a class used to store Crate data. The data is abstracted.
// Some mutator and accessor methods are given to access data about Crate objects.

public class Crate implements Comparable<Crate>
{
	private int exp;
	private int initial;
	private int current;
	private double cost;
	
	// This is the constructor for the Crate class.
	public Crate(int expDate, int initialCount, double costOfBananas)
	{
		exp = expDate;
		initial = initialCount;
		cost = costOfBananas;
		current = initial;
	}
	
	// Returns expiration date (days left)
	public int getExp()
	{
		return exp;
	}
	
	// Gets current amount of bananas in crate.
	public int getCurrentCount()
	{
		return current;
	}
	
	// Gets total amount of bananas that were in the crate.
	public int getInitialCount()
	{
		return initial;
	}
	
	// Gets cost of crate
	public double getCost()
	{
		return cost;
	}
	
	// Removes a banana from the crate (mainly used for use command in Store)
	public void removeBanana()
	{
		if (current > 0)
		{
			current--;
		}
	}
	
	// Compares a crate's expiration date to another's date
	public int compareTo(Crate other)
	{
		int value = 0;
		if (this.getExp() > other.getExp())
		{
			value = 1;
			return value;
		}
		else if (this.getExp() < other.getExp())
		{
			value = -1;
			return value;
		}
		else 
		{
			return value;
		}
	}
	
	// Decreases the expiration date counter by 1. If it hits 0, the banana crate has expired.
	public void nextDay()
	{
		exp--;
	}
	
	// Displays all of the information about a banana crate such as exp date, initial count, current count, and cost.
	public void display()
	{
		System.out.println("Expires:" + exp + "  Start Count:" + initial + "  Remain:" + current + "  Cost:" + cost + "\n");
	}
	
}
