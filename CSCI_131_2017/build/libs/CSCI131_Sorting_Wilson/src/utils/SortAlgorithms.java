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

    public static <T extends Comparable<T>> void bubble(T[] arr) {
        for (int i = 1; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
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
        insertionRanged(arr, 0, arr.length);
    }

    private static void insertionRanged(int[] arr, int l, int r) {
        for (int i = 1; i < r - 1; i++) {
            int j = i;
            while (j > l && arr[j] > arr[j + 1]) {
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
    
    public static <T extends Comparable<T>> void merge(T[] arr) {
        arr = mergeRecur(arr);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> T[] mergeRecur(T[] arr) {
        if (arr.length == 1)
            return arr;

        T[] arr1 = Arrays.copyOfRange(arr, 0, arr.length / 2);
        T[] arr2 = Arrays.copyOfRange(arr, arr.length / 2, arr.length);

        mergeRecur(arr1);
        mergeRecur(arr2);
        
        Comparable<T>[] sortedArr = new Comparable[arr1.length + arr2.length];
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

            sortedArr[i] = arr1[arr1Pos].compareTo(arr2[arr2Pos]) > 0 ? arr1[arr1Pos++] : arr2[arr2Pos++];
        }

        return (T[])sortedArr;
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

    public static <T extends Comparable<T>> void quick(T[] arr) {
        quickRecur(arr, 0, arr.length - 1);
    }

    private static <T extends Comparable<T>> void quickRecur(T[] arr, int l, int r) {
        if (l < r) {
            int pi = quickPartition(arr, l, r);

            quickRecur(arr, l, pi - 1);
            quickRecur(arr, pi + 1, r);
        }
    }

    private static <T extends Comparable<T>> int quickPartition(T[] arr, int l, int r) {
        T pivot = arr[r];
        int i = l - 1;

        for (int j = l; j < r; j++) {
            if (arr[j].compareTo(pivot) <= 0) {
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

    public static void tim(int[] arr) {
        int run = 32;

        for (int i = 0; i < arr.length; i += run) {
            insertionRanged(arr, i, Math.min(i + 31, arr.length - 1));
        }

        for (int size = run; size < arr.length; size *= 2) {
            for (int left = 0; left < arr.length; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, arr.length - 1);
                mergeIterative(arr, left, mid, right);
            }
        }
    }

    private static void mergeIterative(int[] arr, int l, int m, int r) {
        int left[] = new int[m - l + 1];
        int right[] = new int[r - m];

        for (int i = 0; i < left.length; i++)
            left[i] = arr[l + i];
        for (int i = 0; i < right.length; i++)
            right[i] = arr[m + 1 + i];
     
        int i = 0;
        int j = 0;
        int k = l;
     
        for (; i < left.length && j < right.length; k++) {
            if (left[i] <= right[j])
                arr[k] = left[i++];
            else
                arr[k] = right[j++];
        }

        for (; i < left.length; k++, i++)
            arr[k] = left[i];
     
        for (; i < right.length; k++, i++)
            arr[k] = right[i];
    }

    public static void gnome(int[] arr) {
        for (int i = 0; i < arr.length; ) {
            if (i == 0)
                i++;
            
            if (arr[i] >= arr[i - 1]) {
                i++;
            } else {
                swap(arr, i, i - 1);
                i--;
            }
        }
    }

    public static void counting(int[] arr) {
        if (arr == null || arr.length == 0)
            return;
        
        int[] minMaxValues = minMaxValues(arr);

        counting(arr, minMaxValues[0], minMaxValues[1]);
    }

    private static void counting(int[] arr, int min, int max) {
        int[] buckets = new int[max - min + 1];

        for (int i = 0; i < arr.length; i++) {
            buckets[arr[i] - min]++;
        }

        for (int i = 0, sortedIndex = 0; i < buckets.length; i++) {
            while (buckets[i] > 0) {
                arr[sortedIndex++] = i + min;
                buckets[i]--;
            }
        }
    }

    public static void radix(int[] arr) {
        for (int e = 1; maxValue(arr) / e > 0; e *= 10) {
            counting(arr, arr.length, e);
        }
    }

    public static void shell(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap)
                    arr[j] = arr[j - gap];
                arr[j] = temp;
            }
        }
    }

    public static void pigeonhole(int[] arr) {
        int[] minMaxValues = minMaxValues(arr);
        int min = minMaxValues[0];
        int max = minMaxValues[1];
        int range = max - min + 1;
        int[] pigeonhole = new int[range];
        Arrays.fill(pigeonhole, 0);

        for (int i = 0; i < arr.length; i++)
            pigeonhole[arr[i] - min]++;

        for (int j = 0, index = 0; j < range; j++) {
            for (; pigeonhole[j] > 0; pigeonhole[j]--, index++)
                arr[index] = j + min;
        }
    }

    private static int minValue(int[] arr) {
        int min = arr[0];
        for (int n : arr) {
            if (n > min)
                min = n;
        }
        return min;
    }

    private static int maxValue(int[] arr) {
        int max = arr[0];
        for (int n : arr) {
            if (n > max)
                max = n;
        }
        return max;
    }

    private static int[] minMaxValues(int[] arr) {
        int min = arr[0];
        int max = arr[0];

        for (int n : arr) {
            if (n > max)
                max = n;
            else if (n < min)
                min = n;
        }
        return new int[]{min, max};
    }

    private static <T extends Comparable<T>> void swap(ArrayList<T> arr, int i, int j) {
        T temp = arr.get(i);
        arr.set(i, arr.get(i));
        arr.set(j, temp);
    }

    private static <T extends Comparable<T>> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}