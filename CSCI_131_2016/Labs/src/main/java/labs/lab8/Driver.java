package labs.lab8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader("resources/lab8/grades.csv"))) {
            String line;
            do {
                line = reader.readLine();
                if (line == null)
                    break;
                rows.add(line.split(","));
            } while(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!rows.isEmpty() && rows.get(0).length > 1) {
            String[] averagesRow = new String[rows.get(0).length];

            averagesRow[0] = "Average";
            for (int c = 1; c < averagesRow.length; c++) {
                double total = 0.0;
                for (int r = 1; r < rows.size(); r++) {
                    try {
                        total += Double.parseDouble(rows.get(r)[c]);
                    } catch (NumberFormatException e) {}
                }
                averagesRow[c] = Double.toString(total / (rows.size() - 1));
            }

            rows.add(averagesRow);

            String[] averagesColumn = new String[rows.size()];
            averagesColumn[0] = "Average";

            for (int r = 1; r < rows.size(); r++) {
                double total = 0.0;
                for (int c = 1; c < rows.get(0).length; c++) {
                    try {
                        total += Double.parseDouble(rows.get(r)[c]);
                    } catch (NumberFormatException e) {}
                }
                averagesColumn[r] = Double.toString(total / (rows.get(0).length - 1));
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(
                    "resources/lab8/grades_averaged.csv"))) {
                for (int r = 0; r < rows.size(); r++) {
                    writer.write(String.join(",", rows.get(r)) + "," + averagesColumn[r]);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}