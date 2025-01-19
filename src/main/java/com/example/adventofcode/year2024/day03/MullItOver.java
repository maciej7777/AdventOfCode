package com.example.adventofcode.year2024.day03;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class MullItOver {
    private static final String FILENAME = "AdventOfCodeData/2024/day03/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2024/day03/example_input";
    private static final String EXAMPLE_FILENAME2 = "AdventOfCodeData/2024/day03/example_input2";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateInstructionsResult(EXAMPLE_FILENAME));
        System.out.println(calculateInstructionsResult(FILENAME));
        System.out.println(calculateInstructionsResultWithIgnoringConditions(EXAMPLE_FILENAME2));
        System.out.println(calculateInstructionsResultWithIgnoringConditions(FILENAME));
    }

    public static long calculateInstructionsResult(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        final Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        int sum = 0;

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }

        return sum;
    }

    public static int calculateInstructionsResultWithIgnoringConditions(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        final Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)|(do|don't)\\(\\)");
        int sum = 0;
        boolean disabled = false;

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                if (matcher.group(0).startsWith("mul")) {
                    if (!disabled) {
                        sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
                    }
                } else {
                    disabled = matcher.group(0).startsWith("don't");
                }
            }
        }

        return sum;
    }
}