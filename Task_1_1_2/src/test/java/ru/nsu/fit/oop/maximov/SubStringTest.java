package ru.nsu.fit.oop.maximov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SubStringTest {
    private static Stream<Arguments> providerWithException() throws FileNotFoundException {
        return Stream.of(
                // empty file & empty pattern
                Arguments.of(
                        new FileReader("EmptyFile.txt"),
                        "",
                        IllegalArgumentException.class
                ),
                // empty file
                Arguments.of(
                        new FileReader("EmptyFile.txt"),
                        "file",
                        IOException.class
                ),
                // empty pattern
                Arguments.of(
                        new FileReader("SmallFile.txt"),
                        "",
                        IllegalArgumentException.class
                )
        );
    }

    @ParameterizedTest
    @MethodSource("providerWithException")
    public void substringTestWithExceptions(Reader reader, String pattern, Class exception) {
        assertThrows(
                exception,
                () -> {
                    SubString.substring(reader, pattern);
                }
        );
    }

    private static Stream<Arguments> provider() throws FileNotFoundException {
        return Stream.of(
                // StringReader
                Arguments.of(
                        new StringReader("Small file. Not Big file."),
                        "file",
                        Arrays.asList(6, 20)
                ),
                // pattern = one symbol
                Arguments.of(
                        new FileReader("SmallFile.txt"),
                        "l",
                        Arrays.asList(3, 4, 8, 22)
                ),
                // small file
                Arguments.of(
                        new FileReader("SmallFile.txt"),
                        "file",
                        Arrays.asList(6, 20)
                ),
                // big file
                Arguments.of(
                        new FileReader("BigFile.txt"),
                        "clock will",
                        Arrays.asList(821, 884, 1109, 1184, 1517, 1563, 1821, 7114)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provider")
    public void findSubStringTest(Reader reader, String pattern, List<Integer> list) throws IOException {
        ArrayList<Integer> result = SubString.substring(reader, pattern);
        ArrayList<Integer> answer = new ArrayList<>(list);
        assertArrayEquals(result.toArray(), answer.toArray());
    }

    @Test   // big file by filepath
    public void bigFileByFilepath() throws IOException {
        ArrayList<Integer> result = SubString.substring("BigFile.txt", "clock will");
        ArrayList<Integer> answer = new ArrayList<>(Arrays.asList(821, 884, 1109, 1184, 1517, 1563, 1821, 7114));
        assertArrayEquals(result.toArray(), answer.toArray());
    }
}
