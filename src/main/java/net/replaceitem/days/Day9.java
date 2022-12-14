package net.replaceitem.days;

import net.replaceitem.Direction;
import net.replaceitem.Util;
import net.replaceitem.Vec2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Util.loadInput("input9");

        RopeTracker ropeTracker2 = new RopeTracker(new Rope(2));
        RopeTracker ropeTracker10 = new RopeTracker(new Rope(10));

        ropeTracker2.doMoves(lines);
        ropeTracker10.doMoves(lines);

        System.out.println("2-Long rope: " + ropeTracker2.getVisitedLocationCount());
        System.out.println("10-Long rope: " + ropeTracker10.getVisitedLocationCount());
    }

    private static class RopeTracker {

        private final Rope rope;
        private final Set<Vec2> visitedLocations = new HashSet<>();

        public RopeTracker(Rope rope) {
            this.rope = rope;
            visitedLocations.add(this.rope.getTail().position);
        }


        public void doMoves(List<String> moves) {
            for (String line : moves) {
                String[] paras = line.split(" ");
                Direction direction = Direction.fromUDLE(paras[0]);
                int distance = Integer.parseInt(paras[1]);
                for (int i = 0; i < distance; i++) {
                    rope.move(direction.getOffset());
                    visitedLocations.add(rope.getTail().position);
                }
            }
        }

        public int getVisitedLocationCount() {
            return visitedLocations.size();
        }
    }

    private static class Rope {
        private final List<RopeElement> elements = new ArrayList<>();

        public Rope(int elementCount) {
            for (int i = 0; i < elementCount; i++) {
                elements.add(new RopeElement(0,0));
            }
        }

        public void move(Vec2 offset) {
            getHead().move(offset);
            for(int i = 1; i < elements.size(); i++) {
                RopeElement element = elements.get(i);
                RopeElement previous = elements.get(i-1);
                element.dragTowards(previous);
            }
        }

        public RopeElement getHead() {
            return elements.get(0);
        }

        public RopeElement getTail() {
            return elements.get(elements.size()-1);
        }
    }

    private static class RopeElement {

        public Vec2 position;

        public RopeElement(int x, int y) {
            this.position = new Vec2(x,y);
        }

        public void move(Vec2 offset) {
            this.position = this.position.add(offset);
        }

        public void dragTowards(RopeElement head) {
            Vec2 diff = head.position.sub(this.position);
            int dx = diff.x;
            int dy = diff.y;
            if(Math.abs(dx) > 1 || Math.abs(dy) > 1) {
                Vec2 move = new Vec2(Util.clamp(dx, -1, 1), Util.clamp(dy, -1, 1));
                this.position = this.position.add(move);
            }
        }
    }
}
