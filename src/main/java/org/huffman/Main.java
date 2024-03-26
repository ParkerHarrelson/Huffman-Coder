package org.huffman;

import org.huffman.encoder.TextEncoder;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Huffman Encoder!");
        System.out.println("Please enter the path to the input text file:");
        String inputFile = scanner.nextLine().trim();

        File file = new File(inputFile);
        if (!file.exists() || !file.canRead()) {
            System.err.println("Error: Input file does not exist or cannot be read.");
            return;
        }

        System.out.println("Please enter the path for the output binary file:");
        String outputFile = scanner.nextLine().trim();

        TextEncoder textEncoder = new TextEncoder();
        try {
            textEncoder.encodeTextFileToBinary(inputFile, outputFile);
        } catch (IOException e) {
            System.err.println("An error occurred during file encoding: " + e.getMessage());
        }
    }
}