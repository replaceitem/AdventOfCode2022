package net.replaceitem.days;

import net.replaceitem.Util;

import java.io.IOException;
import java.util.List;

public class Day2_2 {
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
            GameResult neededResult = GameResult.ofChar(s[1].charAt(0));
            Shape myMove = neededResult.getMyMove(opponentMove);

            total += myMove.getShapeScore();
            total += neededResult.getMoveScore();
        }
        System.out.println(total);
    }

    public enum GameResult {
        LOSE, DRAW, WIN;

        public static GameResult ofChar(char c) {
            int num = c-'X';
            if(num < 0 || num >= 3) throw new RuntimeException("Invalid char for game result: " + c);
            return GameResult.values()[num];
        }

        public Shape getMyMove(Shape opponentMove) {
            return Shape.values()[((opponentMove.ordinal()+this.ordinal()-1)%3+3)%3];
        }

        public int getMoveScore() {
            return this.ordinal()*3;
        }
    }

    private enum Shape {
        ROCK, PAPER, SCISSORS;

        public static Shape ofChar(char c) {
            int num = c-'A';
            if(num < 0 || num >= 3) throw new RuntimeException("Invalid char for shape: " + c);
            return Shape.values()[num];
        }

        public int getShapeScore() {
            return this.ordinal()+1;
        }
    }
}