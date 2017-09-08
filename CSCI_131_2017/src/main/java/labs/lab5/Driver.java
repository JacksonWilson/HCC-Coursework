package labs.lab5;

import java.io.IOException;

import utils.KeyboardReader;

public class Driver {

    public static void main(String[] args) {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            double x = keyReader.readDouble("Enter x-coordinate of the first endpoint of the segment: ");
            double y = keyReader.readDouble("Enter y-coordinate of the first endpoint of the segment: ");
            Point p1 = new Point(x, y);

            x = keyReader.readDouble("Enter x-coordinate of the second endpoint of the segment: ");
            y = keyReader.readDouble("Enter y-coordinate of the second endpoint of the segment: ");
            Point p2 = new Point(x, y);

            x = keyReader.readDouble("Enter x-coordinate of the (potentially) colliding point: ");
            y = keyReader.readDouble("Enter y-coordinate of the (potentially) colliding point: ");
            Point p3 = new Point(x, y);
            
            Segment segment = new Segment(p1, p2);
            System.out.println("\nThe point does" + (segment.doesCollide(p3) ? " " : " not ") + "collide with the segment.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}