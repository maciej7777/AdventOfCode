package com.example.adventofcode.year2024.day25;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class CodeChronicle {
    private static final String FILENAME = "AdventOfCodeData/2024/day25/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2024/day25/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateFittingKeys(EXAMPLE_FILENAME));
        System.out.println(calculateFittingKeys(FILENAME));
    }

    private record Input(List<List<Integer>> locks, List<List<Integer>> keys) {
    }

    public static long calculateFittingKeys(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseLocksAndKeys(lines);

        return countFittingKeys(input.locks(), input.keys());
    }

    private static Input parseLocksAndKeys(List<String> lines) {
        List<List<Integer>> locks = new ArrayList<>();
        List<List<Integer>> keys = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 8) {
            List<Integer> lock = new ArrayList<>();
            for (int k = 0; k < 5; k++) {
                int count = -1;
                for (int j = 0; j < 7; j++) {
                    if (lines.get(i + j).charAt(k) == '#') {
                        count++;
                    }
                }
                lock.add(count);
            }
            if (lines.get(i).equals("#####")) {
                locks.add(lock);
            } else {
                keys.add(lock);
            }
        }
        return new Input(locks, keys);
    }

    private static int countFittingKeys(List<List<Integer>> locks, List<List<Integer>> keys) {
        int result = 0;
        for (List<Integer> lock : locks) {
            for (List<Integer> key : keys) {
                if (isKeyFitting(lock, key)) {
                    result++;
                }
            }
        }

        return result;
    }

    private static boolean isKeyFitting(List<Integer> lock, List<Integer> key) {
        boolean fits = true;
        for (int i = 0; i < 5; i++) {
            if (lock.get(i) + key.get(i) > 5) {
                fits = false;
                break;
            }
        }
        return fits;
    }
}
