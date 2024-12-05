import input.Input;

public class Day1 {

    static int[] leftList;
    static int[] rightList;

    public static void splitInput() {
        String[] lines = Input.INPUT_DAY_1.split("\n");
        leftList = new int[lines.length];
        rightList = new int[lines.length];
        for (int i = 0; i < lines.length; i++) {
            String[] locationIDs = lines[i].split(" {3}");
            leftList[i] = Integer.parseInt(locationIDs[0]);
            rightList[i] = Integer.parseInt(locationIDs[1]);
        }
    }

    public static void solvePartOne() {
        Utils.sortArray(leftList);
        Utils.sortArray(rightList);
        int distanceBetweenLists = 0;
        for (int i = 0; i < leftList.length; i++) {
            distanceBetweenLists += Math.abs(leftList[i] - rightList[i]);
        }
        System.out.println("SOLUTION DAY 1 PART 1: " + distanceBetweenLists);
    }

    public static void solvePartTwo() {
        int similarityScore = 0;
        for (int element : leftList) {
            similarityScore += element * Utils.containsCount(rightList, element);
        }
        System.out.println("SOLUTION DAY 1 PART 2: " + similarityScore);
    }

    public static void main(String[] args) {
        Day1.splitInput();
        Day1.solvePartOne();
        Day1.solvePartTwo();
    }
}
