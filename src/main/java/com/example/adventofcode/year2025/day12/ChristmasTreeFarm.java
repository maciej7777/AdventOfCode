package com.example.adventofcode.year2025.day12;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class ChristmasTreeFarm {
    private static final String FILENAME = "AdventOfCodeData/2025/day12/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2025/day12/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(countRegionsFittingAllPresents(EXAMPLE_FILENAME));
        System.out.println(countRegionsFittingAllPresents(FILENAME));
    }
    
    record Dimensions(int x, int y) {
    }
    
    record Area(Dimensions dimensions, List<Integer> indexes){}

    public static long countRegionsFittingAllPresents(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Area> areas = parseAreas(lines);

        int regionsCount = 0;
        for (Area area: areas) {
            long space = (long) area.dimensions.x * area.dimensions.y; 
            
            int count = 0;
            for (int i = 0; i < area.indexes.size(); i++) {
                count += area.indexes.get(i);
            }
            
            if (space >= count * 9L) {
                regionsCount++;
            }
        }
        
        return regionsCount;
    }

    private static List<Area> parseAreas(List<String> lines) {
        List<Area> areas = new ArrayList<>();
        for (String line : lines) {
            if (line.contains("x")) {
                String[] split1 = line.split(":");
                String[] dimensions = split1[0].split("x");
                int dimensionX = Integer.parseInt(dimensions[0]);
                int dimensionY = Integer.parseInt(dimensions[1]);

                String[] indexes = split1[1].strip().split(" ");
                List<Integer> integerIndexes = Arrays.stream(indexes)
                        .map(Integer::valueOf)
                        .toList();

                areas.add(new Area(new Dimensions(dimensionX, dimensionY), integerIndexes));
            }
        }
        return areas;
    }
}
