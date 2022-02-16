package ru.nsu.fit.oop.maximov.primenumbers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;

public class Prime {
    public static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();

    public static boolean isPrime(int n) {
//        return BigInteger.valueOf(n).isProbablePrime(1);
        return 1 < n && IntStream.range(2, (int) sqrt(n) + 1).allMatch(i -> n % i != 0);
    }
    public static boolean isComposite(int n) {
        return !isPrime(n);
    }

    // sequential solution
    public static boolean isListContainsCompositeSequentialSolution(List<Integer> list) {
        return list.stream().anyMatch(Prime::isComposite);
    }

    // thread solution
    public static boolean isListContainsCompositeThreadSolution(List<Integer> list) {
        return isListContainsCompositeThreadSolution(list, MAX_THREADS);
    }
    public static boolean isListContainsCompositeThreadSolution(List<Integer> list, int threadsNumber) {
        threadsNumber = Math.min(threadsNumber, MAX_THREADS);

        var result = new AtomicBoolean();
        var threads = new ArrayList<Thread>();
        var step = Math.max(threadsNumber,
                list.size() / (threadsNumber - (list.size() % threadsNumber == 0 ? 0 : 1)));

        for (var i = 0; i < list.size(); i += step){
            var idx = i;

            var thread = new Thread() {
                @Override
                public void run() {
                    for (var num : list.subList(idx, idx + Math.min(list.size() - idx, step) - 1)) {
                        if (isInterrupted()) {
                            break;
                        }
                        if (Prime.isComposite(num)) {
                            result.set(true);
                            break;
                        }
                    }
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

        return result.get();
    }

    // parallel stream solution
    public static boolean isListContainsCompositeParallelStreamSolution(List<Integer> list) {
        return list.parallelStream().anyMatch(Prime::isComposite);
    }
}
