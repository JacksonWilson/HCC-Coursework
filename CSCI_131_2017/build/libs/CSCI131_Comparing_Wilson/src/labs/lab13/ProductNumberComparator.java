package labs.lab13;

import java.util.Comparator;

public class ProductNumberComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return Long.compare(p1.getProductNumber(), p2.getProductNumber());
    }
}