package com.pratyush.riddlemaster.model;

public class LeaderboardEntry {
    private String name;
    private int score;
    private String at;

    public LeaderboardEntry() {}

    public LeaderboardEntry(String name, int score, String at) {
        this.name = name; this.score = score; this.at = at;
    }

    public String getName(){ return name; }
    public int getScore(){ return score; }
    public String getAt(){ return at; }
    public void setScore(int s){ this.score = s; }
}
