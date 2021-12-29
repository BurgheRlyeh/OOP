package ru.nsu.fit.oop.maximov;

import java.util.*;

/**
 * Calculator
 * Main operation - calculate(String)
 */
public class Calculator {
    private static final Stack<String> stack = new Stack<>();

    private static final Map<String, Operation> operations = new HashMap<>() {{
        put("+", new Sum());
        put("-", new Sub());
        put("*", new Mult());
        put("/", new Div());
        put("log", new Log());
        put("pow", new Pow());
        put("sqrt", new Sqrt());
        put("sin", new Sin());
        put("cos", new Cos());
    }};

    /**
     * @param design - designation of new function
     * @param operation - Object of class that extends Operation
     */
    public void addOperation(String design, Object operation) {
        if (!operations.containsKey(design)) {
            operations.put(design, (Operation) operation);
        }
    }

    /**
     * @param expression string - expression in prefix form
     * @return result of calculations
     */
    static double calculate(String expression) {
        stringToStack(expression);
        return operations.get(stack.pop()).calculate(stack);
    }

    /**
     * @param expression for dividing on atoms to push in stack
     */
    private static void stringToStack(String expression) {
        Arrays.stream(expression.split(" ")).forEach(stack::push);
        Collections.reverse(stack);
    }

    /**
     * @param string atom
     * @return is atom a double number
     */
    private static boolean isNumber(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }

    /**
     * @param string atom
     * @return is atom an operation
     */
    private static boolean isOperation(String string) {
        return operations.containsKey(string);
    }

    /**
     * @param atom atom
     * @return result of atom's calculation
     */
    static double calculateAtom(String atom) {
        if (isNumber(atom)) {
            return Double.parseDouble(atom);
        }
        if (isOperation(atom)) {
            return operations.get(atom).calculate(stack);
        }
        return 0;
    }
}
