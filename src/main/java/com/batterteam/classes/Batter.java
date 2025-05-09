/**
 * Author Name: Batter Team
 * Date: 4/22/25
 * File Name: Batter.java
 * Last Update: 5/09/25 by Seth I.
 * Program Description: Class that contains methods and information for a batter.
 */

/*
============= CHANGE LOG =============
James F. - 4/22/25 - added variables, constructors, and gets/sets.
Seth I. - 4/30/25 - Updated package from com.batterteam.main to com.batterteam.classes;
Seth I. - 4/30/25 - Added variables and an associated constructor based on database variables - STILL NEED TO ADD GETTERS/SETTERS
Seth I. - 5/04/25 - Added playerPosition variable as well as made all class variables private as they'll need to be accessed through a getter/setter
Seth I. - 5/04/25 - Added setters and getters for newely added variables
Seth I. - 5/06/25 - Added a new constructor for batter with just essential database variables + added teamID class variable
Seth I. - 5/06/25 - Modified batterAsString() methods to work with new constructor
Seth I. - 5/09/25 - Added new variables for averages tracking, added several new getters and setters, added new methods for report generating and calculations (thanks team for the formulas!)

======================================
 */

package com.batterteam.classes;
import java.util.ArrayList;
import java.util.Locale;

public class Batter {
    
    // Class Variables
    private int playerID;
    private String playerName;
    private String playerFirstName;
    private String playerLastName;
    private String playerPosition;
    private String dateOfGame;
    private String teamName;
    private int teamID;
    private int atBats;
    private int hits;
    private int homeRuns;
    private int strikeOuts;
    private int walks;
    private int runsBattedIn;  
    private int runs;
    private int doubles;
    private int triples;
    
    private int totalBases;
    private double sluggingAmount;
    private double battingAverage;
    private double onBasePerc;
    
    private int basesOnBalls;
    private int sacrificFly;
    private int sacrificBunt;
    private int hitByPitch;
    private int leftOnBase;
    private int stolenBaseAttempts;
    private int homePlate;
    
    // ============= Constructors =============
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
    public Batter(String firstName, String lastName, int tID) {
        playerFirstName = firstName;
        playerLastName = lastName;
        teamID = tID;
    }
    public Batter(String firstName, String lastName, String team, String playPos, String dayOfG, int ab, int h, int hr, int so, int rbi, int runAmount, 
                    int doublesAmount, int triplesAmount, int bob, int sf, int sb, int hbp, int lob, int sb_att, int hp) {
        playerFirstName = firstName;
        playerLastName = lastName;
        teamName = team;
        playerPosition = playPos;
        dateOfGame = dayOfG;
        atBats = ab;
        hits = h;
        homeRuns = hr;
        strikeOuts = so;
        runsBattedIn = rbi;
        runs = runAmount;
        doubles = doublesAmount;
        triples = triplesAmount;
        basesOnBalls = bob;
        sacrificFly = sf;
        sacrificBunt = sb;
        hitByPitch = hbp;
        leftOnBase = lob;
        stolenBaseAttempts = sb_att;
        homePlate = hp;
       
        totalBases = calculateTotalBases(h, doublesAmount, triplesAmount, hr);
        sluggingAmount = calcSluggingPercent(totalBases, ab);
        battingAverage = calcBattingAverage(h, ab);
        onBasePerc = calcOnBasePercent(h, bob, hbp, ab, sf);
        
        updateBattingAverage();
    }
    
    // ============= Getters / Setters =============
    // == PlayerID ==
    public int getPlayerID() {
        return playerID;
    }
    public void setPlayerID(int id) {
        this.playerID = id;
    }
    
    // == Full Name Getter/Setter ==
    public String getName() {
        return playerName;
    }
    public void setName(String name) {
        this.playerName = name;
    }

    // == Team Name Getter/Setter ==
    public String getTeam() {
        return teamName;
    }
    public void setTeam(String team) {
        this.teamName = team;
    }
    
    // == Team ID Getter/Setter ==
    public int getTeamID() {
        return teamID;
    }
    public void setTeam(int teamID) {
        this.teamID = teamID;
    }

    // == At Bats Getter/Setter ==
    public int getAtBats() {
        return atBats;
    }
    public void setAtBats(int ab) {
        this.atBats = ab;
        updateBattingAverage();
    }

    // == Hits Getter/Setter ==
    public int getHits() {
        return hits;
    }
    public void setHits(int h) {
        this.hits = h;
        updateBattingAverage();
    }

    // == Home Runs Getter/Setter ==
    public int getHomeRuns() {
        return homeRuns;
    }
    public void setHomeRuns(int hr) {
        this.homeRuns = hr;
    }

    // == Strikeouts Getter/Setter ==
    public int getStrikeOuts() {
        return strikeOuts;
    }
    public void setStrikeOuts(int so) {
        this.strikeOuts = so;
    }

    // == Walks Getter/Setter ==
    public int getWalks() {
        return walks;
    }
    public void setWalks(int wk) {
        this.walks = wk;
        this.basesOnBalls = wk;
    }

    // == Runs Batted In (RBI) Getter/Setter ==
    public int getRBI() {
        return runsBattedIn;
    }
    public void setRBI(int rbi) {
        this.runsBattedIn = rbi;
    }

    // == Batting Average Getter (Setter not needed as it's calculated) ==
    public double getBattingAvg() {
        return battingAverage;
    }
    public void setBattingAvg(double batAv) {
        this.battingAverage = batAv;
    }

    // == Player First Name Getter/Setter ==
    public String getPlayerFirstName() {
        return playerFirstName;
    }
    public void setPlayerFirstName(String playerFirstName) {
        this.playerFirstName = playerFirstName;
        this.playerName = playerFirstName + " " + this.playerLastName;
    }

    // == Player Last Name Getter/Setter ==
    public String getPlayerLastName() {
        return playerLastName;
    }
    public void setPlayerLastName(String playerLastName) {
        this.playerLastName = playerLastName;
        this.playerName = this.playerFirstName + " " + playerLastName;
    }

    // == Player Position Getter/Setter ==
    public String getPlayerPosition() {
        return playerPosition;
    }
    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    // == Runs Getter/Setter ==
    public int getRuns() {
        return runs;
    }
    public void setRuns(int runs) {
        this.runs = runs;
    }

    // == Doubles Getter/Setter ==
    public int getDoubles() {
        return doubles;
    }
    public void setDoubles(int doubles) {
        this.doubles = doubles;
    }

    // == Triples Getter/Setter ==
    public int getTriples() {
        return triples;
    }
    public void setTriples(int triples) {
        this.triples = triples;
    }

    // == Total Bases Getter/Setter ==
    public int getTotalBases() {
        return totalBases;
    }
    public void setTotalBases(int totalBases) {
        this.totalBases = totalBases;
    }

    // == Bases on Balls Getter/Setter ==
    public int getBasesOnBalls() {
        return basesOnBalls;
    }
    public void setBasesOnBalls(int basesOnBalls) {
        this.basesOnBalls = basesOnBalls;
        this.walks = basesOnBalls;
    }

    // == Sacrifice Fly Getter/Setter ==
    public int getSacrificFly() {
        return sacrificFly;
    }
    public void setSacrificFly(int sacrificFly) {
        this.sacrificFly = sacrificFly;
    }

    // == Sacrifice Bunt Getter/Setter ==
    public int getSacrificBunt() {
        return sacrificBunt;
    }
    public void setSacrificBunt(int sacrificBunt) {
        this.sacrificBunt = sacrificBunt;
    }

    // == Hit By Pitch Getter/Setter ==
    public int getHitByPitch() {
        return hitByPitch;
    }
    public void setHitByPitch(int hitByPitch) {
        this.hitByPitch = hitByPitch;
    }

    // == Left On Base Getter/Setter ==
    public int getLeftOnBase() {
        return leftOnBase;
    }
    public void setLeftOnBase(int leftOnBase) {
        this.leftOnBase = leftOnBase;
    }

    // == Stolen Base Attempts Getter/Setter ==
    public int getStolenBaseAttempts() {
        return stolenBaseAttempts;
    }
    public void setStolenBaseAttempts(int stolenBaseAttempts) {
        this.stolenBaseAttempts = stolenBaseAttempts;
    }

    // == Home Plate Appearances Getter/Setter ==
    public int getHomePlate() {
        return homePlate;
    }
    public void setHomePlate(int homePlate) {
        this.homePlate = homePlate;
    }
    
    // == Date of Game Getter/Setter ==
    public String getDateOfGame() {
        return dateOfGame;
    }
    public void setDateOfGame(String dateOfGame) {
        this.dateOfGame = dateOfGame;
    }
    
    // == Slugging Amount Getter/Setter ==
    public double getSluggingAmount() {
        return sluggingAmount;
    }
    public void setSluggingAmount(double slugAm) {
        this.sluggingAmount = slugAm;
    }
    
    // == On Base Percentage Getter/Setter ==
    public double getOnBasePerc() {
        return onBasePerc;
    }
    public void setOnBasePerc(double onBasePer) {
        this.onBasePerc = onBasePer;
    }
    
    // ============= Class Methods =============
    // Method to update batting average
    private void updateBattingAverage() {
        if (atBats > 0) {
            this.battingAverage = (double) hits / atBats;
        } else {
            this.battingAverage = 0.0;
        }
    }    
    
    // Method returns the batting average of a batter
    public static double calcBattingAverage(int hits, int atBats) {
        if (atBats == 0) {
            return 0.0;
        } else {
            return (double) hits / atBats;
        }
    }
    
    // Method returns the on-base percentage of a batter as a decimal
    public static double calcOnBasePercent(int hits, int walks, int hbp, int atBats, int sacFlies) {
        int top = hits + walks + hbp;
        int bottom = atBats + walks + hbp + sacFlies;

        if (bottom == 0) {
            return 0.0;
        } else {
            return (double) top / bottom;
        }
    }
    
    // Method returns the on-base percentage of a batter as a decimal
    public static double calcSluggingPercent(int totalBases, int atBats) {
        return totalBases / atBats;
    }
    
    // Method returns the total bases of a batter
    public static int calculateTotalBases(int hits, int doubles, int triples, int homeRuns) {
        return hits + (2 * doubles) + (3 * triples) + (4 * homeRuns);
    }
    
    // Method creates a string used to print out batter stats for a report of a SINGLE game
    public static String batterAsString(Batter b, String dateOfGame) {


        
        // Get team name from teamID
        String teamName = BatterAppDB.getTeamFromTeamID(b.getTeamID());
        
        // Create a full batter WITH statistics from a single game for us to display
        Batter batter = BatterAppDB.buildFullBatterObjectFromDBSingleGame(b.getPlayerFirstName(), b.getPlayerLastName(), dateOfGame, teamName);
        
        // Check if a game was actually played on dateOfGame and if so...create string message
        if (BatterAppDB.checkIfGameExists(dateOfGame)) {      
                        
            // Initalize String
            String batterString = "";
            batterString += "=== " + batter.getPlayerFirstName() + "'s Stats ===\n";
            batterString += "=== For Game Played: " + dateOfGame + " ===\n";
            batterString += "First Name: " + batter.getPlayerFirstName() + "\n";
            batterString += "Last Name: " + batter.getPlayerLastName() + "\n";
            batterString += "Team: " + teamName + "\n";
            batterString += "Played Position: " + batter.getPlayerPosition() + "\n\n";

            // String to be used in the String.format() method to ensure stats are displayed in even rows of three columns.
            String formatForThreeLabels = "%-7s%-4s   %-7s%-4s   %-7s%-4s%n"; 

            // Display stats as multiple rows of three variables
            batterString += String.format(formatForThreeLabels,
                "AB: ", String.valueOf(batter.getAtBats()),
                "H: ", String.valueOf(batter.getHits()),
                "HR: ", String.valueOf(batter.getHomeRuns()));
            batterString += String.format(formatForThreeLabels,
                "SO: ", String.valueOf(batter.getStrikeOuts()),
                "R: ", String.valueOf(batter.getRuns()),
                "RBI: ", String.valueOf(batter.getRBI()));
            batterString += String.format(formatForThreeLabels,
                "2B: ", String.valueOf(batter.getDoubles()),
                "3B: ", String.valueOf(batter.getTriples()),
                "TB: ", String.valueOf(batter.getTotalBases()));
            batterString += String.format(formatForThreeLabels,
                "HP: ", String.valueOf(batter.getHomePlate()),
                "BB: ", String.valueOf(batter.getBasesOnBalls()),
                "SF: ", String.valueOf(batter.getSacrificFly()));
            batterString += String.format(formatForThreeLabels,
                "SB: ", String.valueOf(batter.getSacrificBunt()),
                "HBP: ", String.valueOf(batter.getHitByPitch()),
                "LOB: ", String.valueOf(batter.getLeftOnBase()));
            batterString += String.format("%-7s%-4s%n", "SB-ATT: ", String.valueOf(batter.getStolenBaseAttempts()));
            batterString += String.format("%-7s%-4s%n", "Batting Average: ", String.format(Locale.US, "%.2f", batter.getBattingAvg()));
            batterString += String.format("%-7s%-4s%n", "Slugging Amount: ", String.format(Locale.US, "%.2f", batter.getSluggingAmount()));
            batterString += String.format("%-7s%-4s%n%n", "On Base: ", String.format(Locale.US, "%.2f", batter.getOnBasePerc()));

            return batterString;
        } else return "NO RECORDS FOUND!\n" + b.getPlayerFirstName() + " " + b.getPlayerLastName() + " didn't play on " + dateOfGame;
    }
    
    // Method creates a string used to print out batter stats for a report of MULLTIPLE games
    public static String batterAsString(Batter b, String dateOfFirstGame, String dateOfLastGame) {

        // Initalize values for summarization 
        int atBatTotal = 0;
        int hitsTotal = 0;
        int homeRunTotal = 0;
        int strikeOutTotal = 0;
        int runsTotal = 0;
        int rbiTotal = 0;
        int doubleTotal = 0;
        int tripleTotal = 0;
        int totalBaseTotal = 0;
        int homePlateTotal = 0;
        int bobTotal = 0;
        int sacFlyTotal = 0;
        int sacBuntTotal = 0;
        int hitByPitchTotal = 0;
        int leftOnBaseTotal = 0;
        int stolenBaseTotal = 0;
        
        // Get team name from teamID
        String teamName = BatterAppDB.getTeamFromTeamID(b.getTeamID());
        
        if (BatterAppDB.checkIfGameExistsBetween(dateOfFirstGame, dateOfLastGame)) {
        
            // Create an array of game dates found between dateOfFirstGame and dateOfLastGame
            ArrayList gameDateArray = BatterAppDB.getGameDateArrayInRange(dateOfFirstGame, dateOfLastGame);

            // String to be used in the String.format() method to ensure stats are displayed in even rows of three columns.
                String formatForThreeLabels = "%-7s%-4s   %-7s%-4s   %-7s%-4s%n";
            
            // Initalize String
            String batterString = "=== " + b.getPlayerFirstName() + "'s Stats ===\n";  
            batterString += "First Name: " + b.getPlayerFirstName() + "\n";
            batterString += "Last Name: " + b.getPlayerLastName() + "\n";
            batterString += "Team: " + teamName + "\n\n";

            for (var date : gameDateArray) {
                
                // Create a full batter WITH statistics from a single game for us to display
                Batter batter = BatterAppDB.buildFullBatterObjectFromDBSingleGame(b.getPlayerFirstName(), b.getPlayerLastName(), (String)date, teamName);    

                // Update running totals
                atBatTotal += batter.getAtBats();
                hitsTotal += batter.getHits();
                homeRunTotal += batter.getHomeRuns();
                strikeOutTotal += batter.getStrikeOuts();
                runsTotal += batter.getRuns();
                rbiTotal += batter.getRBI();
                doubleTotal += batter.getDoubles();
                tripleTotal += batter.getTriples();
                totalBaseTotal += batter.getTotalBases();
                homePlateTotal += batter.getHomePlate();
                bobTotal += batter.getBasesOnBalls();
                sacFlyTotal += batter.getSacrificFly();
                sacBuntTotal += batter.getSacrificBunt();
                hitByPitchTotal += batter.getHitByPitch();
                leftOnBaseTotal += batter.getLeftOnBase();
                stolenBaseTotal += batter.getStolenBaseAttempts();
                
                batterString += "=== For Game Played: " + date + " ===\n";         
                batterString += "Played Position: " + batter.getPlayerPosition() + "\n";       

                // Display stats as multiple rows of three variables
                batterString += String.format(formatForThreeLabels,
                    "AB: ", String.valueOf(batter.getAtBats()),
                    "H: ", String.valueOf(batter.getHits()),
                    "HR: ", String.valueOf(batter.getHomeRuns()));
                batterString += String.format(formatForThreeLabels,
                    "SO: ", String.valueOf(batter.getStrikeOuts()),
                    "R: ", String.valueOf(batter.getRuns()),
                    "RBI: ", String.valueOf(batter.getRBI()));
                batterString += String.format(formatForThreeLabels,
                    "2B: ", String.valueOf(batter.getDoubles()),
                    "3B: ", String.valueOf(batter.getTriples()),
                    "TB: ", String.valueOf(batter.getTotalBases()));
                batterString += String.format(formatForThreeLabels,
                    "HP: ", String.valueOf(batter.getHomePlate()),
                    "BB: ", String.valueOf(batter.getBasesOnBalls()),
                    "SF: ", String.valueOf(batter.getSacrificFly()));
                batterString += String.format(formatForThreeLabels,
                    "SB: ", String.valueOf(batter.getSacrificBunt()),
                    "HBP: ", String.valueOf(batter.getHitByPitch()),
                    "LOB: ", String.valueOf(batter.getLeftOnBase()));
                batterString += String.format("%-7s%-4s%n", "SB-ATT: ", String.valueOf(batter.getStolenBaseAttempts()));
                batterString += String.format("%-7s%-4s%n", "Batting Average: ", String.format(Locale.US, "%.2f", batter.getBattingAvg()));
                batterString += String.format("%-7s%-4s%n", "Slugging Amount: ", String.format(Locale.US, "%.2f", batter.getSluggingAmount()));
                batterString += String.format("%-7s%-4s%n%n", "On Base: ", String.format(Locale.US, "%.2f", batter.getOnBasePerc()));
            }
            
            // Calculate Overall Percentages and Display All Stats
            double batAvg = calcBattingAverage(hitsTotal, atBatTotal);
            double slugAmnt = calcSluggingPercent(totalBaseTotal, atBatTotal);
            double onBasePerc = calcOnBasePercent(hitsTotal, bobTotal, hitByPitchTotal, atBatTotal, sacFlyTotal);
            
            batterString += "=== Cumulative Scores for All Games! ===\n";
            batterString += String.format(formatForThreeLabels,
                    "AB: ", atBatTotal,
                    "H: ", hitsTotal,
                    "HR: ", homeRunTotal);
                batterString += String.format(formatForThreeLabels,
                    "SO: ", strikeOutTotal,
                    "R: ", runsTotal,
                    "RBI: ", rbiTotal);
                batterString += String.format(formatForThreeLabels,
                    "2B: ", doubleTotal,
                    "3B: ", tripleTotal,
                    "TB: ", totalBaseTotal);
                batterString += String.format(formatForThreeLabels,
                    "HP: ", homePlateTotal,
                    "BB: ", bobTotal,
                    "SF: ", sacFlyTotal);
                batterString += String.format(formatForThreeLabels,
                    "SB: ", sacBuntTotal,
                    "HBP: ", hitByPitchTotal,
                    "LOB: ", leftOnBaseTotal);
            batterString += String.format("%-7s%-4s%n", "Batting Average: ", String.format(Locale.US, "%.2f", batAvg));
            batterString += String.format("%-7s%-4s%n", "Slugging Amount: ", String.format(Locale.US, "%.2f", slugAmnt));
            batterString += String.format("%-7s%-4s%n%n", "On Base: ", String.format(Locale.US, "%.2f", onBasePerc));
                        
            return batterString;
        } else return "NO RECORDS FOUND!\n" + b.getPlayerFirstName() + " " + b.getPlayerLastName() + " didn't play between " + dateOfFirstGame + " and " + dateOfLastGame;
    }
    
    // Method creates a string used to print out stats for a report of all batters on a team during ONE games
    public static String battersTeamAsString(ArrayList<Batter> batterList, String dateOfGame) {

        // Initalize values for summarization 
        int atBatTotal = 0;
        int hitsTotal = 0;
        int homeRunTotal = 0;
        int strikeOutTotal = 0;
        int runsTotal = 0;
        int rbiTotal = 0;
        int doubleTotal = 0;
        int tripleTotal = 0;
        int totalBaseTotal = 0;
        int homePlateTotal = 0;
        int bobTotal = 0;
        int sacFlyTotal = 0;
        int sacBuntTotal = 0;
        int hitByPitchTotal = 0;
        int leftOnBaseTotal = 0;
        int stolenBaseTotal = 0;
              
        if (BatterAppDB.checkIfGameExists(dateOfGame)) {
        
            // String to be used in the String.format() method to ensure stats are displayed in even rows of three columns.
            String formatForThreeLabels = "%-7s%-4s   %-7s%-4s   %-7s%-4s%n";
            
            // Initalize String
            String batterString = "Stats For Batters From Game Played " + dateOfGame + "\n";          

            for (var batter : batterList) {
                
                batterString += "=== " + batter.getPlayerFirstName() + " " + batter.getPlayerLastName() + "'s Stats ===\n"; 

                // Update running totals
                atBatTotal += batter.getAtBats();
                hitsTotal += batter.getHits();
                homeRunTotal += batter.getHomeRuns();
                strikeOutTotal += batter.getStrikeOuts();
                runsTotal += batter.getRuns();
                rbiTotal += batter.getRBI();
                doubleTotal += batter.getDoubles();
                tripleTotal += batter.getTriples();
                totalBaseTotal += batter.getTotalBases();
                homePlateTotal += batter.getHomePlate();
                bobTotal += batter.getBasesOnBalls();
                sacFlyTotal += batter.getSacrificFly();
                sacBuntTotal += batter.getSacrificBunt();
                hitByPitchTotal += batter.getHitByPitch();
                leftOnBaseTotal += batter.getLeftOnBase();
                stolenBaseTotal += batter.getStolenBaseAttempts();
                
                // Display stats as multiple rows of three variables
                batterString += String.format(formatForThreeLabels,
                    "AB: ", String.valueOf(batter.getAtBats()),
                    "H: ", String.valueOf(batter.getHits()),
                    "HR: ", String.valueOf(batter.getHomeRuns()));
                batterString += String.format(formatForThreeLabels,
                    "SO: ", String.valueOf(batter.getStrikeOuts()),
                    "R: ", String.valueOf(batter.getRuns()),
                    "RBI: ", String.valueOf(batter.getRBI()));
                batterString += String.format(formatForThreeLabels,
                    "2B: ", String.valueOf(batter.getDoubles()),
                    "3B: ", String.valueOf(batter.getTriples()),
                    "TB: ", String.valueOf(batter.getTotalBases()));
                batterString += String.format(formatForThreeLabels,
                    "HP: ", String.valueOf(batter.getHomePlate()),
                    "BB: ", String.valueOf(batter.getBasesOnBalls()),
                    "SF: ", String.valueOf(batter.getSacrificFly()));
                batterString += String.format(formatForThreeLabels,
                    "SB: ", String.valueOf(batter.getSacrificBunt()),
                    "HBP: ", String.valueOf(batter.getHitByPitch()),
                    "LOB: ", String.valueOf(batter.getLeftOnBase()));
                batterString += String.format("%-7s%-4s%n", "SB-ATT: ", String.valueOf(batter.getStolenBaseAttempts()));
                batterString += String.format("%-7s%-4s%n", "Batting Average: ", String.format(Locale.US, "%.2f", batter.getBattingAvg()));
                batterString += String.format("%-7s%-4s%n", "Slugging Amount: ", String.format(Locale.US, "%.2f", batter.getSluggingAmount()));
                batterString += String.format("%-7s%-4s%n%n", "On Base: ", String.format(Locale.US, "%.2f", batter.getOnBasePerc()));
            }
                                   
            return batterString;
        } else return "NO RECORDS FOUND!\nNo game was played " + dateOfGame;
    }
}