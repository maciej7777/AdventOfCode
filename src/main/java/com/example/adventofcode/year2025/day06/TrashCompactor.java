package com.example.adventofcode.year2025.day06;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class TrashCompactor {
    private static final String FILENAME = "AdventOfCodeData/2025/day06/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2025/day06/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(solveMathHomework(EXAMPLE_FILENAME));
        System.out.println(solveMathHomework(FILENAME));
        System.out.println(solveMathHomeworkInCephalopodWay(EXAMPLE_FILENAME));
        System.out.println(solveMathHomeworkInCephalopodWay(FILENAME));
    }

    public static long solveMathHomework(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Character> operations = parseOperations(lines);
        List<List<Long>> numbers = parseNumbers(lines);

        long result = 0;
        for (int i = 0; i < operations.size(); i++) {
            int operatorNumber = i;
            List<Long> nb = numbers.stream().map(list -> list.get(operatorNumber)).toList();
            
            if (operations.get(i) == '*') {
                result += multiplyNumbers(nb);
            } else {
                result += sumNumbers(nb);
            }

        }


        return result;
    }

    public static long solveMathHomeworkInCephalopodWay(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Character> operations = parseOperations(lines);
        List<Integer> columnLengths = calculateColumnLengths(lines);
        
        List<List<String>> numbers = buildNumberRows(lines, columnLengths);
        List<List<Long>> numbersInColumns = mapNumbersInCephalopodWay(columnLengths, numbers);

        long result = 0;
        for (int i = 0; i < operations.size(); i++) {
            if (operations.get(i) == '*') {
                result += multiplyNumbers(numbersInColumns.get(i));
            } else {
                result += sumNumbers(numbersInColumns.get(i));
            }
        }

        return result;
    }

    private static List<Character> parseOperations(List<String> lines) {
        String operationsLine = lines.getLast();
        String cleanedLine = operationsLine.trim().replaceAll(" +", " ");
        return new ArrayList<>(Arrays.stream(cleanedLine.split(" "))
                .map(s -> s.charAt(0))
                .toList());
    }
    
    private static List<List<Long>> parseNumbers(List<String> lines) {
        List<List<Long>> numbers = new ArrayList<>();
        for (String line : lines) {
            String cleanedLine = line.trim().replaceAll(" +", " ");
            if (!cleanedLine.contains("+") && !cleanedLine.contains("*")) {
                List<Long> numbersRow = new ArrayList<>();
                for (String n : cleanedLine.split(" ")) {
                    numbersRow.add(Long.parseLong(n));
                }
                numbers.add(numbersRow);
            }
        }
        return numbers;
    }

    private static List<Integer> calculateColumnLengths(List<String> lines) {
        List<Integer> columnLengths = new ArrayList<>();
        String operationsLine = lines.getLast();
        int currentLength = 0;
        for (int i = 0; i < operationsLine.length(); i++) {
            if (operationsLine.charAt(i) == ' ') {
                currentLength++;
            } else {
                if (currentLength > 0) {
                    columnLengths.add(currentLength);
                }
                currentLength = 0;
            }
        }
        columnLengths.add(currentLength + 1);
        return columnLengths;
    }

    private static List<List<String>> buildNumberRows(List<String> lines, List<Integer> columnLengths) {
        List<List<String>> numbers = new ArrayList<>();
        for (String line : lines) {
            if (!line.contains("+") && !line.contains("*")) {
                List<String> numbersRow = new ArrayList<>();
                int prev = 0;
                for (int length : columnLengths) {
                    numbersRow.add(line.substring(prev, prev + length));
                    prev += length + 1;
                }
                numbers.add(numbersRow);
            }
        }
        return numbers;
    }

    private static List<List<Long>> mapNumbersInCephalopodWay(List<Integer> columnLengths, List<List<String>> numbers) {
        List<List<Long>> numbersInColumns = new ArrayList<>();
        for (int i = 0; i < columnLengths.size(); i++) {
            List<Long> numbersInColumn = new ArrayList<>();
            int columnLength = columnLengths.get(i);
            for (int j = 0; j < columnLength; j++) {
                String tempNumber = "";
                for (List<String> number : numbers) {
                    char numberFromRow = number.get(i).charAt(j);
                    if (numberFromRow != ' ') {
                        tempNumber += numberFromRow;
                    }
                }
                numbersInColumn.add(Long.parseLong(tempNumber));
            }
            numbersInColumns.add(numbersInColumn);
        }
        return numbersInColumns;
    }

    private static long multiplyNumbers(List<Long> numbersInColumns) {
        long product = 1;
        for (Long numbersInColumn : numbersInColumns) {
            product *= numbersInColumn;
        }
        return product;
    }

    private static long sumNumbers(List<Long> numbersInColumns) {
        int sum = 0;
        for (Long numbersInColumn : numbersInColumns) {
            sum += numbersInColumn;
        }
        return sum;
    }
}
