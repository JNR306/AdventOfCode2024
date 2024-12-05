import input.Input;

import java.util.ArrayList;
import java.util.List;

public class Day5 {

    static int[][] rules;
    static List<List<Integer>> updates;

    public static void splitInput() {

        String[] parts = Input.INPUT_DAY_5.split("\n\n");
        String[] linesPart1 = parts[0].split("\n");
        String[] linesPart2 = parts[1].split("\n");

        //RULES:
        rules = new int[linesPart1.length][2];
        for (int i = 0; i < linesPart1.length; i++) {
            String[] splitLine = linesPart1[i].split("\\|");
            int[] pages = {Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1])};
            rules[i] = pages;
        }

        //UPDATES:
        updates = new ArrayList<>();
        for (String line : linesPart2) {
            String[] splitLine = line.split(",");
            List<Integer> updatedPages = new ArrayList<>();
            for (String split : splitLine) {
                updatedPages.add(Integer.parseInt(split));
            }
            updates.add(updatedPages);
        }
    }

    public static void solvePartOne() {
        //Searching for correct updates
        List<List<Integer>> correctUpdates = new ArrayList<>();
        for (List<Integer> update : updates) {
            if (areAllRulesAppliedCorrectly(update))
                correctUpdates.add(update);
        }

        //Calculating the sum
        int sumOfMiddlePages = 0;
        for (List<Integer> correctUpdate : correctUpdates) {
            sumOfMiddlePages += correctUpdate.get(correctUpdate.size()/2);
        }
        System.out.println("SOLUTION DAY 5 PART 1: " + sumOfMiddlePages);
    }

    private static boolean isRuleAppliedCorrectly(int[] rule, List<Integer> update) {
        if (!update.contains(rule[0]) || !update.contains(rule[1])) {
            return true;
        }
        boolean foundFirstPage = false;
        for (Integer page : update) {
            if (page == rule[0])
                foundFirstPage = true;
            if (foundFirstPage && page == rule[1])
                return true;
        }
        return false;
    }

    private static boolean areAllRulesAppliedCorrectly(List<Integer> update) {
        for (int[] rule : rules) {
            if (!isRuleAppliedCorrectly(rule, update)) {
                return false;
            }
        }
        return true;
    }

    public static void solvePartTwo() {
        //Searching for incorrect updates
        List<List<Integer>> incorrectUpdates = new ArrayList<>();
        for (List<Integer> update : updates) {
            if (!areAllRulesAppliedCorrectly(update))
                incorrectUpdates.add(update);
        }

        //Correcting incorrect updates
        List<List<Integer>> correctedUpdates = new ArrayList<>();
        for (List<Integer> incorrectUpdate : incorrectUpdates) {
            List<Integer> reorderedPages = new ArrayList<>();
            for (int page : incorrectUpdate) {
                reorderedPages.addFirst(page);
                int index = 0;
                while (!areAllRulesAppliedCorrectly(reorderedPages)) {
                    reorderedPages.set(index, reorderedPages.get(index + 1));
                    reorderedPages.set(index + 1, page);
                    index++;
                }
            }
            correctedUpdates.add(reorderedPages);
        }

        //Calculating the sum
        int sumOfMiddlePages = 0;
        for (List<Integer> correctedUpdate : correctedUpdates) {
            sumOfMiddlePages += correctedUpdate.get(correctedUpdate.size()/2);
        }
        System.out.println("SOLUTION DAY 5 PART 2: " + sumOfMiddlePages);
    }

    public static void main(String[] args) {
        Day5.splitInput();
        Day5.solvePartOne();
        Day5.splitInput();
        Day5.solvePartTwo();
    }
}
