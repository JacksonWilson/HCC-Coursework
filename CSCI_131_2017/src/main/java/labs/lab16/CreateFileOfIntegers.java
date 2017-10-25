package labs.lab16;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class CreateFileOfIntegers {
    //Writes a file with with the number of integers enterd by user, and with the sort characteristic (sorted or not)
    //entered by the user. Creates the file name accordingly.
    //
    //Unsorted values are between 0 and 9999 inclusive
    //Sorted values start with a value between 0 and 9 inclusive, and then continually get larger by random value between 0 and 9 inclusive

    public static final void main (String [] args) {
        Random r = new Random();
        BufferedWriter bw;
        String fileName = "res/labs/lab15/";
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter number of integers to write: ");
        int numToWrite = scan.nextInt();
        scan.nextLine();

        System.out.print("Sorted or unsorted (S/U): ");
        char sort = scan.nextLine().charAt(0);

        try {
            if (Character.toUpperCase(sort) == 'U') {
                fileName += "unsorted" + String.valueOf(numToWrite) + ".txt";
                bw = new BufferedWriter(new FileWriter(fileName));

                for (int i = 1; i <= numToWrite; i++) {
                    bw.write(String.valueOf(r.nextInt(10000)));
                    bw.newLine();
                }
            }
            else {
                fileName += "sorted" + String.valueOf(numToWrite) + ".txt";
                bw = new BufferedWriter(new FileWriter(fileName));
                int previous= 0;

                for (int i = 1; i <= numToWrite; i++) {
                    bw.write(String.valueOf(previous += r.nextInt(10)));
                    bw.newLine();
                }
            }

            bw.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}