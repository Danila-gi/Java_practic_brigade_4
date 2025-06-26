package main;

import main.avl.AvlTree;

public class Main {
    public static void main(String[] args) {
        AvlTree tree = new AvlTree();
        int[] data = {10, 5, 15, 2, 8};
        for (int num : data) {
            tree.insert(num);
            System.out.println("Inserted value " + num);
        }
        System.out.println("\nIn-order traversal:");
        tree.inOrderTraversal();

        System.out.println("\nPre-order traversal:");
        tree.preOrderTraversal();

        System.out.println("\nPost-order traversal:");
        tree.postOrderTraversal();

        System.out.println("\nDeleting values:" + data[1] + " ,then removing min/max in data");
        tree.remove(data[1]);
        tree.removeMin();
        tree.removeMax();

        System.out.println("\nIn-order traversal after deletions:");
        tree.inOrderTraversal();

        System.out.println("Check if tree contains values '10' and '100000'");
        System.out.println(tree.contains(10));
        System.out.println(tree.contains(10000));
    }
}
