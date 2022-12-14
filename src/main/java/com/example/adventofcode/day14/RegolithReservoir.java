package com.example.adventofcode.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RegolithReservoir {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day14/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day14/example_input";

    public static void main(String[] args) throws IOException {
        Set<Point> input = readInput(FILENAME);
        Set<Point> exampleInput = readInput(FILENAME);

        System.out.println(calculateNumberOfUnitsOfSandToBlockSource(input));
        System.out.println(calculateNumberOfUnitsOfSandToBlockSource(exampleInput));
    }

    public static int obtainNumberOfUnitsOfSandToStartFlowing(final String FILENAME) throws IOException {
        Set<Point> input = readInput(FILENAME);
        return calculateNumberOfUnitsOfSandToStartFlowing(input);
    }

    public static int obtainNumberOfUnitsOfSandToBlockSource(final String FILENAME) throws IOException {
        Set<Point> input = readInput(FILENAME);
        return calculateNumberOfUnitsOfSandToBlockSource(input);
    }

    record Point(int x, int y) {
    }

    public static Set<Point> readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            Set<Point> map = new HashSet<>();

            while ((line = br.readLine()) != null) {
                String[] coordinates = line.split(" -> ");
                for (int i = 0; i < coordinates.length - 1; i++) {
                    String[] pointFrom = coordinates[i].split(",");
                    String[] pointTo = coordinates[i + 1].split(",");

                    Point from = new Point(Integer.parseInt(pointFrom[0]), Integer.parseInt(pointFrom[1]));
                    Point to = new Point(Integer.parseInt(pointTo[0]), Integer.parseInt(pointTo[1]));

                    if (from.x == to.x) {
                        for (int j = Math.min(from.y, to.y); j <= Math.max(from.y, to.y); j++) {
                            map.add(new Point(from.x, j));
                        }
                    } else {
                        for (int j = Math.min(from.x, to.x); j <= Math.max(from.x, to.x); j++) {
                            map.add(new Point(j, from.y));
                        }
                    }
                }
            }
            return map;
        }
    }

    public static int calculateNumberOfUnitsOfSandToBlockSource(Set<Point> rocks) {
        Set<Point> sand = new HashSet<>();

        int floor = 0;

        for (Point rock : rocks) {
            if (rock.y > floor) {
                floor = rock.y;
            }
        }

        while (true) {
            int sandX = 500;
            int sandY = 0;

            if (sand.contains(new Point(sandX, sandY))) {
                break;
            }

            while (true) {
                if (sandY > floor) {
                    break;
                } else if (spaceIsAvailable(new Point(sandX, sandY + 1), sand, rocks)) {
                    sandY++;
                } else if (spaceIsAvailable(new Point(sandX - 1, sandY + 1), sand, rocks)) {
                    sandY++;
                    sandX--;
                } else if (spaceIsAvailable(new Point(sandX + 1, sandY + 1), sand, rocks)) {
                    sandY++;
                    sandX++;
                } else {
                    break;
                }
            }
            sand.add(new Point(sandX, sandY));
        }
        return sand.size();
    }

    public static int calculateNumberOfUnitsOfSandToStartFlowing(Set<Point> rocks) {
        Set<Point> sand = new HashSet<>();

        int floor = 0;

        for (Point rock : rocks) {
            if (rock.y > floor) {
                floor = rock.y;
            }
        }

        while (true) {
            int sandX = 500;
            int sandY = 0;

            while (true) {
                if (sandY + 1 > floor) {
                    return sand.size();
                } else if (spaceIsAvailable(new Point(sandX, sandY + 1), sand, rocks)) {
                    sandY++;
                } else if (spaceIsAvailable(new Point(sandX - 1, sandY + 1), sand, rocks)) {
                    sandY++;
                    sandX--;
                } else if (spaceIsAvailable(new Point(sandX + 1, sandY + 1), sand, rocks)) {
                    sandY++;
                    sandX++;
                } else {
                    break;
                }
            }
            sand.add(new Point(sandX, sandY));
        }
    }

    private static boolean spaceIsAvailable(Point point, Set<Point> sand, Set<Point> rocks) {
        return !sand.contains(point)
                && !rocks.contains(point);
    }
}
