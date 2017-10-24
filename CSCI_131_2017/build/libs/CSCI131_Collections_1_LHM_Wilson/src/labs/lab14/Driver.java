package labs.lab14;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import utils.KeyboardReader;

public class Driver {

    public static void main(String[] args) {
        try {
            TreeMap<String, DataSet> disciplines = getDisciplines("reg_in.txt");
            double primaryScoreInput = 0.0;
            
            try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
                primaryScoreInput = keyReader.readDouble("Enter primary score: ");
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("reg_out.txt"))) {
                for (Map.Entry<String, DataSet> entry : disciplines.entrySet()) {
                    bw.write("Discipline: ");
                    bw.write(entry.getKey());
                    bw.newLine();
                    bw.write("---------------------------");
                    bw.newLine();

                    bw.write("Primary scores (sorted):");
                    for (Integer n : getScoresAscending(entry.getValue().getPrimaryScores()))
                        bw.write(" " + n);
                    bw.newLine();

                    bw.write("Secondary scores (sorted): ");
                    for (Integer n : getScoresAscending(entry.getValue().getSecondaryScores()))
                        bw.write(" " + n);
                    bw.newLine();

                    bw.write("Linear Model: ");
                    bw.write(String.format("y = %.2fx + %.2f", entry.getValue().getModel().getSlope(), entry.getValue().getModel().getYIntercept()));
                    bw.newLine();

                    bw.write("Secondary score expected for standard score of ");
                    bw.write(String.format("%.0f", primaryScoreInput));
                    bw.write(": ");
                    bw.write(String.format("%.0f", entry.getValue().getModel().getXValue(primaryScoreInput)));
                    bw.newLine();
                    bw.newLine();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static TreeMap<String, DataSet> getDisciplines(String path) throws IOException {
        TreeMap<String, DataSet> disciplines = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int numSets = Integer.parseInt(br.readLine());

            for (int i = 0; i < numSets; i++) {
                String line = br.readLine();
                int indexOfFirstSpace = line.indexOf(' ');
                int numScores = Integer.parseInt(line.substring(0, indexOfFirstSpace));
                String discipline = line.substring(indexOfFirstSpace + 1);

                String[] primaryScoresRaw = br.readLine().split(" ");
                String[] secondaryScoresRaw = br.readLine().split(" ");
                
                LinkedList<Integer> primaryScores = new LinkedList<>();
                LinkedList<Integer> secondaryScores = new LinkedList<>();

                for (int j = 0; j < numScores; j++) {
                    primaryScores.add(Integer.parseInt(primaryScoresRaw[j]));
                    secondaryScores.add(Integer.parseInt(secondaryScoresRaw[j]));
                }
                
                disciplines.put(discipline, new DataSet(primaryScores, secondaryScores));
            }
        }

        return disciplines;
    }

    private static LinkedList<Integer> getScoresAscending(LinkedList<Integer> scores) {
        LinkedList<Integer> ascending = new LinkedList<>(scores);
        Collections.sort(ascending);
        return ascending;
    }
}