package utils;

import java.util.ArrayList;

public class SearchAlgorithms {

    public static <T extends Comparable<T>> int linear(ArrayList<T> arr, T value) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).compareTo(value) == 0)
                return i;
        }
        return -1;
    }
    
    public static int linear(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value)
                return i;
        }
        return -1;
    }

    public static <T extends Comparable<T>> int binary(ArrayList<T> arr, T value) {
        return binaryRecur(arr, 0, arr.size() - 1, value);
    }

    private static <T extends Comparable<T>> int binaryRecur(ArrayList<T> arr, int l, int r, T value) {
        if (r < l)
            return -1;

        int mid = l + (r - l) / 2;
        if (arr.get(mid).compareTo(value) > 0)
            return binaryRecur(arr, mid + 1, r, value);
        if (arr.get(mid).compareTo(value) < 0)
            return binaryRecur(arr, l, mid - 1, value);
        return mid;
    }

    public static int binary(int[] arr, int value) {
        return binaryRecur(arr, 0, arr.length - 1, value);
    }

    private static int binaryRecur(int[] arr, int l, int r, int value) {
        if (r < l)
            return -1;

        int mid = l + (r - l) / 2;
        if (arr[mid] > value)
            return binaryRecur(arr, mid + 1, r, value);
        if (arr[mid] < value)
            return binaryRecur(arr, l, mid - 1, value);
        return mid;
    }
    
    public static <T extends Comparable<T>> int ternary(ArrayList<T> arr, T value) {
        return ternaryRecur(arr, 0, arr.size() - 1, value);
    }

    private static <T extends Comparable<T>> int ternaryRecur(ArrayList<T> arr, int l, int r, T value) {
        if (r < l)
            return -1;

        int mid1 = l + (r - l) / 3;
        int mid2 = mid1 + (r - l) / 3;

        if (arr.get(mid1).compareTo(value) == 0)
            return mid1;

        if (arr.get(mid2).compareTo(value) == 0)
            return mid2;

        if (arr.get(mid1).compareTo(value) > 0)
            return ternaryRecur(arr, l, mid1 - l, value);

        if (arr.get(mid2).compareTo(value) < 0)
            return ternaryRecur(arr, mid2 + 1, r, value);

        return ternaryRecur(arr, mid1 + 1, mid2 - l, value);
    }
    
    public static int ternary(int[] arr, int value) {
        return ternaryRecur(arr, 0, arr.length - 1, value);
    }

    private static int ternaryRecur(int[] arr, int l, int r, int value) {
        if (r < l)
            return -1;

        int mid1 = l + (r - l) / 3;
        int mid2 = mid1 + (r - l) / 3;

        if (arr[mid1] == value)
            return mid1;

        if (arr[mid2] == value)
            return mid2;

        if (arr[mid1] > value)
            return ternaryRecur(arr, l, mid1 - l, value);

        if (arr[mid2] < value)
            return ternaryRecur(arr, mid2 + 1, r, value);

        return ternaryRecur(arr, mid1 + 1, mid2 - l, value);
    }

    public static <T extends Comparable<T>> int jump(ArrayList<T> arr, T value) {
        int step = (int)Math.floor(Math.sqrt(arr.size()));
        int i = 0;

        while (arr.get(Math.min(step, arr.size()) - 1).compareTo(value) < 0) {
            i = step;
            step *= 2;
            if (i >= arr.size());
                return -1;
        }

        while (arr.get(i).compareTo(value) < 0) {
            if (i++ == Math.min(step, arr.size()))
                return -1;
        }

        if (arr.get(i).compareTo(value) == 0)
            return i;
        
        return -1;
    }

    public static int jump(int[] arr, int value) {
        int step = (int)Math.floor(Math.sqrt(arr.length));
        int i = 0;

        while (arr[Math.min(step, arr.length) - 1] < value) {
            i = step;
            step *= 2;
            if (i >= arr.length);
                return -1;
        }

        while (arr[i] < value) {
            if (i++ == Math.min(step, arr.length))
                return -1;
        }

        if (arr[i] == value)
            return i;
        
        return -1;
    }

    public static <T extends Comparable<T>> int interpolation(ArrayList<T> arr, T value) {
        int l = 0;
        int r = arr.size() - 1;
        int pos;

        while (l <= r && arr.get(l).compareTo(value) <= 0 && arr.get(r).compareTo(value) >= 0) {
            pos = l + (((r - l) / (arr.get(r).compareTo(arr.get(l)))) * (value.compareTo(arr.get(l))));

            if (arr.get(pos).compareTo(value) == 0)
                return pos;
            
            if (arr.get(pos).compareTo(value) > 0)
                l = pos + 1;
            else
                r = pos - 1;
        }

        return -1;
    }

    public static int interpolation(int[] arr, int value) {
        int l = 0;
        int r = arr.length - 1;
        int pos;

        while (l <= r && value >= arr[l] && value <= arr[r]) {
            pos = l + (((r - l) / (arr[r] - arr[l])) * (value - arr[l]));

            if (arr[pos] == value)
                return pos;
            
            if (arr[pos] > value)
                l = pos + 1;
            else
                r = pos - 1;
        }

        return -1;
    }

    public static <T extends Comparable<T>> int exponential(ArrayList<T> arr, T value) {
        if (arr.get(0) == value)
            return 0;
        
        int i = 1;
        for (; i < arr.size() && arr.get(i).compareTo(value) <= 0; i *= 2) {}

        return binaryRecur(arr, i / 2, Math.min(i, arr.size()), value);
    }

    public static int exponential(int[] arr, int value) {
        if (arr[0] == value)
            return 0;
        
        int i = 1;
        for (; i < arr.length && arr[i] <= value; i *= 2) {}

        return binaryRecur(arr, i / 2, Math.min(i, arr.length), value);
    }

    public static <T extends Comparable<T>> int fibonacci(ArrayList<T> arr, T value) {
        int fibNm2 = 0;
        int fibNm1 = 1;
        int fibN = fibNm2 + fibNm1;

        while (fibN < arr.size()) {
            fibNm2 = fibNm1;
            fibNm1 = fibN;
            fibN = fibNm2 + fibNm1;
        }

        int offset = -1;
        int i;

        while (fibN > 1) {
            i = Math.min(offset + fibNm2, arr.size() - 1);

            if (arr.get(i).compareTo(value) < 0) {
                fibN = fibNm1;
                fibNm1 = fibNm2;
                fibNm2 = fibN - fibNm1;
                offset = i;
            } else if (arr.get(i).compareTo(value) > 0) {
                fibN = fibNm2;
                fibNm1 = fibNm1 - fibNm2;
                fibNm2 = fibN - fibNm1;
            } else {
                return i;
            }
        }

        if (fibNm1 != 0 && arr.get(offset + 1).compareTo(value) == 0)
            return offset + 1;

        return -1;
    }

    public static int fibonacci(int[] arr, int value) {
        int fibNm2 = 0;
        int fibNm1 = 1;
        int fibN = fibNm2 + fibNm1;

        while (fibN < arr.length) {
            fibNm2 = fibNm1;
            fibNm1 = fibN;
            fibN = fibNm2 + fibNm1;
        }

        int offset = -1;
        int i;

        while (fibN > 1) {
            i = Math.min(offset + fibNm2, arr.length - 1);

            if (arr[i] < value) {
                fibN = fibNm1;
                fibNm1 = fibNm2;
                fibNm2 = fibN - fibNm1;
                offset = i;
            } else if (arr[i] > value) {
                fibN = fibNm2;
                fibNm1 = fibNm1 - fibNm2;
                fibNm2 = fibN - fibNm1;
            } else {
                return i;
            }
        }

        if (fibNm1 != 0 && arr[offset + 1] == value)
            return offset + 1;

        return -1;
    }
}