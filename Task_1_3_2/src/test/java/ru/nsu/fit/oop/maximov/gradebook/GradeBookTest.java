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
        gradeBook.putGrade("Введение в алгебру и анализ", 4);
        gradeBook.putGrade("Введение в дискретную математику и математическую логику", 5);
        gradeBook.putGrade("Декларативное программирование", 5);
        gradeBook.putGrade("Императивное программирование", 5);
        gradeBook.putGrade("Иностранный язык", 5);
        gradeBook.putGrade("Физическая культура и спорт", 5);
        gradeBook.putGrade("Физическая культура и спорт (элективная дисциплина)", 5);
        gradeBook.putGrade("Цифровые платформы", 5);
        gradeBook.putGrade("История", 5);
        gradeBook.putGrade("Основы культуры речи", 5);

        return gradeBook;
    }

    @Test
    void emptyGradeBookTest() {
        var book = emptyGradeBook();

        // try to put illegal grade
        assertThrows(IllegalArgumentException.class,
                () -> { book.putGrade("Some Subject", 6); } );

        // try to get "put" grade
        assertThrows(KeyException.class,
                () -> { book.getGrade("Some Subject"); } );

        // try to replace some absent discipline
        assertThrows(KeyException.class,
                () -> { book.replaceGrade("Some Subject", 5); } );

        // try to remove some absent discipline
        assertThrows(KeyException.class,
                () -> { book.removeGrade("Some Subject"); } );

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
        book.putGrade("Some Subject", 3);

        // get and compare with put grade
        assertEquals(book.getGrade("Some Subject"), 3);

        // replace grade
        book.replaceGrade("Some Subject", 4);
        assertEquals(book.getGrade("Some Subject"), 4);

        // remove subject
        book.removeGrade("Some Subject");
        assertFalse(book.containsSubject("Some Subject"));

        // count average grade
        assertEquals(4.9d, book.gradeAverage());

        // assert there grade book has no satisfactory grades
        assertFalse(book.isWithSatisfactory());

        // assert scholarship will not be increased
        assertFalse(book.isScholarshipWillBeIncreased());
        // replace 4 on 5
        book.replaceGrade("Введение в алгебру и анализ", 5);
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