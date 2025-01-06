package com.example.adventofcode.year2024.day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LinenLayout {
    private static final String FILENAME = "AdventOfCodeData/2024/day19/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2024/day19/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculatePossibleDesigns(EXAMPLE_FILENAME));
        System.out.println(calculatePossibleDesigns(FILENAME));
        System.out.println(calculatePossibleDesignsOptions(EXAMPLE_FILENAME));
        System.out.println(calculatePossibleDesignsOptions(FILENAME));
    }

    public static long calculatePossbleDesigns(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        String line0 = lines.getFirst();
        List<String> availableTowels = new ArrayList<>(Arrays.asList(line0.split(", ")));

        int possiblePatterns = 0;
        for (int i = 2; i < lines.size(); i++) {
            if (isPossibleDesign(lines.get(i), availableTowels)) {
                possiblePatterns++;
            }
        }

        return possiblePatterns;
    }

    public static long calculatePossibleDesignsOptions(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        String line0 = lines.getFirst();
        List<String> availableTowels = new ArrayList<>(Arrays.asList(line0.split(", ")));

        long possiblePatterns = 0;

        for (int i = 2; i < lines.size(); i++) {
            possiblePatterns += countPossibleDesignsWithCache(lines.get(i), availableTowels, new HashMap<>());
        }

        return possiblePatterns;
    }

    private static boolean isPossibleDesign(String pattern, List<String> availablePatterns) {
        if (pattern.isEmpty()) {
            return true;
        }

        for (String prefix : availablePatterns) {
            if (pattern.startsWith(prefix) && isPossibleDesign(pattern.substring(prefix.length()), availablePatterns)) {
                    return true;

            }
        }
        return false;
    }

    private static long countPossibleDesignsWithCache(String pattern, List<String> availablePatterns, Map<String, Long> designCache) {
        if (designCache.containsKey(pattern)) {
            return designCache.get(pattern);
        }

        long patternCount = countPossibleDesigns(pattern, availablePatterns, designCache);
        designCache.put(pattern, patternCount);
        return patternCount;
    }

    private static long countPossibleDesigns(String pattern, List<String> availablePatterns, Map<String, Long> designCache) {
        if (pattern.isEmpty()) {
            return 1;
        }

        long possibleDesigns = 0;
        for (String prefix : availablePatterns) {
            if (pattern.startsWith(prefix)) {
                possibleDesigns += countPossibleDesignsWithCache(pattern.substring(prefix.length()), availablePatterns, designCache);
            }
        }
        return possibleDesigns;
    }

    private static List<String> readLines(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
