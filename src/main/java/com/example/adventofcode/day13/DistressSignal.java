package com.example.adventofcode.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static com.example.adventofcode.day13.DistressSignal.PacketType.INT;
import static com.example.adventofcode.day13.DistressSignal.PacketType.LIST;

public class DistressSignal {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day13/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day13/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println("Sum: " + obtainSumOfOrderedPairs(FILENAME));
        System.out.println("Multiplication: " + obtainExpectedMultipliedPositions(FILENAME));
    }

    enum PacketType {
        INT, LIST;
    }

    static class Packet {
        List<Packet> elements = new ArrayList<>();
        Integer number;
        PacketType type;

        Packet(PacketType type) {
            this.type = type;
        }
    }

    public static int obtainExpectedMultipliedPositions(String filename) throws IOException {
        List<Packet> objects = readLines(filename);
        addRequestedObjects(objects);
        return calculateExpectedMultipliedPositions(objects);
    }

    private static List<Packet> readLines(String filename) throws IOException {
        List<Packet> objects = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    Packet obj1 = parseLine(line);
                    objects.add(obj1);
                }
            }
        }
        return objects;
    }

    private static void addRequestedObjects(List<Packet> objects) {
        objects.add(createNewIntOrList(2));
        objects.add(createNewIntOrList(6));
    }

    private static int calculateExpectedMultipliedPositions(List<Packet> objects) {
        int result = 1;

        objects.sort(DistressSignal::compareElements);
        for (int i = 0; i < objects.size(); i++) {
            if (isDoubleListWithSingleNumber(objects.get(i), 2)
                    || isDoubleListWithSingleNumber(objects.get(i), 6)) {
                result *= (i + 1);
            }
        }
        return result;
    }

    private static boolean isDoubleListWithSingleNumber(Packet object, int number) {
        return object.type.equals(LIST)
                && object.elements.size() == 1
                && object.elements.get(0).type.equals(LIST)
                && object.elements.get(0).elements.size() == 1
                && object.elements.get(0).elements.get(0).type.equals(INT)
                && object.elements.get(0).elements.get(0).number.equals(number);
    }

    private static Packet createNewIntOrList(int i2) {
        Packet packet = new Packet(LIST);
        Packet packetNested = new Packet(LIST);
        Packet packetNestedNumber = new Packet(INT);

        packet.elements.add(packetNested);
        packetNested.elements.add(packetNestedNumber);
        packetNestedNumber.number = i2;
        return packet;
    }

    private record Pair<T>(T first, T second) {
    }

    public static int obtainSumOfOrderedPairs(String fileName) throws IOException {
        List<Pair<Packet>> linePairs = readLinePairs(fileName);
        return calculateSumOfOrderedPairs(linePairs);
    }

    private static List<Pair<Packet>> readLinePairs(String filename) throws IOException {
        List<Pair<Packet>> linePairs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                Packet line1 = parseLine(line);
                line = br.readLine();
                Packet line2 = parseLine(line);
                linePairs.add(new Pair<>(line1, line2));

                br.readLine();
            }
        }
        return linePairs;
    }

    private static int calculateSumOfOrderedPairs(List<Pair<Packet>> linePairs) {
        int sum = 0;
        int pairNumber = 1;
        for (Pair<Packet> linePair: linePairs) {
            int result = compareElements(linePair.first, linePair.second);
            if (result != 1) {
                sum += pairNumber;
            }
            pairNumber++;
        }

        return sum;
    }

    public static Packet parseLine(String line) {
        Deque<Packet> currentList = new ArrayDeque<>();
        Packet top = new Packet(LIST);
        currentList.addFirst(top);
        String[] elements = line.substring(1).split("");

        String currentNumber = "";
        for (String element : elements) {
            if (element.equals("[")) {
                Packet current = currentList.peek();

                Packet toAdd = new Packet(LIST);
                currentList.addFirst(toAdd);

                current.elements.add(toAdd);
            } else if (element.equals("]")) {
                if (!currentNumber.equals("")) {
                    addItem(currentList.peek(), currentNumber);
                }
                currentNumber = "";
                currentList.pop();
            } else if (element.equals(",")) {
                if (!currentNumber.equals("")) {
                    addItem(currentList.peek(), currentNumber);
                }
                currentNumber = "";
            } else if (isNumeric(element)) {
                currentNumber += element;
            }
        }
        return top;
    }

    private static void addItem(Packet current, String element) {
        Packet toAdd = new Packet(INT);
        toAdd.number = Integer.parseInt(element);

        current.elements.add(toAdd);
    }

    public static boolean isNumeric(String strNum) {
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static int compareElements(Packet el1, Packet el2) {
        int minSize = Math.min(el1.elements.size(), el2.elements.size());

        if (el1.type.equals(LIST) && el2.type.equals(LIST)) {
            return compareLists(el1, el2, minSize);
        } else if (el1.type.equals(INT) && el2.type.equals(INT)) {
            return Integer.compare(el1.number, el2.number);
        } else if (el1.type.equals(LIST) && el2.type.equals(INT)) {
            return compareListWithInteger(el1, el2);
        } else {
            return compareIntegerWithLists(el1, el2);
        }
    }

    private static int compareLists(Packet el1, Packet el2, int minSize) {
        if (el1.elements.isEmpty() && el2.elements.isEmpty()) {
            return 0;
        } else if (el1.elements.isEmpty()) {
            return -1;
        } else if (el2.elements.isEmpty()) {
            return 1;
        }

        for (int i = 0; i < minSize; i++) {
            int comparisonResult = compareElements(el1.elements.get(i), el2.elements.get(i));
            if (comparisonResult != 0) {
                return comparisonResult;
            }
        }

        return Integer.compare(el1.elements.size(), el2.elements.size());
    }

    private static int compareListWithInteger(Packet el1, Packet el2) {
        if (el1.elements.isEmpty()) {
            return -1;
        }

        int comparisonResult = compareElements(el1.elements.get(0), el2);
        if (comparisonResult != 0) {
            return comparisonResult;
        }

        if (el1.elements.size() > 1) {
            return 1;
        }
        return 0;
    }

    private static int compareIntegerWithLists(Packet el1, Packet el2) {
        if (el2.elements.isEmpty()) {
            return 1;
        }

        int comparisonResult = compareElements(el1, el2.elements.get(0));
        if (comparisonResult != 0) {
            return comparisonResult;
        }

        if (el2.elements.size() > 1) {
            return -1;
        }
        return 0;
    }
}
