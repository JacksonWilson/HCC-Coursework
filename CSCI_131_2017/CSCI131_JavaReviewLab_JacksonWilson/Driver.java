import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Driver {
    private static final Pattern POSITIVE_NUM_PATTERN = Pattern.compile("(\\+)?\\d+");
    private static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    private static int getPositiveIntegerFromInput(String prompt) {
        String input;
        do {
            input = getInput(prompt);
            if (POSITIVE_NUM_PATTERN.matcher(input).matches()) {
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException nfe) {}
            }
            System.out.println("Please enter a positive number.");
        } while (true);
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
        int numOfBooks = getPositiveIntegerFromInput("Enter the number of books: ");
        System.out.println();

        ArrayList<Book> books = new ArrayList<Book>();

        for (int i = 1; i <= numOfBooks; i++) {
            String title = getInput("Enter title for Book " + i + ": ");
            String author = getInput("Enter author for Book " + i + ": ");
            int numOfPages = getPositiveIntegerFromInput(
                "Enter number of pages for Book " + i + ": ");

            books.add(new Book(title, author, numOfPages));
            System.out.println();
        }

        System.out.println("Book Inventory\n--------------");
        int totalNumOfPages = 0;
        for (Book book : books) {
            totalNumOfPages += book.getNumOfPages();
            System.out.println(book);
        }
        System.out.println("\nTotal pages in all books: " + totalNumOfPages);

        scanner.close();
    }
}