package com.example.adventofcode.year2023.day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MirageMaintenance {
    private static final String FILENAME = "AdventOfCodeData/2023/day09/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day09/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSumOfExtrapolatedValues(EXAMPLE_FILENAME));
        //114
        System.out.println(calculateSumOfExtrapolatedValues(FILENAME));
        //1789635132
        System.out.println(calculateSumOfExtrapolatedValuesOnTheBeginning(EXAMPLE_FILENAME));
        //2
        System.out.println(calculateSumOfExtrapolatedValuesOnTheBeginning(FILENAME));
        //913
    }

    public static long calculateSumOfExtrapolatedValues(final String filename) throws IOException {
        List<String> inputLines = readLines(filename);
        List<List<Integer>> sequences = mapSequences(inputLines);

        long sum = 0L;
        for (List<Integer> sequence : sequences) {
            sum += extrapolateValue(sequence);
        }
        return sum;
    }

    public static long calculateSumOfExtrapolatedValuesOnTheBeginning(final String filename) throws IOException {
        List<String> inputLines = readLines(filename);
        List<List<Integer>> sequences = mapSequences(inputLines);

        long sum = 0L;
        for (List<Integer> sequence : sequences) {
            sum += extrapolateValueAtHeBeginning(sequence);
        }
        return sum;
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

    private static List<List<Integer>> mapSequences(List<String> inputLines) {
        List<List<Integer>> sequences = new ArrayList<>();
        for (String line : inputLines) {
            String[] numbers = line.split(" ");
            List<Integer> sequence = new ArrayList<>();
            for (String number : numbers) {
                sequence.add(Integer.parseInt(number));
            }
            sequences.add(sequence);
        }
        return sequences;
    }

    private static long extrapolateValue(List<Integer> sequence) {
        List<Integer> currentSequence = sequence;
        List<List<Integer>> steps = new ArrayList<>();
        steps.add(sequence);
        while (!isSequenceStabilized(currentSequence)) {
            currentSequence = new ArrayList<>();
            for (int i = 1; i < steps.getLast().size(); i++) {
                currentSequence.add(steps.getLast().get(i) - steps.getLast().get(i - 1));
            }
            steps.add(currentSequence);
        }
        for (int i = steps.size() - 2; i >= 0; i--) {
            int lastBelow = steps.get(i + 1).getLast();
            int lastElement = steps.get(i).getLast();
            steps.get(i).add(lastBelow + lastElement);
        }
        return steps.getFirst().getLast();
    }

    private static long extrapolateValueAtHeBeginning(List<Integer> sequence) {
        List<Integer> currentSequence = sequence;
        List<List<Integer>> steps = new ArrayList<>();
        steps.add(sequence);
        while (!isSequenceStabilized(currentSequence)) {
            currentSequence = new ArrayList<>();
            for (int i = 1; i < steps.getLast().size(); i++) {
                currentSequence.add(steps.getLast().get(i) - steps.getLast().get(i - 1));
            }
            steps.add(currentSequence);
        }
        for (int i = steps.size() - 2; i >= 0; i--) {
            int firstBelow = steps.get(i + 1).getFirst();
            int firstElement = steps.get(i).getFirst();
            steps.get(i).addFirst(firstElement - firstBelow);
        }
        return steps.getFirst().getFirst();
    }

    private static boolean isSequenceStabilized(List<Integer> currentSequence) {
        for (int i = 0; i < currentSequence.size() - 1; i++) {
            if (!currentSequence.get(i).equals(currentSequence.get(i + 1))) {
                return false;
            }
        }
        return currentSequence.stream().reduce(0, Integer::sum) == 0;
    }
}
