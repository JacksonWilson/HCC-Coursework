package assignments.a2;

import java.io.IOException;

import utils.KeyboardReader;

/**
 * The driver class for Assignment 2 (Recursion) in CSCI 131.
 * 
 * @author Jackson Wilson
 */
public class Driver {

    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            double r1 = keyReader.readDouble("Enter r1 (the constant on the a(n-1) term): ");
            double r2 = keyReader.readDouble("Enter r2 (the constant on the a(n-2) term): ");
            System.out.println();
            double a1 = keyReader.readDouble("Enter the first term in the sequence: ");
            double a2 = keyReader.readDouble("Enter the second term in the sequence: ");
            LHD2Recurrence recurrence = new LHD2Recurrence(r1, r2, a1, a2);

            System.out.println("\nExplicit Sequence: " + recurrence.getExplicitSequenceAsString());
            System.out.print("-------------------");
            for (int i = 0; i < recurrence.getExplicitSequenceAsString().length(); i++)
                System.out.print("-");
            System.out.println("\n" + recurrence.getExplicitSequenceElementsAsString(1, 10));

            System.out.println("Recursive Sequence: " + recurrence.getRecursiveSequenceAsString());
            System.out.print("--------------------");
            for (int i = 0; i < recurrence.getRecursiveSequenceAsString().length(); i++)
                System.out.print("-");
            System.out.println("\n" + recurrence.getRecursiveSequenceElementsAsString(1, 10));

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}