import input.Input;

import java.util.*;

public class Day11 {

    static long[] stones;
    static Map<StoneIterationTuple, Long> knownTransformChains;

    public static void splitInput() {
        String input = Input.INPUT_DAY_11;
        knownTransformChains = new HashMap<>();
        stones = Arrays.stream(input.split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
    }

    private static class StoneIterationTuple {
        long stone;
        int iterationsLeft;

        public StoneIterationTuple(long stone, int iterationsLeft) {
            this.stone = stone;
            this.iterationsLeft = iterationsLeft;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StoneIterationTuple that = (StoneIterationTuple) o;
            return stone == that.stone && iterationsLeft == that.iterationsLeft;
        }

        @Override
        public int hashCode() {
            //return Objects.hash(stone, iterationsLeft);
            return (int) stone + iterationsLeft;
        }

        @Override
        public String toString() {
            return "stone: " + stone + ", iterationsLeft: " + iterationsLeft;
        }
    }

    private static int getLength(long number) {
        return (int) (Math.log10(number) + 1); //e.g.: 18375 has 5 digits. 18375 = 10^x => x = log_10(18375) + 1 => x = 5
    }

    private static long[] getSplitNumber(long number) {
        String numberStr = String.valueOf(number);
        long[] splitNumber = new long[2];
        splitNumber[0] = Integer.parseInt(numberStr.substring(0, getLength(number) / 2));
        splitNumber[1] = Integer.parseInt(numberStr.substring(getLength(number) / 2, getLength(number)));
        return splitNumber;
    }

    private static long countAddedStones(long stone, int iterationsLeft) {
        //base case
        if (iterationsLeft == 0)
            return 0L;
        //check if solution for this situation is already known
        if (knownTransformChains.containsKey(new StoneIterationTuple(stone, iterationsLeft))) {
            return knownTransformChains.get(new StoneIterationTuple(stone, iterationsLeft));
        }
        long res = 0;
        if (stone == 0) {
            res += countAddedStones(1L, iterationsLeft - 1);
        } else if (getLength(stone) % 2 == 0) {
            long[] splitNumber = getSplitNumber(stone);
            res += 1; //because number was split into two parts -> 1 number more than before
            res += countAddedStones(splitNumber[0], iterationsLeft - 1);
            res += countAddedStones(splitNumber[1], iterationsLeft - 1);
        } else {
            res += countAddedStones(stone * 2024L, iterationsLeft - 1);
        }
        //add newly calculated answer for current situation to map of known solutions
        knownTransformChains.put(new StoneIterationTuple(stone, iterationsLeft), res);
        return res;
    }

    public static long solve(int iterations) {
        long n = stones.length; //stone count
        for (long stone : stones) {
            n += countAddedStones(stone, iterations);
        }
        return n;
    }

    public static void solvePartOne() {
        System.out.println("SOLUTION DAY 11 PART 1: " + solve(25));
    }

    public static void solvePartTwo() {
        System.out.println("SOLUTION DAY 11 PART 2: " + solve(75));
    }

    public static void main(String[] args) {
        Day11.splitInput();
        Day11.solvePartOne();
        Day11.splitInput();
        Day11.solvePartTwo();
    }
}