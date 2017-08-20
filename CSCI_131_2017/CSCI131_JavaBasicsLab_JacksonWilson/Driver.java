import java.util.Scanner;
import java.util.regex.Pattern;

public class Driver {
    private static Scanner scanner;
    
    static {
        scanner = new Scanner(System.in);
    }
    
    private static int getIntegerFromInput(String prompt) {
        String input;
        do {
            input = getInput(prompt);
            if (stringIsNumeric(input)) {
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException nfe) {}
            }
            System.out.println("Please enter a number.");
        } while (true);
    }
    
    private static boolean stringIsNumeric(String str) {
        int i = (str.charAt(0) == '+' || str.charAt(0) == '-') ? 1 : 0;
        for (; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    private static String getInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            if (scanner.hasNextLine()) {
                input = scanner.nextLine().trim();
                if (input != null && !input.isEmpty()) {
                    return input;
                }
                System.out.println("Please enter something.");
            }
        } while (true);
    }

    public static void main(String[] args) {
        double boreLength = getIntegerFromInput("Cylinder bore length: ");
        double strokeLength = getIntegerFromInput("Stroke length: ");
        int numCylinders = getIntegerFromInput("Number of cylinders: ");

        Engine engine = new Engine(boreLength, strokeLength, numCylinders);
        System.out.println("\n" + engine);

        scanner.close();
    }
}