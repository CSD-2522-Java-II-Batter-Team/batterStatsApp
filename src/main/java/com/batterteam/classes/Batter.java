/**
 * Author Name: James F.
 * Date: 4/22/25
 * File Name: Batter.java
 * Last Update: 4/22/25 by James F.
 * Program Description: Class that contains methods and information for a batter.
 */

/*
============= CHANGE LOG =============
James F. - 4/22/25 - added variables, constructors, and gets/sets.
Seth I. - 4/29/25 - Test Comment


======================================
 */

package com.batterteam.main;

public class Batter {
    // variables
    String playerName;
    String teamName;
    int atBats;
    int hits;
    int homeRuns;
    int strikeOuts;
    int walks;
    int runsBattedIn;
    double battingAverage;
    
    
    // constructors
    public Batter(String name) {
        playerName = name;
        teamName = "";
        atBats = 0;
        hits = 0;
        homeRuns = 0;
        strikeOuts = 0;
        walks = 0;
        runsBattedIn = 0;
        battingAverage = 0;
    }
    public Batter(String name, String team, int ab, int h, int hr, int so, int wk, int rbi) {
        playerName = name;
        teamName = team;
        atBats = ab;
        hits = h;
        homeRuns = hr;
        strikeOuts = so;
        walks = wk;
        runsBattedIn = rbi;
        updateBattingAverage();
    }
    
    // gets
    public String getName() {
        return playerName;
    }
    public String getTeam() {
        return teamName;
    }
    public int getAtBats() {
        return atBats;
    }
    public int getHits() {
        return hits;
    }
    public int getHomeRuns() {
        return homeRuns;
    }
    public int getStrikeOuts() {
        return strikeOuts;
    }
    public int getWalks() {
        return walks;
    }
    public int getRBI() {
        return runsBattedIn;
    }
    public double getAvg() {
        return battingAverage;
    }
    
    // sets
    public void updateBattingAverage() {
        battingAverage = (double) hits / atBats;
    }
    public void setName(String name) {
        playerName = name;
    }
    public void setTeam(String team) {
        teamName = team;
    }
    public void setAtBats(int ab) {
        atBats = ab;
    }
    public void setHits(int h) {
        hits = h;
    }
    public void setHomeRuns(int hr) {
        homeRuns = hr;
    }
    public void setStrikeOuts(int so) {
        strikeOuts = so;
    }
    public void setWalks(int wk) {
        walks = wk;
    }
    public void setRBI(int rbi) {
        runsBattedIn = rbi;
    }
    
    // print
}
