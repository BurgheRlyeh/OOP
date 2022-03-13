package ru.nsu.fit.oop.maximov.pizzeria.testdata.synchronizedqueuetest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.oop.maximov.pizzeria.synchronizedqueue.SynchronizedQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SynchronizedQueueTest {
    static SynchronizedQueue<Object> queue = new SynchronizedQueue<>(20);

    @BeforeEach
    void cleaner() {
        queue.dequeue(queue.size());
    }

    @Test
    public void testEqProduceAndConsume() {
        new Thread(new Producer(10)).start();
        new Thread(new Consumer(10)).start();

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException ignored) {
        }
        assertEquals(0, queue.size());
    }

    @Test
    public void testMoreProduce() {
        new Thread(new Producer(20)).start();
        new Thread(new Consumer(10)).start();

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException ignored) {
        }
        assertEquals(0, queue.size());
    }

    @Test
    public void testMoreConsume() {
        new Thread(new Producer(10)).start();
        new Thread(new Consumer(20)).start();

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException ignored) {
        }
        assertEquals(0, queue.size());
    }

    record Producer(int testsNumber) implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < testsNumber; i++) {
                try {
                    queue.enqueue(new Object());
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    record Consumer(int testsNumber) implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < testsNumber; i++) {
                try {
                    queue.dequeue();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}