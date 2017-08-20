package labs.lab12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import labs.lab7.KeyboardReader;

public class Driver {
    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            System.out.print("Type of sort: [1]Insertion, or [2]Bubble: ");

            HashSet<String> words = new HashSet<>();

            try (BufferedReader reader = 
                    new BufferedReader(new FileReader("resources/story.txt"))) {
                reader.lines().forEach(line -> {
                    words.addAll(Arrays.asList(line.split("[^\\p{L}0-9']+")));
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] strings = words.toArray(new String[0]);

            switch(keyReader.readInt(1, 2)) {
            case 1: doInsertionSort(strings);
                break;
            case 2: doBubbleSort(strings);
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doInsertionSort(String[] strings) {
        long startTime = System.nanoTime();

        // Start sort
        String temp;
        for(int i = 1; i < strings.length; i++)
        {
            temp = strings[i];
            int j;
            for(j = i; j > 0 && temp.compareTo(strings[j - 1]) < 0; j--)
                strings[j] = strings[j - 1];
            strings[j] = temp;
        }
        // End sort

        long endTime = System.nanoTime();

        System.out.println("Insertion Sort:");
        for (String str : strings)
            System.out.println(str);
        System.out.println("Time: " + (endTime - startTime) + " nanoseconds.");
    }

    private static void doBubbleSort(String[] strings) {
        long startTime = System.nanoTime();

        // Start sort
        String temp;
        for (int i = 0; i < strings.length - 1; i++) {
            for (int j = 0; j < strings.length - 1; j++) {
                if (strings[j].compareTo(strings[j + 1]) > 0) {
                    temp = strings[j];
                    strings[j] = strings[j + 1];
                    strings[j + 1] = temp;
                }
            }
        }
        // End sort

        long endTime = System.nanoTime();

        System.out.println("Insertion Sort:");
        for (String str : strings)
            System.out.println(str);
        System.out.println("Time: " + (endTime - startTime) + " nanoseconds.");
    }
}