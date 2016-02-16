// PROPERTY OF DEVANSH M. DESAI
// UNIVERSITY OF PITTSBURGH - CS 0445 2/27/15

// This class creates a Grid object and asks for a .txt file for input.
// Then, it processes the .txt file and asks for a phrase to be entered.
// If the phraes is available, it is made all uppercase in the word grid.

import java.io.*;
import java.util.*;

public class Assig3
{

	public static void main(String[] args) throws Exception
	{
		boolean nextInput = true;

		System.out.println("Please enter grid file name: ");
		Scanner keyboard = new Scanner(System.in);
		String path = keyboard.nextLine();
		System.out.println(path);

		File name = new File(path);
		Grid wordGrid = new Grid(name);

		// Program exit condition
		if (path.equals(""))
		{
			System.exit(0);
		}

		wordGrid.storeInGrid();
		wordGrid.printGrid();

		// This loop keeps reading input while the user enters a filename.
		while (nextInput)
		{
			System.out.println("Please enter a phrase (separated by a single space)");
			String phrase = keyboard.nextLine();
			if (phrase.equals(""))
			{
				nextInput = false;
				System.out.println("The program has terminated.");
				System.exit(10);
			}
			phrase = phrase.toLowerCase();
			System.out.println("Looking for: " + phrase);
			String[] allWords = phrase.split(" ");
			System.out.println("containing " + allWords.length + " word(s)");
			boolean found = false;

			// This loop searches for the phrase starting at each location on the grid.
			for (int i = 0; i < wordGrid.rows && !found; i++)
			{
					for (int j = 0; j < wordGrid.columns && !found; j++)
					{
						if (wordGrid.findWord(phrase, 0, i, j))
						{
							System.out.println("The phrase: " + phrase + "\nwas found");
							found = true;
						}
					}
			}

			if (!found)
			{
					System.out.println("The phrase: " + phrase + "\nwas not found");
			}
			wordGrid.printGrid();













			// This resets the grid so the user can enter more phrases and find them if they're available.
			wordGrid.count = 0;
			for (int i = 0; i < wordGrid.rows; i++)
			{
				for (int j = 0; j < wordGrid.columns; j++)
				{
					wordGrid.grid[i][j] = wordGrid.grid[i][j].toLowerCase();
				}
			}
		}

		keyboard.close();
	}
}
