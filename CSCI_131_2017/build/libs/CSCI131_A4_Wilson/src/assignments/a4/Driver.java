package assignments.a4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Pattern;

import utils.KeyboardReader;
import utils.ObjectInputStreamResolved;

/**
 * Driver for Assignment 4 - Collections / Data structures.
 * 
 * @author Jackson Wilson
 */
public class Driver {
    private static final String FILE_EMPLOYEE_DATA = "Employees.dat";
    private static final String FILE_COMPONENT_CODES = "ComponentCodes.txt";
    private static final String FILE_JOB_CODES = "JobCodes.txt";
    private static final String FILE_WORK_ORDERS = "WorkOrders.txt";

    private static KeyboardReader keyReader;
    private static HashMap<String, String> componentCodes;
    private static HashMap<String, String> jobCodes;
    private static LinkedList<WorkOrder> workOrders;

    static {
        keyReader = new KeyboardReader(System.in);
    }

    public static void main(String[] args) {
        try {
            Employee currentUser = getCurrentEmployee(FILE_EMPLOYEE_DATA);
            if (currentUser == null)
                return;

            componentCodes = getCodeValues(FILE_COMPONENT_CODES);
            jobCodes = getCodeValues(FILE_JOB_CODES);
            workOrders = getWorkOrders(FILE_WORK_ORDERS);

            do {
                displayMenu(currentUser.getSecurityLevel());
            } while (handleInput(keyReader.readLine("Selection: "), currentUser));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all of the employee objects stored in the .dat file.
     * 
     * @param filePath The path to the .dat file.
     * @return An array of all of the employees.
     * @throws IOException If the file could not be read.
     * @throws ClassNotFoundException If the readObject method could not find a suitable class definition for the Employee objects.
     */
    private static ArrayList<Employee> getEmployees(String filePath) throws IOException, ClassNotFoundException {
        ArrayList<Employee> employees = new ArrayList<>();

        try (ObjectInputStreamResolved oisr = new ObjectInputStreamResolved(new FileInputStream(filePath))) {
            oisr.putClassDefinition("Employee", assignments.a4.Employee.class); // Add the package header to the incoming employee objects.
            Employee employee;

            while ((employee = (Employee)oisr.readObject()) != null) {
                employees.add(employee);
            }

        } catch (EOFException eofe) {}
        
        return employees;
    }

    /**
     * Prompts the user for a valid ID to get the current employee.
     * 
     * @param filePath The path to the .dat file.
     * @return The current employee based on the inputted ID or null if a valid ID was not supplied.
     * @throws IOException If there was an exception when working with files or user input.
     * @throws ClassNotFoundException If the readObject method could not find a suitable class definition for the Employee objects.
     */
    private static Employee getCurrentEmployee(String filePath) throws IOException, ClassNotFoundException {
        ArrayList<Employee> employees = getEmployees(filePath);

        if (employees == null || employees.isEmpty()) {
            System.err.println("Could not load any employees.");
            return null;
        }
        
        String id;
        int attempts = 0;
        Employee currentEmployee = null;

        do {
            attempts++;
            id = keyReader.readLine("Enter ID: ");

            for (Employee e : employees) {
                if (e.getID().equals(id))
                    currentEmployee = e;
            }
        } while (currentEmployee == null && attempts < 3);

        if (currentEmployee == null)
            System.err.println("SECURITY WARNING: FAILED TO SUPPLY VALID ID WITHIN 3 ATTEMPTS.");

        return currentEmployee;
    }

    /**
     * Gets the code values from a file.
     * 
     * @param filePath The file path to the code values.
     * @return A map of the code values.
     * @throws IOException If the file could not be read.
     */
    private static HashMap<String, String> getCodeValues(String filePath) throws IOException {
        HashMap<String, String> codes = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                codes.put(split[0], split[1]);
            }
        }

        return codes;
    }

    /**
     * Gets the work orders from a file.
     * 
     * @param filePath The file path to the work orders.
     * @return A linked list of work orders.
     * @throws IOException If the file could not be read.
     */
    private static LinkedList<WorkOrder> getWorkOrders(String filePath) throws IOException {
        LinkedList<WorkOrder> workOrders = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String order;
            while ((order = br.readLine()) != null) {
                String[] orderInfo = order.split(",");
                workOrders.add(new WorkOrder(orderInfo[0], orderInfo[1], orderInfo[2]));
            }
        }

        return workOrders;
    }

    /**
     * Displays the menu based on a specified security level.
     * 
     * @param securityLevel The security level of the current employee.
     */
    private static void displayMenu(String securityLevel) {
        System.out.println();
        System.out.println("1. Create work order report");
        System.out.println("2. View specific component code");
        System.out.println("3. View specific job code");

        if (securityLevel.equals("Partial edit") || securityLevel.equals("Edit")) {
            System.out.println("4. Create new work order");

            if (securityLevel.equals("Edit")) {
                System.out.println("5. Add component code");
                System.out.println("6. Delete component code");
                System.out.println("7. Update component code");
            }
        }
        System.out.println("9. Exit");
        System.out.println();
    }

    /**
     * Handle the user's input.
     * 
     * @param input The user's selection.
     * @param currentUser The current employee.
     * @return Whether to prompt the user for another selection.
     * @throws IOException If there was an exception when working with files or user input.
     */
    private static boolean handleInput(String input, Employee currentUser) throws IOException {
        switch (input) {
        case "1": createReport(currentUser);
            break;
        case "2": viewCodeValue(keyReader.readLine("Component code: "), componentCodes);
            break;
        case "3": viewCodeValue(keyReader.readLine("Job code: "), jobCodes);
            break;
        case "4": {
            if (!currentUser.getSecurityLevel().equals("View only"))
                createNewWorkOrder();
            else
                System.out.println("Please select a number from the list.");
            break;
        }
        case "9": {
            saveFiles(currentUser.getSecurityLevel());
            return false;
        }
        default: {
            if (currentUser.getSecurityLevel().equals("Edit")) {
                switch (input) {
                case "5": createComponent(keyReader.readLine("Component code: ", false));
                    break;
                case "6": removeComponent(keyReader.readLine("Component code: "));
                    break;
                case "7": updateComponent(keyReader.readLine("Component code: "));
                    break;
                default: System.out.println("Please select a number from the list.");
                    break;
                }
            } else {
                System.out.println("Please select a number from the list.");
            }
            break;
        }
        }
        return true;
    }

    /**
     * Creates a work order report.
     * 
     * @param currentUser The current employee requesting to create a report.
     * @throws IOException If there was an exception when writing to the file.
     */
    private static void createReport(Employee currentUser) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("WorkOrder" + (getNextReportNumber()) + ".txt"))) {
            bw.write("Name: ");
            bw.write(currentUser.getName());
            bw.newLine();
            bw.write("Department: ");
            bw.write(currentUser.getDepartment());
            bw.newLine();
            bw.write("Security level: ");
            bw.write(currentUser.getSecurityLevel());
            bw.newLine();
            bw.write("Quarter: Q");
            bw.write(Integer.toString(LocalDate.now().get(IsoFields.QUARTER_OF_YEAR)));
            bw.newLine();
            bw.newLine();
            bw.write("Work Orders");
            bw.newLine();
            bw.write("-----------------------");
            bw.newLine();
            for (WorkOrder order : workOrders) {
                bw.write("Order: ");
                bw.write(order.getOrderNumber());
                bw.write(" Job: ");
                bw.write(order.getJobCode());
                bw.write(" [");
                bw.write(jobCodes.get(order.getJobCode()));
                bw.write("] Component: ");
                bw.write(order.getComponentCode());
                bw.write(" [");
                bw.write(componentCodes.get(order.getComponentCode()));
                bw.write("]");
                bw.newLine();
            }
        }
        System.out.println("Created report.");
    }

    /**
     * Displays the code's value if it exists, or prompts otherwise.
     * 
     * @param code A code to be looked up.
     * @param codes The map of codes used to look up the value.
     */
    private static void viewCodeValue(String code, HashMap<String, String> codes) {
        if (codes.containsKey(code)) {
            System.out.println(code + " - " + codes.get(code));
        } else {
            System.out.println("No value exists for code: " + code);
        }
    }

    /**
     * Creates a new work order based on user input.
     * 
     * @return Whether a new work order was created or not.
     * @throws IOException If there was an exception when getting user input.
     */
    private static boolean createNewWorkOrder() throws IOException {
        String orderNumber = keyReader.readLine("Order number: ");
        String jobCode = keyReader.readLine("Job code: ");
        String componentCode = keyReader.readLine("Component code: ");

        boolean valid = true;

        for (WorkOrder order : workOrders) {
            if (orderNumber.equals(order.getOrderNumber())) {
                System.out.println("Work order number already exists.");
                valid = false;
                break;
            }
        }

        if (!jobCodes.containsKey(jobCode)) {
            System.out.println("Invalid job code: " + jobCode);
            valid = false;
        }

        if (!componentCodes.containsKey(componentCode)) {
            System.out.println("Invalid component code: " + componentCode);
            valid = false;
        }

        if (valid) {
            WorkOrder newOrder = new WorkOrder(orderNumber, jobCode, componentCode);
            valid = workOrders.add(newOrder);

            if (valid) {
                System.out.println("Added order: " + newOrder.getOrderNumber()
                    + " Job: " + newOrder.getJobCode()
                    + " [" + jobCodes.get(newOrder.getJobCode())
                    + "] Component: " + newOrder.getComponentCode()
                    + " [" + componentCodes.get(newOrder.getComponentCode()) + "]");
            }
        }
        
        if (!valid) {
            System.out.println("Failed to create new order.");
        }

        return valid;
    }

    /**
     * Creates a new component if the supplied code does not already map to a component.
     * 
     * @param componentCode The code of the new component.
     * @throws IOException If there was an exception when getting user input.
     */
    private static void createComponent(String componentCode) throws IOException {
        if (componentCodes.containsKey(componentCode)) {
            System.out.println("Code already exists: " + componentCode + " [" + componentCodes.get(componentCode) + "]");
        } else {
            String description = keyReader.readLine("Description: ");
            componentCodes.put(componentCode, description);
            System.out.println("Added: " + componentCode + " [" + description + "]");
        }
    }
    
    /**
     * Removes a component if there is a value mapped.
     * 
     * @param componentCode The code of the component to be removed.
     */
    private static void removeComponent(String componentCode) {
        if (componentCodes.containsKey(componentCode)) {
            System.out.println("Removed: " + componentCode + " [" + componentCodes.remove(componentCode) + "]");
        } else {
            System.out.println("Code does not exist: " + componentCode);
        }
    }

    /**
     * Updates a component description if the code exists.
     * 
     * @param componentCode The code of the component to be updated.
     * @throws IOException If there was an exception when getting user input.
     */
    private static void updateComponent(String componentCode) throws IOException {
        if (componentCodes.containsKey(componentCode)) {
            System.out.println("Component: " + componentCode + " [" + componentCodes.get(componentCode) + "]");
            String description = keyReader.readLine("New description: ");
            System.out.println("Updated component: " + componentCode + " [" + componentCodes.put(componentCode, description) + "] -> [" + description + "]");
        } else {
            System.out.println("Code does not exist: " + componentCode);
        }
    }

    /**
     * Saves the current instances of the collections based on the security level.
     * 
     * @param securityLevel The security level to determine which files can be saved.
     * @throws IOException If there was an exception when saving the files.
     */
    private static void saveFiles(String securityLevel) throws IOException {
        switch (securityLevel) {
        case "Edit":
            saveCodes(jobCodes, FILE_JOB_CODES);
            saveCodes(componentCodes, FILE_COMPONENT_CODES);
        case "Partial edit":
            saveWorkOrders(FILE_WORK_ORDERS);
        }
    }

    /**
     * Saves the current instance of code values.
     * 
     * @param codes The map of code values to save.
     * @param filePath The file path to save/override the current code values.
     * @throws IOException If there was an exception when saving the files.
     */
    private static void saveCodes(HashMap<String, String> codes, String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : codes.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        }
    }
    
    /**
     * Saves the current instance of work orders.
     * 
     * @param filePath The file path to save/override the current work orders.
     * @throws IOException If there was an exception when writing to the file.
     */
    private static void saveWorkOrders(String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (WorkOrder order : workOrders) {
                bw.write(order.getOrderNumber() + "," + order.getJobCode() + "," + order.getComponentCode());
                bw.newLine();
            }
        }
    }

    /**
     * Gets the next work order report number in the current directory.
     * 
     * @return The next report number.
     */
    private static int getNextReportNumber() {
        File[] listFiles = new File(".").listFiles();
        int reportNum = 0;
        Pattern reportPattern = Pattern.compile("WorkOrder\\d+.txt");
        for (File f : listFiles) {
            if (reportPattern.matcher(f.getName()).matches()) {
                int temp = Integer.parseInt(f.getName().substring("WorkOrder".length(), f.getName().indexOf(".txt")));
                if (temp > reportNum)
                    reportNum = temp;
            }
        }
        return reportNum + 1;
    }
}