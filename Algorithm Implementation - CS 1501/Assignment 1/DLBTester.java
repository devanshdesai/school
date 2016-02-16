// DEVANSH DESAI
// CS 1501 FALL 2015
// ASSIGNMENT 1
// DLBTester.java

public class DLBTester
{
    public static void main(String[] args)
    {
        DLB test = new DLB();
        test.add("hello");
        test.add("header");
        test.add("computer");
        test.add("comparison");

        if (test.contains("hello"))
        {
            System.out.println("The DLB Trie does contain 'hello'");
        }
        else
        {
            System.out.println("The DLB Trie does not contain 'hello'");
        }

        if (test.contains("computer"))
        {
            System.out.println("The DLB Trie does contain 'computer'");
        }
        else
        {
            System.out.println("The DLB Trie does not contain 'computer'");
        }

        if (test.contains("comp"))
        {
            System.out.println("The DLB Trie does contain 'comp'");
        }
        else
        {
            System.out.println("The DLB Trie does not contain 'comp'");
        }

        if (test.contains("header"))
        {
            System.out.println("The DLB Trie does contain 'header'");
        }
        else
        {
            System.out.println("The DLB Trie does not contain 'header'");
        }

        if (test.contains("apple"))
        {
            System.out.println("The DLB Trie does contain 'apple'");
        }
        else
        {
            System.out.println("The DLB Trie does not contain 'apple'");
        }

        if (test.contains("puter"))
        {
            System.out.println("The DLB Trie does contain 'puter'");
        }
        else
        {
            System.out.println("The DLB Trie does not contain 'puter'");
        }
    }
}
