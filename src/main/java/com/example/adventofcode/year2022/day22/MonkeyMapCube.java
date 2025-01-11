package com.example.adventofcode.year2022.day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MonkeyMapCube {
    private static final String FILENAME = "AdventOfCodeData/2022day22/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2022day22/example_input";

    private static final Map<RegionDirection, RegionDirectionInstruction> REGION_DIRECTION_INSTRUCTION_MAP = Map.ofEntries(
            Map.entry(new RegionDirection(0, 2), new RegionDirectionInstruction(3, 0, true)),
            Map.entry(new RegionDirection(0, 3), new RegionDirectionInstruction(5, 0, false)),
            Map.entry(new RegionDirection(1, 0), new RegionDirectionInstruction(4, 2, true)),
            Map.entry(new RegionDirection(1, 1), new RegionDirectionInstruction(2, 2, false)),
            Map.entry(new RegionDirection(1, 3), new RegionDirectionInstruction(5, 3, false)),
            Map.entry(new RegionDirection(2, 0), new RegionDirectionInstruction(1, 3, false)),
            Map.entry(new RegionDirection(2, 2), new RegionDirectionInstruction(3, 1, false)),
            Map.entry(new RegionDirection(3, 2), new RegionDirectionInstruction(0, 0, true)),
            Map.entry(new RegionDirection(3, 3), new RegionDirectionInstruction(2, 0, false)),
            Map.entry(new RegionDirection(4, 0), new RegionDirectionInstruction(1, 2, true)),
            Map.entry(new RegionDirection(4, 1), new RegionDirectionInstruction(5, 2, false)),
            Map.entry(new RegionDirection(5, 0), new RegionDirectionInstruction(4, 3, false)),
            Map.entry(new RegionDirection(5, 1), new RegionDirectionInstruction(1, 1, false)),
            Map.entry(new RegionDirection(5, 2), new RegionDirectionInstruction(0, 1, false))
    );

    private static final Map<Integer, RegionDefinition> REGION_DEFINITION_MAP = Map.of(
            0, new RegionDefinition(50, 99, 0, 49),
            1, new RegionDefinition(100, 149, 0, 49),
            2, new RegionDefinition(50, 99, 50, 99),
            3, new RegionDefinition(0, 49, 100, 149),
            4, new RegionDefinition(50, 99, 100, 149),
            5, new RegionDefinition(0, 49, 150, 199)
    );

    public static void main(String[] args) throws IOException {
        System.out.println(calculateCubePassword(FILENAME));
    }

    public static int calculateCubePassword(String exampleFilename) throws IOException {
        Input exampleInput = readInput(exampleFilename);
        return calculateCubePassword(exampleInput);
    }

    private record RegionDirection(int region, int direction) {
    }

    private record RegionDirectionInstruction(int region, int direction, boolean translate) {
    }

    private record RegionDefinition(int columnBegin, int columnEnd, int rowBegin, int rowEnd) {
    }


    private static int getRegion(Point pointToEvaluate) {
        if (pointToEvaluate.column >= 50 && pointToEvaluate.column < 100 && pointToEvaluate.row >= 0 && pointToEvaluate.row < 50) {
            return 0;
        } else if (pointToEvaluate.column >= 100 && pointToEvaluate.column < 150 && pointToEvaluate.row >= 0 && pointToEvaluate.row < 50) {
            return 1;
        } else if (pointToEvaluate.column >= 50 && pointToEvaluate.column < 100 && pointToEvaluate.row >= 50 && pointToEvaluate.row < 100) {
            return 2;
        } else if (pointToEvaluate.column >= 0 && pointToEvaluate.column < 50 && pointToEvaluate.row >= 100 && pointToEvaluate.row < 150) {
            return 3;
        } else if (pointToEvaluate.column >= 50 && pointToEvaluate.column < 100 && pointToEvaluate.row >= 100 && pointToEvaluate.row < 150) {
            return 4;
        } else if (pointToEvaluate.column >= 0 && pointToEvaluate.column < 50 && pointToEvaluate.row >= 150 && pointToEvaluate.row < 200) {
            return 5;
        } else {
            throw new IllegalStateException();
        }
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

    private static int calculateCubePassword(Input input) {
        Point currentPosition = input.topLeft;

        int minRow = Integer.MAX_VALUE;
        int minColumn = Integer.MAX_VALUE;
        int maxRow = -1;
        int maxColumn = -1;
        for (Point mapPoint : input.map) {
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

        int facingPosition = 0;
        for (Move move : input.moves) {
            if (move.turn != null && move.turn == 'L') {
                facingPosition--;
                if (facingPosition < 0) {
                    facingPosition = 3;
                }
            } else if (move.turn != null && move.turn == 'R') {
                facingPosition++;
                if (facingPosition > 3) {
                    facingPosition = 0;
                }
            } else {
                for (int i = 0; i < move.steps; i++) {
                    Point changePositions;
                    if (facingPosition == 0) {
                        changePositions = new Point(0, 1);
                    } else if (facingPosition == 1) {
                        changePositions = new Point(1, 0);
                    } else if (facingPosition == 2) {
                        changePositions = new Point(0, -1);
                    } else {
                        changePositions = new Point(-1, 0);
                    }

                    int newRow = currentPosition.row + changePositions.row;
                    int newColumn = currentPosition.column + changePositions.column;

                    Point tmpPosition = new Point(newRow, newColumn);

                    if (input.walls.contains(tmpPosition)) {
                        continue;
                    } else if (input.map.contains(tmpPosition)
                            && newRow >= 0 && newRow <= maxRow
                            && newColumn >= 0 && newColumn <= maxColumn) {
                        currentPosition = tmpPosition;
                    } else {
                        PointAndDirection newState = wrapPosition(input, currentPosition, facingPosition);
                        currentPosition = newState.point;
                        facingPosition = newState.facingPosition;
                    }
                }
            }
        }

        return 1000 * (currentPosition.row + 1) + 4 * (currentPosition.column + 1) + facingPosition;
    }


    private record PointAndDirection(Point point, int facingPosition) {
    }

    private static PointAndDirection wrapPosition(Input input, Point currentPosition, int facingPosition) {
        int currentRegionId = getRegion(currentPosition);
        RegionDirectionInstruction newRegionDirection = REGION_DIRECTION_INSTRUCTION_MAP.get(new RegionDirection(currentRegionId, facingPosition));

        RegionDefinition currentRegionDefinition = REGION_DEFINITION_MAP.get(currentRegionId);
        RegionDefinition newRegionDefinition = REGION_DEFINITION_MAP.get(newRegionDirection.region);

        int diff;
        if (facingPosition == 0 || facingPosition == 2) {
            diff = currentPosition.row - currentRegionDefinition.rowBegin;
        } else {
            diff = currentPosition.column - currentRegionDefinition.columnBegin;
        }

        int newColumn = calculateNewColumn(newRegionDirection, newRegionDefinition, diff);
        int newRow = calculateNewRow(newRegionDirection, newRegionDefinition, diff);

        Point newPosition = new Point(newRow, newColumn);

        if (input.walls.contains(newPosition)) {
            return new PointAndDirection(currentPosition, facingPosition);
        }
        return new PointAndDirection(newPosition, newRegionDirection.direction);
    }

    private static int calculateNewRow(RegionDirectionInstruction newRegionDirection, RegionDefinition newRegionDefinition, int diff) {
        if (newRegionDirection.direction == 1) {
            return newRegionDefinition.rowBegin;
        }
        if (newRegionDirection.direction == 3) {
            return newRegionDefinition.rowEnd;
        }
        if (newRegionDirection.translate) {
            return newRegionDefinition.rowEnd - diff;
        }
        return newRegionDefinition.rowBegin + diff;
    }

    private static int calculateNewColumn(RegionDirectionInstruction newRegionDirection, RegionDefinition newRegionDefinition, int diff) {
        if (newRegionDirection.direction == 0) {
            return newRegionDefinition.columnBegin;
        }
        if (newRegionDirection.direction == 2) {
            return newRegionDefinition.columnEnd;
        }
        if (newRegionDirection.translate) {
            return newRegionDefinition.columnEnd - diff;
        }
        return newRegionDefinition.columnBegin + diff;
    }
}
