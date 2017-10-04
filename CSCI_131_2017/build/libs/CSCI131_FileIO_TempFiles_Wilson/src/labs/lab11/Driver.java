package labs.lab11;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) throws IOException {
        File tempFile = File.createTempFile("stats", ".csv");

        try (BufferedReader br = new BufferedReader(new FileReader("data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                ArrayList<Integer> ints = getIntsFromLine(line);

                if (ints.size() > 0) {
                    writeStatDataToFile(tempFile, ints);
                } else {
                    System.err.println("Please make sure data.csv has data in it.");
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        Runtime.getRuntime().exec(new String[] {
                "C:\\Program Files\\Microsoft Office\\Office16\\EXCEL.exe",
                tempFile.getAbsolutePath()
            });
        
        System.out.print("Done. [Press any key to close]");
        System.in.read();
        tempFile.deleteOnExit();
    }

    private static ArrayList<Integer> getIntsFromLine(String line) {
        ArrayList<Integer> ints = new ArrayList<>();

        for (String value : line.split(",")) {
            int intValue = Integer.parseInt(value);
            ints.add(intValue);
        }
        
        return ints;
    }

    private static void writeStatDataToFile(File tempFile, ArrayList<Integer> ints) throws IOException {
        int total = 0;
        int largest = ints.get(0);
        int smallest = ints.get(0);
        for (Integer n : ints) {
            total += n;
            if (n > largest)
                largest = n;
            else if (n < smallest)
                smallest = n;
        }
        
        double mean = (double)total / ints.size();
        int range = largest - smallest;

        int sum = 0;
        for (Integer n : ints) {
            sum += Math.pow(n - mean, 2);
        }

        double sd = Math.sqrt(sum / (ints.size()));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile, true))) {
            bw.write(mean + "," + range + "," + sd);
            bw.newLine();
        }
    }
}