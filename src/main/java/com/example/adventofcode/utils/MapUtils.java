package com.example.adventofcode.utils;

import java.util.List;

public class MapUtils {
    private static void printMap(List<List<Character>> map) {
        for (List<Character> characters : map) {
            for (Character character : characters) {
                System.out.print(character);
            }
            System.out.println(" ");
        }
        System.out.println(" ");
    }
}
