package net.replaceitem.days;

import net.replaceitem.Util;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day6 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Util.loadInput("input6");
        String line = String.join("", lines);

        List<Character> prev = new ArrayList<>();
        for(int i = 0; i < line.length(); i++) {
            prev.add(line.charAt(i));
            while(prev.size() > 4) prev.remove(0);
            if(new HashSet<>(prev).size() == 4) {
                System.out.println("Packet marker: " + (i + 1));
                break;
            }
        }

        for(int i = 0; i < line.length(); i++) {
            prev.add(line.charAt(i));
            while(prev.size() > 14) prev.remove(0);
            if(new HashSet<>(prev).size() == 14) {
                System.out.println("Message marker: " + (i + 1));
                break;
            }
        }
    }
}