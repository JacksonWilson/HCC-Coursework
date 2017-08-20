package labs.lab13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Student implements Comparable<Student> {
    private String firstName;
    private String lastName;
    private long studentID;
    private List<Float> assignments;
    private List<Float> exams;

    public Student(String firstName, String lastName, long studentID, File dataDirectory) throws IOException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        assignments = new LinkedList<>();
        exams = new LinkedList<>();

        File[] assignmentFiles = new File(dataDirectory, "Assignments").listFiles((dir, pathName) -> {
            return pathName.equals(studentID + ".txt");
        });
        if (assignmentFiles.length != 1)
            throw new IOException("Expected only one assignment .txt file per ID.");
        File assignmentFile = assignmentFiles[0];

        try (BufferedReader reader = new BufferedReader(new FileReader(assignmentFile))) {
            reader.lines().forEach(line -> {
                String[] words = line.split("\t");
                try {
                    for (int i = assignments.size(); i < Integer.parseInt(words[0]); i++)
                        assignments.add(0.F);
                    assignments.add(Float.parseFloat(words[1]));
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse value.");
                }
            });
            for (int i = assignments.size(); i < 13; i++)
                assignments.add(0.F);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File[] examFiles = new File(dataDirectory, "Exams").listFiles((dir, pathName) -> {
            return pathName.equals(studentID + ".txt");
        });
        if (examFiles.length != 1)
            throw new IOException("Expected only one exam .txt file per ID.");
        File examFile = examFiles[0];

        try (BufferedReader reader = new BufferedReader(new FileReader(examFile))) {
            reader.lines().forEach(line -> {
                String[] words = line.split("\t");
                try {
                    for (int i = exams.size(); i < Integer.parseInt(words[0]); i++)
                        exams.add(0.F);
                    exams.add(Float.parseFloat(words[1]));
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse value.");
                }
            });
            for (int i = exams.size(); i < 3; i++)
                exams.add(0.F);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printAssignments() {
        System.out.println("\n Assignments\n----------------\n #\tPercent\n----------------");
        for (int i = 0; i < assignments.size(); i++)
            System.out.printf("%03d\t%07.4f%%\n", (i + 1), assignments.get(i));
        System.out.printf("Avg:\t%07.4f%%\n", assignments.stream().mapToDouble(p -> p).average().getAsDouble());
    }

    public void printExams() {
        System.out.println("\n Exams\n----------------\n #\tPercent\n----------------");
        for (int i = 0; i < exams.size(); i++)
            System.out.printf("%03d\t%07.4f%%\n", (i + 1), exams.get(i));
        System.out.printf("Avg:\t%07.4f%%\n", exams.stream().mapToDouble(p -> p).average().getAsDouble());
    }

    @Override
    public String toString() {
        return "> " + lastName + ", " + firstName + " [" + studentID + "]";
    }

    @Override
    public int compareTo(Student other) {
        int lastNameDiff = this.lastName.compareTo(other.lastName);
        if (lastNameDiff == 0) {
            int firstNameDiff = this.firstName.compareTo(other.firstName);
            if (firstNameDiff == 0)
                return Long.compare(this.studentID, other.studentID);
            return firstNameDiff;
        }
        return lastNameDiff;
    }
}