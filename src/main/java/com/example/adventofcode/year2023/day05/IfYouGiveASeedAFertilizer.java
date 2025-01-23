package com.example.adventofcode.year2023.day05;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class IfYouGiveASeedAFertilizer {
    private static final String FILENAME = "AdventOfCodeData/2023/day05/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day05/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateLowestLocationNumber(EXAMPLE_FILENAME));
        //35
        System.out.println(calculateLowestLocationNumber(FILENAME));
        //650599855
        System.out.println(calculateLowestLocationNumberFromRanges(EXAMPLE_FILENAME));
        //46
        System.out.println(calculateLowestLocationNumberFromRanges(FILENAME));
        //1240035
    }

    public static long calculateLowestLocationNumber(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        return findLowestLocationNumber(lines);
    }

    public static long calculateLowestLocationNumberFromRanges(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        return findLowestLocationNumberFromRanges(lines);
    }

    private static long findLowestLocationNumber(final List<String> lines) {
        List<Long> currentNumbers = new ArrayList<>();
        List<Long> alreadyMapped = new ArrayList<>();
        List<Long> newNumbers = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("seeds:")) {
                line = line.substring(7);
                String[] splitted = line.split(" ");

                for (String seed : splitted) {
                    newNumbers.add(Long.parseLong(seed));
                }
            } else if (line.isEmpty()) {
                for (Long number : currentNumbers) {
                    if (!alreadyMapped.contains(number)) {
                        newNumbers.add(number);
                    }
                }
                currentNumbers = newNumbers;
                alreadyMapped = new ArrayList<>();
                newNumbers = new ArrayList<>();
            } else if (!line.contains(":")) {
                String[] lineElements = line.split(" ");
                long destinationRangeStart = Long.parseLong(lineElements[0]);
                Long sourceRangeStart = Long.parseLong(lineElements[1]);
                Long rangeLength = Long.parseLong(lineElements[2]);

                for (Long currentNumber : currentNumbers) {
                    if (currentNumber >= sourceRangeStart
                            && currentNumber <= sourceRangeStart + rangeLength) {
                        alreadyMapped.add(currentNumber);
                        newNumbers.add(destinationRangeStart + (currentNumber - sourceRangeStart));
                    }
                }
            }
        }


        for (Long number : currentNumbers) {
            if (!alreadyMapped.contains(number)) {
                newNumbers.add(number);
            }
        }
        return getMin(newNumbers);
    }

    private static Long getMin(List<Long> newNumbers) {
        Long min = Long.MAX_VALUE;
        for (Long number : newNumbers) {
            if (number < min) {
                min = number;
            }
        }
        return min;
    }

    record Range(long first, long last) {
    }

    private static long findLowestLocationNumberFromRanges(final List<String> lines) {
        List<Range> currentNumbers = new ArrayList<>();
        List<Range> alreadyMapped = new ArrayList<>();
        List<Range> newNumbers = new ArrayList<>();
        for (String line : lines) {

            if (line.startsWith("seeds:")) {
                line = line.substring(7);
                String[] splitted = line.split(" ");

                for (int i = 0; i < splitted.length; i += 2) {
                    long first = Long.parseLong(splitted[i]);
                    long length = Long.parseLong(splitted[i + 1]);

                    newNumbers.add(new Range(first, first + length));
                }
            } else if (line.isEmpty()) {
                for (Range number : currentNumbers) {
                    if (!alreadyMapped.contains(number)) {
                        newNumbers.add(number);
                    }
                }
                currentNumbers = newNumbers;
                alreadyMapped = new ArrayList<>();
                newNumbers = new ArrayList<>();
            } else if (!line.contains(":")) {
                String[] lineElements = line.split(" ");
                Long destinationRangeStart = Long.parseLong(lineElements[0]);
                Long sourceRangeStart = Long.parseLong(lineElements[1]);
                Long rangeLength = Long.parseLong(lineElements[2]);

                Range mappingRange = new Range(sourceRangeStart, sourceRangeStart + rangeLength);
                for (Range currentNumber : currentNumbers) {
                    if (isOverlapping(currentNumber, mappingRange)) {
                        alreadyMapped.add(currentNumber);

                        long mappingFactor = destinationRangeStart - sourceRangeStart;

                        if (currentNumber.first >= mappingRange.first
                                && currentNumber.last <= mappingRange.last) {
                            long firstRangeStart = currentNumber.first + mappingFactor;
                            long newRangeEnd = currentNumber.last + mappingFactor;
                            newNumbers.add(new Range(firstRangeStart, newRangeEnd));
                        } else if (currentNumber.first < mappingRange.first
                                && currentNumber.last > mappingRange.last) {
                            long firstRangeStart = currentNumber.first;
                            long firstRangeEnd = mappingRange.first - 1;
                            long newRangeStart = mappingRange.first + mappingFactor;
                            long newRangeEnd = mappingRange.last + mappingFactor;
                            long lastRangeStart = mappingRange.last + 1;
                            long lastRangeEnd = currentNumber.last;
                            newNumbers.add(new Range(firstRangeStart, firstRangeEnd));
                            newNumbers.add(new Range(newRangeStart, newRangeEnd));
                            newNumbers.add(new Range(lastRangeStart, lastRangeEnd));
                        } else if (currentNumber.first < mappingRange.first) {
                            long firstRangeStart = currentNumber.first;
                            long firstRangeEnd = mappingRange.first - 1;
                            long newRangeStart = mappingRange.first + mappingFactor;
                            long newRangeEnd = currentNumber.last + mappingFactor;
                            newNumbers.add(new Range(firstRangeStart, firstRangeEnd));
                            newNumbers.add(new Range(newRangeStart, newRangeEnd));
                        } else {
                            long firstRangeStart = currentNumber.first + mappingFactor;
                            long firstRangeEnd = mappingRange.last - 1 + mappingFactor;
                            long newRangeStart = mappingRange.last;
                            long newRangeEnd = currentNumber.last;
                            newNumbers.add(new Range(firstRangeStart, firstRangeEnd));
                            newNumbers.add(new Range(newRangeStart, newRangeEnd));
                        }
                    }
                }
            }
        }
        for (Range number : currentNumbers) {
            if (!alreadyMapped.contains(number)) {
                newNumbers.add(number);
            }
        }
        long min = Long.MAX_VALUE;
        for (Range range : newNumbers) {
            long current = range.first;
            if (current < min && current > 0) {
                min = current;
            }
        }
        return min;
    }

    private static boolean isOverlapping(Range range1, Range range2) {
        return !(range1.last < range2.first || range2.last < range1.first);
    }
}