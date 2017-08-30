package labs.lab4;

import java.io.IOException;

import utils.KeyboardReader;

public class Driver {

    public static void main(String[] args) {
        PositiveCompositesOnly pco = new PositiveCompositesOnly();

        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            int numberOfElements = keyReader.readPositiveInt("Enter the number of elements to be added to the list: ");
            int n;

            for (int i = 0; i < numberOfElements; ) {
                try {
                    n = keyReader.readInt("Enter a number to add to the list: ");
                    assert n > 0 : "\n*** input is not positive - caught by Assertion\n";
                    pco.addNumberToList(n);
                    i++;
                } catch (AssertionError ae) {
                    System.out.println(ae.getMessage());
                } catch (PrimeNumberException pne) {
                    System.out.println(pne.getMessage());
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        System.out.println();
        pco.displayNumbersInList();
    }
}