package labs.lab15;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Maintainer {
    public static int[] readFileIntoArray (String fileName, int size) {
        //Reads a file of integers (indicated by the fileName variable) into an integer array and returns the array
        //The number of integers in the file is indicated by the size variable
        int [] nums = new int[size];

        try (BufferedReader br  = new BufferedReader(new FileReader(fileName))) {
            for (int i = 0; i < size; i++) {
                nums[i] = Integer.valueOf(br.readLine());
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        return nums;
    }

    public static void writeArrayToFile (String fileName, int [] numsToWrite) {
        //Writes an array of integers to a file as indicated by the parameters
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (int n : numsToWrite) {
                bw.write(String.valueOf(n));
                bw.newLine();
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static <T> void writeArrayToFile (String fileName, ArrayList<T> listToWrite) {
        //Writes an array of integers to a file as indicated by the parameters
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (T v : listToWrite) {
                bw.write(String.valueOf(v));
                bw.newLine();
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}