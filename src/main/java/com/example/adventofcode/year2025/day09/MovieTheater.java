package com.example.adventofcode.year2025.day09;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class MovieTheater {
    private static final String FILENAME = "AdventOfCodeData/2025/day09/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2025/day09/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateLargestAreaBruteForce(EXAMPLE_FILENAME));
        System.out.println(calculateLargestAreaBruteForce(FILENAME));
        System.out.println(calculateLargestArea(EXAMPLE_FILENAME));
        System.out.println(calculateLargestArea(FILENAME));
        System.out.println(calculateLargestAreaInsideOfPolygon(EXAMPLE_FILENAME));
        System.out.println(calculateLargestAreaInsideOfPolygon(FILENAME));
    }


    record Point(int x, int y) {
    }

    public static long calculateLargestAreaBruteForce(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Point> points = parsePoints(lines);

        long maxArea = 0;
        for (Point p1 : points) {
            for (Point p2 : points) {
                long area = (long) (Math.abs(p1.x - p2.x) + 1) * (Math.abs(p1.y - p2.y) + 1);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }

        return maxArea;
    }

    public static long calculateLargestArea(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Point> points = parsePoints(lines);

        Set<Rectangle> rectangles = calculateRectangles(points);

        long maxArea = 0;
        for (Rectangle rectangle : rectangles) {
            long area = (long) (rectangle.getHeight() + 1) * (long) (rectangle.getWidth() + 1);
            if (area > maxArea) {
                maxArea = area;
            }
        }

        return maxArea;
    }

    public static long calculateLargestAreaInsideOfPolygon(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Point> points = parsePoints(lines);

        Polygon polygon = calculatePolygon(points);
        Set<Rectangle> rectangles = calculateRectangles(points);

        long maxArea = 0;
        for (Rectangle rectangle : rectangles) {
            long area = (long) (rectangle.getHeight() + 1) * (long) (rectangle.getWidth() + 1);

            if (polygon.contains(
                    rectangle.getX() + 1,
                    rectangle.getY() + 1,
                    rectangle.getWidth() - 1,
                    rectangle.getHeight() - 1) && area > maxArea) {
                maxArea = area;
            }
        }

        return maxArea;
    }

    private static List<Point> parsePoints(List<String> lines) {
        List<Point> points = new ArrayList<>();
        for (String line : lines) {
            String[] p = line.split(",");
            points.add(new Point(Integer.parseInt(p[0]), Integer.parseInt(p[1])));
        }
        return points;
    }
    
    private static Set<Rectangle> calculateRectangles(List<Point> points) {
        Set<Rectangle> rectangles = new HashSet<>();
        for (Point p1 : points) {
            for (Point p2 : points) {
                rectangles.add(new Rectangle(
                        Math.min(p1.x, p2.x),
                        Math.min(p1.y, p2.y),
                        Math.abs(p1.x - p2.x),
                        Math.abs(p1.y - p2.y)
                ));
            }
        }
        return rectangles;
    }

    private static Polygon calculatePolygon(List<Point> points) {
        Polygon polygon = new Polygon();
        for (Point p : points) {
            polygon.addPoint(p.x, p.y);
        }
        return polygon;
    }
}