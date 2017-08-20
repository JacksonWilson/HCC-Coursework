package labs.lab9;

import java.util.Comparator;

public class ProductsByPriceFirst implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        int ret = Float.compare(p1.getPrice(), p2.getPrice());
        if (ret == 0) {
            return p1.compareTo(p2);
        }
        return ret;
    }
}