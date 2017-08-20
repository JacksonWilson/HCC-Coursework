package labs.lab4;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.BiFunction;

import javax.swing.plaf.metal.MetalScrollButton;

/**
 * Problem 1: Factorial | Problem 2: The Extended ASCII Chart
 * <p>
 * Problem 1 sets out to explore the differences between using a loop and
 * a recursive method to determine the factorial of a given number.
 * While Problem 2 explores the use of recursion to do stuff.
 * <p>
 * 
 * @author Jackson Wilson
 */
public class Driver {
    public static void main(String[] args) {
        printProblemHeader("Problem 1: Factorial");
        problem1();
        printProblemHeader("Problem 2: The Extended ASCII Chart");
        problem2();
        printProblemHeader("Problem 3: Greatest Common Divisor");
        problem3();
        printProblemHeader("Problem 4: Inheritance");
        problem4();
    }

    private static void printProblemHeader(String str) {
        System.out.print("--------------------------------------------------------------------------------\n|");
        int spaces = 78 - str.length();
        for (int i = 0; i < spaces / 2; i++)
            System.out.print(" ");
        System.out.print(str);
        for (int i = 0; i < spaces / 2 + spaces % 2; i++)
            System.out.print(" ");
        System.out.println("|\n--------------------------------------------------------------------------------");
    }

    private static void problem1() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter n: ");
        int input = Integer.parseInt(scanner.nextLine());

        long startTime = System.nanoTime();
        long result = factorialWithLoop(input);
        long endTime = System.nanoTime();
        long time = endTime - startTime;
        System.out.println("[Loop] Factorial of " + input + " (" + input + "!) = " + result + " (" + time + " nanoseconds)");
        
        startTime = System.nanoTime();
        result = factorialWithRecursion(input);
        endTime = System.nanoTime();
        time = endTime - startTime;
        System.out.println("[Recursion] Factorial of " + input + " (" + input + "!) = " + result + " (" + time + " nanoseconds)");

        Function<Long, Long> factorialWithLambda = n -> {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            BiFunction<BiFunction, Long, Long> factHelper = (f, d) -> {
                return (d == 0) ? 1 : d * (Long)f.apply(f, d - 1);
            };
            return factHelper.apply(factHelper, n);
        };
        startTime = System.nanoTime();
        result = factorialWithLambda.apply((long)input);
        endTime = System.nanoTime();
        time = endTime - startTime;
        System.out.println("[Lambda recursion] Factorial of " + input + " (" + input + "!) = " + result + " (" + time + " nanoseconds)");
        System.out.println();
        
        scanner.close();
    }


    /**
     * Uses a loop to determine the factorial of n.
     * <p>
     * Only accurate when 0 <= n <= 20
     * <p>
     * 
     * @param n the largest number in the factorial
     * @return result of factorial
     */
    private static long factorialWithLoop(int n) {
        long ret = n;
        for (int i = n - 1; i > 0; i--)
            ret *= i;
        return ret;
    }

    /**
     * Uses recursion to determine the factorial of n.
     * <p>
     * Only accurate when 0 <= n <= 20
     * <p>
     * 
     * @param n the largest number in the factorial
     * @return result of factorial
     */
    private static long factorialWithRecursion(int n) {
        if(n == 1)
            return n;
        return n * factorialWithRecursion(n - 1);
    }

    private static void problem2() {
        System.out.println("[Loops] Printing the Extended ASCII chart...");
        long stateTime = System.nanoTime();
        printASCIIWithLoop();
        long endTime = System.nanoTime();
        System.out.println("\nTime: " + (endTime - stateTime) + " nanoseconds\n");

        System.out.println("[Recursion] Printing the Extended ASCII chart...");
        stateTime = System.nanoTime();
        printASCIIWithRecursion(40);
        endTime = System.nanoTime();
        System.out.println("\nTime: " + (endTime - stateTime) + " nanoseconds\n");
    }

    private static void printASCIIWithLoop() {
        for (int r = 4; r < 255/10; r++) {
            for (int c = 0; c < 10; c++) {
                System.out.print((char)(r * 10 + c) + "\t");
            }
            System.out.println();
        }

        for (int i = 250; i < 255; i++) {
            System.out.print((char)(i) + "\t");
        }
    }

    private static int printASCIIWithRecursion(int c) {
        if (c == 255)
            return c;
        System.out.print((char)c + ((c + 1) % 10 == 0 ? "\n" : "\t"));
        return printASCIIWithRecursion(c + 1);
    }

    private static void problem3() {
        System.out.println("[Loop]:");
        long startTime = System.nanoTime();
        System.out.println("gcd(35, 15): " + gcdWithLoop(35, 15));
        System.out.println("gcd(9, 18): " + gcdWithLoop(9, 18));
        System.out.println("gcd(9, 17): " + gcdWithLoop(9, 17));
        long endTime = System.nanoTime();
        System.out.println("Time: " + (endTime - startTime) + " nanoseconds\n");

        BiFunction<Integer, Integer, Integer> gcdWithLoopLambda = (x, y) -> {
            int d = Math.min(x, y);
            while (Math.max(x, y) % d != 0 || Math.min(x, y) % d != 0)
                d--;
            return d;
        };
        System.out.println("[Lambda loop]:");
        startTime = System.nanoTime();
        System.out.println("gcd(35, 15): " + gcdWithLoopLambda.apply(35, 15));
        System.out.println("gcd(9, 18): " + gcdWithLoopLambda.apply(9, 18));
        System.out.println("gcd(9, 17): " + gcdWithLoopLambda.apply(9, 17));
        endTime = System.nanoTime();
        System.out.println("Time: " + (endTime - startTime) + " nanoseconds\n");

        System.out.println("[Recursion]:");
        startTime = System.nanoTime();
        System.out.println("gcd(35, 15): " + gcdWithRecursion(35, 15));
        System.out.println("gcd(9, 18): " + gcdWithRecursion(9, 18));
        System.out.println("gcd(9, 17): " + gcdWithRecursion(9, 17));
        endTime = System.nanoTime();
        System.out.println("Time: " + (endTime - startTime) + " nanoseconds\n");
    }

    private static int gcdWithLoop(int x, int y) {
        int d = Math.min(x, y);
        while (Math.max(x, y) % d != 0 || Math.min(x, y) % d != 0)
            d--;
        return d;
    }

    private static int gcdWithRecursion(int x, int y) {
        if (y <= x && x % y == 0)
            return y;
        if (x < y)
            return gcdWithRecursion(y, x);
        return gcdWithRecursion(y, (x % y));
    }

    private static void problem4() {
        Class<?> c = MetalScrollButton.class;
        System.out.println("[Loops] Printing inheritance tree of \"" + c.getName() + "\"...");
        long startTime = System.nanoTime();
        printClassHierarchyWithRecursion(c);
        long endTime = System.nanoTime();
        System.out.println("Time: " + (endTime - startTime) + " nanoseconds\n");

        System.out.println("[Recursion] Printing inheritance tree of \"" + c.getName() + "\"...");
        startTime = System.nanoTime();
        printClassHierarchyWithLoop(c);
        endTime = System.nanoTime();
        System.out.println("Time: " + (endTime - startTime) + " nanoseconds");
    }

    private static void printClassHierarchyWithLoop(Class<?> c) {
        List<Class<?>> classes = new ArrayList<>();
        while (c != null) {
            classes.add(0, c);
            c = c.getSuperclass();
        }

        for (Class<?> cls : classes) {
            System.out.println("\t" + cls.getName());
        }
    }

    private static void printClassHierarchyWithRecursion(Class<?> c) {
        if (c.equals(Object.class)) {
            System.out.println("\t" + c.getName());
            return;
        }
        printClassHierarchyWithRecursion(c.getSuperclass());
        System.out.println("\t" + c.getName());
    }
}