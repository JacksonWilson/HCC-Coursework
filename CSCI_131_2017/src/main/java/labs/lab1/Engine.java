package labs.lab1;

/**
 * Defines an Engine, a type of Vehicular Component.
 * 
 * @author Jackson Wilson
 */
public class Engine extends VehicularComponent {
    private static final double c;
    static {
        c = 0.7854; // pi / 4;
    }

    private double b;
    private double S;
    private int N;

    /**
     * Sets instance values and calculates engine displacement (cubic measurement).
     * 
     * @param b Length of cylinder bore (linear measurement).
     * @param S Length of stroke (linear measurement).
     * @param N Number of cylinders (unitless measurement).
     */
    public Engine(double b, double S, int N) {
        this.b = b;
        this.S = S;
        this.N = N;
    }
    
    /**
     * Calculates the engine displacement using the engine displacement formula:
     * <p>pi / 4 * Bore^2 * Stroke * Number of Cylinders.
     * 
     * @return Engine Displacement
     */
    public double getEngineDisplacement() {
        return c * b * b * S * N;
    }
    
    public double getCylinderBoreDiameter() {
        return b;
    }
    
    public double getStrokeLength() {
        return S;
    }
    
    public int getNumCylinder() {
        return N;
    }
    
    @Override
    public String getComponentDetails() {
        return N + " Cylinder Engine (bore: " + b + " units, stroke: " + S + " units)";
    }
    
    @Override
    public String toString() {
        return "Number of cylinders: " + N + " Bore diameter: " + b + " Stroke length: " + S;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (o instanceof Engine)
            return this.N == ((Engine)o).N;
        return false;
    }
}