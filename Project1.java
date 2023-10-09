/*
 * Shaun Lee
 * Signed Integers Project #1
 * Due by: October 13th
 * Description: This program asks the user to pick the length of a binary string
 * and for a signed integer representation. After which it will display the binary string 
 * and the chosen representation.
 */

import java.util.Scanner;

public class Project1
{
    public static void main(String[]args)
    {   
        Scanner sc = new Scanner(System.in);
        String name = "";

        //Asks for user input for length
        System.out.print("Enter the length of the binary string (1 - 8): ");
        int length = sc.nextInt();

        if (length < 1 || length > 8)
        {
            System.out.println("Please choose an appropriate binary string length between 1 and 8");
            return;
        }

        //Asks for uder input for which representation
        System.out.println("Choose from the following signed integer representations: ");
        System.out.println("1: Signed Magnitude");
        System.out.println("2: One's Complement");
        System.out.println("3: Two's Complement");
        System.out.println("4: Excess Notation");

        int choice = sc.nextInt();

        //edge cases
        if (choice < 1 || choice > 4)
        {
            System.out.println("Please choose an appropriate choice between 1 and 4");
            return;
        }
        if (choice == 1) { name = "Signed Magnitude"; }
        if (choice == 2) { name = "One's Complement"; }
        if (choice == 3) { name = "Two's Complement"; }
        if (choice == 4) { name = "Excess Notation"; }

          

        //Prints the table
        System.out.println("\nBinary String \t\t" + name);
        
        for (int i = 0; i < Math.pow(2, length); i++)
        {   
            String bString = toBinaryString(i, length);

            if (choice == 4)
            {   
                String sign = "";

                if (bString.charAt(0) == '1')
                {
                    sign = "+";
                }
                if (bString.charAt(0) == '0')
                {
                    sign = "-";
                }
                 int dValue = convertToDecimal(bString, choice, length);

                System.out.println(bString + "\t\t\t" + sign + dValue);
                System.out.println("--------------------------------------");
            }
            else 
            {
                String sign = "";

                if (bString.charAt(0) == '1')
                {
                    sign = "-";
                }
                if (bString.charAt(0) == '0')
                {
                    sign = "+";
                }
        
                //add zero's to match length 
                while (bString.length() < length)
                {
                    bString = "0" + bString;
                }


                int dValue = convertToDecimal(bString, choice, length);

                System.out.println(bString + "\t\t\t" + sign + dValue);
                System.out.println("--------------------------------------");
            }
        }
        sc.close();
    }

    //converts binary string to decimal given the choice
    public static int convertToDecimal(String bString, int choice, int length)
    {
        int dValue = 0;
        int sign = 1;

        switch (choice)
        {
            case 1: //Signed magnitude  
                char signBit = bString.charAt(0);

                //if negative
                if (signBit == '1') 
                { 
                    sign = 1; 
                   
                    for (int i = 1; i < length; i++) //start at index 1 to ignore significant bit, only find the bits after most left bit
                    {   
                        int bit = bString.charAt(i) == '1' ? 1 : 0; //finds value of the binary string
                        dValue += bit * Math.pow(2, length - 1 - i); //finds in which spot the binary is in, eg 2^1, 2^2, 2^3
                    }   
                break;
                } 
                
                //if positive
                for (int i = 0; i < length; i++)
                {   
                    int bit = bString.charAt(i) == '1' ? 1 : 0; //finds value of the binary string
                    dValue += bit * Math.pow(2, length - 1 - i); //finds in which spot the binary is in, eg 2^1, 2^2, 2^3
                } break;

            case 2: //One's Complement
                if (bString.charAt(0) == '1')
                {
                    //If negative, invert
                    String invertBinary = invertBinaryString(bString);
                    dValue = -1 * convertToDecimal(invertBinary, 2, length);
                }
                else 
                {
                    for (int i = 0; i < length; i++)
                    {
                        int bit = bString.charAt(i) == '1' ? 1 : 0; //finds value of binary string       
                        dValue += bit * Math.pow(2, length - 1 - i); //finds in which spot the binary is in, eg 2^1, 2^2, 2^3      
                    }
                } break;

            case 3: //Two's Complement
                if (bString.charAt(0) == '1')  //if negative, invert and subtract 1
                {
                    String invertedB = invertBinaryString(bString);
                    dValue = -1 * (convertToDecimal(invertedB, 3, length) + 1);
                }
                else
                {
                    for (int i = 0; i < length; i++)
                    {
                        int bit = bString.charAt(i) == '1' ? 1: 0; //finds value of binary string 
                        dValue += bit * Math.pow(2, length -1 - i); //finds in which spot the binary is in, eg 2^1, 2^2, 2^3
                    }
                } break;

            case 4: //Excess Notation
                int excess = (int) Math.pow(2, length - 1); //finds in which spot the binary is in, eg 2^1, 2^2, 2^3
                dValue = Integer.parseInt(bString, 2) - excess;
            break;
        }
        return Math.abs(sign * dValue);
    }

    //creates a StringBuilder makes it easier to call/print
    private static String toBinaryString(int decimal, int length) 
    {
        StringBuilder binary = new StringBuilder(); //way easier to use than strings holy

        for (int i = length - 1; i >= 0; i--) 
        {
            int bit = (decimal >> i) & 1; //shifts bits to the right side
            binary.append(bit);
        }
        return binary.toString();
    }

    // Method to invert the binary string when case 2 or case 3 are called
    private static String invertBinaryString(String binaryString) 
    {
        StringBuilder invertedBinary = new StringBuilder();

        for (char c : binaryString.toCharArray()) 
        {
            invertedBinary.append(c == '0' ? '1' : '0'); //inverts the values and adds it to the new string
        }
        return invertedBinary.toString();
    }
}
