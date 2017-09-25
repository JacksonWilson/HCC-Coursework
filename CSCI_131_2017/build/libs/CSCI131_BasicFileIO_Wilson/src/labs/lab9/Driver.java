package labs.lab9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import utils.KeyboardReader;

public class Driver {

    public static void main(String[] args) {
        KeyboardReader keyReader = new KeyboardReader(System.in);
        boolean fileNotFound = false;

        do {
            try {
                String filePath = keyReader.readLine("Enter file name: ");
                BufferedReader br = new BufferedReader(new FileReader(filePath));

                WordSet wordSet = new WordSet();
                String line;
                while ((line = br.readLine()) != null) {
                    for (String word : line.split("\\s"))
                        wordSet.add(word);
                }
                br.close();

                BufferedWriter bw = new BufferedWriter(new FileWriter("WordSet_OUTPUT.txt"));
                bw.write("Date: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
                bw.newLine();
                bw.write("Total number of words: " + wordSet.getTotalAttempts());
                bw.newLine();
                bw.write("Total number of unique: " + wordSet.getWordSet().size());
                bw.newLine();
                for (String word : wordSet.getWordSet()) {
                    bw.write(word);
                    bw.newLine();
                }
                bw.close();

                keyReader.close();

                System.out.println("Done.");

            } catch (FileNotFoundException fnfe) {
                fileNotFound = true;
                System.err.println("File not found.\n");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } while (fileNotFound);
    }
}