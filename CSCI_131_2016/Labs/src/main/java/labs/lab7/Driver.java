package labs.lab7;

import java.io.IOException;

public class Driver {
    
    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            System.out.println("Is data available?");
            System.out.println((keyReader.isDataAvailable())? "Yes." : "No.");
            System.out.println("Enter a boolean value");
            System.out.println("You entered: " + keyReader.readBoolean());
            System.out.println("Enter a byte value");
            System.out.println("You entered: " + keyReader.readByte());
            System.out.println("Enter a char value");
            System.out.println("You entered: " + keyReader.readChar());
            System.out.println("Enter a double value");
            System.out.println("You entered: " + keyReader.readDouble());
            System.out.println("Enter a float value");
            System.out.println("You entered: " + keyReader.readFloat());
            System.out.println("Enter an int value");
            System.out.println("You entered: " + keyReader.readInt());
            System.out.println("Enter a string");
            System.out.println("You entered: " + keyReader.readLine());
            System.out.println("Enter a long value");
            System.out.println("You entered: " + keyReader.readLong());
            System.out.println("Enter a short value");
            System.out.println("You entered: " + keyReader.readShort());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            System.out.println("Is data available?");
            System.out.println((keyReader.isDataAvailable())? "Yes." : "No.");
            System.out.println("Enter an int value");
            System.out.println("You entered: " + keyReader.readInt());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}