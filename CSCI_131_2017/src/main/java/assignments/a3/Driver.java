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
            int gridSize = keyReader.readInt(5, 50, "Enter grid size: ");
            double initialProbability = keyReader.readDouble(0, 1, "Enter initial probability: ");
            int minNeighbours = keyReader.readInt(1, 4, "Enter minimum neighbours: ");
            int maxNeighbours = keyReader.readInt(minNeighbours, 8, "Enter maximum neighbours: ");
            int maxGenerations = keyReader.readInt(1, Integer.MAX_VALUE, "Enter maximum generations: ");
            int modulus = keyReader.readInt(1, maxGenerations, "Enter modulus: ");
            String fileName = keyReader.readLine("Enter save file name: ");

            CellularAutomaton cellAutomaton = new CellularAutomaton(gridSize, initialProbability, minNeighbours, maxNeighbours, maxGenerations, modulus, fileName);
            while (cellAutomaton.createNextGeneration()) {}

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}