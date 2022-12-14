package net.replaceitem.days;

import net.replaceitem.Util;

import java.io.IOException;
import java.util.List;

public class Day4 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Util.loadInput("input4");
        long start = System.nanoTime();
        int totalContains = 0;
        int totalOverlaps = 0;
        for (String line : lines) {
            String[] ranges = line.split(",");
            Range a = Range.parse(ranges[0]);
            Range b = Range.parse(ranges[1]);
            if(a.contains(b) || b.contains(a)) totalContains++;
            if(a.overlaps(b)) totalOverlaps++;
            System.out.println(a + "\t" + b + "\t" + a.overlaps(b));
        }
        long duration = System.nanoTime()-start;
        System.out.println("Duration: " + ((double)duration)*0.001 + " µs");
        System.out.println("Total contains: " + totalContains);
        System.out.println("Total overlaps: " + totalOverlaps);
    }


    static class Range {
        protected int a, b;

        public Range(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public static Range parse(String s) {
            String[] values = s.split("-");
            return new Range(Integer.parseInt(values[0]),Integer.parseInt(values[1]));
        }

        public boolean contains(Range other) {
            return other.a >= this.a && other.b <= this.b;
        }

        public boolean overlaps(Range other) {
            return this.in(other.a) || this.in(other.b) || other.in(this.a) || other.in(this.b);
        }

        public boolean in(int v) {
            return v >= a && v <= b;
        }

        @Override
        public String toString() {
            return a + "-" + b;
        }
    }
}