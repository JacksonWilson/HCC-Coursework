package labs.lab13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

public class Driver {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("resources/lab13/Students/Students.txt"))) {
            TreeSet<Student> students = new TreeSet<>();
            reader.lines().forEach(line -> {
                try {
                    String[] elements = line.split(",");
                    String firstName = elements[0];
                    String lastName = elements[1];
                    long studentID = Long.parseLong(elements[2]);
                    students.add(new Student(firstName, lastName, studentID, new File("resources/lab13/Students/")));
                } catch (NumberFormatException e) {
                    System.err.println("Could not parse studentID.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            System.out.println();
            students.forEach(student -> {
                System.out.println(student);
                student.printAssignments();
                student.printExams();
                System.out.println();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}