package com.example.adventofcode.year2023.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class HotSpringsBruteForce {
    private static final String FILENAME = "AdventOfCodeData/2023/day12/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day12/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(obtainSumOfSpringArrangements(EXAMPLE_FILENAME));
        //21
        System.out.println(obtainSumOfSpringArrangements(FILENAME));
        //7251
    }

    record SpringRecord(String conditions, List<Integer> damagedAreas) {}

    public static long obtainSumOfSpringArrangements(final String filename) throws IOException {
        List<String> inputLines = readLines(filename);
        List<SpringRecord> springRecords = buildSpringRecords(inputLines);

        long times = 0L;
        for (SpringRecord springRecord : springRecords) {
            times += calculateNumberOfPossibilities(springRecord.conditions, springRecord.damagedAreas);
        }
        return times;
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

    private static List<SpringRecord> buildSpringRecords(List<String> lines) {
        List<SpringRecord> springRecords = new ArrayList<>();
        for (String line : lines) {
            String[] splitted = line.split(" ");
            List<Integer> lineDamages = Arrays.stream(splitted[1].split(",")).map(Integer::parseInt).toList();

            springRecords.add(new SpringRecord(splitted[0], lineDamages));
        }
        return springRecords;
    }

    private static long calculateNumberOfPossibilities(String conditions, List<Integer> damagedAreas) {
        long expectedDamages = damagedAreas.stream().mapToInt(Integer::intValue).sum();

        Queue<String> conditionsToCheck = new ArrayDeque<>();
        conditionsToCheck.add(conditions);

        long correctPermutations = 0L;
        while (!conditionsToCheck.isEmpty()) {
            String current = conditionsToCheck.poll();
            if (current.chars().filter(ch -> ch == '#').count() > expectedDamages) {
                continue;
            }

            if (current.contains("?")) {
                int indexToChange = current.indexOf('?');
                String version1 = current.substring(0, indexToChange) + "."
                        + current.substring(indexToChange + 1);
                String version2 = current.substring(0, indexToChange) + "#"
                        + current.substring(indexToChange + 1);
                conditionsToCheck.add(version1);
                conditionsToCheck.add(version2);
            } else {
                if (isPermutationCorrect(current, damagedAreas)) {
                    correctPermutations++;
                }
            }
        }

        return correctPermutations;
    }

    private static boolean isPermutationCorrect(String conditions, List<Integer> damagedAreas) {
        int currentDamageLength = 0;
        List<Integer> damageLengths = new ArrayList<>();
        for (int i = 0; i < conditions.length(); i++) {
            if (conditions.charAt(i) == '.') {
                if (currentDamageLength > 0) {
                    damageLengths.add(currentDamageLength);
                }
                currentDamageLength = 0;
            } else {
                currentDamageLength++;
            }
        }
        if (currentDamageLength > 0) {
            damageLengths.add(currentDamageLength);
        }

        if (damageLengths.size() != damagedAreas.size()) {
            return false;
        }

        for (int i = 0; i < damageLengths.size(); i++) {
            if (!damageLengths.get(i).equals(damagedAreas.get(i))) {
                return false;
            }
        }
        return true;
    }
}
