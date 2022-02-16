package ru.nsu.fit.oop.maximov.primenumbers;

import java.math.BigInteger;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;

public class Prime {
    public static boolean isPrime(int n) {
        return BigInteger.valueOf(n).isProbablePrime(1);
//        return 1 < n && IntStream.range(2, (int) sqrt(n) + 1).allMatch(i -> n % i != 0);
    }
    public static boolean isComposite(int n) {
        return !isPrime(n);
    }
}
