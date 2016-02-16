// PROPERTY OF DEVANSH M. DESAI
// UNIVERSITY OF PITTSBURGH - CS 0445 2/27/15

// This class allows the user to input a phrase and the programs uses
// a recursive backtracking algorithm to find it in a letter grid.

import java.io.*;
import java.util.*;

public class Grid
{
	// Some special variables to take note of:
	// length - keeps track of where the word is in the phrase
	// 			since the phrase is one long string
	// count - 	keeps track of which word the program is on
	public String[][] grid;
	public int rows;
	public int columns;
	public String output = "";
	public int count = 0;
	public String[] phrase;
	public Scanner reader;
	public String[][] coor = new String[2][50];

	public Grid(File input) throws Exception
	{
		reader = new Scanner(input);

		String first = reader.nextLine();
		String[] size = first.split(" ");
		int r = Integer.parseInt(size[0]);
		int c = Integer.parseInt(size[1]);

		rows = r;
		columns = c;

		grid = new String[rows][columns];
	}

	// This method stores the letters from the text file into the grid.
	public void storeInGrid() throws Exception
	{
		for (int i = 0; i < rows; i++)
		{
			String line = reader.nextLine();
			for (int j = 0; j < columns; j++)
			{
				String letter = line.substring(j, j+1);
				grid[i][j] = letter;
			}
		}
	}

	// This is a method to print the letter grid on the screen.
	public void printGrid()
	{
		for (int i = 0; i < rows; i++)
		{
				for (int j = 0; j < columns; j++)
				{
					System.out.print(grid[i][j] + " ");
				}
			System.out.println();
		}
	}

	// This is the first findWord method that is used to find the first word of the phrase.
	public boolean findWord(String word, int loc, int r, int c)
	{
		boolean answer;

		phrase = word.split(" ");

		// Gives the starting coordinates of the phrase.
		coor[0][0] = "(" + r + "," + c + ")";

		answer = findWord(word, loc, r, c, 0, "right");

		// This tries all possible directions if right does not work.
		if (!answer)
		{
			answer = findWord(word, loc, r, c, 0, "down");
		}

		if (!answer)
		{
			answer = findWord(word, loc, r, c, 0, "left");
		}

		if (!answer)
		{
			answer = findWord(word, loc, r, c, 0, "up");
		}

		if (!answer)
		{
			for (int i = 0; i == 1; i++)
			{
				for (int j = 0; j < coor[0].length; j++)
				{
					coor[i][j] = null;
				}
			}

			for (int i = 0; i < phrase.length; i++)
			{
				phrase[i] = null;
			}
		}

		if (answer)
		{
		}

		count = 0;
		return answer;
	}

	// This is the main findWord method that is used to recurse and backtrack in order to find all of the words in the phrase.
	public boolean findWord(String word, int loc, int r, int c, int length, String direction)
	{
		boolean answer;

		if (r >= grid.length || r < 0 || c >= grid[0].length || c < 0)
		{
			return false;
		}
		// This is the case if a letter in the grid does not match the phrase the user inputed.
		else if (!grid[r][c].equals(word.substring(loc, loc+1)))
		{
			return false;
		}
		else
		{
			grid[r][c] = grid[r][c].toUpperCase();


			// This condition is met when the current word has been matched in the phrase.
			if ((loc - length) == (phrase[count].length() - 1))
			{

				// This stores the ending coordinates of the word that was just found.
				coor[1][count] = "(" + r + "," + c + ")";

				count++;

				// This condition is met when there are still more words available to find in the phrase.
				if (phrase.length > count)
				{
					coor[0][count] = "(" + r + "," + c + ")";


					answer =  findWord(word, loc+2, r, c+1, length + phrase[count-1].length() + 1, "right");
					if (!answer)
					{
						answer = findWord(word, loc+2, r+1, c, length + phrase[count-1].length() + 1, "down");
					}
					if (!answer)
					{
						answer = findWord(word, loc+2, r, c-1, length + phrase[count-1].length() + 1, "left");
					}
					if (!answer)
					{
						answer = findWord(word, loc+2, r-1, c, length + phrase[count-1].length() + 1, "up");
					}

					if (!answer)
					{
						grid[r][c] = grid[r][c].toLowerCase();
						count--;

					}


					return answer;
				}
				else
				{
					for (int i = 0; i < count; i++)
					{
							System.out.println(phrase[i] + ": " + coor[0][i] + " to " + coor[1][i]);
					}
					answer = true;
				}

				return answer;
			}
			else
			{
				// These conditions recurse based on which direction is specified in the method.
				if (direction.equals("right"))
				{
					answer = findWord(word, loc+1, r, c+1, length, "right");
					if (!answer)
					{
						grid[r][c] = grid[r][c].toLowerCase();
					}
					return answer;
				}

				if (direction.equals("down"))
				{
					answer = findWord(word, loc+1, r+1, c, length, "down");
					if (!answer)
					{
						grid[r][c] = grid[r][c].toLowerCase();
					}
					return answer;
				}

				if (direction.equals("left"))
				{
					answer = findWord(word, loc+1, r, c-1, length, "left");
					if (!answer)
					{
						grid[r][c] = grid[r][c].toLowerCase();
					}
					return answer;
				}

				if (direction.equals("up"))
				{
					answer = findWord(word, loc+1, r-1, c, length, "up");
					if (!answer)
					{
						grid[r][c] = grid[r][c].toLowerCase();
					}
					return answer;
				}

				answer = false;

			}
		}


		return answer;
	}
}
