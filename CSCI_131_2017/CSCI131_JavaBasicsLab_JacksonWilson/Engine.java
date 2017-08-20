
public class Engine extends VehicularComponent {
    private static final double c = 0.7854; // pi / 4;
    private double E;
    private double b;
    private double S;
    private int N;

    public Engine(double b, double S, int N) {
        this.b = b;
        this.S = S;
        this.N = N;
        this.E = c * b * b * S * N;
    }

    public double getEngineDisplacement() {
        return E;
    }

    public double getCylinderBoreLength() {
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
        return getComponentDetails() + " has an engine displacement of "
            + getEngineDisplacement() + " units squared.";
    }
}