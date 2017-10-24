package labs.lab12;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ProcessInputFile {

    public static void main(String[] args) {
        ArrayList<Integer> numbers = getNumbersFromFile("potentialpalindromes.dat");

        if (numbers.isEmpty())
            return;
        
        ArrayList<Integer> palindromes = new ArrayList<>();
        ArrayList<Integer> nonpalindromes = new ArrayList<>();

        for (Integer n : numbers) {
            if (PalindromeFinder.isPalindrome(n))
                palindromes.add(n);
            else
                nonpalindromes.add(n);
        }
        
        generateOutput(numbers, palindromes, "palindromes", "Palindromes.txt");
        generateOutput(numbers, nonpalindromes, "Non-palindromes", "NonPalindromes.txt");
    }

    private static ArrayList<Integer> getNumbersFromFile(String path) {
        ArrayList<Integer> numbers = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                ArrayList<?> arr = (ArrayList<?>)obj;
                for (Object o : arr) {
                    if (o instanceof Integer)
                        numbers.add((Integer)o);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

        return numbers;
    }

    private static void generateOutput(ArrayList<Integer> numbers, ArrayList<Integer> values, String text, String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("Total amount of numbers: ");
            bw.write(Integer.toString(numbers.size()));
            bw.newLine();
            bw.write("Total number of ");
            bw.write(text.toLowerCase());
            bw.write(": ");
            bw.write(Integer.toString(values.size()));
            bw.newLine();
            bw.newLine();
            bw.write(text);
            bw.newLine();
            bw.write("-----------");
            bw.newLine();
            
            for (Integer n : values) {
                bw.write(n.toString());
                bw.newLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}