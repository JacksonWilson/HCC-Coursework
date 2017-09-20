package assignments.a2;

import java.text.DecimalFormat;
import java.util.List;

/**
 * This represents a linearly homogeneous degree-2 recurrence relation of the form:
 * <p>a(n) = c1*a(n-1) + c2*a(n-2)
 * 
 * @author Jackson Wilson
 */
public class LHD2Recurrence {
    /**
    * The constant on the a(n-1) term.
    */
    private double r1;

    /**
    * The constant on the a(n-2) term.
    */
    private double r2;

    /**
     * The first element in the sequence.
     */
    private double a1;

    /**
     * The second element in the sequence.
     */
    private double a2;

    /**
     * A calculated constant in the explicit sequence.
     */
    private double u;

    /**
     * A calculated constant in the explicit sequence.
     */
    private double v;

    /**
     * The roots of this recursive relation.
     */
    private List<Double> roots;

    private DecimalFormat decimalFormat;

    /**
     * Constructs a second-degree linearly homogeneous recurrence relation.
     * 
     * @param r1 The a(n-1) constant.
     * @param r2 The a(n-2) constant.
     * @param a1 The first element in the sequence.
     * @param a2 The second element in the sequence.
     */
    public LHD2Recurrence(double r1, double r2, double a1, double a2) {
        this.r1 = r1;
        this.r2 = r2;
        this.a1 = a1;
        this.a2 = a2;
        determineExplictSequenceCharacteristics();
    }
    
    /**
     * a(n) = u (root_1 ^ n) + v (root_2 ^ n)
     * 
     * @return A formated String of the explicit sequence or why the formula was not generated.
     */
    public String getExplicitSequenceAsString() {
        if (roots.size() == 0)
            return "Complex roots - no formula generated";
        
        setDecimalFormat(2, 2);

        StringBuilder sb = new StringBuilder()
        .append("a(n) = ")
        .append(decimalFormat.format(u))
        .append("(")
        .append(decimalFormat.format(roots.get(0)))
        .append("^n) + ")
        .append(decimalFormat.format(v));
        
        if (roots.size() == 1)
            sb.append("(n)");
        
        sb.append("(")
        .append(decimalFormat.format(roots.get(roots.size() == 2 ? 1 : 0)))
        .append("^n)");

        return sb.toString();
    }
    
    /**
     * a(n) = r1 * a(n-1) + r2 * a(n-2)
     * 
     * @return A formated String of the recursive sequence.
     */
    public String getRecursiveSequenceAsString() {
        setDecimalFormat(2, 2);
        return "a(n) = " + decimalFormat.format(r1) + "a(n-1) + "
            + decimalFormat.format(r2) + "a(n-2) where a(1) = "
            + decimalFormat.format(a1) + " and a(2) = "+ decimalFormat.format(a2);
    }
    
    /**
     * Returns the element at n using the calculated explicit sequence.
     * 
     * @param n The sequence index.
     * @return The nth element's value: truncated to integer.
     */
    public double getExplicitSequenceElement(int n) {
        if (roots.size() == 1)
            return u * Math.pow(roots.get(0), n) + v * Math.pow(roots.get(0), n) * n;
        if (roots.size() == 2)
            return u * Math.pow(roots.get(0), n) + v * Math.pow(roots.get(1), n);
        return 0;
    }
    
    /**
     * Returns the element at n using the recursive sequence.
     * 
     * @param n The sequence index.
     * @return The nth element's value: truncated to integer.
     */
    public double getRecursiveSequenceElement(int n) {
        if (n == 1)
            return a1;
        if (n == 2)
            return a2;
        return r1 * getRecursiveSequenceElement(n - 1)
            + r2 * getRecursiveSequenceElement(n - 2);
    }

    /**
     * Generates a formatted String for sequence values between two indicies using
     * the calculated explict sequence.
     * 
     * @param start The first index in the sequence.
     * @param end The last index in the sequence.
     * @return A formatted String of all values between start and end of the explicit sequence, inclusive.
     */
    public String getExplicitSequenceElementsAsString(int start, int end) {
        if (roots.size() == 0)
            return "";
        
        setDecimalFormat(0, 0);
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            sb.append("a(")
                .append(i)
                .append(") = ")
                .append(decimalFormat.format(getExplicitSequenceElement(i)))
                .append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Generates a formatted String for sequence values between two indicies using
     * the recurisve sequence.
     * 
     * @param start The first index in the sequence.
     * @param end The last index in the sequence.
     * @return A formatted String of all values between start and end of the recursive sequence, inclusive.
     */
    public String getRecursiveSequenceElementsAsString(int start, int end) {
        setDecimalFormat(0, 0);
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            sb.append("a(")
                .append(i)
                .append(") = ")
                .append(decimalFormat.format(getRecursiveSequenceElement(i)))
                .append("\n");
        }
        return sb.toString();
    }

    private void setDecimalFormat(int min, int max) {
        decimalFormat.setMinimumFractionDigits(min);
        decimalFormat.setMaximumFractionDigits(max);
    }

    /**
     * Uses the QuadraticEquation and SystemOfTwo classes to determine the
     * characteristics of the explict sequence.
     */
    private void determineExplictSequenceCharacteristics() {
        this.roots = new QuadraticEquation(1, -r1, -r2).calculateRoots();
        this.decimalFormat = new DecimalFormat();
        
        if (roots.size() == 2) {
            Point solution = new SystemOfTwo(roots.get(0), Math.pow(roots.get(0), 2),
                roots.get(1), Math.pow(roots.get(1), 2), a1, a2).getSolution();
            this.u = solution.getX();
            this.v = solution.getY();
        } else if (roots.size() == 1) {
            Point solution = new SystemOfTwo(roots.get(0), Math.pow(roots.get(0), 2),
                roots.get(0), Math.pow(roots.get(0), 2) * 2, a1, a2).getSolution();
            this.u = solution.getX();
            this.v = solution.getY();
        }
    }
}