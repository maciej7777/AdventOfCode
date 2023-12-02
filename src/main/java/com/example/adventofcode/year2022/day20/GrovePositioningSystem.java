package com.example.adventofcode.year2022.day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GrovePositioningSystem {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day20/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day20/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSumOfGroveCoordinates(EXAMPLE_FILENAME));
        System.out.println(calculateSumOfGroveCoordinates(FILENAME));

        System.out.println(calculateSumOfEncryptedGroveCoordinates(EXAMPLE_FILENAME));
        System.out.println(calculateSumOfEncryptedGroveCoordinates(FILENAME));
    }

    public static long calculateSumOfGroveCoordinates(String fileName) throws IOException {
        List<Integer> input = readInput(fileName);
        return calculateSumOfGroveCoordinates(input);
    }

    public static long calculateSumOfEncryptedGroveCoordinates(String fileName) throws IOException {
        List<Integer> input = readInput(fileName);
        return calculateSumOfEncryptedGroveCoordinates(input);
    }

    public static List<Integer> readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<Integer> map = new ArrayList<>();

            while ((line = br.readLine()) != null) {

                Integer integer = Integer.parseInt(line);
                map.add(integer);
            }
            return map;
        }
    }

    private static record MovableLong(long value, int originalPosition) {
    }

    private static long calculateSumOfEncryptedGroveCoordinates(List<Integer> inputValues) {
        List<MovableLong> resultList = new ArrayList<>();
        for (int i = 0, inputValuesSize = inputValues.size(); i < inputValuesSize; i++) {
            Integer value = inputValues.get(i);
            MovableLong movableLong = new MovableLong(value * 811589153L, i);
            resultList.add(movableLong);
        }

        for (int k = 0; k < 10; k++) {
            for (int j = 0; j < inputValues.size(); j++) {
                int currentPosition = 0;
                for (int i = 0; i < resultList.size(); i++) {
                    if (resultList.get(i).originalPosition == j) {
                        currentPosition = i;
                        break;
                    }
                }

                long elementValue = resultList.get(currentPosition).value;
                MovableLong element = resultList.get(currentPosition);
                int position = calculateNewPosition(resultList.size(), currentPosition, elementValue);

                resultList.remove(currentPosition);
                resultList.add(position, element);
            }
        }

        int zeroIndex = resultList.stream().map(movableInt -> movableInt.value).collect(Collectors.toList()).indexOf(0L);

        long element1 = resultList.get((1000 + zeroIndex) % resultList.size()).value;
        long element2 = resultList.get((2000 + zeroIndex) % resultList.size()).value;
        long element3 = resultList.get((3000 + zeroIndex) % resultList.size()).value;
        return element1 + element2 + element3;
    }

    private static int calculateNewPosition(int listSize, int currentPosition, long elementValue) {
        if (currentPosition + elementValue > listSize - 1) {
            return (int) ((currentPosition + elementValue) % (listSize - 1));
        }
        if (currentPosition + elementValue < 0) {
            return (int) (listSize - 1 + (currentPosition + elementValue) % (listSize - 1));
        }
        return (int) (currentPosition + elementValue);
    }


    private static record MovableInteger(int value, boolean visited) {
    }


    private static int calculateSumOfGroveCoordinates(List<Integer> inputValues) {
        List<MovableInteger> resultList = inputValues.stream().map(value -> new MovableInteger(value, false)).collect(Collectors.toList());
        for (int j = 0; j < inputValues.size(); j++) {
            int currentPosition = 0;
            for (int i = 0; i < resultList.size(); i++) {
                if (!resultList.get(i).visited) {
                    currentPosition = i;
                    break;
                }
            }

            int element = resultList.get(currentPosition).value;
            int position = calculateNewPosition(resultList.size(), currentPosition, element);

            resultList.remove(currentPosition);
            resultList.add(position, new MovableInteger(element, true));
        }


        int zeroIndex = resultList.stream().map(movableInt -> movableInt.value).toList().indexOf(0);

        int element1 = resultList.get((1000 + zeroIndex) % resultList.size()).value;
        int element2 = resultList.get((2000 + zeroIndex) % resultList.size()).value;
        int element3 = resultList.get((3000 + zeroIndex) % resultList.size()).value;

        return element1 + element2 + element3;
    }
}
