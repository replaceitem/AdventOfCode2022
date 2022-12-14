package net.replaceitem;

import java.util.ArrayList;
import java.util.List;

public class Stack {
    protected List<Character> elements = new ArrayList<>();


    public void put(char c) {
        elements.add(c);
    }

    public char get() {
        return elements.get(elements.size()-1);
    }

    public char pop() {
        return elements.remove(elements.size()-1);
    }

    public void moveTo(Stack other) {
        other.put(this.pop());
    }

    public void moveTo(Stack other, int amount) {
        char[] lift = new char[amount];
        for (int i = 0; i < amount; i++) {
            lift[i] = this.pop();
        }
        for (int i = amount - 1; i >= 0; i--) {
            other.put(lift[i]);
        }
    }
}
