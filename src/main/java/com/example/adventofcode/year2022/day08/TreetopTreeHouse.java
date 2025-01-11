package com.example.adventofcode.year2022.day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TreetopTreeHouse {
    private static final String FILENAME = "AdventOfCodeData/2022day08/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2022day08/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateTreesVisibleFromOutside(EXAMPLE_FILENAME));
        System.out.println(calculateTreesVisibleFromOutside(FILENAME));

        System.out.println(calculateHighestScenicScore(EXAMPLE_FILENAME));
        System.out.println(calculateHighestScenicScore(FILENAME));
    }

    public static int calculateTreesVisibleFromOutside(String fileName) throws IOException {
        List<List<Integer>> input = readInput(fileName);

        int height = input.size();
        int width = input.get(0).size();
        int visible = 2 * height + 2 * width - 4;

        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                if (validatePoint(i, j, height, width, input) > 0) {
                    visible++;
                }
            }
        }

        return visible;
    }

    public static int calculateHighestScenicScore(String fileName) throws IOException {
        List<List<Integer>> input = readInput(fileName);

        int height = input.size();
        int width = input.get(0).size();

        int maxScenicScore = 0;
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                int index = countIndex(i, j, height, width, input);
                if (index>maxScenicScore) {
                    maxScenicScore = index;
                }
            }
        }

        return maxScenicScore;
    }

    private static int countIndex(int i, int j, int height, int width, List<List<Integer>> input) {
        int treeHeight = input.get(i).get(j);

        int index1 = 0;
        for (int z = i - 1; z >= 0; z--) {
            index1++;
            if (treeHeight <= input.get(z).get(j)) {
                break;
            }
        }

        int index2 = 0;
        for (int z = i + 1; z < height; z++) {
            index2++;

            if (treeHeight <= input.get(z).get(j)) {
                break;
            }
        }


        int index3 = 0;
        for (int z = j - 1; z >=0; z--) {
            index3++;

            if (treeHeight <= input.get(i).get(z)) {
                break;
            }

        }

        int index4 = 0;

        for (int z = j + 1; z < width; z++) {
            index4++;

            if (treeHeight <= input.get(i).get(z)) {
                break;
            }

        }

        return index1*index2*index3*index4;
    }

    private static int validatePoint(int i, int j, int height, int width, List<List<Integer>> input) {
        boolean visible = true;
        int visibleCount = 0;
        int treeHeight = input.get(i).get(j);

        for (int z = 0; z < i; z++) {
            if (treeHeight <= input.get(z).get(j)) {
                visible = false;
                break;
            }
        }
        if (visible) visibleCount++;

        visible = true;
        for (int z = i + 1; z < height; z++) {
            if (treeHeight <= input.get(z).get(j)) {
                visible = false;
                break;
            }
        }
        if (visible) visibleCount++;

        visible = true;
        for (int z = 0; z < j; z++) {
            if (treeHeight <= input.get(i).get(z)) {
                visible = false;
                break;
            }
        }
        if (visible) visibleCount++;

        visible = true;
        for (int z = j + 1; z < width; z++) {
            if (treeHeight <= input.get(i).get(z)) {
                visible = false;
                break;
            }
        }
        if (visible) visibleCount++;

        return visibleCount;
    }


    public static List<List<Integer>> readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<List<Integer>> map = new ArrayList<>();
            while ((line = br.readLine()) != null) {

                List<Integer> tmp = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
                    tmp.add(line.charAt(i) - '0');
                }

                map.add(tmp);
            }

            return map;
        }
    }
}
