package ru.nsu.fit.oop.maximov.tree;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

import static java.util.Collections.sort;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TreeTest {
    private static int MAX_SIZE = 10;
    private static int MAX_VALUE = 10;

    static ArrayList<HashSet<Integer>> provider() {
        Random random = new Random(System.currentTimeMillis());
        var array = new ArrayList<HashSet<Integer>>();

        for (int i = 0; i < MAX_SIZE; ++i) {
            var set = new HashSet<Integer>();
            for (int j = 0; j < MAX_SIZE; ++j) {
                set.add(random.nextInt() % MAX_VALUE);
            }
            array.add(set);
        }

        return array;
    }

    @ParameterizedTest(name = "Test tree on random array #{index}")
    @MethodSource("provider")
    void randomDataTest(Set<Integer> set) {
        Random random = new Random(System.currentTimeMillis());

        var tree = new Tree<>(set);

        // size
        assertThat(tree.size()).isEqualTo(set.size());
        // isEmpty
        assertThat(tree.isEmpty()).isEqualTo(set.isEmpty());

        // toArray
        var sorted = new ArrayList<>(set);
        sort(sorted);
        assertArrayEquals(tree.toArray(), sorted.toArray());

        var expected = sorted.stream()
                .filter(o -> o % 2 == 0)
                .map(Object::toString)
                .reduce(String::concat);
        var res = tree.stream()
                .filter(o -> o % 2 == 0)
                .map(Object::toString)
                .reduce(String::concat);
        assertThat(res).isEqualTo(expected);

        int idx = 0;
        for (var elem : tree) {
            assertThat(elem).isEqualTo(sorted.get(idx++));
        }

        int num = random.nextInt() % MAX_VALUE;
        assertThat(tree.add(num)).isEqualTo(set.add(num));
        assertThat(tree.contains(num)).isEqualTo(set.contains(num));
        assertThat(tree.remove(num)).isEqualTo(set.remove(num));

        var array = new ArrayList<Integer>();
        var size = random.nextInt() % MAX_SIZE;
        for (int i = 0; i < size; ++i) {
            array.add(random.nextInt() % MAX_VALUE);
        }
        assertThat(tree.addAll(array)).isEqualTo(set.addAll(array));
        assertThat(tree.containsAll(array)).isEqualTo(set.containsAll(array));
        assertThat(tree.removeAll(array)).isEqualTo(set.removeAll(array));
        for (int i = 0; tree.size() < i && i < size; ++i) {
            assertThat(tree.remove(sorted.get(i)))
                    .isEqualTo(set.remove(sorted.get(i)));
        }
    }

    @Test
    void exceptions() {
        var tree = new Tree(Arrays.asList(1, 2, 3));
        assertThrows(NullPointerException.class, () -> tree.add(null));
        assertThrows(NullPointerException.class, () -> tree.remove(null));
        assertThrows(NullPointerException.class, () -> tree.contains(null));
        assertThrows(ClassCastException.class, () -> tree.add(3.14));
        assertThrows(ClassCastException.class, () -> tree.remove("kcuf"));
        assertThrows(ClassCastException.class, () -> tree.contains(new Object()));

        assertThrows(ConcurrentModificationException.class, () -> {
            for (var elem : tree) {
                tree.add(0);
            }
        });

    }
}