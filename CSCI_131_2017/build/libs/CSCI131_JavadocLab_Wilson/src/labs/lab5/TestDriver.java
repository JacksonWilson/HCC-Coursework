package labs.lab5;

public class TestDriver {
   public static void main(String[] args) {
      Segment segment = new Segment(new Point(-1, -1), new Point(1, 1));
      System.out.println("Directly on segment (0, 0) should be true:\t\t"
        + (segment.doesCollide(new Point(0, 0)) ? "PASSED" : "FAILED"));
      System.out.println("Within tollerance (0, 0.000001) should be true:\t\t"
        + (segment.doesCollide(new Point(0, 0.000001)) ? "PASSED" : "FAILED"));
      System.out.println("Not within tollerance (0, 0.001) should be false:\t"
        + (!segment.doesCollide(new Point(0, 0.001)) ? "PASSED" : "FAILED"));
      System.out.println("On but outside domain (2, 2) should be false:\t\t"
        + (!segment.doesCollide(new Point(2, 2)) ? "PASSED" : "FAILED"));
   }
}  