package com.scratchgame;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 4 || !args[0].equals("--config") || !args[2].equals("--betting-amount")) {
            System.out.println("Usage: java -jar game.jar --config config.json --betting-amount <amount>");
            return;
        }


        String configPath = null;
        int bettingAmount = 0;
     // Parse command-line arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--config") && i + 1 < args.length) {
                configPath = args[i + 1];
            } else if (args[i].equals("--betting-amount") && i + 1 < args.length) {
                try {
                    bettingAmount = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid betting amount. Please enter a valid number.");
                    return;
                }
            }
        }

        // Validate inputs
        if (configPath == null) {
            System.out.println("Error: Config file path is required.");
            return;
        }
        if (bettingAmount <= 0) {
            System.out.println("Error: Betting amount must be greater than zero.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        Config config = mapper.readValue(new File(configPath), Config.class);

        GameEngine gameEngine = new GameEngine(config);
        //2D matrix generation
        String[][] matrix = gameEngine.generateMatrix();
        //Calculate reward based on the generated matix
        GameResult result = gameEngine.calculateReward(matrix, bettingAmount);

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    }
}
