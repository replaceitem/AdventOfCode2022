package net.replaceitem.days;

import net.replaceitem.Util;

import java.io.IOException;
import java.util.List;

public class Day3_2 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Util.loadInput("input3");
        long start = System.nanoTime();
        int total = 0;
        for (int i = 0; i < lines.size(); i+=3) {
            char item = getCommonItem(lines.get(i), lines.get(i+1), lines.get(i+2));
            total += getPriority(item);
        }
        long duration = System.nanoTime()-start;
        System.out.println("Duration: " + ((double)duration)*0.001 + " µs");
        System.out.println("Total: " + total);
    }

    private static char getCommonItem(String a, String b, String c) {
        for (int i = 0; i < a.length(); i++) {
            char charAt = a.charAt(i);
            if(b.indexOf(charAt) >= 0 && c.indexOf(charAt) >= 0) return charAt;
        }
        throw new RuntimeException("Couldn't find common item");
    }

    private static int getPriority(char c) {
        if(c >= 'a') return c-'a'+1;
        if(c >= 'A') return c-'A'+27;
        throw new RuntimeException("What on earth is that character");
    }
}