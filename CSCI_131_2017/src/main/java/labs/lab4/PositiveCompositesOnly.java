package labs.lab4;

import java.math.BigInteger;
import java.util.ArrayList;

public class PositiveCompositesOnly {
    private ArrayList<Integer> positiveComposites;

    public PositiveCompositesOnly() {
        positiveComposites = new ArrayList<>();
    }

    public void addNumberToList(int n) throws PrimeNumberException {
        if (BigInteger.valueOf(n).isProbablePrime(10))
            throw new PrimeNumberException("\n*** High likelihood of prime number - caught by Exception ***\n");
        positiveComposites.add(n);
    }

    public void displayNumbersInList() {
        System.out.println("Here are the valid numbers that were entered");
        System.out.println("--------------------------------------------");
        for (int n : positiveComposites)
            System.out.println(n);
    }
}