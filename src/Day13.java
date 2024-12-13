import input.Input;

import java.util.*;

public class Day13 {

    static long[][] machines;

    public static void splitInput() {
        String input = Input.INPUT_DAY_13;
        machines = Arrays.stream(input.split("\n\n"))
                .map(s -> Arrays.stream(s.split("\\D+"))
                        .filter(str -> !str.isEmpty())
                        .mapToLong(Long::parseLong)
                        .toArray())
                .toArray(long[][]::new);
    }

    public static void solvePartOne() {
        long totalTokens = solve(100);
        System.out.println("SOLUTION DAY 13 PART 1: " + totalTokens);
    }

    private static long solve(Integer buttonPressLimit) {
        long totalTokens = 0L;
        for (long[] machine : machines) {
            //machine: [a,b,c,d,e,f]
            double a = machine[0];
            double b = machine[1];
            double c = machine[2];
            double d = machine[3];
            double e = machine[4];
            double f = machine[5];
            //equals to:
            //I: ax + cy = e
            //II: bx + dy = f
            //if you solve this equation system, you will get the following two equations for x and y:
            long x = Math.round((f * c - d * e) / (b * c - d * a));
            long y = Math.round((e / c) - (a * x / c));
            if (x < 0 || y < 0 || (buttonPressLimit != null && (x > buttonPressLimit || y > buttonPressLimit))) {
                continue;
            }
            if (a * x + c * y == e && b * x + d * y == f) {
                totalTokens += 3 * x + y;
            }
        }
        return totalTokens;
    }

    public static void solvePartTwo() {
        for (int i = 0; i < machines.length; i++) {
            machines[i][4] += 10000000000000L;
            machines[i][5] += 10000000000000L;
        }
        long totalTokens = solve(null);
        System.out.println("SOLUTION DAY 13 PART 2: " + totalTokens);
    }

    public static void main(String[] args) {
        Day13.splitInput();
        Day13.solvePartOne();
        Day13.splitInput();
        Day13.solvePartTwo();
    }
}