package com.example.adventofcode.year2023.day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CamelCards {
    private static final String FILENAME = "AdventOfCodeData/2023/day07/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day07/example_input";

    private static final Map<Character, Integer> CARD_RANKING_PART1 = Map.ofEntries(
            Map.entry('A', 12),
            Map.entry('K', 11),
            Map.entry('Q', 10),
            Map.entry('J', 9),
            Map.entry('T', 8),
            Map.entry('9', 7),
            Map.entry('8', 6),
            Map.entry('7', 5),
            Map.entry('6', 4),
            Map.entry('5', 3),
            Map.entry('4', 2),
            Map.entry('3', 1),
            Map.entry('2', 0));
    private static final Map<Character, Integer> CARD_RANKING_PART2 = Map.ofEntries(
            Map.entry('A', 12),
            Map.entry('K', 11),
            Map.entry('Q', 10),
            Map.entry('T', 9),
            Map.entry('9', 8),
            Map.entry('8', 7),
            Map.entry('7', 6),
            Map.entry('6', 5),
            Map.entry('5', 4),
            Map.entry('4', 3),
            Map.entry('3', 2),
            Map.entry('2', 1),
            Map.entry('J', 0));

    public static void main(String[] args) throws IOException {
        System.out.println(simulateTotalWinnings(EXAMPLE_FILENAME));
        System.out.println(simulateTotalWinnings(FILENAME));

        System.out.println(simulateTotalJokerWinnings(EXAMPLE_FILENAME));
        System.out.println(simulateTotalJokerWinnings(FILENAME));
    }

    public static long simulateTotalWinnings(String filename) throws IOException {
        List<String> inputLines = readLines(filename);
        List<Hand> hands = mapHands(inputLines);
        return calculateTotalWinnings(hands);
    }

    public static long simulateTotalJokerWinnings(String filename) throws IOException {
        List<String> inputLines = readLines(filename);
        List<Hand> hands = mapJokerHands(inputLines);
        return calculateTotalWinnings(hands);
    }

    static class Hand implements Comparable {
        public String cards;
        public long bid;
        protected Map<Character, Integer> ranking = CARD_RANKING_PART1;
        public HandType handType;

        public Hand(String cards, long bid) {
            this.cards = cards;
            this.bid = bid;

            this.handType = markHandType();
        }

        protected HandType markHandType() {
            List<Integer> results = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                int counter = 0;
                for (int j = 0; j < 5; j++) {
                    if (cards.charAt(i) == cards.charAt(j)) {
                        counter++;
                    }
                }
                results.add(counter);
            }
            results = results.stream().sorted(Comparator.reverseOrder()).toList();

            if (results.get(0) == 5) {
                return HandType.FIVE;
            } else if (results.get(0) == 4) {
                return HandType.FOUR;
            } else if (results.get(0) == 3 && results.get(3) == 2) {
                return HandType.FULL_HOUSE;
            } else if (results.get(0) == 3) {
                return HandType.THREE;
            } else if (results.get(0) == 2 && results.get(2) == 2) {
                return HandType.TWO_PAIR;
            } else if (results.get(0) == 2) {
                return HandType.PAIR;
            } else {
                return HandType.HIGH_CARD;
            }
        }

        @Override
        public int compareTo(Object o) {
            Hand other = (Hand) o;
            if (handType.value != other.handType.value) {
                return handType.value - other.handType.value;
            }

            for (int i = 0; i < cards.length(); i++) {
                if (cards.charAt(i) != other.cards.charAt(i)) {
                    return ranking.get(cards.charAt(i)) - ranking.get(other.cards.charAt(i));
                }
            }
            return 0;
        }
    }

    static class JokerHand extends Hand {

        public JokerHand(String cards, long bid) {
            super(cards, bid);
            super.ranking = CARD_RANKING_PART2;
        }

        protected HandType markHandType() {
            List<Integer> results = new ArrayList<>();
            int jokerCount = 0;
            for (int i = 0; i < 5; i++) {
                int counter = 0;
                if (cards.charAt(i) == 'J') {
                    jokerCount++;
                } else {
                    for (int j = 0; j < 5; j++) {
                        if (cards.charAt(i) == cards.charAt(j)) {
                            counter++;
                        }
                    }
                }
                results.add(counter);
            }
            results = results.stream().sorted(Comparator.reverseOrder()).toList();

            if (results.get(0)+jokerCount == 5) {
                return HandType.FIVE;
            } else if (results.get(0)+jokerCount == 4) {
                return HandType.FOUR;
            } else if (results.get(0)+jokerCount == 3 && results.get(results.get(0)) == 2) {
                return HandType.FULL_HOUSE;
            } else if (results.get(0)+jokerCount == 3) {
                return HandType.THREE;
            } else if (results.get(0) == 2 && results.get(2) == 2) {
                return HandType.TWO_PAIR;
            } else if (results.get(0)+jokerCount == 2) {
                return HandType.PAIR;
            } else {
                return HandType.HIGH_CARD;
            }
        }
    }

    enum HandType {
        FIVE(6),
        FOUR(5),
        FULL_HOUSE(4),
        THREE(3),
        TWO_PAIR(2),
        PAIR(1),
        HIGH_CARD(0);

        private final int value;

        HandType(int value) {
            this.value = value;
        }
    }

    private static Long calculateTotalWinnings(List<Hand> hands) {
        Collections.sort(hands);

        long result = 0;
        int position = 1;
        for (Hand hand : hands) {
            result += hand.bid * position;
            position++;
        }
        return result;
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

    private static List<Hand> mapHands(List<String> lines) {
        List<Hand> hands = new ArrayList<>();
        for (String line: lines) {
            String[] lineElements = line.split(" ");
            hands.add(new Hand(lineElements[0], Long.parseLong(lineElements[1])));
        }
        return hands;
    }

    private static List<Hand> mapJokerHands(List<String> lines) {
        List<Hand> hands = new ArrayList<>();
        for (String line: lines) {
            String[] lineElements = line.split(" ");
            hands.add(new JokerHand(lineElements[0], Long.parseLong(lineElements[1])));
        }
        return hands;
    }
}
