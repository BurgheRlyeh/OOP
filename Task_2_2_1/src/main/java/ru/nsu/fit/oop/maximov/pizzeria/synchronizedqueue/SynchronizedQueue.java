package ru.nsu.fit.oop.maximov.pizzeria.synchronizedqueue;

import java.util.*;

/**
 * @param <T> stored object type
 */
public class SynchronizedQueue<T> {
    private final Queue<T> queue;
    private final int capacity;

    public SynchronizedQueue() {
        this(Integer.MAX_VALUE);
    }

    public SynchronizedQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be natural number");
        }
        queue = new ArrayDeque<>();
        this.capacity = capacity;
    }

    /**
     * @param element element to put
     */
    public void enqueue(T element) {
        synchronized (queue) {
            while (queue.size() == capacity) {
                try {
                    queue.wait();
                } catch (InterruptedException ignored) {
                }
            }
            queue.add(element);
            queue.notifyAll();
        }
    }

    /**
     * @param elements collection of elements to put
     */
    public void enqueue(Collection<T> elements) {
        for (var element : elements) {
            enqueue(element);
        }
    }

    /**
     * @return first element of queue
     */
    public T dequeue() {
        T element;
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException ignored) {
                }
            }
            element = queue.remove();
            queue.notifyAll();
        }
        return element;
    }

    /**
     * @param number number of elements to pop
     * @return list of first number elements
     */
    public List<T> dequeue(int number) {
        List<T> list = new ArrayList<T>(number);
        for (int i = 0; i < number; ++i) {
            list.add(dequeue());
        }
        return list;
    }

    /**
     * @param number number of elements to try to pop
     * @return list of first number element of queue, or all elements if n is greater than the size of the queue
     */
    public List<T> tryDequeue(int number) {
        List<T> list;
        synchronized (queue) {
            while(queue.isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException ignored) {
                }
            }
            list = dequeue(Math.min(number, queue.size()));
            queue.notifyAll();
        }
        return list;
    }

    /**
     * @return current size of queue
     */
    public int size() {
        int size;
        synchronized (queue) {
            size = queue.size();
            queue.notifyAll();
        }
        return size;
    }
}
