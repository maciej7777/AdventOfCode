package com.example.adventofcode.year2025.day02;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class GiftShop {
    private static final String FILENAME = "AdventOfCodeData/2025/day02/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2025/day02/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(sumInvalidIds(EXAMPLE_FILENAME));
        System.out.println(sumInvalidIds(FILENAME));
        System.out.println(sumInvalidIdsByNewRules(EXAMPLE_FILENAME));
        System.out.println(sumInvalidIdsByNewRules(FILENAME));
    }

    record Interval(long first, long last) {
    }

    public static long sumInvalidIds(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        long count = 0;

        for (Interval interval : generateIntervals(lines)) {
            for (long i = interval.first; i <= interval.last; i++) {
                String number = Long.toString(i);

                String number1 = number.substring(0, (number.length() / 2));
                String number2 = number.substring(number.length() / 2);
                if (number1.equals(number2)) {
                    count += i;
                }
            }
        }


        return count;
    }

    public static long sumInvalidIdsByNewRules(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        long count = 0;

        for (Interval interval : generateIntervals(lines)) {
            for (long i = interval.first; i <= interval.last; i++) {
                String number = Long.toString(i);

                int length = number.length();
                for (int j = 2; j <= number.length(); j++) {
                    if (checkIfNumberIsSpecial(number, length, j)) {
                        count += i;
                        break;
                    }
                }
            }
        }

        return count;
    }

    private static List<Interval> generateIntervals(List<String> lines) {
        List<Interval> intervalList = new ArrayList<>();
        for (String line : lines) {
            String[] elements = line.split(",");
            for (String element : elements) {
                String[] interval = element.split("-");
                long first = Long.parseLong(interval[0]);
                long last = Long.parseLong(interval[1]);

                intervalList.add(new Interval(first, last));
            }
        }
        return intervalList;
    }

    private static boolean checkIfNumberIsSpecial(String number, int length, int j) {
        if (length % j == 0) {
            for (int k = 1; k < j; k++) {
                String previous = number.substring(number.length() * (k - 1) / j, (number.length() * k / j));
                String next = number.substring(number.length() * k / j, (number.length() * (k + 1) / j));

                if (!next.equals(previous)) {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }
}
