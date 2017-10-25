package labs.lab15;

import java.util.ArrayList;

import utils.SortAlgorithms;

public class Merge {
    
    public static void main(String[] args) {
        int[] arr = Maintainer.readFileIntoArray("res/labs/lab15/unsorted100000.txt", 100000);

        long start = System.currentTimeMillis();
        
        // sort(arr);

        ArrayList<Integer> arrList = new ArrayList<>();
        for (int n : arr)
            arrList.add(n);
        SortAlgorithms.merge(arrList);

        long end = System.currentTimeMillis();

        System.out.println("Merge sort time: " + (end - start));

        Maintainer.writeArrayToFile("res/labs/lab15/SortedByMerge.txt", arrList);
    }

    public static void sort(int[] arr) {
        if (arr == null)
            return;

        if (arr.length > 1) {
            int mid = arr.length / 2;
 
            // Split left part
            int[] left = new int[mid];
            for(int i = 0; i < mid; i++)
                left[i] = arr[i];
             
            // Split right part
            int[] right = new int[arr.length - mid];
            for (int i = mid; i < arr.length; i++)
                right[i - mid] = arr[i];


            sort(left);
            sort(right);
 
            int i = 0;
            int j = 0;
            int k = 0;
 
            // Merge left and right arrays
            while(i < left.length && j < right.length) {
                if (left[i] < right[j]) {
                    arr[k] = left[i];
                    i++;
                } else {
                    arr[k] = right[j];
                    j++;
                }
                k++;
            }

            // Collect remaining elements
            while(i < left.length) {
                arr[k] = left[i];
                i++;
                k++;
            }

            while(j < right.length) {
                arr[k] = right[j];
                j++;
                k++;
            }
        }
    }
}