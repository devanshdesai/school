// DEVANSH DESAI
// CS 1501 FALL 2015
// ASSIGNMENT 1
// LLTester.java

public class LLTester
{
    public static void main(String[] args)
    {
        char a = 'a';
        char b = 'b';
        LL test = new LL(a);
        test.add(b);
        test.add(a);
        test.add(a);
        test.add(b);

        Node temp = test.firstNode;
        for (int i = 0; i < test.size; i++)
        {
            System.out.println(temp.getData());
            temp = temp.sibling;
        }
    }
}
