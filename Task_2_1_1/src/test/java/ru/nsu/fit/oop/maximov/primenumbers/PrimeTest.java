package ru.nsu.fit.oop.maximov.primenumbers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static java.util.Collections.max;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PrimeTest extends Application {
    private static Double sequentialSolAvgTime = 0.0;
    private static List<Double> threadSolAvgTime;
    private static Double threadSolTotalAvgTime = 0.0;
    private static Double parallelStreamSolAvgTime = 0.0;
    private static final Long num = 1L; //testsProvider().count();
    private static int MAX_THREADS = 4;

    private Long sequentialSol(List<Integer> list, boolean answer) {
        var time0 = System.currentTimeMillis();

        var result = false;
        for (var num : list) {
            if (Prime.isComposite(num)) {
                result = true;
                break;
            }
        }

        var time = System.currentTimeMillis();
        return result == answer ? time - time0 : null;
    }
    private List<Long> threadSol(List<Integer> list, boolean answer) {
        var times = new ArrayList<>(Collections.nCopies(MAX_THREADS, 0L));
        var result = new AtomicBoolean();
        var threads = new ArrayList<Thread>();

        var step = Math.max(MAX_THREADS,
                list.size() / (MAX_THREADS - (list.size() % MAX_THREADS == 0 ? 0 : 1)));

        for (var i = 0; i < list.size(); i += step){
            var idx = i;

            var thread = new Thread() {
                @Override
                public void run() {
                    var time0 = System.currentTimeMillis();

                    for (var num : list.subList(idx, idx + Math.min(list.size() - idx, step) - 1)) {
                        if (isInterrupted()) {
                            break;
                        }
                        if (Prime.isComposite(num)) {
                            result.set(true);
                            break;
                        }
                    }

                    var time = System.currentTimeMillis();
                    times.add(time - time0);
                }
            };
            thread.start();
            threads.add(thread);
        }

        for (var thread : threads) {
            if (result.get()) {
                threads.forEach(Thread::interrupt);
                break;
            }
            try {
                thread.join();
            } catch (InterruptedException ignored) {}
        }

        return result.get() == answer ? times : null;
    }
    private static Long parallelStreamSol(List<Integer> list, boolean answer) {
        var time0 = System.currentTimeMillis();

        var result = list
                .parallelStream()
                .anyMatch(Prime::isComposite);

        var time = System.currentTimeMillis();
        return result == answer ? time - time0 : null;
    }

    private static Double timeToAvgFraction(Long time) {
        assertThat(time).isNotNull();
        return time.doubleValue() / num.doubleValue();
    }

    public void main(String[] args) {
        try {
            MAX_THREADS = Integer.parseInt(args[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {}
        threadSolAvgTime = new ArrayList<>(Collections.nCopies(MAX_THREADS, 0.0));

        var list = Arrays.asList(6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053,
                6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051);
        var answer = false;
        var threadSolTime = threadSol(list, answer);
        assertThat(threadSolTime).isNotNull();
        for (var i = 0; i < MAX_THREADS; ++i) {
            threadSolAvgTime.set(i, threadSolAvgTime.get(i) + timeToAvgFraction(threadSolTime.get(i)));
        }

        sequentialSolAvgTime += timeToAvgFraction(sequentialSol(list, answer));
        threadSolTotalAvgTime += timeToAvgFraction(max(threadSolTime));
        parallelStreamSolAvgTime += timeToAvgFraction(parallelStreamSol(list, answer));

        launch(args);
    }

//    private static Stream<Arguments> testsProvider() {
//        return Stream.of(
//                Arguments.of(
//                        Arrays.asList(6, 8, 7, 13, 9, 4),
//                        true
//                ),
//                Arguments.of(
//                        Arrays.asList(6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053,
//                                6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051),
//                        false
//                )
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("testsProvider")
//    void test(List<Integer> list, boolean answer) {
//        var threadSolTime = threadSol(list, answer);
//        assertThat(threadSolTime).isNotNull();
//        for (var i = 0; i < MAX_THREADS; ++i) {
//            threadSolAvgTime.set(i, threadSolAvgTime.get(i) + timeToAvgFraction(threadSolTime.get(i)));
//        }
//
//        sequentialSolAvgTime += timeToAvgFraction(sequentialSol(list, answer));
//        threadSolTotalAvgTime += timeToAvgFraction(max(threadSolTime));
//        parallelStreamSolAvgTime += timeToAvgFraction(parallelStreamSol(list, answer));
//    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Speed bebra");

        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Solution");

        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Speed");

        final BarChart<String, Number> bc = new BarChart<>(xAxis,yAxis);
        bc.setTitle("Country Summary");

        XYChart.Series series = new XYChart.Series();
        series.setName("2003");
        series.getData().add(new XYChart.Data("Sequential Sol Avg Time", sequentialSolAvgTime));
        series.getData().add(new XYChart.Data("Thread Sol Total Avg Time", threadSolTotalAvgTime));
        series.getData().add(new XYChart.Data("Parallel Stream Sol Avg Time", parallelStreamSolAvgTime));

        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series);
        stage.setScene(scene);
        stage.show();
    }
}
