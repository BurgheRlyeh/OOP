package ru.nsu.fit.oop.maximov;

import java.io.*;
import java.util.ArrayList;
import static java.lang.Math.max;

public class SubString {
    /**
     * @param filepath filepath
     * @param pattern pattern to search in file
     * @return ArrayList of occurrences
     * @throws IOException
     */
    public static ArrayList<Integer> substring(String filepath, String pattern) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filepath), 2 * pattern.length());
        return substring(reader, pattern);
    }

    /**
     * @param reader reader with file
     * @param pattern pattern to search in file
     * @return ArrayList of occurrences
     * @throws IOException
     */
    public static ArrayList<Integer> substring(Reader reader, String pattern) throws IOException {
        int length = pattern.length();                            // length of the pattern
        ArrayList<Integer> result = new ArrayList<>();           // list with occurrences

        BufferedReader buffReader = new BufferedReader(reader, 2 * length);

        char[] buffer = new char[2 * length];                // window with 2 patternLength size
        // init buffer
        if (buffReader.read(buffer, 0, 2 * length) == -1) {
            throw new IOException();
        }

        String concat = pattern + "$" + new String(buffer); // concatenated string
        int[] Z = new int[concat.length()];                 // Z-array of concat's length
        zArray(concat.toCharArray(), Z, 0);          // init Z-array

        for (int bufferNum = 0;; ++bufferNum) {
            int shift = bufferNum * length;                 // calculate shift
            int occur = search(Z, length, concat.length()); // search occurrence

            // if occur exists && (1st occur || single occur)
            if (occur != -1 &&
                    (result.isEmpty() || result.get(result.size() - 1) != (shift + occur))) {
                result.add(shift + occur);                      // add to result list
            }
            System.arraycopy(buffer, length, buffer, 0, length);  // shift the buffer by length characters

            if (buffReader.read(buffer, length, length) == -1) {
                break;
            }

            concat = pattern + "$" + new String(buffer);    // update concat
            zArray(concat.toCharArray(), Z, length);       // update Z-array
        }

        return result;
    }

    /**
     * @param Z Z-array
     * @param patternLength length of pattern to search
     * @param concatLength length of concatenated string pattern$text
     * @return index of occurrence
     */
    public static int search(int[] Z, int patternLength, int concatLength) {
        for(int i = 0; i < concatLength; ++i) {
            // if full match of text and pattern
            if (Z[i] == patternLength) {
                return i - patternLength - 1;   // return occurrence's idx
            }
        }

        return -1;
    }

    /**
     * @param str concatenated string pattern$text
     * @param Z Z-array
     * @param begin index at which to start updating the z-array
     */
    private static void zArray(char[] str, int[] Z, int begin) {
        int n = str.length;         // length of string
        int L = 0, R = 0;           // substr = str[L, R - 1]

        for(int i = begin; i < n; ++i) {
            // recalculation based on previous values
            if (i <= R && Z[i - L] < R - i + 1) {
                Z[i] = Z[i - L];
                continue;
            }

            // manually calculation
            R = max(L = i, R);
            while(R < n && str[R - L] == str[R]) {
                ++R;
            }
            Z[i] = R-- - L;
        }
    }
}
