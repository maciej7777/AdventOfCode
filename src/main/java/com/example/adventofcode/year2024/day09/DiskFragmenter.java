package com.example.adventofcode.year2024.day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiskFragmenter {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day09/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day09/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(defragmentDisk(EXAMPLE_FILENAME));
        System.out.println(defragmentDisk(FILENAME));
        System.out.println(defragmentDiskMoveFullFiles(EXAMPLE_FILENAME));
        System.out.println(defragmentDiskMoveFullFiles(FILENAME));
    }

    public static long defragmentDisk(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        List<Integer> space = readDisk(lines);

        for (int i = 0; i < space.size(); i++) {
            if (space.get(i) == -1) {
                replaceWithFirstSpace(space, i);
            }
        }

        long sum = 0;
        for (int i = 0; i < space.size(); i++) {
            long idd = space.get(i)>0 ? space.get(i) : 0;
            sum += idd * i;
        }

        return sum;
    }

    private static List<Integer> readDisk(List<String> lines) {
        List<Integer> space = new ArrayList<>();
        for (String line: lines) {
            boolean occupiedSpace = true;
            int id = 0;
            for (Character character: line.toCharArray()) {
                int digit = character - '0';

                if (occupiedSpace) {
                    for (int i = 0; i < digit; i++) {
                        space.add(id);
                    }
                    id++;
                    occupiedSpace = false;
                } else {
                    for (int i = 0; i < digit; i++) {
                        space.add(-1);
                    }
                    occupiedSpace = true;
                }
            }
        }
        return space;
    }

    record File(int id, int length, int type) {}

    public static long defragmentDiskMoveFullFiles(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        List<File> disk = readDiskByFiles(lines);

        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i).type == 0) {
                tryToReplaceSpace(disk, i);
            }
        }
        long sum = 0;

        int val = 0;
        for (int i = 0; i < disk.size(); i++) {
            for (int d = 0; d < disk.get(i).length; d++) {
                long id = disk.get(i).type > 0 ? disk.get(i).id : 0;
                sum += id * val;
                val++;
            }
        }

        return sum;
    }

    private static List<File> readDiskByFiles(List<String> lines) {
        List<File> diskFile = new ArrayList<>();
        for (String line: lines) {
            boolean occupiedSpace = true;
            int id = 0;
            for (Character character: line.toCharArray()) {
                int digit = character - '0';

                if (occupiedSpace) {
                    diskFile.add(new File(id, digit, 1));
                    id++;
                    occupiedSpace = false;
                } else {
                    diskFile.add(new File(-1, digit, 0));
                    occupiedSpace = true;
                }
            }
        }
        return diskFile;
    }

    private static void tryToReplaceSpace(List<File> diskFile, int i) {
        for (int j = diskFile.size() - 1; j > i; j--) {
            if (diskFile.get(j).type == 1) {
                if (diskFile.get(j).length == diskFile.get(i).length) {
                    File tmpSpace = diskFile.get(i);
                    diskFile.set(i, diskFile.get(j));
                    diskFile.set(j, tmpSpace);
                    return;
                } else if (diskFile.get(j).length < diskFile.get(i).length) {
                    //TODO some space left
                    int diff = diskFile.get(i).length - diskFile.get(j).length;
                    diskFile.set(i, diskFile.get(j));
                    diskFile.set(j, new File(-1, diskFile.get(j).length, 0));
                    diskFile.add(i+1, new File(-1, diff, 0));

                    return;
                }

            }
        }
    }

    private static void replaceWithFirstSpace(List<Integer> space, int i) {
        for (int j = space.size() - 1; j > i; j--) {
            if (space.get(j) > -1) {
                space.set(i, space.get(j));
                space.set(j, -1);
                return;
            }
        }
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
}
