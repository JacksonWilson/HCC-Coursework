package labs.lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Adapted from the original KeyboardReader written by Nicolaas tenBroek for CSCI 130.
 * 
 * <p>This wrapper class for a {@link java.io.BufferedReader} removes the burden of blank
 * input and NumberFormatExceptions which allows the programmer to focus on getting
 * keyboard input from the user rather than handling user caused exceptions.</p>
 * 
 * <p>Original copyright Heartland Community College (c) 2003</p>
 * <p>Copyright Jackson Wilson (c) 2017</p>
 * 
 * @author Jackson Wilson
 * @version 1.1.0
 */
public class KeyboardReader extends BufferedReader {

    /**
     * Creates a new KeyboardReader with the given {@link java.io.InputStream}.
     * 
     * @param inStream An input stream.
     */
    public KeyboardReader(InputStream inStream) {
        super(new InputStreamReader(inStream));
    }

    /**
     * Returns true if data is waiting in the buffer, or false if the stream is not ready
     * or there is no input waiting in the buffer.
     * 
     * @return A boolean that determines if the reader is ready.
     */
    public boolean isDataAvailable() {
        try {
            return ready();
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Reads a line of text. A line is considered to be terminated by any one of a line
     * feed ('\n'), a carriage return ('\r'), or a carriage return followed immediately
     * by a linefeed.
     * 
     * @return A String containing the contents of the line, not including any line-
     * termination characters, or null if the end of the stream has been reached.
     */
    @Override
    public String readLine() throws IOException {
        String input;
        do {
            input = super.readLine();
            if (input != null)
                return input;
            System.out.println("The end of the stream has been reached.");
            throw new IOException();
        } while (true);
    }

    /**
     * Reads one character. Prompts the user to enter again if zero or more than one
     * character is entered.
     * 
     * @return A character.
     */
    public char readChar() throws IOException {
        String input;
        do {
            input = readLine();

            if (input.isEmpty() || input.length() > 1)
                System.out.println("Please enter a character.");
            else
                return input.charAt(0);
        } while (true);
    }

    /**
     * Reads either &quot;TRUE&quot; or &quot;FALSE&quot from the input stream;. Prompts
     * the user to enter again if neither is entered.
     * 
     * @return A boolean.
     */
    public boolean readBoolean() throws IOException {
        String input;
        do {
            input = readLine().toUpperCase();
            if (input.equalsIgnoreCase("TRUE") || input.equalsIgnoreCase("FALSE"))
                return Boolean.parseBoolean(input);
            System.out.println("Please enter either \'true\' or \'false\'.");
        } while (true);
    }

    /**
     * Reads a valid long from the input stream. Prompts the user to enter again if an
     * invalid value is entered.
     * 
     * @return A long.
     */
    public long readLong() throws IOException {
        String input;
        do {
            try {
                input = readLine();
                if (!input.isEmpty() && stringIsNumeric(input))
                    return Long.parseLong(input);
            } catch (NumberFormatException e) {}
            System.out.println("Please enter an integer value between "
                + Long.MIN_VALUE + " and " + Long.MAX_VALUE);
        } while (true);
    }
    
    /**
     * Reads a valid int from the input stream. Prompts the user to enter again if an
     * invalid value is entered.
     * 
     * @return An int between Integer.MIN_VALUE and Integer.MAX_VALUE, inclusive.
     */
    public int readInt() throws IOException {
        return readInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
        
    /**
     * Reads a valid int from the input stream. Prompts the user to enter again if an
     * invalid value is entered.
     * 
     * @return An int between 0 and Integer.MAX_VALUE, inclusive.
     */
    public int readPositiveInt() throws IOException {
        return readInt(0, Integer.MAX_VALUE);
    }

    /**
     * Reads a valid int from the input stream. Prompts the user to enter again if an
     * invalid value is entered.
     * 
     * @param minValue The minimum acceptable value, inclusive.
     * @param maxValue The maximum acceptable value, inclusive.
     * @return An int between minValue and maxValue, inclusive.
     */
    public int readInt(int minValue, int maxValue) throws IOException {
        String input;
        int number;
        do {
            try {
                input = readLine();
                if (!input.isEmpty() && stringIsNumeric(input)) {
                    number = Integer.parseInt(input);
                    if (number >= minValue && number <= maxValue)
                        return number;
                }
            } catch (NumberFormatException e) {}
            System.out.println("Please enter an integer value between "
                + minValue + " and " + maxValue);
        } while (true);
    }

    /**
     * Reads a valid short form the input stream. Prompts the user to enter again if an
     * invalid value is entered.
     * 
     * @return A short.
     */
    public short readShort() throws IOException {
        String input;
        do {
            try {
                input = readLine();
                if (!input.isEmpty() && stringIsNumeric(input))
                    return Short.parseShort(input);
            } catch (NumberFormatException e) {}
            System.out.println("Please enter an integer value between "
                + Short.MIN_VALUE + " and " + Short.MAX_VALUE);
        } while (true);
    }

    /**
     * Reads a valid byte from the input stream. Prompts the user to enter again if an
     * invalid value is entered.
     * 
     * @return A byte.
     */
    public byte readByte() throws IOException {
        String input;
        do {
            try {
                input = readLine();
                if (!input.isEmpty() && stringIsNumeric(input))
                    return Byte.parseByte(input);
            } catch (NumberFormatException e) {}
            System.out.println("Please enter an integer value between "
                + Byte.MIN_VALUE + " and " + Byte.MAX_VALUE);
        } while (true);
    }

    /**
     * Reads a valid double from the input stream. Prompts the user to enter again if an
     * invalid value is entered.
     * 
     * @return A double.
     */
    public double readDouble() throws IOException {
        String input;
        do {
            try {
                input = readLine();
                if (!input.isEmpty() && stringIsFloatingNumeric(input))
                    return Double.parseDouble(input);
            } catch (NumberFormatException e) {}
            System.out.println("Please enter a value between "
                + Double.MIN_VALUE + " and " + Double.MAX_VALUE);
        } while (true);
    }

    /**
     * Reads a valid float from the input stream. Prompts the user to enter again if an
     * invalid value is entered.
     * 
     * @return A float.
     */
    public float readFloat() throws IOException {
        String input;
        do {
            try {
                input = readLine();
                if (!input.isEmpty() && stringIsFloatingNumeric(input))
                    return Float.parseFloat(input);
            } catch (NumberFormatException e) {}
            System.out.println("Please enter a value between "
                + Float.MIN_VALUE + " and " + Float.MAX_VALUE);
        } while (true);
    }
    
    private boolean stringIsNumeric(String str) {
        int i = (str.charAt(0) == '+' || str.charAt(0) == '-') ? 1 : 0;
        for (; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }
    
    private boolean stringIsFloatingNumeric(String str) {
        int indexOfFloat = str.indexOf('.');
        if (indexOfFloat >= 0) 
            return stringIsNumeric(str.substring(0, indexOfFloat))
                && stringIsNumeric(str.substring(indexOfFloat + 1));
        return stringIsNumeric(str);
    }
}
