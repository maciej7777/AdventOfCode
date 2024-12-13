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
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day13/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day13/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateMinimalNumberOfTokens(EXAMPLE_FILENAME, new Point(0L, 0L)));
        System.out.println(calculateMinimalNumberOfTokens(FILENAME, new Point(0L, 0L)));
        System.out.println(calculateMinimalNumberOfTokens(EXAMPLE_FILENAME, new Point(10000000000000L, 10000000000000L)));
        System.out.println(calculateMinimalNumberOfTokens(FILENAME, new Point(10000000000000L, 10000000000000L)));
    }


    record Point(long x, long y) {
    }

    public static long calculateMinimalNumberOfTokens(final String filename, Point prizePositionModifier) throws IOException {
        List<String> lines = readLines(filename);
        long sum = 0;
        Point a = new Point(0, 0);
        Point b = new Point(0, 0);
        Point prize;
        for (String line : lines) {
            if (line.startsWith("Button A: ")) {
                String newLine = line.substring(12);
                String[] numbers = newLine.split(", Y\\+");

                a = new Point(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
            } else if (line.startsWith("Button B: ")) {
                String newLine = line.substring(12);
                String[] numbers = newLine.split(", Y\\+");

                b = new Point(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
            } else if (line.startsWith("Prize: X=")) {
                String newLine = line.substring(9);
                String[] numbers = newLine.split(", Y=");

                prize = new Point(Integer.parseInt(numbers[0]) + prizePositionModifier.x, Integer.parseInt(numbers[1]) + prizePositionModifier.y);

                String equations = buildSolverEquations(a, b, prize);
                Map<String, Long> resultMap = solveEquations(equations);
                if (resultMap.size() == 2) {
                    sum += 3 * resultMap.get("a") + resultMap.get("b");
                }
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
