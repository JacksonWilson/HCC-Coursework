package labs.lab22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Driver {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Missing input file in args.");
            return;
        }
        
        int numTrainsCreated = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = br.readLine()) != null) {
                (new Train(Integer.parseInt(line.substring(0, line.indexOf(','))), Integer.parseInt(line.substring(line.indexOf(',') + 1)))).start();
                numTrainsCreated++;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Train.setAllTrainsDeployed(true);
        System.out.println("*** There were " + numTrainsCreated + " created ***");
    }
}