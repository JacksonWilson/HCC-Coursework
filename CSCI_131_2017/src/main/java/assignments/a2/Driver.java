package assignments.a2;

import java.io.IOException;

import utils.KeyboardReader;

public class Driver {

    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            double r1 = keyReader.readDouble();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}