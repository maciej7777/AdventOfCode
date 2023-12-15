package com.example.adventofcode.year2023.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LensLibrary {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2023/day15/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2023/day15/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSumOfHashes(EXAMPLE_FILENAME));
        //1320
        System.out.println(calculateSumOfHashes(FILENAME));
        //503487
        System.out.println(calculateFocusingPower(EXAMPLE_FILENAME));
        //145
        System.out.println(calculateFocusingPower(FILENAME));
        //261505
    }

    public static long calculateSumOfHashes(final String filename) throws IOException {
        String line = readLine(filename);
        int sum = 0;
        for (String tested : line.split(",")) {
            sum += calculateHash(tested);
        }

        return sum;
    }

    public static long calculateFocusingPower(final String filename) throws IOException {
        String line = readLine(filename);
        List<Map<String, Integer>> boxes = createBoxes(line);
        return sumFocusingPower(boxes);
    }

    private static String readLine(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            return br.readLine();
        }
    }

    private static int calculateHash(String tested) {
        int currentValue = 0;
        for (Character element : tested.toCharArray()) {
            currentValue += element;
            currentValue *= 17;
            currentValue = currentValue % 256;
        }
        return currentValue;
    }

    private static List<Map<String, Integer>> createBoxes(String line) {
        List<Map<String, Integer>> boxes = createEmptyBoxes();
        for (String tested : line.split(",")) {
            if (tested.contains("-")) {
                String[] elements = tested.split("-");
                String label = elements[0];
                int hash = calculateHash(label);

                boxes.get(hash).remove(label);
            } else {
                String[] elements = tested.split("=");
                String label = elements[0];
                int value = Integer.parseInt(elements[1]);
                int hash = calculateHash(label);

                boxes.get(hash).put(label, value);
            }
        }
        return boxes;
    }

    private static List<Map<String, Integer>> createEmptyBoxes() {
        List<Map<String, Integer>> boxes = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            boxes.add(new LinkedHashMap<>());
        }
        return boxes;
    }

    private static int sumFocusingPower(List<Map<String, Integer>> boxes) {
        int sum = 0;
        for (int boxId = 0; boxId < boxes.size(); boxId++) {
            int i = 0;
            for (Map.Entry<String, Integer> element : boxes.get(boxId).entrySet()) {
                int result = (boxId + 1) * (i + 1) * element.getValue();
                sum += (result);
                i++;
            }
        }

        return sum;
    }
}
