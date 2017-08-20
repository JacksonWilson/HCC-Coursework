package labs.lab9;

import java.util.Comparator;

public class ProductsByNumber implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return Long.compare(p1.getNumber(), p2.getNumber());
    }
}