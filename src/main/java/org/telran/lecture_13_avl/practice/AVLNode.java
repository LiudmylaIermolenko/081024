package org.telran.lecture_13_avl.practice;

public class AVLNode {
    private int value;       // Значение узла
    private int height;    // Высота поддерева с корнем в этом узле
    private AVLNode left;  // Левое поддерево
    private AVLNode right; // Правое поддерево

    public AVLNode(int value) {
        this.value = value;
        this.height = 1; // Новый узел всегда лист с высотой 1, для того, чтобы null лист был высотой = 0
        this.left = null;
        this.right = null;
    }

    @Override
    public String toString() {
        return "AVLNode{" +
                "value=" + value +
                ", height=" + height +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public AVLNode getLeft() {
        return left;
    }

    public void setLeft(AVLNode left) {
        this.left = left;
    }

    public AVLNode getRight() {
        return right;
    }

    public void setRight(AVLNode right)
    {
        this.right = right;
    }
}
