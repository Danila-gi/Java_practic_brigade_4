package main.avl;

public class AvlTree {
    private Node root;

    private int getHeight(Node current) {
        return current != null ? current.height : 0;
    }

    private void fixHeight(Node current) {
        int leftHeight = getHeight(current.left);
        int rightHeight = getHeight(current.right);
        current.height = 1 + Math.max(leftHeight, rightHeight);
    }

    private int balanceFactor(Node current) {
        return getHeight(current.right) - getHeight(current.left);
    }

    private Node rotateRight(Node current) {
        Node leftChild = current.left;
        current.left = leftChild.right;
        leftChild.right = current;
        fixHeight(current);
        fixHeight(leftChild);
        return leftChild;
    }

    private Node rotateLeft(Node current) {
        Node rightChild = current.right;
        current.right = rightChild.left;
        rightChild.left = current;
        fixHeight(current);
        fixHeight(rightChild);
        return rightChild;
    }

    private Node balance (Node current){
        fixHeight(current);
        if (balanceFactor(current) == 2) {
            if (balanceFactor(current.right) < 0) {
                current.right = rotateRight(current);
            }
            return rotateLeft(current);
        }

        if (balanceFactor(current) == -2) {
            if (balanceFactor(current.left) > 0) {
                current.left = rotateLeft(current);
            }
            return rotateRight(current);
        }

        return current;
    }

    private boolean contains(Node current, int value) {
        if (current == null) return false;
        if (current.value > value) return contains(current.left, value);
        if (current.value < value) return contains(current.right, value);
        return true;
    }
    private Node findMin(Node current) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node removeMin(Node current) {
        if (current.left == null) return current.right;
        current.left = removeMin(current.left);
        return balance(current);
    }

    private Node removeMax(Node current) {
        if (current.right == null) return current.left;
        current.right = removeMax(current.right);
        return balance(current);
    }

    private Node insert(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }
        if (current.value > value) {
            current.left = insert(current.left, value);
        } else if (current.value < value) {
            current.right = insert(current.right, value);
        } else {
            return current;
        }
        return balance(current);
    }

    private Node remove(Node current, int value) {
        if (current == null) return null;
        if (current.value > value) {
            current.left = remove(current.left, value);
        } else if (current.value < value) {
            current.right = remove(current.right, value);
        } else {
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;
            Node min = findMin(current.right);
            min.right = removeMin(current.right);
            min.left = current.left;
            return balance(min);
        }

        return balance(current);
    }

    private void inOrderTraversal(Node current) {
        if (current != null) {
            inOrderTraversal(current.left);
            System.out.print(current.value + " ");
            inOrderTraversal(current.right);

        }
    }

    private void postOrderTraversal(Node current) {
        if (current != null) {
            postOrderTraversal(current.left);
            postOrderTraversal(current.right);
            System.out.print(current.value + " ");
        }
    }

    private void preOrderTraversal(Node current) {
        if (current != null) {
            System.out.print(current.value + " ");
            preOrderTraversal(current.left);
            preOrderTraversal(current.right);
        }
    }

    public void insert(int value) {
        root = insert(root, value);
    }

    public void remove(int value) {
        if (root == null) return;
        root = remove(root, value);
    }

    public void removeMax() {
        if (root == null) return;
        root = removeMax(root);
    }

    public void removeMin() {
        if (root == null) return;
        root = removeMin(root);
    }

    public boolean contains(int value) {
        return contains(root, value);
    }

    public void inOrderTraversal() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        inOrderTraversal(root);
        System.out.println();
    }

    public void preOrderTraversal() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        preOrderTraversal(root);
        System.out.println();
    }

    public void postOrderTraversal() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        postOrderTraversal(root);
        System.out.println();
    }
}
