package com.example.adventofcode.year2023.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class HotSprings {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2023/day12/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2023/day12/example_input";


    public static void main(String[] args) throws IOException {
        System.out.println(obtainSumOfSpringArrangements(EXAMPLE_FILENAME));
        //21
        System.out.println(obtainSumOfSpringArrangements(FILENAME));
        //7251
        System.out.println(obtainSumOfFoldedSpringArrangements(EXAMPLE_FILENAME));
        //525152
        System.out.println(obtainSumOfFoldedSpringArrangements(FILENAME));
        //2128386729962
    }

    record SpringRecord(String conditions, List<Integer> damagedAreas) {}

    public static Long obtainSumOfSpringArrangements(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<SpringRecord> springRecords = buildSpringRecords(lines);

        Map<SpringRecord, Long> memory = new HashMap<>();
        long times = 0L;
        for (SpringRecord springRecord : springRecords) {
            times += calculateNumberOfPossibilities(springRecord.conditions, springRecord.damagedAreas, memory);
        }
        return times;
    }

    public static Long obtainSumOfFoldedSpringArrangements(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<SpringRecord> springRecords = buildSpringRecords(lines);
        Map<SpringRecord, Long> memory = new HashMap<>();

        long times = 0L;
        for (SpringRecord springRecord: springRecords) {
            String conditionsToCheck = springRecord.conditions
                    + "?" + springRecord.conditions
                    + "?" + springRecord.conditions
                    + "?" + springRecord.conditions
                    + "?" + springRecord.conditions;
            List<Integer> damagedArea = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                damagedArea.addAll(springRecord.damagedAreas);
            }
            times += calculateNumberOfPossibilities(conditionsToCheck, damagedArea, memory);
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

    private static long calculateNumberOfPossibilities(String conditions, List<Integer> damagedAreas, Map<SpringRecord, Long> memory) {
        if (!conditions.contains("#") && !conditions.contains("?") && damagedAreas.isEmpty()) {
            return 1;
        }

        if (conditions.isEmpty()) {
            return 0;
        }

        switch (conditions.charAt(0)) {
            case '.' -> {
                return calculateNumberOfPossibilities(conditions.substring(1), damagedAreas, memory);
            }
            case '#' -> {
                return handleSpring(conditions, damagedAreas, memory);
            }
            default -> {
                return calculateNumberOfPossibilities(conditions.substring(1), damagedAreas, memory)
                        + handleSpring(conditions, damagedAreas, memory);
            }
        }
    }

    private static long handleSpring(String conditions, List<Integer> damagedAreas, Map<SpringRecord, Long> memory) {
        SpringRecord current = new SpringRecord(conditions, damagedAreas);
        if (memory.containsKey(current)) {
            return memory.get(current);
        }

        if (damagedAreas.isEmpty()) {
            return 0L;
        }
        int expectedAreaLength = damagedAreas.getFirst();
        if (conditions.length() < expectedAreaLength) {
            return 0L;
        }
        for (int i = 0; i < expectedAreaLength; i++) {
            if (conditions.charAt(i) == '.') {
                return 0L;
            }
        }

        if (conditions.length() == expectedAreaLength) {
            if (damagedAreas.size() == 1) return 1L;
            return 0L;
        }

        if (conditions.charAt(expectedAreaLength) == '#') {
            return 0L;
        }


        List<Integer> newDamagedArea = new ArrayList<>(damagedAreas);
        newDamagedArea.removeFirst();

        long numberOfPossibilities = calculateNumberOfPossibilities(
                conditions.substring(expectedAreaLength + 1),
                newDamagedArea,
                memory);

        memory.put(current, numberOfPossibilities);
        return numberOfPossibilities;
    }
}
