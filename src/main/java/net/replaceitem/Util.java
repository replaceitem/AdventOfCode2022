package net.replaceitem;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Util {

    public static List<String> loadInput(InputStream stream) throws IOException {
        return new ArrayList<>(List.of(new String(stream.readAllBytes()).split(System.lineSeparator())));
    }

    public static @NotNull List<String> loadInput(String location) throws IOException {
        try (InputStream resourceAsStream = Util.class.getClassLoader().getResourceAsStream("inputs/" + location)) {
            if(resourceAsStream == null) throw new IOException();
            return loadInput(resourceAsStream);
        }
    }

    public static int clamp(int v, int a, int b) {
        return Math.max(a,(Math.min(v, b)));
    }

    public static long evalBinaryOperation(long leftVal, char operator, long rightVal) {
        return switch (operator) {
            case '+' -> leftVal + rightVal;
            case '-' -> leftVal - rightVal;
            case '*' -> leftVal * rightVal;
            case '/' -> leftVal / rightVal;
            default -> throw new RuntimeException("Invalid operator: " + operator);
        };
    }

    public static int evalBinaryOperation(int leftVal, char operator, int rightVal) {
        return (int) evalBinaryOperation(leftVal, operator, (long) rightVal);
    }

    private static class LCMElement {
        private final long original;

        private long value;

        public LCMElement(long val) {
            this.value = val;
            this.original = val;
        }

        public void increase() {
            this.value += this.original;
        }

        public long getValue() {
            return value;
        }
    }

    public static long leastCommonMultiple(Collection<Long> numbers) {
        List<LCMElement> elements = new ArrayList<>(numbers.stream().map(LCMElement::new).toList());
        while(true) {
            long min = elements.stream().map(LCMElement::getValue).reduce(Math::min).orElseThrow();
            long max = elements.stream().map(LCMElement::getValue).reduce(Math::max).orElseThrow();
            if(min == max) return min;
            for (LCMElement element : elements) {
                if(element.getValue() == min) {
                    element.increase();
                    break;
                }
            }
        }
    }
}
