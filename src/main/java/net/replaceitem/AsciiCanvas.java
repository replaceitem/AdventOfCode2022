package net.replaceitem;

import java.util.Arrays;

public class AsciiCanvas {
    public char[][] pixels;

    public AsciiCanvas(int w, int h, char initial) {
        pixels = new char[h][w];
        for(char[] row : pixels) {
            Arrays.fill(row, initial);
        }
    }

    public void setPixel(int x, int y, char c) {
        this.pixels[y][x] = c;
    }

    public void print() {
        for (char[] row : pixels) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.print('\n');
        }
    }
}
