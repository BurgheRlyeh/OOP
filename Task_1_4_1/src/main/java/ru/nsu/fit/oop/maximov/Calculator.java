package ru.nsu.fit.oop.maximov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static java.lang.Math.*;

public class Calculator {
    public static void main(String[] args) {
        System.out.println(calculate("sin + - 1 2 1"));
        System.out.println(calculate("- - + - + 3 14 28 * 58 7 / 113 3 -15"));
    }

    private static final ArrayList<String> unary = new ArrayList<>(Arrays.asList("log", "sqrt", "sin", "cos"));
    private static boolean isUnary(String operation) {
        return unary.contains(operation);
    }

    private static final ArrayList<String> binary = new ArrayList<>(Arrays.asList("+", "-", "*", "/", "pow"));
    private static boolean isBinary(String operation) {
        return binary.contains(operation);
    }

    public static boolean isNumber(String string) {
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

    static double calculate(String expression) {
        String[] atoms = expression.split(" ");
        Stack<Double> stack = new Stack<Double>();

        for (int i = atoms.length - 1; 0 <= i; --i) {
            String atom = atoms[i];

            // if atom is number
            if (isNumber(atom)) {
                // push in double number in stack
                stack.push(Double.parseDouble(atom));
                continue;
            }
            // atom isn't number
            double num = stack.pop();
            double res;

            if (isUnary(atom)) {
                res = switch(atom) {
                    case "log" -> log(num);
                    case "sqrt" -> sqrt(num);
                    case "sin" -> sin(num);
                    case "cos" -> cos(num);
                    default -> throw new IllegalArgumentException();
                };
            }
            else if (isBinary(atom)) {
                double num2 = stack.pop();

                res = switch(atom) {
                    case "+" -> num + num2;
                    case "-" -> num - num2;
                    case "*" -> num * num2;
                    case "/" -> num / num2;
                    case "pow" -> pow(num, num2);
                    default -> throw new IllegalArgumentException();
                };
            }
            else {
                throw new IllegalArgumentException();
            }

            stack.push(res);
        }

        return stack.pop();
    }
}
