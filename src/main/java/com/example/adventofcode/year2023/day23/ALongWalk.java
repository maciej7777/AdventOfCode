package com.example.adventofcode.year2023.day23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ALongWalk {
    private static final String FILENAME = "AdventOfCodeData/2023/day23/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day23/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateLongestHike(EXAMPLE_FILENAME));
        //94
        System.out.println(calculateLongestHike(FILENAME));
        //2170
        System.out.println(calculateLongestHikeWithoutSlopes(EXAMPLE_FILENAME));
        //154
        System.out.println(calculateLongestHikeWithoutSlopes(FILENAME));
        //6502
    }

    record Point(int x, int y) {
    }

    record State(Point point, int distance, Set<Point> history) {
    }

    record Edge(Point point, int distance) {
    }

    private static final List<Point> DIRECTIONS = List.of(
            new Point(0, 1),
            new Point(0, -1),
            new Point(1, 0),
            new Point(-1, 0)
    );

    private static final Set<Character> AVAILABLE_PLOTS = Set.of('.', '>', '<', 'v', '^');

    public static long calculateLongestHike(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> map = buildMap(lines);
        Map<Point, Set<Edge>> initialGraph = buildGraph(map);
        Map<Point, Set<Edge>> reducedGraph = reduceGraph(initialGraph);

        Point start = new Point(1, 0);
        Point end = new Point(map.getLast().size() - 2, map.size() - 1);

        return calculateLongestDistanceBetween(start, reducedGraph, end);
    }

    public static long calculateLongestHikeWithoutSlopes(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> map = buildMap(lines);
        Map<Point, Set<Edge>> initialGraph = buildGraphWithoutSlopes(map);
        Map<Point, Set<Edge>> reducedGraph = reduceGraph(initialGraph);

        Point start = new Point(1, 0);
        Point end = new Point(map.getLast().size() - 2, map.size() - 1);
        return calculateLongestDistanceBetween(start, reducedGraph, end);
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

    private static List<List<Character>> buildMap(List<String> inputLines) {
        List<List<Character>> map = new ArrayList<>();
        for (String line : inputLines) {
            char[] splitted = line.toCharArray();
            List<Character> lavaLine = new ArrayList<>();
            for (char c : splitted) {
                lavaLine.add(c);
            }
            map.add(lavaLine);
        }
        return map;
    }

    private static Map<Point, Set<Edge>> buildGraph(List<List<Character>> map) {
        Map<Point, Set<Edge>> graph = new HashMap<>();

        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (AVAILABLE_PLOTS.contains(map.get(y).get(x))) {
                    Point current = new Point(x, y);
                    graph.put(current, getNeighbourEdges(map, current));
                }
            }
        }

        return graph;
    }

    private static Map<Point, Set<Edge>> buildGraphWithoutSlopes(List<List<Character>> map) {
        Map<Point, Set<Edge>> graph = new HashMap<>();

        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (AVAILABLE_PLOTS.contains(map.get(y).get(x))) {
                    Point current = new Point(x, y);
                    graph.put(current, getNeighbourEdgesWithoutSlopes(map, current));
                }
            }
        }

        return graph;
    }

    private static Set<Edge> getNeighbourEdges(List<List<Character>> map, Point current) {
        Set<Edge> neighbours = new HashSet<>();
        Point newPoint;
        switch (map.get(current.y).get(current.x)) {
            case '.': {
                for (Point direction : DIRECTIONS) {
                    newPoint = new Point(current.x + direction.x, current.y + direction.y);
                    if (isValid(newPoint, map) && AVAILABLE_PLOTS.contains(map.get(newPoint.y).get(newPoint.x))) {
                        neighbours.add(new Edge(newPoint, 1));
                    }
                }
                return neighbours;
            }
            case '>': {
                newPoint = new Point(current.x + 1, current.y);
                break;
            }
            case '<': {
                newPoint = new Point(current.x - 1, current.y);
                break;
            }
            case 'v': {
                newPoint = new Point(current.x, current.y + 1);
                break;
            }
            case '^': {
                newPoint = new Point(current.x, current.y - 1);
                break;
            }
            default:
                throw new UnsupportedOperationException("The only available directions are '.', '<', '>', 'v' and '^'");
        }

        if (isValid(newPoint, map) && AVAILABLE_PLOTS.contains(map.get(newPoint.y).get(newPoint.x))) {
            neighbours.add(new Edge(newPoint, 1));
        }
        return neighbours;
    }

    private static Set<Edge> getNeighbourEdgesWithoutSlopes(List<List<Character>> map, Point current) {
        Set<Edge> neighbours = new HashSet<>();
        for (Point direction : DIRECTIONS) {
            Point newPoint = new Point(current.x + direction.x, current.y + direction.y);
            if (isValid(newPoint, map) && AVAILABLE_PLOTS.contains(map.get(newPoint.y).get(newPoint.x))) {
                neighbours.add(new Edge(newPoint, 1));
            }
        }
        return neighbours;
    }

    private static boolean isValid(Point newPoint, List<List<Character>> map) {
        return newPoint.y >= 0 && newPoint.y < map.size()
                && newPoint.x >= 0 && newPoint.x < map.getFirst().size();
    }

    private static Map<Point, Set<Edge>> reduceGraph(Map<Point, Set<Edge>> initialGraph) {
        Map<Point, Set<Edge>> graph = new HashMap<>();

        for (Map.Entry<Point, Set<Edge>> edgeEntry : initialGraph.entrySet()) {
            if (edgeEntry.getValue().size() != 2) {
                Set<Edge> newEdges = new HashSet<>();

                for (Edge edge : edgeEntry.getValue()) {
                    int count = 1;

                    Point current = edge.point;
                    Point previous = edgeEntry.getKey();
                    while (initialGraph.get(current).size() == 2) {
                        count++;
                        for (Edge tmp : initialGraph.get(current)) {
                            if (!tmp.point.equals(previous)) {
                                previous = current;
                                current = tmp.point;
                                break;
                            }
                        }
                    }
                    newEdges.add(new Edge(current, count));
                }

                graph.put(edgeEntry.getKey(), newEdges);
            }

        }
        return graph;
    }

    private static int calculateLongestDistanceBetween(Point start, Map<Point, Set<Edge>> reducedGraph, Point end) {
        Set<Point> history = new HashSet<>();
        history.add(start);

        int maxPath = 0;
        ArrayDeque<State> queue = new ArrayDeque<>();
        queue.add(new State(start, 0, history));

        while (!queue.isEmpty()) {
            State polled = queue.poll();
            for (Edge edge : reducedGraph.getOrDefault(polled.point, new HashSet<>())) {
                int distance = edge.distance + polled.distance;
                if (edge.point.equals(end)) {
                    if (distance > maxPath) {
                        maxPath = distance;
                    }
                } else if (!polled.history.contains(edge.point)) {
                    Set<Point> newHistory = new HashSet<>(polled.history);
                    newHistory.add(edge.point);
                    queue.push(new State(edge.point, distance, newHistory));
                }
            }
        }

        return maxPath;
    }
}
