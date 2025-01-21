package com.example.adventofcode.year2023.day22;

import java.io.IOException;
import java.util.*;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class SandSlabs {
    private static final String FILENAME = "AdventOfCodeData/2023/day22/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day22/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(countSafeToDisintegrateBricks(EXAMPLE_FILENAME));
        System.out.println(countSafeToDisintegrateBricks(FILENAME));
        System.out.println(countFallingBricks(EXAMPLE_FILENAME));
        System.out.println(countFallingBricks(FILENAME));
    }

    record Coordinate(int x, int y, int z) {
    }

    record CoordinatesPair(Coordinate coordinate1, Coordinate coordinate2) {
    }

    private static class BricksComparator implements Comparator<Set<Coordinate>> {
        @Override
        public int compare(Set<Coordinate> firstBrick, Set<Coordinate> secondBrick) {
            int firstZ = firstBrick.stream().map(brick -> brick.z).min(Integer::compare).orElse(0);
            int secondZ = secondBrick.stream().map(brick -> brick.z).min(Integer::compare).orElse(0);

            return Integer.compare(firstZ, secondZ);
        }
    }

    public static long countSafeToDisintegrateBricks(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<CoordinatesPair> cubeCoordinates = mapToCubeCoordinates(lines);
        List<Set<Coordinate>> bricks = describeBricks(cubeCoordinates);

        bricks.sort(new BricksComparator());
        List<Set<Coordinate>> settledBricks = moveBricks(bricks);

        int stable = 0;
        for (Set<Coordinate> brick : settledBricks) {
            List<Set<Coordinate>> bricksWithoutOne = new ArrayList<>(settledBricks);
            bricksWithoutOne.remove(brick);
            List<Set<Coordinate>> bricksWithoutOneMoved = moveBricks(bricksWithoutOne);

            if (countMovedBricks(bricksWithoutOne, bricksWithoutOneMoved) == 0) {
                stable++;
            }
        }
        return stable;
    }

    public static long countFallingBricks(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<CoordinatesPair> cubeCoordinates = mapToCubeCoordinates(lines);
        List<Set<Coordinate>> bricks = describeBricks(cubeCoordinates);

        bricks.sort(new BricksComparator());
        List<Set<Coordinate>> settledBricks = moveBricks(bricks);

        int fallingSum = 0;
        for (Set<Coordinate> brick : settledBricks) {
            List<Set<Coordinate>> bricksWithoutOne = new ArrayList<>(settledBricks);
            bricksWithoutOne.remove(brick);
            List<Set<Coordinate>> bricksWithoutOneMoved = moveBricks(bricksWithoutOne);

            fallingSum += countMovedBricks(bricksWithoutOne, bricksWithoutOneMoved);
        }
        return fallingSum;
    }

    private static List<CoordinatesPair> mapToCubeCoordinates(final List<String> lines) {
        List<CoordinatesPair> cubeCoordinates = new ArrayList<>();
        for (String line : lines) {
            String[] splitted = line.split("~");
            String[] coord1 = splitted[0].split(",");
            Coordinate coordinate1 = new Coordinate(Integer.parseInt(coord1[0]),
                    Integer.parseInt(coord1[1]),
                    Integer.parseInt(coord1[2]));
            String[] coord2 = splitted[1].split(",");
            Coordinate coordinate2 = new Coordinate(Integer.parseInt(coord2[0]),
                    Integer.parseInt(coord2[1]),
                    Integer.parseInt(coord2[2]));
            cubeCoordinates.add(new CoordinatesPair(coordinate1, coordinate2));
        }
        return cubeCoordinates;
    }

    private static List<Set<Coordinate>> describeBricks(final List<CoordinatesPair> cubeCoordinates) {
        List<Set<Coordinate>> bricks = new ArrayList<>();

        for (CoordinatesPair pair : cubeCoordinates) {
            Set<Coordinate> cubes = new HashSet<>();
            for (int x = pair.coordinate1.x; x <= pair.coordinate2.x; x++) {
                for (int y = pair.coordinate1.y; y <= pair.coordinate2.y; y++) {
                    for (int z = pair.coordinate1.z; z <= pair.coordinate2.z; z++) {
                        cubes.add(new Coordinate(x, y, z));
                    }
                }
            }
            bricks.add(cubes);
        }
        return bricks;
    }

    private static List<Set<Coordinate>> moveBricks(final List<Set<Coordinate>> bricks) {
        Set<Coordinate> settled = new HashSet<>();
        List<Set<Coordinate>> settledBricks = new ArrayList<>();

        for (Set<Coordinate> brick : bricks) {
            Set<Coordinate> current = new HashSet<>();
            Set<Coordinate> nextCoordinates = brick;
            while (shouldKeepMovingBrick(nextCoordinates, settled)) {
                current = nextCoordinates;
                nextCoordinates = new HashSet<>();

                for (Coordinate coordinate : current) {
                    nextCoordinates.add(new Coordinate(coordinate.x, coordinate.y, coordinate.z - 1));
                }
            }

            settled.addAll(current);
            settledBricks.add(current);
        }
        return settledBricks;
    }

    private static boolean shouldKeepMovingBrick(final Set<Coordinate> nextCoordinates, final Set<Coordinate> settled) {
        for (Coordinate coord : nextCoordinates) {
            if (coord.z == -1 || settled.contains(coord)) {
                return false;
            }
        }
        return true;
    }

    private static int countMovedBricks(final List<Set<Coordinate>> originalBricks,
                                        final List<Set<Coordinate>> bricksToCompare) {
        int movedBricks = 0;
        for (int i = 0; i < originalBricks.size(); i++) {
            if (!originalBricks.get(i).equals(bricksToCompare.get(i))) {
                movedBricks++;
            }
        }
        return movedBricks;
    }
}