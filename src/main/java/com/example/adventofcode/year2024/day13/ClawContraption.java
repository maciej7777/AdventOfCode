package com.example.adventofcode.year2024.day13;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClawContraption {
    private static final String FILENAME = "AdventOfCodeData/2024/day13/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2024/day13/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateMinimalNumberOfTokens(EXAMPLE_FILENAME, new Point(0L, 0L)));
        System.out.println(calculateMinimalNumberOfTokens(FILENAME, new Point(0L, 0L)));
        System.out.println(calculateMinimalNumberOfTokens(EXAMPLE_FILENAME, new Point(10000000000000L, 10000000000000L)));
        System.out.println(calculateMinimalNumberOfTokens(FILENAME, new Point(10000000000000L, 10000000000000L)));
    }


    public record Point(long x, long y) {
    }

    public record ClawMachineConfiguration(Point a, Point b, Point prize) {
    }

    public static long calculateMinimalNumberOfTokens(final String filename, Point prizePositionModifier) throws IOException {
        List<String> lines = readLines(filename);
        List<ClawMachineConfiguration> machines = parseMachineConfiguration(lines, prizePositionModifier);

        long sum = 0;
        for (ClawMachineConfiguration machine : machines) {
            String equations = buildSolverEquations(machine.a, machine.b, machine.prize);
            Map<String, Long> resultMap = solveEquations(equations);
            if (resultMap.size() == 2) {
                sum += 3 * resultMap.get("a") + resultMap.get("b");
            }
        }

        return sum;
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

    private static List<ClawMachineConfiguration> parseMachineConfiguration(List<String> inputLines, Point prizePositionModifier) {
        List<ClawMachineConfiguration> machines = new ArrayList<>();

        for (int i = 0; i < inputLines.size(); i += 4) {
            String[] aButtonData = inputLines.get(i).substring(12).split(", Y\\+");
            Point a = new Point(Integer.parseInt(aButtonData[0]), Integer.parseInt(aButtonData[1]));

            String[] bButtonData = inputLines.get(i + 1).substring(12).split(", Y\\+");
            Point b = new Point(Integer.parseInt(bButtonData[0]), Integer.parseInt(bButtonData[1]));

            String[] prizeData = inputLines.get(i + 2).substring(9).split(", Y=");
            Point prize = new Point(Integer.parseInt(prizeData[0]) + prizePositionModifier.x, Integer.parseInt(prizeData[1]) + prizePositionModifier.y);

            machines.add(new ClawMachineConfiguration(a, b, prize));
        }

        return machines;
    }

    private static String buildSolverEquations(Point a, Point b, Point prize) {
        StringBuilder equations = new StringBuilder();
        equations.append("a").append(">=0,").append("b").append(">=0,");
        equations.append(a.x).append("*").append("a").append("+").append(b.x).append("*").append("b").append("==").append(prize.x).append(",");
        equations.append(a.y).append("*").append("a").append("+").append(b.y).append("*").append("b").append("==").append(prize.y).append(",");
        return "Solve[{" + equations.substring(0, equations.length() - 1) + "},{a,b}]";
    }

    private static Map<String, Long> solveEquations(String equations) {
        ExprEvaluator util = new ExprEvaluator(false, (short) 100);
        IExpr result = util.eval(equations);
        return mapResult(result);
    }

    private static Map<String, Long> mapResult(IExpr result) {
        Map<String, Long> resultMapped = new HashMap<>();

        String parsed = result.toString().replace("{", "").replace("}", "").replace("\n", "");
        String[] elements = parsed.split(",");

        for (String element : elements) {
            String[] tmp = element.split("->");

            if (tmp.length == 2 && !tmp[1].contains("/")) {
                resultMapped.put(tmp[0], Long.parseLong(tmp[1]));
            }
        }
        return resultMapped;
    }
}
