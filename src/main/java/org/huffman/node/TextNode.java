package org.huffman.node;

public class TextNode extends Node {

    private final Character character;

    public TextNode(Character character) {
        super();
        this.character = character;
    }

    public TextNode(int frequency, TextNode left, TextNode right) {
        super(frequency, left, right);
        this.character = null;
    }

    public Character getCharacter() {
        return this.character;
    }
}
