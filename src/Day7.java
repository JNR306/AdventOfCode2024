import input.Input;

import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

    static List<List<Long>> equations;

    public static void splitInput() {
        equations = Arrays.stream(Input.INPUT_DAY_7.split("\n")) //Input.INPUT_DAY_7
                .map(s ->
                        Arrays.stream(s.split(" |: "))
                                .mapToLong(Long::parseLong)
                                .boxed()
                                .toList()
                )
                .collect(Collectors.toList());
    }

    public static void solvePartOne() {
        long sumOfResults = 0L;
        for (List<Long> equation : equations) {
            List<Long> equationWithResultAsLastElement = Utils.moveElementToEndInCopyOfList(equation, 0);
            Long result = tryOperations(equationWithResultAsLastElement, false);
            if (result != null)
                sumOfResults += result;
        }
        System.out.println("SOLUTION DAY 7 PART 1: " + sumOfResults);
    }

    //needs equation with result at the end: A, B, C, RESULT
    private static Long tryOperations(List<Long> equation, boolean testConcatenation) {
        //base case: only X, RESULT left
        if (equation.size() == 2) {
            return equation.getFirst().equals(equation.get(1)) ? equation.getFirst() : null;
        }

        long firstElement = equation.getFirst();
        long secondElement = equation.get(1);

        //Addition
        List<Long> equationAfterAddition = Utils.copyList(equation);
        equationAfterAddition.set(1, firstElement + secondElement);

        //Multiplication
        List<Long> equationAfterMultiplication = Utils.copyList(equation);
        equationAfterMultiplication.set(1, firstElement * secondElement);

        //Optional testing of concatenation
        List<Long> equationAfterConcatenation = null;
        if (testConcatenation) {
            equationAfterConcatenation = Utils.copyList(equation);
            equationAfterConcatenation.set(1, Long.valueOf(String.valueOf(firstElement) + secondElement));
        }

        Long addResult = tryOperations(Utils.copyListWithout(equationAfterAddition, 0), testConcatenation);
        Long multiplyResult = tryOperations(Utils.copyListWithout(equationAfterMultiplication, 0), testConcatenation);

        //Optional testing of concatenation
        Long concatenationResult = null;
        if (testConcatenation)
            concatenationResult = tryOperations(Utils.copyListWithout(equationAfterConcatenation, 0), true);

        //return result of equation if any case (addition, multiplication, concatenation) is true (= is not null)
        //else: return null
        if (addResult != null)
            return addResult;
        if (concatenationResult != null)
            return concatenationResult;
        return multiplyResult;
    }

    public static void solvePartTwo() {
        long sumOfResults = 0L;
        for (List<Long> equation : equations) {
            List<Long> equationWithResultAsLastElement = Utils.moveElementToEndInCopyOfList(equation, 0);
            Long result = tryOperations(equationWithResultAsLastElement, true);
            if (result != null)
                sumOfResults += result;
        }
        System.out.println("SOLUTION DAY 7 PART 2: " + sumOfResults);
    }

    public static void main(String[] args) {
        Day7.splitInput();
        Day7.solvePartOne();
        Day7.splitInput();
        Day7.solvePartTwo();
    }
}
