package com.scratchgame;

public class Symbol {
    private String name;
    private double rewardMultiplier;
    private String type;
    private String impact;
    private int extra;

    public Symbol(String name, double rewardMultiplier, String type, String impact, int extra) {
        this.name = name;
        this.rewardMultiplier = rewardMultiplier;
        this.type = type;
        this.impact = impact;
        this.extra = extra;
    }

    public String getName() { return name; }
    public double getRewardMultiplier() { return rewardMultiplier; }
    public String getType() { return type; }
    public String getImpact() { return impact; }
    public int getExtra() { return extra; }
}
