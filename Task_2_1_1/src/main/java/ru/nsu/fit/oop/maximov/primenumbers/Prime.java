package ru.nsu.fit.oop.maximov.primenumbers;

import java.util.Collection;

public class Prime {
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }

        for (int i = 2; i < n; i++)
            if (n % i == 0)
                return false;

        return true;
    }
    public static boolean isCollectionHasPrime(Collection<Integer> collection) {
        for (var num : collection) {
            if (isPrime(num)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isComposite(int n) {
        return !isPrime(n);
    }
    public static boolean isCollectionHasComposite(Collection<Integer> collection) {
        for (var num : collection) {
            if (isComposite(num)) {
                return true;
            }
        }
        return false;
    }
}