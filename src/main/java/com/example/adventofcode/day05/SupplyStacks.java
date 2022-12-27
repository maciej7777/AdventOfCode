package com.example.adventofcode.day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class SupplyStacks {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day05/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day05/example_input";


    public static void main(String[] args) throws IOException {
        System.out.println(simulateStacksMovingTogether(EXAMPLE_FILENAME));
        System.out.println(simulateStacksMovingSeparated(EXAMPLE_FILENAME));


        System.out.println(simulateStacksMovingTogether(FILENAME));
        System.out.println(simulateStacksMovingSeparated(FILENAME));
    }

    public static String simulateStacksMovingTogether(String fileName) throws IOException {
        Input input = readInputFile(fileName);

        moveTogether(input);
        return getStacksTop(input.stacks);
    }

    public static String simulateStacksMovingSeparated(String fileName) throws IOException {
        Input input = readInputFile(fileName);

        moveSeparated(input);
        return getStacksTop(input.stacks);
    }

    record Operation(int count, int from, int to) {
    }

    private static record Input(List<ArrayDeque<String>> stacks, List<Operation> operations) {
    }

    private static void moveSeparated(Input input) {
        for (Operation operation : input.operations) {
            for (int i = 0; i < operation.count; i++) {
                String element = input.stacks.get(operation.from - 1).pop();
                input.stacks.get(operation.to - 1).push(element);
            }
        }
    }

    private static void moveTogether(Input input) {
        for (Operation operation : input.operations) {
            ArrayDeque<String> tmpStack = new ArrayDeque<>();
            for (int i = 0; i < operation.count; i++) {
                tmpStack.push(input.stacks.get(operation.from - 1).pop());
            }

            for (int i = 0; i < operation.count; i++) {
                input.stacks.get(operation.to - 1).push(tmpStack.pop());
            }
        }
    }

    private static String getStacksTop(final List<ArrayDeque<String>> stacks) {
        String result = "";
        for (ArrayDeque<String> stack : stacks) {
            result += stack.peek();
        }

        return result;
    }

    private static Input readInputFile(String fileName) throws IOException {
        List<ArrayDeque<String>> stacks = new ArrayList<>();
        List<Operation> operations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("m")) {
                    String[] operation = line.split(" ");
                    operations.add(new Operation(Integer.parseInt(operation[1]), Integer.parseInt(operation[3]), Integer.parseInt(operation[5])));
                } else if (!line.contains("1")) {
                    int j = 0;
                    for (int i = 0; i < line.length(); i += 4) {
                        if (line.charAt(i + 1) != ' ') {
                            while (stacks.size() <= j) {
                                stacks.add(new ArrayDeque<>());
                            }

                            stacks.get(j).addLast(line.substring(i + 1, i + 2));
                        }
                        j++;
                    }
                }
            }
        }
        return new Input(stacks, operations);
    }
}
