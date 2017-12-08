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
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import utils.KeyboardReader;

/**
 * CSCI 131 - Assignment 5 (JDBC)
 * 
 * Objectives:
 *  - Implement standard SQL functionality for creation, updating, inserting, and deleting of records
 *  - Read files and update tables accordingly
 *  - Continue to devlelop File I/O skills
 *  - Implement report processing methodology
 * 
 * @author Jackson Wilson
 */
public class Driver {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMdd");
    private static final String FILE_DATABASE = "CompanyReport.accdb";
    private static final String FILE_CUST = "update_cust.txt";
    private static final String FILE_EMPL = "update_emp.txt";
    private static final String FILE_ERROR = "db_update_errors.txt";

    private static KeyboardReader keyReader;

    static {
        keyReader = new KeyboardReader(System.in);
    }

    /**
     * Establishes database connection, processes update files, and manages menu loop.
     * 
     * @param args Not used.
     */
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:ucanaccess://" + FILE_DATABASE)) {
            File errorFile = new File(FILE_ERROR);

            ArrayList<File> processedFiles = new ArrayList<>();
            int numErrorRecords = 0;

            // Update customers table
            File custUpdateFile = new File(FILE_CUST);
            if (custUpdateFile.exists()) {
                numErrorRecords += processCustUpdates(connection, custUpdateFile, errorFile);
                processedFiles.add(custUpdateFile);
            }

            // Update employees table
            File emplUpdateFile = new File(FILE_EMPL);
            if (emplUpdateFile.exists()) {
                numErrorRecords += processEmplUpdates(connection, emplUpdateFile, errorFile);
                processedFiles.add(emplUpdateFile);
            }

            // Begin menu
            Statement statement = connection.createStatement();
            PreparedStatement pstmSelectEmpl = connection.prepareStatement("SELECT * FROM Employees WHERE Empl_ID=?");
            PreparedStatement pstmSelectRegionFromState = connection.prepareStatement("SELECT Region FROM Regions WHERE State=?");
            PreparedStatement pstmSelectCompanyFromId = connection.prepareStatement("SELECT Company FROM Customers WHERE Cust_ID=?");
            PreparedStatement pstmSelectCust = connection.prepareStatement("SELECT * FROM Customers WHERE Cust_ID=?");
            PreparedStatement pstmSelectEmployeeFromCustId = connection.prepareStatement("SELECT Last_Name, First_Name FROM Employees WHERE (Primary_Cust_ID=? OR Secondary_Cust_ID=?)");
            PreparedStatement pstmSelectRevenueRange = connection.prepareStatement("SELECT Low, High FROM Revenue_Groups WHERE Revenue_Groups.Group=?");
            
            int numEmployeeRecords = 0;
            int selection;
            do  {
                System.out.println();
                switch (selection = getUserSelection()) {
                case 1:
                    displayEmployeeInfo(pstmSelectEmpl, pstmSelectRegionFromState, pstmSelectCompanyFromId);
                    break;
                case 2:
                    displayCustomerInfo(pstmSelectCust, pstmSelectRevenueRange, pstmSelectEmployeeFromCustId);
                    break;
                case 3:
                    displayRegions(statement);
                    break;
                case 4:
                    generateEmployeeReport(statement);
                    numEmployeeRecords++;
                    break;
                case 5:
                    generateLog(processedFiles, numErrorRecords, numEmployeeRecords);
                    break;
                }
            } while (selection != 5);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Processes the customer updates.
     * 
     * @param connection The database connection
     * @param custUpdateFile The update file for customer updates
     * @param errorFile The error file to write errors to
     * @return The number of error records
     * @throws IOException if an IOException occurs
     * @throws SQLException if a database error occurs
     */
    private static int processCustUpdates(Connection connection, File custUpdateFile, File errorFile) throws IOException, SQLException {
        int numErrorRecords = 0;
        try (BufferedWriter bwErrorFile = new BufferedWriter(new FileWriter(errorFile, true))) {
            PreparedStatement pstmInsertCust = connection.prepareStatement("INSERT INTO Customers (Cust_ID, Company, Revenue_Group, Years) VALUES (?, ?, ?, ?)");
            PreparedStatement pstmUpdateCust = connection.prepareStatement("UPDATE Customers SET Cust_ID=?, Company=?, Revenue_Group=?, Years=? WHERE Cust_ID=?");
            PreparedStatement pstmEmplCust = connection.prepareStatement("SELECT Empl_ID FROM Employees WHERE (Primary_Cust_ID=? OR Secondary_Cust_ID=?)");
            PreparedStatement pstmSelectCust = connection.prepareStatement("SELECT Cust_ID FROM Customers WHERE Cust_ID=?");
            PreparedStatement pstmDeleteCust = connection.prepareStatement("DELETE FROM Customers WHERE CUST_ID=?");
            PreparedStatement pstmSelectRevenueGroup = connection.prepareStatement("SELECT Revenue_Groups.Group FROM Revenue_Groups WHERE Revenue_Groups.Low<=?");
            
            for (String[] u : getUpdateList(custUpdateFile)) { // u = custId, companyName, revenueAmount, years, maintenanceId
                boolean success = false;
                
                if (u.length != 0) {
                    switch (u[u.length - 1]) {
                    case "D":
                        success = u.length == 2 && !isCustReferencedInEmpl(u[0], pstmEmplCust)
                            && updateRow(pstmDeleteCust, u[0]) == 1;
                        break;
                    case "I":
                        success = u.length == 5 && !doesIdExist(u[0], pstmSelectCust)
                            && updateRow(pstmInsertCust, u[0], u[1], getRevenueGroup(u[2], pstmSelectRevenueGroup), u[3]) == 1;
                        break;
                    case "U":
                        success = u.length == 5 && doesIdExist(u[0], pstmSelectCust)
                            && updateRow(pstmUpdateCust, u[0], u[1], getRevenueGroup(u[2], pstmSelectRevenueGroup), u[3], u[0]) == 1;
                        break;
                    }
                }
                
                if (!success) {
                    bwErrorFile.write(String.join(",", u));
                    bwErrorFile.newLine();
                    numErrorRecords++;
                }
            }
        }
        return numErrorRecords;
    }

    /**
     * Processes the employee updates.
     * 
     * @param connection The database connection
     * @param emplUpdateFile The update file for employee updates
     * @param errorFile The error file to write errors to
     * @return The number of error records
     * @throws IOException if an IOException occurs
     * @throws SQLException if a database error occurs
     */
    private static int processEmplUpdates(Connection connection, File emplUpdateFile, File errorFile) throws IOException, SQLException {
        int numErrorRecords = 0;
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
                    numErrorRecords++;
                }
            }
        }
        return numErrorRecords;
    }

    /**
     * Displays employee information after prompting user for employee ID.
     * 
     * @param pstmSelectEmpl Prepared statement to query for employee from employee ID
     * @param pstmSelectRegionFromState Prepared statement to query for region from a state
     * @param pstmSelectCompanyFromId Prepared statement to query for company name from customer ID
     * @throws IOException if an IOException occurs
     * @throws SQLException if a database error occurs
     */
    private static void displayEmployeeInfo(PreparedStatement pstmSelectEmpl, PreparedStatement pstmSelectRegionFromState, PreparedStatement pstmSelectCompanyFromId) throws SQLException, IOException {
        String emplId = keyReader.readLine("Employee ID: ", false);
        pstmSelectEmpl.setString(1, emplId);
        ResultSet results = pstmSelectEmpl.executeQuery();
        if (results.next()) {
            System.out.printf("[First Name]: %s [Last Name]: %s [Region]: %s [Primary Customer Name]: %s [Has Secondary Customer]: %s\n",
                    results.getString("First_Name"),
                    results.getString("Last_Name"),
                    getRegionFromState(results.getString("State_Represented"), pstmSelectRegionFromState),
                    getCustomerName(results.getString("Primary_Cust_ID"), pstmSelectCompanyFromId),
                    results.getString("Secondary_Cust_ID").equals("0") ? "No" : "Yes"
                );
        } else {
            System.out.println("No employee associated with ID: " + emplId);
        }
    }

    /**
     * Queries for region from regions table from state.
     * 
     * @param state The state to use in query
     * @param pstmSelectRegionFromState Prepared statement to query for region
     * @return The region
     * @throws SQLException if a database error occurs
     */
    private static String getRegionFromState(String state, PreparedStatement pstmSelectRegionFromState) throws SQLException {
        pstmSelectRegionFromState.setString(1, state);
        ResultSet results = pstmSelectRegionFromState.executeQuery();
        return results.next() ? results.getString("Region") : "n/a";
    }

    /**
     * Gets the customer name from a customer ID.
     * 
     * @param custId The customer ID
     * @param pstmSelectCompanyFromId Prepared statement to query for company name from customer ID
     * @return The customer name
     * @throws SQLException if a database error occurs
     */
    private static String getCustomerName(String custId, PreparedStatement pstmSelectCompanyFromId) throws SQLException {
        pstmSelectCompanyFromId.setString(1, custId);
        ResultSet results = pstmSelectCompanyFromId.executeQuery();
        return results.next() ? results.getString("Company") : "n/a";
    }

    /**
     * Displays customer information after prompting user for a customer ID.
     * 
     * @param pstmSelectCust Prepared statement to query for customer from customer ID
     * @param pstmSelectRevenueRange Prepared statement for getting the revenue range from a revenue group
     * @param pstmSelectEmployeeFromCustId Prepared statement to query for employee from customer ID
     * @throws IOException if an IOException occurs
     * @throws SQLException if a database error occurs
     */
    private static void displayCustomerInfo(PreparedStatement pstmSelectCust, PreparedStatement pstmSelectRevenueRange, PreparedStatement pstmSelectEmployeeFromCustId) throws SQLException, IOException {
        String custId = keyReader.readLine("Customer ID: ", false);
        pstmSelectCust.setString(1, custId);
        ResultSet results = pstmSelectCust.executeQuery();
        if (results.next()) {
            System.out.printf("[Company Name]: %s [Revenue range]: %s [Years]: %s [Associated Employees]: %s\n",
                    results.getString("Company"),
                    getRevenueRangeStr(results.getString("Revenue_Group"), pstmSelectRevenueRange),
                    results.getString("Years"),
                    getAssociatedEmployees(custId, pstmSelectEmployeeFromCustId)
                );
        } else {
            System.out.println("No customer associated with ID: " + custId);
        }
    }

    /**
     * Gets a formatted string for the revenue range from a revenue group.
     * 
     * @param revenueGroup The revenue group
     * @param pstmSelectRevenueRange Prepared statement for getting the revenue range from a revenue group
     * @return A formated string of the revenue range
     * @throws SQLException if a database error occurs
     */
    private static String getRevenueRangeStr(String revenueGroup, PreparedStatement pstmSelectRevenueRange) throws SQLException {
        pstmSelectRevenueRange.setString(1, revenueGroup);
        ResultSet results = pstmSelectRevenueRange.executeQuery();
        if (results.next()) {
            StringBuilder sb = new StringBuilder()
                .append("$")
                .append(results.getString("Low"))
                .append(" - $")
                .append(results.getString("High").equals("0") ? results.getString("Low") + "+" : results.getString("High"));
            return sb.toString();
        }
        return "n/a";
    }

    /**
     * Gets a formatted string of all of the associated employees of a customer.
     * 
     * @param custId The customer ID
     * @param pstmSelectEmployeeFromCustId Prepared statement to query for employee from customer ID.
     * @return The associated employees to a customer
     * @throws SQLException if a database error occurs
     */
    private static String getAssociatedEmployees(String custId, PreparedStatement pstmSelectEmployeeFromCustId) throws SQLException {
        pstmSelectEmployeeFromCustId.setString(1, custId);
        pstmSelectEmployeeFromCustId.setString(2, custId);
        ResultSet results = pstmSelectEmployeeFromCustId.executeQuery();
        StringBuilder sb = new StringBuilder();
        while (results.next()) {
            sb.append(results.getString("First_Name"));
            sb.append(" ");
            sb.append(results.getString("Last_Name"));
            sb.append(", ");
        }
        return sb.length() == 0 ? "n/a" : sb.substring(0, sb.length() - 2);
    }

    /**
     * Displays all of the regions, states, and employees to standard output.
     * 
     * @param statement Database statement to execute sql queries
     * @throws SQLException if a database error occurs
     */
    private static void displayRegions(Statement statement) throws SQLException {
        String sql = "SELECT Regions.Region, Regions.State, Employees.First_Name, Employees.Last_Name FROM Regions LEFT JOIN Employees ON Regions.State = Employees.State_Represented ORDER BY Regions.Region, Regions.State";
        ResultSet results = statement.executeQuery(sql);
        System.out.printf("%-12s  %-5s  %-10s  %s\n", "Region", "State", "First Name", "Last Name");
        System.out.println("----------------------------------------------");
        while (results.next()) {
            System.out.printf("%-12s  %-5s  %-10s  %s\n",
                results.getString(1) == null ? "-" : results.getString(1),
                results.getString(2) == null ? "-" : results.getString(2),
                results.getString(3) == null ? "-" : results.getString(3),
                results.getString(4) == null ? "-" : results.getString(4)
            );
        }
    }

    /**
     * Generates a employee report to the current directory.
     * 
     * @param statement Database statement to execute sql queries
     * @throws IOException if an IOException occurs
     * @throws SQLException if a database error occurs
     */
    private static void generateEmployeeReport(Statement statement) throws IOException, SQLException {
        String sql = "SELECT Employees.Last_Name, Employees.First_Name, Employees.Years_Service, Regions.Region, c1.Company as Primary_Company, r1.Low AS Primary_Company_Revenue_Low, r1.High AS Primary_Company_Revenue_High, c2.Company as Secondary_Company, r2.Low AS Secondary_Company_Revenue_Low, r2.High AS Secondary_Company_Revenue_High FROM ((((Employees LEFT JOIN Customers c1 ON Employees.Primary_Cust_ID = c1.Cust_ID) LEFT JOIN Customers c2 ON Employees.Secondary_Cust_ID = c2.Cust_ID) LEFT JOIN Revenue_Groups r1 ON c1.Revenue_Group = r1.Group) LEFT JOIN Revenue_Groups r2 ON c2.Revenue_Group = r2.Group) LEFT JOIN Regions ON Employees.State_Represented = Regions.State ORDER BY Employees.Last_Name, Employees.First_Name";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("employee_report.txt"))) {
            bw.write(String.format("Employee report [%s]", new Date().toString()));
            bw.newLine();
            bw.newLine();
            bw.write(String.format("%-11s  %-10s  %-5s  %-12s  %-30s  %-17s  %-30s  %-17s  %s", "Last Name", "First Name", "Years", "Region", "Primary Customer", "P.C. Revenue High", "Secondary Customer", "S.C. Revenue High", "Compensation modifier"));
            bw.newLine();
            bw.write("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            bw.newLine();
            ResultSet results = statement.executeQuery(sql);
            while (results.next()) {
                bw.write(String.format("%-11s  %-10s  %-5s  %-12s  %-30s  %-17s  %-30s  %-17s  %.2f",
                            results.getString(1),
                            results.getString(2),
                            results.getString(3),
                            results.getString(4),
                            results.getString(5),
                            String.format("$%s", results.getString(7).equals("0") ? results.getInt(6) + "+" : results.getString(7)),
                            results.getString(8) == null ? "none" : results.getString(8),
                            results.getString(8) == null ? "none" : String.format("$%s", results.getString(10).equals("0") ? results.getInt(9) + "+" : results.getString(10)),
                            calculateCompensationModifier(results.getInt(6), results.getInt(9), results.getInt(3), results.getString(4))
                        )
                );
                bw.newLine();
            }
        }
    }

    /**
     * Calculates the compensation modifier to an employee.
     * 
     * @param primaryLow The primary customer revenue low amount
     * @param secondaryLow The secondary customer revenu low amount
     * @param yos The years of service
     * @param region The region
     * @return The calculated compensation modifier
     */
    private static double calculateCompensationModifier(int primaryLow, int secondaryLow, int yos, String region) {
        double total = 0.005 * primaryLow + 0.0015 * secondaryLow;
        if (yos > 8)
            total += yos / 20.0;
        if (region.equals("Northeast"))
            total -= 500;
        return total < 0 ? 0 : total;
    }

    /**
     * Generates a log of the current session.
     * 
     * @param processedFiles The update files that were processed
     * @param numErrorRecords The number of error records processed
     * @param numEmployeeRecords The number of employee records generated
     * @throws IOException if an IOException occurs
     */
    private static void generateLog(ArrayList<File> processedFiles, int numErrorRecords, int numEmployeeRecords) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt"))) {
            bw.write("Processed Files:");
            bw.newLine();
            for (File f : processedFiles) {
                bw.write("  ");
                bw.write(f.getName());
                bw.newLine();
            }
            bw.newLine();
            bw.write("Number of error records: ");
            bw.write(Integer.toString(numErrorRecords));
            bw.newLine();
            bw.write("Number of reports generated: ");
            bw.write(Integer.toString(numEmployeeRecords));
            bw.newLine();
        }
    }

    /**
     * Prompts the user with a menu and returns their selection.
     * 
     * @return The selection
     * @throws IOException if an IOException occurs
     */
    private static int getUserSelection() throws IOException {
        StringBuilder prompt = new StringBuilder()
            .append("1. Display employee information\n")
            .append("2. Display customer information\n")
            .append("3. Display regions\n")
            .append("4. Produce employee report\n")
            .append("5. Exit\n")
            .append("Selection: ");
        return keyReader.readInt(1, 5, prompt.toString());
    }

    /**
     * Gets a list of updates to be processed from an update file.
     * 
     * @param updateFile A file with updates to be processed
     * @return A list of updates to be processed from an update file
     * @throws IOException if an IOException occurs
     */
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

    /**
     * Queries the database for the revenue group of a specific revenue amount.
     * 
     * @param revenueAmount The revenue amount
     * @param pstm A prepared statement for querying the revenue groups table
     * @return The revenue group from a given revenue amount
     * @throws SQLException if a database error occurs
     */
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

    /**
     * Calculates the years of service from part time and full time years of service.
     * 
     * @param partTimeYoS The years of service as part time
     * @param fullTimeYoS The years of service as full time
     * @return Calculated years of service
     */
    private static int calculateEmplYoS(String partTimeYoS, String fullTimeYoS) {
        return (int)(Double.parseDouble(fullTimeYoS) + 0.25 * Double.parseDouble(partTimeYoS));
    }

    /**
     * Determines if a customer is referenced in the employees table.
     * 
     * @param custId The customer ID to check
     * @param pstm A customized prepared statement to do the check
     * @return Whether the customer is referenced or not
     * @throws SQLException if a database error occurs
     */
    private static boolean isCustReferencedInEmpl(String custId, PreparedStatement pstm) throws SQLException {
        pstm.setString(1, custId);
        pstm.setString(2, custId);
        return pstm.executeQuery().next();
    }

    /**
     * Determines if an ID exists in a table using a prepared statement.
     * 
     * @param id An entry ID
     * @param pstm Customized prepared statement for querying a table
     * @return If the ID exists in the table
     * @throws SQLException if a database error occurs
     */
    private static boolean doesIdExist(String id, PreparedStatement pstm) throws SQLException {
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }

    /**
     * Determines if the state exists in the regions table.
     * 
     * @param state The state to look for
     * @param pstm The customized prepared statement to query for state
     * @return If the state exists in the regions table
     * @throws SQLException if a database error occurs
     */
    private static boolean doesStateExist(String state, PreparedStatement pstm) throws SQLException {
        pstm.setString(1, state);
        return pstm.executeQuery().next();
    }

    /**
     * Updates row in table.
     * 
     * @param pstm Customized prepared statement for update
     * @param fields Fields to be injected into prepared statement
     * @return The number of rows updated
     * @throws SQLException if a database error occurs
     */
    private static int updateRow(PreparedStatement pstm, String... fields) throws SQLException {
        for (int i = 0; i < fields.length; i++)
            pstm.setString(i + 1, fields[i]);
        return pstm.executeUpdate();
    }
}