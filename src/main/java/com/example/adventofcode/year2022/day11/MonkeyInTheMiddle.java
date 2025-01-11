package com.example.adventofcode.year2022.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static com.example.adventofcode.year2022.day11.MonkeyInTheMiddle.OperationType.*;

public class MonkeyInTheMiddle {
    private static final String FILENAME = "AdventOfCodeData/2022day11/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2022day11/example_input";

    public static void main(String[] args) throws IOException {
        List<Monkey> input = readInput(FILENAME);
        System.out.println(calculateMonkeyBusiness(input, 1, 10000));

        List<Monkey> exampleInput = readInput(EXAMPLE_FILENAME);
        System.out.println(calculateMonkeyBusiness(exampleInput, 1, 10000));
    }

    public static long obtainMonkeyBusiness(String filename, int divideBy, int rounds) throws IOException {
        List<Monkey> input = readInput(filename);
        return calculateMonkeyBusiness(input, divideBy, rounds);
    }

    private static long calculateMonkeyBusiness(List<Monkey> input, int divideBy, int rounds) {
        List<Integer> processedItems = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            processedItems.add(0);
        }

        long commonDenominator = input
                .stream()
                .mapToLong(monkey -> monkey.divisibleBy)
                .reduce(1L, (first, second) -> first * second);

        for (int i = 0; i < rounds; i++) {
            for (Monkey monkey : input) {
                for (Long item : monkey.startingItems) {
                    long newWorryLevel;
                    newWorryLevel = switch (monkey.operationType) {
                        case MULTIPLICATION -> (item * monkey.operationValue) / divideBy;
                        case SELF_MULTIPLICATION -> (item * item) / divideBy;
                        case ADDITION -> (item + monkey.operationValue) / divideBy;
                    };

                    if (newWorryLevel % monkey.divisibleBy == 0) {
                        input.get(monkey.monkeyTrue).startingItems.add(newWorryLevel % commonDenominator);
                    } else {
                        input.get(monkey.monkeyFalse).startingItems.add(newWorryLevel % commonDenominator);
                    }

                    int newProcessedValue = processedItems.get(monkey.id) + 1;
                    processedItems.set(monkey.id, newProcessedValue);
                }
                monkey.startingItems = new ArrayList<>();
            }
        }

        processedItems.sort(Comparator.comparingInt(o -> o));

        return (long) processedItems.get(processedItems.size() - 1) * (long) processedItems.get(processedItems.size() - 2);
    }

    enum OperationType {
        SELF_MULTIPLICATION, MULTIPLICATION, ADDITION
    }

    static class Monkey {
        int id;
        List<Long> startingItems;
        String operation;
        OperationType operationType;
        long operationValue;
        long divisibleBy;
        int monkeyTrue;
        int monkeyFalse;

        public Monkey(int id,
                      List<Long> startingItems,
                      String operation,
                      int operationValue,
                      int divisibleBy) {
            this.id = id;
            this.startingItems = startingItems;
            this.operation = operation;
            this.operationValue = operationValue;
            this.divisibleBy = divisibleBy;
        }

        public Monkey(int id) {
            this.id = id;
            this.startingItems = new ArrayList<>();
        }
    }

    public static List<Monkey> readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<Monkey> map = new ArrayList<>();

            Monkey currentMonkey = new Monkey(-1);
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Monkey ")) {
                    int monkeyId = Integer.parseInt(line.substring(0, line.length() - 1).split(" ")[1]);
                    currentMonkey = new Monkey(monkeyId);
                    map.add(currentMonkey);
                } else if (line.startsWith("  Starting items: ")) {
                    String[] extractedItems = line.substring(18).replace(",", "").split(" ");
                    for (String item : extractedItems) {
                        currentMonkey.startingItems.add(Long.parseLong(item));
                    }
                } else if (line.startsWith("  Operation: new = ")) {
                    String extractedOperation = line.substring(19);
                    if (extractedOperation.equals("old * old")) {
                        currentMonkey.operationType = SELF_MULTIPLICATION;
                    } else if (extractedOperation.startsWith("old *")) {
                        currentMonkey.operationType = MULTIPLICATION;
                        countOperationValue(currentMonkey, extractedOperation);
                    } else {
                        currentMonkey.operationType = ADDITION;
                        countOperationValue(currentMonkey, extractedOperation);
                    }
                } else if (line.startsWith("  Test: ")) {
                    String[] elements = line.split(" ");
                    currentMonkey.divisibleBy = Integer.parseInt(elements[elements.length - 1]);
                } else if (line.startsWith("    If true: ")) {
                    String[] elements = line.split(" ");
                    currentMonkey.monkeyTrue = Integer.parseInt(elements[elements.length - 1]);
                } else if (line.startsWith("    If false: ")) {
                    String[] elements = line.split(" ");
                    currentMonkey.monkeyFalse = Integer.parseInt(elements[elements.length - 1]);
                }
            }
            return map;
        }
    }

    private static void countOperationValue(Monkey currentMonkey, String extractedOperation) {
        String[] operationElements = extractedOperation.split(" ");
        currentMonkey.operationValue = Integer.parseInt(operationElements[operationElements.length - 1]);
    }
}
