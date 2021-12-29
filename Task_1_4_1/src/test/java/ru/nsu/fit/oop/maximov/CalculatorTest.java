package ru.nsu.fit.oop.maximov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Stack;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    private static Stream<Arguments> provider() {
        return Stream.of(
                // expression from example
                Arguments.of(
                        "sin + - 1 2 1",
                        0.0
                ),
                Arguments.of(
                        "- - + - + 3 14 28 * 58 7 / 113 3 -15",
                        372.3333333333333
                ),
                Arguments.of(
                        "- 7 - 5 sqrt + 19 17",
                        8.0
                ),
                Arguments.of(
                        "pow 2 15",
                        32768.0
                ),
                Arguments.of(
                        "log 1000",
                        6.907755278982137
                ),
                Arguments.of(
                        "- 5 * 19 7",
                        -128.0
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provider")
    public void regularTest(String expression, double answer) {
        assertEquals(Calculator.calculate(expression), answer);
    }

    @Test
    public void TestNaN() {
        assertTrue(Double.isNaN(Calculator.calculate("cos log 0")));
    }

    @Test
    public void newOperationTest() {
        Calculator calculator = new Calculator();
        calculator.addOperation("tan", new Operation() {
            @Override
            double calculate(Stack<String> atoms) {
                var a = Calculator.calculateAtom(atoms.pop());
                return Math.tan(a);
            }
        });
        assertEquals(0.9999999999851034, Calculator.calculate("tan 0.78539816339"));
    }
}
