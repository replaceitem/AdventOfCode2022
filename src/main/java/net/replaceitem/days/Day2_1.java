package net.replaceitem.days;

import net.replaceitem.Util;

import java.io.IOException;
import java.util.List;

public class Day2_1 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Util.loadInput("input2");
        int total = 0;
        for (String line : lines) {
            if(line.isBlank()) continue;
            String[] s = line.split(" ");
            if(s.length != 2) {
                System.out.println("Weird line there: " + line);
                continue;
            }
            Shape opponentMove = Shape.ofChar(s[0].charAt(0));
            Shape myMove = Shape.ofChar(s[1].charAt(0));

            total += myMove.getShapeScore();
            total += myMove.getMoveScore(opponentMove);
        }
        System.out.println(total);
    }

    private enum Shape {
        ROCK, PAPER, SCISSORS;

        public static Shape ofChar(char c) {
            int num = c > 'C' ? c-'X' : c-'A';
            if(num < 0 || num >= 3) throw new RuntimeException("Invalid char for shape: " + c);
            return Shape.values()[num];
        }

        public int getShapeScore() {
            return this.ordinal()+1;
        }

        public int beats(Shape other) {
            int diff = this.ordinal() - other.ordinal();
            diff = (diff%3+3)%3;
            return switch (diff) {
                case 0 -> 0;
                case 1 -> 1;
                case 2 -> -1;
                default -> throw new RuntimeException("HOW?");
            };
        }

        public int getMoveScore(Shape other) {
            return switch(beats(other)) {
                case 0 -> 3;
                case 1 -> 6;
                case -1 -> 0;
                default -> throw new RuntimeException("HOW?");
            };
        }
    }
}