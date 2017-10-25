package labs.lab16;

import java.io.IOException;

import utils.KeyboardReader;
import utils.SearchAlgorithms;

public class Driver {

    public static void main(String[] args) {
        String[] searchTypes = {"Linear", "Binary", "Ternary", "Jump", "Interpolation", "Exponential"};
        
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {

            String filePath = keyReader.readLine("Enter file path of list: ");
            int sizeOfList = keyReader.readPositiveInt("Enter size of list: ");
            int[] arr = Maintainer.readFileIntoArray(filePath, sizeOfList);

            int searchValue = keyReader.readInt("Enter value to search for: ");

            int counter = 0;
            for (String type : searchTypes) {
                System.out.println(++counter + ". " + type);
            }

            int selection = keyReader.readInt(1, searchTypes.length) - 1;
            int index = -1;

            long start = System.currentTimeMillis();
            switch (selection) {
            case 0:
                index = SearchAlgorithms.linear(arr, searchValue);
                break;
            case 1:
                index = SearchAlgorithms.binary(arr, searchValue);
                break;
            case 2:
                index = SearchAlgorithms.ternary(arr, searchValue);
                break;
            case 3:
                index = SearchAlgorithms.jump(arr, searchValue);
                break;
            case 4:
                index = SearchAlgorithms.interpolation(arr, searchValue);
                break;
            case 5:
                index = SearchAlgorithms.exponential(arr, searchValue);
                break;
            }
            long end = System.currentTimeMillis();

            System.out.println("Finished searching " + arr.length + " elements using " + searchTypes[selection].toLowerCase() + " search in " + (end - start) + " milliseconds.");
            System.out.println("Index: " + (index != -1 ? index : "[element not found]"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}