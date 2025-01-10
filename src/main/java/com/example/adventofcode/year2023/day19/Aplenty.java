package com.example.adventofcode.year2023.day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Aplenty {
    private static final String FILENAME = "AdventOfCodeData/2023/day19/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day19/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateAcceptedPartsRatingsSum(EXAMPLE_FILENAME));
        //19114
        System.out.println(calculateAcceptedPartsRatingsSum(FILENAME));
        //319062

        System.out.println(calculateAcceptedCombinationsCount(EXAMPLE_FILENAME));
        //167409079868000
        System.out.println(calculateAcceptedCombinationsCount(FILENAME));
        //118638369682135

    }


    record Point(int x, int y) {
    }

    record Rule(Condition condition, String destination) {
    }

    record Condition(String element, Operator operator, int value) {
    }

    record Range(int min, int max) {
    }

    record RangeElement(Range x, Range m, Range a, Range s) {
    }

    record RangeElementRule(RangeElement rangeElement, String rule) {
    }

    enum Operator {
        LESS, MORE
    }

    record Part(int x, int m, int a, int s) {
    }

    public static long calculateAcceptedPartsRatingsSum(final String filename) throws IOException {
        List<String> inputLines = readLines(filename);
        Map<String, List<Rule>> ruleSet = buildRuleSet(inputLines);
        List<Part> parts = buildPartsList(inputLines);

        int result = 0;
        for (Part el : parts) {
            String decision = "in";
            while (!decision.equals("A") && !decision.equals("R")) {
                List<Rule> rules = ruleSet.get(decision);
                decision = getDecision(rules, el);
            }
            if (decision.equals("A")) {
                result += el.a + el.m + el.s + el.x;
            }
        }
        return result;
    }

    private static List<Part> buildPartsList(List<String> inputLines) {
        List<Part> parts = new ArrayList<>();

        for (String line : inputLines) {
            if (line.startsWith("{")) {
                line = line.replace("{x=", "")
                        .replace("m=", "")
                        .replace("a=", "")
                        .replace("s=", "")
                        .replace("}", "");
                String[] el = line.split(",");

                parts.add(new Part(Integer.parseInt(el[0]), Integer.parseInt(el[1]), Integer.parseInt(el[2]), Integer.parseInt(el[3])));
            }
        }
        return parts;
    }

    public static long calculateAcceptedCombinationsCount(final String filename) throws IOException {
        List<String> inputLines = readLines(filename);
        Map<String, List<Rule>> ruleSet = buildRuleSet(inputLines);
        List<RangeElement> accepted = getAcceptedRanges(ruleSet);

        long result = 0;
        for (RangeElement acc : accepted) {
            result += (long) (acc.x.max - acc.x.min + 1)
                    * (acc.a.max - acc.a.min + 1)
                    * (acc.m.max - acc.m.min + 1)
                    * (acc.s.max - acc.s.min + 1);
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

    private static Map<String, List<Rule>> buildRuleSet(List<String> inputLines) {
        Map<String, List<Rule>> ruleSet = new HashMap<>();
        for (String line : inputLines) {
            if (!line.isEmpty() && !line.startsWith("{")) {
                List<Rule> ruleList = new ArrayList<>();

                String[] parts = line.split("\\{");
                String index = parts[0];

                for (String part : parts[1].replace("}", "").split(",")) {
                    if (!part.contains(":")) {
                        ruleList.add(new Rule(null, part));
                    } else {
                        String[] tmp = part.split(":");

                        Operator op = null;
                        if (tmp[0].charAt(1) == '<') {
                            op = Operator.LESS;
                        } else if (tmp[0].charAt(1) == '>') {
                            op = Operator.MORE;
                        }

                        Condition condition = new Condition(tmp[0].substring(0, 1), op, Integer.parseInt(tmp[0].substring(2)));
                        ruleList.add(new Rule(condition, tmp[1]));
                    }
                    ruleSet.put(index, ruleList);
                }
            }
        }
        return ruleSet;
    }

    private static String getDecision(List<Rule> rules, Part el) {
        for (Rule rule : rules) {
            if (rule.condition == null) {
                return rule.destination;
            }

            if (rule.condition.operator == Operator.LESS) {
                if (rule.condition.element.equals("x") && el.x < rule.condition.value) {
                    return rule.destination;
                }
                if (rule.condition.element.equals("a") && el.a < rule.condition.value) {
                    return rule.destination;
                }
                if (rule.condition.element.equals("s") && el.s < rule.condition.value) {
                    return rule.destination;
                }
                if (rule.condition.element.equals("m") && el.m < rule.condition.value) {
                    return rule.destination;
                }
            } else {
                if (rule.condition.element.equals("x") && el.x > rule.condition.value) {
                    return rule.destination;
                }
                if (rule.condition.element.equals("a") && el.a > rule.condition.value) {
                    return rule.destination;
                }
                if (rule.condition.element.equals("s") && el.s > rule.condition.value) {
                    return rule.destination;
                }
                if (rule.condition.element.equals("m") && el.m > rule.condition.value) {
                    return rule.destination;
                }
            }
        }
        return null;
    }

    private static List<RangeElement> getAcceptedRanges(Map<String, List<Rule>> ruleSet) {
        List<RangeElement> accepted = new ArrayList<>();
        RangeElement element = new RangeElement(
                new Range(1, 4000), new Range(1, 4000), new Range(1, 4000), new Range(1, 4000)
        );


        Queue<RangeElementRule> input = new ArrayDeque<>();
        input.add(new RangeElementRule(element, "in"));
        while (!input.isEmpty()) {
            RangeElementRule rer = input.poll();
            String decision = rer.rule;
            RangeElement rangeElement = rer.rangeElement;

            if (decision.equals("A")) {
                accepted.add(rangeElement);
                continue;
            }
            if (decision.equals("R")) {
                continue;
            }

            for (Rule rule : ruleSet.get(decision)) {
                if (rule.condition == null) {
                    if (rule.destination.equals("A")) {
                        accepted.add(rangeElement);
                    } else if (!rule.destination.equals("R")) {
                        input.add(new RangeElementRule(rangeElement, rule.destination));
                    }
                    break;
                }

                if (rule.condition.operator == Operator.LESS) {
                    if (rule.condition.element.equals("x") && rangeElement.x.min < rule.condition.value) {
                        if (rangeElement.x.max > rule.condition.value) {
                            RangeElement rangeElement1 = new RangeElement(new Range(rangeElement.x.min, rule.condition.value - 1),
                                    rangeElement.m, rangeElement.a, rangeElement.s);
                            rangeElement = new RangeElement(new Range(rule.condition.value, rangeElement.x.max), rangeElement.m, rangeElement.a, rangeElement.s);
                            input.add(new RangeElementRule(rangeElement1, rule.destination));
                        } else {
                            input.add(new RangeElementRule(rangeElement, rule.destination));
                            break;
                        }
                    }
                    if (rule.condition.element.equals("a") && rangeElement.a.min < rule.condition.value) {
                        if (rangeElement.a.max > rule.condition.value) {
                            RangeElement rangeElement1 = new RangeElement(rangeElement.x,
                                    rangeElement.m, new Range(rangeElement.a.min, rule.condition.value - 1), rangeElement.s);
                            rangeElement = new RangeElement(rangeElement.x, rangeElement.m, new Range(rule.condition.value, rangeElement.a.max), rangeElement.s);
                            input.add(new RangeElementRule(rangeElement1, rule.destination));
                        } else {
                            input.add(new RangeElementRule(rangeElement, rule.destination));
                            break;
                        }
                    }
                    if (rule.condition.element.equals("s") && rangeElement.s.min < rule.condition.value) {
                        if (rangeElement.s.max > rule.condition.value) {
                            RangeElement rangeElement1 = new RangeElement(rangeElement.x,
                                    rangeElement.m, rangeElement.a, new Range(rangeElement.s.min, rule.condition.value - 1));
                            rangeElement = new RangeElement(rangeElement.x, rangeElement.m, rangeElement.a, new Range(rule.condition.value, rangeElement.s.max));
                            input.add(new RangeElementRule(rangeElement1, rule.destination));
                        } else {
                            input.add(new RangeElementRule(rangeElement, rule.destination));
                            break;
                        }
                    }
                    if (rule.condition.element.equals("m") && rangeElement.m.min < rule.condition.value) {
                        if (rangeElement.m.max > rule.condition.value) {
                            RangeElement rangeElement1 = new RangeElement(rangeElement.x, new Range(rangeElement.m.min, rule.condition.value - 1),
                                    rangeElement.a, rangeElement.s);
                            rangeElement = new RangeElement(rangeElement.x, new Range(rule.condition.value, rangeElement.m.max), rangeElement.a, rangeElement.s);
                            input.add(new RangeElementRule(rangeElement1, rule.destination));
                        } else {
                            input.add(new RangeElementRule(rangeElement, rule.destination));
                            break;
                        }
                    }
                } else {
                    if (rule.condition.element.equals("x") && rangeElement.x.max > rule.condition.value) {
                        if (rangeElement.x.min < rule.condition.value) {
                            RangeElement rangeElement1 = new RangeElement(new Range(rule.condition.value + 1, rangeElement.x.max), rangeElement.m, rangeElement.a, rangeElement.s);
                            rangeElement = new RangeElement(new Range(rangeElement.x.min, rule.condition.value),
                                    rangeElement.m, rangeElement.a, rangeElement.s);
                            input.add(new RangeElementRule(rangeElement1, rule.destination));
                        } else {
                            input.add(new RangeElementRule(rangeElement, rule.destination));
                            break;
                        }

                    }
                    if (rule.condition.element.equals("a") && rangeElement.a.max > rule.condition.value) {
                        if (rangeElement.a.min < rule.condition.value) {
                            RangeElement rangeElement1 = new RangeElement(rangeElement.x, rangeElement.m, new Range(rule.condition.value + 1, rangeElement.a.max), rangeElement.s);
                            rangeElement = new RangeElement(rangeElement.x,
                                    rangeElement.m, new Range(rangeElement.a.min, rule.condition.value), rangeElement.s);

                            input.add(new RangeElementRule(rangeElement1, rule.destination));
                        } else {
                            input.add(new RangeElementRule(rangeElement, rule.destination));
                            break;
                        }
                    }
                    if (rule.condition.element.equals("s") && rangeElement.s.max > rule.condition.value) {
                        if (rangeElement.s.min < rule.condition.value) {
                            RangeElement rangeElement1 = new RangeElement(rangeElement.x, rangeElement.m, rangeElement.a, new Range(rule.condition.value + 1, rangeElement.s.max));

                            rangeElement = new RangeElement(rangeElement.x,
                                    rangeElement.m, rangeElement.a, new Range(rangeElement.s.min, rule.condition.value));
                            input.add(new RangeElementRule(rangeElement1, rule.destination));
                        } else {
                            input.add(new RangeElementRule(rangeElement, rule.destination));
                            break;
                        }
                    }
                    if (rule.condition.element.equals("m") && rangeElement.m.max > rule.condition.value) {
                        if (rangeElement.m.min < rule.condition.value) {
                            RangeElement rangeElement1 = new RangeElement(rangeElement.x, new Range(rule.condition.value + 1, rangeElement.m.max), rangeElement.a, rangeElement.s);
                            rangeElement = new RangeElement(rangeElement.x, new Range(rangeElement.m.min, rule.condition.value),
                                    rangeElement.a,
                                    rangeElement.s);
                            input.add(new RangeElementRule(rangeElement1, rule.destination));
                        } else {
                            input.add(new RangeElementRule(rangeElement, rule.destination));
                            break;
                        }
                    }
                }
            }
        }
        return accepted;
    }
}
