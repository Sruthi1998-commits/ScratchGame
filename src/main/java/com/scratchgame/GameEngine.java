package com.scratchgame;

import java.util.*;

public class GameEngine {
    private final Config config;
    private final Random random = new Random();

    public GameEngine(Config config) {
        this.config = config;
    }

    /*
     * method to generate matrix based on the probabilities of symbols 
     * at each positions.
     */
    public String[][] generateMatrix() {
        String[][] matrix = new String[config.rows][config.columns];

        for (int row = 0; row < config.rows; row++) {
            for (int col = 0; col < config.columns; col++) {
                matrix[row][col] = pickRandomSymbol();
            }
        }
        insertRandomBonus(matrix);
        return matrix;
    }

    /*
     * method to pick random symbol for each
     * position in the matrix.
     */
    private String pickRandomSymbol() {
        Map<String, Integer> probabilities = config.probabilities.standard_symbols.get(0).symbols;
        int totalWeight = probabilities.values().stream().mapToInt(Integer::intValue).sum();
        int randomNum = random.nextInt(totalWeight) + 1;

        int cumulativeWeight = 0;
        for (Map.Entry<String, Integer> entry : probabilities.entrySet()) {
            cumulativeWeight += entry.getValue();
            if (randomNum <= cumulativeWeight) {
                return entry.getKey();
            }
        }
        return "F"; // Default fallback
    }

    /*
     * method to insert a random bonus symbol
     * at any random position of the matrix.
     */
    private void insertRandomBonus(String[][] matrix) {
        int row = random.nextInt(config.rows);
        int col = random.nextInt(config.columns);
        matrix[row][col] = pickRandomBonus();
    }

    /*
     * method to pick a random bonus symbol
     * based on bonus symbol probabilities.
     */
    private String pickRandomBonus() {
        Map<String, Integer> bonusProbabilities = config.probabilities.bonus_symbols.symbols;
        int totalWeight = bonusProbabilities.values().stream().mapToInt(Integer::intValue).sum();
        int randomNum = random.nextInt(totalWeight) + 1;

        int cumulativeWeight = 0;
        for (Map.Entry<String, Integer> entry : bonusProbabilities.entrySet()) {
            cumulativeWeight += entry.getValue();
            if (randomNum <= cumulativeWeight) {
                return entry.getKey();
            }
        }
        return "MISS"; //Default
    }

    /*
     * Calculate reward for the matrix passed.
     * Reward is applied for each symbol based
     * on its reward_multipliers from config.json
     */
    public GameResult calculateReward(String[][] matrix, int betAmount) {
        Map<String, List<String>> appliedWins = new HashMap<>();
        String appliedBonus = null;
        double totalReward = 0;

        Map<String, Integer> symbolCounts = new HashMap<>();
        for (String[] row : matrix) {
            for (String cell : row) {
                symbolCounts.put(cell, symbolCounts.getOrDefault(cell, 0) + 1);
            }
        }

        //Loops over each symbol in the sybmbolCounts map and checks if the value matches
        //With the winCombinations of standard symbols in the config.json and calculate reward based on that
        for (Map.Entry<String, Integer> entry : symbolCounts.entrySet()) {
            String symbol = entry.getKey();
            if (config.symbols.containsKey(symbol) && config.symbols.get(symbol).type.equals("standard")) {
                for (Map.Entry<String, WinCombination> winEntry : config.winCombinations.entrySet()) {
                    WinCombination winCombo = winEntry.getValue();
                    if ("same_symbols".equals(winCombo.when) && entry.getValue() >= winCombo.count) {
                        totalReward += betAmount * config.symbols.get(symbol).reward_multiplier * winCombo.reward_multiplier;
                        appliedWins.computeIfAbsent(symbol, k -> new ArrayList<>()).add(winEntry.getKey());
                    }
                }
            }
        }
        
        //Checks if any linear_symbols configuration holds true in the generated matrix
        //And computes the reward accordingly.
        for (Map.Entry<String, WinCombination> winEntry : config.winCombinations.entrySet()) {
            WinCombination winCombo = winEntry.getValue();
            if ("linear_symbols".equals(winCombo.when)) {
                for (List<String> positions : winCombo.covered_areas) {
                    String firstSymbol = null;
                    boolean match = true;
                    for (String pos : positions) {
                        String[] coords = pos.split(":");
                        int row = Integer.parseInt(coords[0]);
                        int col = Integer.parseInt(coords[1]);
                        if (firstSymbol == null) {
                            firstSymbol = matrix[row][col];
                        } else if (!firstSymbol.equals(matrix[row][col])) {
                            match = false;
                            break;
                        }
                    }
                    if (match && firstSymbol != null && config.symbols.containsKey(firstSymbol)) {
                        totalReward += betAmount * config.symbols.get(firstSymbol).reward_multiplier * winCombo.reward_multiplier;
                        appliedWins.computeIfAbsent(firstSymbol, k -> new ArrayList<>()).add(winEntry.getKey());
                    }
                }
            }
        }

        //Checks if there is any bonus symbols present in the matrix generated.
        //And it applies only if there exist at least one winning combination.
        if(totalReward>0) {
        	for (String[] row : matrix) {
                for (String cell : row) {
                    if (config.symbols.containsKey(cell) && "bonus".equals(config.symbols.get(cell).type)) {
                        appliedBonus = cell;
                        if ("multiply_reward".equals(config.symbols.get(cell).impact)) {
                            totalReward *= config.symbols.get(cell).reward_multiplier;
                        } else if ("extra_bonus".equals(config.symbols.get(cell).impact)) {
                            totalReward += config.symbols.get(cell).extra;
                        }
                    }
                }
            }
        }
        
        return new GameResult(matrix, (int) totalReward, appliedWins, appliedBonus);
    }
}
