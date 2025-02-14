package com.scratchgame;

public class Game {
    private ConfigLoader configLoader;

    public Game(String configPath) throws Exception {
        configLoader = new ConfigLoader(configPath);
    }

    public void play(double betAmount) {
        int rows = configLoader.getConfig().get("rows").asInt();
        int cols = configLoader.getConfig().get("columns").asInt();

        String[][] matrix = MatrixGenerator.generateMatrix(rows, cols);
        double reward = RewardCalculator.calculateReward(matrix, betAmount);

        System.out.println("Matrix:");
        for (String[] row : matrix) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println("Reward: " + reward);
    }
}
