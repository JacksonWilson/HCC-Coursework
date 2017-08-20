public class Book {
    private String title;
    private String author;
    private int numOfPages;

    public Book() {
        this.title = "[No Title]";
        this.author = "[No Author]";
        this.numOfPages = -1;
    }

    public Book(String title, String author, int numOfPages) {
        this.title = title;
        this.author = author;
        this.numOfPages = numOfPages;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    @Override
    public String toString() {
        return "Title: " + title + " Author: " + author + " Pages: " + numOfPages;
    }

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