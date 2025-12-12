package com.example.adventofcode.year2025.day10;

import com.microsoft.z3.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class Factory {
    private static final String FILENAME = "AdventOfCodeData/2025/day10/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2025/day10/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(countMinButtonPressesForIndicatorLights(EXAMPLE_FILENAME));
        System.out.println(countMinButtonPressesForIndicatorLights(FILENAME));
        System.out.println(countMinButtonPressesForJoltageLevelCounters(EXAMPLE_FILENAME));
        System.out.println(countMinButtonPressesForJoltageLevelCounters(FILENAME));
    }
    
    record State(String[] lights, int steps) {
    }

    public static long countMinButtonPressesForIndicatorLights(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        int steps = 0;
        for (String line : lines) {
            String[] elements = line.split(" ");
            String[] targetLights = elements[0].substring(1, elements[0].length() - 1).split("");
            List<List<Integer>> buttons = parseButtons(elements);
            
            steps += countMinButtonPressesForIndicatorLights(targetLights, buttons);
        }
        
        return steps;
    }

    public static long countMinButtonPressesForJoltageLevelCounters(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        long steps = 0;
        for (String line : lines) {
            String[] elements = line.split(" ");
            List<List<Integer>> buttons = parseButtons(elements);
            List<Integer> joltages = parseJoltages(elements);

            steps += solveLinearProblem(buttons, joltages);
        }

        return steps;
    }

    private static List<List<Integer>> parseButtons(String[] elements) {
        List<List<Integer>> buttons = new ArrayList<>();
        for (int i = 1; i < elements.length - 1; i++) {
            String buttonsString = elements[i].substring(1, elements[i].length() - 1);

            String[] buttonString = buttonsString.split(",");

            List<Integer> buttonElements = new ArrayList<>();
            for (String b: buttonString) {
                buttonElements.add(Integer.parseInt(b));
            }
            buttons.add(buttonElements);
        }
        return buttons;
    }
    
    private static int countMinButtonPressesForIndicatorLights(final String[] targetLights, final List<List<Integer>> buttons) {
        Deque<State> p = new ArrayDeque<>();
        p.addLast(new State(parseInitialState(targetLights), 0));

        while (!p.isEmpty()) {
            State current = p.pollFirst();
            for (List<Integer> button : buttons) {
                String[] nextLights = Arrays.copyOf(current.lights, current.lights.length);

                for (int lightPosition : button) {
                    if (lightPosition >= nextLights.length) {
                        continue;
                    }

                    String light = nextLights[lightPosition];

                    if (light.equals(".")) {
                        nextLights[lightPosition] = "#";
                    } else {
                        nextLights[lightPosition] = ".";
                    }
                }
                if (checkIfTargetReached(targetLights, nextLights)) {
                    return current.steps + 1;
                } else {
                    p.addLast(new State(nextLights, current.steps + 1));
                }
            }
        }
        return 0;
    }
    
    private static String[] parseInitialState(String[] targetLights) {
        String[] initialState = new String[targetLights.length];
        for (int i = 0; i < targetLights.length; i++) {
            initialState[i] = ".";
        }
        return initialState;
    }
    
    private static boolean checkIfTargetReached(String[] targetLights, String[] currentLights) {
        for (int k = 0; k < targetLights.length; k++) {
            if (!targetLights[k].equals(currentLights[k])) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> parseJoltages(String[] elements) {
        String joltageString = elements[elements.length - 1].substring(1, elements[elements.length - 1].length() - 1);
        return Arrays.stream(joltageString.split(",")).map(Integer::parseInt).toList();
    }
    
    private static int solveLinearProblem(List<List<Integer>> buttons, List<Integer> joltages) {
        Context ctx = new Context();
        Optimize opt = ctx.mkOptimize();

        IntExpr[] buttonVariables = buildButtonVariables(buttons, ctx);
        addButtonGreaterOrEqualToZeroEquations(ctx, buttonVariables, opt);
        addJoltageEquations(buttons, joltages, buttonVariables, ctx, opt);

        IntExpr total = ctx.mkIntConst("total");
        addButtonPressesEquation(ctx, buttonVariables, total, opt);

        opt.MkMinimize(total);

        Status status = opt.Check();
        if (status == Status.SATISFIABLE) {
            return ((IntNum) opt.getModel()
                    .evaluate(total, false))
                    .getInt();
        }

        return -1;
    }
    
    private static IntExpr[] buildButtonVariables(List<List<Integer>> buttons, Context ctx) {
        return IntStream.range(0, buttons.size())
                .mapToObj(i -> ctx.mkIntConst("b" + i))
                .toArray(IntExpr[]::new);
    }
    
    private static void addButtonGreaterOrEqualToZeroEquations(Context ctx, IntExpr[] buttonVariables, Optimize opt) {
        IntExpr zeroVariable = ctx.mkInt(0);
        for (IntExpr buttonVar : buttonVariables) {
            opt.Add(ctx.mkGe(buttonVar, zeroVariable));
        }
    }
    
    private static void addJoltageEquations(List<List<Integer>> buttons, List<Integer> joltages, IntExpr[] buttonVariables, Context ctx, Optimize opt) {
        for (int joltageNumber = 0; joltageNumber < joltages.size(); joltageNumber++) {
            List<IntExpr> buttonsUsed = new ArrayList<>();
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).contains(joltageNumber)) {
                    buttonsUsed.add(buttonVariables[i]);
                }
            }
            IntExpr joltageButtonsSum = (IntExpr) ctx.mkAdd(buttonsUsed.toArray(new IntExpr[0]));
            IntExpr targetValue = ctx.mkInt(joltages.get(joltageNumber));

            BoolExpr equation = ctx.mkEq(joltageButtonsSum, targetValue);
            opt.Add(equation);
        }
    }
    
    private static void addButtonPressesEquation(Context ctx, IntExpr[] buttonVariables, IntExpr total, Optimize opt) {
        IntExpr sumOfAllButtonVariables = (IntExpr) ctx.mkAdd(buttonVariables);
        BoolExpr totalPresses = ctx.mkEq(total, sumOfAllButtonVariables);
        opt.Add(totalPresses);
    }
}
