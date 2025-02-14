package com.scratchgame;

import java.util.Random;

public class MatrixGenerator {
    private static final String[] SYMBOLS = {"A", "B", "C", "D", "E", "F", "10x", "5x", "+1000", "+500", "MISS"};

    public static String[][] generateMatrix(int rows, int cols) {
        Random rand = new Random();
        String[][] matrix = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = SYMBOLS[rand.nextInt(SYMBOLS.length)];
            }
        }

        return matrix;
    }
}
