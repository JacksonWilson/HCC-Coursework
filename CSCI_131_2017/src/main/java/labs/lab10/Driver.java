package labs.lab10;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) {
        ArrayList<Agent> agentData = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("solditems.ser"))) {
            SoldItem item;
            
            while ((item = (SoldItem)ois.readObject()) != null) {
                int existingAgentIndex = -1;
                for (int i = 0; i < agentData.size(); i++) {
                    if (agentData.get(i).getLastName().equals(item.getAgentLastName())) {
                        existingAgentIndex = i;
                        break;
                    }
                }

                if (existingAgentIndex == -1) {
                    agentData.add(new Agent(item.getAgentLastName(), 1,
                        item.getItemType().equals("Truck") ? 1 : 0, 
                        item.getItemType().equals("Car") ? 1 : 0,
                        item.getProfit()));
                } else {
                    switch (item.getItemType()) {
                    case "Truck":
                        agentData.get(existingAgentIndex).addTruckSold();
                        break;
                    case "Car":
                        agentData.get(existingAgentIndex).addCarSold();
                        break;
                    default:
                        agentData.get(existingAgentIndex).addVehicleSold();
                        break;
                    }
                    agentData.get(existingAgentIndex).addProfit(item.getProfit());
                }
            }
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("agents.ser"))) {
            for (Agent agent : agentData) {
                oos.writeObject(agent);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("audit.txt"))) {
            int totalNumVehicles = 0;
            int totalNumCars = 0;
            int totalNumTrucks = 0;
            double totalProfit = 0;

            for (Agent agent : agentData) {
                totalNumVehicles += agent.getNumVehiclesSold();
                totalNumCars += agent.getNumCarsSold();
                totalNumTrucks += agent.getNumTrucksSold();
                totalProfit += agent.getProfit();
            }

            bw.write("Total number of vehicles sold: ");
            bw.write(totalNumVehicles);
            bw.newLine();
            bw.write("Total number of cars sold: ");
            bw.write(totalNumCars);
            bw.newLine();
            bw.write("Total number of trucks sold: ");
            bw.write(totalNumTrucks);
            bw.newLine();
            bw.write("Total number of agents: ");
            bw.write(agentData.size());
            bw.newLine();
            bw.write("Total profit: ");
            bw.write(NumberFormat.getCurrencyInstance().format(totalProfit));
            bw.newLine();
            bw.newLine();

            bw.write("    Agents    ");
            bw.newLine();
            bw.write("--------------");
            bw.newLine();

            for (Agent agent : agentData) {
                bw.write(agent.getLastName());
                bw.write(" sold ");
                bw.write(agent.getNumVehiclesSold());
                bw.write(" vehicles for a profit of ");
                bw.write(NumberFormat.getCurrencyInstance().format(agent.getProfit()));
                bw.newLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}