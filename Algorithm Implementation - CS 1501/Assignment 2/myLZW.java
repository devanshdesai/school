// DEVANSH DESAI
// CS 1501 - FALL 2015
// ASSIGNMENT 02 - myLZW.java


/*************************************************************************
 *  Compilation:  javac myLZW.java
 *  Execution:    java myLZW - < input.txt   (compress)
 *  Execution:    java myLZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using myLZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 *************************************************************************/

public class myLZW {
    private static final int R = 256;       // number of input chars
    private static int L = 512;            // number of codewords = 2^W
    private static int W = 9;              // codeword width
    private static String mode = "m";            // can be either n, r, or m
    private static boolean startMonitoring = false;
    private static double oldRatio = 0.0;
    private static double newRatio = 0.0;
    private static double sizeOriginal = 0.0;
    private static double sizeOutput = 0.0;
    private static int resetCounter = 0;

    public static void compress()
    {
        if (mode.equals("r"))
        {
            BinaryStdOut.write('r', 8);
        }
        if (mode.equals("m"))
        {
            BinaryStdOut.write('m', 8);
        }
        if (mode.equals("n"))
        {
            BinaryStdOut.write('n', 8);
        }
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
        {
            st.put("" + (char) i, i);
        }
        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0)
        {
            L = (int) Math.pow(2, W);
            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            sizeOriginal += s.length() * 8;
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            sizeOutput += W;
            newRatio = sizeOriginal/sizeOutput;     // Sets the newRatio every time a new word is read in

            int t = s.length();
            if (t < input.length() && code < L)    // Add s to symbol table.
            {
                st.put(input.substring(0, t + 1), code++);
            }

            if ((W < 16) && ((int) Math.pow(2, W) == code))
            {
                W++;
                L = (int) Math.pow(2.0, W);
                st.put(input.substring(0, t + 1), code++);
            }

            if (code == 65536 && mode.equals("r"))      // Resets the codebook if the max number of codewords is reached
            {
                resetCounter++;
                st = new TST<Integer>();
                for (int i = 0; i < R; i++)
                {
                    st.put("" + (char) i, i);
                }
                code = R+1;  // R is codeword for EOF
                W = 9;
                L = 512;
            }

            if (code == 65536 && mode.equals("m"))  // Monitors the compression ratio if the codebook is full and resets if the ratio of ratios falls below 1.1
            {
                if (!startMonitoring)
                {
                    oldRatio = newRatio;
                    startMonitoring = true;
                }
                if ((oldRatio/newRatio) > 1.1)
                {
                    System.err.println("Ratio of ratios: " + (oldRatio/newRatio));
                    resetCounter++;
                    st = new TST<Integer>();
                    for (int i = 0; i < R; i++)
                    {
                        st.put("" + (char) i, i);
                    }
                    code = R+1;  // R is codeword for EOF
                    W = 9;
                    L = 512;
                    oldRatio = 0.0;
                    newRatio = 0.0;
                    startMonitoring = false;
                }
            }
            input = input.substring(t);            // Scan past s in input.
        }
        System.err.println("Codeword length: " + W);
        System.err.println("Resets: " + resetCounter);
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }


    public static void expand()
    {
        char modeChar = BinaryStdIn.readChar(8);
        if (modeChar == 'r')
        {
            mode = "r";
        }
        if (modeChar == 'm')
        {
            mode = "m";
        }
        if (modeChar == 'n')
        {
            mode = "n";
        }
        String[] st = new String[65536];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
        {
            st[i] = "" + (char) i;
        }
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R)
        {
            return;           // expanded message is empty string
        }
        String val = st[codeword];

        while (true)
        {
            BinaryStdOut.write(val);
            sizeOriginal += val.length() * 8;
            codeword = BinaryStdIn.readInt(W);
            sizeOutput += W;
            newRatio = sizeOriginal/sizeOutput;

            if (codeword == R)
            {
                break;
            }

            String s = st[codeword];

            if (i == codeword)
            {
                s = val + val.charAt(0);   // special case hack
            }
            if (i < (L-1))
            {
                st[i++] = val + s.charAt(0);
            }
            if ((i == (L-1))  && (W < 16))
            {
                st[i++] = val + s.charAt(0);
                W++;
                L = (int) Math.pow(2.0, (double) W);
            }

            val = s;

            if (i == 65535 && mode.equals("r"))
            {
               W=9;
               L=512 ;
               st = new String[65536];
               for (i = 0; i < R; i++)
               {
                   st[i] = "" + (char) i;
               }
               st[i++] = "";
               codeword = BinaryStdIn.readInt(W);
               if (codeword == R)
               {
                   return;                          // expanded message is empty string
               }
               val = st[codeword];
            }

            if (i == 65535 && mode.equals("m"))
            {
                if (!startMonitoring)
                {
                    oldRatio = newRatio;
                    startMonitoring = true;
                }
                if ((oldRatio/newRatio) > 1.1)
                {
                    System.err.println("Ratio of ratios: " + (oldRatio/newRatio));
                    resetCounter++;
                    W=9;
                    L=512 ;
                    st = new String[65536];
                    for (i = 0; i < R; i++)
                    {
                        st[i] = "" + (char) i;
                    }
                    st[i++] = "";
                    codeword = BinaryStdIn.readInt(W);
                    if (codeword == R)
                    {
                        return;                          // expanded message is empty string
                    }
                    val = st[codeword];
                    oldRatio = 0.0;
                    newRatio = 0.0;
                    startMonitoring = false;
                }
            }
        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
        if (args[0].equals("-") && args[1].equals("n"))
        {
            mode = "n";
            compress();
        }
        else if (args[0].equals("-") && args[1].equals("r"))
        {
            mode = "r";
            compress();
        }
        else if (args[0].equals("-") && args[1].equals("m"))
        {
            mode = "m";
            compress();
        }
        else if (args[0].equals("+"))
        {
            expand();
        }
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
