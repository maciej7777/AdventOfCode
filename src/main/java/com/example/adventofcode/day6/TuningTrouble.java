package com.example.adventofcode.day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TuningTrouble {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day6/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day6/example_input";

    public static void main(String[] args) throws IOException {
        List<String> lines = readLines(FILENAME);

        for (String line: lines) {
            System.out.println(calculateMarkerPositionFor(line, 4));
            System.out.println(calculateMarkerPositionFor(line, 14));
        }
    }

    public static List<String> readLines(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
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
