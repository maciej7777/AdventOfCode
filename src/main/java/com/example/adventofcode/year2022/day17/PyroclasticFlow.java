package com.example.adventofcode.year2022.day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PyroclasticFlow {
    private static final String FILENAME = "AdventOfCodeData/2022day17/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2022day17/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(obtainTowerHeight(EXAMPLE_FILENAME, 2022L));
        System.out.println(obtainTowerHeight(FILENAME, 2022L));

        System.out.println(obtainTowerHeight(EXAMPLE_FILENAME, 1000000000000L));
        System.out.println(obtainTowerHeight(FILENAME, 1000000000000L));
    }

    public static long obtainTowerHeight(final String fileName, final long iterations) throws IOException {
        List<String> input = readInput(fileName);
        return calculateRocksHeight(input.get(0), iterations);
    }

    private static List<String> readInput(final String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<String> map = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                map.add(line);
            }
            return map;
        }
    }

    private record Shape(int width, int height, int type, List<Point> elements) {
    }

    private record Point(int x, long y) {
    }

    private record CycleKey(int windNumber, int shapeNumber, long height0, long height1, long height2,
                           long height3, long height4, long height5, long height6) {
    }

    private record CycleParams(long iteration, long caveHeight) {
    }

    private static Shape[] shapes = {new Shape(4, 1, 1, List.of(
            new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0)
    )),
            new Shape(3, 3, 2, List.of(
                    new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)
            )),
            new Shape(3, 3, 3, List.of(
                    new Point(2, 2), new Point(2, 1), new Point(0, 0), new Point(1, 0), new Point(2, 0)
            )),
            new Shape(1, 4, 4, List.of(
                    new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3)
            )),
            new Shape(2, 2, 5, List.of(
                    new Point(0, 0), new Point(1, 0), new Point(0, 1), new Point(1, 1)
            ))
    };

    public static long calculateRocksHeight(final String wind, final long iterations) {
        Set<Point> rocks = new HashSet<>();
        long caveHeight = 0;
        int windNumber = 0;
        int shapeNumber = 0;
        Map<CycleKey, CycleParams> cyclePairs = new HashMap<>(); //states
        boolean cycleFound = false;

        long cycleDy = 0;
        long skippedCycles = 0;
        long[] absoluteColumnHeights = {0, 0, 0, 0, 0, 0, 0};

        long i = 0;
        while (i < iterations) {
            int x = 2;
            long y = caveHeight + 3;
            Shape shape = shapes[shapeNumber];

            while (true) {
                int dx;
                windNumber = windNumber % wind.length();
                if (wind.charAt(windNumber) == '<') {
                    dx = -1;
                } else {
                    dx = 1;
                }
                windNumber++;

                if (isPositionAvailable(rocks, x + dx, y, shape)
                        && x + dx >= 0
                        && x + dx + shape.width <= 7) {
                    x += dx;
                }

                if (isPositionAvailable(rocks, x, y - 1, shape)) {
                    y--;
                } else {
                    for (Point point : shape.elements) {
                        Point tmpPoint = new Point(point.x + x, point.y + y);
                        rocks.add(tmpPoint);
                        if (point.y + y + 1 > caveHeight) {
                            caveHeight = point.y + y + 1;
                        }
                        if (absoluteColumnHeights[point.x + x] < point.y + y + 1) {
                            absoluteColumnHeights[point.x + x] = point.y + y + 1;
                        }
                    }
                    break;
                }
            }

            if (!cycleFound) {
                long currentMin = calculateColumnHeightsMin(absoluteColumnHeights);
                long[] relativeColumnHeights = calculateRelativeColumnHeights(absoluteColumnHeights, currentMin);

                CycleKey currentCyclePair = new CycleKey(windNumber, shapeNumber, relativeColumnHeights[0],
                        relativeColumnHeights[1], relativeColumnHeights[2], relativeColumnHeights[3],
                        relativeColumnHeights[4], relativeColumnHeights[5], relativeColumnHeights[6]);

                CycleParams cycleParams = cyclePairs.get(currentCyclePair);

                if (cycleParams != null) {
                    cycleDy = caveHeight - cycleParams.caveHeight;
                    long cycleIteration = i - cycleParams.iteration;
                    skippedCycles = (iterations - i - 1)  / cycleIteration;
                    i += skippedCycles * cycleIteration;
                    cycleFound = true;

                } else {
                    cyclePairs.put(currentCyclePair, new CycleParams(i, caveHeight));
                }

            }

            shapeNumber = (shapeNumber + 1) % shapes.length;
            i++;
        }
        return caveHeight + skippedCycles*cycleDy;
    }

    private static long calculateColumnHeightsMin(final long[] absoluteColumnHeights) {
        long currentMin = Long.MAX_VALUE;
        for (long highestColumn : absoluteColumnHeights) {
            if (highestColumn < currentMin) {
                currentMin = highestColumn;
            }
        }
        return currentMin;
    }

    private static long[] calculateRelativeColumnHeights(final long[] absoluteColumnHeights, final long currentMin) {
        long[] relativeColumnHeights = {0, 0, 0, 0, 0, 0, 0};
        for (int z = 0; z < absoluteColumnHeights.length; z++) {
            relativeColumnHeights[z] = absoluteColumnHeights[z] - currentMin;
        }
        return relativeColumnHeights;
    }

    private static boolean isPositionAvailable(final Set<Point> rocks, final int x, final long y, final Shape shape) {
        for (Point shapePoint : shape.elements) {
            Point newShapePosition = new Point(shapePoint.x + x, shapePoint.y + y);

            if (rocks.contains(newShapePosition) || y == -1) {
                return false;
            }
        }
        return true;
    }
}

