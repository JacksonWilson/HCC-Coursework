import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ConvertEmployees {

    public static void main(String[] args) {
        ArrayList<assignments.a4.Employee> employees = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Employees.dat"))) {
            Employee employee;
            
            while ((employee = (Employee)ois.readObject()) != null) {
                employees.add(new assignments.a4.Employee(employee.getName(), employee.getID(), employee.getDepartment(), employee.getSecurityLevel()));
            }
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("EmployeesFixed.dat"))) {
            for (assignments.a4.Employee e : employees) {
                oos.writeObject(e);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}