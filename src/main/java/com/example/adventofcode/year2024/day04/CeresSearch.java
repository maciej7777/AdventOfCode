package com.example.adventofcode.year2024.day04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class CeresSearch {
    private static final String FILENAME = "AdventOfCodeData/2024/day04/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2024/day04/example_input";
    private static final List<Point> DIRECTIONS = List.of(
            new Point(-1, 0),
            new Point(1, 0),
            new Point(0, 1),
            new Point(0, -1),
            new Point(-1, -1),
            new Point(1, 1),
            new Point(1, -1),
            new Point(-1, 1)
    );

    public static void main(String[] args) throws IOException {
        System.out.println(countXMASWords(EXAMPLE_FILENAME));
        System.out.println(countXMASWords(FILENAME));
        System.out.println(countXShapedMASWords(EXAMPLE_FILENAME));
        System.out.println(countXShapedMASWords(FILENAME));
    }

    record Point(int x, int y) {
    }

    public static long countXMASWords(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> elements = createElementsList(lines);

        int count = 0;
        for (int lineN = 0; lineN < elements.size(); lineN++) {
            for (int colN = 0; colN < elements.get(lineN).size(); colN++) {
                count += countXMASWordsForPosition(lineN, colN, elements);
            }
        }

        return count;
    }

    public static long countXShapedMASWords(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> elements = createElementsList(lines);

        int count = 0;
        for (int lineN = 0; lineN < elements.size(); lineN++) {
            for (int colN = 0; colN < elements.get(lineN).size(); colN++) {
                if (isXShapedMASWord(lineN, colN, elements)) {
                    count++;
                }
            }
        }

        return count;
    }

    private static List<List<Character>> createElementsList(List<String> lines) {
        List<List<Character>> elements = new ArrayList<>();
        for (String line : lines) {
            List<Character> l = new ArrayList<>();
            elements.add(l);
            for (Character c : line.toCharArray()) {
                l.add(c);
            }
        }
        return elements;
    }

    private static int countXMASWordsForPosition(int lineNumber, int columnNumber, List<List<Character>> elements) {
        if (elements.get(lineNumber).get(columnNumber) != 'X') {
            return 0;
        }
        int count = 0;

        for (Point d : DIRECTIONS) {
            if (wordContinues(elements, new Point(lineNumber + d.x, columnNumber + d.y), 'M')
                    && wordContinues(elements, new Point(lineNumber + 2 * d.x, columnNumber + 2 * d.y), 'A')
                    && wordContinues(elements, new Point(lineNumber + 3 * d.x, columnNumber + 3 * d.y), 'S')) {
                count++;
            }
        }

        return count;
    }

    private static boolean isXShapedMASWord(int lineNumber, int columnNumber, List<List<Character>> elements) {
        if (elements.get(lineNumber).get(columnNumber) != 'A') {
            return false;
        }

        return ((wordContinues(elements, new Point(lineNumber - 1, columnNumber - 1), 'M')
                && wordContinues(elements, new Point(lineNumber + 1, columnNumber + 1), 'S'))
                || (wordContinues(elements, new Point(lineNumber + 1, columnNumber + 1), 'M')
                && wordContinues(elements, new Point(lineNumber - 1, columnNumber - 1), 'S')))
                && ((wordContinues(elements, new Point(lineNumber - 1, columnNumber + 1), 'M')
                && wordContinues(elements, new Point(lineNumber + 1, columnNumber - 1), 'S'))
                || (wordContinues(elements, new Point(lineNumber + 1, columnNumber - 1), 'M')
                && wordContinues(elements, new Point(lineNumber - 1, columnNumber + 1), 'S')));
    }

    private static boolean wordContinues(List<List<Character>> elements, Point newPoint, char character) {
        return isValid(newPoint, elements) && elements.get(newPoint.x).get(newPoint.y) == character;
    }

    private static boolean isValid(Point newPoint, List<List<Character>> elements) {
        if (newPoint.x < 0 || newPoint.y < 0) {
            return false;
        }
        if (newPoint.x >= elements.size()) {
            return false;
        }
        if (newPoint.y >= elements.get(newPoint.x).size()) {
            return false;
        }

        return true;
    }
}
