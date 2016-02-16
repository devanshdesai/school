/*  DEVANSH DESAI
    CS 1501 - FALL 2015
    ASSIGNMENT 5 - 12/09/2015
    MySign.java
*/

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.security.MessageDigest;
import java.math.BigInteger;


public class MySign
{
    public static void main(String[] args)
    {
        if (args[0].equals("s"))
        {
            try
            {
                // Read in D and N from the private RSA key file
                FileInputStream privFile = new FileInputStream("privkey.rsa");
                ObjectInputStream privObj = new ObjectInputStream(privFile);
                BigInteger d = (BigInteger) privObj.readObject();
                BigInteger n = (BigInteger) privObj.readObject();

                // Read in the message from the file given in the command line
                Path path = Paths.get(args[1]);
                Scanner reader = new Scanner(new FileInputStream(args[1]));
                String message = reader.nextLine();
                while (reader.hasNext())
                {
                    message = message + reader.nextLine();
                }

                // Hash the message from the file
                MessageDigest md = MessageDigest.getInstance("SHA-256");
    			byte[] data = message.getBytes();
                md.update(data);
                byte[] digest = md.digest();
                BigInteger msgHash = new BigInteger(1, digest);

                // Generate the signature using the RSA keys from the hash
                BigInteger signature = msgHash.modPow(d, n);

                // Output the original message along with the signature to a signed version of the file
                FileOutputStream signedFile = new FileOutputStream(args[1] + ".signed");
                ObjectOutputStream signedObj = new ObjectOutputStream(signedFile);
                signedObj.writeObject(message);
                signedObj.writeObject(signature);

                System.out.println("\nYour file '" + args[1] + "' was sucessfully signed.");
            }
            catch (Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
        else if (args[0].equals("v"))
        {
            try
            {
                // Read in the original message and the signature from the file given in the command line
                FileInputStream signedFile = new FileInputStream(args[1]);
                ObjectInputStream signedObj = new ObjectInputStream(signedFile);
                String message = (String) signedObj.readObject();
                BigInteger signature = (BigInteger) signedObj.readObject();

                // Read in E and N from the public RSA key file
                FileInputStream pubFile = new FileInputStream("pubkey.rsa");
                ObjectInputStream pubObj = new ObjectInputStream(pubFile);
                BigInteger e = (BigInteger) pubObj.readObject();
                BigInteger n = (BigInteger) pubObj.readObject();

                // Hash the message from the file
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] data = message.getBytes();
                md.update(data);
                byte[] digest = md.digest();
                BigInteger msgHash = new BigInteger(1, digest);

                // Compute the RSA signature hash using the public RSA keys
                BigInteger rsaHash = signature.modPow(e, n);

                // The RSA signature hash must be equal to the message hash for the signature to be valid
                if (rsaHash.equals(msgHash))
                {
                    System.out.println("\nThe signature is valid.");
                }
                else
                {
                    System.out.println("\nThe signature is invalid.");
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
        else
        {
            System.out.println("\nERROR: Please enter a valid command line input.\n\nTo sign a file, type 'java MySign s example.txt'\nTo verify a file, type 'java MySign v example.txt.signed'");
        }
    }
}
