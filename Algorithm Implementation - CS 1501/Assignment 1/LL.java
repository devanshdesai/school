// DEVANSH DESAI
// CS 1501 FALL 2015
// ASSIGNMENT 1
// LL.java

public class LL
{
    // firstNode points to the first node in the LL.
    // size stores the size of the LL.
    public Node firstNode;
    public int size;

    // This constructor creates a LL object
    // without a first node.
    public LL()
    {
        this.firstNode = null;
        this.size = 0;
    }

    // This constructor creates a LL with a first node
    // that has the data value of the argument.
    public LL(char data)
    {
        this.size++;
        Node newNode = new Node(data);
        this.firstNode = newNode;
        newNode.myList = this;
    }

    // This method traverses the LL to see if any of
    // the nodes contain the character in the argument
    public boolean contains(char data)
    {
        boolean check = false;
        Node temp = this.firstNode;
        for (int i = 0; i < this.size; i++)
        {
            if (temp.getData() == data)
            {
                check = true;
                break;
            }
            else
            {
                temp = temp.sibling;
            }
        }
        return check;
    }

    // This method adds to the front of the LL so
    // no time is wasted going all the way to the end
    // of the LL to add.
    public void add(char data)
    {
        Node newNode = new Node(data);
        Node temp = this.firstNode;
        if (this.size == 0)
        {
            this.firstNode = newNode;
        }
        for (int i = 0; i < this.size; i++)
        {
            if (i == (this.size - 1))
            {
                temp.sibling = newNode;
            }
            temp = temp.sibling;
        }
        newNode.myList = this;
        this.size++;
    }

    // This method returns the node which contains
    // the data in the argument. I make sure in DLB.java
    // to always call this method after calling contains()
    // to make sure I don't have to call it again here.
    public Node findNode(char data)
    {
        Node temp = this.firstNode;
        Node returnNode = null;
        while (temp != null)
        {
            if (temp.getData() == data)
            {
                returnNode = temp;
                return returnNode;
            }
            else
            {
                temp = temp.sibling;
            }
        }

        // This code will never be run but Java
        // needs it. I will make sure to always call
        // contains() first so the return will always
        // occur in the if statement in the while loop
        // above.
        return returnNode;
    }
}
