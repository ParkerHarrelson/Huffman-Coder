package org.huffman.node;

public abstract class Node {

    private int frequency;
    private Node left;
    private Node right;

    public Node() {
        this.frequency = 1;
    }

    public Node(int frequency, Node left, Node right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public void increaseFrequency() {
        this.frequency++;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }
}
