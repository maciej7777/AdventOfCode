package com.example.adventofcode.year2024.day21;

import java.io.IOException;
import java.util.*;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class KeypadConundrum {
    private static final String FILENAME = "AdventOfCodeData/2024/day21/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2024/day21/example_input";

    public static final Map<Character, Point> NUMBER_PAD_POSITIONS = Map.ofEntries(
            Map.entry('7', new Point(0, 0)),
            Map.entry('8', new Point(1, 0)),
            Map.entry('9', new Point(2, 0)),
            Map.entry('4', new Point(0, 1)),
            Map.entry('5', new Point(1, 1)),
            Map.entry('6', new Point(2, 1)),
            Map.entry('1', new Point(0, 2)),
            Map.entry('2', new Point(1, 2)),
            Map.entry('3', new Point(2, 2)),
            Map.entry('0', new Point(1, 3)),
            Map.entry('A', new Point(2, 3))
    );

    public static final Map<Character, Point> DIRECTIONAL_KEYPAD_POSITIONS = Map.ofEntries(
            Map.entry('^', new Point(1, 0)),
            Map.entry('A', new Point(2, 0)),
            Map.entry('<', new Point(0, 1)),
            Map.entry('v', new Point(1, 1)),
            Map.entry('>', new Point(2, 1))
    );

    private static final Map<Character, Point> DIRECTIONS = Map.of(
            '<', new Point(-1, 0),
            '>', new Point(1, 0),
            '^', new Point(0, -1),
            'v', new Point(0, 1)
    );

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSumOfCodeComplexities(EXAMPLE_FILENAME, 2));
        System.out.println(calculateSumOfCodeComplexities(FILENAME, 2));
        System.out.println(calculateSumOfCodeComplexities(EXAMPLE_FILENAME, 25));
        System.out.println(calculateSumOfCodeComplexities(FILENAME, 25));
    }


    public record Point(int x, int y) {
    }

    record CachedMoves(String input, int numberOfKeypads, boolean isNumberKeypad, Point currentPosition) {
    }

    public static long calculateSumOfCodeComplexities(final String filename, final int numberOfKeypads) throws IOException {
        List<String> lines = readLines(filename);

        long sumOfComplexities = 0L;

        Map<CachedMoves, Long> movesCache = new HashMap<>();
        for (String line : lines) {
            long length = calculatePossibleInputsWithCache(line, numberOfKeypads, movesCache);
            int number = Integer.parseInt(line.replace("A", ""));
            sumOfComplexities += length * number;
        }

        return sumOfComplexities;
    }

    private static Long calculatePossibleInputsWithCache(String input, int numberOfKeypads, Map<CachedMoves, Long> movesCache) {
        return calculatePossibleInputsWithCache(input, numberOfKeypads, true, null, movesCache);
    }

    private static Long calculatePossibleInputsWithCache(String input, int numberOfKeypads, boolean isNumberKeypad, Point currentPosition, Map<CachedMoves, Long> movesCache) {
        CachedMoves cacheEntry = new CachedMoves(input, numberOfKeypads, isNumberKeypad, currentPosition);
        if (movesCache.containsKey(cacheEntry)) {
            return movesCache.get(cacheEntry);
        }

        long count = calculatePossibleInputs(input, numberOfKeypads, isNumberKeypad, currentPosition, movesCache);
        movesCache.put(cacheEntry, count);
        return count;
    }

    private static long calculatePossibleInputs(String input, int numberOfKeypads, boolean isNumberKeypad, Point currentPosition, Map<CachedMoves, Long> movesCache) {
        if (input.isEmpty()) {
            return 0;
        }

        if (currentPosition == null) {
            currentPosition = getPosition(isNumberKeypad, 'A');
        }

        Point nextPosition = getPosition(isNumberKeypad, input.charAt(0));
        int xDiff = nextPosition.x - currentPosition.x;
        int yDiff = nextPosition.y - currentPosition.y;
        String tmp = getNecessaryMoves(yDiff, xDiff);

        long length = -1;
        if (numberOfKeypads == 0) {
            length = tmp.length() + 1L;
        } else {
            Point denyPoint = getDenyPoint(isNumberKeypad);
            Set<String> permutations = generatePermutations(tmp);

            for (String permutation : permutations) {
                long permutationNestedLength = getPermutationNestedLength(numberOfKeypads, currentPosition, movesCache, permutation, denyPoint);
                if (permutationNestedLength != -1 && (length == -1 || permutationNestedLength < length)) {
                    length = permutationNestedLength;
                }
            }
        }
        return length + calculatePossibleInputsWithCache(input.substring(1), numberOfKeypads, isNumberKeypad, nextPosition, movesCache);
    }

    private static Point getPosition(boolean isNumberKeypad, char element) {
        if (isNumberKeypad) {
            return NUMBER_PAD_POSITIONS.get(element);
        }
        return DIRECTIONAL_KEYPAD_POSITIONS.get(element);
    }

    private static String getNecessaryMoves(int yDiff, int xDiff) {
        String tmp = "";
        for (int i = 0; i < Math.abs(yDiff); i++) {
            if (yDiff > 0) {
                tmp += "v";
            } else {
                tmp += "^";
            }
        }
        for (int i = 0; i < Math.abs(xDiff); i++) {
            if (xDiff > 0) {
                tmp += ">";
            } else {
                tmp += "<";
            }
        }
        return tmp;
    }

    private static Point getDenyPoint(boolean isNumberKeypad) {
        Point denyPoint;
        if (isNumberKeypad) {
            denyPoint = new Point(0, 3);
        } else {
            denyPoint = new Point(0, 0);
        }
        return denyPoint;
    }

    private static Set<String> generatePermutations(String input) {
        if (input.isEmpty()) {
            Set<String> baseResult = new HashSet<>();
            baseResult.add("");
            return baseResult;
        }

        char ch = input.charAt(0);
        String restStr = input.substring(1);

        Set<String> prevRes = generatePermutations(restStr);

        Set<String> result = new HashSet<>();
        for (String s : prevRes) {
            for (int i = 0; i <= s.length(); i++) {
                String f = s.substring(0, i) + ch + s.substring(i);

                result.add(f);
            }
        }

        return result;
    }

    private static long getPermutationNestedLength(int numberOfKeypads, Point currentPosition, Map<CachedMoves, Long> movesCache, String permutation, Point denyPoint) {
        Point currentPermutationPosition = currentPosition;
        for (Character move : permutation.toCharArray()) {
            Point nextPermutationPosition = new Point(currentPermutationPosition.x + DIRECTIONS.get(move).x, currentPermutationPosition.y + DIRECTIONS.get(move).y);
            if (nextPermutationPosition.equals(denyPoint)) {
                return -1;
            }
            currentPermutationPosition = nextPermutationPosition;
        }
        return calculatePossibleInputsWithCache(permutation + "A", numberOfKeypads - 1, false, null, movesCache);
    }
}
