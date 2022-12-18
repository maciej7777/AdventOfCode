package com.example.adventofcode.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BoilingBoulders {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day18/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day18/example_input";

    public static void main(String[] args) throws IOException {
        List<CubePosition> input = readInput(FILENAME);
        List<CubePosition> exampleInput = readInput(EXAMPLE_FILENAME);

        System.out.println(calculateSurfaceArea(input));
        System.out.println(calculateSurfaceArea(exampleInput));

        System.out.println(calculateExteriorSurfaceArea(exampleInput));
        System.out.println(calculateExteriorSurfaceArea(input));

    }

    public static int obtainSurfaceArea(String fileName) throws IOException {
        List<CubePosition> input = readInput(fileName);
        return calculateSurfaceArea(input);
    }

    public static int obtainExteriorSurfaceArea(String fileName) throws IOException {
        List<CubePosition> input = readInput(fileName);
        return calculateExteriorSurfaceArea(input);
    }

    record CubePosition(int x, int y, int z) {
    }

    public static List<CubePosition> readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<CubePosition> map = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                CubePosition cube = new CubePosition(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
                map.add(cube);
            }
            return map;
        }
    }

    public static int calculateSurfaceArea(List<CubePosition> cubes) {
        int positions = 0;
        for (CubePosition cube : cubes) {
            int tmpPositions = 6;
            for (CubePosition otherCube : cubes) {
                if (cube != otherCube) {
                    if ((cube.x == otherCube.x) && (cube.y == otherCube.y)
                            && (Math.abs(cube.z - otherCube.z) <= 1)) {
                        tmpPositions--;
                    }
                    if ((cube.x == otherCube.x) && (cube.z == otherCube.z)
                            && (Math.abs(cube.y - otherCube.y) <= 1)) {
                        tmpPositions--;
                    }
                    if ((cube.y == otherCube.y) && (cube.z == otherCube.z)
                            && (Math.abs(cube.x - otherCube.x) <= 1)) {
                        tmpPositions--;
                    }
                }
            }
            positions += tmpPositions;
        }
        return positions;
    }

    public static int calculateExteriorSurfaceArea(List<CubePosition> cubes) {
        int positions = 0;

        List<CubePosition> internalAir = countInternalAir(cubes);

        for (CubePosition cube : cubes) {
            if (isAirExternal(cubes, internalAir, cube.x - 1, cube.y, cube.z)) {
                positions++;
            }
            if (isAirExternal(cubes, internalAir, cube.x + 1, cube.y, cube.z)) {
                positions++;
            }

            if (isAirExternal(cubes, internalAir, cube.x, cube.y - 1, cube.z)) {
                positions++;
            }
            if (isAirExternal(cubes, internalAir, cube.x, cube.y + 1, cube.z)) {
                positions++;
            }

            if (isAirExternal(cubes, internalAir, cube.x, cube.y, cube.z - 1)) {
                positions++;
            }
            if (isAirExternal(cubes, internalAir, cube.x, cube.y, cube.z + 1)) {
                positions++;
            }
        }
        return positions;
    }

    private static List<CubePosition> countInternalAir(List<CubePosition> cubes) {
        CubePosition minCube = getMinCubeValues(cubes);
        CubePosition maxCube = getMaxCubeValues(cubes);

        List<CubePosition> internalAir = new ArrayList<>();

        for (int x = minCube.x; x <= maxCube.x; x++) {
            for (int y = minCube.y; y <= maxCube.y; y++) {
                for (int z = minCube.z; z <= maxCube.z; z++) {
                    if (isAirInternal(x, y, z, cubes)) {
                        internalAir.add(new CubePosition(x, y, z));
                    }
                }
            }
        }

        return internalAir;
    }

    private static boolean isAirExternal(List<CubePosition> cubes, List<CubePosition> internalAir, int x, int y, int z) {
        CubePosition neighbourCube = new CubePosition(x, y, z);
        return !cubes.contains(neighbourCube) && !internalAir.contains(neighbourCube);
    }

    private static CubePosition getMinCubeValues(List<CubePosition> cubes) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        for (CubePosition cube : cubes) {
            if (cube.x < minX) {
                minX = cube.x;
            }
            if (cube.y < minY) {
                minY = cube.y;
            }
            if (cube.z < minZ) {
                minZ = cube.z;
            }
        }
        return new CubePosition(minX, minY, minZ);
    }

    private static CubePosition getMaxCubeValues(List<CubePosition> cubes) {
        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;
        for (CubePosition cube : cubes) {
            if (cube.x > maxX) {
                maxX = cube.x;
            }
            if (cube.y > maxY) {
                maxY = cube.y;
            }
            if (cube.z > maxZ) {
                maxZ = cube.z;
            }
        }
        return new CubePosition(maxX, maxY, maxZ);
    }

    private static boolean isAirInternal(int x, int y, int z, List<CubePosition> cubes) {
        CubePosition minValues = getMinCubeValues(cubes);
        CubePosition maxValues = getMaxCubeValues(cubes);

        CubePosition testedPosition = new CubePosition(x, y, z);


        Set<CubePosition> seen = new HashSet<>();
        Deque<CubePosition> queue = new ArrayDeque<>();
        queue.add(testedPosition);
        seen.add(testedPosition);

        while (!queue.isEmpty()) {
            CubePosition polled = queue.pollFirst();

            if (polled.x > maxValues.x || polled.x < minValues.x
                    || polled.y > maxValues.y || polled.y < minValues.y
                    || polled.z > maxValues.z || polled.z < minValues.z) {
                return false;
            }

            tryToVisit(polled.x + 1, polled.y, polled.z, seen, queue, cubes);
            tryToVisit(polled.x - 1, polled.y, polled.z, seen, queue, cubes);
            tryToVisit(polled.x, polled.y + 1, polled.z, seen, queue, cubes);
            tryToVisit(polled.x, polled.y - 1, polled.z, seen, queue, cubes);
            tryToVisit(polled.x, polled.y, polled.z + 1, seen, queue, cubes);
            tryToVisit(polled.x, polled.y, polled.z - 1, seen, queue, cubes);
        }
        return true;
    }

    private static void tryToVisit(int x, int y, int z, Set<CubePosition> seen, Deque<CubePosition> queue, List<CubePosition> cubes) {
        CubePosition tmpPosition = new CubePosition(x, y, z);
        if (!seen.contains(tmpPosition) && !cubes.contains(tmpPosition)) {
            queue.addLast(tmpPosition);
            seen.add(tmpPosition);
        }
    }
}
