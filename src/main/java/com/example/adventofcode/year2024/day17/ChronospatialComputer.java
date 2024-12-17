package com.example.adventofcode.year2024.day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ChronospatialComputer {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day17/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day17/example_input";
    private static final String EXAMPLE_FILENAME2 = "src/main/java/com/example/adventofcode/year2024/day17/example_input2";
    
    
    public static void main(String[] args) throws IOException {
        System.out.println(calculateProgramOutput(EXAMPLE_FILENAME));
        System.out.println(calculateProgramOutput(EXAMPLE_FILENAME2));
        System.out.println(calculateProgramOutput(FILENAME));
        System.out.println(calculateMinimalInitialValueToCopyProgram(EXAMPLE_FILENAME2));
        System.out.println(calculateMinimalInitialValueToCopyProgram(FILENAME));
    }

    private record Input(long registerA, int registerB, int registerC, List<Integer> program) {
    }

    record PartialSolution(int index, long moduloResult) {
    }

    public static List<Integer> calculateProgramOutput(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseInput(lines);
        return runProgram(input.program(), input.registerA(), input.registerB(), input.registerC());
    }

    public static long calculateMinimalInitialValueToCopyProgram(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Integer> program = parseProgram(lines);

        Queue<PartialSolution> availablePartialSolutions = new ArrayDeque<>();
        availablePartialSolutions.add(new PartialSolution(program.size() - 1, 0));

        List<Long> results = new ArrayList<>();
        while (!availablePartialSolutions.isEmpty()) {
            PartialSolution c = availablePartialSolutions.poll();
            for (long a = 8 * c.moduloResult; a < 8 * (c.moduloResult + 1); a++) {
                List<Integer> newResults = runProgram(program, a, 0, 0);
                if (newResults.equals(program.subList(c.index, program.size()))) {
                    if (c.index == 0) {
                        results.add(a);
                    } else {
                        availablePartialSolutions.add(new PartialSolution(c.index - 1, a));
                    }
                }
            }
        }

        return results.stream().min(Comparator.comparingLong(a -> a)).orElse(-1L);
    }

    private static List<String> readLines(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static Input parseInput(List<String> lines) {
        long registerA = 0;
        int registerB = 0;
        int registerC = 0;
        List<Integer> numbers = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("Register A:")) {
                registerA = Long.parseLong(line.substring(12));
            } else if (line.startsWith("Register B:")) {
                registerB = Integer.parseInt(line.substring(12));
            } else if (line.startsWith("Register C:")) {
                registerC = Integer.parseInt(line.substring(12));
            } else if (!line.isEmpty()) {
                String[] numbersS = line.substring(9).split(",");
                for (String n : numbersS) {
                    numbers.add(Integer.parseInt(n));
                }
            }
        }
        return new Input(registerA, registerB, registerC, numbers);
    }

    private static List<Integer> parseProgram(List<String> lines) {
        List<Integer> numbers = new ArrayList<>();
        for (String line : lines) {
            if (!line.startsWith("Register A:")
                    && !line.startsWith("Register B:")
                    && !line.startsWith("Register C:")
                    && !line.isEmpty()) {
                String[] numbersS = line.substring(9).split(",");
                for (String n : numbersS) {
                    numbers.add(Integer.parseInt(n));
                }
            }
        }
        return numbers;
    }

    private static List<Integer> runProgram(List<Integer> numbers, long registerAValue, long registerBValue, long registerCValue) {
        List<Integer> results = new ArrayList<>();
        int i = 0;
        while (i < numbers.size()) {
            int instruction = numbers.get(i);
            int operand = numbers.get(i + 1);
            long comboOperand = operand;

            if (comboOperand == 4) {
                comboOperand = registerAValue;
            } else if (comboOperand == 5) {
                comboOperand = registerBValue;
            } else if (comboOperand == 6) {
                comboOperand = registerCValue;
            }

            switch (instruction) {
                case 0:
                    registerAValue = registerAValue >> comboOperand;
                    i += 2;
                    break;
                case 1:
                    registerBValue = registerBValue ^ operand;
                    i += 2;
                    break;
                case 2:
                    registerBValue = (int) (comboOperand % 8);
                    i += 2;
                    break;
                case 3:
                    if (registerAValue != 0) {
                        i = operand;
                    } else {
                        i += 2;
                    }
                    break;
                case 4:
                    registerBValue = registerBValue ^ registerCValue;
                    i += 2;
                    break;
                case 5:
                    results.add((int) (comboOperand % 8));
                    i += 2;
                    break;
                case 6:
                    registerBValue = registerAValue >> comboOperand;
                    i += 2;
                    break;
                case 7:
                    registerCValue = registerAValue >> comboOperand;
                    i += 2;
                    break;

            }
        }
        return results;
    }
}
