package com.example.adventofcode.year2024.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlutonianPebbles {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day11/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day11/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(countStonesAfterBlinkingBruteForce(EXAMPLE_FILENAME, 25));
        System.out.println(countStonesAfterBlinkingBruteForce(FILENAME, 25));
        System.out.println(countStonesAfterBlinking(EXAMPLE_FILENAME, 25));
        System.out.println(countStonesAfterBlinking(FILENAME, 25));
        System.out.println(countStonesAfterBlinking(EXAMPLE_FILENAME, 75));
        System.out.println(countStonesAfterBlinking(FILENAME, 75));
    }

    public static long countStonesAfterBlinkingBruteForce(final String filename, int times) throws IOException {
        String line = readLine(filename);
        String[] initialNumbers = line.split(" ");

        List<Long> numbers = new ArrayList<>();
        for (String initialNumber : initialNumbers) {
            numbers.add(Long.parseLong(initialNumber));
        }

        for (int i = 0; i < times; i++) {
            numbers = updateStonesByBlinking(numbers);
        }

        return numbers.size();
    }

    private record CacheEntry(Long number, int iteration) {

    }

    private static Map<CacheEntry, Long> stoneCache = new HashMap<>();

    public static long countStonesAfterBlinking(final String filename, int times) throws IOException {
        String line = readLine(filename);
        String[] initialNumbers = line.split(" ");
        stoneCache = new HashMap<>();

        List<Long> numbers = new ArrayList<>();
        for (String initialNumber : initialNumbers) {
            numbers.add(Long.parseLong(initialNumber));
        }

        long sum = 0;
        for (Long number : numbers) {
            sum += countBlinkingStonesSize(number, 0, times);
        }

        return sum;
    }

    private static Long countBlinkingStonesSizeWithCache(long number, int iteration, int maxIterations) {
        CacheEntry cacheEntry = new CacheEntry(number, iteration);
        if (stoneCache.containsKey(cacheEntry)) {
            return stoneCache.get(cacheEntry);
        }

        long count = countBlinkingStonesSize(number, iteration, maxIterations);
        stoneCache.put(cacheEntry, count);
        return count;
    }

    private static Long countBlinkingStonesSize(long number, int iteration, int maxIterations) {
        if (iteration == maxIterations) {
            return 1L;
        }

        String stringNumber = String.valueOf(number);
        if (number == 0) {
            return countBlinkingStonesSizeWithCache(1, iteration + 1, maxIterations);
        } else if (stringNumber.length() % 2 == 0) {
            return countBlinkingStonesSizeWithCache(Long.parseLong(stringNumber.substring(0, stringNumber.length() / 2)), iteration + 1, maxIterations) +
                    countBlinkingStonesSizeWithCache(Long.parseLong(stringNumber.substring(stringNumber.length() / 2)), iteration + 1, maxIterations);
        } else {
            return countBlinkingStonesSizeWithCache(2024 * number, iteration + 1, maxIterations);
        }
    }

    private static List<Long> updateStonesByBlinking(List<Long> numbers) {
        List<Long> newStones = new ArrayList<>();

        for (long number : numbers) {
            String stringNumber = String.valueOf(number);
            if (number == 0) {
                newStones.add(1L);
            } else if (stringNumber.length() % 2 == 0) {
                newStones.add(Long.parseLong(stringNumber.substring(0, stringNumber.length() / 2)));
                newStones.add(Long.parseLong(stringNumber.substring(stringNumber.length() / 2)));
            } else {
                newStones.add(2024 * number);
            }
        }

        return newStones;
    }

    private static String readLine(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            return br.readLine();
        }
    }
}
