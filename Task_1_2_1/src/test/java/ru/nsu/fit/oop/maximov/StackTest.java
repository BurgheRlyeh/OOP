package ru.nsu.fit.oop.maximov;

import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;
import java.util.Random;


import static org.junit.jupiter.api.Assertions.*;

public class StackTest  {
    private static Stack<Integer> stackCreate(int size) {
        var stack = new Stack<Integer>(size);
        for (int i = 0; i < size; ++i) {
            stack.push(i);
        }
        return stack;
    }

    @Test
    void pushPopTest() {
        Random rd = new Random();
        int size = rd.nextInt(100);
        var stack = stackCreate(size);  // stack with size items
        assertEquals(stack.size(), size);               // assert size

        stack.push(rd.nextInt());                       // size + 1 items
        assertEquals(stack.size(), size + 1);   // assert size (+1)
        stack.pop();

        for (int i = stack.size() - 1; stack.size() != 0; --i) {
            assertEquals(i, stack.pop());
        }


        assertThrows(EmptyStackException.class, stack::pop); // assert empty stack
    }

    @Test
    void pushPopStackTest() {
        var stack = new Stack<Integer>(0);

        Random rd = new Random();
        int sizeToPush = rd.nextInt(100);
        var stackToPush = stackCreate(sizeToPush);  // stack with size items

        stack.pushStack(stackToPush);                        // push stack1 to stack
        assertEquals(stack.size(), sizeToPush);              // assert all elements had been pushed

        var stackPopped = stack.popStack(2 * sizeToPush);   // try to pop more items than we have
        assertEquals(sizeToPush, stackPopped.size());                   // assert we still have sizeToPush items
        for (int i = stackPopped.size() - 1; stackPopped.size() != 0; --i) {
            assertEquals(i, stackPopped.pop());
        }

        stack.pushStack(stackToPush);                        // push no items
        assertEquals(stack.size(), stackToPush.size());      // assert size == 0

        // assert empty stack
        assertThrows(EmptyStackException.class,
                () -> { stack.popStack(100); } );

        // assert negative number to pop
        assertThrows(IllegalArgumentException.class,
                () -> { stack.popStack(-100); } );
    }
}
