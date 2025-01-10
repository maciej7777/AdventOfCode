package com.example.adventofcode.year2023.day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HauntedWasteland {
    private static final String FILENAME = "AdventOfCodeData/2023/day08/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day08/example_input";

    private static final String EXAMPLE_FILENAME2 = "AdventOfCodeData/2023/day08/example_input2";

    private static final String EXAMPLE_FILENAME3 = "AdventOfCodeData/2023/day08/example_input3";


    public static void main(String[] args) throws IOException {
        System.out.println(calculateStepsToReachZZZ(EXAMPLE_FILENAME));
        System.out.println(calculateStepsToReachZZZ(EXAMPLE_FILENAME2));
        System.out.println(calculateStepsToReachZZZ(FILENAME));
        System.out.println(calculateStepsToReachZNodesByGhosts(EXAMPLE_FILENAME3));
        System.out.println(calculateStepsToReachZNodesByGhosts(FILENAME));
    }

    private record TreeNode(String current, String left, String right) {
    }

    private record Input(String instructions, Map<String, TreeNode> nodes) {
    }

    public static Long calculateStepsToReachZZZ(final String filename) throws IOException {
        List<String> inputLines = readLines(filename);
        Input input = mapInput(inputLines);

        return calculateStepsFor(input, "AAA", "ZZZ");
    }

    public static Long calculateStepsToReachZNodesByGhosts(final String filename) throws IOException {
        List<String> inputLines = readLines(filename);
        Input input = mapInput(inputLines);
        List<String> aNodes = findANodes(input);

        List<Long> distances = new ArrayList<>();
        for (String node : aNodes) {
            distances.add(calculateStepsFor(input, node, "Z"));
        }
        return calculateLcmOfArrayElements(distances.toArray(new Long[0]));
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

    private static Input mapInput(List<String> inputLines) {
        String instructions = inputLines.getFirst();

        Map<String, TreeNode> elementsMap = new HashMap<>();
        for (String line : inputLines.subList(1, inputLines.size())) {

            if (!line.isEmpty()) {
                line = line.replace("= (", "").replace(",", "").replace(")", "");
                String[] splitted = line.split(" ");
                elementsMap.put(splitted[0], new TreeNode(splitted[0], splitted[1], splitted[2]));
            }
        }
        return new Input(instructions, elementsMap);
    }

    private static List<String> findANodes(Input input) {
        List<String> aNodes = new ArrayList<>();
        for (String node : input.nodes.keySet()) {
            if (node.endsWith("A")) {
                aNodes.add(node);
            }
        }
        return aNodes;
    }

    private static long calculateStepsFor(Input input, String startElement, String destinationNodeSuffix) {
        long instructionId = 0;
        String currentElement = startElement;
        while (!currentElement.endsWith(destinationNodeSuffix)) {
            TreeNode currentNode = input.nodes.get(currentElement);
            char instruction = input.instructions.charAt((int) (instructionId % input.instructions.length()));
            instructionId++;

            if (instruction == 'L') {
                currentElement = currentNode.left;
            } else {
                currentElement = currentNode.right;
            }
        }

        return instructionId;
    }

    private static long calculateLcmOfArrayElements(Long[] elements) {
        long lcmOfArrayElements = 1;
        int divisor = 2;

        while (true) {
            int counter = 0;
            boolean divisible = false;

            for (int i = 0; i < elements.length; i++) {
                if (elements[i] == 0) {
                    return 0;
                } else if (elements[i] < 0) {
                    elements[i] = elements[i] * (-1);
                }
                if (elements[i] == 1) {
                    counter++;
                }

                if (elements[i] % divisor == 0) {
                    divisible = true;
                    elements[i] = elements[i] / divisor;
                }
            }

            if (divisible) {
                lcmOfArrayElements = lcmOfArrayElements * divisor;
            } else {
                divisor++;
            }

            if (counter == elements.length) {
                return lcmOfArrayElements;
            }
        }
    }
}
