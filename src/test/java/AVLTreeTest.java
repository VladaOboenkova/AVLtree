import avl.AVLTree;
import org.junit.*;

public class AVLTreeTest {

    @SuppressWarnings("unchecked")
    @Test
    public void height() {
        AVLTree tree = new AVLTree();
        tree.put(3, "Apples");
        tree.put(4, "Up");
        tree.put(23, "Pine");
        tree.put(5, "Termit");
        tree.put(1, "Sun");

        Assert.assertEquals(tree.height(), 3);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void size() {
        AVLTree tree = new AVLTree();
        tree.put(3, "Apples");
        tree.put(4, "Up");
        tree.put(23, "Pine");
        tree.put(5, "Termit");
        tree.put(1, "Sun");

        Assert.assertEquals(tree.size(), 5);

        tree.remove(5);

        Assert.assertEquals(tree.size(), 4);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void get() {
        AVLTree tree = new AVLTree();
        tree.put(3, "Apples");
        tree.put(4, "Up");
        tree.put(23, "Pine");
        tree.put(5, "Termit");
        tree.put(1, "Sun");

        Assert.assertEquals(tree.get(3), "Apples");
        Assert.assertEquals(tree.get(4), "Up");
        Assert.assertNull(tree.get(6));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void put() {
        AVLTree tree = new AVLTree();
        tree.put(3, "Apples");
        tree.put(4, "Up");
        tree.put(23, "Pine");
        tree.put(5, "Termit");
        tree.put(1, "Sun");

        Assert.assertEquals(tree.put(3, "Apples"), "Apples");
        Assert.assertEquals(tree.put(5, "Termit"), "Termit");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void remove() {
        AVLTree tree = new AVLTree();
        tree.put(3, "Apples");
        tree.put(4, "Up");
        tree.put(23, "Pine");
        tree.put(5, "Termit");
        tree.put(1, "Sun");

        Assert.assertEquals(tree.remove(23), "Pine");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void balancePut() {
        AVLTree tree = new AVLTree();

        tree.put(3, "Apples");
        tree.put(4, "Up");
        tree.put(23, "Pine");
        tree.put(5, "Termit");
        tree.put(1, "Sun");

        Assert.assertEquals(4, tree.getRoot());

        tree.put(24, "K");

        Assert.assertEquals(4, tree.getRoot());

        AVLTree tree1 = new AVLTree();

        tree1.put(7, "Apples");
        tree1.put(10, "Up");
        tree1.put(4, "Pine");
        tree1.put(5, "Wee");
        tree1.put(6, "Sun");
        tree1.put(3, "f");

        Assert.assertEquals(5, tree1.getRoot());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void balanceRemove() {
        AVLTree tree = new AVLTree();

        tree.put(7, "Apples");
        tree.put(10, "Up");
        tree.put(4, "Pine");
        tree.put(5, "Wee");
        tree.put(6, "Sun");
        tree.put(3, "f");

        tree.remove(5);
        Assert.assertEquals(6, tree.getRoot());

        tree.remove(10);
        Assert.assertEquals(6, tree.getRoot());
    }
}