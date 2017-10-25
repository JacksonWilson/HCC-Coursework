package labs.lab15;

import java.io.IOException;

import utils.KeyboardReader;
import utils.SortAlgorithms;

public class Driver {

    public enum SortType {
        Bubble,
        Insertion,
        Merge
    }

    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {

            String filePath = keyReader.readLine("Enter file path of list: ");
            int sizeOfList = keyReader.readPositiveInt("Enter size of list: ");
            int[] arr = Maintainer.readFileIntoArray(filePath, sizeOfList);

            int counter = 0;
            for (SortType type : SortType.values()) {
                System.out.println(++counter + ". " + type.name());
            }

            int selection = keyReader.readInt(1, SortType.values().length) - 1;

            switch (SortType.values()[selection]) {
            case Bubble:
                SortAlgorithms.bubble(arr);
                break;
            case Insertion:
                SortAlgorithms.insertion(arr);
                break;
            case Merge:
                SortAlgorithms.merge(arr);
                break;
            }

            Maintainer.writeArrayToFile("SortedBy" + SortType.values()[selection].name() + ".txt", arr);

            System.out.println("Done.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}