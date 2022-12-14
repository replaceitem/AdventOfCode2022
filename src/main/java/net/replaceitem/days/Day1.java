package net.replaceitem.days;

import net.replaceitem.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day1 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Util.loadInput("input1");
        int max = 0;
        int accumulator = 0;
        for (String line : lines) {
            if(line.isBlank()) {
                max = Math.max(max, accumulator);
                accumulator = 0;
                continue;
            }
            int calories = Integer.parseInt(line);
            accumulator += calories;
        }

        System.out.println(max);


        // PART TWO



        ArrayList<Integer> calorieList = new ArrayList<>();

        accumulator = 0;
        for (String line : lines) {
            if(line.isBlank()) {
                calorieList.add(accumulator);
                accumulator = 0;
                continue;
            }
            int calories = Integer.parseInt(line);
            accumulator += calories;
        }

        calorieList.sort(Comparator.comparingInt(Integer::intValue));

        int size = calorieList.size();
        int total = calorieList.get(size-1)+calorieList.get(size-2)+calorieList.get(size-3);
        System.out.println(total);
    }
}