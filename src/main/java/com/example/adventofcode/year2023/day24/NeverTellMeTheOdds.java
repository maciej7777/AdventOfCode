package com.example.adventofcode.year2023.day24;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeverTellMeTheOdds {
    private static final String FILENAME = "AdventOfCodeData/2023/day24/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day24/example_input";

    static long inputAreaBegin = 200000000000000L;
    static long inputAreaEnd = 400000000000000L;

    public static void main(String[] args) throws IOException {
        System.out.println(countXYLinesIntersectionsInArea(EXAMPLE_FILENAME, 7L, 27L));
        //2
        System.out.println(countXYLinesIntersectionsInArea(FILENAME, inputAreaBegin, inputAreaEnd));
        //18184
        System.out.println(obtainThrowingPositionCoordinatesSum(EXAMPLE_FILENAME));
        //47
        System.out.println(obtainThrowingPositionCoordinatesSum(FILENAME));
        //557789988450159
    }


    record Point(double x, double y) {
    }

    record Hailstone(long x, long y, long z, long vx, long vy, long vz) {
    }

    private static Point findIntersection(Hailstone hailstone1, Hailstone hailstone2) {
        double a1 = (double) hailstone1.vy / hailstone1.vx;
        double b1 = hailstone1.y - a1 * hailstone1.x;
        double a2 = (double) hailstone2.vy / hailstone2.vx;
        double b2 = hailstone2.y - a2 * hailstone2.x;

        double x = (b2 - b1) / (a1 - a2);
        double y = a1 * x + b1;

        return new Point(x, y);
    }

    public static int countXYLinesIntersectionsInArea(final String filename, long areaBegin, long areaEnd) throws IOException {
        List<String> lines = readLines(filename);
        List<Hailstone> hailstones = mapHailstones(lines);

        int intersectionsCount = 0;
        for (int i = 0; i < hailstones.size() - 1; i++) {
            Hailstone hailstone1 = hailstones.get(i);
            for (int j = i; j < hailstones.size(); j++) {
                Hailstone hailstone2 = hailstones.get(j);
                Point intersection = findIntersection(hailstone1, hailstone2);

                double t1 = (intersection.x - hailstone1.x) / hailstone1.vx;
                double t2 = (intersection.x - hailstone2.x) / hailstone2.vx;

                if (t1 >= 0 && t2 >= 0
                        && intersection.x >= areaBegin && intersection.x <= areaEnd
                        && intersection.y >= areaBegin && intersection.y <= areaEnd) {
                    intersectionsCount++;
                }
            }
        }
        return intersectionsCount;
    }

    // instead of the solver library you can also paste the equations to https://matheclipse.org/input
    public static long obtainThrowingPositionCoordinatesSum(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Hailstone> hailstones = mapHailstones(lines);

        String equations = buildSolverEquations(hailstones);
        Map<String, Long> resultMapped = solveEquations(equations);

        return resultMapped.get("x") + resultMapped.get("y") + resultMapped.get("z");
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

    private static List<Hailstone> mapHailstones(List<String> lines) {
        List<Hailstone> hailstones = new ArrayList<>();

        for (String line : lines) {
            String cleaned = line.replace(",", "").replace("@ ", "").replace("  ", " ");
            String[] splitted = cleaned.split(" ");
            hailstones.add(new Hailstone(Long.parseLong(splitted[0]), Long.parseLong(splitted[1]), Long.parseLong(splitted[2]),
                    Long.parseLong(splitted[3]), Long.parseLong(splitted[4]), Long.parseLong(splitted[5])));
        }
        return hailstones;
    }

    private static String buildSolverEquations(List<Hailstone> hailstones) {
        StringBuilder equations = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            String t = "t" + i;
            equations.append(t).append(">=0,").append(hailstones.get(i).x).append("+").append(hailstones.get(i).vx).append(t).append("==x + vx*").append(t).append(",");
            equations.append(hailstones.get(i).y).append("+").append(hailstones.get(i).vy).append(t).append("==y + vy*").append(t).append(",");
            equations.append(hailstones.get(i).z).append("+").append(hailstones.get(i).vz).append(t).append("==z + vz*").append(t).append(",");
        }
        return "Solve[{" + equations.substring(0, equations.length() - 1) + "},{x,y,z,vx,vy,vz,t0,t1,t2}]";
    }

    private static Map<String, Long> solveEquations(String equations) {
        ExprEvaluator util = new ExprEvaluator(false, (short)100);
        IExpr result = util.eval(equations);
        return mapResult(result);
    }

    private static Map<String, Long> mapResult(IExpr result) {
        Map<String, Long> resultMapped = new HashMap<>();

        String parsed = result.toString().replace("{", "").replace("}", "").replace("\n", "");
        String[] elements = parsed.split(",");

        for (String element : elements) {
            String[] tmp = element.split("->");
            resultMapped.put(tmp[0], Long.parseLong(tmp[1]));
        }
        return resultMapped;
    }
}
