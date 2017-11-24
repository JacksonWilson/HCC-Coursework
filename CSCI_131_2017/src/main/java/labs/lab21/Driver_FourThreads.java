package labs.lab21;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import utils.KeyboardReader;

public class Driver_FourThreads {
    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            int minValue = keyReader.readPositiveInt("Enter min value: ");
            int maxValue = keyReader.readPositiveInt("Enter max value: ");

            int interval = (maxValue - minValue) / 4;

            PrimeFinder[] pFinders = new PrimeFinder[4];
            pFinders[0] = new PrimeFinder(minValue, minValue + interval);
            pFinders[1] = new PrimeFinder(minValue + interval + 1, minValue + 2 * interval + 1);
            pFinders[2] = new PrimeFinder(minValue + 2 * interval + 2, minValue + 3 * interval + 2);
            pFinders[3] = new PrimeFinder(minValue + 3 * interval + 3, maxValue);

            long startTime, endTime;
            startTime = System.nanoTime();
            for (PrimeFinder pFinder : pFinders)
                pFinder.run();
            endTime = System.nanoTime();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("primes.txt"))) {
                for (PrimeFinder pFinder : pFinders) {
                    for (Integer p : pFinder.getPrimes()) {
                        bw.write(p.toString());
                        bw.newLine();
                    }
                }
            }
            
            System.out.println("Total time: " + ((double)(endTime - startTime) / 10e9) + " seconds");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}