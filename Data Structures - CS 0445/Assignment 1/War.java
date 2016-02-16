// PROPERTY OF DEVANSH M. DESAI
// UNIVERSITY OF PITTSBURGH - CS 0445 1/23/15

// This program uses the data structure created in the MultiDS class to create a game called War.
// War is a card game where card in both players' hands are compared.
// The deck, hands, discard piles, and the cards in play are all stored in MultiDS objects.


public class War 
{
	public static void main(String[] args)
	{
	
	int rounds = Integer.parseInt(args[0]);
		
	System.out.println("Welcome to the game of WAR!\n" );
	
	
	//Creating a deck data structure
	MultiDS<Card> deck = new MultiDS<Card>(52);
		
	//Adding 52 cards to the deck
	for (Card.Suits s: Card.Suits.values())
	{
		for (Card.Ranks r: Card.Ranks.values())
		{
			deck.addItem(new Card(s, r));
		}
	}
	
	
	
	
	
	
	System.out.println("\n\nShuffling...");
	
	//Shuffling the deck a few times...
	deck.shuffle();
	deck.shuffle();
	deck.shuffle();
	
	System.out.println("Now dealing the cards to both players...\n\n");
	
	//Creating data structures for the hands and discard piles of both players and an one for card in play
	MultiDS<Card> playerOne = new MultiDS<Card>(52);
	MultiDS<Card> playerTwo = new MultiDS<Card>(52);
	MultiDS<Card> playerOneDiscard = new MultiDS<Card>(52);
	MultiDS<Card> playerTwoDiscard = new MultiDS<Card>(52);
	MultiDS<Card> inPlay = new MultiDS<Card>(52);

	//Dealing cards out to both players
	for (int i=0; i < 52; i++)
	{
		if (i%2 == 0)
		{
			playerOne.addItem(deck.removeItem());
		}
		else
		{
			playerTwo.addItem(deck.removeItem());
		}
	}
	
	//Printing the hands of both players
	System.out.println("Here is Player 1's hand:\n" + playerOne.toString() + "\n");
	System.out.println("Here is Player 2's hand:\n" + playerTwo.toString() + "\n\nSTARTING THE WAR!\n");
	
	

	//Creating the game based on how many rounds the user has inputted
	for (int i = 0; i < rounds; i++)
	{
		//Continuing the game if both players' hands aren't empty
		if (!playerOne.empty() && !playerTwo.empty())
		{
			Card p1 = playerOne.removeItem();
			Card p2 = playerTwo.removeItem();

			//Comparing the two cards
			if ((p1.compareTo(p2)) > 0)
			{
				System.out.println("Player 1 Wins: " + p1.toString() + " beats " + p2.toString());
				playerOneDiscard.addItem(p1);
				playerOneDiscard.addItem(p2);	
			}
			else if ((p1.compareTo(p2)) < 0)
			{
				System.out.println("Player 2 Wins: " + p1.toString() + " loses to " + p2.toString());
				playerTwoDiscard.addItem(p1);
				playerTwoDiscard.addItem(p2);
			}
			//If both cards are the same, a WAR ensues
			else if (p1.compareTo(p2) == 0)
			{
				System.out.println("WAR: " + p1.toString() + " ties " + p2.toString());
				
				//Adding already compared cards to the inPlay data structure so it is easy to give all the cards away when someone wins the war
				inPlay.addItem(p1);
				inPlay.addItem(p2);
																											//Checking to see if both players can even play cards in the WAR
																											if (playerOne.empty())
																											{
																												if (playerOneDiscard.empty())
																												{
																													System.out.println("Player 1 is out of cards!\nPlayer 2 is the WINNER!");
																													System.exit(0);
																												}
																												else
																												{
																													System.out.println("Getting and shuffling the cards for Player 1...");
																													for (int j = 0; j < playerOneDiscard.size() + 1; j++)
																													{
																														playerOne.addItem(playerOneDiscard.removeItem());
																														playerOne.shuffle();
																													}
																												}
																											}
																											
																											if (playerTwo.empty())
																											{
																												if (playerTwoDiscard.empty())
																												{
																													System.out.println("Player 2 is out of cards!\nPlayer 1 is the WINNER!");
																													System.exit(0);
																												}
																												else
																												{
																													System.out.println("Getting and shuffling the cards for Player 2...");
																													for (int j = 0; j < playerTwoDiscard.size() + 1; j++)
																													{
																														playerTwo.addItem(playerTwoDiscard.removeItem());
																														playerTwo.shuffle();
																													}
																												}
																											}
				//Making both players draw a card so they can be compared															
				inPlay.addItem(playerOne.removeItem());
				inPlay.addItem(playerTwo.removeItem());
																											//Again, checking if both players have enough cards to continue in the WAR
																											if (playerOne.empty())
																											{
																												if (playerOneDiscard.empty())
																												{
																													System.out.println("Player 1 is out of cards!\nPlayer 2 is the WINNER!");
																													System.exit(0);
																												}
																												else
																												{
																													System.out.println("Getting and shuffling the cards for Player 1...");
																													for (int j = 0; j < playerOneDiscard.size() + 1; j++)
																													{
																														playerOne.addItem(playerOneDiscard.removeItem());
																														playerOne.shuffle();
																													}
																												}
																											}
																											
																											if (playerTwo.empty())
																											{
																												if (playerTwoDiscard.empty())
																												{
																													System.out.println("Player 2 is out of cards!\nPlayer 1 is the WINNER!");
																													System.exit(0);
																												}
																												else
																												{
																													System.out.println("Getting and shuffling the cards for Player 2...");
																													for (int j = 0; j < playerTwoDiscard.size() + 1; j++)
																													{
																														playerTwo.addItem(playerTwoDiscard.removeItem());
																														playerTwo.shuffle();
																													}
																												}
																											}
				Card p1Compared = playerOne.removeItem();
				Card p2Compared = playerTwo.removeItem();
			
									//Comparison in 1st War
									if ((p1Compared.compareTo(p2Compared)) > 0)
									{
										System.out.println("Player 1 Wins the WAR: " + p1Compared.toString() + " beats " + p2Compared.toString());
										playerOneDiscard.addItem(p1Compared);
										playerOneDiscard.addItem(p2Compared);
										for (i = 0; i < 4; i++)
										{
											playerOneDiscard.addItem(inPlay.removeItem());
										}
									}
									else if ((p1Compared.compareTo(p2Compared)) < 0)
									{
										System.out.println("Player 2 Wins the WAR: " + p1.toString() + " loses to " + p2.toString());
										playerTwoDiscard.addItem(p1Compared);
										playerTwoDiscard.addItem(p2Compared);
										for (i = 0; i < 4; i++)
										{
											playerTwoDiscard.addItem(inPlay.removeItem());
										}
									}
									//If both cards are equal a second time, a second WAR ensues
									else if ((p1Compared.compareTo(p2Compared)) == 0)
									{
										System.out.println("WAR: " + p1Compared.toString() + " ties " + p2Compared.toString());
										inPlay.addItem(p1Compared);
										inPlay.addItem(p2Compared);
																												//Checking if both players have enough cards to continue playing in the WAR
																												if (playerOne.empty())
																												{
																													if (playerOneDiscard.empty())
																													{
																														System.out.println("Player 1 is out of cards!\nPlayer 2 is the WINNER!");
																														System.exit(0);
																													}
																													else
																													{
																														System.out.println("Getting and shuffling the cards for Player 1...");
																														for (int j = 0; j < playerOneDiscard.size() + 1; j++)
																														{
																															playerOne.addItem(playerOneDiscard.removeItem());
																															playerOne.shuffle();
																														}
																													}
																												}
																												
																												if (playerTwo.empty())
																												{
																													if (playerTwoDiscard.empty())
																													{
																														System.out.println("Player 2 is out of cards!\nPlayer 1 is the WINNER!");
																														System.exit(0);
																													}
																													else
																													{
																														System.out.println("Getting and shuffling the cards for Player 2...");
																														for (int j = 0; j < playerTwoDiscard.size() + 1; j++)
																														{
																															playerTwo.addItem(playerTwoDiscard.removeItem());
																															playerTwo.shuffle();
																														}
																													}
																												}
										inPlay.addItem(playerOne.removeItem());
										inPlay.addItem(playerTwo.removeItem());
																													//Checking if both players have enough cards to continue playing in the WAR
																													if (playerOne.empty())
																													{
																														if (playerOneDiscard.empty())
																														{
																															System.out.println("Player 1 is out of cards!\nPlayer 2 is the WINNER!");
																															System.exit(0);
																														}
																														else
																														{
																															System.out.println("Getting and shuffling the cards for Player 1...");
																															for (int j = 0; j < playerOneDiscard.size() + 1; j++)
																															{
																																playerOne.addItem(playerOneDiscard.removeItem());
																																playerOne.shuffle();
																															}
																														}
																													}
																													
																													if (playerTwo.empty())
																													{
																														if (playerTwoDiscard.empty())
																														{
																															System.out.println("Player 2 is out of cards!\nPlayer 1 is the WINNER!");
																															System.exit(0);
																														}
																														else
																														{
																															System.out.println("Getting and shuffling the cards for Player 2...");
																															for (int j = 0; j < playerTwoDiscard.size() + 1; j++)
																															{
																																playerTwo.addItem(playerTwoDiscard.removeItem());
																																playerTwo.shuffle();
																															}
																														}
																													}
																													
										Card p1Compared2 = playerOne.removeItem();
										Card p2Compared2 = playerTwo.removeItem();
										
																	//Comparing both players' cards to find a winner of the WAR
																	if (p1Compared2.compareTo(p1Compared2) > 0)
																	{
																		System.out.println("Player 1 Wins the WAR: " + p1Compared2.toString() + " beats " + p2Compared2.toString());
																		playerOneDiscard.addItem(p1Compared2);
																		playerOneDiscard.addItem(p2Compared2);
																		for (i = 0; i < 8; i++)
																		{
																			playerOneDiscard.addItem(inPlay.removeItem());
																		}
																	}
																	else if (p1Compared2.compareTo(p2Compared2) < 0)
																	{
																		System.out.println("Player 2 Wins the WAR: " + p1Compared2.toString() + " loses to " + p2Compared2.toString());
																		playerTwoDiscard.addItem(p1Compared2);
																		playerTwoDiscard.addItem(p2Compared2);
																		for (i = 0; i < 8; i++)
																		{
																			playerTwoDiscard.addItem(inPlay.removeItem());
																		}
																	}
																	
									}
									
			}
			
			
		}
		
	if (playerOne.empty())
		{
			if (playerOneDiscard.empty())
			{
				System.out.println("Player 1 is out of cards!\nPlayer 2 is the WINNER!");
				System.exit(0);
			}
			else
			{
				System.out.println("Getting and shuffling the cards for Player 1...");
				for (int j = 0; j < playerOneDiscard.size() + 1; j++)
				{
					playerOne.addItem(playerOneDiscard.removeItem());
					playerOne.shuffle();
				}
			}
		}
		else if (playerTwo.empty())
		{
			if (playerTwoDiscard.empty())
			{
				System.out.println("Player 2 is out of cards!\nPlayer 1 is the WINNER!");
				System.exit(0);
			}
			else
			{
				System.out.println("Getting and shuffling the cards for Player 2...");
				for (int j = 0; j < playerTwoDiscard.size() + 1; j++)
				{
					playerTwo.addItem(playerTwoDiscard.removeItem());
					playerTwo.shuffle();
				}
			}
		}
	}
	
	
	//After all rounds have finished, this statement determines which player has the most amount of cards in both the hand and discard pile.
	if (!playerOne.empty() && !playerTwo.empty())
	{
		int playerOneCards = playerOne.size() + playerOneDiscard.size();
		int playerTwoCards = playerTwo.size() + playerTwoDiscard.size();
		
		if(playerOneCards > playerTwoCards)
		{
			System.out.println("After " + rounds + " rounds, Player 2 has less cards than Player 1, Player 1 is the WINNER!");
		}
		else if(playerOneCards < playerTwoCards)
		{
			System.out.println("After " + rounds + " rounds, Player 1 has less cards than Player 2, Player 2 is the WINNER!");
		}
		else if(playerOneCards == playerTwoCards)
		{
			System.out.println("The game ends in a tie.");
		}
	}
	
	
	}
	
}
