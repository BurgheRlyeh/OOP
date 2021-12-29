package ru.nsu.fit.oop.maximov;

import java.util.Stack;

import static java.lang.Math.*;

abstract class Operation {
    /**
     * @param operands stack with expression's atoms
     * @return result of atom's calculation
     */
    abstract double calculate(Stack<String> operands);
}

class Sum extends Operation {
    double calculate(Stack<String> atoms){
        var a = Calculator.calculateAtom(atoms.pop());
        var b = Calculator.calculateAtom(atoms.pop());
        return a + b;
    }
}

class Sub extends Operation {
    double calculate(Stack<String> atoms){
        var a = Calculator.calculateAtom(atoms.pop());
        var b = Calculator.calculateAtom(atoms.pop());
        return a - b;
    }
}

class Mult extends Operation {
    double calculate(Stack<String> atoms){
        var a = Calculator.calculateAtom(atoms.pop());
        var b = Calculator.calculateAtom(atoms.pop());
        return a * b;
    }
}

class Div extends Operation {
    double calculate(Stack<String> atoms){
        var a = Calculator.calculateAtom(atoms.pop());
        var b = Calculator.calculateAtom(atoms.pop());
        return a / b;
    }
}

class Log extends Operation {
    double calculate(Stack<String> atoms){
        var num = Calculator.calculateAtom(atoms.pop());
        return log(num);
    }
}

class Pow extends Operation {
    double calculate(Stack<String> atoms){
        var num = Calculator.calculateAtom(atoms.pop());
        var pow = Calculator.calculateAtom(atoms.pop());
        return pow(num, pow);
    }
}

class Sqrt extends Operation {
    double calculate(Stack<String> atoms){
        var num = Calculator.calculateAtom(atoms.pop());
        return sqrt(num);
    }
}
class Sin extends Operation {
    double calculate(Stack<String> atoms){
        var num = Calculator.calculateAtom(atoms.pop());
        return sin(num);
    }
}

class Cos extends Operation {
    double calculate(Stack<String> atoms){
        var num = Calculator.calculateAtom(atoms.pop());
        return cos(num);
    }
}

