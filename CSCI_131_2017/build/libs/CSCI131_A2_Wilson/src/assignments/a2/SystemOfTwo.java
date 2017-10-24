package assignments.a2;

/**
 * Solves a system of equations for two unknown variables where the equations are quadratic.
 * 
 * @author Jackson Wilson
 */
public class SystemOfTwo {
    private double a1, a2, b1, b2, c1, c2;

    /**
    * Constructs a system of equations for two unknown variables.
    *
    * @param a1 The quadratic coefficient of the first equation.
    * @param a2 The quadratic coefficient of the second equation.
    * @param b1 The linear coefficient of the first equation.
    * @param b2 The linear coefficient of the second equation.
    * @param c1 The constant of the first equation.
    * @param c2 The constant of the second equation.
    */
    public SystemOfTwo(double a1, double a2, double b1, double b2, double c1, double c2) {
        this.a1 = a1;
        this.a2 = a2;
        this.b1 = b1;
        this.b2 = b2;
        this.c1 = c1;
        this.c2 = c2;
    }

    /**
    * Determines the solution by using Cramer's rule.
    * 
    * @return The solution as a 2D point.
    */
    public Point getSolution() {
        double x = getDeterminate(c1, b1, c2, b2) / getDeterminate(a1, b1, a2, b2);
        double y = getDeterminate(a1, c1, a2, c2) / getDeterminate(a1, b1, a2, b2);
        return new Point(x, y);
    }

    private double getDeterminate(double a, double b, double c, double d) {
        return a * d - c * b;
    }
}