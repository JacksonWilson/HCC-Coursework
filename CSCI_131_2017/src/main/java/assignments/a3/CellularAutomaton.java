package assignments.a3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CellularAutomaton {
    private boolean[][] grid;
    private int minNeighbours;
    private int maxNeighbours;
    private int currentGeneration;
    private int maxGeneration;
    private int modulus;
    private File saveFile;

    public CellularAutomaton(int gridSize, double initialProbability, int minNeighbours, int maxNeighbours, int maxGenerations, int modulus, String fileName) throws IllegalArgumentException {
        
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

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.saveFile, true))) {
            bw.write("Generation: " + this.currentGeneration);
            bw.newLine();

            for (int i = 0; i < this.grid.length * 5; i++) {
                for (int j = 0; j < this.grid[0].length * 5; j++) {
                    if (i % 2 == 1 || j % 2 == 1) {
                        bw.write(" ");
                    } else {
                        if (i % 5 == 0) {
                            bw.write("-");
                        } else if (j % 5 == 0) {
                            bw.write("|");
                        } else {
                            bw.write("*");
                        }
                    }
                }
                bw.newLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void setupInitialState(double initialProbability) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = Math.random() >= initialProbability;
            }
        }
    }

    private boolean shouldBeAlive(int i, int j) {
        int numAround = 0;

        if (i == 0) {
            if (grid[i + 1][j])
                numAround++;
            if (grid[grid.length - 1][j])
                numAround++;
        } else if (i == grid.length - 1) {
            if (grid[i - 1][j])
                numAround++;
            if (grid[0][j])
                numAround++;
        } else {
            if (grid[i - 1][j])
                numAround++;
            if (grid[i + 1][j])
                numAround++;
        }

        if (j == 0) {
            if (grid[i][j + 1])
                numAround++;
            if (grid[i][grid[i].length - 1])
                numAround++;
        } else if (j == grid[i].length - 1) {
            if (grid[i][j - 1])
                numAround++;
            if (grid[i][0])
                numAround++;
        } else {
            if (grid[i][j - 1])
                numAround++;
            if (grid[i][j + 1])
                numAround++;
        }

        return numAround >= minNeighbours && numAround <= maxNeighbours;
    }
}