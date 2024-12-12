package com.example.adventofcode.year2024.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GardenGroups {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day12/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day12/example_input";
    private static final String EXAMPLE_FILENAME2 = "src/main/java/com/example/adventofcode/year2024/day12/example_input2";
    private static final String EXAMPLE_FILENAME3 = "src/main/java/com/example/adventofcode/year2024/day12/example_input3";

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
        Set<Point> currentlyVisited = new HashSet<>();

        Character currentElement = map.get(j).get(i);
        Deque<Point> areaToCheck = new ArrayDeque<>();
        areaToCheck.add(currentPoint);
        visited.add(currentPoint);
        currentlyVisited.add(currentPoint);
        int numberOfElements = 1;

        int borders = 0;
        while (!areaToCheck.isEmpty()) {
            Point checking = areaToCheck.pop();

            for (Point direction : DIRECTIONS) {
                Point newPoint = new Point(checking.x + direction.x, checking.y + direction.y);

                if (isValid(newPoint, map) && !visited.contains(newPoint) && map.get(newPoint.y).get(newPoint.x).equals(currentElement)) {
                    numberOfElements++;
                    areaToCheck.add(newPoint);
                    visited.add(newPoint);
                    currentlyVisited.add(newPoint);
                } else if (!currentlyVisited.contains(newPoint)) {
                    borders++;
                }
            }
        }

        return numberOfElements * borders;
    }

    record PointAndDirection(Point point, Point direction) {

    }

    private static int calculateRegionFencePriceDiscounted(int j, int i, List<List<Character>> map, Set<Point> visited) {
        Point currentPoint = new Point(i, j);
        if (visited.contains(currentPoint)) {
            return 0;
        }
        Set<Point> currentlyVisited = new HashSet<>();

        Character currentElement = map.get(j).get(i);
        Deque<Point> areaToCheck = new ArrayDeque<>();
        areaToCheck.add(currentPoint);
        visited.add(currentPoint);
        currentlyVisited.add(currentPoint);
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
                    currentlyVisited.add(newPoint);
                } else if (!currentlyVisited.contains(newPoint)) {
                    borders.add(new PointAndDirection(newPoint, direction));
                }
            }
        }

        int finalBorders = 0;
        while (!borders.isEmpty()) {
            PointAndDirection pd = borders.pop();
            finalBorders++;


            int currentX = pd.point.x + pd.direction.y;
            int currentY = pd.point.y + pd.direction.x;
            Point neighboar = new Point(currentX, currentY);
            PointAndDirection neighboarPAD = new PointAndDirection(neighboar, pd.direction);
            while (borders.contains(neighboarPAD)) {
                borders.remove(neighboarPAD);
                currentX += pd.direction.y;
                currentY += pd.direction.x;
                neighboar = new Point(currentX, currentY);
                neighboarPAD = new PointAndDirection(neighboar, pd.direction);
            }

            currentX = pd.point.x - pd.direction.y;
            currentY = pd.point.y - pd.direction.x;
            neighboar = new Point(currentX, currentY);
            neighboarPAD = new PointAndDirection(neighboar, pd.direction);
            while (borders.contains(neighboarPAD)) {
                borders.remove(neighboarPAD);
                currentX -= pd.direction.y;
                currentY -= pd.direction.x;
                neighboar = new Point(currentX, currentY);
                neighboarPAD = new PointAndDirection(neighboar, pd.direction);
            }
        }

        return numberOfElements * finalBorders;
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
