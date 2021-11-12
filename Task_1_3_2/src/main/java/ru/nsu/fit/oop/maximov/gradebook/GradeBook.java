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
        Null, Regular, Big
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
    private boolean isSemesterCorrect(int semester) {
        return 1 <= semester && semester <= 8;
    }

    public void putGrade(String subject, int grade) {
        putGrade(semesterNumber, subject, grade);
    }
    public void putGrade(int semesterNum, String subject, int grade) {
        var semester = getSemester(semesterNum);

        if (!isSemesterCorrect(semesterNum)) {
            throw new IllegalArgumentException("Illegal semester num");
        }
        if (!isGradeCorrect(grade)) {
            throw new IllegalArgumentException("Illegal grade");
        }
        if (semester.containsKey(subject)) {
            throw new KeyAlreadyExistsException();
        }

        semester.put(subject, grade);
    }

    public Integer getGrade(String subject) throws KeyException {
        return getGrade(semesterNumber, subject);
    }
    public Integer getGrade(int semesterNum, String subject) throws KeyException {
        var semester = getSemester(semesterNum);

        if (!isSemesterCorrect(semesterNum)) {
            throw new IllegalArgumentException("Illegal semester num");
        }
        if (!semester.containsKey(subject)) {
            throw new KeyException(subject + "is missing this semester");
        }

        return semester.get(subject);
    }

    public void replaceGrade(String subject, int grade) throws KeyException {
        replaceGrade(semesterNumber, subject, grade);
    }
    public void replaceGrade(int semesterNum, String subject, int grade) throws KeyException {
        var semester = getSemester(semesterNum);

        if (!isSemesterCorrect(semesterNum)) {
            throw new IllegalArgumentException("Illegal semester num");
        }
        if (!isGradeCorrect(grade)) {
            throw new IllegalArgumentException("Illegal grade");
        }
        if (!semester.containsKey(subject)) {
            throw new KeyException(subject + "is missing this semester");
        }

        semester.replace(subject, grade);
    }

    public void removeGrade(String subject) throws KeyException {
        removeGrade(semesterNumber, subject);
    }
    public void removeGrade(int semesterNum, String subject) throws KeyException {
        var semester = getSemester(semesterNum);

        if (!isSemesterCorrect(semesterNum)) {
            throw new IllegalArgumentException("Illegal semester num");
        }
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
        if (!isSemesterCorrect(semesterNum)) {
            throw new IllegalArgumentException("Illegal semester num");
        }
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
        if (!isSemesterCorrect(semesterNum)) {
            throw new IllegalArgumentException("Illegal semester num");
        }
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
        if (!isSemesterCorrect(semesterNum)) {
            throw new IllegalArgumentException("Illegal semester num");
        }
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
            return Scholarship.Null;
        }
        return gradeAverage() == 5.0 ?
                Scholarship.Big : Scholarship.Regular;
    }
    public boolean isScholarshipWillBeIncreased() throws Exception {
        var next = scholarshipNext();
        return scholarship == Scholarship.Null && next != Scholarship.Null ||
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
