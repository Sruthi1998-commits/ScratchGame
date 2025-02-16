package com.scratchgame;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class Config {
    @JsonProperty("columns")
    public int columns;

    @JsonProperty("rows")
    public int rows;

    @JsonProperty("symbols")
    public Map<String, Symbol> symbols;

    @JsonProperty("probabilities")
    public Probabilities probabilities;

    @JsonProperty("win_combinations")
    public Map<String, WinCombination> winCombinations;
}
