package com.example.adventofcode.year2025.day05;

import org.matheclipse.core.reflection.system.In;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class Cafeteria {
    private static final String FILENAME = "AdventOfCodeData/2025/day05/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2025/day05/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(countFreshIngredientsAvailable(EXAMPLE_FILENAME));
        System.out.println(countFreshIngredientsAvailable(FILENAME));
        System.out.println(countFreshIngredients(EXAMPLE_FILENAME));
        System.out.println(countFreshIngredients(FILENAME));
    }


    record Range(long begin, long end) {
    }

    private record Input(List<Range> freshRanges, List<Long> ingredientsAvailable) {
    }

    public static long countFreshIngredientsAvailable(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = readInput(lines);

        int freshCount = 0;
        for (Long ingredient : input.ingredientsAvailable) {
            for (Range range : input.freshRanges) {
                if (ingredient >= range.begin && ingredient <= range.end) {
                    freshCount++;
                    break;
                }
            }
        }
        return freshCount;
    }

    public static long countFreshIngredients(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = readInput(lines);

        input.freshRanges.sort(Comparator.comparingLong(element -> element.begin));

        long current = 0;
        long freshCount = 0;
        for (Range range : input.freshRanges) {
            if (range.end > current) {
                freshCount += range.end - Math.max(current + 1, range.begin) + 1;
                current = range.end;
            }
        }

        return freshCount;
    }

    private static Input readInput(List<String> lines) {
        List<Range> freshRanges = new ArrayList<>();
        List<Long> ingredientsAvailable = new ArrayList<>();

        for (String line : lines) {
            if (line.contains("-")) {
                String[] rangeDefinition = line.split("-");
                long begin = Long.parseLong(rangeDefinition[0]);
                long end = Long.parseLong(rangeDefinition[1]);
                freshRanges.add(new Range(begin, end));
            } else if (!line.isEmpty()) {
                ingredientsAvailable.add(Long.parseLong(line));
            }
        }

        return new Input(freshRanges, ingredientsAvailable);
    }
}
