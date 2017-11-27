package assignments.a5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Driver {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMdd");
    private static final String FILE_DATABASE = "C:/Users/jacks/dev/Visual Studio Code/Assignment5/res/assignments/a5/CompanyReport.accdb";
    private static final String FILE_CUST = "C:/Users/jacks/dev/Visual Studio Code/Assignment5/res/assignments/a5/update_cust.txt";
    private static final String FILE_EMPL = "C:/Users/jacks/dev/Visual Studio Code/Assignment5/res/assignments/a5/update_emp.txt";
    private static final String FILE_ERROR = "C:/Users/jacks/dev/Visual Studio Code/Assignment5/res/assignments/a5/db_update_errors.txt";

    private static void processCustUpdates(Connection connection, File custUpdateFile, File errorFile) throws IOException, SQLException {
        try (BufferedWriter bwErrorFile = new BufferedWriter(new FileWriter(errorFile, true))) {
            PreparedStatement pstmInsertCust = connection.prepareStatement("INSERT INTO Customers (Cust_ID, Company, Revenue_Group, Years) VALUES (?, ?, ?, ?)");
            PreparedStatement pstmUpdateCust = connection.prepareStatement("UPDATE Customers SET Cust_ID=?, Company=?, Revenue_Group=?, Years=? WHERE Cust_ID=?");
            PreparedStatement pstmEmplCust = connection.prepareStatement("SELECT Empl_ID FROM Employees WHERE (Primary_Cust_ID=? OR Secondary_Cust_ID=?)");
            PreparedStatement pstmSelectCust = connection.prepareStatement("SELECT Cust_ID FROM Customers WHERE Cust_ID=?");
            PreparedStatement pstmDeleteCust = connection.prepareStatement("DELETE FROM Customers WHERE CUST_ID=?");
            PreparedStatement pstmSelectRevenueGroup = connection.prepareStatement("SELECT Revenue_Groups.Group FROM Revenue_Groups WHERE Revenue_Groups.Low<=?");

            for (String[] u : getUpdateList(custUpdateFile)) { // u = custId, companyName, revenueAmount, years, maintenanceId
                boolean success = false;
                
                if (u.length == 2 && u[1].equals("D") && !isCustReferencedInEmpl(u[0], pstmEmplCust)) {
                    success = updateRow(pstmDeleteCust, u[0]) == 1;
                } else if (u.length == 5) {
                    if (u[4].equals("I") && !doesIdExist(u[0], pstmSelectCust)) {
                        success = updateRow(pstmInsertCust, u[0], u[1], getRevenueGroup(u[2], pstmSelectRevenueGroup), u[3]) == 1;
                    } else if (u[4].equals("U") && doesIdExist(u[0], pstmSelectCust)) {
                        success = updateRow(pstmUpdateCust, u[0], u[1], getRevenueGroup(u[2], pstmSelectRevenueGroup), u[3], u[0]) == 1;
                    }
                }
                
                if (!success) {
                    bwErrorFile.write(String.join(",", u));
                    bwErrorFile.newLine();
                }
            }
        }
    }

    private static void processEmplUpdates(Connection connection, File emplUpdateFile, File errorFile) throws IOException, SQLException {
        try (BufferedWriter bwErrorFile = new BufferedWriter(new FileWriter(errorFile, true))) {
            PreparedStatement pstmInsertEmpl = connection.prepareStatement("INSERT INTO Employees (Empl_ID, Last_Name, First_Name, Years_Service, State_Represented, Primary_Cust_ID, Secondary_Cust_ID) VALUES (?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement pstmUpdateEmpl = connection.prepareStatement("UPDATE Employees SET Empl_ID=?, Last_Name=?, First_Name=?, Years_Service=?, State_Represented=?, Primary_Cust_ID=?, Secondary_Cust_ID=? WHERE Empl_ID=?");
            PreparedStatement pstmSelectEmpl = connection.prepareStatement("SELECT Empl_ID FROM Employees WHERE Empl_ID=?");
            PreparedStatement pstmDeleteEmpl = connection.prepareStatement("DELETE FROM Employees WHERE Empl_ID=?");
            PreparedStatement pstmSelectState = connection.prepareStatement("SELECT State FROM Regions WHERE State=?");

            for (String[] u : getUpdateList(emplUpdateFile)) { // u = employeeId, lastName, firstName, partTimeYoS, fullTimeYoS, state, primaryCust, secondaryCust, maintenanceId
                boolean success = false;

                if (u.length == 2 && u[1].equals("D")) {
                    success = updateRow(pstmDeleteEmpl, u[0]) == 1;
                } else if (u.length == 9 && doesStateExist(u[5], pstmSelectState)) {
                    int emplYoS = calculateEmplYoS(u[3], u[4]);

                    if (u[7].equals("0") || emplYoS > 8) {
                        if (u[8].equals("I") && !doesIdExist(u[0], pstmSelectEmpl)) {
                            success = updateRow(pstmInsertEmpl, u[0], u[1], u[2], Integer.toString(emplYoS), u[5], u[6], u[7]) == 1;
                        } else if (u[8].equals("U") && doesIdExist(u[0], pstmSelectEmpl)) {
                            success = updateRow(pstmUpdateEmpl, u[0], u[1], u[2], Integer.toString(emplYoS), u[5], u[6], u[7], u[0]) == 1;
                        }
                    }
                }
                
                if (!success) {
                    bwErrorFile.write(String.join(",", u));
                    bwErrorFile.newLine();
                }
            }
        }
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:ucanaccess://" + FILE_DATABASE)) {
            File errorFile = new File(FILE_ERROR);
            if (errorFile.exists())
                try (FileWriter fw = new FileWriter(errorFile)) {} // Clear error file if it exists.

            File custUpdateFile = new File(FILE_CUST);
            if (custUpdateFile.exists())
                processCustUpdates(connection, custUpdateFile, errorFile);

            File emplUpdateFile = new File(FILE_EMPL);
            if (emplUpdateFile.exists())
                processEmplUpdates(connection, emplUpdateFile, errorFile);

            

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String[]> getUpdateList(File updateFile) throws IOException {
        ArrayList<String[]> updates = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(updateFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                updates.add(line.split(","));
            }
        }
        updateFile.renameTo(new File(updateFile.getPath().substring(0, updateFile.getPath().indexOf(".")) + "_" + (DATE_FORMAT.format(new Date())) + ".txt"));
        return updates;
    }

    private static String getRevenueGroup(String revenueAmount, PreparedStatement pstm) throws SQLException {
        pstm.setInt(1, Integer.parseInt(revenueAmount));
        ResultSet results = pstm.executeQuery();

        if (results.next()) {
            int group = results.getInt(1);
            while (results.next()) {
                if (results.getInt(1) < group)
                    group = results.getInt(1);
            }
            return Integer.toString(group);
        } else {
            return "0";
        }
    }

    private static int calculateEmplYoS(String partTimeYoS, String fullTimeYoS) {
        return (int)(Double.parseDouble(fullTimeYoS) + 0.25 * Double.parseDouble(partTimeYoS));
    }

    private static boolean isCustReferencedInEmpl(String custId, PreparedStatement pstm) throws SQLException {
        pstm.setString(1, custId);
        pstm.setString(2, custId);
        return pstm.executeQuery().next();
    }

    private static boolean doesIdExist(String id, PreparedStatement pstm) throws SQLException {
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }

    private static boolean doesStateExist(String state, PreparedStatement pstm) throws SQLException {
        pstm.setString(1, state);
        return pstm.executeQuery().next();
    }

    private static int updateRow(PreparedStatement pstm, String... fields) throws SQLException {
        for (int i = 0; i < fields.length; i++)
            pstm.setString(i + 1, fields[i]);
        return pstm.executeUpdate();
    }
}