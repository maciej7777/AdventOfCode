package com.example.adventofcode.year2025.day07;

import java.io.IOException;
import java.util.*;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class Laboratories {
    private static final String FILENAME = "AdventOfCodeData/2025/day07/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2025/day07/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateTimesBeamSplits(EXAMPLE_FILENAME));
        System.out.println(calculateTimesBeamSplits(FILENAME));
        System.out.println(calculateTachyonParticleTimelines(EXAMPLE_FILENAME));
        System.out.println(calculateTachyonParticleTimelines(FILENAME));
    }
    
    record Point(int x, int y) {
    }

    private record Input(Point startPosition, Set<Point> beams, int height) {
    }
    
    public static long calculateTimesBeamSplits(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseInput(lines);
        
        Deque<Point> beamsToAnalyze = new ArrayDeque<>();
        beamsToAnalyze.add(input.startPosition());
        Set<Point> seen = new HashSet<>();

        int splitCount = 0;
        while(!beamsToAnalyze.isEmpty()) {
            Point current = beamsToAnalyze.poll();
            if (seen.contains(current)) {
                continue;
            }
            seen.add(current);

            if (current.y + 2 < input.height()) {
                Point next = new Point(current.x, current.y+2);
                if (input.beams().contains(next)) {
                    beamsToAnalyze.push(new Point(current.x-1, current.y+2));
                    beamsToAnalyze.push(new Point(current.x+1, current.y+2));
                    splitCount++;
                } else {
                    beamsToAnalyze.push(next);
                }
            }
        }

        return splitCount;
    }

    public static long calculateTachyonParticleTimelines(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseInput(lines);

        Map<Point, Long> timelinesCache = new HashMap<>();
        return calculateRays(input.startPosition, input.beams, timelinesCache, input.height);
    }

    private static Input parseInput(List<String> lines) {
        Point startPosition = new Point(0, 0);
        Set<Point> beams = new HashSet<>();
        int j = 0;
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '^') {
                    beams.add(new Point(i, j));
                } else if (line.charAt(i) == 'S') {
                    startPosition = new Point(i, j);
                }
            }
            j++;
        }
        int height = lines.size();
        return new Input(startPosition, beams, height);
    }

    private static long calculateRays(Point current, Set<Point> beams, Map<Point, Long> timelinesCache, int height) {
        if (current.y + 2 == height) {
            timelinesCache.put(current, 1L);
            return 1;
        }
        Point next = new Point(current.x, current.y+2);
        if (beams.contains(next)) {
            Point left = new Point(current.x-1, current.y+2);
            Point right = new Point(current.x+1, current.y+2);

            long result = calculateRaysWithCache(left, beams, timelinesCache, height) + calculateRaysWithCache(right, beams, timelinesCache, height);
            timelinesCache.put(current, result);
            return result;
        } else {
            long result = calculateRaysWithCache(next, beams, timelinesCache, height);
            timelinesCache.put(current, result);
            return result;
        }
    }

    private static long calculateRaysWithCache(Point current, Set<Point> beams, Map<Point, Long> timelinesCache, int height) {
        if (timelinesCache.containsKey(current)) {
            return timelinesCache.get(current);
        }
        return calculateRays(current, beams, timelinesCache, height);
    }
}
