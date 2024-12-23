package com.example.adventofcode.year2024.day23;

import org.jgrapht.Graph;
import org.jgrapht.alg.clique.BronKerboschCliqueFinder;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LANParty {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day23/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day23/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateInterConnectedComputersWithTElement(EXAMPLE_FILENAME));
        System.out.println(calculateInterConnectedComputersWithTElement(FILENAME));
        System.out.println(calculatePassword(EXAMPLE_FILENAME));
        System.out.println(calculatePassword(FILENAME));
    }

    public static long calculateInterConnectedComputersWithTElement(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Map<String, List<String>> graph = parseGraph(lines);

        int count = 0;
        List<String> nodes = graph.keySet().stream().toList();
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                for (int k = j + 1; k < nodes.size(); k++) {
                    String nodeI = nodes.get(i);
                    String nodeJ = nodes.get(j);
                    String nodeK = nodes.get(k);
                    if ((nodeI.startsWith("t") || nodeJ.startsWith("t") || nodeK.startsWith("t"))
                            && graph.get(nodeI).contains(nodeJ)
                            && graph.get(nodeJ).contains(nodeK)
                            && graph.get(nodeK).contains(nodeI)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public static String calculatePassword(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Graph<String, DefaultEdge> graph = generateUndirectedGraph(lines);

        BronKerboschCliqueFinder<String, DefaultEdge> cliqueAlghoritm = new BronKerboschCliqueFinder<>(graph);

        Collection<Set<String>> cliques = new HashSet<>();
        cliqueAlghoritm.maximumIterator().forEachRemaining(cliques::add);

        StringBuilder result = new StringBuilder();
        for (String element : cliques.stream().findFirst().get().stream().sorted().toList()) {
            result.append(element);
            result.append(",");
        }
        return result.substring(0, result.length() - 1);
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

    private static Map<String, List<String>> parseGraph(List<String> lines) {
        Map<String, List<String>> graph = new HashMap<>();

        for (String line : lines) {
            String[] nodes = line.split("-");

            List<String> node0 = graph.getOrDefault(nodes[0], new ArrayList<>());
            node0.add(nodes[1]);
            graph.put(nodes[0], node0);

            List<String> node1 = graph.getOrDefault(nodes[1], new ArrayList<>());
            node1.add(nodes[0]);
            graph.put(nodes[1], node1);
        }
        return graph;
    }

    private static SimpleGraph<String, DefaultEdge> generateUndirectedGraph(List<String> lines) {
        SimpleGraph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        for (String line : lines) {
            String[] nodes = line.split("-");
            graph.addVertex(nodes[0]);
            graph.addVertex(nodes[1]);

            graph.addEdge(nodes[0], nodes[1]);
            graph.addEdge(nodes[1], nodes[0]);
        }
        return graph;
    }
}
