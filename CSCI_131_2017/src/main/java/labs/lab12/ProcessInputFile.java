package labs.lab12;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ProcessInputFile {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("potentialpalindromes.dat"))) {
            numbers = (ArrayList<Integer>)(ois.readObject());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

        if (numbers == null)
            return;
        
        ArrayList<Integer> palindromes = new ArrayList<>();
        ArrayList<Integer> nonpalindromes = new ArrayList<>();

        for (Integer n : numbers) {
            if (PalindromeFinder.isPalindrome(n))
                palindromes.add(n);
            else
                nonpalindromes.add(n);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Palindromes.txt"))) {
            bw.write("Total amount of numbers: " + numbers.size());
            bw.newLine();
            bw.write("Total number of palindromes: " + palindromes.size());
            bw.newLine();
            bw.newLine();
            bw.write("Palindromes");
            bw.newLine();
            bw.write("-----------");
            bw.newLine();
            
            for (Integer n : palindromes) {
                bw.write(n.toString());
                bw.newLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("NonPalindromes.txt"))) {
            bw.write("Total amount of numbers: " + numbers.size());
            bw.newLine();
            bw.write("Total number of non-palindromes: " + nonpalindromes.size());
            bw.newLine();
            bw.newLine();
            bw.write("Non-palindromes");
            bw.newLine();
            bw.write("-----------");
            bw.newLine();
            
            for (Integer n : nonpalindromes) {
                bw.write(n.toString());
                bw.newLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}