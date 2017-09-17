package assignments.a2;

public class SystemOfTwo {
    private double a1, a2, b1, b2, c1, c2;

    public SystemOfTwo(double a1, double a2, double b1, double b2, double c1, double c2) {
        this.a1 = a1;
        this.a2 = a2;
        this.b1 = b1;
        this.b2 = b2;
        this.c1 = c1;
        this.c2 = c2;
    }

    public Point getSolution() {
        double x = getDeterminate(c1, b1, c2, b2) / getDeterminate(a1, b1, a2, b2);
        double y = getDeterminate(a1, c1, a2, c2) / getDeterminate(a1, b1, a2, b2);
        return new Point(x, y);
    }

    private double getDeterminate(double a, double b, double c, double d) {
        return a * d - c * b;
    }
}