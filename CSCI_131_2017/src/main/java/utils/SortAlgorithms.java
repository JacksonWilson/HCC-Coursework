package utils;

import java.util.ArrayList;
import java.util.Arrays;

public class SortAlgorithms {

    public static <T extends Comparable<T>> void bubble(ArrayList<T> arr) {
        for (int i = 1; i < arr.size() - 1; i++) {
            for (int j = 0; j < arr.size() - i; j++) {
                if (arr.get(j).compareTo(arr.get(j + 1)) > 0) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    public static void bubble(int[] arr) {
        for (int i = 1; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    public static <T extends Comparable<T>> void insertion(ArrayList<T> arr) {
        for (int i = 1; i < arr.size() - 1; i++) {
            int j = i;
            while (j > 0 && arr.get(j).compareTo(arr.get(j + 1)) > 1) {
                swap(arr, j, j + 1);
                j--;
            }
        }
    }

    public static void insertion(int[] arr) {
        for (int i = 1; i < arr.length - 1; i++) {
            int j = i;
            while (j > 0 && arr[j] > arr[j + 1]) {
                swap(arr, j, j + 1);
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

    public static <T extends Comparable<T>> void quick(ArrayList<T> arr) {
        quickRecur(arr, 0, arr.size() - 1);
    }

    private static <T extends Comparable<T>> void quickRecur(ArrayList<T> arr, int l, int r) {
        if (l < r) {
            int pi = quickPartition(arr, l, r);

            quickRecur(arr, l, pi - 1);
            quickRecur(arr, pi + 1, r);
        }
    }

    private static <T extends Comparable<T>> int quickPartition(ArrayList<T> arr, int l, int r) {
        T pivot = arr.get(r);
        int i = l - 1;

        for (int j = l; j < r; j++) {
            if (arr.get(j).compareTo(pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, r, i + 1);

        return i + 1;
    }

    public static void quick(int[] arr) {
        quickRecur(arr, 0, arr.length - 1);
    }

    private static void quickRecur(int[] arr, int l, int r) {
        if (l < r) {
            int pi = quickPartition(arr, l, r);

            quickRecur(arr, l, pi - 1);
            quickRecur(arr, pi + 1, r);
        }
    }

    private static int quickPartition(int[] arr, int l, int r) {
        int pivot = arr[r];
        int i = l - 1;

        for (int j = l; j < r; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, r, i + 1);

        return i + 1;
    }

    public static <T extends Comparable<T>> void heap(ArrayList<T> arr) {
        for (int i = arr.size() / 2 - 1; i >= 0; i--)
            heapify(arr, arr.size(), i);

        for (int i = arr.size() - 1; i >= 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private static <T extends Comparable<T>> void heapify(ArrayList<T> arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr.get(l).compareTo(arr.get(largest)) > 0)
            largest = l;

        if (r < n && arr.get(r).compareTo(arr.get(largest)) > 0)
            largest = r;

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    public static void heap(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--)
            heapify(arr, arr.length, i);

        for (int i = arr.length - 1; i >= 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr[l] > arr[largest])
            largest = l;

        if (r < n && arr[r] > arr[largest])
            largest = r;

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    public static <T extends Comparable<T>> void selection(ArrayList<T> arr) {
        for (int i = 0; i < arr.size() - 1; i++) {
            int indexOfMin = i;
            for (int j = i + 1; j < arr.size(); j++) {
                if (arr.get(j).compareTo(arr.get(indexOfMin)) > 0)
                    indexOfMin = j;
            }
            swap(arr, i, indexOfMin);
        }
    }

    public static void selection(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int indexOfMin = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[indexOfMin])
                    indexOfMin = j;
            }
            swap(arr, i, indexOfMin);
        }
    }

    private static <T extends Comparable<T>> void swap(ArrayList<T> arr, int i, int j) {
        T temp = arr.get(i);
        arr.set(i, arr.get(i));
        arr.set(j, temp);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}