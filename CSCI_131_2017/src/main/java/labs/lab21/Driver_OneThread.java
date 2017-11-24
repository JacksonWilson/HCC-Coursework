package labs.lab21;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import utils.KeyboardReader;

public class Driver_OneThread {

    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            int minValue = keyReader.readPositiveInt("Enter min value: ");
            int maxValue = keyReader.readPositiveInt("Enter max value: ");

            PrimeFinder primeFinder = new PrimeFinder(minValue, maxValue);
            long startTime, endTime;
            startTime = System.nanoTime();
            primeFinder.run();
            endTime = System.nanoTime();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("primes.txt"))) {
                for (Integer p : primeFinder.getPrimes()) {
                    bw.write(p.toString());
                    bw.newLine();
                }
            }
            
            System.out.println("Total time: " + ((double)(endTime - startTime) / 10e9) + " seconds");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}