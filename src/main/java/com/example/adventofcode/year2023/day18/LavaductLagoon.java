package com.example.adventofcode.year2023.day18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.adventofcode.utils.FileUtils.readLines;

/**
 * To learn more about this solution take a look to the <a href="https://en.wikipedia.org/wiki/Shoelace_formula">Shoelace formula</a>.
 */
public class LavaductLagoon {
    private static final String FILENAME = "AdventOfCodeData/2023/day18/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day18/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateLavaHold(EXAMPLE_FILENAME));
        //62
        System.out.println(calculateLavaHold(FILENAME));
        //46334
        System.out.println(calculateCorrectedLavaHold(EXAMPLE_FILENAME));
        //952408144115
        System.out.println(calculateCorrectedLavaHold(FILENAME));
        //102000662718092
    }


    record Point(long x, long y) {
    }

    private enum Direction {
        L, R, U, D
    }

    private static final Map<Direction, Point> DIRECTION_MAPPING = Map.of(
            Direction.L, new Point(-1, 0),
            Direction.R, new Point(1, 0),
            Direction.D, new Point(0, -1),
            Direction.U, new Point(0, 1)
    );

    record Operation(Direction direction, int length, String colour) {}

    public static long calculateLavaHold(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Operation> operations = obtainOperations(lines);

        return calculateLavaHold(operations);
    }

    public static long calculateCorrectedLavaHold(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Operation> operations = obtainCorrectOperations(lines);

        return calculateLavaHold(operations);
    }

    private static List<Operation> obtainOperations(List<String> lines) {
        List<Operation> operations = new ArrayList<>();
        for (String line : lines) {
            String[] splitted = line.split(" ");
            operations.add(new Operation(Direction.valueOf(splitted[0]), Integer.parseInt(splitted[1]),
                    splitted[2].replace("(", "").replace(")", "")));
        }
        return operations;
    }

    private static List<Operation> obtainCorrectOperations(List<String> lines) {
        List<Operation> operations = new ArrayList<>();
        for (String line : lines) {
            String[] splitted = line.split(" ");
            String op = splitted[2].replace("(#", "").replace(")", "");
            Direction direction;
            switch (op.charAt(5)) {
                case '0' -> direction = Direction.R;
                case '1' -> direction = Direction.D;
                case '2' -> direction = Direction.L;
                default -> direction = Direction.U;
            }
            operations.add(new Operation(direction, Integer.parseInt(op.substring(0, 5), 16), op));
        }
        return operations;
    }

    private static long calculateLavaHold(List<Operation> operations) {
        long sumX = 0;
        long sumY = 0;
        long circuit = 0;
        Point current = new Point(0, 0);
        for (Operation operation : operations) {
            Point delta = DIRECTION_MAPPING.get(operation.direction);
            Point next = new Point(current.x + delta.x * operation.length, current.y + delta.y * operation.length);
            sumX += current.x * next.y;
            sumY += current.y * next.x;
            circuit += operation.length;
            current = next;
        }
        long area = Math.abs(sumX - sumY) / 2;
        return (area + circuit / 2 + 1);
    }
}
