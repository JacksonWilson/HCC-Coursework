package labs.lab0;

import java.util.Scanner;

public class Bases {
	public static void main(String args[]) {
        try (Scanner scanner = new Scanner(System.in)) {
            int valToConvert = 0, base = 0;
    
            System.out.print("Enter the number to convert:  ");
            valToConvert = scanner.nextInt();
            System.out.println();
            System.out.print("Enter the base to convert to:  ");
            base = scanner.nextInt();
            System.out.println();
            System.out.print(valToConvert + " in base " + base);
            System.out.println(" is:  " + Integer.toString(valToConvert, base));
            System.out.println();
        }
	}
}
