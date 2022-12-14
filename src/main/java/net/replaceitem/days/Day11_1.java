package net.replaceitem.days;

import net.replaceitem.Util;

import java.io.IOException;
import java.util.*;

public class Day11_1 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Util.loadInput("input11");

        Monkey currentlyParsing = null;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int indentation = getIndentation(line);
            if(line.isBlank()) continue;
            if(indentation == 0) currentlyParsing = null;

            List<String> tokens = Arrays.stream(line.split(":")).map(String::trim).toList();
            String location = tokens.get(0);
            String value = tokens.size() > 1 ? tokens.get(1) : null;

            if(location.startsWith("Monkey")) {
                int monkeyId = Integer.parseInt(location.split(" ")[1]);
                currentlyParsing = new Monkey(monkeyId);
                continue;
            }

            switch (location) {
                case "Starting items" -> {
                    if (value == null || currentlyParsing == null) continue;
                    currentlyParsing.items.addAll(Arrays.stream(value.split(", ")).map(Integer::parseInt).toList());
                }
                case "Operation" -> {
                    if (value == null || currentlyParsing == null) continue;
                    String operation = value.replace("new = ", "");
                    currentlyParsing.operation = new Operation(operation);
                }
                case "Test" -> {
                    if (value == null || currentlyParsing == null) continue;
                    currentlyParsing.test = new Test(value);
                }
                case "If true" -> {
                    if (value == null || currentlyParsing == null) continue;
                    currentlyParsing.throwToTrue = Integer.parseInt(value.replace("throw to monkey ", ""));
                }
                case "If false" -> {
                    if (value == null || currentlyParsing == null) continue;
                    currentlyParsing.throwToFalse = Integer.parseInt(value.replace("throw to monkey ", ""));
                }
            }

        }

        for (int round = 0; round < 20; round++) {
            Monkey.monkeys.forEach((integer, monkey) -> {
                List<Integer> items = monkey.items;
                for (int i = 0; i < items.size(); i++) {
                    Integer item = items.get(i);
                    monkey.inspectionCount++;
                    items.set(i, monkey.operation.evaluate(item) / 3);
                    boolean test = monkey.test.test(items.get(i));
                    int throwTarget = test ? monkey.throwToTrue : monkey.throwToFalse;
                    Monkey.monkeys.get(throwTarget).items.add(items.remove(i));
                    i--;
                }
            });
        }

        List<Monkey> ranking = Monkey.monkeys.values().stream().sorted(Comparator.comparingInt(o -> -o.inspectionCount)).toList();
        int result = ranking.get(0).inspectionCount * ranking.get(1).inspectionCount;

        System.out.println(result);
    }

    public static int getIndentation(String string) {
        for(int i = 0; i < string.length(); i++) if(string.charAt(i) != ' ') return i/2;
        return string.length();
    }


    static class Monkey {


        static Map<Integer, Monkey> monkeys = new LinkedHashMap<>();

        int id;
        List<Integer> items = new ArrayList<>();
        Operation operation;
        Test test;
        int throwToTrue;
        int throwToFalse;

        int inspectionCount = 0;


        public Monkey(int id) {
            this.id = id;
            monkeys.put(id, this);
        }
    }

    static class Test {
        // method, if more than divisible
        int operand;

        public Test(String str) {
            if(str.startsWith("divisible by ")) {
                operand = Integer.parseInt(str.replace("divisible by ", ""));
            }
        }

        public boolean test(int num) {
            return num % operand == 0;
        }
    }

    static class Operation {
        int left, right;
        char operator;

        public Operation(String str) {
            String[] s = str.split(" ");
            if(s.length != 3) throw new RuntimeException("Invalid operation: " + str);
            left = s[0].equals("old") ? -1 : Integer.parseInt(s[0]);
            right = s[2].equals("old") ? -1 : Integer.parseInt(s[2]);
            operator = s[1].charAt(0);
        }

        public int evaluate(int old) {
            int leftVal = left == -1 ? old : left;
            int rightVal = right == -1 ? old : right;
            return switch (operator) {
                case '+' -> leftVal + rightVal;
                case '-' -> leftVal - rightVal;
                case '*' -> leftVal * rightVal;
                case '/' -> leftVal / rightVal;
                default -> throw new RuntimeException("Invalid operator: " + operator);
            };
        }
    }
}
