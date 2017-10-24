package labs.lab8;

import java.io.IOException;

import utils.KeyboardReader;

public class Driver {
    private static CalculatingPowers calcPowers;
    private static KeyboardReader keyReader;
    private static boolean floatDigitsSet;
    private static int minFloatDigits;
    private static int maxFloatDigits;

    static {
        keyReader = new KeyboardReader(System.in);
        floatDigitsSet = false;
    }

    public static void main(String[] args) {
        try {
            do {
                acceptValues();
            } while (keyReader.readChar("\nContinue (Y/N)? ", new char[]{ 'Y', 'N' }) == 'Y');
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void acceptValues() throws IOException {
        if (!floatDigitsSet) {
            minFloatDigits = keyReader.readPositiveInt("Enter the minimum number of digits to display in floating-pt numbers: ");
            maxFloatDigits = keyReader.readPositiveInt("Enter the maximum number of digits to display in floating-pt numbers: ");
            floatDigitsSet = true;
        }

        double base = keyReader.readDouble("\nEnter the base: ");
        int exponent = keyReader.readInt("Enter the exponent: ");

        calcPowers = new CalculatingPowers(base, exponent);
        calcPowers.setDecimalFormatting(minFloatDigits, maxFloatDigits);

        System.out.println("\n" + calcPowers.getBaseAsString() + " ^ "
            + calcPowers.getExponentAsString() + " = "
            + calcPowers.calculatePowerAsString());
    }
}