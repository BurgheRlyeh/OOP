package ru.nsu.fit.oop.maximov.gradebook;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.HashMap;

public class GradeBook {
    private final int id;         // grade book's id
    private final String name;    // student's name
    private final String surname; // student's surname
    private int semesterNumber;   // num of current semester
    private int qualifyingWork;   // qualifying work grade

    private final int SEMESTER_NUMBER = 8;

    private enum Scholarship {
        NULL, Regular, Big
    }
    private Scholarship scholarship;

    // HashMap<Semester, HashMap<Subject, Grade>>
    private final ArrayList<HashMap<String, Integer>> semesters = new ArrayList<>();

    /**
     * @param id id of grade book
     * @param name first name of student
     * @param surname surname of student
     */
    GradeBook(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;

        semesters.add(null);
        for (int i = 1; i <= SEMESTER_NUMBER; ++i) {
            semesters.add(new HashMap<>());
        }
        qualifyingWork = 0;

        semesterNumber = 1;
        scholarship = Scholarship.Regular;
    }

    private HashMap<String, Integer> getSemester(int semester) {
        return semesters.get(semester);
    }

    private boolean isGradeCorrect(int grade) {
        return 2 <= grade && grade <= 5;
    }

    public void gradePut(String subject, int grade) {
        gradePut(semesterNumber, subject, grade);
    }
    public void gradePut(int semesterNum, String subject, int grade) {
        var semester = getSemester(semesterNum);

        if (!isGradeCorrect(grade)) {
            throw new IllegalArgumentException("Illegal grade");
        }
        if (semester.containsKey(subject)) {
            throw new KeyAlreadyExistsException();
        }

        semester.put(subject, grade);
    }

    public Integer gradeGet(String subject) throws KeyException {
        return gradeGet(semesterNumber, subject);
    }
    public Integer gradeGet(int semesterNum, String subject) throws KeyException {
        var semester = getSemester(semesterNum);

        if (!semester.containsKey(subject)) {
            throw new KeyException(subject + "is missing this semester");
        }

        return semester.get(subject);
    }

    public void gradeReplace(String subject, int grade) throws KeyException {
        gradeReplace(semesterNumber, subject, grade);
    }
    public void gradeReplace(int semesterNum, String subject, int grade) throws KeyException {
        var semester = getSemester(semesterNum);

        if (!isGradeCorrect(grade)) {
            throw new IllegalArgumentException("Illegal grade");
        }
        if (!semester.containsKey(subject)) {
            throw new KeyException(subject + "is missing this semester");
        }

        semester.replace(subject, grade);
    }

    public void gradeRemove(String subject) throws KeyException {
        gradeRemove(semesterNumber, subject);
    }
    public void gradeRemove(int semesterNum, String subject) throws KeyException {
        var semester = getSemester(semesterNum);

        if (!semester.containsKey(subject)) {
            throw new KeyException(subject + "is missing this semester");
        }

        semester.remove(subject);
    }

    private HashMap<String, Integer> getAllSemesters() throws Exception {
        var map = new HashMap<String, Integer>();

        for (int i = 1; i <= SEMESTER_NUMBER; ++i) {
            var semester = getSemester(i);
            map.putAll(semester);
        }

        if (map.isEmpty()) {
            throw new Exception("No disciplines in this semester");
        }

        return map;
    }

    boolean containsSubjectInSemester(String subject) {
        return containsSubjectInSemester(semesterNumber, subject);
    }
    boolean containsSubjectInSemester(int semesterNum, String subject) {
        var semester = getSemester(semesterNum);
        return semester.containsKey(subject);
    }
    boolean containsSubject(String subject) throws Exception {
        var map = getAllSemesters();

        return map.containsKey(subject);
    }

    private double averageOfHashMap(HashMap<String, Integer> map) {
        int sum = 0;
        for (int grade : map.values()) {
            sum += grade;
        }

        return (double)sum / (double)map.size();
    }
    public double gradeAverageBySemester() throws Exception {
        return gradeAverageBySemester(semesterNumber);
    }
    public double gradeAverageBySemester(int semesterNum) throws Exception {
        var semester = getSemester(semesterNum);
        if (semester.isEmpty()) {
            throw new Exception("No disciplines in this semester");
        }

        return averageOfHashMap(getSemester(semesterNum));
    }
    public double gradeAverage() throws Exception {
        var map = getAllSemesters();
        return averageOfHashMap(map);
    }

    public boolean isWithSatisfactoryInSemester() throws Exception {
        return isWithSatisfactoryInSemester(semesterNumber);
    }
    public boolean isWithSatisfactoryInSemester(int semesterNum) throws Exception {
        var semester = getSemester(semesterNum);

        if (semester.isEmpty()) {
            throw new Exception("No disciplines in this semester");
        }

        return semester.containsValue(2) || semester.containsValue(3);
    }
    public boolean isWithSatisfactory() throws Exception {
        var map = getAllSemesters();

        return map.containsValue(2) || map.containsValue(3);
    }

    public Scholarship scholarshipNext() throws Exception {
        if (isWithSatisfactoryInSemester()) {
            return Scholarship.NULL;
        }
        return gradeAverage() == 5.0 ?
                Scholarship.Big : Scholarship.Regular;
    }
    public boolean isScholarshipWillBeIncreased() throws Exception {
        var next = scholarshipNext();
        return scholarship == Scholarship.NULL && next != Scholarship.NULL ||
                scholarship == Scholarship.Regular && next == Scholarship.Big;
    }

    public void semesterMoveNext() throws Exception {
        scholarship = scholarshipNext();
        ++semesterNumber;
    }

    public void setQualifyingWork(int grade) {
        if (!isGradeCorrect(grade)) {
            throw new IllegalArgumentException("Illegal grade");
        }
        qualifyingWork = grade;
    }

    public boolean isCanGetHonorDegree() throws Exception {
        return 4.75 <= gradeAverage() && !isWithSatisfactory() && qualifyingWork == 5;
    }
}
