package com.example.adventofcode.year2023.day25;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;
import org.jgrapht.alg.interfaces.MinimumSTCutAlgorithm;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class Snowverload {
    private static final String FILENAME = "AdventOfCodeData/2023/day25/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day25/example_input";
    private static final int EXPECTED_CUTS = 3;

    public static void main(String[] args) throws IOException {
        System.out.println(calculateMinWiresCutSets(EXAMPLE_FILENAME));
        System.out.println(calculateMinWiresCutSets(FILENAME));
    }


    record Edge(String x, String y) {
    }

    public static int calculateMinWiresCutSets(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Graph<String, DefaultWeightedEdge> graph = generateGraph(lines);

        MinimumSTCutAlgorithm<String, DefaultWeightedEdge> mc = executeCuts(graph, EXPECTED_CUTS);
        return mc.getSourcePartition().size() * mc.getSinkPartition().size();
    }

    private static Graph<String, DefaultWeightedEdge> generateGraph(List<String> lines) {
        List<Edge> edges = new ArrayList<>();
        Set<String> vertexes = new HashSet<>();

        for (String line : lines) {
            String[] splitted = line.split(": ");
            vertexes.add(splitted[0]);

            String[] elements = splitted[1].split(" ");
            for (String element : elements) {
                edges.add(new Edge(splitted[0], element));
                edges.add(new Edge(element, splitted[0]));
                vertexes.add(element);
            }
        }

        Graph<String, DefaultWeightedEdge> graph = new SimpleDirectedGraph<>(DefaultWeightedEdge.class);
        Graphs.addAllVertices(graph, vertexes);

        for (Edge edge: edges) {
            graph.addEdge(edge.x, edge.y);
        }
        return graph;
    }

    private static MinimumSTCutAlgorithm<String, DefaultWeightedEdge> executeCuts(Graph<String, DefaultWeightedEdge> graph, int expectedCuts) {
        MinimumSTCutAlgorithm<String, DefaultWeightedEdge> mc = new EdmondsKarpMFImpl<>(graph);

        for (String vertex1: graph.vertexSet()) {
            for (String vertex2: graph.vertexSet()) {
                if (!vertex1.equals(vertex2)) {
                    double cuts = mc.calculateMinCut(vertex1, vertex2);

                    if (cuts==expectedCuts) {
                        return mc;
                    }
                }
            }
        }
        return mc;
    }
}
