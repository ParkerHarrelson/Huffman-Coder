package org.huffman.encoder;

import org.huffman.node.InternalNode;
import org.huffman.node.Node;

import java.util.*;

public abstract class HuffmanEncoder {

    private final Comparator<Node> nodeFrequencyComparator;

    public HuffmanEncoder() {
        this.nodeFrequencyComparator = Comparator.comparingInt(Node::getFrequency);
    }

    protected Node buildBinaryTree(Set<Node> nodes) {
        System.out.println("Beginning Binary Tree Build");
        int size = nodes.size();
        PriorityQueue<Node> minPriorityQueue = buildPriorityQueue(nodes);

        System.out.println("Building tree...");
        for (int i = 1; i < size; i++) {
            Node left = minPriorityQueue.poll();
            Node right = minPriorityQueue.poll();

            if (Objects.nonNull(left) && Objects.nonNull(right)) {
                Node internal = new InternalNode(left.getFrequency() + right.getFrequency(), left, right);
                minPriorityQueue.add(internal);
            }
        }

        System.out.println("Binary Tree Finished Building. Returning root...");
        return minPriorityQueue.poll();
    }

    private PriorityQueue<Node> buildPriorityQueue(Set<Node> nodes) {
        System.out.println("Building Priority Queue");
        PriorityQueue<Node> minPriorityQueue = new PriorityQueue<>(this.nodeFrequencyComparator);
        minPriorityQueue.addAll(nodes);
        return minPriorityQueue;
    }
}
