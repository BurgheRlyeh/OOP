package ru.nsu.fit.oop.maximov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubStringTest {
    @Test   // empty string & empty substring
    public void substrTest0() {
        String str = "";
        String sub = "";
        int result = -2;
        assertEquals(SubString.SubStr(str, sub), result);
    }

    @Test   // empty string
    public void substrTest1() {
        String str = "";
        String sub = "pqr";
        int result = -3;
        assertEquals(SubString.SubStr(str, sub), result);
    }

    @Test   // empty substring
    public void substrTest2() {
        String str = "abcdefghijklmnopqrstuvwxyz";
        String sub = "";
        int result = -2;
        assertEquals(SubString.SubStr(str, sub), result);
    }

    @Test   // 3 letters from alphabet
    public void substrTest3() {
        String str = "abcdefghijklmnopqrstuvwxyz";
        String sub = "pqr";
        int result = 15;
        assertEquals(SubString.SubStr(str, sub), result);
    }
}
