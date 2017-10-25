package labs.lab15;

import java.io.IOException;

import utils.KeyboardReader;
import utils.SortAlgorithms;

public class Driver {

    public static void main(String[] args) {
        String[] sortTypes = {"Bubble", "Insertion", "Merge"};
        
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {

            String filePath = keyReader.readLine("Enter file path of list: ");
            int sizeOfList = keyReader.readPositiveInt("Enter size of list: ");
            int[] arr = Maintainer.readFileIntoArray(filePath, sizeOfList);

            int counter = 0;
            for (String type : sortTypes) {
                System.out.println(++counter + ". " + type);
            }

            int selection = keyReader.readInt(1, sortTypes.length) - 1;

            long start = System.currentTimeMillis();
            switch (selection) {
            case 0:
                SortAlgorithms.bubble(arr);
                break;
            case 1:
                SortAlgorithms.insertion(arr);
                break;
            case 2:
                SortAlgorithms.merge(arr);
                break;
            }
            long end = System.currentTimeMillis();

            Maintainer.writeArrayToFile("SortedBy" + sortTypes[selection] + ".txt", arr);

            System.out.println("Finished sorting " + arr.length + " elements using " + sortTypes[selection].toLowerCase() + " sort in " + (end - start) + " milliseconds.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}