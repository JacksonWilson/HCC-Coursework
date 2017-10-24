package assignments.a2;

import java.util.ArrayList;

/**
 * A quadratic equation of the form:
 * <p>a * x^2 + b * x + c = 0
 * 
 * @author Jackson Wilson
 */
public class QuadraticEquation {
    private double quadraticCoefficient;
    private double linearCoefficient;
    private double constant;

    /**
    * Constructs a quadratic equation of the form:
    * <p>a * x^2 + b * x + c = 0
    *
    * @param quadraticCoefficient a
    * @param linearCoefficient b
    * @param constant c
    */
    public QuadraticEquation(double quadraticCoefficient, double linearCoefficient, double constant) {
        this.quadraticCoefficient = quadraticCoefficient;
        this.linearCoefficient = linearCoefficient;
        this.constant = constant;
    }

    /**
    * Calculates the roots for this quadratic equation using the quadratic formula.
    *
    * @return A list of the roots. Either 0, 1, or 2 roots possible.
    */
    public ArrayList<Double> calculateRoots() {
        double radicand = Math.pow(linearCoefficient, 2) - 4 * quadraticCoefficient * constant;
        ArrayList<Double> roots = new ArrayList<>();
        if (radicand == 0)
            roots.add((-linearCoefficient)/(2 * quadraticCoefficient));
        else if (radicand > 0) {
            roots.add((-linearCoefficient + Math.sqrt(radicand))/(2 * quadraticCoefficient));
            roots.add((-linearCoefficient - Math.sqrt(radicand))/(2 * quadraticCoefficient));
        }
        return roots;
    }
}