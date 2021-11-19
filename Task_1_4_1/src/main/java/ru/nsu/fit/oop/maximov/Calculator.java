package ru.nsu.fit.oop.maximov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Calculator {
    public static void main(String[] args) {
        System.out.println(calculate("abracadabra"));
    }

    private static Stack<String> stack = new Stack<String>();
    private static void stringToStack(String expression) {
        String[] atoms = expression.split(" ");

        for (var i = atoms.length - 1; 0 <= i; --i) {
            stack.push(atoms[i]);
        }
    }

    static double calculate(String expression) {
        stringToStack(expression);

        var atom = stack.pop();
        return defineOperation(atom).calculate(stack);
    }

    private static Operation defineOperation(String operation) {
        return switch (operation) {
            case ("+") -> new Sum();
            case ("-") -> new Sub();
            case ("*") -> new Mult();
            case ("/") -> new Div();
            case ("log") -> new Log();
            case ("pow") -> new Pow();
            case ("sqrt") -> new Sqrt();
            case ("sin") -> new Sin();
            case ("cos") -> new Cos();
            default -> throw new IllegalArgumentException("Operation does not exist\n");
        };
    }

    private static boolean isNumber(String string) {
        if (string == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    static double calculateAtom(String atom) {
        if (isNumber(atom)) {
            return Double.parseDouble(atom);
        }
        return defineOperation(atom).calculate(stack);
    }
}
