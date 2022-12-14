package net.replaceitem.days;

import net.replaceitem.Util;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day10 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Util.loadInput("input10");

        List<Instruction> instructions = lines.stream().filter(s -> !s.isBlank()).map(Instruction::parse).toList();

        AtomicInteger signalStrengthSum = new AtomicInteger(0);

        InstructionExecutor executor = new InstructionExecutor() {
            @Override
            protected void onClockTick() {
                if((clock+20) % 40==0) {
                    int signalStrength = clock*register;
                    signalStrengthSum.addAndGet(signalStrength);
                }
            }
        };

        for (Instruction instruction : instructions) {
            instruction.execute(executor);
        }

        System.out.println(signalStrengthSum.get());
    }

    private static class InstructionExecutor {
        protected int clock = 0;
        public int register = 1;

        public void tickClock(int times) {
            for (int i = 0; i < times; i++) {
                tickClock();
            }
        }

        public void tickClock() {
            clock++;
            onClockTick();
        }

        protected void onClockTick() {

        }
    }

    private static abstract class Instruction {

        public abstract void execute(InstructionExecutor executor);

        public static Instruction parse(String str) {
            String[] args = str.split(" ");
            String name = args[0];
            return switch (name) {
                case "noop" -> new NoopInstruction();
                case "addx" -> new AddxInstruction(args);
                default -> throw new IllegalArgumentException("Invalid instruction: " + name);
            };
        }
    }

    private static class NoopInstruction extends Instruction {

        public NoopInstruction() {

        }

        @Override
        public void execute(InstructionExecutor executor) {
            executor.tickClock(1);
        }
    }

    private static class AddxInstruction extends Instruction {

        private final int addVal;

        public AddxInstruction(String[] args) {
            addVal = Integer.parseInt(args[1]);
        }

        @Override
        public void execute(InstructionExecutor executor) {
            executor.tickClock(2);
            executor.register += addVal;
        }
    }
}
