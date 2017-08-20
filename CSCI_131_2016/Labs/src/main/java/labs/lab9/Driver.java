package labs.lab9;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

public class Driver {
    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            products.add(new Product(
                rand.nextLong(), randDescription(40, rand), rand.nextFloat()));
        }

        int selection = -1;
        try (Scanner scanner = new Scanner(System.in)) {
            boolean validInput = false;
            String input;
            do {
                System.out.print("Products sorted: [1]Natural, [2]Product Number, or [3]Price First: ");
                input = scanner.nextLine();
                if (!input.isEmpty()) {
                    try {
                        selection = Integer.parseInt(input);
                        if (selection >= 1 && selection <= 3)
                            validInput = true;
                        else
                            System.out.println("Please enter either 1, 2, or 3.");
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter either 1, 2, or 3.");
                    }
                } else
                    System.out.println("Please enter either 1, 2, or 3.");
            } while (!validInput);
        }

        TreeSet<Product> set = null;

        switch (selection) {
        default:
            System.out.println("\nNot a valid selection. Defaulting to natural ordering.\n");
        case 1:
            set = new TreeSet<>();
            set.addAll(products);
            System.out.println("\nNatural Ordering\n_________________________________________________________________________");
            break;
        case 2:
            set = new TreeSet<>(new ProductsByNumber());
            set.addAll(products);
            System.out.println("\nProducts sorted by number\n_________________________________________________________________________");
            break;
        case 3:
            set = new TreeSet<>(new ProductsByPriceFirst());
            set.addAll(products);
            System.out.println("\nProducts sorted by price first\n_________________________________________________________________________");
            break;
        }

        for (Product p : set) {
            System.out.println(p);
        }
    }

    public static String randDescription(int length, Random rand) {
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 abcdefghijklmnopqrstuvwxyz";
        StringBuilder sBuilder = new StringBuilder();

        for (int i = 0; i < length; i++)
            sBuilder.append(allChars.charAt(rand.nextInt(allChars.length())));

        return sBuilder.toString();
    }
}