package assignments.a2;

import java.text.DecimalFormat;
import java.util.List;

public class LHD2Recurrence {
    private double r1;
    private double r2;
    private double a1;
    private double a2;
    private double u;
    private double v;
    private List<Double> roots;
    private DecimalFormat decimalFormat;

    public LHD2Recurrence(double r1, double r2, double a1, double a2) {
        this.r1 = r1;
        this.r2 = r2;
        this.a1 = a1;
        this.a2 = a2;
        this.roots = new QuadraticEquation(1, -r1, -r2).calculateRoots();
        this.decimalFormat = new DecimalFormat();
        
        if (roots.size() == 2) {
            Point solution = new SystemOfTwo(roots.get(0), Math.pow(roots.get(0), 2), roots.get(1), Math.pow(roots.get(1), 2), a1, a2).getSolution();
            this.u = solution.getX();
            this.v = solution.getY();
        } else if (roots.size() == 1) {
            Point solution = new SystemOfTwo(roots.get(0), Math.pow(roots.get(0), 2), roots.get(0), Math.pow(roots.get(0), 2) * 2, a1, a2).getSolution();
            this.u = solution.getX();
            this.v = solution.getY();
        }
    }
    
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
    
    public String getRecursiveSequenceAsString() {
        setDecimalFormat(2, 2);
        return "a(n) = " + decimalFormat.format(r1) + "a(n-1) + "
            + decimalFormat.format(r2) + "a(n-2) where a(1) = "
            + decimalFormat.format(a1) + " and a(2) = "+ decimalFormat.format(a2);
    }
    
    public double getExplicitSequenceElement(int n) {
        if (roots.size() == 1)
            return u * Math.pow(roots.get(0), n) + v * Math.pow(roots.get(0), n) * n;
        if (roots.size() == 2)
            return u * Math.pow(roots.get(0), n) + v * Math.pow(roots.get(1), n);
        return 0;
    }
    
    public double getRecursiveSequenceElement(int n) {
        if (n == 1)
            return a1;
        if (n == 2)
            return a2;
        return r1 * getRecursiveSequenceElement(n - 1)
            + r2 * getRecursiveSequenceElement(n - 2);
    }

    public String getExplicitSequenceElements(int start, int end) {
        if (roots.size() == 0)
            return "";
        
        setDecimalFormat(0, 0);
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            sb.append("a(" + i + ") = "
                + decimalFormat.format(getExplicitSequenceElement(i)) + "\n");
        }
        return sb.toString();
    }
    
    public String getRecursiveSequenceElements(int start, int end) {
        setDecimalFormat(0, 0);
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            sb.append("a(" + i + ") = "
                + decimalFormat.format(getRecursiveSequenceElement(i)) + "\n");
        }
        return sb.toString();
    }

    private void setDecimalFormat(int min, int max) {
        decimalFormat.setMinimumFractionDigits(min);
        decimalFormat.setMaximumFractionDigits(max);
    }
}