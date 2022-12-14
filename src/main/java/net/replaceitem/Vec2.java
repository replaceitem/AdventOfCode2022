package net.replaceitem;

public class Vec2 {
    public final int x, y;

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 add(Vec2 other) {
        return add(other.x, other.y);
    }
    public Vec2 add(int dx, int dy) {
        return new Vec2(this.x+dx, this.y+dy);
    }

    public Vec2 sub(Vec2 other) {
        return sub(other.x, other.y);
    }
    public Vec2 sub(int dx, int dy) {
        return new Vec2(this.x-dx, this.y-dy);
    }

    public Vec2 mult(int multiplier) {
        return new Vec2(this.x*multiplier, this.y*multiplier);
    }

    @Override
    public int hashCode() {
        return this.x+this.y*31;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vec2 vec2 && this.x == vec2.x && this.y == vec2.y;
    }
}
