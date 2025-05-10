/**
 * Author Name: Batter Team
 * Date: 5/06/25
 * File Name: BatterAppDB.java
 * Last Update: 5/09/25 by Seth I.
 * Program Description: Class that contains methods to validate user input
 */

/*
============= CHANGE LOG =============
Seth I - 5/9/25 - Added methods to validate Strings and ensure they are formatted with a capital letter at the start and then lowercase afterwards.
Seth I - 5/9/25 - Updated integer and double methods to ensure the retrieved value is positive.

======================================
 */

package com.batterteam.classes;

public class Validation {

    private final String lineEnd;
    
    public Validation() { 
        this.lineEnd = "\n";  
    }
    
    public Validation(String lineEnd) {
        this.lineEnd = lineEnd;
    }
    
    public String isPresent(String value, String name) {
        String msg = "";
        if (value.isEmpty()) {
            msg = name + " is required." + lineEnd;
        }
        return msg;
    }

    public String isDouble(String value, String name) {
        String msg = "";
        try {
            double number = Double.parseDouble(value);
            if (number < 0) {
                msg = name + " must be a postive number (0 and up)." + lineEnd;
                return msg;
            } 
        } catch (NumberFormatException e) {
            msg = name + " must be a valid number." + lineEnd;
        }
        return msg;
    }
    
    public String isInteger(String value, String name) {
        String msg = "";
        try {
            int number = Integer.parseInt(value);
            if (number < 0) {
                msg = name + " must be a postive integer (0 and up)." + lineEnd;
                return msg;
            } 
        } catch (NumberFormatException e) {
            msg = name + " must be an integer." + lineEnd;
        }
        return msg;
    }
    
    // Method ensures a word always have a capitalized first letter in a sentence
    public String capitalizeWords(String sentence) {
        
        // If nothing or just empty space is detected...return null
        if (sentence == null || sentence.trim().isEmpty()) {
            return null;
        }
        
        // Otherwise create a sentence splitting words on a "space"
        String[] words = sentence.split("\\s+");
        String result = "";
        for (int i = 0; i < words.length; i++) {
            // Scan through word capitalizing the first and ensuring the rest are lowercase. 
            String word = words[i];
            if (!word.isEmpty()) {
                result += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                if (i < words.length - 1) {
                    result += " ";
                }
            }
        }
        return result;
    }
}