package ru.nsu.fit.oop.maximov.primenumbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrimeTest {
    static public List<List<Integer>> arrays() {
        var list = new ArrayList<List<Integer>>();

        list.add(Arrays.asList(6, 8, 7, 13, 9, 4));
//        list.add(Arrays.asList(6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053));

        return list;
    }

    @ParameterizedTest
    @MethodSource("arrays")
    void comp(List<Integer> list) {
        boolean answer = true;
        System.out.println("SequentialSolution = " + SequentialSolution(list, answer));
        System.out.println("ThreadSolution = " + ThreadSolution(list, answer));
        System.out.println("ParallelStreamSolution = " + ParallelStreamSolution(list, answer));
    }

    public long SequentialSolution(Collection<Integer> collection, boolean answer) {
        long time0 = System.currentTimeMillis();

        boolean result = false;
        for (var num : collection) {
            if (Prime.isComposite(num)) {
                result = true;
                break;
            }
        }

        long time = System.currentTimeMillis();
        return result == answer ? time - time0 : -1;
    }

    private static final int MAX_THREADS = 6;
    public long ThreadSolution(List<Integer> list, boolean answer) {
        long time0 = System.currentTimeMillis();

        AtomicBoolean result = new AtomicBoolean(false);

        int chunkSize = (list.size() - 1) / MAX_THREADS + 1;
        final ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < MAX_THREADS; i++) {
            int beg = i * chunkSize;
            int end = Math.min(beg + chunkSize, list.size());

            Thread t = new Thread(() -> result.set(result.get() ||
                    Prime.isCollectionHasComposite(list.subList(beg, end))));
            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {}
        }

        long time = System.currentTimeMillis();
        return result.get() == answer ? time - time0 : -1;
    }

    public static long ParallelStreamSolution(Collection<Integer> collection, boolean answer) {
        long time0 = System.currentTimeMillis();

//        boolean result = Arrays.asList(arr)
//                .parallelStream()
//                .anyMatch(Prime::isComposite);

        boolean result = collection
                .parallelStream()
                .anyMatch(Prime::isComposite);

        long time = System.currentTimeMillis();
        return result == answer ? time - time0 : -1;
    }
}
