package labs.lab6;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import csci130.KeyboardReader;

public class Driver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = null;
        String fileName = null;
        boolean validInput = false;
        do {
            System.out.print("Enter file name: ");

            if (scanner.hasNextLine())
                input = scanner.nextLine();
            if (!input.isEmpty()) {
                fileName = input;
                validInput = true;
            }
        } while (!validInput);

        File file = new File(fileName);
        try {
            if (!file.exists() && file.createNewFile() && file.canWrite()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));

                // while (scanner.hasNextLine()) {
                //     input = scanner.nextLine();
                //     if (input.toLowerCase().contains("quit")) {
                //         writer.write(input.substring(0, input.toLowerCase().indexOf("quit")));
                //         break;
                //     }
                //     writer.write(input);
                //     writer.newLine();
                // }

                while (KeyboardReader.isDataAvailable()) {
                    input = KeyboardReader.readLine();
                    if (input.toLowerCase().contains("quit")) {
                        writer.write(input.substring(0, input.toLowerCase().indexOf("quit")));
                        break;
                    }
                    writer.write(input);
                    writer.newLine();
                }
                writer.close();
                scanner.close();

                Scanner fileScanner = new Scanner(new File(fileName));
                while (fileScanner.hasNextLine())
                    System.out.println(fileScanner.nextLine());
                fileScanner.close();
            } else
                System.out.println("Could not create file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}