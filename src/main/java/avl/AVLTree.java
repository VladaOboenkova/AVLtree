package avl;

import java.util.*;

public class AVLTree<Key extends Comparable<Key>, Value> extends AbstractMap<Key, Value> {

    private class Node<Key> {
        Key key;
        Value value;
        Node<Key> left = null;
        Node<Key> right = null;

        Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<Key> root = null;

    public Key getRoot() {
        return root.key;
    }

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    private int balanceFactor(Node<Key> node) {
        return height(node.left) - height(node.right);
    }

    private Node<Key> balance(Node<Key> node) {
        if (balanceFactor(node) > 1) {
            if (balanceFactor(node.left) > 0) return rightRotation(node);
            else if (balanceFactor(node.left) < 0) return leftRightRotation(node);
        }
        else if (balanceFactor(node) < -1) {
            if (balanceFactor(node.right) < 0) return leftRotation(node);
            else if (balanceFactor(node.right) > 0) return rightLeftRotation(node);
        }
        return node;
    }

    private Node<Key> rightRotation(Node<Key> firstNode) {
        Node<Key> secondNode = firstNode.left;
        firstNode.left = secondNode.right;
        secondNode.right = firstNode;
        return secondNode;
    }

    private Node<Key> leftRotation(Node<Key> firstNode) {
        Node<Key> secondNode = firstNode.right;
        firstNode.right = secondNode.left;
        secondNode.left = firstNode;
        return secondNode;
    }

    private Node<Key> leftRightRotation(Node<Key> firstNode) {
        Node<Key> secondNode = firstNode.left;
        firstNode.left = leftRotation(secondNode);
        return rightRotation(firstNode);
    }

    private Node<Key> rightLeftRotation(Node<Key> firstNode){
        Node<Key> secondNode = firstNode.right;
        firstNode.right = rightRotation(secondNode);
        return leftRotation(firstNode);
    }

    public int height() {
        return height(root);
    }

    private int height(Node<Key> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Value get(Object key) {
        if (root == null) return null;
        Key k = (Key) key;
        if (!containsKey(k)) return null;
        return get(root, k);
    }

    private Value get(Node<Key> node, Key key) {
        int compare = key.compareTo(node.key);
        if (compare == 0) return node.value;
        else if (compare < 0) return get(node.left, key);
        else return get(node.right, key);
    }

    private Node<Key> find(Key key) {
        if (root == null) return null;
        return find(root, key);
    }

    private Node<Key> find(Node<Key> node, Key key) {
        int compare = key.compareTo(node.key);
        if (compare == 0) return node;
        else if (compare < 0) {
            if (node.left == null) return node;
            return find(node.left, key);
        } else {
            if (node.right == null) return node;
            return find(node.right, key);
        }
    }

    public Value put(Key key, Value value) {
        if (root == null) {
            root = new Node<>(key, value);
            size++;
            return value;
        }
        root = put(root, key, value);
        size++;
        return value;
    }

     private Node<Key> put(Node<Key> node, Key key, Value value){
         if (node == null) return new Node<>(key, value);
         if (containsKey(key)) node.value = value;
         if (key.compareTo(node.key) < 0) {
             node.left = put(node.left, key, value);
         }
         else if (key.compareTo(node.key) > 0) {
             node.right = put(node.right, key, value);
         }
         return balance(node);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean containsKey(Object key) {
        Key k = (Key) key;
        Node<Key> node = find(k);
        return node != null && k.compareTo(node.key) == 0;
    }

    @SuppressWarnings("unchecked")
    public Value remove(Object key){
        if (root == null) return null;
        if (!containsKey(key)) return null;
        Value value = get(key);
        root = remove(root,(Key) key);
        size--;
        return value;
    }

    private Node<Key> remove(Node<Key> node, Key key) {
        if (node == null) return null;
        if (key.compareTo(node.key) < 0) node.left = remove(node.left, key);
        else if (key.compareTo(node.key) > 0) node.right = remove(node.right, key);
        else {
            Node<Key> leftSubTree = node.left;
            Node<Key> rightSubTree = node.right;
            if (rightSubTree == null) return leftSubTree;
            else {
                Node<Key> successor = min(rightSubTree);
                successor.right = delete(rightSubTree);
                successor.left = leftSubTree;
                return balance(successor);
            }
        }
        return balance(node);
    }

    private Node<Key> delete(Node<Key> node) {
        if (node.left == null) return node.right;
        node.left = delete(node.left);
        return balance(node);
    }

    private Node<Key> min(Node<Key> node) {
        if (node.left == null) return node;
        return min(node.left);
    }

    public void clear() {
        root = null;
    }

    private EntrySet entrySet;

    public Set<Map.Entry<Key, Value>> entrySet() {
        EntrySet es = entrySet;
        return ((es != null) ? es : (entrySet = new EntrySet()));
    }

    static class Entry<Key,Value> implements Map.Entry<Key,Value> {
        Key key;
        Value value;

        Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        public Value setValue(Value value) {
            Value oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

        class EntrySet extends AbstractSet<Map.Entry<Key, Value>> {

         @Override
         public MyIterator iterator() {
             return new MyIterator();
         }

         @Override
         public int size() {
             return AVLTree.this.size();
         }
     }

    private class MyIterator implements Iterator<Map.Entry<Key, Value>> {

        Stack<Node> stack;

        private MyIterator() {
            stack = new Stack<>();
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Entry<Key, Value> next() {
            Node<Key> node = stack.pop();
            Entry<Key, Value> result = new Entry<>(node.key, node.value);
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            return result;
        }
    }
}


