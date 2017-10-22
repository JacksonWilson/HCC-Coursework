package labs.lab14;

public class NonVerticalLine {
    private double slope;
    private double yIntercept;

    public NonVerticalLine(double slope, double yIntercept) {
        this.slope = slope;
        this.yIntercept = yIntercept;
    }

    public double getSlope() {
        return slope;
    }

    public double getYIntercept() {
        return yIntercept;
    }

    public double getXValue(double x) {
        return x * slope + yIntercept;
    }
}