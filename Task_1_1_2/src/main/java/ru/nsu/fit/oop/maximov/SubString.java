package ru.nsu.fit.oop.maximov;

import java.util.Arrays;

public class SubString {
    // Boyer-Moore-Horspool SubString Search
    /*
    return value
    -3 - text size smaller than pattern
    -2 - zero pattern size
    -1 - no entry of pattern in text
    otherwise - the first entry
     */
    public static int SubStr(String text, String pattern) {
        if (pattern.length() == 0) {
            return -2;
        }
        if (text.length() < pattern.length()) {
            return -3;
        }

        final short CHAR_SIZE = 256;        // size of char in bits
        int[] shift = new int[CHAR_SIZE];

        Arrays.fill(shift, pattern.length());

        for (int i = 0; i < pattern.length() - 1; ++i) {
            shift[pattern.charAt(i)] = pattern.length() - 1 - i;
        }

        for (int i = 0;
             i + pattern.length() <= text.length();
             i += shift[text.charAt(pattern.length() - 1 + i)])
        {
            for (int j = pattern.length() - 1;
                 text.charAt(i + j) == pattern.charAt(j);)
            {
                if (--j < 0) {
                    return i;
                }
            }
        }

        return -1;
    }
}
