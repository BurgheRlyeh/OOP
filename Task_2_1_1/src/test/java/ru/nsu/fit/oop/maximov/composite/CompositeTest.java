package ru.nsu.fit.oop.maximov.composite;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
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
    static void testPreparation() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/test/java/ru/nsu/fit/oop/maximov/composite/data/numbers.txt"));
            for (String text = reader.readLine(); text != null; text = reader.readLine()) {
                list.add(Integer.parseInt(text));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // warming up
        for (int i = 0; i < TESTS_NUMBER / 10; ++i) {
            new ThreadComposite().getTimeExecution(list);
        }
    }

    private static final int TESTS_NUMBER = 10000;

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

    private static final String format = "#0.00";

    @AfterAll
    static void createDiagram() {
        System.out.println("Average time for sequential solution:\t" +
                new DecimalFormat(format).format(sequentialSolutionAverageTime));

        System.out.println("Average time for thread solution:\t" +
                new DecimalFormat(format).format(threadSolutionAverageTime));

        System.out.println("Average time for each of threads:\t");
        for (var time : threadSolutionAverageTimes) {
            System.out.print("\t" + new DecimalFormat(format).format(time));
        }

        System.out.println("\nAverage time for parallel stream solution:" +
                new DecimalFormat(format).format(parallelStreamSolutionAverageTime));
    }
}
