package labs.lab13;

import java.text.NumberFormat;

public class Product implements Comparable<Product> {
    private long productNumber;
    private String description;
    private double price;

    public Product(long productNumber, String description, double price) {
        this.productNumber = productNumber;
        this.description = description;
        this.price = price;
    }

    public long getProductNumber() {
        return productNumber;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public int compareTo(Product other) {
        int ret = this.description.compareToIgnoreCase(other.description);
        if (ret == 0) {
            ret = Double.compare(this.price, other.price);
            if (ret == 0)
                return Long.compare(this.productNumber, other.productNumber);
        }
        return ret;
    }

    @Override
    public String toString() {
        return "Product: " + description + " Price: "
            + NumberFormat.getCurrencyInstance().format(price) + " ID: " + productNumber;
    }
}