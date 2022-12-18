package net.replaceitem.days;

import net.replaceitem.Util;

import java.io.IOException;
import java.util.*;

public class Day11_2 {
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
                    currentlyParsing.items.addAll(Arrays.stream(value.split(", ")).mapToLong(Long::parseLong).boxed().toList());
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

        long lcm = Util.leastCommonMultiple(Monkey.monkeys.values().stream().map(monkey -> monkey.test.operand).toList());
        System.out.println("LCM: " + lcm);

        for (int round = 0; round < 10_000; round++) {
            Monkey.monkeys.forEach((integer, monkey) -> {
                List<Long> items = monkey.items;
                for (int i = 0; i < items.size(); i++) {
                    long item = items.get(i);
                    items.set(i, monkey.operation.evaluate(item) % lcm);
                    monkey.inspectionCount++;
                    boolean test = monkey.test.test(items.get(i));
                    int throwTarget = test ? monkey.throwToTrue : monkey.throwToFalse;
                    Monkey.monkeys.get(throwTarget).items.add(items.remove(i));
                    i--;
                }
            });
        }

        List<Monkey> ranking = Monkey.monkeys.values().stream().sorted(Comparator.comparingLong(o -> -o.inspectionCount)).toList();
        System.out.println(ranking);
        long result = ranking.get(0).inspectionCount * ranking.get(1).inspectionCount;
        System.out.println(result);
    }

    public static int getIndentation(String string) {
        for(int i = 0; i < string.length(); i++) if(string.charAt(i) != ' ') return i/2;
        return string.length();
    }


    static class Monkey {


        static Map<Integer, Monkey> monkeys = new LinkedHashMap<>();

        int id;
        // go BIG or go home
        List<Long> items = new ArrayList<>();
        Operation operation;
        Test test;
        int throwToTrue;
        int throwToFalse;

        long inspectionCount = 0;


        public Monkey(int id) {
            this.id = id;
            monkeys.put(id, this);
        }
    }

    static class Test {
        // method, if more than divisible
        long operand;

        public Test(String str) {
            if(str.startsWith("divisible by ")) {
                operand = Long.parseLong(str.replace("divisible by ", ""));
            }
        }

        public boolean test(long num) {
            return num % operand == 0;
        }
    }

    static class Operation {
        long left, right;
        char operator;

        public Operation(String str) {
            String[] s = str.split(" ");
            if(s.length != 3) throw new RuntimeException("Invalid operation: " + str);
            left = s[0].equals("old") ? -1 : Long.parseLong(s[0]);
            right = s[2].equals("old") ? -1 : Long.parseLong(s[2]);
            operator = s[1].charAt(0);
        }

        public long evaluate(long old) {
            long leftVal = left == -1 ? old : left;
            long rightVal = right == -1 ? old : right;
            return Util.evalBinaryOperation(leftVal, operator, rightVal);
        }
    }
}
