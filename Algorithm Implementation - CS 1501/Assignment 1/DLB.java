// DEVANSH DESAI
// CS 1501 FALL 2015
// ASSIGNMENT 1
// DLB.java

public class DLB
{
    public LL firstChar = new LL();
    public static int size = 0;

    public DLB()
    {
    }

    public void add(String s)
    {
        char[] charArray = (s.toLowerCase()).toCharArray();
        LL myList;
        if (!(this.firstChar).contains(charArray[0]))
        {
            (this.firstChar).add(charArray[0]);
            Node temp = (this.firstChar).findNode(charArray[0]);
            for (int i = 1; i < charArray.length; i++)
            {
                myList = new LL(charArray[i]);
                temp.child = myList.firstNode;
                if (i == (charArray.length - 1))
                {
                    temp = temp.child;
                    temp.makeEnd();
                    this.size++;
                }
                else
                {
                    temp = temp.child;
                }
            }
        }
        else
        {
            Node temp = (this.firstChar).findNode(charArray[0]);
            for (int i = 1; i < charArray.length; i++)
            {
                if (temp.child == null)
                {
                    myList = new LL();
                    myList.add(charArray[i]);
                    temp.child = myList.findNode(charArray[i]);
                    temp = temp.child;
                }
                else
                {
                    temp = temp.child;
                    myList = temp.myList;
                    if (!(temp.myList).contains(charArray[i]))
                    {
                        myList.add(charArray[i]);
                    }
                    temp = myList.findNode(charArray[i]);
                }
                if (i == (charArray.length - 1))
                {
                    temp.makeEnd();
                }
            }
        }
    }


    public boolean contains(String s)
    {
        char[] ch = (s.toLowerCase()).toCharArray();
        Node temp = (this.firstChar).firstNode;
        for (int i = 0; i < ch.length; i++)
        {
            while (temp.getData() != ch[i])
            {
                if (temp.sibling == null)
                {
                    return false;                  // CANNOT CONTINUE IN ARRAY OF CHARS
                }
                temp = temp.sibling;
            }
            if (temp.child == null && i == ch.length-1)
            {
                return true;
            }
            else if (temp.child != null && i < ch.length)
            {
                temp = ((temp.child).myList).firstNode;
            }
            else
            {
                return false;
            }
        }
        return true;
    }


    /*public boolean contains(String s, Node n, int level)
    {
        boolean found = false;
        if (n.child != null)
        {
            if (this.contains(s, n.child, level++))
            {
                found = true;
            }
        }
        if (n.sibling != null)
        {
            if (this.contains(s, n.sibling, level))
            {
                found = true;
            }
        }
        if ((level + s.length()) > 5)
        {
            return false;
        }
        char[] charArray = (s.toLowerCase()).toCharArray();
        LL myList = n.myList;
        Node temp;
        for (int i = 0; i < charArray.length; i++)
        {
            if (myList.contains(charArray[i]))
            {
                temp = myList.findNode(charArray[i]);
            }
            else
            {
                return found;
            }

            if (i == (charArray.length - 1))
            {
                found = true;
                return found;
            }
            else
            {
                if (temp.child == null)
                {
                    myList = temp.myList;
                    if (!myList.contains(charArray[i]))
                    {
                        return found;
                    }
                    else
                    {
                        temp = myList.findNode(charArray[i]);
                    }
                }
                else
                {
                    temp = temp.child;
                    myList = temp.myList;
                }
            }
        }
        return found;
    }*/
}
