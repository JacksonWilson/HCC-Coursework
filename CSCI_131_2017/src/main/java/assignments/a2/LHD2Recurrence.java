package assignments.a2;

import java.text.DecimalFormat;

public class LHD2Recurrence {
    private double r1;
    private double r2;
    private double a1;
    private double a2;
    private double u;
    private double v;
    private double s1;
    private double s2;
    private DecimalFormat decimalFormat;

    public LHD2Recurrence() {
        decimalFormat = new DecimalFormat();
    }

    public String getSequenceAsString() {
        setDecimalFormat(2, 2);
        return decimalFormat.format(1);
    }
    
    public double getSequenceElement(int n) {
        return 0;
    }

    private void setDecimalFormat(int min, int max) {
        decimalFormat.setMinimumFractionDigits(min);
        decimalFormat.setMaximumFractionDigits(max);
    }
}