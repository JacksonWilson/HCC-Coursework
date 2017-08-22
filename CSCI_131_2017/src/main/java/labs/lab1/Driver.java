package labs.lab1;

import java.io.IOException;

import utils.KeyboardReader;

/**
 * Creates an Engine object by prompting the user for the cylinder bore length,
 * stroke length, and number of cylinders.
 * 
 * @author Jackson Wilson
 */
public class Driver {

    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            double boreLength = keyReader.readDouble("Cylinder bore length: "); //getIntegerFromInput("Cylinder bore length: ");
            double strokeLength =  keyReader.readDouble("Stroke length: "); //getIntegerFromInput("Stroke length: ");
            int numCylinders =  keyReader.readInt("Number of cylinders: "); //getIntegerFromInput("Number of cylinders: ");
    
            Engine engine = new Engine(boreLength, strokeLength, numCylinders);
            System.out.println("\n" + engine);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}