package labs.lab21;

import java.util.ArrayList;

public class PrimeFinder extends Thread {
    private int minValue;
    private int maxValue;
    private ArrayList<Integer> primes;

    public PrimeFinder(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        primes = new ArrayList<>();
    }

    @Override
    public void run() {
        for (int i = minValue; i <= maxValue; i++) {
            if (isPrime(i))
                primes.add(i);
        }
    }

    public boolean isPrime(int n) {
        for (int i = 2; i <= (int)Math.sqrt(n); i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public ArrayList<Integer> getPrimes() {
        return primes;
    }
}