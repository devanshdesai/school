// PROPERTY OF DEVANSH M. DESAI
// UNIVERSITY OF PITTSBURGH - CS 0445 2/7/15

// This file is a class used to simulate an ice cream shop. A text file's path
// is entered in the command line and the text file is read and operations are
// completed based on what this class reads.


import java.io.BufferedReader;
import java.io.FileReader;


public class Assig2 
{
	
	// The user enters the file path of the .txt file to read as an argument in the commmand line.
	public static void main(String[] args) throws Exception
	{
		String input = args[0];
		Store licketySplits = new Store();
		
		System.out.println("Welcome to the ice cream shop simulator.\n\n");
		
		FileReader example = new FileReader(input);
		BufferedReader br = new BufferedReader(example);
		
		String thisLine = null;
		
		// This while loop traverses the entire .txt file and reads commands and acts upon those commands as it enters the proper if statement.
		while ((thisLine = br.readLine()) != null)
		{	
			if (thisLine.equals("receive"))
			{
				licketySplits.resetRecentNumbers();
				
				thisLine = br.readLine();
				int crates = Integer.parseInt(thisLine);
				
				System.out.println("Receiving " + crates + " crates of bananas.");
				
				
				for (int i = 0; i < crates; i++)
				{
					String crate = br.readLine();
					String[] info =  crate.split(" ");
					int exp = Integer.parseInt(info[0]);
					int count = Integer.parseInt(info[1]);
					double cost = Double.parseDouble(info[2]);
					
					licketySplits.receive(exp,count,cost);
				}
				
				licketySplits.resetStacks();
				
				licketySplits.putOnCounter();
			}
			else if (thisLine.equals("use"))
			{
				String howMany = br.readLine();
				int count = Integer.parseInt(howMany);
				licketySplits.useBananas(count);
			}
			else if (thisLine.equals("display"))
			{
				licketySplits.display();
			}
			else if (thisLine.equals("skip"))
			{
				licketySplits.skip();
			}
			else if (thisLine.equals("report"))
			{
				licketySplits.report();
			}
			
		}
		
		System.out.println("\n \n End of simulation.");
		
		br.close();
	}
	
}