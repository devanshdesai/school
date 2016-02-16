/*  DEVANSH DESAI
    CS 1501 - FALL 2015
    ASSIGNMENT 5 - 12/09/2015
    MyKeyGen.java
*/

import java.io.*;
import java.util.Random;
import java.math.BigInteger;

public class MyKeyGen
{
    public static void main(String[] args)
    {
        Random rand = new Random();
        BigInteger one = new BigInteger("1");

        // Generate random 1024 bit prime to get P and Q, find N by multiplying them and then find phiN
        BigInteger p = new BigInteger(1024, 1000000, rand);
        BigInteger q = new BigInteger(1024, 1000000, rand);
        BigInteger n = p.multiply(q);
        BigInteger phiN = (p.subtract(one)).multiply(q.subtract(one));

        // Choosing an E value
        BigInteger e;
        while (true)
        {
            e = new BigInteger(1024, 1000000, rand);

            // Checks if E is greater than 1 and less than phi(N)
            if (e.compareTo(one) == 1 && e.compareTo(phiN) == -1)
            {
                // Makes sure GCD of E and phi(N) is 1
                if (e.gcd(phiN).compareTo(one) == 0)
                {
                    break;
                }
            }
        }

        try
        {
            // Computes D from E and phiN
            BigInteger d = e.modInverse(phiN);

            // E and then N are written to the public key file 'pubkey.rsa'
            FileOutputStream pubFile = new FileOutputStream("pubkey.rsa");
            ObjectOutputStream pubObj = new ObjectOutputStream(pubFile);
            pubObj.writeObject(e);
            pubObj.writeObject(n);
            pubObj.close();

            // D and then N are written to the private key file 'privkey.rsa'
            FileOutputStream privFile = new FileOutputStream("privkey.rsa");
            ObjectOutputStream privObj = new ObjectOutputStream(privFile);
            privObj.writeObject(d);
            privObj.writeObject(n);
            privObj.close();

            System.out.println("\nE:\n" + e);
            System.out.println("\n\nD:\n" + d);
            System.out.println("\n\nN:\n" + n);
        }
		catch(Exception ex)
        {
			System.out.println(ex.toString());
		}
    }
}
