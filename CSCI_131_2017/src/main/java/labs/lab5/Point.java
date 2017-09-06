package labs.lab5;

/**
 * A class to represent a two dimensional point.
 * 
 * @author Jackson Wilson
 */
public class Point {
    /**
     * The x-coordinate.
     */
    private double x;
    /**
     * The y-coordinate.
     */
    private double y;

    /**
     * Cunstructs a new point with the given x and y coordinates.
     * 
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y-coordinate.
     */
    public double getY() {
        return y;
    }
}