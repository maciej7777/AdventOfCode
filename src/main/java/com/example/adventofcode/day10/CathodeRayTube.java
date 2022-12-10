package com.example.adventofcode.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CathodeRayTube {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day10/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day10/example_input";

    public static void main(String[] args) throws IOException {
        DrawingSum result = obtainSignalStrengthSumAndDrawings(FILENAME);
        DrawingSum exampleResult = obtainSignalStrengthSumAndDrawings(EXAMPLE_FILENAME);

        printResult(result, "Task");
        printResult(exampleResult, "Example");
    }

    private record ExecutionStep(String type, int positions) {
    }

    record DrawingSum(int signalStrengthSum, String drawing) {
    }

    public static DrawingSum obtainSignalStrengthSumAndDrawings(String inputPath) throws IOException {
        List<ExecutionStep> input = readExecutionPlan(inputPath);
        return calculateRegisterAndDrawings(input);
    }

    private static List<ExecutionStep> readExecutionPlan(String filename) throws IOException {
        List<ExecutionStep> executionSteps = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] linedElements = line.split(" ");
                if (linedElements.length == 2) {
                    executionSteps.add(new ExecutionStep(linedElements[0], Integer.parseInt(linedElements[1])));
                } else {
                    executionSteps.add(new ExecutionStep(linedElements[0], 0));
                }
            }
        }
        return executionSteps;
    }

    private static DrawingSum calculateRegisterAndDrawings(List<ExecutionStep> executionSteps) {
        int register = 1;
        int cycle = 0;
        int signalStrengthSum = 0;
        String drawing = "";

        for (ExecutionStep step: executionSteps) {
            cycle++;
            signalStrengthSum = checkCycle(register, cycle, signalStrengthSum);
            drawing = drawGraph(register, cycle, drawing);
            if (step.type.equals("addx")) {
                cycle++;
                signalStrengthSum = checkCycle(register, cycle, signalStrengthSum);
                drawing = drawGraph(register, cycle, drawing);
                register += step.positions;
            }
        }

        return new DrawingSum(signalStrengthSum, drawing);
    }

    private static int checkCycle(int index, int cycle, int indexSum) {
        if (cycle % 40 == 20) {
            indexSum += cycle * index;
        }
        return indexSum;
    }

    private static String drawGraph(int index, int cycle, String drawing) {
        if (Math.abs(index - ((cycle - 1) % 40)) <= 1) {
            drawing += "#";
        } else {
            drawing += ".";
        }
        return drawing;
    }

    private static void printResult(DrawingSum result, String type) {
        System.out.println(type + " result: ");
        System.out.println(result.signalStrengthSum);
        System.out.println(result.drawing.substring(0, 40));
        System.out.println(result.drawing.substring(40, 80));
        System.out.println(result.drawing.substring(80, 120));
        System.out.println(result.drawing.substring(120, 160));
        System.out.println(result.drawing.substring(160, 200));
        System.out.println(result.drawing.substring(200, 240));
    }
}
