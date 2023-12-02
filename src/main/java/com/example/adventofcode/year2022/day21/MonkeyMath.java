package com.example.adventofcode.year2022.day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MonkeyMath {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day21/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day21/example_input";
    public static final String ROOT = "root";
    public static final String HUMN = "humn";

    public static void main(String[] args) throws IOException {
        Input exampleInputFirst = readInput(EXAMPLE_FILENAME);
        System.out.println(calculateRoot(exampleInputFirst));

        Input inputFirst = readInput(FILENAME);
        System.out.println(calculateRoot(inputFirst));

        Input exampleInputSecond = readInput(EXAMPLE_FILENAME);
        System.out.println(calculateHumn(exampleInputSecond));

        Input inputSecond = readInput(FILENAME);
        System.out.println(calculateHumn(inputSecond));
    }

    public static long calculateRoot(String fileName) throws IOException {
        Input input = readInput(fileName);
        return calculateRoot(input);
    }

    public static long calculateHumn(String fileName) throws IOException {
        Input input = readInput(fileName);
        return calculateHumn(input);
    }

    private static Input readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<Operation> operations = new ArrayList<>();
            Map<String, Long> values = new HashMap<>();

            while ((line = br.readLine()) != null) {
                line = line.replace(":", "");
                String[] elements = line.split(" ");

                if (elements.length > 2) {
                    operations.add(new Operation(elements[0], elements[1], elements[3], elements[2]));
                } else {
                    values.put(elements[0], Long.parseLong(elements[1]));
                }
            }
            return new Input(operations, values);
        }
    }

    private static record Operation(String result, String first, String second, String type) {
    }

    private static record Input(List<Operation> operations, Map<String, Long> values) {
    }

    private static long calculateRoot(Input operations) {
        while (!operations.operations.isEmpty()) {
            int toRemove = -1;
            for (int i = 0; i < operations.operations.size(); i++) {
                Operation tmpOperation = operations.operations.get(i);
                if (operations.values.containsKey(tmpOperation.first)
                        && operations.values.containsKey(tmpOperation.second)) {
                    toRemove = i;
                    operations.values.put(tmpOperation.result, calculateEquationValue(operations, tmpOperation));
                    break;
                }
            }
            operations.operations.remove(toRemove);
        }

        return operations.values.get(ROOT);
    }

    private static long calculateEquationValue(Input operations, Operation tmpOperation) {
        return switch (tmpOperation.type) {
            case "+":
                yield operations.values.get(tmpOperation.first) + operations.values.get(tmpOperation.second);
            case "-":
                yield operations.values.get(tmpOperation.first) - operations.values.get(tmpOperation.second);
            case "*":
                yield operations.values.get(tmpOperation.first) * operations.values.get(tmpOperation.second);
            case "/":
                yield operations.values.get(tmpOperation.first) / operations.values.get(tmpOperation.second);
            default:
                throw new UnsupportedOperationException();
        };
    }

    public static long calculateHumn(Input operations) {
        operations.values.remove(HUMN);

        List<Operation> processedOperations = operations.operations;
        while (!processedOperations.isEmpty()) {
            int toRemove = -1;
            for (int i = 0; i < processedOperations.size(); i++) {
                Operation tmpOperation = processedOperations.get(i);
                if (operations.values.containsKey(tmpOperation.first) && operations.values.containsKey(tmpOperation.second)) {
                    toRemove = i;
                    long operationValue = calculateEquationValue(operations, tmpOperation);
                    operations.values.put(tmpOperation.result, operationValue);
                    break;
                } else if (tmpOperation.result.equals(ROOT) && operations.values.containsKey(tmpOperation.first)) {
                    operations.values.put(tmpOperation.second, operations.values.get(tmpOperation.first));
                    break;
                } else if (tmpOperation.result.equals(ROOT) && operations.values.containsKey(tmpOperation.second)) {
                    operations.values.put(tmpOperation.first, operations.values.get(tmpOperation.second));
                    break;
                }
            }
            if (toRemove > -1) {
                processedOperations.remove(toRemove);
            } else {
                processedOperations = createFullSetOfEquations(operations);
            }
        }

        return operations.values.get(HUMN);
    }

    private static List<Operation> createFullSetOfEquations(Input operations) {
        List<Operation> remainingOperations = new ArrayList<>();
        remainingOperations.addAll(operations.operations);
        for (Operation tmpOperation : operations.operations) {
            switch (tmpOperation.type) {
                case "+":
                    remainingOperations.add(new Operation(tmpOperation.first, tmpOperation.result, tmpOperation.second, "-"));
                    remainingOperations.add(new Operation(tmpOperation.second, tmpOperation.result, tmpOperation.first, "-"));
                    break;
                case "-":
                    remainingOperations.add(new Operation(tmpOperation.first, tmpOperation.result, tmpOperation.second, "+"));
                    remainingOperations.add(new Operation(tmpOperation.second, tmpOperation.first, tmpOperation.result, "-"));
                    break;
                case "*":
                    remainingOperations.add(new Operation(tmpOperation.first, tmpOperation.result, tmpOperation.second, "/"));
                    remainingOperations.add(new Operation(tmpOperation.second, tmpOperation.result, tmpOperation.first, "/"));
                    break;
                case "/":
                    remainingOperations.add(new Operation(tmpOperation.first, tmpOperation.result, tmpOperation.second, "*"));
                    remainingOperations.add(new Operation(tmpOperation.second, tmpOperation.first, tmpOperation.result, "/"));
                    break;
            }
        }
        return remainingOperations;
    }
}
