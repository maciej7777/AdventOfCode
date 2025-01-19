package com.example.adventofcode.year2024.day12;

import java.io.IOException;
import java.util.*;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class GardenGroups {
    private static final String FILENAME = "AdventOfCodeData/2024/day12/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2024/day12/example_input";
    private static final String EXAMPLE_FILENAME2 = "AdventOfCodeData/2024/day12/example_input2";
    private static final String EXAMPLE_FILENAME3 = "AdventOfCodeData/2024/day12/example_input3";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateFencingPrice(EXAMPLE_FILENAME));
        System.out.println(calculateFencingPrice(EXAMPLE_FILENAME2));
        System.out.println(calculateFencingPrice(EXAMPLE_FILENAME3));
        System.out.println(calculateFencingPrice(FILENAME));
        System.out.println(calculateFencingPriceDiscounted(EXAMPLE_FILENAME));
        System.out.println(calculateFencingPriceDiscounted(EXAMPLE_FILENAME2));
        System.out.println(calculateFencingPriceDiscounted(EXAMPLE_FILENAME3));
        System.out.println(calculateFencingPriceDiscounted(FILENAME));
    }


    record Point(int x, int y) {
    }

    private static final List<Point> DIRECTIONS = List.of(
            new Point(-1, 0),
            new Point(1, 0),
            new Point(0, 1),
            new Point(0, -1)
    );

    public static long calculateFencingPrice(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> map = readMap(lines);
        Set<Point> visited = new HashSet<>();
        int sum = 0;
        for (int j = 0; j < map.size(); j++) {
            for (int i = 0; i < map.get(j).size(); i++) {
                sum += calculateRegionFencePrice(j, i, map, visited);
            }
        }

        return sum;
    }

    public static long calculateFencingPriceDiscounted(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> map = readMap(lines);
        Set<Point> visited = new HashSet<>();
        int sum = 0;
        for (int j = 0; j < map.size(); j++) {
            for (int i = 0; i < map.get(j).size(); i++) {
                sum += calculateRegionFencePriceDiscounted(j, i, map, visited);
            }
        }

        return sum;
    }

    private static int calculateRegionFencePrice(int j, int i, List<List<Character>> map, Set<Point> visited) {
        Point currentPoint = new Point(i, j);
        if (visited.contains(currentPoint)) {
            return 0;
        }
        Set<Point> currentRegion = new HashSet<>();

        Character currentElement = map.get(j).get(i);
        Deque<Point> areaToCheck = new ArrayDeque<>();
        areaToCheck.add(currentPoint);
        visited.add(currentPoint);
        currentRegion.add(currentPoint);

        int borders = 0;
        while (!areaToCheck.isEmpty()) {
            Point checking = areaToCheck.pop();

            for (Point direction : DIRECTIONS) {
                Point newPoint = new Point(checking.x + direction.x, checking.y + direction.y);

                if (isValid(newPoint, map) && !visited.contains(newPoint) && map.get(newPoint.y).get(newPoint.x).equals(currentElement)) {
                    areaToCheck.add(newPoint);
                    visited.add(newPoint);
                    currentRegion.add(newPoint);
                } else if (!currentRegion.contains(newPoint)) {
                    borders++;
                }
            }
        }

        return currentRegion.size() * borders;
    }

    record PointAndDirection(Point point, Point direction) {

    }

    private static int calculateRegionFencePriceDiscounted(int j, int i, List<List<Character>> map, Set<Point> visited) {
        Point currentPoint = new Point(i, j);
        if (visited.contains(currentPoint)) {
            return 0;
        }
        Set<Point> currentRegion = new HashSet<>();

        Character currentElement = map.get(j).get(i);
        Deque<Point> areaToCheck = new ArrayDeque<>();
        areaToCheck.add(currentPoint);
        visited.add(currentPoint);
        currentRegion.add(currentPoint);
        int numberOfElements = 1;

        Deque<PointAndDirection> borders = new ArrayDeque<>();
        while (!areaToCheck.isEmpty()) {
            Point checking = areaToCheck.pop();

            for (Point direction : DIRECTIONS) {
                Point newPoint = new Point(checking.x + direction.x, checking.y + direction.y);

                if (isValid(newPoint, map) && !visited.contains(newPoint) && map.get(newPoint.y).get(newPoint.x).equals(currentElement)) {
                    numberOfElements++;
                    areaToCheck.add(newPoint);
                    visited.add(newPoint);
                    currentRegion.add(newPoint);
                } else if (!currentRegion.contains(newPoint)) {
                    borders.add(new PointAndDirection(newPoint, direction));
                }
            }
        }

        return numberOfElements * countNumberOfSides(borders);
    }

    private static int countNumberOfSides(Deque<PointAndDirection> borders) {
        int finalBorders = 0;
        while (!borders.isEmpty()) {
            PointAndDirection pd = borders.pop();
            finalBorders++;

            removePointsOnTheSameSide(borders, pd, pd.direction.y, pd.direction.x);
            removePointsOnTheSameSide(borders, pd, -1 * pd.direction.y, -1 * pd.direction.x);
        }
        return finalBorders;
    }

    private static void removePointsOnTheSameSide(Deque<PointAndDirection> borders, PointAndDirection pd, int dx, int dy) {
        int currentX = pd.point.x + dx;
        int currentY = pd.point.y + dy;
        Point neighboar = new Point(currentX, currentY);
        PointAndDirection neighboarPAD = new PointAndDirection(neighboar, pd.direction);
        while (borders.contains(neighboarPAD)) {
            borders.remove(neighboarPAD);
            currentX += dx;
            currentY += dy;
            neighboar = new Point(currentX, currentY);
            neighboarPAD = new PointAndDirection(neighboar, pd.direction);
        }
    }

    private static boolean isValid(Point newPoint, List<List<Character>> elements) {
        if (newPoint.x < 0 || newPoint.y < 0) {
            return false;
        }
        if (newPoint.x >= elements.size()) {
            return false;
        }
        if (newPoint.y >= elements.get(newPoint.x).size()) {
            return false;
        }

        return true;
    }

    private static List<List<Character>> readMap(List<String> lines) {
        List<List<Character>> map = new ArrayList<>();
        for (String line : lines) {
            List<Character> elements = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                elements.add(line.charAt(i));
            }
            map.add(elements);
        }
        return map;
    }
}
