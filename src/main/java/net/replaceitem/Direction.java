package net.replaceitem;

import java.util.ArrayList;
import java.util.List;

public class Direction {
    private final Vec2 offset;
    private final int ordinal;

    public static final List<Direction> DIRECTIONS = new ArrayList<>(4);

    public static final Direction NORTH = new Direction(new Vec2(0, -1),  0);
    public static final Direction EAST = new Direction(new Vec2(1, 0),  1);
    public static final Direction SOUTH = new Direction(new Vec2(0, 1),  2);
    public static final Direction WEST = new Direction(new Vec2(-1, 0),  3);

    public static final Direction UP = NORTH;
    public static final Direction RIGHT = EAST;
    public static final Direction DOWN = SOUTH;
    public static final Direction LEFT = WEST;

    public Direction(Vec2 offset, int ordinal) {
        this.offset = offset;
        this.ordinal = ordinal;
        DIRECTIONS.add(this);
    }

    public Vec2 getOffset() {
        return offset;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public static Direction fromNWSE(String dir) {
        return fromNWSE(dir.charAt(0));
    }

    public static Direction fromNWSE(char dir) {
        dir = Character.toUpperCase(dir);
        return switch (dir) {
            case 'N' -> NORTH;
            case 'E' -> EAST;
            case 'S' -> SOUTH;
            case 'W' -> WEST;
            default -> throw new IllegalStateException("Invalid NWSE direction: " + dir);
        };
    }

    public static Direction fromUDLE(String dir) {
        return fromUDLE(dir.charAt(0));
    }

    public static Direction fromUDLE(char dir) {
        dir = Character.toUpperCase(dir);
        return switch (dir) {
            case 'U' -> UP;
            case 'D' -> DOWN;
            case 'L' -> LEFT;
            case 'R' -> RIGHT;
            default -> throw new IllegalStateException("Invalid NWSE direction: " + dir);
        };
    }
}
