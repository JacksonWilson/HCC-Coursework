package labs.lab1;

/*
 * Author:       Todd Simeone
 *
 * Class:        CSCI 115
 *
 * Date Written: 4/30/2002
 *
 * Topic:        Number bases
 *
 */

import java.util.Scanner;

public class NumberBases {
	public static void main(String[] args) {
		Scanner s = new Scanner (System.in);
        System.out.println("This program will display the first 100 decimal numbers");
        System.out.println("and will show their corresponding binary and octal");
        System.out.println("equivalent values.\n");
        System.out.println("Press enter to begin\n");

        s.nextLine();

        System.out.println("Binary\t\tOctal\t\tDecimal\t\tHexadecimal");

        for (int i = 1; i < 101; i++) {
            System.out.print(Integer.toBinaryString(i) + "\t\t");
            System.out.print(Integer.toOctalString(i) + "\t\t");
            System.out.print(Integer.toHexString(i) + "\t\t");
            System.out.print(i + "\t\t");
            System.out.println();

            if (((i%10) == 0) && (i != 100)) {
                System.out.println("\nPress enter to begin");
                s.nextLine();
                System.out.println("\nBinary\t\tOctal\t\tDecimal\t\tHexadecimal");
            }
        }
        System.out.println();
        s.close();
	}
}