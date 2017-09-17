package assignments.a2;

import java.util.ArrayList;

public class QuadraticEquation {
    private double quadraticCoefficient;
    private double linearCoefficient;
    private double constant;

    public QuadraticEquation(double quadraticCoefficient, double linearCoefficient, double constant) {
        this.quadraticCoefficient = quadraticCoefficient;
        this.linearCoefficient = linearCoefficient;
        this.constant = constant;
    }

    public ArrayList<Double> calculateRoots() {
        double radicand = Math.pow(linearCoefficient, 2) - 4 * quadraticCoefficient * constant;
        ArrayList<Double> roots = new ArrayList<>();
        if (radicand == 0)
            roots.add((-linearCoefficient)/(2 * quadraticCoefficient));
        if (radicand > 0) {
            roots.add((-linearCoefficient + Math.sqrt(radicand))/(2 * quadraticCoefficient));
            roots.add((-linearCoefficient - Math.sqrt(radicand))/(2 * quadraticCoefficient));
        }
        return roots;
    }
}