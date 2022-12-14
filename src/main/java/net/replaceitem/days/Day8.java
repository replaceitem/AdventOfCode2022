package net.replaceitem.days;

import net.replaceitem.Util;

import java.io.IOException;
import java.util.List;

public class Day8 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Util.loadInput("input8");

        char[][] trees = new char[lines.get(0).length()][lines.size()];

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                trees[x][y] = line.charAt(x);
            }
        }

        int count = 0;
        for (int i = 0; i < trees.length; i++) {
            char[] col = trees[i];
            for (int j = 0; j < col.length; j++) {
                if (isVisible(i,j,trees)) count++;
            }
        }

        int maxScore = 0;
        for (int i = 0; i < trees.length; i++) {
            char[] col = trees[i];
            for (int j = 0; j < col.length; j++) {
                int score = getScenicScore(i, j, trees);
                if(score > maxScore) maxScore = score;
            }
        }

        System.out.println(count);
        System.out.println(maxScore);



    }

    public static int getScenicScore(int x, int y, char[][] trees) {
        int w = trees.length;
        int h = trees[0].length;
        int treeHeight = trees[x][y];

        int westTrees = 0;
        int i = x-1;
        while(i >= 0) {
            westTrees++;
            if(trees[i][y] >= treeHeight) break;
            i--;
        }

        int eastTrees = 0;
        i = x+1;
        while(i < w) {
            eastTrees++;
            if(trees[i][y] >= treeHeight) break;
            i++;
        }

        int northTrees = 0;
        i = y-1;
        while(i >= 0) {
            northTrees++;
            if(trees[x][i] >= treeHeight) break;
            i--;
        }

        int southTrees = 0;
        i = y+1;
        while(i < h) {
            southTrees++;
            if(trees[x][i] >= treeHeight) break;
            i++;
        }


        return northTrees*southTrees*westTrees*eastTrees;
    }

    public static boolean isVisible(int x, int y, char[][] trees) {
        int w = trees.length;
        int h = trees[0].length;
        int treeHeight = trees[x][y];

        if(x == 0 || y == 0) return true;
        if(x == w-1) return true;
        if(y == h-1) return true;


        boolean westVisible = true;
        for(int i = 0; i < x; i++) {
            if(trees[i][y] >= treeHeight) {
                westVisible = false;
                break;
            }
        }
        boolean eastVisible = true;
        for(int i = x+1; i < w; i++) {
            if(trees[i][y] >= treeHeight) {
                eastVisible = false;
                break;
            }
        }

        boolean northVisible = true;
        for(int i = 0; i < y; i++) {
            if(trees[x][i] >= treeHeight) {
                northVisible = false;
                break;
            }
        }
        boolean southVisible = true;
        for(int i = y+1; i < h; i++) {
            if(trees[x][i] >= treeHeight) {
                southVisible = false;
                break;
            }
        }

        return eastVisible || northVisible || southVisible | westVisible;
    }
}
