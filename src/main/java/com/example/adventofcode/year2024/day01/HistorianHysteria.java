package com.example.adventofcode.year2024.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistorianHysteria {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day01/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day01/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateTotalDistance(EXAMPLE_FILENAME));
        System.out.println(calculateTotalDistance(FILENAME));
        System.out.println(calculateSimilarityScore(EXAMPLE_FILENAME));
        System.out.println(calculateSimilarityScore(FILENAME));
    }

    public static long calculateTotalDistance(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Integer>> idLists = getIntegerLists(lines);

        Collections.sort(idLists.get(0));
        Collections.sort(idLists.get(1));

        int sum = 0;
        for (int i = 0; i < idLists.get(0).size(); i++) {
            sum += Math.abs(idLists.get(0).get(i) - idLists.get(1).get(i));
        }

        return sum;
    }

    public static long calculateSimilarityScore(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Integer>> idLists = getIntegerLists(lines);

        long sum = 0;
        for (long number : idLists.get(0)) {
            long count2 = idLists.get(1).stream().filter(x -> x == number).count();
            sum += number * count2;
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

    private static List<List<Integer>> getIntegerLists(List<String> lines) {
        List<List<Integer>> idLists = new ArrayList<>();
        List<Integer> idList1 = new ArrayList<>();
        List<Integer> idList2 = new ArrayList<>();

        for (String line: lines) {
            String[] ids = line.split("   ");
            idList1.add(Integer.parseInt(ids[0]));
            idList2.add(Integer.parseInt(ids[1]));
        }

        idLists.add(idList1);
        idLists.add(idList2);
        return idLists;
    }
}
