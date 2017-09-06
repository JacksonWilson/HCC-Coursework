package labs.lab5;

/**
 * A line segment consisting of two points.
 * 
 * @author Jackson Wilson
 */
public class Segment {
    /**
     * An endpoint.
     */
    private Point p1;
    /**
     * An endpoint.
     */
    private Point p2;

    /**
     * Constructs a new line segment from two points.
     * 
     * @param p1 an endpoint.
     * @param p2 an endpoint.
     */
    public Segment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Determines if a point is colliding with this line segment with a 0.001% tollerance.
     * 
     * @param p a point potentially colliding with this line segment.
     * @return whether the point collides with this line segment or not.
     */
    public boolean doesCollide(Point p) {
        return p.getX() >= Math.min(p1.getX(), p2.getX())
            && p.getX() <= Math.max(p1.getX(), p2.getX())
            && Math.abs(p.getY() - getYFromEquation(p.getX())) <= 0.00001;
    }

    /**
     * Returns the y-coordinate poisition associated with the given x coordinate from the
     * line equation of this segment.
     * 
     * @param x the x-coordinate of a point.
     * @return the y-coordinate calculated from the y=mx+b equation of this segment.
     */
    private double getYFromEquation(double x) {
        return (p1.getY() - p2.getY()) / (p1.getX() - p2.getX()) * (x - p1.getX()) + p1.getY();
    }
}