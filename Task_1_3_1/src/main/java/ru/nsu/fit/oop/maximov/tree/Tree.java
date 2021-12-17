package ru.nsu.fit.oop.maximov.tree;

import java.util.Collection;
import java.util.Iterator;

import static java.lang.Integer.max;

class Node<T> {
    public Node<T> left;
    public T key;
    public Node<T> right;
    public Integer height;

    public Node(T data) {
        this.key = data;
        height = 1;
    }

}

public class Tree<E extends Comparable> implements Collection {
    void preOrder(Node<E> node) {
        if (node == null) {
            return;
        }

        preOrder(node.left);
        System.out.print(node.key + " ");
        preOrder(node.right);
    }

    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>();

        /* Constructing tree given in the above figure */
        tree.root = tree.insert(tree.root, 10);
        tree.root = tree.insert(tree.root, 20);
        tree.root = tree.insert(tree.root, 30);
        tree.root = tree.insert(tree.root, 40);
        tree.root = tree.insert(tree.root, 50);
        tree.root = tree.insert(tree.root, 25);

        /* The constructed AVL Tree would be
             30
            /  \
          20   40
         /  \     \
        10  25    50
        */
        System.out.println("Preorder traversal" +
                " of constructed tree is : ");
        tree.preOrder(tree.root);

        tree.root = tree.remove(tree.root, 25);
        System.out.println("Preorder traversal" +
                " of constructed tree is : ");
        tree.preOrder(tree.root);
    }

    Node<E> root;
    int size;

    Integer height(Node<E> N) {
        return N == null ? 0 : N.height;
    }
    Integer balance(Node<E> N) {
        return N == null ? 0 : height(N.left) - height(N.right);
    }
    void updHeight(Node<E> node) {
        node.height = 1 + max(height(node.left), height(node.right));
    }

    Node<E> rotateLeft(Node<E> left) {
        Node<E> right = left.right;
        Node<E> T2 = right.left;

        right.left = left;
        left.right = T2;

        updHeight(left);
        updHeight(right);

        return right;
    }
    Node<E> rotateRight(Node<E> right) {
        Node<E> left = right.left;
        Node<E> T2 = left.right;

        left.right = right;
        right.left = T2;

        updHeight(left);
        updHeight(right);

        return left;
    }

    Node<E> insert(Node<E> node, E key) {
        // empty tree
        if (node == null) {
            return new Node<E>(key);
        }

        // less than - insert in left subtree
        if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key);
        }
        // greater than - insert in right subtree
        else if (0 < key.compareTo(node.key)) {
            node.right = insert(node.right, key);
        }
        // equals to - no duplicates
        else {
            return null;
        }

        // update height
        updHeight(node);

        if (node.left != null && balance(node) > 1 &&
                key.compareTo(node.left.key) != 0) {
            // double rotation (left-right)
            if (key.compareTo(node.left.key) > 0) {
                node.left = rotateLeft(node.left);
            }
            // simple right rotation
            return rotateRight(node);
        }
        if (node.right != null && balance(node) < -1 &&
                key.compareTo(node.right.key) != 0) {
            // double rotation (right-left)
            if (key.compareTo(node.right.key) < 0) {
                node.right = rotateRight(node.right);
            }
            // simple left rotation
            return rotateLeft(node);
        }

        return node;
    }

    Node<E> find(Node<E> node, E key) {
        if (node == null) {
            return null;
        }

        // less than
        if (key.compareTo(node.key) < 0) {
            return find(node.left, key);
        }
        // greater than
        else if (key.compareTo(node.key) > 0) {
            return find(node.right, key);
        }
        // equals to
        else {
            return node;
        }
    }

    Node<E> remove(Node<E> node, E key) {
        // empty tree
        if (node == null) {
            return node;
        }

        // less than - remove from left subtree
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
        }
        // greater than - insert in right subtree
        else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
        }
        // equals to
        else {
            // 0 or 1 child
            if (node.left == null || node.right == null) {
                node = node.left == null ? node.right : node.left;
            }
            // 2 children
            else {
                node.right = remove(node.right, node.key = find(node, key).key);
            }
        }

        // tree - only root
        if (node == null) {
            return node;
        }

        // update height
        updHeight(node);

        if (balance(node) > 1) {
            // double rotation (left-right)
            if (balance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            // simple right rotation
            return rotateRight(node);
        }
        if (balance(node) < -1) {
            // double rotation (right-left)
            if (0 < balance(node.right)) {
                node.right = rotateRight(node.right);
            }
            // simple left rotation
            return rotateLeft(node);
        }

        return node;
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

    @Override
    public Iterator<Node<E>> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        if (insert(root, (E) o) != null) {
            ++size;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (this.contains(o)) {
            remove(root, (E) o);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}