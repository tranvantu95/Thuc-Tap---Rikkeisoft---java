/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rikkeisoft.sorts;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Admin
 */
public class Sort {
    
    public static void main(String[] args) {
        
        //
        int n = 100;
        System.out.println("n = " + n);
        
        Integer[] arr = new Integer[n];
        
        for(int i = 0; i < n; i++) arr[i] = i;          // Best case
        
        Collections.reverse(Arrays.asList(arr));      // Worst case
        
//        Collections.shuffle(Arrays.asList(arr));      // Average case
        
        print(arr);
        
        //
        Sort sort = new Sort();
        
        sort.bubbleSort(Arrays.copyOf(arr, n));
        
        sort.insertSort(Arrays.copyOf(arr, n));
        
        sort.quickSort(Arrays.copyOf(arr, n));
        
        sort.selectionSort(Arrays.copyOf(arr, n));
        
        sort.mergeSort(Arrays.copyOf(arr, n));
        
        sort.arraysSort(Arrays.copyOf(arr, n));
        
        sort.collectionsSort(Arrays.copyOf(arr, n));
        
        System.out.println();
        print(arr);
    }
    
    public static <T> void print(T[] arr) {
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i].toString() + " ");
        }
        System.out.println();
    }
    
    public <T extends Comparable<T>> void bubbleSort(T[] arr) {
        System.out.println("\nBubble Sort:");
        long time = System.nanoTime();
        
        int n = arr.length; 
        
        System.out.println("Worst case: " + n*(n - 1)/2 + ", Best case: " + (n - 1));
        int loopCount = 0, swapCount = 0;
        
        boolean swap;
        
        do {
            swap = false;
            
            for (int i = 1; i < n; i++) {
                if(arr[i - 1].compareTo(arr[i]) > 0) {
                    swap(arr, i - 1, i);
                    swap = true;
                    
                    swapCount++;
                }
                
                loopCount++;
            }
            
//            if(swap) {
//                System.out.print("--> ");
//                print(arr);
//            }
            
            n--;
        } 
        while (swap && n > 1);
        
        System.out.print("--> ");
        print(arr);
                
        System.out.println("swapCount = " + swapCount);
        System.out.println("loopCount = " + loopCount);
        System.out.println("timeCount = " + formatNumber(System.nanoTime() - time));
        
        /*  Worst case
        *       (n - 1) + (n - 2) + ... + 1 + n - n
        *   <=> n^2 - (1 + 2 + ... + n)
        *   <=> n^2 - n(n + 1)/2
        *   <=> (n^2 - n)/2
        *   <=> n(n - 1)/2
        *
        *   Best case: n - 1;
        */
    }
    
    public <T extends Comparable<T>> void insertSort(T arr[]) {
        System.out.println("\nInsert Sort:");
        long time = System.nanoTime();
        
        int n = arr.length;
        
        System.out.println("Worst case: " + n*(n - 1)/2 + ", Best case: " + (n - 1));
        
        int loopCount = 0;
        
        T key;
        for (int i, j = 1; j < n; j++) {
            
            // Picking up the key(Card)
            key = arr[j];
            
            for(i = j - 1; i >= 0; i--) {
                loopCount++;
                
                if(key.compareTo(arr[i]) < 0) {
                    arr[i+1] = arr[i];
                }
                else break;
            }
            
            // Placing the key (Card) at its correct position in the sorted subarray
            arr[i+1] = key;
            
//            System.out.print("--> ");
//            print(arr);
        }
        
        System.out.print("--> ");
        print(arr);
        
        System.out.println("loopCount = " + loopCount);
        System.out.println("timeCount = " + formatNumber(System.nanoTime() - time));
    }
    
    // Merge Sort
    public <T extends Comparable<T>> void mergeSort(T[] arr) {
        System.out.println("\nMerge Sort:");
        long time = System.nanoTime();
        
        int  n = arr.length, left = 0, right = n - 1;
        T[] temp = Arrays.copyOf(arr, n);
        
        mergeSort(arr, temp, left, right);
        
        System.out.print("--> ");
        print(arr);
            
        System.out.println("timeCount = " + formatNumber(System.nanoTime() - time));
    }
    
    /**
     * This method implements the Generic Merge Sort
     *
     * @param arr The array to be sorted
     * @param temp The copy of the actual array
     * @param left The first index of the array
     * @param right The last index of the array
     * Recursively sorts the array in increasing order
     **/

    public <T extends Comparable<T>> void mergeSort(T[] arr, T[] temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, temp, left, mid);
            mergeSort(arr,  temp,mid + 1, right);
            merge(arr, temp, left, mid, right);
        }

    }

    /**
     * This method implements the merge step of the merge sort
     *
     * @param arr The array to be sorted
     * @param temp The copy of the actual array
     * @param left The first index of the array
     * @param mid The middle index of the array
     * @param right The last index of the array
     * merges two parts of an array in increasing order
     **/

    public <T extends Comparable<T>> void merge(T[] arr, T[] temp, int left, int mid, int right) {
        for (int i=left;i<=right;i++) {
            temp[i] = arr[i];
        }

        int i= left;
        int j = mid + 1;
        int k = left;

        while (i<=mid && j<=right) {
            if (temp[i].compareTo(temp[j]) <= 0) {
                arr[k] = temp[i];
                i++;
            }
            else {
                arr[k] = temp[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            arr[k] = temp[i];
            i++;
            k++;
        }
    }    
        
    // Quick Sort
    public <T extends Comparable<T>> void quickSort (T arr[]) {
        System.out.println("\nQuick Sort:");
        long time = System.nanoTime();
        
        quickSort(arr, 0, arr.length - 1);
        
        System.out.print("--> ");
        print(arr);
            
        System.out.println("timeCount = " + formatNumber(System.nanoTime() - time));
    }
    
    /**
     * This method implements the Generic Quick Sort
     *
     * @param array The array to be sorted
     * @param start The first index of an array
     * @param end The last index of an array
     * Sorts the array in increasing order
     **/

    public <T extends Comparable<T>> void quickSort (T array[], int start, int end) {
        if (start < end) {
            int PIndex = partition(array, start, end);
            quickSort(array, start, PIndex - 1);
            quickSort(array, PIndex + 1, end);
        }
    }

    /**
     * This method finds the partition index for an array
     *
     * @param array The array to be sorted
     * @param start The first index of an array
     * @param end The last index of an array
     * Finds the partition index of an array
     **/

    public <T extends Comparable<T>> int partition(T array[], int start, int end) {
        T pivot = array[end];
        int PIndex = start;
        for (int i=start;i<end;i++) {
            if (array[i].compareTo(pivot) <= 0) {
                swap(array, i, PIndex);
                PIndex++;
            }
        }
        swap(array, PIndex, end);
        return PIndex;
    }    
    
    /**
     * This method implements the Generic Selection Sort
     *
     * @param arr The array to be sorted
     * @param n The count of total number of elements in array
     * Sorts the array in increasing order
     **/

    public <T extends Comparable<T>> void selectionSort(T[] arr) {
        System.out.println("\nSelection Sort:");
        long time = System.nanoTime();
        
        int n = arr.length;

        for (int i=0;i<n-1;i++) {

            // Initial index of min
            int min = i;

            for (int j=i+1;j<n;j++) {
                if (arr[j].compareTo(arr[min]) < 0) {
                    min = j;
                }
            }

            // Swapping if index of min is changed
            if (min != i) {
                T temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }
        
        System.out.print("--> ");
        print(arr);
            
        System.out.println("timeCount = " + formatNumber(System.nanoTime() - time));
    }
    
    public <T> void swap(T[] arr, int index1, int index2) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
    
    public static String formatNumber(long l) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        return numberFormat.format(l);
    }
    
    public <T extends Comparable<T>> void arraysSort(T[] arr) {
        System.out.println("\nArrays Sort:");
        long time = System.nanoTime();
        
        Arrays.sort(arr);
        
        System.out.print("--> ");
        print(arr);
                
        System.out.println("timeCount = " + formatNumber(System.nanoTime() - time));
    }
    
    public <T extends Comparable<T>> void collectionsSort(T[] arr) {
        System.out.println("\nCollections Sort:");
        long time = System.nanoTime();
        
        List<T> list = Arrays.asList(arr);
        
        Collections.sort(list);
        
        System.out.print("--> ");
        print(list.toArray());
                
        System.out.println("timeCount = " + formatNumber(System.nanoTime() - time));
    }
}
