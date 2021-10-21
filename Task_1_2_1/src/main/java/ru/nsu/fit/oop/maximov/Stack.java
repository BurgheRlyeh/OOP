package ru.nsu.fit.oop.maximov;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * My stack realization on dynamically growing array
 * @param <T> generic type
 */
public class Stack<T> {
    private T[] stack;
    private int size = 0;

    private static final int DEFAULT_SIZE = 10;

    public Stack() {
        this(DEFAULT_SIZE);
    }

    /**
     * @param capacity size of stack
     */
    public Stack(int capacity) {
        stack = (T[])(new Object[capacity]);
    }

    /**
     * @param elem element to push in stack
     */
    public void push(T elem) {
        if (stack.length <= size) {
            stack = Arrays.copyOf(stack, size << 1);
        }
        stack[size++] = elem;
    }

    /**
     * @param src source to push in stack
     */
    public void pushStack(Stack<T> src) {
        if (stack.length <= size + src.size()) {
            stack = Arrays.copyOf(stack, size + src.size() << 1);
        }
        while (src.size() != 0) {
            stack[size++] = src.pop();
        }
    }

    /**
     * @return last added element in stack
     */
    public T pop() {
        if (size <= 0) {
            throw new EmptyStackException();
        }
        return stack[--size];
    }

    /**
     * @param num number of elements to pop
     * @return last num elements in stack
     */
    public Stack<T> popStack(int num) {
        if (size <= 0 && 0 < num) {
            throw new EmptyStackException();
        }
        if (num < 0) {
            throw new IllegalArgumentException();
        }
        if (size < num) {
            num = size;
        }
        Stack<T> out = new Stack<T>(num);
        for (int i = 0; i < num; ++i) {
            out.push(stack[--size]);
        }
        return out;
    }

    /**
     * @return number of elements in stack
     */
    public int size() {
        return size;
    }
}
