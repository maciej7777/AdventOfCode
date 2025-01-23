package com.example.adventofcode.year2023.day04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class Scratchcards {
    private static final String FILENAME = "AdventOfCodeData/2023/day04/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day04/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateWinningPoints(EXAMPLE_FILENAME));
        //13
        System.out.println(calculateWinningPoints(FILENAME));
        //24542

        System.out.println(calculateTotalScratchcards(EXAMPLE_FILENAME));
        //30
        System.out.println(calculateTotalScratchcards(FILENAME));
        //8736438
    }

    private record Scratchcard(List<Integer> winningNumbers, List<Integer> ourNumbers){}

    public static int calculateWinningPoints(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Scratchcard> scratchcards = buildScratchcards(lines);

        return countWinningPoints(scratchcards);
    }

    public static int calculateTotalScratchcards(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Scratchcard> scratchcards = buildScratchcards(lines);

        Map<Integer, Integer> cards = simulateScratchcardsWins(scratchcards);
        return countScratchcards(cards);
    }

    private static List<Scratchcard> buildScratchcards(List<String> lines) {
        List<Scratchcard> scratchcards = new ArrayList<>();
        for (String line : lines) {
            String[] entry = line.split(": ");

            String[] numbersToParse = entry[1].split(" \\| ");
            String[] winningNumbersToParse = numbersToParse[0].trim().split(" ");
            String[] ourNumbersToParse = numbersToParse[1].trim().split(" ");

            List<Integer> winningNumbers = parseNumbersToIntegerList(winningNumbersToParse);
            List<Integer> ourNumbers = parseNumbersToIntegerList(ourNumbersToParse);
            scratchcards.add(new Scratchcard(winningNumbers, ourNumbers));
        }
        return scratchcards;
    }

    private static List<Integer> parseNumbersToIntegerList(String[] numbersArray) {
        List<Integer> numbersList = new ArrayList<>();
        for (String number : numbersArray) {
            if (!number.isEmpty()) {
                numbersList.add(Integer.parseInt(number));
            }
        }
        return numbersList;
    }

    private static int countWinningPoints(List<Scratchcard> scratchcards) {
        int sum = 0;
        for (Scratchcard scratchcard : scratchcards) {
            int power = countWinningNumbers(scratchcard.ourNumbers, scratchcard.winningNumbers);

            if (power == 0) {
                sum += 0;
            } else if (power == 1) {
                sum += 1;
            } else {
                sum += pow(2, power - 1);
            }
        }
        return sum;
    }

    private static int pow(int a, int b) {
        if (b == 0) return 1;
        if (b == 1) return a;
        if (((b & 1) == 0)) return pow(a * a, b / 2); //even a=(a^2)^b/2
        else return a * pow(a * a, b / 2); //odd  a=a*(a^2)^b/2

    }

    private static int countWinningNumbers(List<Integer> ourNumbers, List<Integer> winningNumbers) {
        ourNumbers.retainAll(winningNumbers);
        return ourNumbers.size();
    }

    private static Map<Integer, Integer> simulateScratchcardsWins(List<Scratchcard> scratchcards) {
        Map<Integer, Integer> cards = new HashMap<>();
        for (int i = 0; i < scratchcards.size(); i++) {
            int currentNumberCounter = cards.getOrDefault(i, 0) + 1;
            cards.put(i, currentNumberCounter);

            int wins = countWinningNumbers(scratchcards.get(i).ourNumbers, scratchcards.get(i).winningNumbers);

            for (int j = i + 1; j <= wins + i; j++) {
                cards.put(j, cards.getOrDefault(j, 0) + currentNumberCounter);
            }
        }
        return cards;
    }

    private static int countScratchcards(Map<Integer, Integer> cards) {
        int sumOfCards = 0;
        for (Map.Entry<Integer, Integer> card : cards.entrySet()) {
            sumOfCards += card.getValue();
        }
        return sumOfCards;
    }
}
