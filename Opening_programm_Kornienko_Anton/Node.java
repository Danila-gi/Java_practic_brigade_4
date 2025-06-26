package AVL;

public class Node {
    int val;
    Node left;
    Node right;
    int height;

    public Node(int val) {
        this(val, null, null);
    }

    public Node(int val, Node left, Node right) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.height = 1;
    }
}