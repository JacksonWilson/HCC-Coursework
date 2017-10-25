package utils;

import java.util.ArrayList;
import java.util.Arrays;

public class SortAlgorithms {

    public static <T extends Comparable<T>> void bubble(ArrayList<T> arr) {
        for (int i = 1; i < arr.size() - 1; i++) {
            for (int j = 0; j < arr.size() - i; j++) {
                if (arr.get(j).compareTo(arr.get(j + 1)) > 0) {
                    T temp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, temp);
                }
            }
        }
    }

    public static void bubble(int[] arr) {
        for (int i = 1; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void bubble(long[] arr) {
        for (int i = 1; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    long temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void bubble(double[] arr) {
        for (int i = 1; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (Double.compare(arr[j], arr[j + 1]) > 1) {
                    double temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static <T extends Comparable<T>> void insertion(ArrayList<T> arr) {
        for (int i = 1; i < arr.size() - 1; i++) {
            int j = i;
            while (j > 0 && arr.get(j).compareTo(arr.get(j + 1)) > 1) {
                T temp = arr.get(j);
                arr.set(j, arr.get(j + 1));
                arr.set(j + 1, temp);
                j--;
            }
        }
    }

    public static void insertion(int[] arr) {
        for (int i = 1; i < arr.length - 1; i++) {
            int j = i;
            while (j > 0 && arr[j] > arr[j + 1]) {
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                j--;
            }
        }
    }

    public static void insertion(long[] arr) {
        for (int i = 1; i < arr.length - 1; i++) {
            int j = i;
            while (j > 0 && arr[j] > arr[j + 1]) {
                long temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                j--;
            }
        }
    }
    
    public static void insertion(double[] arr) {
        for (int i = 1; i < arr.length - 1; i++) {
            int j = i;
            while (j > 0 && Double.compare(arr[j], arr[j + 1]) > 1) {
                double temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                j--;
            }
        }
    }

    public static <T extends Comparable<T>> void merge(ArrayList<T> arr) {
        arr = mergeRecur(arr);
    }

    private static <T extends Comparable<T>> ArrayList<T> mergeRecur(ArrayList<T> arr) {
        if (arr.size() == 1)
            return arr;

        ArrayList<T> arr1 = new ArrayList<>(arr.subList(0, arr.size() / 2));
        ArrayList<T> arr2 = new ArrayList<>(arr.subList(arr.size() / 2, arr.size()));

        mergeRecur(arr1);
        mergeRecur(arr2);
        
        ArrayList<T> sortedList = new ArrayList<>();
        int arr1Pos = 0;
        int arr2Pos = 0;
        for (int i = 0; i < arr1.size() + arr2.size(); i++) {
            if (arr1Pos == arr1.size()) {
                sortedList.addAll(arr2.subList(arr2Pos, arr2.size()));
                break;
            }

            if (arr2Pos == arr2.size()) {
                sortedList.addAll(arr1.subList(arr1Pos, arr1.size()));
                break;
            }

            sortedList.add((arr1.get(arr1Pos).compareTo(arr2.get(arr2Pos)) > 0 ? arr1.get(arr1Pos++) : arr2.get(arr2Pos++)));
        }

        return sortedList;
    }
    
    public static void merge(int[] arr) {
        arr = mergeRecur(arr);
    }

    private static int[] mergeRecur(int[] arr) {
        if (arr.length == 1)
            return arr;

        int[] arr1 = Arrays.copyOfRange(arr, 0, arr.length / 2);
        int[] arr2 = Arrays.copyOfRange(arr, arr.length / 2, arr.length);

        mergeRecur(arr1);
        mergeRecur(arr2);
        
        int[] sortedArr = new int[arr1.length + arr2.length];
        int arr1Pos = 0;
        int arr2Pos = 0;
        for (int i = 0; i < arr1.length + arr2.length; i++) {
            if (arr1Pos == arr1.length) {
                for (int j = arr2Pos; j < arr2.length; j++)
                    sortedArr[i] = arr2[j];
                break;
            }

            if (arr2Pos == arr2.length) {
                for (int j = arr1Pos; j < arr1.length; j++)
                    sortedArr[i] = arr1[j];
                break;
            }

            sortedArr[i] = arr1[arr1Pos] > arr2[arr2Pos] ? arr1[arr1Pos++] : arr2[arr2Pos++];
        }

        return sortedArr;
    }

    public static void merge(long[] arr) {
        arr = mergeRecur(arr);
    }

    private static long[] mergeRecur(long[] arr) {
        if (arr.length == 1)
            return arr;

        long[] arr1 = Arrays.copyOfRange(arr, 0, arr.length / 2);
        long[] arr2 = Arrays.copyOfRange(arr, arr.length / 2, arr.length);

        mergeRecur(arr1);
        mergeRecur(arr2);
        
        long[] sortedArr = new long[arr1.length + arr2.length];
        int arr1Pos = 0;
        int arr2Pos = 0;
        for (int i = 0; i < arr1.length + arr2.length; i++) {
            if (arr1Pos == arr1.length) {
                for (int j = arr2Pos; j < arr2.length; j++)
                    sortedArr[i] = arr2[j];
                break;
            }

            if (arr2Pos == arr2.length) {
                for (int j = arr1Pos; j < arr1.length; j++)
                    sortedArr[i] = arr1[j];
                break;
            }

            sortedArr[i] = arr1[arr1Pos] > arr2[arr2Pos] ? arr1[arr1Pos++] : arr2[arr2Pos++];
        }

        return sortedArr;
    }

    public static void merge(double[] arr) {
        arr = mergeRecur(arr);
    }

    private static double[] mergeRecur(double[] arr) {
        if (arr.length == 1)
            return arr;

        double[] arr1 = Arrays.copyOfRange(arr, 0, arr.length / 2);
        double[] arr2 = Arrays.copyOfRange(arr, arr.length / 2, arr.length);

        mergeRecur(arr1);
        mergeRecur(arr2);
        
        double[] sortedArr = new double[arr1.length + arr2.length];
        int arr1Pos = 0;
        int arr2Pos = 0;
        for (int i = 0; i < arr1.length + arr2.length; i++) {
            if (arr1Pos == arr1.length) {
                for (int j = arr2Pos; j < arr2.length; j++)
                    sortedArr[i] = arr2[j];
                break;
            }

            if (arr2Pos == arr2.length) {
                for (int j = arr1Pos; j < arr1.length; j++)
                    sortedArr[i] = arr1[j];
                break;
            }

            sortedArr[i] = Double.compare(arr1[arr1Pos], arr2[arr2Pos]) > 1 ? arr1[arr1Pos++] : arr2[arr2Pos++];
        }

        return sortedArr;
    }
}