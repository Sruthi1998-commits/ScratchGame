package com.scratchgame;

public class RewardCalculator {
    public static double calculateReward(String[][] matrix, double betAmount) {
        double reward = 0;
        
        for (String[] row : matrix) {
            if (row[0].equals(row[1]) && row[1].equals(row[2])) {
                reward += betAmount * 5;
            }
        }

        return reward;
    }
}
