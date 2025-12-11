package com.example.adventofcode.year2025.day11;

import java.io.IOException;
import java.util.*;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class Reactor {
    private static final String FILENAME = "AdventOfCodeData/2025/day11/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2025/day11/example_input";
    private static final String EXAMPLE_FILENAME2 = "AdventOfCodeData/2025/day11/example_input2";

    public static void main(String[] args) throws IOException {
        System.out.println(countWaysOut(EXAMPLE_FILENAME));
        System.out.println(countWaysOut(FILENAME));
        System.out.println(countWaysOutThroughNodesSubset(EXAMPLE_FILENAME2));
        System.out.println(countWaysOutThroughNodesSubset(FILENAME));
    }
    
    record NodeWithPaths(String next, List<String> path) {
    }
    
    record NodeCache(String next, boolean seenDac, boolean seenFft) {
    }
    
    public static long countWaysOut(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Map<String, List<String>> nodes = parseNodes(lines);

        Deque<NodeWithPaths> queue = new ArrayDeque<>();
        for (String node : nodes.get("you")) {
            queue.add(new NodeWithPaths(node, new ArrayList<>()));
        }

        int count = 0;
        while (!queue.isEmpty()) {
            NodeWithPaths current = queue.poll();
            current.path.add(current.next);

            for (String neighbour : nodes.get(current.next)) {
                if (neighbour.equals("out")) {
                    count++;
                } else if (!current.path.contains(neighbour)) {
                    queue.add(new NodeWithPaths(neighbour, new ArrayList<>(current.path)));
                }
            }
        }

        return count;
    }

    public static long countWaysOutThroughNodesSubset(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Map<String, List<String>> nodes = parseNodes(lines);

        return countWaysOutThroughNodesSubset(new NodeCache("svr", false, false), nodes, new HashMap<>());
    }

    private static Map<String, List<String>> parseNodes(List<String> lines) {
        Map<String, List<String>> nodes = new HashMap<>();
        for (String line : lines) {
            String[] targetingArray = line.split(":")[1].substring(1).split(" ");
            List<String> targeting = Arrays.asList(targetingArray);

            nodes.put(line.split(":")[0], targeting);
        }
        return nodes;
    }

    private static long countWaysOutThroughNodesSubset(NodeCache node, Map<String, List<String>> nodes, Map<NodeCache, Long> cache) {
        String nodeName = node.next;
        boolean seenDac = node.seenDac;
        boolean seenFft = node.seenFft;
        switch (nodeName) {
            case "out" -> {
                if (node.seenDac && node.seenFft) {
                    return 1;
                } else {
                    return 0;
                }
            }
            case "dac" -> seenDac = true;
            case "fft" -> seenFft = true;
        }

        long sumOut = 0;
        for (String nextNode : nodes.get(nodeName)) {
            sumOut += countWaysOutThroughNodesSubsetWithCache(new NodeCache(nextNode, seenDac, seenFft), nodes, cache);
        }
        return sumOut;
    }

    private static long countWaysOutThroughNodesSubsetWithCache(NodeCache node, Map<String, List<String>> nodes, Map<NodeCache, Long> cache) {
        if (cache.containsKey(node)) {
            return cache.get(node);
        }

        long result = countWaysOutThroughNodesSubset(node, nodes, cache);
        cache.put(node, result);
        return result;
    }
}
