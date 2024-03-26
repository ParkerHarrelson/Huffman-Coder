package org.huffman.encoder;

import org.huffman.node.Node;
import org.huffman.node.TextNode;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TextEncoder extends HuffmanEncoder {

    public TextEncoder() {
        super();
    }

    public void encodeTextFileToBinary(String inputFilePath, String outputFilePath) throws IOException {

        Map<Character, TextNode> frequencyMap = new HashMap<>();

        System.out.println("Reading in " + inputFilePath + " and building frequency map...");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                char character = (char) ch;
                frequencyMap.computeIfAbsent(character, TextNode::new)
                        .increaseFrequency();
            }
        }
        System.out.println("Frequency Map Built.");

        Set<TextNode> nodes = new HashSet<>(frequencyMap.values());
        Map<Character, String> prefixCodeMap = buildPrefixCodeMap(nodes);

        System.out.println("Beginning encoding in file " + outputFilePath + "...");
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(inputFilePath));
             FileOutputStream fos = new FileOutputStream(outputFilePath);
             BufferedOutputStream out = new BufferedOutputStream(fos)) {

            StringBuilder binaryString = new StringBuilder();
            int ch;
            while ((ch = in.read()) != -1) {
                char character = (char) ch;
                String code = prefixCodeMap.get(character);
                if (code != null) {
                    binaryString.append(code);

                    while (binaryString.length() >= 8) {
                        byte b = (byte) Integer.parseInt(binaryString.substring(0, 8), 2);
                        out.write(b);
                        binaryString.delete(0, 8);
                    }
                }
            }

            if (!binaryString.isEmpty()) {
                System.out.println("Padding output with bits...");
                byte b = (byte) Integer.parseInt(binaryString.toString(), 2);
                out.write(b);
            }
        }
        System.out.println("Encoding finished.");
    }

    private Map<Character, String> buildPrefixCodeMap(Set<TextNode> textNodes) {
        Map<Character, String> prefixCodeMap = new HashMap<>();
        Set<Node> nodes = new HashSet<>(textNodes);
        Node root = this.buildBinaryTree(nodes);
        buildEncodingMapRecursive(root, "", prefixCodeMap);
        return prefixCodeMap;
    }

    private void buildEncodingMapRecursive(Node node, String code, Map<Character, String> prefixCodeMap) {
        if (node instanceof TextNode && ((TextNode) node).getCharacter() != null) {
            prefixCodeMap.put(((TextNode) node).getCharacter(), code);
        } else {
            if (node.getLeft() != null) {
                buildEncodingMapRecursive(node.getLeft(), code + "0", prefixCodeMap);
            }
            if (node.getRight() != null) {
                buildEncodingMapRecursive(node.getRight(), code + "1", prefixCodeMap);
            }
        }
    }

}
