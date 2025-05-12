package org.telran.lecture_12_trees.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Этот файл — юнит-тест. Он нужен, чтобы автоматически проверить,
// правильно ли работает твой код, без необходимости вручную вызывать main и смотреть глазами.
//Он использует библиотеку JUnit 5 — это популярный инструмент для тестирования Java-кода.

public class BinarySearchTreeTest {

    private BinarySearchTree bst;

    @BeforeEach
    void setUp() {
        bst = new BinarySearchTree();
    }
    //Вызывается перед каждым тестом.
    //Каждый раз создаёт новое, чистое дерево.
    //Это важно, чтобы тесты не мешали друг другу.

    @Test
    void testEmptyTreeLength() {
        assertEquals(0, bst.length(), "Empty tree should have length 0");
//Проверяет: если дерево только что создано, его длина (кол-во узлов) — 0.
    }

    @Test
    void testInsertSingleNode() {
        bst.insert(10);
        assertEquals(1, bst.length(), "Tree with one node should have length 1");
        bst.displayTree();
        //assertTrue(bst.contains(10), "Tree should contain inserted value");
        //Вставляем один узел (10).
        //Проверяем: длина дерева должна быть 1.
        //displayTree() — визуально выводит дерево в консоль.
    }

    @Test
    void testInsertMultipleNodes() {
        int[] values = {5, 3, 7, 2, 4, 6, 8};
        for (int value : values) {
            bst.insert(value);
        }

        assertEquals(7, bst.length(), "Tree should have correct length after multiple inserts");
        bst.displayTree();
        /*for (int value : values) {
            assertTrue(bst.contains(value), "Tree should contain all inserted values");
        }*/
    }
    //Вставляем 7 значений.
    //Проверяем, что размер дерева стал 7.

    @Test
    void testInsertDuplicateValues() {
        bst.insert(10);
        bst.insert(10);

        assertEquals(1, bst.length(), "Tree should not increase size for duplicate inserts");
    }
    //Проверяет: вставка одного и того же значения не увеличивает дерево.
    //Обычно бинарное дерево поиска не допускает дубликаты.

    @Test
    void testGetNode() {
        int[] values = {5, 3, 7, 2, 4, 6, 8};
        for (int value : values) {
            bst.insert(value);
        }

        for (int value : values) {
            Node node = bst.getNode(value);
            assertNotNull(node, "getNode should return a node for existing values");
            assertEquals(value, node.getValue(), "getNode should return node with correct value");
        }

        assertNull(bst.getNode(100), "getNode should return null for non-existing values");
    }
    //Проверяет: метод getNode() находит нужный узел по значению.
    //Также проверяется, что null возвращается для несуществующих значений.

    @Test
    void testMin() {
        assertNull(bst.min(), "Min of empty tree should be null");

        int[] values = {5, 3, 7, 2, 4, 6, 8};
        for (int value : values) {
            bst.insert(value);
        }

        Node minNode = bst.min();
        assertNotNull(minNode, "Min should not be null in non-empty tree");
        assertEquals(2, minNode.getValue(), "Min should return node with smallest value");
    }

    @Test
    void testMax() {
        assertNull(bst.max(), "Max of empty tree should be null");

        int[] values = {5, 3, 7, 2, 4, 6, 8};
        for (int value : values) {
            bst.insert(value);
        }

        Node maxNode = bst.max();
        assertNotNull(maxNode, "Max should not be null in non-empty tree");
        assertEquals(8, maxNode.getValue(), "Max should return node with largest value");
    }

    @Test
    void testContains() {
        assertFalse(bst.contains(10), "Empty tree should not contain any value");

        int[] values = {5, 3, 7, 2, 4, 6, 8};
        for (int value : values) {
            bst.insert(value);
        }

        for (int value : values) {
            assertTrue(bst.contains(value), "contains should return true for existing values");
        }

        assertFalse(bst.contains(100), "contains should return false for non-existing values");
    }

    @Test
    void testRemoveSingleNode() {
        bst.insert(10);
        assertEquals(1, bst.length(), "Tree should have one node before removal");

        bst.remove(10);
        assertEquals(0, bst.length(), "Tree should be empty after removing only node");
        bst.displayTree();
        assertFalse(bst.contains(10), "Tree should not contain removed value");
    }

    @Test
    void testRemoveNodeWithNoChildren() {
        // Create a tree:
        //      5
        //    /   \
        //   3     7
        //  / \   / \
        // 2   4 6   8

        int[] values = {5, 3, 7, 2, 4, 6, 8};
        for (int value : values) {
            bst.insert(value);
        }

        bst.remove(2); // Remove leaf node
        assertEquals(6, bst.length(), "Tree should have one less node after removal");
        assertFalse(bst.contains(2), "Tree should not contain removed value");

        // Check remaining structure
        assertTrue(bst.contains(3), "Parent of removed node should still exist");
        assertTrue(bst.contains(4), "Sibling of removed node should still exist");
    }
    //Проверяются разные ситуации удаления:
    //Лист (без детей)
    //Один ребёнок
    //Два ребёнка
    //Корень
    //Несуществующий элемент

    @Test
    void testRemoveNodeWithOneChild() {
        // Create an unbalanced tree:
        //      5
        //    /   \
        //   3     7
        //  /     / \
        // 2     6   8
        //              \
        //               9

        int[] values = {5, 3, 7, 2, 6, 8, 9};
        for (int value : values) {
            bst.insert(value);
        }

        bst.remove(8); // Remove node with one child
        assertEquals(6, bst.length(), "Tree should have one less node after removal");
        assertFalse(bst.contains(8), "Tree should not contain removed value");

        // Check remaining structure
        assertTrue(bst.contains(9), "Child of removed node should still exist");
        assertTrue(bst.contains(7), "Parent of removed node should still exist");
    }

    @Test
    void testRemoveNodeWithTwoChildren() {
        // Create a tree:
        //      5
        //    /   \
        //   3     7
        //  / \   / \
        // 2   4 6   8

        int[] values = {5, 3, 7, 2, 4, 6, 8};
        for (int value : values) {
            bst.insert(value);
        }

        bst.remove(3); // Remove node with two children
        bst.displayTree();
        assertEquals(6, bst.length(), "Tree should have one less node after removal");
        assertFalse(bst.contains(3), "Tree should not contain removed value");

        // Check remaining structure
        assertTrue(bst.contains(2), "Left child of removed node should still exist");
        assertTrue(bst.contains(4), "Right child of removed node should still exist");
    }

    @Test
    void testRemoveRoot() {
        // Create a tree:
        //      5
        //    /   \
        //   3     7
        //  / \   / \
        // 2   4 6   8

        int[] values = {5, 3, 7, 2, 4, 6, 8};
        for (int value : values) {
            bst.insert(value);
        }

        bst.remove(5); // Remove root node
        assertEquals(6, bst.length(), "Tree should have one less node after removal");
        assertFalse(bst.contains(5), "Tree should not contain removed root value");

        // Check remaining structure is intact
        for (int value : new int[]{2, 3, 4, 6, 7, 8}) {
            assertTrue(bst.contains(value), "Tree should still contain other values");
        }
    }

    @Test
    void testRemoveNonExistingNode() {
        int[] values = {5, 3, 7, 2, 4, 6, 8};
        for (int value : values) {
            bst.insert(value);
        }

        int initialLength = bst.length();
        bst.remove(100); // Remove non-existing node

        assertEquals(initialLength, bst.length(), "Tree length should not change when removing non-existing node");

        // Check structure is unchanged
        for (int value : values) {
            assertTrue(bst.contains(value), "Tree should still contain all original values");
        }
    }

    @Test
    void testComplexOperations() {
        // Insert values
        int[] initialValues = {10, 5, 15, 3, 7, 12, 17};
        for (int value : initialValues) {
            bst.insert(value);
        }

        // Remove some values
        bst.remove(3);
        bst.remove(12);

        // Insert new values
        bst.insert(4);
        bst.insert(13);

        // Verify tree structure
        assertFalse(bst.contains(3), "Tree should not contain removed value 3");
        assertFalse(bst.contains(12), "Tree should not contain removed value 12");
        assertTrue(bst.contains(4), "Tree should contain newly inserted value 4");
        assertTrue(bst.contains(13), "Tree should contain newly inserted value 13");

        // Check min and max
        assertEquals(4, bst.min().getValue(), "Min value should be 4 after operations");
        assertEquals(17, bst.max().getValue(), "Max value should be 17 after operations");

        // Check length
        assertEquals(7, bst.length(), "Tree should have correct length after operations");
    }
    //Комплексный тест: и вставка, и удаление, и поиск.
    //Проверяется финальная структура дерева.
}
