package main.avl;

public class Node {
    public int value;
    public int height;
    public Node left;
    public Node right;
    public Node(int value) {
        this.value = value;
        this.height = 1;
    }
}
