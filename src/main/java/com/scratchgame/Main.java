package com.scratchgame;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 4) {
            System.out.println("Usage: java -jar target/scratchgame-1.0.jar --config <config.json> --betting-amount <amount>");
            return;
        }

        String configPath = null;
        double bettingAmount = 0;

        // Parse command-line arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--config") && i + 1 < args.length) {
                configPath = args[i + 1];
            } else if (args[i].equals("--betting-amount") && i + 1 < args.length) {
                try {
                    bettingAmount = Double.parseDouble(args[i + 1]);
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

        // Run the game
        Game game = new Game(configPath);
        game.play(bettingAmount);
    }
}
