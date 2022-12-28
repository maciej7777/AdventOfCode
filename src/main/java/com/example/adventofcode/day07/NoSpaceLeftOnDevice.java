package com.example.adventofcode.day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NoSpaceLeftOnDevice {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day07/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day07/example_input";
    private static final int SMALL_FILE_MAX_SIZE = 100000;

    public static void main(String[] args) throws IOException {
        System.out.println(obtainSmallFilesSizeSum(EXAMPLE_FILENAME));
        System.out.println(obtainSmallFilesSizeSum(FILENAME));

        System.out.println(obtainFileToRemove(EXAMPLE_FILENAME));
        System.out.println(obtainFileToRemove(FILENAME));
    }

    public static int obtainSmallFilesSizeSum(String fileName) throws IOException {
        DirectorySystem input = readDirectories(fileName);
        return calculateSmallFilesSizeSum(input.directoriesList);
    }

    public static int obtainFileToRemove(String fileName) throws IOException {
        DirectorySystem input = readDirectories(fileName);
        return calculateFileToRemove(input.root, input.directoriesList);
    }

    static class Directory {
        String name;
        Directory parent;
        Map<String, Directory> children;
        Integer size;

        public Directory(String name,
                         Directory parent) {
            this.name = name;
            this.parent = parent;
            this.children = new HashMap();
            size = 0;
        }

        public Integer getSize() {
            int totalSize = size;
            for (Directory child: children.values()) {
                totalSize += child.getSize();
            }
            return totalSize;
        }

        public void increaseSize(int fileSize) {
            size += fileSize;
        }
    }
    record DirectorySize(Directory directory, Integer size){}
    record DirectorySystem(Directory root, Set<Directory> directoriesList){}

    public static DirectorySystem readDirectories(final String filename) throws IOException {
        Directory root = new Directory("/", null);
        Directory current = root;
        Set<Directory> directoriesList = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.equals("$ cd /")) {
                    current = root;
                } else if (line.equals("$ cd ..")) {
                    current = current.parent;
                } else if (line.startsWith("$ cd")) {
                    current = current.children.get(line.substring(5));
                } else if (line.startsWith("$ ls")) {
                    //
                } else if (line.startsWith("dir")) {
                    String name = line.split(" ")[1];
                    Directory childrenDirectory = new Directory(name, current);
                    current.children.put(name, childrenDirectory);
                    directoriesList.add(childrenDirectory);
                } else {
                    int fileSize = Integer.parseInt(line.split(" ")[0]);
                    current.increaseSize(fileSize);
                }
            }
        }
        return new DirectorySystem(root, directoriesList);
    }

    private static int calculateSmallFilesSizeSum(final Set<Directory> directoriesList) {
        int smallFilesSizeSum = 0;
        for (Directory dir: directoriesList) {
            int tmpSize = dir.getSize();
            if (tmpSize <= SMALL_FILE_MAX_SIZE) {
                smallFilesSizeSum += tmpSize;
            }
        }

        return smallFilesSizeSum;
    }

    private static int calculateFileToRemove(final Directory root,
                                             final Set<Directory> directoriesList) {
        List<DirectorySize> directorySizes = getDirectorySizes(directoriesList);
        return calculateFileToRemove(root, directorySizes);
    }

    private static List<DirectorySize> getDirectorySizes(final Set<Directory> directoriesList) {
        List<DirectorySize> directorySizes = new ArrayList<>();
        for (Directory dir: directoriesList) {
            int tmpSize = dir.getSize();
            directorySizes.add(new DirectorySize(dir, tmpSize));
        }
        return directorySizes;
    }

    private static int calculateFileToRemove(final Directory root,
                                             final List<DirectorySize> directorySizes) {
        int missingSpace = 30000000 - (70000000 - root.getSize());
        directorySizes.sort((Comparator.comparingInt(o -> o.size)));
        for (DirectorySize dirs: directorySizes) {
            if (dirs.size > missingSpace) {
                return dirs.size;
            }
        }
        return 0;
    }
}
