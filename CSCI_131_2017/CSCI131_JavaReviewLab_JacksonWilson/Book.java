/**
 * An object dedicated to storing the title, author, and number of pages of a book.
 * 
 * @author Jackson Wilson
 */
public class Book {
    private String title;
    private String author;
    private int numOfPages;

    /**
     * Default book has &quot;[No Title]&quot; as the title, &quot;[No Author]&quot;,
     * and &quot;-1&quot; as the number of pages.
     */
    public Book() {
        this.title = "[No Title]";
        this.author = "[No Author]";
        this.numOfPages = -1;
    }

    /**
     * A book object with given title, author, and number of pages.
     * 
     * @param title
     * @param author
     * @param numOfPages
     */
    public Book(String title, String author, int numOfPages) {
        this.title = title;
        this.author = author;
        this.numOfPages = numOfPages;
    }
    
    public int getNumOfPages() {
        return numOfPages;
    }

    /**
     * A formatted string with the title, author, and number of pages of the book.
     * 
     * @return A string to represent a book object.
     */
    @Override
    public String toString() {
        return "Title: " + title + " Author: " + author + " Pages: " + numOfPages;
    }

    /**
     * Determines whether or not the other object has the same title, author, and
     * number of pages as this object.
     * 
     * @param o An object.
     * @return A boolean.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Book) {
            Book other = (Book) o;
            return this.title.equals(other.title) && this.author.equals(other.author)
                && this.numOfPages == other.numOfPages;
        }
        return false;
    }
}