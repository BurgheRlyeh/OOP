package ru.nsu.fit.oop.maximov.composite;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

class Composite {
    static boolean isComposite(int number) {
        return !BigInteger.valueOf(number).isProbablePrime(1);
    }

    boolean isListContainsComposite(List<Integer> list) {
        return list.stream().anyMatch(Composite::isComposite);
    }

    long getTimeExecution(List<Integer> list) {
        var time0 = System.currentTimeMillis();
        isListContainsComposite(list);
        return System.currentTimeMillis() - time0;
    }
}

class ThreadComposite extends Composite {
    public static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();

    @Override
    public boolean isListContainsComposite(List<Integer> list) {
        return isListContainsComposite(list, MAX_THREADS);
    }

    public boolean isListContainsComposite(List<Integer> list, int threadsNumber) {
        threadsNumber = Math.min(threadsNumber, MAX_THREADS);

        var result = new AtomicBoolean();
        var threads = new ArrayList<Thread>();
        var sublistLength = Math.max(threadsNumber,
                list.size() / (threadsNumber - (list.size() % threadsNumber == 0 ? 0 : 1)));

        for (var i = 0; i < list.size(); i += sublistLength) {
            var sublist = list.subList(i,
                    i + Math.min(list.size() - i, sublistLength) - 1);

            var thread = new Thread(() -> {
                for (var number : sublist) {
                    if (result.get()) {
                        break;
                    }
                    if (isComposite(number)) {
                        result.set(true);
                        break;
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }

        return result.get();
    }

    public long getTimeExecution(List<Integer> list, int threadsNumber) {
        var time0 = System.currentTimeMillis();
        assert !isListContainsComposite(list, threadsNumber);
        return System.currentTimeMillis() - time0;
    }
}

class ParallelStreamComposite extends Composite {
    @Override
    public boolean isListContainsComposite(List<Integer> list) {
        return list.parallelStream().anyMatch(Composite::isComposite);
    }
}