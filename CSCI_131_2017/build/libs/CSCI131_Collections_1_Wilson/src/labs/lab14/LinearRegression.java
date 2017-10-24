package labs.lab14;

import java.util.LinkedList;

public class LinearRegression {
    
    public static double getSlope(LinkedList<Integer> xValues, LinkedList<Integer> yValues) {
        int sumX = 0;
        int sumY = 0;
        int sumProd = 0;
        int sumX2 = 0;

        for (int i = 0; i < xValues.size(); i++) {
            sumProd += xValues.get(i) * yValues.get(i);
        }

        for (Integer x : xValues) {
            sumX += x;
            sumX2 += x * x;
        }

        for (Integer y : yValues) {
            sumY += y;
        }

        return (double)(xValues.size() * sumProd - sumX * sumY) / (double)(xValues.size() * sumX2 - sumX * sumX);
    }

    public static double getYIntercept(LinkedList<Integer> xValues, LinkedList<Integer> yValues) {
        int sumX = 0;
        for (Integer x : xValues)
            sumX += x;

        int sumY = 0;
        for (Integer y : yValues)
            sumY += y;

        return ((double)sumY / yValues.size()) - (getSlope(xValues, yValues) * ((double)sumX / xValues.size()));
    }
}