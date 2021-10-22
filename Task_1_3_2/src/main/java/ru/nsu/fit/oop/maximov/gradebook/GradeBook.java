package ru.nsu.fit.oop.maximov.gradebook;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.HashMap;

public class GradeBook {
    private int id;         // grade book's id
    private String name;    // student's name
    private String surname; // student's surname
    private int semesterNumber;   // num of current semester
    private int qualifyingWork;   // qualifying work grade

    private final int SEMESTER_NUMBER = 8;

    private enum Scholarship {
        NULL, Regular, Big
    }
    private Scholarship scholarship;

    // HashMap<Semester, HashMap<Subject, Grade>>
    private final ArrayList<HashMap<String, Integer>> semesters = new ArrayList<>();

    GradeBook(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;

        semesters.add(null);
        for (int i = 1; i < SEMESTER_NUMBER + 1; ++i) {
            semesters.add(new HashMap<>());
        }
        qualifyingWork = 0;

        semesterNumber = 1;
        scholarship = Scholarship.Regular;
    }

    HashMap<String, Integer> getSemester(int semester) {
        return semesters.get(semester);
    }

    boolean isGradeCorrect(int grade) {
        return 2 <= grade || grade <= 5;
    }

    void gradePut(String subject, int grade) {
        gradePut(semesterNumber, subject, grade);
    }
    void gradePut(int semesterNum, String subject, int grade) {
        var semester = getSemester(semesterNum);

        if (!isGradeCorrect(grade)) {
            throw new IllegalArgumentException("Illegal grade");
        }
        if (semester.containsKey(subject)) {
            throw new KeyAlreadyExistsException();
        }

        semester.put(subject, grade);
    }

    void gradeReplace(String subject, int grade) throws KeyException {
        gradeReplace(semesterNumber, subject, grade);
    }
    void gradeReplace(int semesterNum, String subject, int grade) throws KeyException {
        var semester = getSemester(semesterNum);

        if (!isGradeCorrect(grade)) {
            throw new IllegalArgumentException("Illegal grade");
        }
        if (!semester.containsKey(subject)) {
            throw new KeyException(subject + "is missing this semester");
        }

        semester.replace(subject, grade);
    }

    void gradeRemove(String subject) throws KeyException {
        gradeRemove(semesterNumber, subject);
    }
    void gradeRemove(int semesterNum, String subject) throws KeyException {
        var semester = getSemester(semesterNum);

        if (!semester.containsKey(subject)) {
            throw new KeyException(subject + "is missing this semester");
        }

        semester.remove(subject);
    }

    double gradeAverageBySemester() {
        return gradeAverageBySemester(semesterNumber);
    }
    double gradeAverageBySemester(int semesterNum) {
        var semester = getSemester(semesterNum);

        int sum = 0;
        for (int grade : semester.values()) {
            sum += grade;
        }

        return (double)sum / (double)semester.size();
    }
    double gradeAverage() {
        double average = 0.0;
        for (int i = 1; i < SEMESTER_NUMBER; ++i) {
            average += gradeAverageBySemester(i);
        }
        return average;
    }

    boolean isWithSatisfactoryInSemester() {
        return isWithSatisfactoryInSemester(semesterNumber);
    }
    boolean isWithSatisfactoryInSemester(int semesterNum) {
        var semester = getSemester(semesterNum);

        for (int grade : semester.values()) {
            if (grade == 3) {
                return true;
            }
        }

        return false;
    }
    boolean isWithSatisfactory() {
        for (int i = 1; i < SEMESTER_NUMBER; ++i) {
            if (isWithSatisfactoryInSemester(i)) {
                return true;
            }
        }
        return false;
    }

    Scholarship scholarshipNext() {
        if (isWithSatisfactoryInSemester()) {
            return Scholarship.NULL;
        }
        return gradeAverage() == 5.0 ?
                Scholarship.Big : Scholarship.Regular;
    }
    boolean isScholarshipWillBeIncreased() {
        var next = scholarshipNext();
        return scholarship == Scholarship.NULL && next != Scholarship.NULL ||
                scholarship == Scholarship.Regular && next == Scholarship.Big;
    }

    void semesterMoveNext() {
        scholarship = scholarshipNext();
        ++semesterNumber;
    }

    void setQualifyingWork(int grade) {
        if (!isGradeCorrect(grade)) {
            throw new IllegalArgumentException("Illegal grade");
        }
        qualifyingWork = grade;
    }

    boolean isCanGetHonorDegree() {
        return 4.75 <= gradeAverage() && !isWithSatisfactory() && qualifyingWork == 5;
    }
}
