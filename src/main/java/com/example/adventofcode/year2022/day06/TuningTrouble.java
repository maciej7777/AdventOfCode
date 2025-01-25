package com.example.adventofcode.year2022.day06;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class TuningTrouble {
    private static final String FILENAME = "AdventOfCodeData/2022/day06/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2022/day06/example_input";

    public static void main(String[] args) throws IOException {
        List<String> lines = readLines(FILENAME);

        for (String line: lines) {
            System.out.println(calculateMarkerPositionFor(line, 4));
            System.out.println(calculateMarkerPositionFor(line, 14));
        }
    }

    public static int calculateMarkerPositionFor(String line, int markerLength) {
        ArrayDeque<Character> letters = new ArrayDeque<>();
        for (int i = 0; i < line.length(); i++) {
            letters.push(line.charAt(i));

            if (i > markerLength - 1) {
                letters.removeLast();
            }

            if (i > markerLength - 2) {
                Set<Character> tmpSet = new HashSet<>(letters);
                if (tmpSet.size() == markerLength) {
                    return i + 1;
                }
            }

        }
        return 0;
    }
}
