package ru.nsu.fit.oop.maximov.gradebook;

import org.junit.jupiter.api.Test;

import java.security.KeyException;

import static org.junit.jupiter.api.Assertions.*;

public class GradeBookTest {
    static GradeBook emptyGradeBook() {
        return new GradeBook(0, "Name", "Surname");
    }
    static GradeBook myGradeBook() {
        var gradeBook = new GradeBook(200698, "Egor", "Maximov");

        // the 1st semester's grades
        gradeBook.gradePut("Введение в алгебру и анализ", 4);
        gradeBook.gradePut("Введение в дискретную математику и математическую логику", 5);
        gradeBook.gradePut("Декларативное программирование", 5);
        gradeBook.gradePut("Императивное программирование", 5);
        gradeBook.gradePut("Иностранный язык", 5);
        gradeBook.gradePut("Физическая культура и спорт", 5);
        gradeBook.gradePut("Физическая культура и спорт (элективная дисциплина)", 5);
        gradeBook.gradePut("Цифровые платформы", 5);
        gradeBook.gradePut("История", 5);
        gradeBook.gradePut("Основы культуры речи", 5);

        return gradeBook;
    }

    @Test
    void emptyGradeBookTest() {
        var book = emptyGradeBook();

        // try to put illegal grade
        assertThrows(IllegalArgumentException.class,
                () -> { book.gradePut("Some Subject", 6); } );

        // try to get "put" grade
        assertThrows(KeyException.class,
                () -> { book.gradeGet("Some Subject"); } );

        // try to replace some absent discipline
        assertThrows(KeyException.class,
                () -> { book.gradeReplace("Some Subject", 5); } );

        // try to remove some absent discipline
        assertThrows(KeyException.class,
                () -> { book.gradeRemove("Some Subject"); } );

        // try to count average grade
        assertThrows(Exception.class, book::gradeAverage);

        // try to find satisfactory
        assertThrows(Exception.class, book::isWithSatisfactory);

        // try to find out will scholarship will be increased
        assertThrows(Exception.class, book::isScholarshipWillBeIncreased);

        // try to find out can student get honor degree
        assertThrows(Exception.class, book::isCanGetHonorDegree);
    }

    @Test
    void myGradeBookTest() throws Exception {
        var book = myGradeBook();

        // put some subject with its grade
        book.gradePut("Some Subject", 3);

        // get and compare with put grade
        assertEquals(book.gradeGet("Some Subject"), 3);

        // replace grade
        book.gradeReplace("Some Subject", 4);
        assertEquals(book.gradeGet("Some Subject"), 4);

        // remove subject
        book.gradeRemove("Some Subject");
        assertFalse(book.containsSubject("Some Subject"));

        // count average grade
        assertEquals(4.9d, book.gradeAverage());

        // assert there grade book has no satisfactory grades
        assertFalse(book.isWithSatisfactory());

        // assert scholarship will not be increased
        assertFalse(book.isScholarshipWillBeIncreased());
        // replace 4 on 5
        book.gradeReplace("Введение в алгебру и анализ", 5);
        // assert scholarship will be increased
        assertTrue(book.isScholarshipWillBeIncreased());

        // assert student cannot get honor degree
        assertFalse(book.isCanGetHonorDegree());

        // set qualifying work result 4
        book.setQualifyingWork(4);
        // assert student still cannot get honor degree
        assertFalse(book.isCanGetHonorDegree());

        // set qualifying work result 5
        book.setQualifyingWork(5);
        // assert student can get honor degree
        assertTrue(book.isCanGetHonorDegree());

    }
}