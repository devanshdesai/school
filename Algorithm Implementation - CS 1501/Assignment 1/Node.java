// DEVANSH DESAI
// CS 1501 FALL 2015
// ASSIGNMENT 1
// Node.java

public class Node
{
    public LL myList;
    public Node child = null;
    public Node sibling = null;
    private char data;
    private boolean endOfWord = false;

    // Constructor for the node class
    public Node (char data)
    {
        this.data = data;
    }

    // Returns the char data of the node
    public char getData()
    {
        return this.data;
    }

    // Sets argument as the data value for the node
    public void changeData(char data)
    {
        this.data = data;
    }

    // This method makes the given node the end of a word
    // or phrase.
    public void makeEnd()
    {
        this.endOfWord = true;
    }

    // Checks if the given node's data is the last letter of
    // a word. Returns true if it is, false otherwise.
    public boolean checkEnd()
    {
        if (this.endOfWord)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
