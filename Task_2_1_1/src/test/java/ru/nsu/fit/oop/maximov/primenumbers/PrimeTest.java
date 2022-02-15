package ru.nsu.fit.oop.maximov.primenumbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class PrimeTest {
    private static Stream<Arguments> testsProvider() {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(6, 8, 7, 13, 9, 4),
                        true
                ),
                Arguments.of(
                        Arrays.asList(6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053,
                                6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053,
                                6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053,
                                6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053,
                                6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053,
                                6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053,
                                6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053,
                                6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053,
                                6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053),
                        false
                )
        );
    }

    @ParameterizedTest
    @MethodSource("testsProvider")
    void comp(List<Integer> list, boolean answer) {
        System.out.println("SequentialSolution = " + SequentialSolution(list, answer));
        System.out.println("ThreadSolution = " + ThreadSolution(list, answer));
        System.out.println("ParallelStreamSolution = " + ParallelStreamSolution(list, answer));
    }

    public long SequentialSolution(List<Integer> list, boolean answer) {
        var time0 = System.currentTimeMillis();

        var result = false;
        for (var num : list) {
            if (Prime.isComposite(num)) {
                result = true;
                break;
            }
        }

        var time = System.currentTimeMillis();
        return result == answer ? time - time0 : -1;
    }

    private static final int MAX_THREADS = 3;
    public List<Long> ThreadSolution(List<Integer> list, boolean answer) {

        var result = new AtomicBoolean();
        var threads = new ArrayList<Thread>();
        var times = new ArrayList<Long>();

        var step = Math.max(MAX_THREADS,
                list.size() / (MAX_THREADS - (list.size() % MAX_THREADS == 0 ? 0 : 1)));

        for (var i = 0; i < list.size(); i += step){
            var idx = i;

            Thread thread = new Thread() {
                @Override
                public void run() {
                    var time0 = System.currentTimeMillis();

                    for (var num : list.subList(idx, idx + Math.min(list.size() - idx, step) - 1)) {
                        if (isInterrupted()) {
                            break;
                        }
                        if (Prime.isComposite(num)) {
                            result.set(true);
                            break;
                        }
                    }

                    var time = System.currentTimeMillis();
                    times.add(time - time0);
                }
            };
            thread.start();
            threads.add(thread);
        }

        for (var thread : threads) {
            if (result.get()) {
                threads.forEach(Thread::interrupt);
                break;
            }
            try {
                thread.join();
            } catch (InterruptedException ignored) {}
        }

        return result.get() == answer ? times : null;
    }

    public static long ParallelStreamSolution(List<Integer> list, boolean answer) {
        long time0 = System.currentTimeMillis();

        boolean result = list
                .parallelStream()
                .anyMatch(Prime::isComposite);

        long time = System.currentTimeMillis();
        return result == answer ? time - time0 : -1;
    }
}
