package com.example.adventofcode.year2024.day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PrintQueue {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day05/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day05/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateMiddlePageSumForCorrectlyOrdered(EXAMPLE_FILENAME));
        System.out.println(calculateMiddlePageSumForCorrectlyOrdered(FILENAME));
        System.out.println(calculateMiddlePageSumForIncorrectlyOrdered(EXAMPLE_FILENAME));
        System.out.println(calculateMiddlePageSumForIncorrectlyOrdered(FILENAME));
    }

    record Entry(String number, int beforeElements) {
    }

    public static long calculateMiddlePageSumForCorrectlyOrdered(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Map<String, List<String>> beforeList = new HashMap<>(); // all the elements that need to be before x
        int middleNumbers = 0;
        for (String line : lines) {
            if (line.contains("|")) {
                addNewRule(line, beforeList);
            } else if (!line.isEmpty()) {
                String[] numbers = line.split(",");
                if (isLineCorrect(numbers, beforeList)) {
                    middleNumbers += Integer.parseInt(numbers[numbers.length / 2]);
                }
            }
        }

        return middleNumbers;
    }

    public static long calculateMiddlePageSumForIncorrectlyOrdered(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Map<String, List<String>> beforeList = new HashMap<>();
        int middleNumbers = 0;
        for (String line : lines) {
            if (line.contains("|")) {
                addNewRule(line, beforeList);
            } else if (!line.isEmpty()) {
                String[] numbers = line.split(",");
                if (!isLineCorrect(numbers, beforeList)) {
                    List<Entry> entries = getOrderedNumbers(numbers, beforeList);
                    middleNumbers += Integer.parseInt(entries.get(entries.size() / 2).number);
                }
            }
        }


        return middleNumbers;
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

    private static void addNewRule(String line, Map<String, List<String>> beforeList) {
        String[] numbers = line.split("\\|");
        List<String> beforeElements = beforeList.getOrDefault(numbers[1], new ArrayList<>());
        beforeElements.add(numbers[0]);
        beforeList.put(numbers[1], beforeElements);
    }

    private static boolean isLineCorrect(String[] numbers, Map<String, List<String>> beforeList) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (beforeList.getOrDefault(numbers[i], new ArrayList<>()).contains(numbers[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<Entry> getOrderedNumbers(String[] numbers, Map<String, List<String>> beforeList) {
        List<Entry> entries = new ArrayList<>();
        for (String number : numbers) {
            int beforeElements = 0;
            for (String otherNumber : numbers) {
                if (beforeList.getOrDefault(number, new ArrayList<>()).contains(otherNumber)) {
                    beforeElements++;
                }
            }
            entries.add(new Entry(number, beforeElements));
        }

        entries.sort(Comparator.comparingInt(ent -> ent.beforeElements));
        return entries;
    }
}
