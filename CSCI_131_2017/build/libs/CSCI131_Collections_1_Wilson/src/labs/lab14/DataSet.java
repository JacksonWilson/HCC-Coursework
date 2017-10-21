package labs.lab14;

import java.util.LinkedList;

public class DataSet {
    private LinkedList<Integer> primaryScores;
    private LinkedList<Integer> secondaryScores;
    private NonVerticalLine model;

    public DataSet(LinkedList<Integer> primaryScores, LinkedList<Integer> secondaryScores) {
        this.primaryScores = primaryScores;
        this.secondaryScores = secondaryScores;
        this.model = new NonVerticalLine(
            LinearRegression.getSlope(primaryScores, secondaryScores),
            LinearRegression.getYIntercept(primaryScores, secondaryScores));
    }

    public LinkedList<Integer> getPrimaryScores() {
        return primaryScores;
    }

    public LinkedList<Integer> getSecondaryScores() {
        return secondaryScores;
    }

    public NonVerticalLine getModel() {
        return model;
    }
}