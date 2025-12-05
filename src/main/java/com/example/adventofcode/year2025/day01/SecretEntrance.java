package com.example.adventofcode.year2025.day01;

import java.io.IOException;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class SecretEntrance {
    private static final String FILENAME = "AdventOfCodeData/2025/day01/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2025/day01/example_input";


    /**
     * 0x434C49434B hex to text is "click"
     */
    public static void main(String[] args) throws IOException {
        System.out.println(restorePasswordSimulation(EXAMPLE_FILENAME));
        System.out.println(restorePasswordSimulation(FILENAME));
        System.out.println(restorePasswordImproved(EXAMPLE_FILENAME));
        System.out.println(restorePasswordImproved(FILENAME));
        System.out.println(restorePasswordClickMethod(EXAMPLE_FILENAME));
        System.out.println(restorePasswordClickMethod(FILENAME));
    }

    public static long restorePasswordSimulation(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        int position = 50;
        int count = 0;
        for (String line: lines) {
            String side = line.substring(0, 1);
            int number = Integer.parseInt(line.substring(1));

            if (side.equals("L")) {
                for (int i = 0; i < number; i++) {
                    if (position > 0) {
                        position--;
                    } else {
                        position = 99;
                    }
                }
            } else {
                for (int i = 0; i < number; i++) {
                    if (position < 99) {
                        position++;
                    } else {
                        position = 0;
                    }
                }
            }
            if (position == 0) {
                count++;
            }
        }

        return count;
    }

    public static long restorePasswordImproved(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        int position = 50;
        int count = 0;
        for (String line: lines) {
            String side = line.substring(0, 1);
            int number = Integer.parseInt(line.substring(1));

            if (side.equals("L")) {
                position -= number;
                while (position < 0) {
                    position += 100;
                }
            } else {
                position += number;
                while (position > 99) {
                    position -= 100;
                }
            }

            if (position == 0) {
                count++;
            }
        }

        return count;
    }

    public static long restorePasswordClickMethod(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        int position = 50;
        int count = 0;
        for (String line: lines) {
            String side = line.substring(0, 1);
            int number = Integer.parseInt(line.substring(1));

            if (side.equals("L")) {

                for (int i = 0; i < number; i++) {
                    if (position > 0) {
                        position--;
                        if (position == 0) {
                            count++;
                        }
                    } else {
                        position = 99;
                    }
                }
            } else {
                for (int i = 0; i < number; i++) {
                    if (position < 99) {
                        position++;
                    } else {
                        position = 0;
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
