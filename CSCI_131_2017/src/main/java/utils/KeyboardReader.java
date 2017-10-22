package utils;

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
 * @version 1.1.1
 */
public class KeyboardReader extends BufferedReader {

    /**
     * Creates a new KeyboardReader with the given {@link java.io.InputStream}.
     * 
     * @param inStream An input stream such as System.in.
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
     * @throws IOException If an I/O error occurs.
     */
    public String readLine() throws IOException {
        return readLine("", true);
    }

    /**
     * Reads a line of text. A line is considered to be terminated by any one of a line
     * feed ('\n'), a carriage return ('\r'), or a carriage return followed immediately
     * by a linefeed.
     * 
     * @param allowEmpty A flag to allow empty strings as valid input.
     * @return A String containing the contents of the line, not including any line-
     * termination characters, or null if the end of the stream has been reached.
     * @throws IOException If an I/O error occurs.
     */
    public String readLine(boolean allowEmpty) throws IOException {
        return readLine("", allowEmpty);
    }

    /**
     * Reads a line of text. A line is considered to be terminated by any one of a line
     * feed ('\n'), a carriage return ('\r'), or a carriage return followed immediately
     * by a linefeed.
     * 
     * @param prompt A message to prompt the user for input.
     * @return A String containing the contents of the line, not including any line-
     * termination characters, or null if the end of the stream has been reached.
     * @throws IOException If an I/O error occurs.
     */
    public String readLine(String prompt) throws IOException {
        return readLine(prompt, true);
    }

    /**
     * Prompts user and reads a line of text. A line is considered to be terminated by
     * any one of a line feed ('\n'), a carriage return ('\r'), or a carriage return
     * followed immediately by a linefeed.
     * 
     * @param prompt A message to prompt the user for input.
     * @param allowEmpty A flag to allow empty strings as valid input.
     * @return A String containing the contents of the line, not including any line-
     * termination characters, or null if the end of the stream has been reached.
     * @throws IOException If an I/O error occurs.
     */
    public String readLine(String prompt, boolean allowEmpty) throws IOException {
        String input;
        do {
            System.out.print(prompt);
            input = super.readLine();
            if (input == null) {
                System.out.println("The end of the stream has been reached.");
                throw new IOException();
            } else {
                if (allowEmpty || !input.isEmpty())
                    return input;
                System.out.println("Please enter something.");
            }
        } while (true);
    }
    
    /**
     * Reads one character. Prompts the user to enter again if zero or more than one
     * character is entered.
     * 
     * @return A character.
     * @throws IOException If an I/O error occurs.
     */
    public char readChar() throws IOException {
        return readChar("", null);
    }
    
    /**
     * Prompts user and reads one character. Prompts the user to enter again if zero or
     * more than one character is entered.
     * 
     * @param prompt A message to prompt the user for input.
     * @return A character.
     * @throws IOException If an I/O error occurs.
     */
    public char readChar(String prompt) throws IOException {
        return readChar(prompt, null);
    }
        
    /**
     * Reads one character. Prompts the user to enter again if zero or more than one
     * character is entered.
     * 
     * @param possibleCharacters An array of valid characters.
     * @return A character.
     * @throws IOException If an I/O error occurs.
     */
    public char readChar(char... possibleCharacters) throws IOException {
        return readChar("", possibleCharacters);
    }

    /**
     * Prompts user and reads one character. Prompts the user to enter again if zero or
     * more than one character is entered.
     * 
     * @param prompt A message to prompt the user for input.
     * @param possibleCharacters An array of valid characters.
     * @return A character.
     * @throws IOException If an I/O error occurs.
     */
    public char readChar(String prompt, char... possibleCharacters) throws IOException {
        String input;
        do {
            System.out.print(prompt);
            input = readLine();

            if (!input.isEmpty() && input.length() == 1) {
                if (possibleCharacters != null) {
                    for (char ch : possibleCharacters) {
                        if (input.charAt(0) == ch)
                            return ch;
                    }
                } else {
                    return input.charAt(0);
                }
            }
            if (possibleCharacters == null) {
                System.out.println("Please enter a character.");
            } else {
                System.out.print("Please enter a valid character: { ");
                for (char ch : possibleCharacters)
                    System.out.print(ch + " ");
                System.out.println("}");
            }
        } while (true);
    }

    /**
     * Reads either &quot;TRUE&quot; or &quot;FALSE&quot; from the input stream;. Prompts
     * the user to enter again if neither is entered.
     * 
     * @return A boolean.
     * @throws IOException If an I/O error occurs.
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
     * @throws IOException If an I/O error occurs.
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
     * @throws IOException If an I/O error occurs.
     */
    public int readInt() throws IOException {
        return readInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Prompts user and reads a valid int from the input stream. Prompts the user to
     * enter again if an invalid value is entered.
     * 
     * @param prompt A message to prompt the user for input.
     * @return An int between Integer.MIN_VALUE and Integer.MAX_VALUE, inclusive.
     * @throws IOException If an I/O error occurs.
     */
    public int readInt(String prompt) throws IOException {
        return readInt(Integer.MIN_VALUE, Integer.MAX_VALUE, prompt);
    }

    /**
     * Reads a valid int from the input stream. Prompts the user to enter again if an
     * invalid value is entered.
     * 
     * @return An int between 0 and Integer.MAX_VALUE, inclusive.
     * @throws IOException If an I/O error occurs.
     */
    public int readPositiveInt() throws IOException {
        return readInt(0, Integer.MAX_VALUE);
    }

    /**
     * Prompts user and reads a valid int from the input stream. Prompts the user to
     * enter again if an invalid value is entered.
     * 
     * @param prompt A message to prompt the user for input.
     * @return An int between 0 and Integer.MAX_VALUE, inclusive.
     * @throws IOException If an I/O error occurs.
     */
    public int readPositiveInt(String prompt) throws IOException {
        return readInt(0, Integer.MAX_VALUE, prompt);
    }

    /**
     * Reads a valid int from the input stream. Prompts the user to enter again if an
     * invalid value is entered.
     * 
     * @param minValue The minimum acceptable value, inclusive.
     * @param maxValue The maximum acceptable value, inclusive.
     * @return An int between minValue and maxValue, inclusive.
     * @throws IOException If an I/O error occurs.
     */
    public int readInt(int minValue, int maxValue) throws IOException {
        return readInt(minValue, maxValue, "");
    }

    /**
     * Prompts user and reads a valid int from the input stream. Prompts the user to
     * enter again if an invalid value is entered.
     * 
     * @param minValue The minimum acceptable value, inclusive.
     * @param maxValue The maximum acceptable value, inclusive.
     * @param prompt A message to prompt the user for input.
     * @return An int between minValue and maxValue, inclusive.
     * @throws IOException If an I/O error occurs.
     */
    public int readInt(int minValue, int maxValue, String prompt) throws IOException {
        String input;
        int number;
        do {
            if (!prompt.isEmpty())
                System.out.print(prompt);
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
     * @throws IOException If an I/O error occurs.
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
     * @throws IOException If an I/O error occurs.
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
     * @throws IOException If an I/O error occurs.
     */
    public double readDouble() throws IOException {
        return readDouble("");
    }

    /**
     * Reads a valid double from the input stream. Prompts the user to enter again if an
     * invalid value is entered.
     * 
     * @param prompt A message to prompt the user for input.
     * @return A double.
     * @throws IOException If an I/O error occurs.
     */
    public double readDouble(String prompt) throws IOException {
        return readDouble(Double.MIN_VALUE, Double.MAX_VALUE, prompt);
    }

    /**
     * Reads a valid double from the input stream that is between a minimum and maximum
     * value, inclusive. Prompts the user to enter again if an invalid value is entered.
     * 
     * @param min The minimum acceptable value.
     * @param max The maximum acceptable value.
     * @param prompt A message to prompt the user for input.
     * @return A double.
     * @throws IOException If an I/O error occurs.
     */
    public double readDouble(double min, double max, String prompt) throws IOException {
        String input;
        do {
            if (!prompt.isEmpty())
                System.out.print(prompt);
            try {
                input = readLine();
                if (!input.isEmpty() && stringIsFloatingNumeric(input)) {
                    double value = Double.parseDouble(input);
                    if (value >= min && value <= max)
                        return value;
                }
            } catch (NumberFormatException e) {}
            System.out.println("Please enter a value between "
                + min + " and " + max);
        } while (true);
    }

    /**
     * Reads a valid float from the input stream. Prompts the user to enter again if an
     * invalid value is entered.
     * 
     * @return A float.
     * @throws IOException If an I/O error occurs.
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
    
    /**
     * Checks if every character (excluding a leading sign) is a digit.
     * 
     * @param str The string to be checked.
     * @return Whether the string is numeric or not.
     */
    private boolean stringIsNumeric(String str) {
        int i = (str.charAt(0) == '+' || str.charAt(0) == '-') ? 1 : 0;
        for (; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }
    
    /**
     * Checks if every character (excluding a leading sign and floating point) is a digit.
     * 
     * @param str The string to be checked.
     * @return Whether the string is numeric or not.
     */
    private boolean stringIsFloatingNumeric(String str) {
        int indexOfFloat = str.indexOf('.');
        if (indexOfFloat == 0)
            return stringIsNumeric(str.substring(1));
        if (indexOfFloat > 0)
            return stringIsNumeric(str.substring(0, indexOfFloat))
                && stringIsNumeric(str.substring(indexOfFloat + 1));
        return stringIsNumeric(str);
    }
}
