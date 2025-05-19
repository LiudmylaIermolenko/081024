package org.telran.lecture_12_trees.practice;

import org.telran.utils.TreePrinter;

public class BinarySearchTree {
    private Node root;
    private TreePrinter<Node> treePrinter;
    private int length = 0;

    /**
     * Конструктор для создания нового бинарного дерева поиска.
     */
    public BinarySearchTree() {
        root = null;
        treePrinter = new TreePrinter<>(node-> String.valueOf(node.getValue()), Node::getLeft, Node::getRight);
        treePrinter.setTspace(2);
        treePrinter.setSquareBranches(true);
        treePrinter.setLrAgnostic(false);
        treePrinter.setHspace(2);
    }

    /**
     * Вставляет новый узел в дерево.
     *
     * @param value Значение нового узла.
     */
    public void insert(int value) {// Вставляет новый элемент в дерево, вызывая рекурсивный метод insertNodeRec.
        /*if (root == null) {
            root = new Node(value); //10
        }
        if (value < root.getValue()) {
            root.setLeft(new Node(value));
        }
        else if (value > root.getValue()) {
            root.setRight(new Node(value));
        }
        // TODO: реализуйте метод вставки узла
        throw new UnsupportedOperationException("insert() is not implemented yet");*/
        root = insertNodeRec(root, value);
    }

    /**
     * Рекурсивно вставляет новый узел в поддерево.
     *
     * @param node  Текущий узел.
     * @param value Значение.
     * @return Новый корень поддерева.
     */
    private Node insertNodeRec(Node node, int value) {
        if (node == null) {
            length++;
            return new Node(value);
        }
        if (value < node.getValue()) {
            node.setLeft(insertNodeRec(node.getLeft(), value));
        }
        else if (value > node.getValue()) {
            node.setRight(insertNodeRec(node.getRight(), value));
        }

        return node;
    }
    //Если дерево пустое (или поддерево пустое), создаем новый узел.
    //
    //Если value < node.value — идем влево.
    //
    //Если value > node.value — идем вправо.
    //
    //Если value == node.value — не вставляем (дубликаты игнорируются).
    //
    //Каждый раз, когда добавляем новый узел, увеличиваем length.

    /**
     * Ищет значение узла по заданному значению.
     *
     * @param value Значение искомого узла.
     * @return Узел или null.
     */
    public Node getNode(int value) {//Ищет узел с заданным значением (рекурсивно).
        // TODO: реализуйте поиск узла
        return searchNodeRec(root, value);
    }

    /**
     * Рекурсивно ищет узел с заданным значением.
     */
    private Node searchNodeRec(Node node, int value) {
        // TODO: реализуйте рекурсивный поиск
        if (node == null || node.getValue() == value) {
            return node;
        }
        if (value < node.getValue()) {
            return searchNodeRec(node.getLeft(), value);
        } else {
            return searchNodeRec(node.getRight(), value);
        }
    }
    //Ищет value.
    //
    //Если найден — возвращает узел.
    //
    //Если не найден — возвращает null.

    /**
     * Находит минимальный узел в дереве.
     *
     * @return Узел с минимальным значением или null.
     */
    public Node min() {
        // TODO: реализуйте поиск минимума
        if (root == null) return null;
        Node current = root;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }
    //Находит узел с минимальным значением (самый левый узел в дереве).

    /**
     * Находит максимальный узел в дереве.
     *
     * @return Узел с максимальным значением или null.
     */
    public Node max() {
        // TODO: реализуйте поиск максимума
        if (root == null) return null;
        Node current = root;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current;
    }//Находит узел с максимальным значением (самый правый узел).

    /**
     * Возвращает количество узлов в дереве.
     */
    public int length() {
        return length;
    }
    /**
     * Отображает дерево в виде структуры (с использованием TreePrinter).
     */
    public void displayTreeVisual() {
        if (root == null) {
            System.out.println("Дерево пустое.");
            return;
        }
        treePrinter.printTree(root);
    }

    /**
     * Отображает значения узлов дерева в порядке in-order (слева → корень → справа).
     */
    public void displayTreeInOrder() {
        if (root == null) {
            System.out.println("Дерево пустое.");
            return;
        }
        System.out.println("In-order обход:");
        inOrderTraversal(root);
        System.out.println();
    }

    private void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.getLeft());
            System.out.print(node.getValue() + " ");
            inOrderTraversal(node.getRight());
        }
    }

    /**
     * Удаляет узел с заданным значением.
     */
    public void remove(int value) {
        // TODO: реализуйте метод удаления узла
        root = removeRec(root, value);
    }//Удаляет узел с заданным значением, вызывая рекурсивную функцию removeRec

    /**
     * Рекурсивно удаляет узел.
     */
    private Node removeRec(Node node, int value) {
        // TODO: реализуйте рекурсивное удаление
        if (node == null) return null;

        if (value < node.getValue()) {
            node.setLeft(removeRec(node.getLeft(), value));
        } else if (value > node.getValue()) {
            node.setRight(removeRec(node.getRight(), value));
        } else {
            // Случай 1: нет потомков
            if (node.getLeft() == null && node.getRight() == null) {
                length--;
                return null;
            }

            // Случай 2: один потомок
            if (node.getLeft() == null) {
                length--;
                return node.getRight();
            }
            if (node.getRight() == null) {
                length--;
                return node.getLeft();
            }

            // Случай 3: два потомка //— ищем минимум в правом поддереве
            Node minNode = findMin(node.getRight());
            node.setValue(minNode.getValue());
            node.setRight(removeRec(node.getRight(), minNode.getValue()));
        }
        return node;
    }

    private Node findMin(Node node) {//Находит самый маленький узел в поддереве.
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }
    //Что делает:
    //Если узел не найден — ничего не делает.
    //
    //Если у узла:
    //
    //нет детей → просто удаляет его;
    //
    //один ребенок → возвращает ребенка;
    //
    //два ребенка → заменяет значение на минимальный узел справа.

    /**
     * Проверяет, содержится ли значение в дереве.
     */
    public boolean contains(int value) {
        // TODO: реализуйте метод contains
        return getNode(value) != null;//Возвращает true, если узел найден, иначе false.
    }

    /**
     * Пример использования дерева.
     */
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(10);
        bst.insert(3);
        bst.insert(5);
        bst.insert(4);
        bst.insert(11);
        bst.insert(15);
        bst.insert(12);
        bst.insert(16);
        // Визуальное дерево
        bst.displayTreeVisual();
        System.out.println("Min: " + bst.min().getValue());     // 3
        System.out.println("Max: " + bst.max().getValue());     // 16
        System.out.println("Contains 12? " + bst.contains(12)); // true
        System.out.println("Contains 99? " + bst.contains(99)); // false

        bst.remove(10); // Удалим корень
        // Отсортированный in-order вывод
        bst.displayTreeInOrder();// TODO: после реализации должен напечатать дерево// In-order: 3 4 5 10 11 12 15 16
        System.out.println("Length: " + bst.length()); // Проверка длины
    }
}
// Удаление узла (remove() и removeRec())
//Удаление — самая сложная часть, потому что есть 3 случая:
//
//Узел — лист (просто удаляем)
//
//Узел имеет одного потомка (заменяем его)
//
//Узел имеет двух потомков (находим минимум в правом поддереве, заменяем значение, и рекурсивно удаляем этот минимум)

//Поле length отслеживает количество узлов (элементов) в дереве.
//То есть: если ты вставил 8 чисел, length == 8.
//Если потом удалил один — станет length == 7.

//Разница между treePrinter.printTree(root) и inOrderTraversal(root)
//🔸 1. treePrinter.printTree(root)
//(из твоего оригинального кода, через TreePrinter)
//public void displayTree() {
//    if (root == null) {
//        System.out.println("Дерево пустое.");
//        return;
//    }
//    treePrinter.printTree(root);
//}
//📌 Что делает:
//
//Выводит дерево в визуальной структуре, как иерархию, где видно корень, ветви, уровни.
//
//Пример (упрощённо):
//
//markdown
//Copy
//Edit
//      10
//     /  \
//    5    15
//         /
//       12
//📌 Для чего подходит:
//
//Отлично видно структуру дерева: кто чей родитель, где левый/правый ребёнок.
//
//Удобно для отладки и визуализации.

//inOrderTraversal(node)
//(ручной обход in-order)
//Проходит по дереву в порядке: левый → корень → правый.
//
//Выводит значения узлов по возрастанию.
//
//Пример:
//
//Copy
//Edit
//3 5 10 12 15
//📌 Для чего подходит:
//
//Чтобы получить отсортированный список значений.
//
//Увидеть, все ли элементы на месте.
//Вывод:
//Метод	Вывод	Для чего полезен
//treePrinter.printTree(root)	Дерево в виде иерархии	Визуализация структуры дерева
//inOrderTraversal(root)	Значения по порядку (лево→корень→право)	Проверка порядка, сортировки, содержимого

//Итог:
//
//displayTreeVisual() — для структуры.
//
//displayTreeInOrder() — для значений по порядку.