package com.example.adventofcode.year2022.day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class RucksackReorganization {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day03/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day03/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSumOfPriorities(EXAMPLE_FILENAME));
        System.out.println(calculateSumOfPriorities(FILENAME));

        System.out.println(calculateSumOfPrioritiesForBatches(EXAMPLE_FILENAME));
        System.out.println(calculateSumOfPrioritiesForBatches(FILENAME));
    }

    public static int calculateSumOfPriorities(final String fileName) throws IOException {
        List<String> inputLines = readInput(fileName);
        return calculateSumOfPriorities(inputLines);
    }

    public static int calculateSumOfPrioritiesForBatches(final String fileName) throws IOException {
        List<String> inputLines = readInput(fileName);
        return calculateSumOfPrioritiesForBatches(inputLines);
    }

    private static List<String> readInput(final String fileName) throws IOException {
        List<String> inputLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                inputLines.add(line);
            }
        }
        return inputLines;
    }

    private static int calculateSumOfPriorities(final List<String> inputLines) {
        int sumOfElements = 0;
        for (String line : inputLines) {
            Set<Character> compartmentOneElements = line.substring(0, line.length() / 2).chars()
                    .mapToObj(e -> (char) e)
                    .collect(Collectors.toSet());

            sumOfElements += evaluateNumber(line, compartmentOneElements);
        }

        return sumOfElements;
    }

    private static int evaluateNumber(final String line, final Set<Character> compartmentOneElements) {
        for (int i = line.length() / 2; i < line.length(); i++) {
            if (compartmentOneElements.contains(line.charAt(i))) {
               return evaluatePriority(line.charAt(i));
            }
        }
        return 0;
    }

    private static int calculateSumOfPrioritiesForBatches(final List<String> inputLines) {
        int sumOfElements = 0;

        for (int i = 0; i < inputLines.size(); i+=3) {
            String[] elfGroup = {inputLines.get(i), inputLines.get(i+1), inputLines.get(i+2)};
                    sumOfElements += evaluatePriority(findCommonElement(elfGroup));
            }

        return sumOfElements;
    }

    private static char findCommonElement(String[] lines) {
        Set<Character> elementsOfFirstBackpack = lines[0].chars()
                .mapToObj(e -> (char)e)
                .collect(Collectors.toSet());

        Set<Character> commonElementsOfBackpacksOneAndTwo = new HashSet<>();
        for (int i = 0; i < lines[1].length(); i++) {
            if (elementsOfFirstBackpack.contains(lines[1].charAt(i))) {
                commonElementsOfBackpacksOneAndTwo.add(lines[1].charAt(i));
            }
        }

        for (int i = 0; i < lines[2].length(); i++) {
            if (commonElementsOfBackpacksOneAndTwo.contains(lines[2].charAt(i))) {
                return lines[2].charAt(i);
            }
        }
        return 0;
    }

    private static int evaluatePriority(final char element) {
        if (Character.isUpperCase(element)) {
            return element - ('A' - 27);
        }
        return element - ('a' - 1);
    }
}
