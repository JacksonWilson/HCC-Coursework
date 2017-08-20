package labs.lab9;

public class Product implements Comparable<Product> {
    private long number;
    private String description;
    private float price;

    public Product(long number, String description, float price) {
        this.number = number;
        this.description = description;
        this.price = price;
    }

    public long getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

	@Override
	public int compareTo(Product other) {
        if (this == other)
            return 0;
        
        if (this.description.toLowerCase().compareTo(other.description.toLowerCase()) == 0) {
            if (Float.compare(this.price, other.price) == 0) {
                return Long.compare(this.number, other.number);
            }
            return Float.compare(this.price, other.price);
        }
        return this.description.toLowerCase().compareTo(other.description.toLowerCase());
	}

    @Override
    public String toString() {
        return number + " | " + description + " | " + price;
    }
}