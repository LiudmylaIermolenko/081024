package org.telran.lecture_13_avl.practice;

import org.telran.utils.TreePrinter;

import java.util.NoSuchElementException;

/**
 * Класс реализует самобалансирующееся дерево поиска — AVL-дерево.
 * Основные операции: вставка, удаление, поиск, балансировка дерева.
 */
public class AvlTree {
    private AVLNode root;//Основные компоненты: Корень дерева. Всё дерево строится от него.

    /**
     * Возвращает высоту узла.
     *
     * @param node узел
     * @return высота или 0, если null
     */
    //Вспомогательные методы
    private int height(AVLNode node) {
        // TODO: Если node == null, вернуть 0, иначе вернуть node.getHeight()
        //Возвращает высоту поддерева, корнем которого является node. Если node == null, возвращает 0.
        if (node == null){
            return 0;
        }
        return node.getHeight();
    }

    /**
     * Вычисляет баланс-фактор узла (разность высот левого и правого поддеревьев).
     *
     * @param node узел
     * @return баланс-фактор
     */
    //Баланс-фактор — разница высот левого и правого поддеревьев.
    //Используется для определения необходимости поворотов.
    private int balanceFactor(AVLNode node) {
        // TODO: Вернуть разницу между высотой левого и правого поддеревьев
         if (node == null){
            return 0;
        }
        return height(node.getLeft()) - height(node.getRight());
        //    int leftHeight = node.getLeft() == null ? 0 : node.getLeft().getHeight();
        //    int rightHeight = node.getRight() == null ? 0 : node.getRight().getHeight();
        //    return leftHeight - rightHeight;
    }

    /**
     * Обновляет высоту узла на основе высоты его дочерних поддеревьев.
     *
     * @param node узел
     */
    //Обновление высоты:
    //Метод updateHeight(node) нужен, чтобы обновлять "высоту" каждого узла после изменений в дереве (вставки, удаления, вращения),
    // так как AVL-дерево использует высоты узлов для определения дисбаланса.
    //Высота узла — это 1 + max(высота левого, правого поддерева).
    //Обновляется каждый раз после изменения потомков.
    private void updateHeight(AVLNode node) {
        // TODO: Установить высоту как 1 + max(высота левого, высота правого)
        if (node == null) {
            return;
        }
        node.setHeight(1 + Math.max(
                        height(node.getLeft()),
                        height(node.getRight())
                )
        );
    }
//        int leftHeight = (node.getLeft() != null) ? node.getLeft().getHeight() : 0;
//        int rightHeight = (node.getRight() != null) ? node.getRight().getHeight() : 0;
//        node.setHeight(1 + Math.max(leftHeight, rightHeight));
//    }

    /**
     * Выполняет правый поворот для восстановления баланса дерева.
     * Используется в случае левостороннего дисбаланса.
     * <pre>
     *
     *  До поворота:          Y         После поворота:    X
     *                       /                            / \
     *                      X                            A   Y
     *                     /
     *                    A
     *
     *  До поворота :     Y            После поворота:      X
     *                   / \                               / \
     *                  X   C                             A   Y
     *                 / \                                   / \
     *                A   B                                 B   C
     * </pre>
     *
     * @param yNode Узел, у которого нарушен баланс
     * @return Новый корень поддерева
     */
    private AVLNode rotateRight(AVLNode yNode) {
        // TODO: Выполнить малое правое вращение
        if (yNode == null){
            return null;
        }
        AVLNode xNode = yNode.getLeft();
        if (xNode == null){
            return yNode;// x — левый ребёнок yNode
        }
        AVLNode bSubTree = xNode.getRight();       // beta — правое поддерево x
        // 2. Делаем вращение:
        //    - правый ребёнок x становится левым ребёнком yNode
        yNode.setLeft(bSubTree);
        //    - x становится родителем yNode (yNode правый ребенок для x)
        xNode.setRight(yNode);
        // Обновляем высоты (сначала нижний, потом верхний/сначала yNode, затем x)
        updateHeight(yNode);
        updateHeight(xNode);
        // 4. Возвращаем x как новый корень поддерева
        //    Это узел x, который станет новым корнем поддерева
        return xNode;  // x становится новым корнем поддерева
    }
    //    private AVLNode rotateRight(AVLNode y) {
    //    AVLNode x = y.getLeft();
    //    AVLNode T2 = x.getRight();
    //
    //    x.setRight(y);
    //    y.setLeft(T2);
    //
    //    updateHeight(y);
    //    updateHeight(x);
    //
    //    return x;
    //}

    /**
     * Выполняет левый поворот для восстановления баланса дерева.
     * Используется в случае правостороннего дисбаланса.
     * <pre>
     *
     *  До поворота:      Y         После поворота:      X
     *                     \                            / \
     *                      X                          Y   A
     *                       \
     *                        A
     *
     *  До поворота :    Y              После поворота:       X
     *                  / \                                  / \
     *                 A   X                                Z   C
     *                    / \                              / \
     *                   B   C                            A   B
     * </pre>
     *
     * @param yNode Узел, у которого нарушен баланс
     * @return Новый корень поддерева
     */
    private AVLNode rotateLeft(AVLNode yNode) {
        // TODO: Выполнить малое левое вращение
        // 1. Сохраняем правое поддерево yNode — это x
        if (yNode == null) {
            return null;
        }
        // 2. x.left (B) становится правым поддеревом yNode
        AVLNode xNode = yNode.getRight();
        if (xNode == null) {
            return yNode;
        }
            // 3. x становится родителем yNode
            AVLNode bSubTree = xNode.getLeft();       // beta — правое поддерево x
            yNode.setRight(bSubTree);
            xNode.setLeft(yNode);
            // 4. Обновляем высоты узлов (сначала yNode, потом x)
            updateHeight(yNode);
            updateHeight(xNode);
            // 5. Возвращаем x как новый корень поддерева
            return xNode;
    }
    //private AVLNode rotateLeft(AVLNode x) {
    //    AVLNode y = x.getRight();
    //    AVLNode T2 = y.getLeft();
    //
    //    y.setLeft(x);
    //    x.setRight(T2);
    //
    //    updateHeight(x);
    //    updateHeight(y);
    //
    //    return y;
    //}

    /**
     * Выполняет двойной поворот влево-вправо (LR-rotation) при левом дисбалансе правого поддерева.
     * <pre>
     *  До поворота:     Z      После первого поворота:    Z     После второго поворота:   Y
     *                  / \                               / \                             / \
     *                 X   D                            Y   D                           X   Z
     *                / \                              / \                             / \ / \
     *               A   Y                            X   C                           A  B C  D
     *                  / \                          / \
     *                 B   C                        A   B
     * </pre>
     *
     * @param zNode Узел с нарушением баланса
     * @return Новый корень сбалансированного поддерева
     */
    private AVLNode rotateLeftRight(AVLNode zNode) {
        // TODO: Сначала малое левое вращение левого поддерева, затем правое вращение
        if (zNode == null || zNode.getLeft() == null) {
            return zNode;
        }
   /* AVLNode xNode = zNode.getLeft(); // x -> y -> c
    AVLNode yNode = rotateLeft(xNode);
    // z -> Y -> X
    zNode.setLeft(yNode);
    return rotateRight(zNode);*/
        zNode.setLeft(rotateLeft(zNode.getLeft()));
        return rotateRight(zNode);
    }

    /**
     * Выполняет двойной поворот вправо-влево (RL-rotation) при правом дисбалансе левого поддерева.
     * <pre>
     *  До поворота:     Z      После первого поворота:    Z        После второго поворота:    Y
     *                  / \                               / \                                /   \
     *                 A   X                             A   Y                              Z     X
     *                    / \                               / \                            / \   / \
     *                   Y   D                             B   X                          A   B C   D
     *                  / \                                   / \
     *                 B   C                                 C   D
     * <pre>
     * @param zNode Узел с нарушением баланса
     * @return Новый корень сбалансированного поддерева
     */
    private AVLNode rotateRightLeft(AVLNode zNode) {
        // TODO: Сначала малое правое вращение правого поддерева, затем левое вращение
        //throw new UnsupportedOperationException("method rotateRightLeft(AVLNode zNode) is not implemented yet");
        if (zNode == null || zNode.getRight() == null){
            return zNode;
    }
        zNode.setRight(rotateRight(zNode.getRight()));
        return rotateLeft(zNode);
        }

    /**
     * Выполняет ребалансировку дерева после вставки или удаления.
     *
     * @param node узел
     * @return сбалансированный узел
     */
    //Проверяет баланс-фактор и применяет нужные повороты, чтобы восстановить сбалансированность.
    private AVLNode rebalance(AVLNode node) {
        // TODO:
        if (node == null) {
            return null;
        }
            //  1. Вычислить balanceFactor
            int balance = balanceFactor(node);
        //  2. В зависимости от значения выполнить соответствующее вращение:
        //     > 1 и левый баланс >= 0 → rotateRight
            if (balance > 1){
               if (balanceFactor(node.getLeft()) >= 0){
                   return rotateRight(node);
               }else {
                   return rotateLeftRight(node);
               }
            }
            if (balance < -1){
                if (balanceFactor(node.getRight()) < 0){
                    return rotateLeft(node);
                }else {
                    return rotateRightLeft(node);
                }
            }
            return node;
        //     > 1 и левый баланс < 0  → rotateLeftRight
        //     < -1 и правый баланс <= 0 → rotateLeft
        //     < -1 и правый баланс > 0  → rotateRightLeft
    }
    //private AVLNode balance(AVLNode node) {
    //    updateHeight(node);
    //    int balanceFactor = getBalanceFactor(node);
    //
    //    // Левое поддерево слишком высокое
    //    if (balanceFactor > 1) {
    //        if (getBalanceFactor(node.getLeft()) < 0) {
    //            // Левый-правый случай: сначала поворот влево
    //            node.setLeft(rotateLeft(node.getLeft()));
    //        }
    //        return rotateRight(node);
    //    }
    //
    //    // Правое поддерево слишком высокое
    //    if (balanceFactor < -1) {
    //        if (getBalanceFactor(node.getRight()) > 0) {
    //            // Правый-левый случай: сначала поворот вправо
    //            node.setRight(rotateRight(node.getRight()));
    //        }
    //        return rotateLeft(node);
    //    }
    //
    //    // Балансировка не нужна
    //    return node;
    //}

    /**
     * Рекурсивная вставка элемента с поддержанием балансировки.
     *
     * @param node текущий узел
     * @param value   вставляемое значение
     * @return корень поддерева
     */
    private AVLNode insertRecursively(AVLNode node, int value) {
        // 1. Базовый случай — место для вставки найдено
        if (node == null) {
            return new AVLNode(value);
        }
        // 2. Спускаемся рекурсивно в нужное поддерево
        if (value < node.getValue()) {
            node.setLeft(insertRecursively(node.getLeft(), value));
        }
        else if (value > node.getValue()) {
            node.setRight(insertRecursively(node.getRight(), value));
        }
        updateHeight(node);
        //4. Балансируем, если надо
        return rebalance(node);
    }
    //private AVLNode insertRecursively(AVLNode node, int value) {
    //    if (node == null) {
    //        return new AVLNode(value);
    //    }
    //    if (value < node.getValue()) {
    //        node.setLeft(insertRecursively(node.getLeft(), value));
    //    } else if (value > node.getValue()) {
    //        node.setRight(insertRecursively(node.getRight(), value));
    //    } else {
    //        // Повторяющееся значение, ничего не делаем
    //        return node;
    //    }
    //    // Обновляем высоту и балансируем
    //    return balance(node);
    //}

    /**
     * Вставляет значение в дерево.
     *
     * @param value значение для вставки
     */
    public void insert(int value) {
        root = insertRecursively(root, value);
    }


    /**
     * Удаляет значение из дерева, если оно есть.
     *
     * @param value значение для удаления
     */
    public void remove(int value) {
        root = removeRecursively(root, value);
    }

    /**
     * Рекурсивное удаление с балансировкой.
     *
     * @param node  текущий узел
     * @param value значение для удаления
     * @return новый корень поддерева
     */
    private AVLNode removeRecursively(AVLNode node, int value) {
        // TODO:
        if (node == null){
            return null;
        }
        //Удаляет значение:
        //
        //Если узел — лист, просто удаляется.
        //Если один ребёнок — заменяется на него.
        //Если два — находит минимальный элемент в правом поддереве, заменяет удаляемый и р
        // екурсивно удаляет найденный.
        if (value < node.getValue()){
            node.setLeft(removeRecursively(node.getLeft(), value));
        }else if (value > node.getValue()){
            node.setRight(removeRecursively(node.getRight(), value));
        }else {
            if (node.getLeft() == null && node.getRight() == null){
                return null;
            }else if (node.getLeft() == null) {
                return node.getRight(); // Только правый ребенок
            } else if (node.getRight() == null) {
                return node.getLeft(); // Только левый ребенок
            } else {
                // Два ребенка: ищем минимальный в правом поддереве
                AVLNode minNode = min(node.getRight());
                node.setValue(minNode.getValue());
                node.setRight(removeRecursively(node.getRight(), minNode.getValue()));
            }
        }
        // Обновляем высоту
        updateHeight(node);
        // Ребалансировка
        return rebalance(node);
        }
        //     - один ребёнок: вернуть не-null ребёнка
        //     - два ребёнка: найти min в правом поддереве, заменить значение, удалить min
        //  3. Обновить высоту и ребалансировать
    //private AVLNode removeRecursively(AVLNode node, int value) {
    //        // TODO:
    //        //  1. Ищем узел для удаления
    //        if (node == null) {
    //            return null;
    //        }
    //        //  2. Удаление:
    //        if (value < node.getValue()) {
    //            node.setLeft(removeRecursively(node.getLeft(), value));
    //        } else if (value > node.getValue()) {
    //            node.setRight(removeRecursively(node.getRight(), value));
    //        } else {
    //            if (node.getLeft() == null && node.getRight() == null) {
    //                return null;
    //            }
    //            if (node.getLeft() == null) {
    //                return node.getRight();
    //            }
    //            if (node.getRight() == null) {
    //                return node.getLeft();
    //            }
    //            AVLNode minNode = min(node.getRight());
    //            node.setValue(minNode.getValue());
    //            node.setRight(removeRecursively(node.getRight(), node.getValue()));
    //
    //        }
    //        if (node == null) {
    //            return null;
    //        }
    //        updateHeight(node);
    //        return rebalance(node);
    //    }

    /**
     * Возвращает минимальное значение дерева.
     *
     * @return минимальное значение
     */
    //Находит узел с минимальным значением в поддереве, начиная с переданного узла node.
    //Минимальный элемент в бинарном дереве поиска — это самый левый узел.
    //если дерево пустое — метод выбрасывал NoSuchElementException, как ожидает тест;
    //если не пустое — корректно возвращал минимальное значение.
    public int min() {
        if (root == null) {
            throw new NoSuchElementException("Tree is empty");
        }
        return min(root).getValue();
    }

    private AVLNode min(AVLNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Возвращает максимальное значение дерева.
     *
     * @return максимальное значение
     */
    //Находит максимальное значение — это самый правый узел.
    public int max() {
        if (root == null) {
            throw new NoSuchElementException("Tree is empty");
        }
        return max(root).getValue();
    }

    private AVLNode max(AVLNode node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    /**
     * Проверяет, содержится ли значение в дереве.
     *
     * @param value значение
     * @return true, если найдено
     */
    public boolean contains(int value) {
        return search(value) != null;
    }

    /**
     * Поиск узла по значению.
     *
     * @param value значение
     * @return найденный узел или null
     */
    //Осуществляет бинарный поиск по дереву — либо находит значение, либо возвращает null.
    //Если value < node.value, идём влево.
    //Если value > node.value, идём вправо.
    //Иначе — нашли нужный узел.
    public AVLNode search(int value) {
        return searchRecursively(root, value);
    }

    private AVLNode searchRecursively(AVLNode node, int value) {
        // TODO: Классический бинарный поиск
        if (node == null || node.getValue() == value) {
            return node;
        }
        if (value < node.getValue()) {
            return searchRecursively(node.getLeft(), value);
        } else {
            return searchRecursively(node.getRight(), value);
        }
    }

    //Сервисные методы:
    /**
     * Возвращает количество узлов в дереве.
     *
     * @return размер дерева
     */
    public int size() {
        return size(root);
    }
    //Рекурсивная формула:
    //size(node) = size(left) + size(right) + 1

    private int size(AVLNode node) {
        // TODO: Рекурсивно считаем количество узлов: size(left) + size(right) + 1
        if (node == null) {
            return 0;
        }
        return 1 + size(node.getLeft()) + size(node.getRight());
    }

    public boolean isBalanced(AvlTree tree) {//Проверяет, сбалансировано ли дерево
        return isBalanced(tree.root);
    }

    public boolean isBalanced(AVLNode node) {
        if (node == null) {
            return true;
        }
        ////💡 Для чего это нужно?
        //    //Чтобы вычислить баланс-фактор:
        //    //balanceFactor = height(left) - height(right);
        //    //Чтобы решить, нужно ли вращать дерево.
        //    //
        //    //Чтобы гарантировать,
        //    // что дерево остаётся сбалансированным (разница высот ≤ 1) на каждом уровне.

        int balance = balanceFactor(node);
        if (balance < -1 || balance > 1) {
            return false;
        }

        return isBalanced(node.getLeft()) && isBalanced(node.getRight());
    }

    public boolean isEmpty() {//Проверяет, пусто ли дерево
        return root == null;
    }

    protected AVLNode getRoot() {
        return root;
    }


    public static void main(String[] args) {
        TreePrinter<AVLNode> printer = new TreePrinter<>(
                node -> node.getValue() + " (" + (node.getHeight() - 1) + ")",
                AVLNode::getLeft,
                AVLNode::getRight
        );
        printer.setHspace(2);
        printer.setLrAgnostic(false);
        printer.setSquareBranches(true);
        printer.setTspace(2);

        AvlTree tree = new AvlTree();

        //TODO: Вставка значений в дерево (например, от 1 до 12)
        for (int i = 1; i <= 12; i++) {
            tree.insert(i);
        }

        //TODO: Вывести размер и визуализировать дерево
        System.out.println("Size = " + tree.size());

        // Проверка минимального и максимального
        System.out.println("Min = " + tree.min());
        System.out.println("Max = " + tree.max());

        // Проверка поиска
        System.out.println("Contains 5? " + tree.contains(5));
        System.out.println("Contains 50? " + tree.contains(50));
        printer.printTree(tree.root);
    }

}

//Этот класс AvlTree реализует сбалансированное двоичное дерево поиска (AVL-дерево) на языке Java.

//Что такое AVL-дерево?
//AVL-дерево — это двоичное дерево поиска, где для каждого узла выполняется инвариант:
//|высота левого поддерева − высота правого поддерева| ≤ 1
//Если после вставки или удаления это условие нарушается — выполняются вращения (повороты), чтобы дерево снова стало сбалансированным.
