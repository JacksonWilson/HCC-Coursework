import java.io.IOException;
import java.util.ArrayList;

/**
 * Creates an inventory of books by prompting the user for information on each book
 * and displaying the complete inventory at the end.
 * 
 * @author Jackson Wilson
 */
public class Driver {

    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            // Get the number of books to be entered into inventory.
            int numOfBooks = keyReader.readPositiveInt("Enter the number of books: ");
            System.out.println();
    
            ArrayList<Book> books = new ArrayList<Book>();
            
            // Create book objects based on user input and add to array of books.
            for (int i = 1; i <= numOfBooks; i++) {
                String title = keyReader.readLine("Enter title for Book " + i + ": ");
                String author = keyReader.readLine("Enter author for Book " + i + ": ");
                int numOfPages = keyReader.readPositiveInt(
                    "Enter number of pages for Book " + i + ": ");
    
                books.add(new Book(title, author, numOfPages));
                System.out.println();
            }
            
            // Print every book and keep track of total number of pages.
            System.out.println("Book Inventory\n--------------");
            int totalNumOfPages = 0;
            for (Book book : books) {
                totalNumOfPages += book.getNumOfPages();
                System.out.println(book);
            }

            // Print total number of pages in every book in list.
            System.out.println("\nTotal pages in all books: " + totalNumOfPages);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}