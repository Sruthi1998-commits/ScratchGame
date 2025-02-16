package com.scratchgame;

import java.util.List;
import java.util.Map;

public class Probabilities {
    public List<ProbabilityEntry> standard_symbols;
    public ProbabilityBonus bonus_symbols;
}

class ProbabilityEntry {
    public int column;
    public int row;
    public Map<String, Integer> symbols;
}

class ProbabilityBonus {
    public Map<String, Integer> symbols;
}
