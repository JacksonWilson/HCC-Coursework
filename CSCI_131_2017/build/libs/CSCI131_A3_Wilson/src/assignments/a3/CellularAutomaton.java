package assignments.a3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A simple implementation of cellular automaton that determines the next generation of
 * cells by checking if each cell has a certain range of neighbours alive around it in
 * the current generation.
 * 
 * @author Jackson Wilson
 */
public class CellularAutomaton {
    /**
     * The grid of cells in the current generation.
     */
    private boolean[][] grid;

    /**
     * The minimum number of neighbours required for a cell to be alive in the next
     * generation.
     */
    private int minNeighbours;

    /**
     * The maximum number of neighbours required for a cell to be alive in the next
     * generation.
     */
    private int maxNeighbours;

    /**
     * A counter to keep track of the current generation in the automaton.
     */
    private int currentGeneration;

    /**
     * The maximum number of generations to generate.
     */
    private int maxGeneration;

    /**
     * Determines which generations to append to the file
     */
    private int modulus;

    /**
     * Stores the state of each generation that modulos the maximum number of generations.
     */
    private File saveFile;

    /**
     * Initializes the initial parameters for the cellular automaton.
     * 
     * @param gridSize The number of rows and columns of the cell grid.
     * @param initialProbability The probability that a cell will not begin alive.
     * @param minNeighbours The minimum number of neighbours required to be alive for a cell to be alive in the next generation.
     * @param maxNeighbours The maximum number of neighbours required to be alive for a cell to be alive in the next generation.
     * @param maxGenerations The maximum number of generations allowed to be generated for this automaton.
     * @param modulus Determines which generations to append to the file.
     * @param fileName The name of the file that stores the state of each select generation.
     * @throws IllegalArgumentException If not all parameters are valid values for this implementation.
     */
    public CellularAutomaton(int gridSize, double initialProbability, int minNeighbours, int maxNeighbours, int maxGenerations, int modulus, String fileName)
            throws IllegalArgumentException {
        
        if (gridSize < 5 || gridSize > 50)
            throw new IllegalArgumentException("Grid size must be between 5 and 50.");

        if (initialProbability < 0.0 || initialProbability >= 1.0)
            throw new IllegalArgumentException("Initial probability must be between 0 and 1; exclusive of 1.");
        
        if (minNeighbours < 1 || minNeighbours > 4)
            throw new IllegalArgumentException("Minimum neighbours must be between 1 and 4 inclusively.");
        
        if (maxNeighbours < minNeighbours || maxNeighbours > 8)
            throw new IllegalArgumentException("Maximum neighbours must be between the minimum neighbours and 8 inclusively");

        if (modulus < 1 || modulus > maxGenerations)
            throw new IllegalArgumentException("Modulus must be between 1 and the maximum generations inclusively.");

        this.grid = new boolean[gridSize][gridSize];
        this.minNeighbours = minNeighbours;
        this.maxNeighbours = maxNeighbours;
        this.currentGeneration = 0;
        this.maxGeneration = maxGenerations;
        this.modulus = modulus;
        this.saveFile = new File(fileName);
        setupInitialState(initialProbability);
    }

    /**
     * Replaces the grid with a new one that is created by checking if the old cell
     * should be alive for the next generation.
     * 
     * @return Whether another generation can occur or not.
     */
    public boolean createNextGeneration() {
        boolean[][] nextGrid = new boolean[this.grid.length][this.grid[0].length];

        for (int i = 0; i < nextGrid.length; i++) {
            for (int j = 0; j < nextGrid[i].length; j++) {
                nextGrid[i][j] = shouldBeAlive(i, j);
            }
        }

        this.grid = nextGrid;
        this.currentGeneration++;

        if (this.currentGeneration == this.maxGeneration || this.currentGeneration % modulus == 0)
            save();

        return this.currentGeneration < this.maxGeneration;
    }

    /**
     * Appends the current generation to the save file as a grid. An asterisks signifies
     * an alive cell.
     */
    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.saveFile, true))) {
            bw.write("Generation: " + this.currentGeneration);
            bw.newLine();

            for (int i = 0; i < this.grid.length; i++) {
                for (int k = 0; k < this.grid.length; k++) {
                    bw.write("----");
                }
                bw.write("-");
                bw.newLine();
                for (int j = 0; j < this.grid[i].length; j++) {
                    bw.write(shouldBeAlive(i, j) ? "| * " : "|   ");
                }
                bw.write("|");
                bw.newLine();
            }
            for (int k = 0; k < this.grid.length; k++) {
                bw.write("----");
            }
            bw.write("-");
            bw.newLine();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Randomly populates the grid.
     * 
     * @param initialProbability
     */
    private void setupInitialState(double initialProbability) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++)
                grid[i][j] = Math.random() >= initialProbability;
        }
    }

    /**
     * Determines if the cell has between the minimum and maximum neighbours to survive.
     * 
     * @param i The i index of the grid.
     * @param j The j index of the grid.
     * @return Whether the cell has a vaild number of neighbours to survive or not.
     */
    private boolean shouldBeAlive(int i, int j) {
        int numAround = 0;

        if (i == 0) {
            if (grid[i + 1][j])
                numAround++;
            if (grid[grid.length - 1][j])
                numAround++;
        } else if (i == grid.length - 1) {
            if (grid[0][j])
                numAround++;
            if (grid[i - 1][j])
                numAround++;
        } else {
            if (grid[i + 1][j])
                numAround++;
            if (grid[i - 1][j])
                numAround++;
        }

        if (j == 0) {
            if (grid[i][j + 1])
                numAround++;
            if (grid[i][grid[i].length - 1])
                numAround++;
        } else if (j == grid[i].length - 1) {
            if (grid[i][0])
                numAround++;
            if (grid[i][j - 1])
                numAround++;
        } else {
            if (grid[i][j + 1])
                numAround++;
            if (grid[i][j - 1])
                numAround++;
        }

        return numAround >= minNeighbours && numAround <= maxNeighbours;
    }
}