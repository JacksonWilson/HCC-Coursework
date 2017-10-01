package assignments.a3;

import java.io.IOException;

import utils.KeyboardReader;

/**
 * The driver for Assignment 3: File I/O.
 * 
 * @author Jackson Wilson
 */
public class Driver {

    /**
     * Prompts the user for the initial parameters for the cellular automaton.
     * 
     * @param args Not used.
     */
    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            int gridSize = keyReader.readInt("Enter grid size: ");
            double initialProbability = keyReader.readDouble("Enter initial probability: ");
            int minNeighbours = keyReader.readPositiveInt("Enter minimum neighbours: ");
            int maxNeighbours = keyReader.readInt(minNeighbours, Integer.MAX_VALUE, "Enter maximum neighbours: ");
            int maxGenerations = keyReader.readPositiveInt("Enter maximum generations: ");
            int modulus = keyReader.readPositiveInt("Enter modulus: ");
            String fileName = keyReader.readLine("Enter save file name: ");

            CellularAutomaton cellAutomaton = new CellularAutomaton(gridSize, initialProbability, minNeighbours, maxNeighbours, maxGenerations, modulus, fileName);
            while (cellAutomaton.createNextGeneration()) {}
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
        System.out.println("DONE.");
    }
}