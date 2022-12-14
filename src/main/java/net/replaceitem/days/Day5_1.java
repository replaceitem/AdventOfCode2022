package net.replaceitem.days;

import net.replaceitem.Stack;
import net.replaceitem.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5_1 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Util.loadInput("input5");
        // find stack index line
        int indexLineNum = 0;
        for (int i = 0; i < lines.size(); i++) {
            if(lines.get(i).startsWith(" 1 ")) {
                indexLineNum = i;
                break;
            }
        }

        String indexLine = lines.get(indexLineNum);
        String[] indexes = indexLine.split(" ");
        int lastIndex = Integer.parseInt(indexes[indexes.length-1]);
        Stack[] stacks = new Stack[lastIndex];
        for (int i = 0; i < stacks.length; i++) {
            stacks[i] = new Stack();
        }


        // initialize stacks
        for(int i = indexLineNum-1; i >= 0; i--) {
            String line = lines.get(i);
            for(int j = 0; j < line.length(); j+=4) {
                String box = line.substring(j,j+3);
                if(box.isBlank()) continue;
                int stack = j/4;
                char item = box.charAt(1);
                stacks[stack].put(item);
            }
        }

        // move stuff around

        for(int i = indexLineNum+1; i < lines.size(); i++) {
            String line = lines.get(i);
            if(line.isBlank()) continue;
            String numString = line.replace("move ","").replace("from ","").replace("to ","");
            String[] nums = numString.split(" ");
            int amount = Integer.parseInt(nums[0]);
            int from = Integer.parseInt(nums[1]);
            int to = Integer.parseInt(nums[2]);
            for (int n = 0; n < amount; n++) {
                stacks[from-1].moveTo(stacks[to-1]);
            }
        }

        System.out.println(Arrays.stream(stacks).map(Stack::get).map(String::valueOf).collect(Collectors.joining()));
    }
}