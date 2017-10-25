package labs.lab15;

import java.io.IOException;

import utils.KeyboardReader;

public class Driver {

    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            switch (keyReader.readChar("Enter entry (c=CreateFileOfIntegers, b=Bubble, i=Insertion, m=Merge): ", 'c', 'b', 'i', 'm')) {
            case 'c': CreateFileOfIntegers.main(null);
                break;
            case 'b': Bubble.main(null);
                break;
            case 'i': Insertion.main(null);
                break;
            case 'm': Merge.main(null);
                break;
            default: System.err.println("No entry selected.");
                break;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}