package com.example.adventofcode.year2024.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestroomRedoubt {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day14/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day14/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSafetyFactor(EXAMPLE_FILENAME, 11, 7));
        System.out.println(calculateSafetyFactor(FILENAME, 101, 103));
        //System.out.println(calculateStepsToReachEasterEgg(EXAMPLE_FILENAME, 11, 7));
        System.out.println(calculateStepsToReachEasterEgg(FILENAME, 101, 103));
    }


    record Point(int x, int y) {
    }

    record Robot(Point position, Point velocity) {

    }

    public static long calculateSafetyFactor(final String filename, int width, int height) throws IOException {
        List<String> lines = readLines(filename);
        List<Robot> robots = parseRobots(lines);

        for (int i = 0; i < 100; i++) {
            List<Robot> tmpRobots = new ArrayList<>();
            for (Robot r : robots) {
                int newX = (r.position.x + r.velocity.x + width) % width;
                int newY = (r.position.y + r.velocity.y + height) % height;

                tmpRobots.add(new Robot(new Point(newX, newY), r.velocity));
            }
            robots = tmpRobots;
        }

        return multiplyQuadrantResults(width, height, robots);
    }

    public static long calculateStepsToReachEasterEgg(final String filename, int width, int height) throws IOException {
        List<String> lines = readLines(filename);
        List<Robot> robots = parseRobots(lines);


        int i = 0;
        Set<Point> seen;
        do {
            i++;
            seen = new HashSet<>();
            List<Robot> tmpRobots = new ArrayList<>();
            for (Robot r : robots) {
                int newX = (r.position.x + r.velocity.x + width) % width;
                int newY = (r.position.y + r.velocity.y + height) % height;

                tmpRobots.add(new Robot(new Point(newX, newY), r.velocity));
                seen.add(new Point(newX, newY));
            }
            robots = tmpRobots;
        } while (seen.size() < robots.size());

        printTheMap(robots, width, height);

        return i;
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

    private static List<Robot> parseRobots(List<String> lines) {
        List<Robot> robots = new ArrayList<>();
        for (String line : lines) {
            String[] splited = line.split(" ");

            String[] splitedP = splited[0].substring(2).split(",");
            String[] splitedV = splited[1].substring(2).split(",");
            Point p = new Point(Integer.parseInt(splitedP[0]), Integer.parseInt(splitedP[1]));
            Point v = new Point(Integer.parseInt(splitedV[0]), Integer.parseInt(splitedV[1]));

            robots.add(new Robot(p, v));
        }
        return robots;
    }

    private static int multiplyQuadrantResults(int width, int height, List<Robot> robots) {
        int r1 = 0;
        int r2 = 0;
        int r3 = 0;
        int r4 = 0;
        for (Robot r : robots) {
            if (r.position.x < width / 2 && r.position.y < height / 2) {
                r1++;
            } else if (r.position.x > width / 2 && r.position.y < height / 2) {
                r2++;
            } else if (r.position.x < width / 2 && r.position.y > height / 2) {
                r3++;
            } else if (r.position.x > width / 2 && r.position.y > height / 2) {
                r4++;
            }
        }

        return r1 * r2 * r3 * r4;
    }

    private static void printTheMap(List<Robot> robots, int width, int height) {
        List<List<Integer>> map = new ArrayList<>();
        for (int j = 0; j < height; j++) {
            map.add(new ArrayList<>());
            for (int i = 0; i < width; i++) {
                int count = 0;
                for (Robot r : robots) {
                    if (r.position.x == i && r.position.y == j) {
                        count++;
                    }
                }
                if (count == 0) {
                    System.out.print(".");
                } else {
                    System.out.print(count);
                }
                map.get(j).add(count);
            }
            System.out.println("\n");
        }
    }
}
