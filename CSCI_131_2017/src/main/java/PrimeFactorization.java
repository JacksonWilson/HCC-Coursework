import java.io.IOException;
import java.util.ArrayList;

import utils.KeyboardReader;

public class PrimeFactorization {
    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            System.out.print("Enter number: ");
            Long number = keyReader.readLong();

            System.out.print("Primes: ");

            for (Long p : getPrimes(number)) {
                System.out.print(p + ", ");
            }

            System.out.println("\b\b ");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static ArrayList<Long> getPrimes(long number) {
        ArrayList<Long> primes = new ArrayList<>();

        for (long i = 2; i <= number; i++) {
            while (number % i == 0) {
                primes.add(i);
                number /= i;
            }
        }

        return primes;
    }
}