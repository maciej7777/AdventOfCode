package com.example.adventofcode.year2025.day04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class PrintingDepartment {
    private static final String FILENAME = "AdventOfCodeData/2025/day04/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2025/day04/example_input";

    private static final Point[] DIRECTIONS = {new Point(0, 1), new Point(1, 0), new Point(-1, 0), new Point(0, -1),
            new Point(1, 1), new Point(1, -1), new Point(-1, 1), new Point(-1, -1)
    };

    public static void main(String[] args) throws IOException {
        System.out.println(countAccessibleRollsOfPaper(EXAMPLE_FILENAME));
        System.out.println(countAccessibleRollsOfPaper(FILENAME));
        System.out.println(countAccessibleRollsOfPaperRecursively(EXAMPLE_FILENAME));
        System.out.println(countAccessibleRollsOfPaperRecursively(FILENAME));
    }


    record Point(int x, int y) {
    }

    public static long countAccessibleRollsOfPaper(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> map = readMap(lines);

        int count = 0;
        for (int j = 0; j < map.size(); j++) {
            for (int i = 0; i < map.get(j).size(); i++) {
                if (map.get(j).get(i) == '@' && countNeighbourRolls(map, new Point(i, j)) < 4) {
                    count++;
                }
            }
        }


        return count;
    }

    public static long countAccessibleRollsOfPaperRecursively(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> map = readMap(lines);

        int count;
        int removedRolls = 0;

        do {
            count = 0;
            List<List<Character>> newMap = new ArrayList<>();
            for (int j = 0; j < map.size(); j++) {
                List<Character> newRow = new ArrayList<>();
                for (int i = 0; i < map.get(j).size(); i++) {
                    if (map.get(j).get(i) == '@' && countNeighbourRolls(map, new Point(i, j)) < 4) {
                        count++;
                        removedRolls++;
                        newRow.add('.');
                    } else {
                        newRow.add(map.get(j).get(i));
                    }
                }
                newMap.add(newRow);
            }
            map = newMap;
        } while (count != 0);

        return removedRolls;
    }

    private static List<List<Character>> readMap(List<String> lines) {
        List<List<Character>> map = new ArrayList<>();
        for (String line : lines) {
            List<Character> elements = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                elements.add(line.charAt(i));
            }
            map.add(elements);
        }
        return map;
    }

    private static int countNeighbourRolls(List<List<Character>> map, Point point) {
        int count = 0;
        for (Point direction : DIRECTIONS) {
            Point neighbour = new Point(point.x + direction.x, point.y + direction.y);
            if (isValid(neighbour, map) && map.get(neighbour.y).get(neighbour.x) == '@') {
                count++;
            }
        }

        return count;
    }

    private static boolean isValid(Point newPoint, List<List<Character>> elements) {
        if (newPoint.x < 0 || newPoint.y < 0) {
            return false;
        }
        if (newPoint.x >= elements.size()) {
            return false;
        }
        return newPoint.y < elements.get(newPoint.x).size();
    }
}
