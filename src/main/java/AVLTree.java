import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class AVLTree<Key extends Comparable<Key>, Value>implements Map{

    private class Node<T>{
        Key key;
        Value value;
        int height;
        private int size;
        Node<T> left;
        Node<T> right;

        Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    private Node<Key> root = null;

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;
        else return node.size;
    }

    public int height() {
        return height(root);
    }
    private int height(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(Object key) {
        if (key == null) throw new IllegalArgumentException();
        return get(key) != null;
    }

    public boolean containsValue(Object value) { //!!!!
        return false;
    }

    public Object get(Object key) {
        return get(root, (Key) key);
    }

    private Object get(Node node, Key key) {
        if (key == null) throw new IllegalArgumentException();
        if (node == null) return null;
        int compare = key.compareTo((Key) node.key);
        if (compare < 0) return get(node.left, key);
        else if (compare > 0) return get(node.right, key);
        else return node.value;
    }

    public Object put(Object key, Object value) { // проверка баланса(!)
        if (key == null) throw new IllegalArgumentException();
        return root = put(root, (Key) key, (Value) value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) return new Node(key, value, 1);
        int compare = key.compareTo((Key) node.key);
        if (compare < 0) node.left  = put(node.left,  key, value);
        else if (compare > 0) node.right = put(node.right, key, value);
        else node.value = value;
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    public Object remove(Object key) {
        if (key == null) throw new IllegalArgumentException();
        return root = delete(root, (Key) key);
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;

        int compare = key.compareTo((Key) node.key);
        if  (compare < 0) node.left  = delete(node.left,  key);
        else if (compare > 0) node.right = delete(node.right, key);
        else {
            if (node.right == null) return node.left;
            if (node.left  == null) return node.right;
            Node t = node;
            node = min(t.right);
            node.right = deleteMin(t.right);
            node.left = t.left;
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException();
        return (Key) min(root).key;
    }

    private Node min(Node node) {
        if (node.left == null) return node;
        else return min(node.left);
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException();
        root = deleteMin(root);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException();
        root = deleteMax(root);
    }

    private Node deleteMax(Node node) {
        if (node.right == null) return node.left;
        node.right = deleteMax(node.right);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void putAll(Map m) {

    }

    public void clear() {
        root = null;
    }

    public Set keySet() {
        return null;
    }

    public Collection values() {
        return null;
    }

    public Set<Entry> entrySet() {
        return null;
    }

    public static void main(String[] args){
        AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
        tree.put(3, "Map");
        System.out.println(tree.get(3));
        System.out.println(tree.size());
    }
}