package labs.lab13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import utils.KeyboardReader;

public class Driver {

    private static KeyboardReader keyReader;

    static {
        keyReader = new KeyboardReader(System.in);
    }

    private static ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(17732, "Paper", 2.11));
        products.add(new Product(14413, "Scissors", 5.43));
        products.add(new Product(20011, "Binder", 7.91));
        products.add(new Product(14413, "scissors", 4.43));
        products.add(new Product(14499, "Tablet", 5.43));
        products.add(new Product(74413, "scissors", 2.43));
        return products;
    }

    public static void main(String[] args) {
        ArrayList<Product> products = getProducts();
        try {
            char option;
            do {
                switch (option = displayMenu()) {
                case '1':
                    Collections.sort(products);
                    printProducts(products);
                    break;
                case '2':
                    Collections.sort(products, new ProductNumberComparator());
                    printProducts(products);
                    break;
                case '3':
                    Collections.sort(products, new ProductPriceComparator());
                    printProducts(products);
                    break;
                case '9':
                    break;
                default:
                    System.out.println("Invalid option, please enter 1, 2, 3, or 9.");
                    break;
                }
            } while (option != '9');
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static char displayMenu() throws IOException {
        System.out.println("1. Display by product description");
        System.out.println("2. Display by product number");
        System.out.println("3. Display by product price");
        System.out.println("9. Exit");
        System.out.println();
        return keyReader.readChar("Enter option: ", '1', '2', '3', '9');
    }

    private static void printProducts(ArrayList<Product> products) {
        System.out.println();
        for (Product p : products)
            System.out.println(p);
        System.out.println();
    }
}