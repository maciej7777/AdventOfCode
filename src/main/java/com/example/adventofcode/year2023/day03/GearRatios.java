package com.example.adventofcode.year2023.day03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class GearRatios {
    private static final String FILENAME = "AdventOfCodeData/2023/day03/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day03/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(sumPartNumbers(EXAMPLE_FILENAME));
        //4361
        System.out.println(sumPartNumbers(FILENAME));
        //536576

        System.out.println(sumGearRatios(EXAMPLE_FILENAME));
        //467835
        System.out.println(sumGearRatios(FILENAME));
        //75741499
    }

    private record Input(List<Point> specialCharacters, Map<Point, Integer> numbers, List<Point> stars) {
    }

    private record Point(int x, int y) {
    }

    public static int sumPartNumbers(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = mapInput(lines);

        return calculatePartNumbersSum(input.numbers(), input.specialCharacters());
    }

    public static int sumGearRatios(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = mapInput(lines);

        return calculateGearRatiosSum(input);
    }

    private static GearRatios.Input mapInput(List<String> lines) {
        List<Point> specialCharacters = new ArrayList<>();
        List<Point> stars = new ArrayList<>();
        Map<Point, Integer> numbers = new HashMap<>();

        int y = 0;
        for (String line : lines) {
            String currentNumber = "";
            Point numberStartPoint = null;

            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '.') {
                    if (!currentNumber.isEmpty()) {
                        numbers.put(numberStartPoint, Integer.parseInt(currentNumber));
                        currentNumber = "";
                        numberStartPoint = null;
                    }
                } else if (Character.isDigit(line.charAt(x))) {
                    if (numberStartPoint == null) {
                        numberStartPoint = new Point(x, y);
                    }
                    currentNumber += line.charAt(x);
                } else {
                    if (!currentNumber.isEmpty()) {
                        numbers.put(numberStartPoint, Integer.parseInt(currentNumber));
                        currentNumber = "";
                        numberStartPoint = null;
                    }
                    specialCharacters.add(new Point(x, y));
                    if (line.charAt(x) == '*') {
                        stars.add(new Point(x, y));
                    }
                }
            }
            y++;

            if (!currentNumber.isEmpty()) {
                numbers.put(numberStartPoint, Integer.parseInt(currentNumber));
            }
        }
        return new Input(specialCharacters, numbers, stars);
    }

    private static int calculatePartNumbersSum(Map<Point, Integer> numbers, List<Point> specialCharacters) {
        int sum = 0;
        for (Map.Entry<Point, Integer> number : numbers.entrySet()) {
            if (isPointPartNumber(number.getKey(), number.getValue().toString().length(), specialCharacters)) {
                sum += number.getValue();
            }
        }

        return sum;
    }

    private static int calculateGearRatiosSum(Input input) {
        int sum = 0;
        for (Point star : input.stars) {
            List<Integer> points = getPointsMultiplied(star, input.numbers);
            if (points.size() == 2) {
                sum += points.get(0) * points.get(1);
            }
        }
        return sum;
    }

    private static List<Integer> getPointsMultiplied(Point toCheck, Map<Point, Integer> numbers) {
        List<Integer> points = new ArrayList<>();
        for (Map.Entry<Point, Integer> number : numbers.entrySet()) {
            int numberY = number.getKey().y;
            int numberXLeft = number.getKey().x;
            int numberXRRight = number.getKey().x + number.getValue().toString().length() - 1;

            if (Math.abs(numberY - toCheck.y) <= 1
                    && (Math.abs(numberXLeft - toCheck.x) <= 1
                    || Math.abs(numberXRRight - toCheck.x) <= 1)
            ) {
                points.add(number.getValue());
            }
        }
        return points;
    }

    private static boolean isPointPartNumber(Point toCheck, int length, List<Point> specialCharacters) {
        for (Point specialCharacter : specialCharacters) {
            int pointY = toCheck.y;
            int pointXLeft = toCheck.x;
            int pointXRight = toCheck.x + length - 1;

            if (Math.abs(pointY - specialCharacter.y) <= 1
                    && (Math.abs(pointXLeft - specialCharacter.x) <= 1
                    || Math.abs(pointXRight - specialCharacter.x) <= 1)
            ) {
                return true;
            }
        }
        return false;
    }
}