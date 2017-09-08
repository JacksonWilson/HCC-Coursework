package labs.lab6;

import java.io.IOException;

import utils.KeyboardReader;

public class Driver {

    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            int x = keyReader.readPositiveInt("Enter a subscript x: ");

            System.out.println("\nSequence 1: a(n) = -0.25 * a(n-1) + 0.375 * a(n-2) where a(1) = 5 and a(2) = -3");
            for (int n = 1; n <= x; n++)
                System.out.println("a(" + n + ") = " + getSequence1Value(n));
            
            System.out.println("\nSequence 2: S(n) = Summation of [(S(n-i) - 1 ) / i!] from i=1 to n where S(0) = 0");
            for (int n = 1; n <= x; n++)
                System.out.println("a(" + n + ") = " + getSequence2Value(n));

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static double getSequence1Value(int n) {
        if (n == 1)
            return 5;
        if (n == 2)
            return -3;
        return -0.25 * getSequence1Value(n - 1) + 0.375 * getSequence1Value(n - 2);
    }

    private static double getSequence2Value(int n) {
        if (n == 0)
            return 0;
        double total = 0.0;
        for (int i = 1; i <= n; i++)
            total += (getSequence2Value(n - i) - 1) / getFactorialValue(i);
        return total;
    }

    private static int getFactorialValue(int n) {
        if (n == 1)
            return 1;
        return n * getFactorialValue(n - 1);
    }
}