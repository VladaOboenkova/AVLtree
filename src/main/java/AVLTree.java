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

    @SuppressWarnings("unchecked")
    private int balanceFactor(Node node) {
        return height(node.left) - height(node.right);
    }

    public int height() {
        return height(root);
    }

    @SuppressWarnings("unchecked")
    private Node rightRotation(Node firstNode) {
        Node secondNode = firstNode.left;
        firstNode.left = secondNode.right;
        secondNode.right = firstNode;
        secondNode.size = firstNode.size;
        firstNode.size = 1 + size(firstNode.left) + size(firstNode.right);
        firstNode.height = 1 + Math.max(height(firstNode.left), height(firstNode.right));
        secondNode.height = 1 + Math.max(height(secondNode.left), height(secondNode.right));
        return secondNode;
    }

    @SuppressWarnings("unchecked")
    private Node leftRotation(Node firstNode) {
        Node secondNode = firstNode.right;
        firstNode.right = secondNode.left;
        secondNode.left = firstNode;
        secondNode.size = firstNode.size;
        firstNode.size = 1 + size(firstNode.left) + size(firstNode.right);
        firstNode.height = 1 + Math.max(height(firstNode.left), height(firstNode.right));
        secondNode.height = 1 + Math.max(height(secondNode.left), height(secondNode.right));
        return secondNode;
    }

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    public Object get(Object key) {
        return get(root, (Key) key);
    }

    @SuppressWarnings("unchecked")
    private Object get(Node node, Key key) {
        if (key == null) throw new IllegalArgumentException();
        if (node == null) return null;
        int compare = key.compareTo((Key) node.key);
        if (compare < 0) return get(node.left, key);
        else if (compare > 0) return get(node.right, key);
        else return node.value;
    }

    @SuppressWarnings("unchecked")
    public Object put(Object key, Object value) {
        if (key == null) throw new IllegalArgumentException();
        return root = put(root, (Key) key, (Value) value);
    }

    @SuppressWarnings("unchecked")
    private Node put(Node node, Key key, Value value) { // проверка баланса(!)
        if (node == null) return new Node(key, value, 1);
        int compare = key.compareTo((Key) node.key);
        if (compare < 0) node.left  = put(node.left,  key, value);
        else if (compare > 0) node.right = put(node.right, key, value);
        else node.value = value;
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    @SuppressWarnings("unchecked")
    public Object remove(Object key) {
        if (key == null) throw new IllegalArgumentException();
        return root = delete(root, (Key) key);
    }

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException();
        return (Key) min(root).key;
    }

    @SuppressWarnings("unchecked")
    private Node min(Node node) {
        if (node.left == null) return node;
        else return min(node.left);
    }

    @SuppressWarnings("unchecked")
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException();
        root = deleteMin(root);
    }

    @SuppressWarnings("unchecked")
    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    @SuppressWarnings("unchecked")
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException();
        root = deleteMax(root);
    }

    @SuppressWarnings("unchecked")
    private Node deleteMax(Node node) {
        if (node.right == null) return node.left;
        node.right = deleteMax(node.right);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void clear() {
        root = null;
    }

    public void putAll(Map m) {

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
