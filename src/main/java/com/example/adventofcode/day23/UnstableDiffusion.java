package com.example.adventofcode.day23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class UnstableDiffusion {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day23/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day23/example_input";

    //110 - example
    //4025 - prod input
    public static void main(String[] args) throws IOException {
        Set<Point> input = readInput(FILENAME);
        Set<Point> exampleInput = readInput(EXAMPLE_FILENAME);
        Set<Point> input2 = readInput(FILENAME);
        Set<Point> exampleInput2 = readInput(EXAMPLE_FILENAME);

        System.out.println(calculatePositions2(input, true));
        System.out.println(calculatePositions2(input2, false));

        System.out.println(calculatePositions2(exampleInput, true));
        System.out.println(calculatePositions2(exampleInput2, false));

    }

    public static int calculateElfMoves(String filename, boolean part1) throws IOException {
        Set<Point> input = readInput(filename);
        return calculatePositions2(input, part1);
    }

    private record Point(int x, int y) {
    }

    public static Set<Point> readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            Set<Point> map = new HashSet<>();

            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split("");
                for (int i = 0; i < elements.length; i++) {
                    if (elements[i].equals("#")) {
                        map.add(new Point(i, lineNumber));
                    }
                }
                lineNumber++;
            }
            return map;
        }
    }

    private static List<List<Point>> getMovesList() {
        List<List<Point>> moves = new ArrayList<>();
        moves.add(List.of(new Point(0, -1), new Point(-1, -1), new Point(1, -1)));
        moves.add(List.of(new Point(0, 1), new Point(-1, 1), new Point(1, 1)));
        moves.add(List.of(new Point(-1, 0), new Point(-1, -1), new Point(-1, 1)));
        moves.add(List.of(new Point(1, 0), new Point(1, -1), new Point(1, 1)));

        return moves;
    }

    public static int calculatePositions2(Set<Point> elves, boolean part1) {

        List<List<Point>> moves = getMovesList();
        int elvesMoved = -1;
        int round = 0;
        while (elvesMoved != 0) {
            round++;
            Map<Point, Point> movePropositions = new HashMap<>();
            Set<Point> repeatedPoints = new HashSet<>();
            for (Point point : elves) {
                if (thereIsNoElfAround(elves, point)) {
                    //Do nothing
                } else if (containsPoint(elves, point, 0, moves)) {
                    if (movePropositions.containsKey(new Point(point.x + moves.get(0).get(0).x, point.y + moves.get(0).get(0).y))) {
                        repeatedPoints.add(new Point(point.x + moves.get(0).get(0).x, point.y + moves.get(0).get(0).y));
                    } else {
                        movePropositions.put(new Point(point.x + moves.get(0).get(0).x, point.y + moves.get(0).get(0).y), point);
                    }
                } else if (containsPoint(elves, point, 1, moves)) {
                    if (movePropositions.containsKey(new Point(point.x + moves.get(1).get(0).x, point.y + moves.get(1).get(0).y))) {
                        repeatedPoints.add(new Point(point.x + moves.get(1).get(0).x, point.y + moves.get(1).get(0).y));
                    } else {
                        movePropositions.put(new Point(point.x + moves.get(1).get(0).x, point.y + moves.get(1).get(0).y), point);
                    }
                } else if (containsPoint(elves, point, 2, moves)) {
                    if (movePropositions.containsKey(new Point(point.x + moves.get(2).get(0).x, point.y + moves.get(2).get(0).y))) {
                        repeatedPoints.add(new Point(point.x + moves.get(2).get(0).x, point.y + moves.get(2).get(0).y));
                    } else {
                        movePropositions.put(new Point(point.x + moves.get(2).get(0).x, point.y + moves.get(2).get(0).y), point);
                    }
                } else if (containsPoint(elves, point, 3, moves)) {
                    if (movePropositions.containsKey(new Point(point.x + moves.get(3).get(0).x, point.y + moves.get(3).get(0).y))) {
                        repeatedPoints.add(new Point(point.x + moves.get(3).get(0).x, point.y + moves.get(3).get(0).y));
                    } else {
                        movePropositions.put(new Point(point.x + moves.get(3).get(0).x, point.y + moves.get(3).get(0).y), point);
                    }
                }
            }

            for (Point repeatedPoint : repeatedPoints) {
                movePropositions.remove(repeatedPoint);
            }

            elvesMoved = movePropositions.size();

            for (Map.Entry<Point, Point> moveProposition : movePropositions.entrySet()) {
                elves.remove(moveProposition.getValue());
                elves.add(moveProposition.getKey());
            }

            List<Point> move = moves.get(0);
            moves.remove(0);
            moves.add(3, move);

            if (round == 10 && part1) {
                return calculateSum(elves);
            }
        }

        return round;
    }

    private static boolean thereIsNoElfAround(Set<Point> elves, Point point) {
        return !elves.contains(new Point(point.x, point.y - 1))
                && !elves.contains(new Point(point.x, point.y + 1))
                && !elves.contains(new Point(point.x - 1, point.y))
                && !elves.contains(new Point(point.x + 1, point.y))
                && !elves.contains(new Point(point.x + 1, point.y - 1))
                && !elves.contains(new Point(point.x + 1, point.y + 1))
                && !elves.contains(new Point(point.x - 1, point.y - 1))
                && !elves.contains(new Point(point.x - 1, point.y + 1));
    }

    private static int calculateSum(Set<Point> elves) {
        int minX = Integer.MAX_VALUE;
        int maxX = 0;
        int minY = Integer.MAX_VALUE;
        int maxY = 0;
        for (Point elve : elves) {
            if (elve.x < minX) {
                minX = elve.x;
            }
            if (elve.x > maxX) {
                maxX = elve.x;
            }
            if (elve.y < minY) {
                minY = elve.y;
            }
            if (elve.y > maxY) {
                maxY = elve.y;
            }
        }

        int sum = 0;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (!elves.contains(new Point(x, y))) {
                    sum++;
                }
            }
        }
        return sum;
    }

    private static boolean containsPoint(Set<Point> elves, Point point, int position, List<List<Point>> moves) {
        return !elves.contains(new Point(point.x + moves.get(position).get(0).x, point.y + moves.get(position).get(0).y))
                && !elves.contains(new Point(point.x + moves.get(position).get(1).x, point.y + moves.get(position).get(1).y))
                && !elves.contains(new Point(point.x + moves.get(position).get(2).x, point.y + moves.get(position).get(2).y));
    }
}
