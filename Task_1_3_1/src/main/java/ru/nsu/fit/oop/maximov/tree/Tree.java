package ru.nsu.fit.oop.maximov.tree;

import java.util.*;
import java.util.function.Consumer;

public class Tree<E extends Comparable<E>> implements Collection<E> {
    public static void main(String[] args) {
        var array = new ArrayList<Integer>();
        var splitter = array.spliterator();
    }

    class Node {
        private Node left;

        private E key;
        private Node anc;   // ancestor
        private int height;

        private Node right;

        public Node(E data, Node anc) {
            this.key = data;
            this.anc = anc;
            height = 1;
        }
    }

    private Node root = null;
    private int size = 0;
    private boolean isTreeChanged = false;

    private int height(Node node) {
        return node == null ? -1 : node.height;
    }
    private int balance(Node node) {
        return node == null ? 0 : height(node.right) - height(node.left);
    }
    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }
    private void updateAncestors(Node x, Node y, Node z) {
        x.anc = y.anc;
        y.anc = x;
        z.anc = y;
    }

    private Node rotateLeft(Node y) {
        Node x = y.right;
        Node z = x.left;

        x.left = y;
        y.right = z;

        updateAncestors(x, y, z);
        updateHeight(y);
        updateHeight(x);

        return x;
    }
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;

        x.right = y;
        y.left = z;

        updateAncestors(x, y, z);
        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private Node rebalance(Node node) {
        if (node == null) {
            return null;
        }

        updateHeight(node);
        if (balance(node) < -1) {
            if (height(node.left.left) <= height(node.left.right)) {
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
        }
        else if (balance(node) > 1) {
            if (height(node.right.right) <= height(node.right.left)) {
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        }

        return node;
    }

    private Node min(Node node) {
        if (node == null) {
            return null;
        }
        return node.left != null ? min(node.left) : node;
    }
    private Node max(Node node) {
        if (node == null) {
            return null;
        }
        return node.right != null ? max(node.right) : node;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object o) {
        return find(root, (E) o) != null;
    }
    private Node find(Node node, E key) {
        // no such key
        if (node == null) {
            return null;
        }

        // equals to - key found
        if (key.equals(node.key)) {
            return node;
        }

        // less than and greater than
        return find(key.compareTo(node.key) < 0 ? node.left : node.right, key);
    }

//    public Spliterator<E> spliterator() {
//        return new Spliterator<>() {
//            public static int ORDERED       = 0b10000000;
//            public static int DISTINCT      = 0b01000000;
//            public static int SORTED        = 0b00100000;
//            public static int SIZED         = 0b00010000;
//            public static int NONNULL       = 0;
//            public static int IMMUTABLE     = 0;
//            public static int CONCURRENT    = 0;
//            public static int SUBSIZED      = 0;
//
//            {
//
//            }
//
//            @Override
//            public boolean tryAdvance(Consumer<? super E> action) {
//                return false;
//            }
//
//            @Override
//            public Spliterator<E> trySplit() {
//                return null;
//            }
//
//            @Override
//            public long estimateSize() {
//                return 0;
//            }
//
//            @Override
//            public int characteristics() {
//                return 0;
//            }
//        };
//    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node node;
            private final Node last;

            {
                node = min(root);
                last = max(root);
                isTreeChanged = false;
            }

            private void checkChanges() {
                if (isTreeChanged) {
                    throw new RuntimeException("Tree structure was upgraded");
                }
            }

            @Override
            public boolean hasNext() {
                checkChanges();
                return node != last;
            }

            @Override
            public E next() {
                checkChanges();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                if (node.right != null) {
                    node = min(node.right);
                }
                else if (node != root) {
                    Node prev = node;
                    node = node.anc;
                    if (prev == node.right) {
                        node = node.anc;
                    }
                }

                return node.key;
            }
        };
    }


    @Override
    public Object[] toArray() {
        var array = new Object[size];

        int idx = 0;
        for (E e : this) {
            array[idx++] = e;
        }

        return array;
    }

    @Override
    public boolean add(E o) {
        if ((root = insert(root, o, null)) == null) {
            return false;
        }
        ++size;
        return isTreeChanged = true;
    }
    private Node insert(Node node, E key, Node anc) {
        if (node == null) {
            return new Node(key, anc);
        }

        // less than - insert in left subtree
        if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, node);
        }
        // greater than - insert in right subtree
        else if (0 < key.compareTo(node.key)) {
            node.right = insert(node.right, key, node);
        }
        // equals to - no duplicates
        else {
            return null;
        }

        return rebalance(node);
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) {
            return false;
        }
        delete(root, (E) o);
        --size;
        return isTreeChanged = true;
    }
    private Node delete(Node node, E key) {
        if (node == null) {
            return null;
        }

        // less than - remove from left subtree
        if (key.compareTo(node.key) < 0) {
            node.left = delete(node.left, key);
        }
        // greater than - insert in right subtree
        else if (key.compareTo(node.key) > 0) {
            node.right = delete(node.right, key);
        }
        // equals to
        else {
            // 0 or 1 child
            if (node.left == null || node.right == null) {
                if (node.left != null) {
                    node.left.anc = node.anc;
                    node = node.left;
                }
                else {
                    node.right.anc = node.anc;
                    node = node.right;
                }
            }
            // 2 children
            else {
                node.right = delete(node.right, node.key = min(node.right).key);
            }
        }
        node = rebalance(node);
        return node;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isChanged = false;

        for (E e : c) {
            if (add(e)) {
                isChanged = true;
            }
        }

        return isChanged;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
        isTreeChanged = false;
    }

    @Override
    public boolean retainAll(Collection c) {
        boolean isChanged = false;
        for (E e : this) {
            if (!c.contains(e)) {
                remove(e);
                isChanged = true;
            }
        }
        return isChanged;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean isChanged = false;
        for (Object obj : c) {
            if (remove(obj)) {
                isChanged = true;
            }
        }
        return isChanged;
    }

    @Override
    public boolean containsAll(Collection c) {
        for (Object obj : c) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < size) {
            array = Arrays.copyOf(array, size);
        }

        int idx = 0;
        for (E e : this) {
            array[idx++] = (T) e;
        }

        while (idx < array.length) {
            array[idx++] = null;
        }

        return array;
    }
}
