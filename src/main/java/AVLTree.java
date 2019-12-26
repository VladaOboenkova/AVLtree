import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class AVLTree<Key extends Comparable<Key>, Value> extends AbstractMap<Key, Value> {

    class Node<Key> {
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
            Node<Key> leftSubTree = node.left;
            if (balanceFactor(leftSubTree) > 0) node = rightRotation(node);
            else if (balanceFactor(leftSubTree) < 0) {
                leftSubTree = leftRotation(leftSubTree);
                node.left = leftSubTree;
                node = rightRotation(node);
            }
        } else if (balanceFactor(node) < -1) {
            Node<Key> rightSubTree = node.right;
            if (balanceFactor(rightSubTree) < 0) node = leftRotation(node);
            else if (balanceFactor(rightSubTree) > 0) {
                rightSubTree = rightRotation(rightSubTree);
                node.right = rightSubTree;
                node = leftRotation(node);
            }
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
        return get(root, k);
    }

    private Value get(Node<Key> node, Key key) {
        if (node == null) return null;
        int compare = key.compareTo(node.key);
        if (compare == 0) return node.value;
        else if (compare < 0) return get(node.left, key);
        else return get(node.right, key);
    }

    public Node<Key> find(Key key) {
        if (root == null) return null;
        return find(root, key);
    }

    private Node<Key> find(Node<Key> node, Key key) {
        if (root == null) return null;
        if (node == null) return null;
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
        }
        root = put(root, key, value);
        return value;
    }

    private Node<Key> put(Node<Key> node, Key key, Value value) {
        Node<Key> n = new Node<>(key, value);
        Node<Key> nodeA = find(root, key);
        assert nodeA != null;
        int compare = key.compareTo(nodeA.key);
        if (compare == 0) nodeA.value = value;
        else {
            insert(n);
            size++;
        }
        return balance(node);
    }

    private void insert(Node<Key> node) {
        if (root == null) root = node;
        else {
            Node<Key> current = root;
            Node<Key> parent;
            int compare = node.key.compareTo(current.key);
            while (true) {
                parent = current;
                if (compare < 0) {
                    current = current.left;
                    if (current == null) {
                        parent.left = node;
                        return;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        parent.right = node;
                        return;
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Value remove(Object key) {
        Value value = get(key);
        root = remove(root, (Key) key);
        return value;
    }

    private Node<Key> remove(Node<Key> node, Key key) {
        if (root == null) return null;
        Node<Key> current = root;
        Node<Key> parent = root;
        boolean isLeft = true;

        while (current.key != key) {
            parent = current;
            if (key.compareTo(current.key) < 0) {
                isLeft = true;
                current = current.left;
            } else {
                isLeft = false;
                current = current.right;
            }
            if (current == null)
                return null;
        }

        if (current.left == null && current.right == null) {
            if (current == root) {
                root = null;
            } else if (isLeft) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (current.right == null) {
            if (current == root) {
                root = current.left;
            } else if (isLeft) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        } else if (current.left == null) {
            if (current == root) {
                root = current.right;
            } else if (isLeft) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else {
            Node<Key> successor = getSuccessor(current);
            if (current == root) {
                root = successor;
            } else if (isLeft) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
            successor.left = current.left;
        }
        size--;
        return balance(current);
    }


    private Node<Key> getSuccessor(Node<Key> delNode) {
        Node<Key> successorParent = delNode;
        Node<Key> successor = delNode;
        Node<Key> current = delNode.right;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }
        if (successor != delNode.right) {
            successorParent.left = successor.right;
            successor.right = delNode.right;
        }
        return successor;
    }

    public void clear() {
        root = null;
    }

    public Set<Entry<Key, Value>> entrySet() {
        throw new NotImplementedException();
    }
}


