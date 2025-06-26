package AVL;

public class AVLTree {
    private Node root;

    public AVLTree() {
        this.root = null;
    }

    public boolean check() {
        return checkNode(root);
    }

    private boolean checkNode(Node node) {
        if (node == null) return true;
        if (node.left == null && node.right == null) {
            return true;
        }
        if (node.left == null && (node.right.right != null || node.right.left != null)) {
            return false;
        }
        if (node.right == null && (node.left.right != null || node.left.left != null)) {
            return false;
        }

        Node left = node.left;
        while (left != null) {
            if (!checkNode(left)) {
                return false;
            }
            left = left.left;
        }

        Node right = node.right;
        while (right != null) {
            if (!checkNode(right)) {
                return false;
            }
            right = right.right;
        }
        return true;
    }


    public int diff() {
        IntList values = new IntList();
        passageOfNodes(values, root);
        return values.size() > 0 ? values.findMin() : 0;
    }

    private void passageOfNodes(IntList values, Node node) {
        if (node == null) return;

        if (node.right != null) {
            values.add(Math.abs(node.val - node.right.val));
            passageOfNodes(values, node.right);
        }

        if (node.left != null) {
            values.add(Math.abs(node.val - node.left.val));
            passageOfNodes(values, node.left);
        }
    }

    private int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    private void updateHeight(Node node) {
        if (node != null) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }
    }

    private Node rightRotate(Node node) {
        Node leftChild = node.left;
        Node rightChildLeft = leftChild.right;

        leftChild.right = node;
        node.left = rightChildLeft;

        updateHeight(node);
        updateHeight(leftChild);

        return leftChild;
    }

    private Node leftRotate(Node node) {
        Node rightChild = node.right;
        Node leftChildRight = rightChild.left;

        rightChild.left = node;
        node.right = leftChildRight;

        updateHeight(node);
        updateHeight(rightChild);

        return rightChild;
    }

    public void insert(int val) {
        System.out.println("Добавили значение: " + val);
        root = insertNode(val, root);
    }

    private Node insertNode(int val, Node node) {
        if (node == null) {
            return new Node(val);
        } else if (val < node.val) {
            node.left = insertNode(val, node.left);
        } else {
            node.right = insertNode(val, node.right);
        }

        return makeRotate(node);
    }

    public void delete(int val) {
        System.out.println("Удалили значение: " + val);
        root = deleteNode(val, root);
    }

    private Node deleteNode(int val, Node node) {
        if (node == null) {
            return null;
        }
        if (val < node.val) {
            node.left = deleteNode(val, node.left);
        } else if (val > node.val) {
            node.right = deleteNode(val, node.right);
        } else {
            if (node.right == null) {
                return node.left;
            }
            if (node.left == null) {
                return node.right;
            }

            Node temp = node.right;
            while (temp.left != null) {
                temp = temp.left;
            }

            node.val = temp.val;
            node.right = deleteNode(temp.val, node.right);
        }

        return makeRotate(node);
    }

    private Node makeRotate(Node node) {
        updateHeight(node);

        int heightDifference = getHeight(node.left) - getHeight(node.right);

        // Малый левый поворот
        if (heightDifference <= -2 && getHeight(node.right.left) <= getHeight(node.right.right)) {
            return leftRotate(node);
        }

        // Малый правый поворот
        if (heightDifference >= 2 && getHeight(node.left.right) <= getHeight(node.left.left)) {
            return rightRotate(node);
        }

        // Большой левый поворот
        if (heightDifference <= -2 && getHeight(node.right.left) > getHeight(node.right.right)) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // Большой правый поворот
        if (heightDifference >= 2 && getHeight(node.left.right) > getHeight(node.left.left)) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        return node;
    }
}