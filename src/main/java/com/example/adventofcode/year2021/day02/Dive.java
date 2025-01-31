package com.example.adventofcode.year2021.day02;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class Dive {
    private static final String FILENAME = "AdventOfCodeData/2021/day02/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2021/day02/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculatePositionProduct(EXAMPLE_FILENAME));
        System.out.println(calculatePositionProduct(FILENAME));
        System.out.println(calculatePositionProductWithAim(EXAMPLE_FILENAME));
        System.out.println(calculatePositionProductWithAim(FILENAME));
    }

    enum Direction {
        FORWARD,
        UP,
        DOWN
    }

    record Instruction(Direction direction, int units) {
    }

    public static long calculatePositionProduct(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Instruction> instructions = parseInstructions(lines);

        int forward = 0;
        int depth = 0;

        for (Instruction instruction : instructions) {
            switch (instruction.direction) {
                case FORWARD -> forward += instruction.units;
                case UP -> depth -= instruction.units;
                case DOWN -> depth += instruction.units;
            }
        }

        return (long) forward * depth;
    }

    public static long calculatePositionProductWithAim(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Instruction> instructions = parseInstructions(lines);

        int forward = 0;
        int depth = 0;
        int aim = 0;

        for (Instruction instruction : instructions) {
            switch (instruction.direction) {
                case FORWARD -> {
                    forward += instruction.units;
                    depth += aim * instruction.units;
                }
                case UP -> aim -= instruction.units;
                case DOWN -> aim += instruction.units;
            }
        }

        return (long) forward * depth;
    }

    private static List<Instruction> parseInstructions(List<String> lines) {
        List<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {
            String[] splitted = line.split(" ");
            instructions.add(new Instruction(Direction.valueOf(splitted[0].toUpperCase()), Integer.parseInt(splitted[1])));
        }
        return instructions;
    }
}
