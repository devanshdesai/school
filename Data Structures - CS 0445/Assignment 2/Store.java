// PROPERTY OF DEVANSH M. DESAI
// UNIVERSITY OF PITTSBURGH - CS 0445 2/7/15

// This file is a class used to store all of the methods and data of the ice cream shop
// simulator. All commands that the simulator allows are methods in this class. When a command
// is read in Assig2.java, a Store method is called to complete the task.


public class Store 
{
	private ArrayStack<Crate> permanent = new ArrayStack<Crate>(100);
	private ArrayStack<Crate> temporary = new ArrayStack<Crate>(100);
	private ArrayStack<Crate> counter = new ArrayStack<Crate>(100);
	private int day = 0;
	private int movesRecent = 0;
	private int movesTotal = 0;
	private int cratesRecent = 0;
	private int cratesTotal = 0;
	private double laborRecent = 0;
	private double laborTotal = 0;
	private double costRecent = 0;
	private double costTotal = 0;
	
	
	// Store constructor
	public Store()
	{
	}
	
	// This method resets the recent numbers before every receive so the report command works properly
	public void resetRecentNumbers()
	{
		movesRecent=0;
		cratesRecent=0;
		laborRecent=0;
		costRecent=0;
	}
	
	// This is the algorithm to receive banana crates
	public void receive(int exp, int initial, double cost)
	{
		Crate temp = new Crate (exp, initial, cost);
		
		System.out.println(cost);
		cratesRecent++;
		cratesTotal++;
		
		if (!temporary.isEmpty())
		{
			while (temp.compareTo(temporary.peek()) < 0)
			{
				permanent.push(temporary.pop());
				movesRecent++;
				movesTotal++;
				laborRecent++;
				laborTotal++;
				if (temporary.isEmpty())
				{
					break;
				}
			}
		}
		
		if (!permanent.isEmpty())
		{
			while (temp.compareTo(permanent.peek()) > 0)
			{
				temporary.push(permanent.pop());
				movesRecent++;
				movesTotal++;
				laborRecent++;
				laborTotal++;
				if (permanent.isEmpty())
				{
					break;
				}
			}
		}
		
		permanent.push(temp);
		movesRecent++;
		movesTotal++;
		laborRecent++;
		laborTotal++;
	
		costRecent += temp.getCost();
		costTotal += temp.getCost();
		
	}
	
	// This is the method to use the given amount of bananas. It gets more crates if needed.
	public void useBananas(int k)
	{
		int needed = k;
		int used = 0;
		
		System.out.println(needed + " bananas needed for order.");
		
		if ((counter.peek()) == null)
		{
			System.out.println("Go and buy some bananas! There's always money in the banana stand.");
			return;
		}
		
		if (k < (counter.peek()).getCurrentCount())
		{
			System.out.println(needed + " bananas used from current crate.");
			while (needed > 0)
			{
				(counter.peek()).removeBanana();
				needed--;
			}
		}	
		else if (k > (counter.peek()).getCurrentCount())
		{
			while (needed > 0)
			{
				if ((counter.peek()).getCurrentCount() > 0)
				{
					(counter.peek()).removeBanana();
					needed--;
					used++;
					if (needed == 0)
					{
						System.out.println(used + " bananas used from current crate.");
					}
				}
				else
				{
					System.out.println(used + " bananas used from current crate.");
					System.out.print("Getting crate: ");
					(permanent.peek()).display();
					counter.clear();
					counter.push(permanent.pop());
					used = 0;
				}
			}
		}
		
		else
		{
			System.out.println("Getting " + needed + " bananas from current crate.");
			while ((counter.peek()).getCurrentCount() > 0)
			{
				(counter.peek()).removeBanana();
			}
			
			counter.clear();
			counter.push(permanent.pop());
		}
	}
	
	// This method displays all of the crates in the store, including the crate on the counter.
	public void display()
	{
		if (counter.peek() == null)
		{
			System.out.println("There are no crates in the stack.");
			return;
		}
		System.out.println("Counter crate: ");
		(counter.peek()).display();
		
		System.out.println("\nStack Crates (top to bottom):\n");
		while (!permanent.isEmpty())
		{
			Crate temp = permanent.pop();
			temp.display();
			temporary.push(temp);
		}
		while (!temporary.isEmpty())
		{
			permanent.push(temporary.pop());
		}
	}
	
	// This method moves onto the next day. It increments the global clock and decreases the expiration date of the bananas.
	// When the banana's exp date hits 0, the banana crate is then thrown out. A message is displayed on how many were thrown out.
	public void skip()
	{
		day++;
		System.out.println("It is now day " + day + ".");
		
		while (!permanent.isEmpty())
		{
			Crate temp = permanent.pop();
			temp.nextDay();
			temporary.push(temp);
		}
		while (!temporary.isEmpty())
		{
			permanent.push(temporary.pop());
		}
		
		int thrownAway = 0;
		while (!permanent.isEmpty())
		{
			Crate temp = permanent.pop();
			if (temp.getExp() == 0 || temp.getExp() < 0)
			{
				thrownAway++;
				temp = null;
			}
			else
			{
				temporary.push(temp);	
			}
		}
		while (!temporary.isEmpty())
		{
			permanent.push(temporary.pop());
		}
		
		if (counter.peek() == null)
		{
			return;
		}
		
		if ((counter.peek()).getExp() == 0 || (counter.peek()).getExp() < 0)
		{
			thrownAway++;
			counter.clear();
			counter.push(permanent.pop());
		}
		
		System.out.println("A total of " + thrownAway + " crate(s) were thrown away.");
	}
	
	// This method reports the financial statement concerning costs of crates and labor. Also, total crates and moves for the employee are shown.
	public void report()
	{
		System.out.println("\nFinancial Statement:\n     Most Recent Shipment:");
		System.out.println("          Crates: " + cratesRecent + "\n          Banana cost: " + costRecent);
		System.out.println("          Labor(moves): " + movesRecent + "\n          Labor cost: " + laborRecent);
		System.out.println("          ____________________\n          Total cost: " + (laborRecent + costRecent));
		
		System.out.println("\n     Overall Expenses:");
		System.out.println("          Crates: " + cratesTotal + "\n          Banana cost: " + costTotal);
		System.out.println("          Labor(moves): " + movesTotal + "\n          Labor cost: " + laborTotal);
		System.out.println("          ____________________\n          Total cost: " + (laborTotal + costTotal));	
	}
	
	// This method puts everything from the temporary stack into the permanent stack.
	public void resetStacks()
	{
		while (!temporary.isEmpty())
		{
			permanent.push(temporary.pop());
			movesRecent++;
			movesTotal++;
			laborTotal++;
			laborRecent++;
		}

	}
	
	// This method places a crate onto the counter so it can be used when the use command is used.
	public void putOnCounter()
	{
		counter.push(permanent.pop());
	}
}
