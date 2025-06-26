package AVL;

public class Main {
    public static void main(String[] args) {
        testInsertAndBalance();      // Тест вставки и балансировки
        testDeleteAndBalance();      // Тест удаления и балансировки
        testCheckAVLStructure();     // Тест корректности структуры AVL
        testDiffWithEmptyTree();     // Тест разницы в пустом дереве
        testDiffWithSingleNode();    // Тест разницы с одним узлом
        testDiffWithMultipleNodes(); // Тест разницы с несколькими узлами
        testComplexInsertDelete();   // Комплексный тест вставки и удаления
    }

    /**
     * Тест вставки и автоматической балансировки.
     * - Создается дерево, вставляются 10, 20, 30.
     * - После вставки 30 должен произойти левый поворот.
     * - Проверяется, что дерево остается AVL-деревом (check() == true).
     */
    public static void testInsertAndBalance() {
        System.out.println("=== Тест вставки и балансировки ===");
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30); // Должен вызвать левый поворот

        boolean isBalanced = tree.check();
        System.out.println("Дерево сбалансировано после вставки? " + isBalanced);
        System.out.println();
    }

    /**
     * Тест удаления и балансировки.
     * - Вставляются 10, 5, 15, 20.
     * - Удаляется 5.
     * - Проверяется, что дерево остается сбалансированным.
     */
    public static void testDeleteAndBalance() {
        System.out.println("=== Тест удаления и балансировки ===");
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(20);

        tree.delete(5); // Удаление может потребовать балансировку

        boolean isBalanced = tree.check();
        System.out.println("Дерево сбалансировано после удаления? " + isBalanced);
        System.out.println();
    }

    /**
     * Тест корректности структуры AVL.
     * - Вставляются несколько значений в правильном порядке.
     * - Проверяется, что дерево соответствует свойствам AVL.
     */
    public static void testCheckAVLStructure() {
        System.out.println("=== Тест корректности AVL-структуры ===");
        AVLTree tree = new AVLTree();
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);

        boolean isValidAVL = tree.check();
        System.out.println("Дерево является корректным AVL-деревом? " + isValidAVL);
        System.out.println();
    }

    /**
     * Тест метода diff() на пустом дереве.
     * - Ожидается, что вернется 0.
     */
    public static void testDiffWithEmptyTree() {
        System.out.println("=== Тест diff() на пустом дереве ===");
        AVLTree tree = new AVLTree();
        int minDiff = tree.diff();
        System.out.println("Минимальная разница в пустом дереве: " + minDiff);
        System.out.println();
    }

    /**
     * Тест метода diff() с одним узлом.
     * - Вставляется 10.
     * - Ожидается, что вернется 0 (нет соседей для сравнения).
     */
    public static void testDiffWithSingleNode() {
        System.out.println("=== Тест diff() с одним узлом ===");
        AVLTree tree = new AVLTree();
        tree.insert(10);
        int minDiff = tree.diff();
        System.out.println("Минимальная разница с одним узлом: " + minDiff);
        System.out.println();
    }

    /**
     * Тест метода diff() с несколькими узлами.
     * - Вставляются 10, 5, 15, 12.
     * - Ожидается min(|10-5|, |10-15|, |15-12|) = 3.
     */
    public static void testDiffWithMultipleNodes() {
        System.out.println("=== Тест diff() с несколькими узлами ===");
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(12);

        int minDiff = tree.diff();
        System.out.println("Минимальная разница между узлами: " + minDiff);
        System.out.println();
    }

    /**
     * Комплексный тест вставки и удаления.
     * - Вставляются 50, 30, 70, 20, 40, 60, 80.
     * - Удаляются 20 и 80.
     * - Проверяется баланс и минимальная разница.
     */
    public static void testComplexInsertDelete() {
        System.out.println("=== Комплексный тест вставки и удаления ===");
        AVLTree tree = new AVLTree();
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);

        System.out.println("Дерево после вставки:");
        System.out.println("Сбалансировано? " + tree.check());
        System.out.println("Минимальная разница: " + tree.diff());

        tree.delete(20);
        tree.delete(80);

        System.out.println("\nДерево после удаления 20 и 80:");
        System.out.println("Сбалансировано? " + tree.check());
        System.out.println("Минимальная разница: " + tree.diff());
        System.out.println();
    }
}
