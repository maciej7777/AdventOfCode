package com.example.adventofcode.year2022.day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NotEnoughMinerals {
    private static final String FILENAME = "AdventOfCodeData/2022day19/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2022day19/example_input";

    public static void main(String[] args) throws IOException {
        List<Blueprint> input = readInput(FILENAME);
        List<Blueprint> exampleInput = readInput(EXAMPLE_FILENAME);

        System.out.println(calculateMaximumGeodesSum(exampleInput));
        System.out.println(calculateMaximumGeodesMultiplication(exampleInput));

        System.out.println(calculateMaximumGeodesSum(input));
        System.out.println(calculateMaximumGeodesMultiplication(input));

    }

    public static int obtainMaximumGeodesSum(String fileName) throws IOException {
        List<Blueprint> input = readInput(fileName);
        return calculateMaximumGeodesSum(input);
    }

    public static int obtainMaximumGeodesMultiplication(String fileName) throws IOException {
        List<Blueprint> input = readInput(fileName);
        return calculateMaximumGeodesMultiplication(input);
    }

    public record Blueprint(int id, int oreRobotCost, int clayRobotCost, int obsidianCostOre, int obsidianCostClay,
                            int geodeCostOre, int geodeCostObsidian) {
    }

    public static List<Blueprint> readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<Blueprint> map = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] elements = line.split("\\.");
                String[] split1 = elements[0].split(": Each ore robot costs ");
                int id = Integer.parseInt(split1[0].replace("Blueprint ", ""));
                int oreRobotCost = Integer.parseInt(split1[1].replace(" ore", ""));
                int clayRobotCost = Integer.parseInt(
                        elements[1].replace(" Each clay robot costs ", "")
                                .replace(" ore", "")
                );
                String[] split3 = elements[2].replace(" Each obsidian robot costs ", "")
                        .replace(" clay", "")
                        .split(" ore and ");
                int obsidianCostOre = Integer.parseInt(split3[0]);
                int obsidianCostClay = Integer.parseInt(split3[1]);

                String[] split4 = elements[3].replace(" Each geode robot costs ", "")
                        .replace(" obsidian", "")
                        .split(" ore and ");
                int geodeCostOre = Integer.parseInt(split4[0]);
                int geodeCostObsidian = Integer.parseInt(split4[1]);

                map.add(new Blueprint(id, oreRobotCost, clayRobotCost, obsidianCostOre, obsidianCostClay, geodeCostOre, geodeCostObsidian));
            }
            return map;
        }
    }

    public static int calculateMaximumGeodesSum(List<Blueprint> blueprints) {

        int blueprintSum = 0;
        int blueprintId = 0;

        for (Blueprint blueprint : blueprints) {
            blueprintId++;
            blueprintSum += blueprintId * calculateMaxGeodesFor(blueprint, new State());
        }

        return blueprintSum;
    }

    public static int calculateMaximumGeodesMultiplication(List<Blueprint> blueprints) {

        int blueprintSum = 1;
        int blueprintId = 0;

        for (Blueprint blueprint : blueprints) {
            blueprintId++;
            if (blueprintId > 3) {
                break;
            }
            blueprintSum *= calculateMaxGeodesFor(blueprint,
                    new State(0, 0, 0, 0,
                            1, 0, 0, 0,
                            32));
        }

        return blueprintSum;
    }

    public static class State {
        public int ore = 0;
        public int clay = 0;
        public int obsidian = 0;
        public int geode = 0;
        public int oreRobots = 1;
        public int clayRobots = 0;
        public int obsidianRobots = 0;
        public int geodeRobots = 0;
        public int time = 24;

        public State() {

        }

        public State(int ore, int clay, int obsidian,
                     int geode, int oreRobots, int clayRobots,
                     int obsidianRobots, int geodeRobots, int time) {
            this.ore = ore;
            this.clay = clay;
            this.obsidian = obsidian;
            this.geode = geode;
            this.oreRobots = oreRobots;
            this.clayRobots = clayRobots;
            this.obsidianRobots = obsidianRobots;
            this.geodeRobots = geodeRobots;
            this.time = time;
        }

        private State copy() {
            State tempState = new State();
            tempState.ore = this.ore;
            tempState.clay = this.clay;
            tempState.obsidian = this.obsidian;
            tempState.geode = this.geode;
            tempState.oreRobots = this.oreRobots;
            tempState.clayRobots = this.clayRobots;
            tempState.obsidianRobots = this.obsidianRobots;
            tempState.geodeRobots = this.geodeRobots;
            tempState.time = this.time;
            return tempState;
        }

        @Override
        public boolean equals(Object o) {
            // self check
            if (this == o)
                return true;
            // null check
            if (o == null)
                return false;
            // type check and cast
            if (getClass() != o.getClass())
                return false;
            State otherState = (State) o;
            // field comparison
            return Objects.equals(ore, otherState.ore)
                    && Objects.equals(clay, otherState.clay)
                    && Objects.equals(obsidian, otherState.obsidian)
                    && Objects.equals(geode, otherState.geode)
                    && Objects.equals(oreRobots, otherState.oreRobots)
                    && Objects.equals(obsidianRobots, otherState.obsidianRobots)
                    && Objects.equals(geodeRobots, otherState.geodeRobots)
                    && Objects.equals(clayRobots, otherState.clayRobots)
                    && Objects.equals(time, otherState.time);
        }

        @Override
        public int hashCode() {

            int hash = 7;
            hash = 31 * hash + ore;
            hash = 31 * hash + clay;
            hash = 31 * hash + obsidian;
            hash = 31 * hash + geode;
            hash = 31 * hash + oreRobots;
            hash = 31 * hash + clayRobots;
            hash = 31 * hash + obsidianRobots;
            hash = 31 * hash + geodeRobots;
            hash = 31 * hash + time;

            return hash;
        }
    }

    private static int calculateMaxGeodesFor(Blueprint blueprint,
                                             State beginningState) {

        Deque<State> toVisit = new ArrayDeque<>();
        toVisit.addLast(beginningState);
        Set<State> visited = new HashSet<>();

        int maxGeodes = 0;

        while (!toVisit.isEmpty()) {
            State tempState = toVisit.pollFirst().copy();

            if (tempState.geode > maxGeodes) {
                maxGeodes = tempState.geode;
            }

            if (tempState.time == 0) {
                continue;
            }

            Integer[] oreCosts = {blueprint.oreRobotCost, blueprint.clayRobotCost, blueprint.obsidianCostOre, blueprint.geodeCostOre};
            int maxOre = Arrays.stream(oreCosts).max(Integer::compareTo).orElse(0);
            if (tempState.oreRobots > maxOre) {
                tempState.oreRobots = maxOre;
            }
            if (tempState.clayRobots > blueprint.obsidianCostClay) {
                tempState.clayRobots = blueprint.obsidianCostClay;
            }
            if (tempState.obsidianRobots > blueprint.geodeCostObsidian) {
                tempState.obsidianRobots = blueprint.geodeCostObsidian;
            }

            if (tempState.ore > tempState.time * maxOre - tempState.oreRobots * (tempState.time - 1)) {
                tempState.ore = tempState.time * maxOre - tempState.oreRobots * (tempState.time - 1);
            }
            if (tempState.clay > tempState.time * blueprint.obsidianCostClay - tempState.clayRobots * (tempState.time - 1)) {
                tempState.clay = tempState.time * blueprint.obsidianCostClay - tempState.clayRobots * (tempState.time - 1);
            }
            if (tempState.obsidian > tempState.time * blueprint.geodeCostObsidian - tempState.obsidianRobots * (tempState.time - 1)) {
                tempState.obsidian = tempState.time * blueprint.geodeCostObsidian - tempState.obsidianRobots * (tempState.time - 1);
            }

            if (visited.contains(tempState)) {
                continue;
            }
            visited.add(tempState);

            if (tempState.ore >= blueprint.geodeCostOre
                    && tempState.obsidian >= blueprint.geodeCostObsidian) {
                toVisit.addLast(new State(
                        tempState.ore + tempState.oreRobots - blueprint.geodeCostOre,
                        tempState.clay + tempState.clayRobots,
                        tempState.obsidian + tempState.obsidianRobots - blueprint.geodeCostObsidian,
                        tempState.geode + tempState.geodeRobots,
                        tempState.oreRobots,
                        tempState.clayRobots,
                        tempState.obsidianRobots,
                        tempState.geodeRobots + 1,
                        tempState.time - 1
                ));
            } else {
                toVisit.addLast(new State(
                        tempState.ore + tempState.oreRobots,
                        tempState.clay + tempState.clayRobots,
                        tempState.obsidian + tempState.obsidianRobots,
                        tempState.geode + tempState.geodeRobots,
                        tempState.oreRobots,
                        tempState.clayRobots,
                        tempState.obsidianRobots,
                        tempState.geodeRobots,
                        tempState.time - 1
                ));

                if (tempState.ore >= blueprint.oreRobotCost) {
                    toVisit.addLast(new State(
                            tempState.ore + tempState.oreRobots - blueprint.oreRobotCost,
                            tempState.clay + tempState.clayRobots,
                            tempState.obsidian + tempState.obsidianRobots,
                            tempState.geode + tempState.geodeRobots,
                            tempState.oreRobots + 1,
                            tempState.clayRobots,
                            tempState.obsidianRobots,
                            tempState.geodeRobots,
                            tempState.time - 1
                    ));
                }
                if (tempState.ore >= blueprint.clayRobotCost) {
                    toVisit.addLast(new State(
                            tempState.ore + tempState.oreRobots - blueprint.clayRobotCost,
                            tempState.clay + tempState.clayRobots,
                            tempState.obsidian + tempState.obsidianRobots,
                            tempState.geode + tempState.geodeRobots,
                            tempState.oreRobots,
                            tempState.clayRobots + 1,
                            tempState.obsidianRobots,
                            tempState.geodeRobots,
                            tempState.time - 1
                    ));
                }
                if (tempState.ore >= blueprint.obsidianCostOre
                        && tempState.clay >= blueprint.obsidianCostClay) {
                    toVisit.addLast(new State(
                            tempState.ore + tempState.oreRobots - blueprint.obsidianCostOre,
                            tempState.clay + tempState.clayRobots - blueprint.obsidianCostClay,
                            tempState.obsidian + tempState.obsidianRobots,
                            tempState.geode + tempState.geodeRobots,
                            tempState.oreRobots,
                            tempState.clayRobots,
                            tempState.obsidianRobots + 1,
                            tempState.geodeRobots,
                            tempState.time - 1
                    ));
                }
            }
        }

        return maxGeodes;
    }
}
