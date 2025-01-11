package com.example.adventofcode.year2022.day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RopeBridge {
    private static final String FILENAME = "AdventOfCodeData/2022day09/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2022day09/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculatePositionsTouchedByRopeTail(EXAMPLE_FILENAME, 2));
        System.out.println(calculatePositionsTouchedByRopeTail(FILENAME, 2));

        System.out.println(calculatePositionsTouchedByRopeTail(EXAMPLE_FILENAME, 10));
        System.out.println(calculatePositionsTouchedByRopeTail(FILENAME, 10));
    }

    public static int calculatePositionsTouchedByRopeTail(String fileName, int ropeLength) throws IOException {
        List<String> input = readInput(fileName);
        return calculatePositionsTouchedByRopeTail(input, ropeLength);
    }

    private record Point(int x, int y) {
    }

    private static final Map<String, Point> offset = Map.of(
            "L", new Point(-1, 0),
            "R", new Point(1, 0),
            "U", new Point(0, 1),
            "D", new Point(0, -1)
    );

    private static List<String> readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<String> map = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] elements = line.split(" ");
                for (int i = 0; i < Integer.parseInt(elements[1]); i++) {
                    map.add(elements[0]);
                }
            }
            return map;
        }
    }

    private static int calculatePositionsTouchedByRopeTail(List<String> moves, int ropeLength) {
        List<Point> ropePositions = new ArrayList<>();
        for (int i = 0; i < ropeLength; i++) {
            ropePositions.add(new Point(0, 0));
        }

        Set<Point> positions = new HashSet<>();

        for (String move : moves) {
            Point movement = offset.get(move);

            Point hPoint = ropePositions.get(0);
            ropePositions.set(0, new Point(hPoint.x + movement.x, hPoint.y + movement.y));

            for (int i = 1; i < ropePositions.size(); i++) {
                Point previous = ropePositions.get(i - 1);
                Point current = ropePositions.get(i);

                int currentX = current.x;
                int currentY = current.y;

                if (Math.max(Math.abs(currentX - previous.x), Math.abs(currentY - previous.y)) > 1) {
                    if (Math.abs(currentX - previous.x) > 0) {
                        if (previous.x > currentX) {
                            currentX += 1;
                        } else {
                            currentX -= 1;
                        }
                    }
                    if (Math.abs(currentY - previous.y) > 0) {
                        if (previous.y > currentY) {
                            currentY += 1;
                        } else {
                            currentY -= 1;
                        }
                    }
                }
                ropePositions.set(i, new Point(currentX, currentY));
            }
            positions.add(ropePositions.get(ropePositions.size() - 1));
        }

        return positions.size();
    }
}
