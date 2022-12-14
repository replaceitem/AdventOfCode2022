package net.replaceitem.days;

import net.replaceitem.Util;

import java.io.IOException;
import java.util.List;

public class Day3_1 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Util.loadInput("input3");
        long start = System.nanoTime();
        int total = 0;
        for (String line : lines) {
            int halfLen = line.length()/2;
            String firstCompartment = line.substring(0, halfLen);
            String secondCompartment = line.substring(halfLen);
            char common = getCommonItem(firstCompartment, secondCompartment);
            int priority = getPriority(common);
            total += priority;
        }
        long duration = System.nanoTime()-start;
        System.out.println("Duration: " + ((double)duration)*0.001 + " µs");
        System.out.println("Total: " + total);
    }

    private static char getCommonItem(String a, String b) {
        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            if(b.indexOf(c) >= 0) return c;
        }
        throw new RuntimeException("Couldn't find common item");
    }

    private static int getPriority(char c) {
        if(c >= 'a') return c-'a'+1;
        if(c >= 'A') return c-'A'+27;
        throw new RuntimeException("What on earth is that character");
    }
}