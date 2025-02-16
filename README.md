# Scratch Game  

## Overview  
This project implements a **Scratch Game** that evaluates a given **game matrix** and calculates rewards based on predefined rules.  

## Features  
- Detects **winning combinations** based on standard and linear symbol matches.  
- Applies **bonus symbols** only if there is an existing win.  
- Supports different **reward multipliers** and **extra bonus values**.  
- Returns a reward amount based on the calculation of the betting amount entered by the user with the matrix formed in each try.
- Configurable via `GameConfig.java`.  

## File Structure  

src/ ├── 📄 GameEngine.java # Core logic for calculating game rewards.  
├── 📄 Config.java # Configuration for symbols and win combinations.  
├── 📄 GameResult.java # Stores the final game results.  
├── 📄 Main.java # Entry point for running the game.  
├── 📄 Probabilities.java # Configuration for Probabilities given in config.json file.  
├── 📄 Symbol.java # Configuration for Symbols given in config.json file.  
├── 📄 WinCombination.java # Configuration for WinCombination given in config.json file.  


Future Scope : 
1. Unit Testing Integration - Unit tests can be written under the src/tests folder