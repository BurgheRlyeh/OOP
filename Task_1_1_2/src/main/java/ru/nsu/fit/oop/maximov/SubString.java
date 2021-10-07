package ru.nsu.fit.oop.maximov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.max;



public class SubString {
    public static void main(String[] args) {
//        String text = "GEEKS FOR GEEKS";
//        String pattern = "GEEK";
//        search(text, pattern);

        ArrayList<Integer> result = reader("/home/burgherlyeh/Programming/OOP/Task_1_1_2/src/main/java/ru/nsu/fit/oop/maximov/file.txt", "/");
        for (int i = 0; i < result.size(); ++i) {
            System.out.println(result.get(i));
        }
    }

    private static ArrayList<Integer> reader(String filepath, String pattern) {
        int patternLength = pattern.length();
        ArrayList<Integer> occurs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath), 2 * patternLength)) {
            char[] buffer = new char[2 * patternLength];
            reader.read(buffer, 0, 2 * patternLength);

            int bufferNum = 0;

            do {
                int shift = bufferNum * patternLength;
                int occurrence = search(new String(buffer), pattern);

                if (occurrence != -1 &&
                    (occurs.isEmpty() || occurs.get(occurs.size() - 1) != (shift + occurrence))) {
                    occurs.add(shift + occurrence);
                }
                System.arraycopy(buffer, patternLength, buffer, 0, patternLength);
                ++bufferNum;
            } while (reader.read(buffer, patternLength, patternLength) != -1);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return occurs;
    }

    public static int search(String text, String pattern) {
        String concat = pattern + "$" + text;
        int textLength = concat.length();
        int patternLength = pattern.length();

        int[] Z = new int[textLength];
        getZarr(concat.toCharArray(), Z);

        for(int i = 0; i < textLength; ++i){
            if (Z[i] == patternLength) {
                return i - patternLength - 1;
            }
        }

        return -1;
    }

    private static void getZarr(char[] str, int[] Z) {
        int n = str.length;
        int L = 0, R = 0;

        for(int i = 1; i < n; ++i) {
            if (i <= R && Z[i - L] < R - i + 1) {
                Z[i] = Z[i - L];
                continue;
            }

            R = max(L = i, R);
            while(R < n && str[R - L] == str[R]) {
                ++R;
            }
            Z[i] = R-- - L;
        }
    }
}
