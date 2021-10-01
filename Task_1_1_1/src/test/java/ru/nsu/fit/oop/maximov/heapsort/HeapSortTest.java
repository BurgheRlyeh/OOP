package ru.nsu.fit.oop.maximov.heapsort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HeapSortTest {
    static public ArrayList<int[]> arrays() {
        var list = new ArrayList<int[]>();

        list.add(new int[]{});  // empty array
        list.add(new int[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE});   // array with boundary values
        list.add(new int[]{0, 0, 0, 0, 0}); // array with equal values
        list.add(new int[]{0, 1, 2, 3, 4}); // already sorted array
        list.add(new int[]{4, 3, 2, 1, 0});  // reversed sorted array
        list.add(new int[]{0, -1, -2, -3, -4});  // reversed sorted array with negative values
        list.add(new int[]{2, 1, 0, -1, -2});   // reversed sorted array with both negative and positive values
        list.add(new int[]{0, -1, 1, -2, 2});   // array with both negative and positive values

        return list;
    }

    @ParameterizedTest
    @MethodSource("arrays")
    void comp(int[] array) {
        int[] result = array.clone();

        HeapSort.heapsort(array);   // sort array
        Arrays.sort(result, 0, result.length);  // sort by built-in sort

        assertArrayEquals(array, result);
    }

    @Test   // NULL-pointer
    public void heapsortTestNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    HeapSort.heapsort(null);
                }
        );
    }
}
