package ru.nsu.fit.oop.maximov.primenumbers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.nsu.fit.oop.maximov.primenumbers.Prime.*;

public class PrimeTest {
    private static Double sequentialSolutionAverageTime = 0D;
    private static Double threadSolutionTotalAverageTime = 0D;
    private static final List<Double> threadSolutionAverageTime = new ArrayList<>(Collections.nCopies(Prime.MAX_THREADS, 0D));
    private static Double parallelStreamSolutionAverageTime = 0D;

    private static final Integer testsNumber = 1;

    private static Stream<Arguments> testsProvider() {
        List<Integer> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/java/ru/nsu/fit/oop/maximov/primenumbers/numbers.txt"));
            for (String text = reader.readLine(); text != null; text = reader.readLine()) {
                list.add(Integer.parseInt(text));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Stream.of(Arguments.of(list, false));
    }

    @ParameterizedTest
    @MethodSource("testsProvider")
//    @RepeatedTest(testsNumber)
    void test(List<Integer> list, boolean answer) {
        long time0;
        long time;

        time0 = System.currentTimeMillis();
        assertThat(isListContainsCompositeSequentialSolution(list)).isEqualTo(answer);
        time = System.currentTimeMillis();
        sequentialSolutionAverageTime += (time - time0) / testsNumber.doubleValue();

        time0 = System.currentTimeMillis();
        assertThat(isListContainsCompositeThreadSolution(list)).isEqualTo(answer);
        time = System.currentTimeMillis();
        threadSolutionTotalAverageTime += (time - time0) / testsNumber.doubleValue();

        for (var i = 0; i < MAX_THREADS; ++i) {
            time0 = System.currentTimeMillis();
            assertThat(isListContainsCompositeThreadSolution(list, i + 1)).isEqualTo(answer);
            time = System.currentTimeMillis();
            threadSolutionAverageTime.set(i, threadSolutionAverageTime.get(i) + (time - time0) / testsNumber.doubleValue());
        }

        time0 = System.currentTimeMillis();
        assertThat(isListContainsCompositeParallelStreamSolution(list)).isEqualTo(answer);
        time = System.currentTimeMillis();
        parallelStreamSolutionAverageTime += (time - time0) / testsNumber.doubleValue();
    }

    @AfterAll
    static void createDiagram() {
        // just println yet
        System.out.println(sequentialSolutionAverageTime);
        System.out.println(threadSolutionTotalAverageTime);
        System.out.println(threadSolutionAverageTime);
        System.out.println(parallelStreamSolutionAverageTime);
    }
}
