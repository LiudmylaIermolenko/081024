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
        root = null;//В начале у дерева нет корня, оно пустое.
        //Создаётся объект-помощник treePrinter, который нужен только для красивого отображения дерева в консоли.
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
    //метод insert() и insertNodeRec() — вставляют значения в дерево по правилам BST
    public void insert(int value) {
//        if (root == null){
//            root = new Node(value);//10
//        }
//        if (value < root.getValue()){
//            root.setLeft(new Node(value));
//        }else if (value > root.getValue()){
//            root.setRight(new Node(value));
//        }
//        throw new UnsupportedOperationException("insert() is not implemented yet");
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
        // TODO: реализуйте рекурсивную вставку
        //метод находит, куда именно вставить число, и делает это рекурсивно (вызывает сам себя до нужного места).
        if (node == null){
            length++;
            return new Node(value);//Если места (узла) ещё нет — создаём новый узел. Это конец рекурсии.
        }
        if (value < node.getValue()){
            node.setLeft(insertNodeRec(node.getLeft(),value));
        }else if (value > node.getValue()){
            node.setRight(insertNodeRec(node.getRight(),value));
        }else if (value == node.getValue()) {
            return node; // Значение уже есть — ничего не делаем
        }
        return node;
        //throw new UnsupportedOperationException("insertNodeRec() is not implemented yet")
        // Если вставляемое значение меньше текущего, идём влево и вызываем вставку для левого поддерева
    }
    //Зачем нужна рекурсия?
    //Потому что дерево может быть глубоким, и мы не знаем заранее, где находится нужное место.
    // Рекурсия "спускается" по дереву, пока не найдёт пустую ячейку (где node == null).

    /**
     * Ищет значение узла по заданному значению.
     *
     * @param value Значение искомого узла.
     * @return Узел или null.
     */
    public Node getNode(int value) {//найти узел по значению.
        // TODO: реализуйте поиск узла
        //throw new UnsupportedOperationException("getNode() is not implemented yet");
        return searchNodeRec(root, value);
    }

    /**
     * Рекурсивно ищет узел с заданным значением.
     */
    private Node searchNodeRec(Node node, int value) {
        // TODO: реализуйте рекурсивный поиск
        //throw new UnsupportedOperationException("searchNodeRec() is not implemented yet");
        if (node == null || node.getValue() == value) return node;
        if (value < node.getValue()) return searchNodeRec(node.getLeft(), value);
        else return searchNodeRec(node.getRight(), value);
    }

    /**
     * Находит минимальный узел в дереве.
     *
     * @return Узел с минимальным значением или null.
     */
    //Минимум — самый левый узел.
    //Максимум — самый правый узел.
    public Node min() {
        // TODO: реализуйте поиск минимума
        //throw new UnsupportedOperationException("min() is not implemented yet");
        Node current = root;
        while (current != null && current.getLeft() != null) current = current.getLeft();
        return current;
    }

    /**
     * Находит максимальный узел в дереве.
     *
     * @return Узел с максимальным значением или null.
     */
    public Node max() {
        // TODO: реализуйте поиск максимума
        //throw new UnsupportedOperationException("max() is not implemented yet");
        Node current = root;
        while (current != null && current.getRight() != null) {
            current = current.getRight();
        }
        return current;
    }

    /**
     * Возвращает количество узлов в дереве.
     */ //length() — количество узлов
    //Ты уже увеличиваешь length при вставке — просто верни его:
    public int length() {
        // TODO: реализуйте подсчёт количества узлов
        //throw new UnsupportedOperationException("length() is not implemented yet");
        return length;
    }

    /**
     * Отображает дерево (например, in-order обход).
     */
    public void displayTree() {
        if (root == null) {
            System.out.println("Дерево пустое.");
            return;
        }
        // TODO: реализуйте вывод дерева
        //throw new UnsupportedOperationException("displayTree() is not implemented yet");
        treePrinter.printTree(root);
    }

    /**
     * Удаляет узел с заданным значением.
     */
    //метод, который удаляет узел с определённым значением из бинарного дерева поиска (BST)
    public void remove(int value) {
        // TODO: реализуйте метод удаления узла
        //throw new UnsupportedOperationException("remove() is not implemented yet");
        root = removeRec(root, value);
//ты говоришь дереву "удали значение", и оно начинает поиск с корня (root), вызывая removeRec
    }

    /**
     * Рекурсивно удаляет узел.
     */
    //метод, который рекурсивно находит нужный узел и удаляет его, при этом аккуратно перестраивает дерево, чтобы всё осталось правильно (BST)
    private Node removeRec(Node node, int value) {
        // TODO: реализуйте рекурсивное удаление
        //throw new UnsupportedOperationException("removeRec() is not implemented yet"
        if (node == null) return null;

        if (value < node.getValue()) {
            node.setLeft(removeRec(node.getLeft(), value));
        } else if (value > node.getValue()) {
            node.setRight(removeRec(node.getRight(), value));
        } else {
            // Найден узел
            if (node.getLeft() == null && node.getRight() == null) {
                length--;
                return null; // 1. Нет детей
            } else if (node.getLeft() == null) {
                length--;
                return node.getRight(); // 2. Только правый потомок
            } else if (node.getRight() == null) {
                length--;
                return node.getLeft(); // 2. Только левый потомок
            } else {
                // 3. Два потомка
                Node minNode = findMin(node.getRight());
                node.setValue(minNode.getValue());
                node.setRight(removeRec(node.getRight(), minNode.getValue()));
            }
        }
        return node;
    }
    private Node findMin(Node node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Проверяет, содержится ли значение в дереве.
     */
    public boolean contains(int value) {
        // TODO: реализуйте метод contains
        //throw new UnsupportedOperationException("contains() is not implemented yet");
        return getNode(value) != null;
    }

    /**
     * Пример использования дерева.
     */
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();//конструктор класса BinarySearchTree.
        //Он вызывается, когда ты создаёшь новое дерево:
        bst.insert(10);// Вставка значений
        bst.insert(3);
        bst.insert(5);
        bst.insert(4);
        bst.insert(11);
        bst.insert(15);
        bst.insert(12);
        bst.insert(16);
        bst.displayTree(); // TODO: после реализации должен напечатать дерево

        // Вывод дерева
        System.out.println("🌳 Дерево:");
        bst.displayTree();

        // Длина
        System.out.println("📏 Кол-во узлов: " + bst.length());

        // Минимум
        Node min = bst.min();
        System.out.println("🔽 Минимум: " + (min != null ? min.getValue() : "null"));

        // Максимум
        Node max = bst.max();
        System.out.println("🔼 Максимум: " + (max != null ? max.getValue() : "null"));

        // Поиск
        int searchVal = 5;
        System.out.println("🔍 Содержит " + searchVal + "? " + bst.contains(searchVal));
        Node found = bst.getNode(searchVal);
        System.out.println("🔍 Найден узел: " + (found != null ? found.getValue() : "null"));

        // Удаление
        System.out.println("❌ Удалим узел 10 (корень с двумя детьми)");
        bst.remove(10);
        bst.displayTree();
        System.out.println("📏 После удаления длина: " + bst.length());

    }
}
//Дерево — это структура данных, которая похожа на дерево в реальной жизни, только перевёрнутое вверх корнем:
//
//У каждого узла (node) есть:
//
//значение (value)
//
//левый потомок (left)
//
//правый потомок (right)
//
//🌳 Что такое бинарное дерево поиска (BST)?
//Binary Search Tree (BST) — это особый тип дерева:
//
//У каждого узла:
//
//Все меньшие значения — в левом поддереве
//
//Все большие значения — в правом поддереве

//Что такое TreePrinter<>(...)?
//Это вспомогательный класс, который тебе помогли подключить заранее (скорее всего, преподаватель или библиотека), чтобы ты мог красиво печатать дерево.
//
//Внутри скобок:
//
//java
//Copy
//Edit
//node -> String.valueOf(node.getValue()),
//Node::getLeft,
//Node::getRight
//Это лямбда-функции, которые объясняют TreePrinter:
//
//как получать значение узла (node.getValue())
//
//как получить левого и правого потомка (node.getLeft() и node.getRight())
//⚙️ Настройки treePrinter
//treePrinter.setTspace(2);              // вертикальные отступы
//treePrinter.setSquareBranches(true);   // рисовать квадратные ветки
//treePrinter.setLrAgnostic(false);      // не игнорировать лево/право
//treePrinter.setHspace(2);              // горизонтальные отступы
//Это всё про визуализацию дерева, не влияет на алгоритмы.


// 3 случая, когда мы удаляем узел:
//1. 🔹 Узел без детей (лист)
//Просто удаляем его:
// 5
// /
//3   ← Удаляем 3
//→ Просто return null — у родителя setLeft(null).

//🔸 Узел с одним потомком
//Заменяем узел его потомком:
//
//markdown
//Copy
//Edit
//    5
//   /
//  3
//   \
//    4   ← Удаляем 3
//→ Подставляем 4 вместо 3.

//🔶 Узел с двумя потомками
//Сложнее:
//
//Нужно найти наименьший узел в правом поддереве (min(node.getRight()))
//
//Скопировать его значение в текущий узел
//
//А затем удалить этот наименьший узел отдельно
//
//markdown
//Copy
//Edit
//     5
//    / \
//   3   7
//      /
//     6
//
//Удаляем 5 → наименьший в правом поддереве = 6
//→ Заменяем 5 на 6 → удаляем 6