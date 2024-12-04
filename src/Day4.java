import input.Input;

public class Day4 {

    static char[][] wordInput;

    public static void splitInput() {
        wordInput = null;
        String[] lines = Input.INPUT_DAY_4.split("\n");
        wordInput = new char[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            wordInput[i] = lines[i].toCharArray();
        }
    }

    public static void solvePartOne() {
        int xmasCount = 0;
        for (int i = 0; i < wordInput.length; i++) {
            for (int j = 0; j < wordInput[i].length - 3; j++) {
                if (wordInput[i][j] == 'X' && wordInput[i][j + 1] == 'M' && wordInput[i][j + 2] == 'A' && wordInput[i][j + 3] == 'S'
                        || wordInput[i][j] == 'S' && wordInput[i][j + 1] == 'A' && wordInput[i][j + 2] == 'M' && wordInput[i][j + 3] == 'X')
                    xmasCount++;
            }
        }
        for (int i = 0; i < wordInput.length - 3; i++) {
            for (int j = 0; j < wordInput[i].length; j++) {
                if (wordInput[i][j] == 'X' && wordInput[i + 1][j] == 'M' && wordInput[i + 2][j] == 'A' && wordInput[i + 3][j] == 'S'
                        || wordInput[i][j] == 'S' && wordInput[i + 1][j] == 'A' && wordInput[i + 2][j] == 'M' && wordInput[i + 3][j] == 'X')
                    xmasCount++;
            }
        }
        for (int i = 0; i < wordInput.length - 3; i++) {
            for (int j = 0; j < wordInput[i].length - 3; j++) {
                if (wordInput[i][j] == 'X' && wordInput[i + 1][j + 1] == 'M' && wordInput[i + 2][j + 2] == 'A' && wordInput[i + 3][j + 3] == 'S'
                        || wordInput[i][j] == 'S' && wordInput[i + 1][j + 1] == 'A' && wordInput[i + 2][j + 2] == 'M' && wordInput[i + 3][j + 3] == 'X')
                    xmasCount++;
            }
        }
        for (int i = 0; i < wordInput.length - 3; i++) {
            for (int j = 3; j < wordInput[i].length; j++) {
                if (wordInput[i][j] == 'X' && wordInput[i + 1][j - 1] == 'M' && wordInput[i + 2][j - 2] == 'A' && wordInput[i + 3][j - 3] == 'S'
                        || wordInput[i][j] == 'S' && wordInput[i + 1][j - 1] == 'A' && wordInput[i + 2][j - 2] == 'M' && wordInput[i + 3][j - 3] == 'X')
                    xmasCount++;
            }
        }
        System.out.println("SOLUTION DAY 4 PART 1" + ": " + xmasCount);
    }

    public static void solvePartTwo() {
        int xmasCount = 0;
        for (int i = 0; i < wordInput.length - 2; i++) {
            for (int j = 0; j < wordInput[i].length - 2; j++) {
                int masCount = 0;
                if (wordInput[i][j] == 'M' && wordInput[i + 1][j + 1] == 'A' && wordInput[i + 2][j + 2] == 'S'
                        || wordInput[i][j] == 'S' && wordInput[i + 1][j + 1] == 'A' && wordInput[i + 2][j + 2] == 'M')
                    masCount++;
                if (wordInput[i][j + 2] == 'M' && wordInput[i + 1][j + 1] == 'A' && wordInput[i + 2][j] == 'S'
                        || wordInput[i][j + 2] == 'S' && wordInput[i + 1][j + 1] == 'A' && wordInput[i + 2][j] == 'M')
                    masCount++;
                if (masCount == 2)
                    xmasCount++;
            }
        }
        System.out.println("SOLUTION DAY 4 PART 2" + ": " + xmasCount);
    }

    public static void main(String[] args) {
        Day4.splitInput();
        Day4.solvePartOne();
        Day4.splitInput();
        Day4.solvePartTwo();
    }
}
