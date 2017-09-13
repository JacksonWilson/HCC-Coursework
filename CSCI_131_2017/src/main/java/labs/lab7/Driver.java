package labs.lab7;

import java.lang.reflect.Method;

public class Driver {
    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("Riddle");
            printRecursively(clazz);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public static void printRecursively(Class<?> clazz) {
        if (!clazz.isInstance(Object.class))
            printRecursively(clazz.getSuperclass());
        System.out.println("Class Name: " + clazz.getName() + (clazz.getSuperclass() != null ? " (extends " + clazz.getSuperclass().getName() + ")\n" : "\n"));
        System.out.println(clazz.getMethods().length + " total public methods associated with the class");
        System.out.println("Declared (i.e. uninherited) methods associated with the class listed below");
        System.out.println("--------------------------------------------------------------------------");
        for (Method m : clazz.getDeclaredMethods())
            System.out.println(m);
        System.out.println();
    }
}