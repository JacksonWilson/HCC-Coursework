package labs.lab19;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.KeyboardReader;

public class Driver {
    private static final String FILE_CAR_DATABASE = "C:/Users/02104/repos/HCC-Coursework/CSCI_131_2017/res/labs/lab19CarTracker.accdb";
    private static KeyboardReader keyReader;

    static {
        keyReader = new KeyboardReader(System.in);
    }

    public static void main(String[] args) {
        try (Statement statement = DriverManager.getConnection("jdbc:ucanaccess://" + FILE_CAR_DATABASE).createStatement()) {
            // Display the Model, Body_Type, and Color for all Mistsubushi vehicles
            ResultSet results = statement.executeQuery("SELECT Model, Body_Type, Color FROM Cars WHERE Make = \"Mitsubishi\"");
            printResults(results);

            // Display the Model, Body_Type, and Color for all Mistsubushi vehicles, but do not display any duplicate rows
            results = statement.executeQuery("SELECT DISTINCT Model, Body_Type, Color FROM Cars WHERE Make = \"Mitsubishi\"");
            printResults(results);

            // Display the Last_Name, Salary, Make, and Model for all owned cars - use a standard join to do this
            results = statement.executeQuery("SELECT Last_Name, Salary, Make, Model FROM Cars INNER JOIN Owners ON Cars.VIN = Owners.VIN");
            printResults(results);

            // Display the Last_Name, Salary, Make, and Model for all cars owned by anyone between the ages of 25 and 35 inclusive - use the BETWEEN operator to do this
            results = statement.executeQuery("SELECT Last_Name, Salary, Make, Model FROM Cars INNER JOIN Owners ON Cars.VIN = Owners.VIN");
            printResults(results);

            // Display the Make, Model, and Color for all vehicles that are for sale (i.e. not owned) - use the IN operator, the NOT operator, and a subquery to do this
            results = statement.executeQuery("SELECT Make, Model, Color FROM Cars WHERE Cars.VIN NOT IN (SELECT Owners.VIN FROM Owners)");
            printResults(results);

            // Allow the user to enter two colors - change all of the cars that are the first color to the second color and display the number of rows updated to the user
            String color1 = keyReader.readLine("Enter old color: ");
            String color2 = keyReader.readLine("Enter new color: ");
            int numUpdated = statement.executeUpdate("UPDATE Cars SET VIN = \"" + color2 + "\" WHERE VIN=\"" + color1 + "\"");
            System.out.println("Number of records updated from the Cars table: " + numUpdated);

            // Allow the user to enter a VIN to delete a record from the Cars table. Prior to deleting the record, ensure that it is not referenced in the Owners table. Display an appropriate message detailing the result (which could also be that there was no such record to delete on the Cars table)
            String vin = keyReader.readLine("Enter VIN to delete: ");
            if (!statement.executeQuery("SELECT VIN FROM Owners WHERE VIN=" + vin).next()) {
                numUpdated = statement.executeUpdate("DELETE FROM Cars WHERE VIN=" + vin);
                System.out.println("Number of records deleted from the Cars table: " + numUpdated);
            } else {
                System.out.println("Cannot delete because VIN is referenced in the Owners table.");
            }

            // Allow the user to enter the appropriate fields (but not the VIN) for a record to be added to the Cars table.
            String make = keyReader.readLine("Enter make: ");
            String model = keyReader.readLine("Enter model: ");
            String bodyType = keyReader.readLine("Enter body type: ");
            String numDoors = keyReader.readLine("Enter number of doors: ");
            String color = keyReader.readLine("Enter color: ");
            int nextVin = findNextVIN(statement, make);

            if (statement.executeUpdate(String.format("INSERT INTO Cars VALUES (%d, %s, %s, %s, %s, %s)", nextVin, make, model, bodyType, numDoors, color)) != 0) {
                System.out.println("Successfully added new record.");
            } else {
                System.out.println("Failed to add new record.");
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void printResults(ResultSet results) throws SQLException {
        while (results.next()) {
            for (int r = 1; r < results.getMetaData().getColumnCount(); r++) {
                System.out.print(results.getString(r) + "\t");
            }
            System.out.println();
        }
    }

    private static int findNextVIN(Statement statement, String make) throws SQLException {
        ResultSet results = statement.executeQuery("SELECT VIN FROM Cars WHERE Make=\"" + make + "\"");
        int vin = -1;
        while (results.next()) {
            if (results.getInt(0) > vin)
                vin = results.getInt(0);
        }

        if (vin == -1) {
            results = statement.executeQuery("SELECT VIN FROM CARS");

            while (results.next()) {
                if (results.getInt(0) > vin)
                    vin = results.getInt(0);
            }

            if (vin == -1)
                return vin;

            return (vin / 100) * 100;
        }

        return vin + 1;
    }
}