package com.example.adventofcode.year2024.day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day09 {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day09/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day09/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSolution1(EXAMPLE_FILENAME));
        System.out.println(calculateSolution1(FILENAME));
        System.out.println(calculateSolution2(EXAMPLE_FILENAME));
        System.out.println(calculateSolution2(FILENAME));
    }


    record Point(int x, int y) {
    }


    private static int pow(int a, int b) {
        if (b == 0) return 1;
        if (b == 1) return a;
        if (((b & 1) == 0)) return pow(a * a, b / 2); //even a=(a^2)^b/2
        else return a * pow(a * a, b / 2); //odd  a=a*(a^2)^b/2

    }

    public static long calculateSolution1(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        List<Integer> space = new ArrayList<>();
        for (String line: lines) {
            boolean occupiedSpace = true;
            int id = 0;
            for (Character character: line.toCharArray()) {
                int digit = character - '0';

                if (occupiedSpace) {
                    for (int i = 0; i < digit; i++) {
                        space.add(id);
                    }
                    id++;
                    occupiedSpace = false;
                } else {
                    for (int i = 0; i < digit; i++) {
                        space.add(-1);
                    }
                    occupiedSpace = true;
                }
            }
        }

        for (int i = 0; i < space.size(); i++) {
            if (space.get(i) == -1) {
                replaceWithFirstSpace(space, i);
            }
        }

        long sum = 0;
        for (int i = 0; i < space.size(); i++) {
            long id = space.get(i)>0 ? space.get(i) : 0;
            sum += id * i;
        }

        return sum;
    }

    record File(int id, int length, int type) {}

    public static long calculateSolution2(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        List<File> discFile = new ArrayList<>();
        for (String line: lines) {
            boolean occupiedSpace = true;
            int id = 0;
            for (Character character: line.toCharArray()) {
                int digit = character - '0';

                if (occupiedSpace) {
                    discFile.add(new File(id, digit, 1));
                    id++;
                    occupiedSpace = false;
                } else {
                    discFile.add(new File(-1, digit, 0));
                    occupiedSpace = true;
                }
            }
        }

        for (int i = 0; i < discFile.size(); i++) {
            if (discFile.get(i).type == 0) {
                tryToReplaceSpace(discFile, i);
            }
        }
        long sum = 0;

        int val = 0;
        for (int i = 0; i < discFile.size(); i++) {
            for (int d = 0; d < discFile.get(i).length; d++) {
                long id = discFile.get(i).type > 0 ? discFile.get(i).id : 0;
                sum += id * val;
                val++;
            }
        }

        return sum;
    }

    private static void tryToReplaceSpace(List<File> discFile, int i) {
        for (int j = discFile.size() - 1; j > i; j--) {
            if (discFile.get(j).type == 1) {
                if (discFile.get(j).length == discFile.get(i).length) {
                    File tmpSpace = discFile.get(i);
                    discFile.set(i, discFile.get(j));
                    discFile.set(j, tmpSpace);
                    return;
                } else if (discFile.get(j).length < discFile.get(i).length) {
                    //TODO some space left
                    int diff = discFile.get(i).length - discFile.get(j).length;
                    discFile.set(i, discFile.get(j));
                    discFile.set(j, new File(-1, discFile.get(j).length, 0));
                    discFile.add(i+1, new File(-1, diff, 0));

                    return;
                }

            }
        }
    }

    private static void replaceWithFirstSpace(List<Integer> space, int i) {
        for (int j = space.size() - 1; j > i; j--) {
            if (space.get(j) > -1) {
                space.set(i, space.get(j));
                space.set(j, -1);
                return;
            }
        }
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
}
