package labs.lab21;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import utils.KeyboardReader;

public class Driver_Runnable {

    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            int minValue = keyReader.readPositiveInt("Enter min value: ");
            int maxValue = keyReader.readPositiveInt("Enter max value: ");

            PrimeFinderRunnable primeFinder = new PrimeFinderRunnable(minValue, maxValue);
            long startTime, endTime;
            startTime = (new Date()).getTime();
            Thread thread = new Thread(primeFinder);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            endTime = (new Date()).getTime();
            System.out.println("Total time: " + (endTime - startTime) + "ms");

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("primes.txt"))) {
                for (Integer p : primeFinder.getPrimes()) {
                    bw.write(p.toString());
                    bw.newLine();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}