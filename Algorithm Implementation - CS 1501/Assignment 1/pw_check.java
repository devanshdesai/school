// DEVANSH DESAI
// CS 1501 FALL 2015
// ASSIGNMENT 1
// pw_check.java

import java.io.*;
import java.util.*;

public class pw_check
{
        public static char[] alphabet = "bcdefghjklmnopqrstuvwxyz".toCharArray();
        public static char[] numbers = "123567890".toCharArray();
        public static char[] symbols = "!@$%&*".toCharArray();
        public static String[] expWords = new String[500000];
        public static int expWordsCounter = 0;
        public static DLB dict = new DLB();
        public static String[] passwords = new String[99999999];
        public static int passwordsCounter = 0;

    /*
    public static boolean checkValidity(StringBuilder sb)
    {
        int letters = 0, numbers = 0, symbols = 0;
        for (int i = 0; i < 5; i++)
        {
            char val = sb.charAt(i);
            if (Character.isDigit(val))
                numbers++;
            else if (Character.isLetter(val))
                letters++;
            else
                symbols++;
        }
        if ((letters >= 1) && (letters <= 3) && (numbers >= 1) &&
        (numbers <= 2) && (symbols >= 1) && (symbols <= 2))
            return true;
        else
            return false;
    }
    */
    public static void dfs(String curPass, int depth, int availLets, int availNums, int availSyms) throws FileNotFoundException, UnsupportedEncodingException
    {
        if (depth > 5) // HAVE A GOOD PASSWORD
        {
            passwords[passwordsCounter] = curPass;
            passwordsCounter++;
            System.out.println(curPass);
            return;
        }
        for (int i = 1; i < depth; i++)
        {
            String s = curPass.substring(i,depth);
            if (dict.contains(s))
            {
                return;
            }
        }
        if (availLets > 0)
        {
            for (int i = 0; i < alphabet.length; i++)
            {
                dfs(curPass + alphabet[i], depth + 1, availLets - 1, availNums, availSyms);
            }
        }
        if (availNums > 0)
        {
            for (int i = 0; i < numbers.length; i++)
            {
                dfs(curPass + numbers[i], depth + 1, availLets, availNums - 1, availSyms);
            }
        }
        if (availSyms > 0)
        {
            for (int i = 0; i < symbols.length; i++)
            {
                dfs(curPass + symbols[i], depth + 1, availLets, availNums, availSyms - 1);
            }
        }
    }

    /*public static boolean canBeSubstituted(char c)
    {
        if (c == '4' || c == '0' || c == '3' || c == '1' || c == '5')
        {
            System.out.println("true");
            return true;
        }
        else
        {
            return false;
        }
    }
    */
    /*public static String substitute(String s, int index)
    {
        char c = s.charAt(index);
        String temp = "";
        if (c == '4')
        {
            return (s.substring(0, index) + "a" + s.substring(index));
        }
        else if (c == '0')
        {
            return (s.substring(0, index) + "o" + s.substring(index));
        }
        else if (c == '3')
        {
            return (s.substring(0, index) + "e" + s.substring(index));
        }
        else if (c == '1')
        {
            return (s.substring(0, index) + "l" + s.substring(index));
        }
        else if (c == '5')
        {
            return (s.substring(0, index) + "s" + s.substring(index));
        }
        return temp;
    }
    */
    public static String substitute(String s, int index)
    {
        char c = s.charAt(index);
        String temp;
        if (c == 'a')
        {
            return (s.substring(0, index) + "4" + s.substring(index+1));
        }
        else if (c == 'o')
        {
            return (s.substring(0, index) + "0" + s.substring(index+1));
        }
        else if (c == 'e')
        {
            return (s.substring(0, index) + "3" + s.substring(index+1));
        }
        else if (c == 'l')
        {
            return (s.substring(0, index) + "1" + s.substring(index+1));
        }
        else if (c == 's')
        {
            return (s.substring(0, index) + "5" + s.substring(index+1));
        }
        else if (c == 't')
        {
            return (s.substring(0, index) + "7" + s.substring(index+1));
        }
        return s;
    }

    public static String subAll(String s)
    {
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) == 'a')
            {
                return (s.substring(0, i) + "4" + s.substring(i+1));
            }
            if (s.charAt(i) == 'o')
            {
                return (s.substring(0, i) + "0" + s.substring(i+1));
            }
            if (s.charAt(i) == 'e')
            {
                return (s.substring(0, i) + "3" + s.substring(i+1));
            }
            if (s.charAt(i) == 'l')
            {
                return (s.substring(0, i) + "1" + s.substring(i+1));
            }
            if (s.charAt(i) == 's')
            {
                return (s.substring(0, i) + "5" + s.substring(i+1));
            }
            if (s.charAt(i) == 't')
            {
                return (s.substring(0, i) + "7" + s.substring(i+1));
            }
        }
        return s;
    }
    /*public static void expand(String pass, int depth)
    {
        System.out.println(depth);
        if (depth >= pass.length())
        {
            System.out.println("HERE");
            expWords[expWordsCounter] = pass;
            expWordsCounter++;
            return;
        }
        for (int i = depth; i < pass.length(); i++)
        {
            if (canBeSubstituted(pass.charAt(i)))
            {
                expand(pass, depth + 1);
                String pass2 = substitute(pass, i);
                expand(pass2, depth + 1);
            }
            else
            {
                expand(pass, depth + 1);
            }
        }
    }
    */
    public static void findSubs(String s)
    {
        String temp = "";
        for (int a = 0; a < 3; a++)
        {
            if (s.contains("a"))
            {
                if (a == 0)
                {
                    temp = substitute(s, s.indexOf('a'));
                    expWords[expWordsCounter] = temp;
                    expWordsCounter++;
                    expWords[expWordsCounter] = subAll(temp);
                    expWordsCounter++;
                }
                else if (a == 1)
                {
                    temp = substitute(s, s.lastIndexOf('a'));
                    expWords[expWordsCounter] = temp;
                    expWordsCounter++;
                    expWords[expWordsCounter] = subAll(temp);
                    expWordsCounter++;
                }
                else
                {
                    temp = substitute(s, s.indexOf('a'));
                    temp = substitute(s, s.lastIndexOf('a'));
                    expWords[expWordsCounter] = temp;
                    expWordsCounter++;
                    expWords[expWordsCounter] = subAll(temp);
                    expWordsCounter++;
                }
            }
            for (int b = 0; b < 3; b++)
            {
                if (s.contains("o"))
                {
                    if (b == 0)
                    {
                        temp = substitute(s, s.indexOf('o'));
                        expWords[expWordsCounter] = temp;
                        expWordsCounter++;
                        expWords[expWordsCounter] = subAll(temp);
                        expWordsCounter++;
                    }
                    else if (b == 1)
                    {
                        temp = substitute(s, s.lastIndexOf('o'));
                        expWords[expWordsCounter] = temp;
                        expWordsCounter++;
                        expWords[expWordsCounter] = subAll(temp);
                        expWordsCounter++;
                    }
                    else
                    {
                        temp = substitute(s, s.indexOf('o'));
                        temp = substitute(s, s.lastIndexOf('o'));
                        expWords[expWordsCounter] = temp;
                        expWordsCounter++;
                    }
                }
                for (int c = 0; c < 3; c++)
                {
                    if (s.contains("e"))
                    {
                        if (c == 0)
                        {
                            temp = substitute(s, s.indexOf('e'));
                            expWords[expWordsCounter] = temp;
                            expWordsCounter++;
                            expWords[expWordsCounter] = subAll(temp);
                            expWordsCounter++;
                        }
                        else if (c == 1)
                        {
                            temp = substitute(s, s.lastIndexOf('e'));
                            expWords[expWordsCounter] = temp;
                            expWordsCounter++;
                            expWords[expWordsCounter] = subAll(temp);
                            expWordsCounter++;
                        }
                        else
                        {
                            temp = substitute(s, s.indexOf('e'));
                            temp = substitute(s, s.lastIndexOf('e'));
                            expWords[expWordsCounter] = temp;
                            expWordsCounter++;
                            expWords[expWordsCounter] = subAll(temp);
                            expWordsCounter++;
                        }
                    }
                    for (int d = 0; d < 3; d++)
                    {
                        if (s.contains("l"))
                        {
                            if (d == 0)
                            {
                                temp = substitute(s, s.indexOf('l'));
                                expWords[expWordsCounter] = temp;
                                expWordsCounter++;
                                expWords[expWordsCounter] = subAll(temp);
                                expWordsCounter++;
                            }
                            else if (d == 1)
                            {
                                temp = substitute(s, s.lastIndexOf('l'));
                                expWords[expWordsCounter] = temp;
                                expWordsCounter++;
                                expWords[expWordsCounter] = subAll(temp);
                                expWordsCounter++;
                            }
                            else
                            {
                                temp = substitute(s, s.indexOf('l'));
                                temp = substitute(s, s.lastIndexOf('l'));
                                expWords[expWordsCounter] = temp;
                                expWordsCounter++;
                                expWords[expWordsCounter] = subAll(temp);
                                expWordsCounter++;
                            }
                        }
                        for (int e = 0; e < 3; e++)
                        {
                            if (s.contains("s"))
                            {
                                if (e == 0)
                                {
                                    temp = substitute(s, s.indexOf('s'));
                                    expWords[expWordsCounter] = temp;
                                    expWordsCounter++;
                                    expWords[expWordsCounter] = subAll(temp);
                                    expWordsCounter++;
                                }
                                else if (e == 1)
                                {
                                    temp = substitute(s, s.lastIndexOf('s'));
                                    expWords[expWordsCounter] = temp;
                                    expWordsCounter++;
                                    expWords[expWordsCounter] = subAll(temp);
                                    expWordsCounter++;
                                }
                                else
                                {
                                    temp = substitute(s, s.indexOf('s'));
                                    temp = substitute(s, s.lastIndexOf('s'));
                                    expWords[expWordsCounter] = temp;
                                    expWordsCounter++;
                                    expWords[expWordsCounter] = subAll(temp);
                                    expWordsCounter++;
                                }
                            }
                            for (int f = 0; f < 3; f++)
                            {
                                if (s.contains("t"))
                                {
                                    if (f == 0)
                                    {
                                        temp = substitute(s, s.indexOf('t'));
                                        expWords[expWordsCounter] = temp;
                                        expWordsCounter++;
                                        expWords[expWordsCounter] = subAll(temp);
                                        expWordsCounter++;
                                    }
                                    else if (e == 1)
                                    {
                                        temp = substitute(s, s.lastIndexOf('t'));
                                        expWords[expWordsCounter] = temp;
                                        expWordsCounter++;
                                        expWords[expWordsCounter] = subAll(temp);
                                        expWordsCounter++;
                                    }
                                    else
                                    {
                                        temp = substitute(s, s.indexOf('t'));
                                        temp = substitute(s, s.lastIndexOf('t'));
                                        expWords[expWordsCounter] = temp;
                                        expWordsCounter++;
                                        expWords[expWordsCounter] = subAll(temp);
                                        expWordsCounter++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
    {
        File dictionary = new File("dictionary.txt");
    	Scanner reader = new Scanner(dictionary);
        String[] badWords = new String[503];
        String temp;
        PrintWriter writer = new PrintWriter("my_dictionary.txt", "UTF-8");
        PrintWriter writer2 = new PrintWriter("good_passwords.txt", "UTF-8");

        if (args.length == 0)
        {
            System.out.println("Sorry, this functionality does not exit. It was not able to be completed in a timely manner. :(");
            System.exit(0);
        }


        for (int i = 0; i < 503; i++)
        {
            if (reader.hasNextLine())
            {
                temp = reader.nextLine();
                if (!temp.equals(""))
                {
                    if (temp.length() <= 5)
                    {
                        if (temp.indexOf("a") == -1)
                        {
                            if (temp.indexOf("i") == -1)
                            {
                                dict.add(temp);
                                writer.println(temp);
                                findSubs(temp);
                            }
                        }
                    }
                }
            }
            else
            {
                break;
            }
        }

        for (int i = 0; i < 500000; i++)
        {
            if (expWords[i] != null && !expWords[i].equals(""))
            {
                if (!dict.contains(expWords[i]))
                {
                    dict.add(expWords[i]);
                    writer.println(expWords[i]);
                }
            }
        }

        writer.close();
        dfs("", 0, 3, 2, 2);

        for (int i = 0; i < passwords.length; i++)
        {
            if (passwords[i] != null)
            {
                writer2.println(passwords[i]);
            }
            else
                break;
        }

        writer2.close();
        /*
        char[] validChars = "bcdefghjklmnopqrstuvwxyz02356789!@$%&*".toCharArray();
        StringBuilder password = new StringBuilder(5);
        StringBuilder tempPass = new StringBuilder(5);
        PrintWriter pw = new PrintWriter("good_passwords.txt", "UTF-8");
        char[] subs = "7t4a0o3e1il5s".toCharArray();
        char[] oneToTwo = "1234567890!@$%&*".toCharArray();
        char[] threeToFour = "1234567890bcdefghjklmnopqrstuvwxyz".toCharArray();
        char[] five = "bcdefghjklmnopqrstuvwxyz".toCharArray();
        */






        /*
        int num = 0;
        int nums = 0, lets = 0, syms = 0;

        for (int a = 0; a < 16; a++)
        {
            nums = 0;
            lets = 0;
            syms = 0;
            if (Character.isDigit(oneToTwo[a]))
                nums++;
            else
                syms++;
            for (int b = 0; b < 16; b++)
            {
                nums = 0;
                lets = 0;
                syms = 0;
                if (Character.isDigit(oneToTwo[a]))
                    nums++;
                else
                    syms++;
                if (Character.isDigit(oneToTwo[b]))
                    nums++;
                else
                    syms++;
                if (syms != 0)
                {
                    for (int c = 0; c < 34; c++)
                    {
                        nums = 0;
                        lets = 0;
                        syms = 0;
                        if (Character.isDigit(oneToTwo[a]))
                            nums++;
                        else
                            syms++;
                        if (Character.isDigit(oneToTwo[b]))
                            nums++;
                        else
                            syms++;
                        if (Character.isDigit(threeToFour[c]))
                            nums++;
                        else
                            lets++;
                        if (nums == 2)
                        {
                            for (int d = 10; d < 34; d++)
                            {
                                for (int e = 0; e < 24; e++)
                                {
                                    password.append(oneToTwo[a]);
                                    password.append(oneToTwo[b]);
                                    password.append(threeToFour[c]);
                                    password.append(threeToFour[d]);
                                    password.append(five[e]);

                                    System.out.println(password);
                                    password.setLength(0);
                                    password.setLength(5);
                                    tempPass.setLength(0);
                                    tempPass.setLength(5);
                                }
                            }
                        }
                        else
                        {
                            for (int d = 0; d < 34; d++)
                            {
                                nums = 0;
                                lets = 0;
                                syms = 0;
                                if (Character.isDigit(oneToTwo[a]))
                                    nums++;
                                else
                                    syms++;
                                if (Character.isDigit(oneToTwo[b]))
                                    nums++;
                                else
                                    syms++;
                                if (Character.isDigit(threeToFour[c]))
                                    nums++;
                                else
                                    lets++;
                                if (Character.isDigit(threeToFour[d]))
                                    nums++;
                                else
                                    lets++;
                                if (nums != 0)
                                {
                                    for (int e = 0; e < 24; e++)
                                    {
                                        password.append(oneToTwo[a]);
                                        password.append(oneToTwo[b]);
                                        password.append(threeToFour[c]);
                                        password.append(threeToFour[d]);
                                        password.append(five[e]);

                                        System.out.println(password);
                                        password.setLength(0);
                                        password.setLength(5);
                                        tempPass.setLength(0);
                                        tempPass.setLength(5);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }



        for (int a = 0; a < 38; a++)
        {
            for (int b = 0; b < 38; b++)
            {
                for (int c = 0; c < 38; c++)
                {
                    for (int d = 0; d < 38; d++)
                    {
                        for (int e = 0; e < 38; e++)
                        {
                            password.append(validChars[a]);
                            password.append(validChars[b]);
                            password.append(validChars[c]);
                            password.append(validChars[d]);
                            password.append(validChars[e]);
                            if (checkValidity(password))
                            {
                                for (int passCopier = 0; passCopier < 5; passCopier++)
                                {
                                    tempPass.append(password.charAt(passCopier));
                                }
                                for (int sbCounter = 0; sbCounter < 5; sbCounter++)
                                {
                                    if (tempPass.charAt(sbCounter) == subs[0])      // == 7
                                         tempPass.setCharAt(sbCounter, subs[1]);    // == t
                                    if (tempPass.charAt(sbCounter) == subs[4])      // == 0
                                         tempPass.setCharAt(sbCounter, subs[5]);    // == o
                                    if (tempPass.charAt(sbCounter) == subs[6])      // == 3
                                         tempPass.setCharAt(sbCounter, subs[7]);    // == e
                                    if (tempPass.charAt(sbCounter) == subs[11])     // == 5
                                         tempPass.setCharAt(sbCounter, subs[12]);   // == s
                                }

                                if (!dict.contains(tempPass.toString()))
                                {
                                    pw.println(password.toString());
                                }
                            }

                            password.setLength(0);
                            password.setLength(5);
                            tempPass.setLength(0);
                            tempPass.setLength(5);

                        }
                    }
                }
            }
        }

        pw.close();

        if (dict.contains("back"))
        {
            System.out.println("Yes - back");
        }
        else
        {
            System.out.println("No - back");
        }

        if (dict.contains("here"))
        {
            System.out.println("Yes - here");
        }
        else
        {
            System.out.println("No - here");
        }

        if (dict.contains("old"))
        {
            System.out.println("Yes - old");
        }
        else
        {
            System.out.println("No - old");
        }

        if (dict.contains("ppp"))
        {
            System.out.println("Yes - ppp");
        }
        else
        {
            System.out.println("No - ppp");
        }
        */
    }
}
