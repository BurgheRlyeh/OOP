import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import ru.nsu.fit.oop.maximov.heapsort.HeapSort;

import java.util.Arrays;

/*
Параметризированные тесты
Обработка NULL-pointer'а
 */

public class HeapSortTest {
    @Test   // NULL-pointer
    public void heapsortTestNull() {
        int[] arr = null;
        HeapSort.heapsort(arr);
    }

    @Test   // empty array
    public void heapsortTestEmpty() {
        int[] array = {};
        int[] result = {};
        HeapSort.heapsort(array);
        assertArrayEquals(array, result);
    }

    @Test   // array with boundary values
    public void heapsortTestBound() {
        int[] array = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
        int[] result = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        HeapSort.heapsort(array);
        assertArrayEquals(array, result);
    }

    @Test   // array with equal values
    public void heapsortTestEqual() {
        int[] array = {0, 0, 0, 0, 0};
        int[] result = {0, 0, 0, 0, 0};
        HeapSort.heapsort(array);
        assertArrayEquals(array, result);
    }

    @Test   // already sorted array
    public void heapsortTestSorted() {
        int[] array = {0, 1, 2, 3, 4};
        int[] result = {0, 1, 2, 3, 4};
        HeapSort.heapsort(array);
        assertArrayEquals(array, result);
    }

    @Test   // deployed sorted array
    public void heapsortTestDepSortedPos() {
        int[] array = {4, 3, 2, 1, 0};
        int[] result = {0, 1, 2, 3, 4};
        HeapSort.heapsort(array);
        assertArrayEquals(array, result);
    }

    @Test   // deployed sorted array with negative values
    public void heapsortTestDepSortedNeg() {
        int[] array = {0, -1, -2, -3, -4};
        int[] result = {-4, -3, -2, -1, 0};
        HeapSort.heapsort(array);
        assertArrayEquals(array, result);
    }

    @Test   // deployed sorted array with both negative and positive values
    public void heapsortTestDepSorted() {
        int[] array = {2, 1, 0, -1, -2};
        int[] result = {-2, -1, 0, 1, 2};
        HeapSort.heapsort(array);
        assertArrayEquals(array, result);
    }

    @Test   // array with both negative and positive values
    public void heapsortTest() {
        int[] array = {0, -1, 1, -2, 2};
        int[] result = {-2, -1, 0, 1, 2};
        HeapSort.heapsort(array);
        assertArrayEquals(array, result);
    }

}
