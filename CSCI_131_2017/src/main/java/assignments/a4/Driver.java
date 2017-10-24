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

public class Driver {
    private static final String FILE_EMPLOYEE_DATA = "res/assignments/a4/Employees.dat";
    private static final String FILE_COMPONENT_CODES = "res/assignments/a4/ComponentCodes.txt";
    private static final String FILE_JOB_CODES = "res/assignments/a4/JobCodes.txt";
    private static final String FILE_WORK_ORDERS = "res/assignments/a4/WorkOrders.txt";

    private static KeyboardReader keyReader;

    static {
        keyReader = new KeyboardReader(System.in);
    }

    public static void main(String[] args) {
        try {
            ArrayList<Employee> employees = getEmployees(FILE_EMPLOYEE_DATA);
            if (employees == null || employees.isEmpty())
                return;

            Employee currentUser = getCurrentEmployee(employees);
            if (currentUser == null) {
                System.err.println("SECURITY WARNING: FAILED TO SUPPLY VALID ID WITHIN 3 ATTEMPTS.");
                return;
            }

            HashMap<String, String> componentCodes = getCodeValues(FILE_COMPONENT_CODES);
            HashMap<String, String> jobCodes = getCodeValues(FILE_JOB_CODES);
            LinkedList<WorkOrder> workOrders = getWorkOrders(FILE_WORK_ORDERS);

            String input;
            do {
                displayMenu(currentUser.getSecurityLevel());
                input = keyReader.readLine("Selection: ");
                System.out.println();

                switch (input) {
                case "1": createReport(currentUser, workOrders, jobCodes, componentCodes);
                    break;
                case "2": viewCodeValue(keyReader.readLine("Component code: "), componentCodes);
                    break;
                case "3": viewCodeValue(keyReader.readLine("Job code: "), jobCodes);
                    break;
                case "4":
                    if (currentUser.getSecurityLevel().equals("View only"))
                        System.out.println("Please select a number from the list.");
                    else
                        createNewWorkOrder(workOrders, jobCodes, componentCodes);
                    break;
                case "5": {
                    if (currentUser.getSecurityLevel().equals("Edit"))
                        createNewCodeValue(keyReader.readLine("Component code: ", false), componentCodes);
                    else
                        System.out.println("Please select a number from the list.");
                    break;
                }
                case "6": {
                    if (currentUser.getSecurityLevel().equals("Edit"))
                        removeCodeValue(keyReader.readLine("Component code: "), componentCodes);
                    else
                        System.out.println("Please select a number from the list.");
                    break;
                }
                case "7": {
                    if (currentUser.getSecurityLevel().equals("Edit"))
                        updateCodeValue(keyReader.readLine("Component code: "), componentCodes);
                    else
                        System.out.println("Please select a number from the list.");
                    break;
                }
                case "9": {
                    switch (currentUser.getSecurityLevel()) {
                    case "Edit":
                        saveCodeValues(jobCodes, FILE_JOB_CODES);
                        saveCodeValues(componentCodes, FILE_COMPONENT_CODES);
                    case "Partial edit":
                        saveWorkOrders(workOrders, FILE_WORK_ORDERS);
                    }
                    break;
                }
                default: System.out.println("Please select a number from the list.");
                    break;
                }
            } while (!input.equals("9"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private static ArrayList<Employee> getEmployees(String filePath) {
        ArrayList<Employee> employees = new ArrayList<>();

        try (ObjectInputStreamResolved oisr = new ObjectInputStreamResolved(new FileInputStream(filePath))) {
            oisr.putClassDefinition("Employee", assignments.a4.Employee.class);
            Employee employee;

            while ((employee = (Employee)oisr.readObject()) != null) {
                employees.add(employee);
            }

        } catch (EOFException eofe) {

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        
        return employees;
    }

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

    private static Employee getCurrentEmployee(ArrayList<Employee> employees) throws IOException {
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

        return currentEmployee;
    }
    
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
    }

    private static void createReport(Employee currentUser, LinkedList<WorkOrder> workOrders, HashMap<String, String> jobCodes, HashMap<String, String> componentCodes) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("WorkOrder" + (getReportNumber()) + ".txt"))) {
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
            System.out.println("Created report.");
        }
    }
    
    private static void viewCodeValue(String code, HashMap<String, String> codes) {
        if (codes.containsKey(code)) {
            System.out.println(code + " - " + codes.get(code));
        } else {
            System.out.println("No value exists for code: " + code);
        }
    }

    private static void updateCodeValue(String code, HashMap<String, String> codes) throws IOException {
        if (codes.containsKey(code)) {
            System.out.println("Component: " + code + " [" + codes.get(code) + "]");
            String description = keyReader.readLine("New description: ");
            System.out.println("Updated component: " + code + " [" + codes.put(code, description) + "] -> [" + description + "]");
        } else {
            System.out.println("Code does not exist: " + code);
        }
    }

    private static void removeCodeValue(String code, HashMap<String, String> codes) {
        if (codes.containsKey(code)) {
            System.out.println("Removed: " + code + " [" + codes.remove(code) + "]");
        } else {
            System.out.println("Code does not exist: " + code);
        }
    }

    private static void createNewCodeValue(String code, HashMap<String, String> codes) throws IOException {
        if (codes.containsKey(code)) {
            System.out.println("Code already exists: " + code + " [" + codes.get(code) + "]");
        } else {
            String description = keyReader.readLine("Description: ");
            codes.put(code, description);
            System.out.println("Added: " + code + " [" + description + "]");
        }
    }

    private static void createNewWorkOrder(LinkedList<WorkOrder> workOrders, HashMap<String, String> jobCodes, HashMap<String, String> componentCodes) throws IOException {
        String orderNumber;
        do {
            orderNumber = keyReader.readLine("Order number: ");
            for (WorkOrder order : workOrders) {
                if (orderNumber.equals(order.getOrderNumber())) {
                    System.out.println("Number already exists.");
                    orderNumber = null;
                    break;
                }
            }
        } while (orderNumber == null);

        String jobCode;
        while (!jobCodes.containsKey(jobCode = keyReader.readLine("Job code: ")))
            System.out.println("Invalid code: " + jobCode);

        String componentCode;
        while (!componentCodes.containsKey(componentCode = keyReader.readLine("Component code: ")))
            System.out.println("Invalid code: " + componentCode);

        WorkOrder newOrder = new WorkOrder(orderNumber, jobCode, componentCode);
        workOrders.add(newOrder);

        System.out.println("Added Order: " + newOrder.getOrderNumber()
            + " Job: " + newOrder.getJobCode()
            + " [" + jobCodes.get(newOrder.getJobCode())
            + "] Component: " + newOrder.getComponentCode()
            + " [" + componentCodes.get(newOrder.getComponentCode()) + "]");
    }

    private static void saveCodeValues(HashMap<String, String> codes, String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : codes.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        }
    }
    
    private static void saveWorkOrders(LinkedList<WorkOrder> workOrders, String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (WorkOrder order : workOrders) {
                bw.write(order.getOrderNumber() + "," + order.getJobCode() + "," + order.getComponentCode());
                bw.newLine();
            }
        }
    }

    /*
     * Finds the next report number in the current directory.
     */
    private static int getReportNumber() {
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