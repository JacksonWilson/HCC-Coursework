package labs.lab19;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Driver {
    private static String FILE_CAR_DATABASE = "C:/Users/02104/repos/HCC-Coursework/CSCI_131_2017/res/labs/lab19CarTracker.accdb";

    public static void main(String[] args) {
        try (Statement statement = DriverManager.getConnection("jdbc:ucanaccess://" + FILE_CAR_DATABASE).createStatement()) {
            ResultSet results = statement.executeQuery("SELECT Model, Body_Type, Color FROM Cars WHERE Make = \"Mistsubushi\"");
            printResults(results);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
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
}