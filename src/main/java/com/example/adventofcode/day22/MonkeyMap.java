package com.example.adventofcode.day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// TODO finish refactor
public class MonkeyMap {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day22/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day22/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculatePassword(EXAMPLE_FILENAME));
        System.out.println(calculatePassword(FILENAME));
    }

    public static int calculatePassword(String exampleFilename) throws IOException {
        Input exampleInput = readInput(exampleFilename);
        return calculatePassword(exampleInput);
    }


    public static Input readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            Set<Point> map = new HashSet<>();
            Set<Point> walls = new HashSet<>();
            List<Move> moves = new ArrayList<>();
            String inputKey = "";
            int lineNumber = 0;
            Point topLeft = null;

            while ((line = br.readLine()) != null) {
                if (line.contains(".") || line.contains("#")) {
                    String[] elements = line.split("");
                    for (int i = 0; i < elements.length; i++) {
                        if (elements[i].equals(".")) {
                            map.add(new Point(lineNumber, i));

                            if (topLeft == null) {
                                topLeft = new Point(lineNumber, i);
                            }
                        } else if (elements[i].equals("#")) {
                            walls.add(new Point(lineNumber, i));
                        }
                    }


                    lineNumber++;
                } else if (!line.strip().isBlank()) {
                    String number = "";
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == 'L' || line.charAt(i) == 'R') {
                            moves.add(new Move(Integer.parseInt(number), null));
                            number = "";
                            moves.add(new Move(null, line.charAt(i)));
                        } else {
                            number += line.charAt(i);
                        }
                    }
                    moves.add(new Move(Integer.parseInt(number), null));
                    inputKey = line;
                }
            }
            return new Input(map, walls, inputKey, topLeft, moves);
        }
    }

    private static record Move(Integer steps, Character turn) {
    }

    private static record Point(int row, int column) {
    }

    private static record Input(Set<Point> map, Set<Point> walls, String inputKey, Point topLeft, List<Move> moves) {
    }

    public static int calculatePassword(Input input) {
        Point currentPosition = input.topLeft;
        int facingPosition = 0;

        List<Integer> moves = new ArrayList<>();
        for (Move move : input.moves) {
            if (move.steps != null) {
                for (int i = 0; i < move.steps; i++) {
                    moves.add(facingPosition);
                }
            } else if (move.turn == 'L') {
                facingPosition--;
                if (facingPosition < 0) {
                    facingPosition = 3;
                }
            } else {
                facingPosition++;
                if (facingPosition > 3) {
                    facingPosition = 0;
                }
            }
        }

        int minRow = Integer.MAX_VALUE;
        int minColumn = Integer.MAX_VALUE;
        int maxRow = -1;
        int maxColumn = -1;
        for (Point mapPoint: input.map) {
            if (mapPoint.row < minRow) {
                minRow = mapPoint.row;
            }
            if (mapPoint.row > maxRow) {
                maxRow = mapPoint.row;
            }

            if (mapPoint.column < minColumn) {
                minColumn = mapPoint.column;
            }
            if (mapPoint.column > maxColumn) {
                maxColumn = mapPoint.column;
            }
        }


        for (Integer move : moves) {
            Point changePositions;
            if (move == 0) {
                changePositions = new Point(0, 1);
            } else if (move == 1) {
                changePositions = new Point(1, 0);
            } else if (move == 2) {
                changePositions = new Point(0, -1);
            } else {
                changePositions = new Point(-1, 0);
            }

            Integer newRow = (currentPosition.row + changePositions.row) % maxRow;
            Integer newColumn = currentPosition.column+ changePositions.column % maxColumn;

            if (newRow < 0) {
                newRow = maxRow;
            }
            if (newColumn < 0) {
                newColumn = maxColumn;
            }

            Point tmpPosition = new Point(newRow, newColumn);

            if (input.walls.contains(tmpPosition)) {
                continue;
            } else if (input.map.contains(tmpPosition)){
                currentPosition = tmpPosition;
            } else { // find the point at the other side of the map
                currentPosition = wrapPosition(input, currentPosition, maxRow, maxColumn, changePositions, tmpPosition);

            }
        }

        return 1000 * (currentPosition.row+1) + 4 * (currentPosition.column+1) + facingPosition;
    }

    private static Point wrapPosition(Input input, Point currentPosition, int maxRow, int maxColumn, Point changePositions, Point tmpPosition) {
        if (changePositions.column == 1) {
            for (int i = 0; i <= maxColumn; i++) {
                Point pointToTest = new Point(tmpPosition.row, i);
                if (input.walls.contains(pointToTest)) {
                    break;
                }
                if (input.map.contains(pointToTest)) {
                    currentPosition = pointToTest;
                    break;
                }
            }
        } else if (changePositions.column == -1) {
            for (int i = maxColumn; i >= 0; i--) {
                Point pointToTest = new Point(tmpPosition.row, i);
                if (input.walls.contains(pointToTest)) {
                    break;
                }
                if (input.map.contains(pointToTest)) {
                    currentPosition = pointToTest;
                    break;
                }
            }
        } else if (changePositions.row == 1) {
            for (int i = 0; i <= maxRow; i++) {
                Point pointToTest = new Point(i, tmpPosition.column);
                if (input.walls.contains(pointToTest)) {
                    break;
                }
                if (input.map.contains(pointToTest)) {
                    currentPosition = pointToTest;
                    break;
                }
            }
        } else if (changePositions.row == -1) {
            for (int i = maxRow; i >= 0; i--) {
                Point pointToTest = new Point(i, tmpPosition.column);
                if (input.walls.contains(pointToTest)) {
                    break;
                }
                if (input.map.contains(pointToTest)) {
                    currentPosition = pointToTest;
                    break;
                }
            }
        }
        return currentPosition;
    }
}
