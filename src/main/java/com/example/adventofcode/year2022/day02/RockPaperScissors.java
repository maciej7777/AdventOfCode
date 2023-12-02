package com.example.adventofcode.year2022.day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RockPaperScissors {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day02/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day02/example_input";


    private static final Map<String, String> figuresMap = Map.of("X", "A", "Y", "B", "Z", "C");

    private static final Map<String, String> loseMap = Map.of("A", "C", "B", "A", "C", "B");
    private static final Map<String, String> winsMap = Map.of("A", "B", "B", "C", "C", "A");


    public static void main(String[] args) throws IOException {
        System.out.println(obtainStrategyScoreForMoves(EXAMPLE_FILENAME));
        System.out.println(obtainStrategyScoreForMoves(FILENAME));

        System.out.println(obtainStrategyScoreForResults(EXAMPLE_FILENAME));
        System.out.println(obtainStrategyScoreForResults(FILENAME));
    }

    public static int obtainStrategyScoreForMoves(final String fileName) throws IOException {
        List<Strategy> input = readStrategy(fileName);
        return calculateTotalStrategyScoreForMoves(input);
    }

    public static int obtainStrategyScoreForResults(final String fileName) throws IOException {
        List<Strategy> input = readStrategy(fileName);
        return calculateTotalStrategyScoreForResults(input);
    }

    private record Strategy(String opponentsMove, String secondColumn) {
    }

    private static List<Strategy> readStrategy(final String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            List<Strategy> strategies = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] strategy = line.split(" ");
                strategies.add(new Strategy(strategy[0], strategy[1]));
            }

            return strategies;
        }
    }

    private static int calculateTotalStrategyScoreForMoves(final List<Strategy> moves) {
        int result = 0;
        for (Strategy move : moves) {
            String myMoveMapped = figuresMap.get(move.secondColumn);
            result += evaluateFigure(myMoveMapped);

            if (myMoveMapped.equals(move.opponentsMove)) {
                result += 3;
            } else if (winsMap.get(move.opponentsMove).equals(myMoveMapped)) {
                result += 6;
            }
        }

        return result;
    }

    private static int calculateTotalStrategyScoreForResults(final List<Strategy> moves) {
        int result = 0;
        for (Strategy move : moves) {
            switch (move.secondColumn) {
                case "X" -> {
                    result += evaluateFigure(loseMap.get(move.opponentsMove));
                }
                case "Y" -> {
                    result += 3;
                    result += evaluateFigure(move.opponentsMove);

                }
                case "Z" -> {
                    result += 6;
                    result += evaluateFigure(winsMap.get(move.opponentsMove));
                }
            }
        }

        return result;
    }

    private static int evaluateFigure(final String figure) {
        return switch (figure) {
            case "A" -> 1;
            case "B" -> 2;
            case "C" -> 3;
            default -> 0;
        };
    }
}
