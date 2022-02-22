package ru.nsu.fit.oop.maximov.composite;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.nsu.fit.oop.maximov.composite.ThreadComposite.MAX_THREADS;

public class CompositeTest {
    private static Double sequentialSolutionAverageTime = 0D;
    private static Double threadSolutionAverageTime = 0D;
    private static final List<Double> threadSolutionAverageTimes =
            new ArrayList<>(Collections.nCopies(MAX_THREADS, 0D));
    private static Double parallelStreamSolutionAverageTime = 0D;

    private static final List<Integer> list = new ArrayList<>();

    @BeforeAll
    static void testDataReader() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/java/ru/nsu/fit/oop/maximov/composite/numbers.txt"));
            for (String text = reader.readLine(); text != null; text = reader.readLine()) {
                list.add(Integer.parseInt(text));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final int TESTS_NUMBER = 100;

    @RepeatedTest(TESTS_NUMBER)
    void test() {
        sequentialSolutionAverageTime += new Composite().getTimeExecution(list) / (double) TESTS_NUMBER;

        var threadComposite = new ThreadComposite();
        threadSolutionAverageTime += new ThreadComposite().getTimeExecution(list) / (double) TESTS_NUMBER;
        for (var i = 0; i < MAX_THREADS; ++i) {
            threadSolutionAverageTimes.set(i, threadSolutionAverageTimes.get(i) +
                    threadComposite.getTimeExecution(list, i + 1) / (double) TESTS_NUMBER);
        }

        parallelStreamSolutionAverageTime += new ParallelStreamComposite().getTimeExecution(list) / (double) TESTS_NUMBER;
    }

    @AfterAll
    static void createDiagram() {
        // just println yet
        System.out.println(sequentialSolutionAverageTime);
        System.out.println(threadSolutionAverageTime);
        System.out.println(threadSolutionAverageTimes);
        System.out.println(parallelStreamSolutionAverageTime);
    }
}
