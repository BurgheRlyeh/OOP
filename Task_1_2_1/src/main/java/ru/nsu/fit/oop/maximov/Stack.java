package ru.nsu.fit.oop.maximov;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack<T> {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(5);
        stack.push(6);
        stack.push(7);
        System.out.println(stack.pop());
        Stack<Integer> stack789 = new Stack<>();
        stack789.push(7);
        stack789.push(8);
        stack789.push(9);
        stack.pushStack(stack789);
        while (stack.count() != 0) {
            System.out.println(stack.pop());
        }
    }

    private T[] stack;
    private int size = 0;
//    private int capacity = 10;

    public Stack() {
        stack = (T[])(new Object[10]);
    }

    public Stack(int capacity) {
        stack = (T[])(new Object[capacity]);
    }

    public void push(T elem) {
        if (stack.length <= size) {
            stack = Arrays.copyOf(stack, size <<= 1);
        }
        stack[size++] = elem;
    }

    public void pushStack(Stack<T> src) {
        if (stack.length <= size + src.count()) {
            stack = Arrays.copyOf(stack, size += src.count() << 1);
        }
        for (int i = 0; i < src.count(); ++i) {
            stack[size++] = src.pop();
        }
    }

    public T pop() {
        if (size <= 0) {
            throw new EmptyStackException();
        }
        return stack[--size];
    }

    public Stack<T> popStack(int num) {
        if (size < 0 && 0 < num) {
            throw new EmptyStackException();
        }
        if (num < 0) {
            throw new IllegalArgumentException();
        }
        if (size <= num) {
            num = size;
        }
        Stack<T> out = new Stack<T>(num);
        for (int i = size; num < i; --i) {
            out.push(stack[--size]);
        }
        return out;
    }

    public int count() {
        return size;
    }
}
