package labs.lab17;

import java.util.Random;

public class Driver {

    public static void main(String[] args) {
        testLinkedList();
        testStack();
        testDictionary();
    }
    
    private static void testLinkedList() {
        testLinkedList_AddToEnd();
        testLinkedList_AddToBeginning();
        testLinkedList_AddAtPosition();
    }

    private static void testLinkedList_AddToEnd() {
        int[] arr = generateIntArray(1000);
        LinkedList list = new LinkedList();
        for (int n : arr)
            list.addToEnd(n);
        for (int i = 0; i < arr.length; i++) {
            if (list.retreiveElementAtPosition(i + 1) != arr[i]) {
                fail();
                return;
            }
        }
        pass();
    }

    private static void testLinkedList_AddToBeginning() {
        int[] arr = generateIntArray(1000);
        LinkedList list = new LinkedList();
        for (int n : arr)
            list.addToBeginning(n);
        for (int i = 0; i < arr.length; i++) {
            if (list.retreiveElementAtPosition(i) != arr[arr.length - i - 1]) {
                fail();
                return;
            }
        }
        pass();
    }

    private static void testLinkedList_AddAtPosition() {
        int[] arr = generateIntArray(1000);
        LinkedList list = new LinkedList();
        for (int i = 0; i < arr.length / 2; i++)
            list.addToEnd(arr[i]);
        for (int i = arr.length / 2; i < arr.length; i++)
            list.addAtPosition(i, arr[i]);
        for (int i = arr.length / 2; i < arr.length; i++) {
            if (list.retreiveElementAtPosition(i) != arr[arr.length - i - 1]) {
                fail();
                System.out.println(list.retreiveElementAtPosition(i));
                return;
            }
        }
        pass();
    }

    private static int[] generateIntArray(int size) {
        Random r = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = r.nextInt();
        return arr;
    }

    private static void testStack() {

    }

    private static void testDictionary() {

    }

    private static void pass() {
        System.out.println("Pass");
    }
    
    private static void fail() {
        System.out.println("Fail whale");
    }
}