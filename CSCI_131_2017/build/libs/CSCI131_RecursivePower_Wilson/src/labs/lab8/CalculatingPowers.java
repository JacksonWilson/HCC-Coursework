package labs.lab8;

import java.text.DecimalFormat;

public class CalculatingPowers {
    private double base;
    private int exponent;
    private DecimalFormat decimalFormat;

    public CalculatingPowers() {
        setDecimalFormatting(3, 3);
        setToDefault();
    }

    public CalculatingPowers(double base, int exponent) {
        try {
            setValues(base, exponent);
        } catch (ZeroException ze) {
            System.out.println("ZeroException: " + ze.getMessage());
        }
    }

    public void setDecimalFormatting(int min, int max) {
        decimalFormat = new DecimalFormat();
        decimalFormat.setMinimumFractionDigits(min);
        decimalFormat.setMaximumFractionDigits(max);
    }

    public void setValues(double base, int exponent) throws ZeroException {
        if (base == 0 && exponent == 0) {
            setToDefault();
            throw new ZeroException();
        } else {
            this.base = base;
            this.exponent = exponent;
        }
    }

    public String getBaseAsString() {
        return decimalFormat.format(base);
    }

    public String getExponentAsString() {
        return Integer.toString(exponent);
    }

    public String calculatePowerAsString() {
        if (exponent > 0)
            return decimalFormat.format(calculateWithPositiveExponent(exponent));
        if (exponent < 0)
            return decimalFormat.format(calculateWithNegativeExponent(exponent));
        return "1";
    }

    private double calculateWithPositiveExponent(double exponent) {
        if (exponent == 0)
            return 1;
        return calculateWithPositiveExponent(exponent - 1) * base;
    }

    private double calculateWithNegativeExponent(double exponent) {
        if (exponent == 0)
            return 1;
        return calculateWithNegativeExponent(exponent + 1) * (1.0 / base);
    }

    private void setToDefault() {
        base = 1;
        exponent = 1;
    }
}