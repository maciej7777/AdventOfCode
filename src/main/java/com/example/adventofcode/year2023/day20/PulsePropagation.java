package com.example.adventofcode.year2023.day20;

import java.io.IOException;
import java.util.*;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class PulsePropagation {
    private static final String FILENAME = "AdventOfCodeData/2023/day20/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day20/example_input";
    private static final String EXAMPLE_FILENAME2 = "AdventOfCodeData/2023/day20/example_input2";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateLowAndHighPulsesProduct(EXAMPLE_FILENAME));
        System.out.println(calculateLowAndHighPulsesProduct(EXAMPLE_FILENAME2));
        System.out.println(calculateLowAndHighPulsesProduct(FILENAME));
        System.out.println(calculateMinButtonPushesForRx(FILENAME));
    }


    enum NodeType {
        BROADCASTING, FLIP_FLOP, CONJUNCTION
    }

    enum State {
        ON, OFF
    }

    enum Pulse {
        HIGH, LOW
    }

    record Signal(String from, String to, Pulse type) {
    }

    static class Node {
        String name;
        NodeType type;
        State state;
        List<String> connected;

        Map<String, Pulse> incoming = new HashMap<>();
        Pulse recentPulse = Pulse.LOW;

        public Node(String name, NodeType type, State state, List<String> connected) {
            this.name = name;
            this.type = type;
            this.state = state;
            this.connected = connected;
        }
    }

    public static long calculateLowAndHighPulsesProduct(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Map<String, Node> nodes = createNodes(lines);

        updateConjunctionLastSignals(nodes);

        Queue<Signal> signalsQueue = new ArrayDeque<>();
        int low = 0;
        int high = 0;

        for (int i = 0; i < 1000; i++) {
            signalsQueue.add(new Signal("button", "broadcaster", Pulse.LOW));

            while (!signalsQueue.isEmpty()) {
                Signal polled = signalsQueue.poll();

                if (polled.type == Pulse.LOW) {
                    low++;
                } else {
                    high++;
                }

                if (!nodes.containsKey(polled.to)) {
                    continue;
                }

                updateNodes(nodes, polled, signalsQueue);
            }
        }
        return (long) low * high;
    }

    public static long calculateMinButtonPushesForRx(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Map<String, Node> nodes = createNodes(lines);
        updateConjunctionLastSignals(nodes);

        String nodesBeforeRx = getNodeBeforeRx(nodes);
        List<String> cycleNodes = getCycleNodes(nodes, nodesBeforeRx);

        Map<String, Long> cycleLength = initializeCycleLengths(cycleNodes);

        Map<String, Long> visitedNodes = new HashMap<>();
        List<Long> cycleCounts = new ArrayList<>();
        Queue<Signal> signalsQueue = new ArrayDeque<>();

        int buttonPushes = 1;
        while (cycleNodes.size() != cycleCounts.size()) {
            signalsQueue.add(new Signal("button", "broadcaster", Pulse.LOW));

            while (!signalsQueue.isEmpty()) {
                Signal polled = signalsQueue.poll();

                if (!nodes.containsKey(polled.to)) {
                    continue;
                }

                if (polled.type == Pulse.LOW && cycleNodes.contains(polled.to)) {
                    if (visitedNodes.containsKey(polled.to)
                            && cycleLength.get(polled.to) == 2) {
                        cycleCounts.add(buttonPushes - visitedNodes.get(polled.to));
                    }
                    cycleLength.put(polled.to, cycleLength.get(polled.to)+1);
                    visitedNodes.put(polled.to, (long) buttonPushes);
                }

                updateNodes(nodes, polled, signalsQueue);
            }
            buttonPushes++;
        }
        return lcm(cycleCounts);
    }

    private static Map<String, Node> createNodes(List<String> lines) {
        Map<String, Node> nodes = new HashMap<>();
        for (String line : lines) {
            String[] splitted = line.split(" -> ");
            String[] connectedNodesArray = splitted[1].split(", ");
            List<String> connectedNodes = Arrays.asList(connectedNodesArray);

            if (line.startsWith("%")) {
                String name = splitted[0].substring(1);
                nodes.put(name, new Node(name, NodeType.FLIP_FLOP, State.OFF, connectedNodes));
            } else if (line.startsWith("&")) {
                String name = splitted[0].substring(1);
                nodes.put(name, new Node(name, NodeType.CONJUNCTION, State.OFF, connectedNodes));
            } else {
                nodes.put(splitted[0], new Node(splitted[0], NodeType.BROADCASTING, State.OFF, connectedNodes));
            }
        }
        return nodes;
    }

    private static void updateConjunctionLastSignals(Map<String, Node> nodes) {
        for (Map.Entry<String, Node> node : nodes.entrySet()) {
            for (String next : node.getValue().connected) {
                Node nextNode = nodes.get(next);
                if (nextNode != null && nextNode.type == NodeType.CONJUNCTION) {
                    nextNode.incoming.put(node.getKey(), Pulse.LOW);
                }
            }
        }
    }

    private static void updateNodes(Map<String, Node> nodes, Signal polled, Queue<Signal> signalsQueue) {
        Node current = nodes.get(polled.to);
        if (current.type == NodeType.BROADCASTING) {
            for (String node : current.connected) {
                signalsQueue.add(new Signal(polled.to, node, polled.type));
            }
        } else if (current.type == NodeType.FLIP_FLOP) {
            if (polled.type != Pulse.HIGH) {
                Pulse newType;
                if (current.state != State.ON) {
                    current.state = State.ON;
                    newType = Pulse.HIGH;
                } else {
                    current.state = State.OFF;
                    newType = Pulse.LOW;
                }
                for (String node : current.connected) {
                    signalsQueue.add(new Signal(polled.to, node, newType));
                }
            }
        } else {
            // type == CONJUNCTION
            current.incoming.put(polled.from, polled.type);
            Pulse newType;

            if (areAllIncomingSignalsHigh(current)) {
                newType = Pulse.LOW;
            } else {
                newType = Pulse.HIGH;
            }

            for (String node : current.connected) {
                signalsQueue.add(new Signal(polled.to, node, newType));
            }
        }
    }

    private static boolean areAllIncomingSignalsHigh(Node current) {
        for (Map.Entry<String, Pulse> incoming : current.incoming.entrySet()) {
            if (incoming.getValue() == Pulse.LOW) {
                return false;
            }
        }
        return true;
    }

    private static String getNodeBeforeRx(Map<String, Node> nodes) {
        for (Map.Entry<String, Node> node : nodes.entrySet()) {
            for (String next : node.getValue().connected) {
                if (next.equals("rx")) {
                    return node.getKey();
                }
            }
        }
        return null;
    }

    private static List<String> getCycleNodes(Map<String, Node> nodes, String nodesBeforeRx) {
        List<String> cycleNodes = new ArrayList<>();
        for (Map.Entry<String, Node> node : nodes.entrySet()) {
            for (String next : node.getValue().connected) {
                if (next.equals(nodesBeforeRx)) {
                    cycleNodes.add(node.getKey());
                }
            }
        }
        return cycleNodes;
    }

    private static Map<String, Long> initializeCycleLengths(List<String> cycleNodes) {
        Map<String, Long> cycleLength = new HashMap<>();
        for (String cycleNode: cycleNodes) {
            cycleLength.put(cycleNode, 0L);
        }
        return cycleLength;
    }

    private static long lcm(List<Long> numbers) {
        long sum = 1;
        for (Long number: numbers) {
            sum = Math.floorDiv(sum*number, gcdByEuclidsAlgorithm(number, sum));
        }
        return sum;
    }

    private static long gcdByEuclidsAlgorithm(long n1, long n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcdByEuclidsAlgorithm(n2, n1 % n2);
    }
}
