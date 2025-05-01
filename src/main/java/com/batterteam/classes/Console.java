package com.batterteam.classes;

import java.util.Scanner;

public class Console {
    private static final Scanner sc = new Scanner(System.in);

    public static void print(String s){
        System.out.print(s);
    }
    public static void println(){
        System.out.println();
    }
    public static void println(String s){
        System.out.println(s);
    }
    public static void printArray(int[] arrayToPrint) {
        String msg = "";
        for (int element : arrayToPrint) {
            msg += (element + ",");
        }
        int lastComma = msg.lastIndexOf(",");      
        System.out.print("[" + msg.substring(0,lastComma) + "]");
    }
    
    public static int getInt(String prompt) {
        int i = 0;
        boolean isValid = false;
        while (!isValid) {
            System.out.print(prompt);
            String input = sc.nextLine();

            if (input.isEmpty()) {
                System.out.println("Error! No input entered. Try again.");
                continue;
            }

            try {
                i = Integer.parseInt(input);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Error! Invalid integer. Try again.");
            }
        }
        return i;
    }
    
    public static int getInt(String prompt, int min, int max){
        int i = 0;
        boolean isValid = false;
        while (!isValid) {
            i = getInt(prompt);
            if (i <= min) {
                println("Error! Number must be greater than " + min + ".");
            } else if (i >= max) {
                println("Error! Number must be less than " + max + ".");
            } else {
                isValid = true;
            }
        }
        return i;
    }
    
    public static double getDouble(String prompt) {
        double d = 0;
        boolean isValid = false;
        while (!isValid) {
            System.out.print(prompt);
            String input = sc.nextLine();

            if (input.isEmpty()) {
                System.out.println("Error! No input entered. Try again.");
                continue;
            }

            try {
                d = Double.parseDouble(input);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Error! Invalid number. Try again.");
            }
        }
        return d;
    }
    
    public static double getDouble(String prompt, double min, double max){
        double d = 0;
        boolean isValid = false;
        while (!isValid) {
            d = getDouble(prompt);
            if (d <= min) {
                println("Error! Number must be greater than " + min + ".");
            } else if (d >= max) {
                println("Error! Number must be less than " + max + ".");
            } else {
                isValid = true;
            }
        }
        return d;
    }

    public static String getString(String prompt){
        String s = "";
        boolean isValid = false;
        while (isValid == false) {
            print(prompt);
            s = sc.nextLine();
            if (s.equals("")) {
                println("Error! This entry is required. Try again.");
            } else {
                isValid = true;
            }
        }
        return s;
    }
    public static String getString(String prompt, String s1, String s2){
        String s = "";
        boolean isValid = false;
        while (isValid == false) {
            s = getString(prompt);
            if (!s.equalsIgnoreCase(s1) && !s.equalsIgnoreCase(s2)) {
                println("Error! Entry must be '" + s1 + "' or '" + s2 + "'. Try again.");
            } else {
                isValid = true;
            }
        }
        return s;
    }
}