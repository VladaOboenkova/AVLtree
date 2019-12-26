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
}