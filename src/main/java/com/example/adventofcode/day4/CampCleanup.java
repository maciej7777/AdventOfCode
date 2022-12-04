package com.example.adventofcode.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CampCleanup {
    private static final String FILENAME = "src/main/java/com.example.adventofcode.day4/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com.example.adventofcode.day4/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(countFullyInclusiveIntervals(FILENAME));
        System.out.println(countIntervalsIntersections(FILENAME));
    }

    public static int countFullyInclusiveIntervals(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int numOfPairs = 0;
            while ((line = br.readLine()) != null) {
                List<CampSectionAssignment> assignments = parseCampSectionAssignments(line);
                if (isThePairInternallyIncluded(assignments)) {
                    numOfPairs++;
                }
            }

            return numOfPairs;
        }
    }

    public static int countIntervalsIntersections(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int numOfPairs = 0;
            while ((line = br.readLine()) != null) {
                List<CampSectionAssignment> assignments = parseCampSectionAssignments(line);
                if (isIntervalsIntersectionPresent(assignments)) {
                    numOfPairs++;
                }
            }

            return numOfPairs;
        }
    }

    private static List<CampSectionAssignment> parseCampSectionAssignments(String line) {
        String[] pairElements = line.split(",");
        String[] o1Values = pairElements[0].split("-");
        String[] o2Values = pairElements[1].split("-");

        List<CampSectionAssignment> assignments = new ArrayList<>();
        assignments.add(new CampSectionAssignment(Integer.parseInt(o1Values[0]), Integer.parseInt(o1Values[1])));
        assignments.add(new CampSectionAssignment(Integer.parseInt(o2Values[0]), Integer.parseInt(o2Values[1])));

        return assignments;
    }

    public record CampSectionAssignment(int beginSection, int endSection){}

    private static boolean isThePairInternallyIncluded(List<CampSectionAssignment> assignments) {
        sortAssignments(assignments);

        return assignments.get(0).endSection >= assignments.get(1).endSection;
    }

    private static void sortAssignments(List<CampSectionAssignment> assignments) {
        assignments.sort((o1, o2) -> {
            if (o1.beginSection == o2.beginSection) {
                return o2.endSection - o1.endSection;
            } else {
                return o1.beginSection - o2.beginSection;
            }
        });
    }

    private static boolean isIntervalsIntersectionPresent(List<CampSectionAssignment> assignments) {
        sortAssignments(assignments);

        return assignments.get(0).endSection >= assignments.get(1).beginSection;
    }
}
