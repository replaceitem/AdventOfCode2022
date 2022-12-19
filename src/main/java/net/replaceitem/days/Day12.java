package net.replaceitem.days;

import net.replaceitem.Direction;
import net.replaceitem.Util;
import net.replaceitem.Vec2;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day12 {

    public static TerrainMap map;

    public static void main(String[] args) throws IOException {
        List<String> lines = Util.loadInput("input12");
        Vec2 start = null, end = null;
        for (int i = 0; i < lines.size(); i++) {
            int indexS = lines.get(i).indexOf("S");
            int indexE = lines.get(i).indexOf("E");
            if(indexS != -1) start = new Vec2(indexS, i);
            if(indexE != -1) end = new Vec2(indexE, i);
        }
        map = new TerrainMap(lines);
        map.printMap();

        //checkPathsRec();
        List<Vec2> path = getPath(start, end);
        System.out.println(path.size()-1);
    }


    static class PathFindingElement {
        Vec2 pos;
        int depth;

        public PathFindingElement(Vec2 pos) {
            this(pos, 0);
        }

        public PathFindingElement(Vec2 pos, int depth) {
            this.pos = pos;
            this.depth = depth;
        }
    }

    private static List<Vec2> getPath(Vec2 start, Vec2 end) {
        List<PathFindingElement> queue = new ArrayList<>();
        HashMap<Vec2, PathFindingElement> lookup = new HashMap<>();
        PathFindingElement endElement = new PathFindingElement(end);
        queue.add(endElement);
        lookup.put(endElement.pos, endElement);

        for(int i = 0; i < queue.size(); i++) {
            PathFindingElement current = queue.get(i);
            List<PathFindingElement> movables = new ArrayList<>(map.backwardsMovablePositions(current.pos).map(vec2 -> new PathFindingElement(vec2, current.depth + 1)).toList());
            for (PathFindingElement pathFindingElement : queue) {
                movables.removeIf(pathFindingElement1 -> pathFindingElement1.pos.equals(pathFindingElement.pos));
            }
            boolean bridged = false;
            for (PathFindingElement movable : movables) {
                queue.add(movable);
                lookup.put(movable.pos,movable);
                if(movable.pos.equals(start)) {
                    bridged = true;
                }
            }
            if(bridged) break;
        }


        for (int i = 0; i < map.h; i++) {
            for (int j = 0; j < map.w; j++) {
                PathFindingElement pathFindingElement = lookup.get(new Vec2(j, i));
                String label = pathFindingElement == null ? "-" : String.valueOf(pathFindingElement.depth);
                System.out.print(label + "\t");
            }
            System.out.println();
        }

        List<Vec2> path = new ArrayList<>();
        Vec2 current = start;
        path.add(current);
        while(!current.equals(end)) {
            Optional<Vec2> highest = map.movablePositions(current).sorted(Comparator.comparingInt(value -> lookup.containsKey(value) ? lookup.get(value).depth : Integer.MAX_VALUE)).findFirst();
            Vec2 nextPos = highest.orElseThrow();
            path.add(nextPos);
            current = nextPos;
        }

        return path;
    }

    static class TerrainMap {
        private final char[][] heightmap;
        int w, h;

        public TerrainMap(List<String> lines) {
            List<String> terrainLines = new ArrayList<>(lines);
            terrainLines = terrainLines.stream().filter(Predicate.not(String::isBlank)).map(s -> s.replaceAll("S","a").replaceAll("E","z")).toList();
            h = terrainLines.size();
            w = terrainLines.get(0).length();
            heightmap = new char[h][];
            for (int i = 0; i < heightmap.length; i++) {
                heightmap[i] = terrainLines.get(i).toCharArray();
            }
        }

        public void printMap() {
            for (char[] chars : heightmap) {
                System.out.println(new String(chars));
            }
        }

        public boolean canMove(Vec2 from, Vec2 to) {
            return isValidPosition(from) && isValidPosition(to) && get(to)-get(from) <= 1;
        }

        public Stream<Vec2> movablePositions(Vec2 pos) {
            return Direction.DIRECTIONS.stream().map(pos::move).filter(to -> canMove(pos, to));
        }
        public Stream<Vec2> backwardsMovablePositions(Vec2 pos) {
            return Direction.DIRECTIONS.stream().map(pos::move).filter(to -> canMove(to, pos));
        }

        public boolean isValidPosition(int x, int y) {
            return x >= 0 && x < w && y >= 0 && y < h;
        }

        public boolean isValidPosition(Vec2 pos) {
            return isValidPosition(pos.x, pos.y);
        }

        public char get(int x, int y) {
            return heightmap[y][x];
        }
        public char get(Vec2 pos) {
            return this.get(pos.x, pos.y);
        }
    }
}
