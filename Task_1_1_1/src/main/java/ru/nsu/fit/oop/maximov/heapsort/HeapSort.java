package ru.nsu.fit.oop.maximov.heapsort;

public class HeapSort {
    /**
     * This method sorts given array in-place
     * @param arr Array to sort
     */
    static public void heapsort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException();
        }

        // heap building
        for (int i = arr.length / 2 - 1; 0 <= i; --i) {
            heapify(arr, i, arr.length);
        }

        // sorting
        for (int i = arr.length - 1; i > 0; i--) {
            // swap the root (max) with the last
            swap(arr, 0, i);

            // heapify left tree
            heapify(arr, 0, i);
        }
    }

    /**
     * @param arr Array to heapify
     * @param idx Index of node to heapify from
     * @param size Size of array
     */
    // heapify idx's subtree
    static void heapify(int[] arr, int idx, int size) {
        int max = idx; // Initialize max as root
        int left = 2 * idx + 1, right = left + 1; // children

        // choose the largest son
        if (left < size && arr[max] < arr[left]) {
            max = left;
        }
        if (right < size && arr[max] < arr[right]) {
            max = right;
        }

        // if parent is max - do nothing
        if (idx == max) {
            return;
        }

        swap(arr, idx, max);

        // heapify sub-tree
        heapify(arr, max, size);
    }

    /**
     * @param arr Array in which elements sholud be swapped
     * @param i Index of the 1st element
     * @param j Index of the 2nd element
     */
    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
