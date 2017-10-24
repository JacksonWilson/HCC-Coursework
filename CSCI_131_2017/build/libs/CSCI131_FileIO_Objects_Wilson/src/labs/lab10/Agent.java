package labs.lab10;

import java.io.Serializable;

public class Agent implements Serializable {
    public static final long serialVersionUID = 1L;
    
    private String lastName;
    private int numVehiclesSold;
    private int numTrucksSold;
    private int numCarsSold;
    private double profit;

    public Agent(String lastName, int numVehiclesSold, int numTrucksSold, int numCarsSold, double profit) {
        this.lastName = lastName;
        this.numVehiclesSold = numVehiclesSold;
        this.numTrucksSold = numTrucksSold;
        this.numCarsSold = numCarsSold;
        this.profit = profit;
    }

    public int getNumVehiclesSold() {
        return numVehiclesSold;
    }

    public void addVehicleSold() {
        this.numVehiclesSold++;
    }

    public int getNumTrucksSold() {
        return numTrucksSold;
    }

    public void addTruckSold() {
        addVehicleSold();
        this.numTrucksSold++;
    }

    public int getNumCarsSold() {
        return numCarsSold;
    }

    public void addCarSold() {
        addVehicleSold();
        this.numCarsSold++;
    }
    
    public double getProfit() {
        return profit;
    }

    public void addProfit(double amount) {
        this.profit += amount;
    }

    public String getLastName() {
        return lastName;
    }
}