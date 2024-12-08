import input.Input;

import java.util.HashSet;
import java.util.Set;

public class Day6 {

    static char[][] puzzle;
    static Set<Coordinate> visited = new HashSet<>();

    public static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return x + y;
        }

        @Override
        public String toString() {
            return "x=" + x + ", y=" + y;
        }
    }

    public static void splitInput() {
        String[] lines = Input.INPUT_DAY_6.split("\n");
        puzzle = new char[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            puzzle[i] = lines[i].toCharArray();
        }
    }

    public static void solvePartOne() {
        visited.clear();
        Coordinate currentGuardPosition = findGuard();
        visited.add(currentGuardPosition);
        while (currentGuardPosition != null) {
            currentGuardPosition = moveGuard(currentGuardPosition);
            if (currentGuardPosition != null)
                visited.add(currentGuardPosition);
        }
        System.out.println("SOLUTION DAY 6 PART 1: " + visited.size());
    }

    private static Coordinate findGuard() {
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if (puzzle[i][j] == '^')
                    return new Coordinate(j, i);
            }
        }
        return null;
    }

    //return null if guard is not in visible area anymore
    private static Coordinate moveGuard(Coordinate currentGuardPos) {
        switch (puzzle[currentGuardPos.y][currentGuardPos.x]) {
            case '^' -> {
                //is next position not blocked?
                if (currentGuardPos.y-1 >= 0 && puzzle[currentGuardPos.y - 1][currentGuardPos.x] != '#') {
                    //move forward
                    puzzle[currentGuardPos.y - 1][currentGuardPos.x] = '^';
                    puzzle[currentGuardPos.y][currentGuardPos.x] = '.';
                    return new Coordinate(currentGuardPos.x, currentGuardPos.y - 1);
                } else if (currentGuardPos.y-1 >= 0 && puzzle[currentGuardPos.y - 1][currentGuardPos.x] == '#'){
                    //turn right
                    puzzle[currentGuardPos.y][currentGuardPos.x] = '>';
                    return currentGuardPos;
                } else {
                    //move outside visible area
                    puzzle[currentGuardPos.y][currentGuardPos.x] = '.';
                    return null;
                }
            }
            case '>' -> {
                //is next position not blocked?
                if (currentGuardPos.x+1 < puzzle[0].length && puzzle[currentGuardPos.y][currentGuardPos.x + 1] != '#') {
                    //move forward
                    puzzle[currentGuardPos.y][currentGuardPos.x + 1] = '>';
                    puzzle[currentGuardPos.y][currentGuardPos.x] = '.';
                    return new Coordinate(currentGuardPos.x + 1, currentGuardPos.y);
                } else if (currentGuardPos.x+1 < puzzle[0].length && puzzle[currentGuardPos.y][currentGuardPos.x+1] == '#'){
                    //turn right
                    puzzle[currentGuardPos.y][currentGuardPos.x] = 'v';
                    return currentGuardPos;
                } else {
                    //move outside visible area
                    puzzle[currentGuardPos.y][currentGuardPos.x] = '.';
                    return null;
                }
            }
            case 'v' -> {
                //is next position not blocked?
                if (currentGuardPos.y+1 < puzzle.length && puzzle[currentGuardPos.y+1][currentGuardPos.x] != '#') {
                    //move forward
                    puzzle[currentGuardPos.y + 1][currentGuardPos.x] = 'v';
                    puzzle[currentGuardPos.y][currentGuardPos.x] = '.';
                    return new Coordinate(currentGuardPos.x, currentGuardPos.y + 1);
                } else if (currentGuardPos.y+1 < puzzle.length && puzzle[currentGuardPos.y+1][currentGuardPos.x] == '#'){
                    //turn right
                    puzzle[currentGuardPos.y][currentGuardPos.x] = '<';
                    return currentGuardPos;
                } else {
                    //move outside visible area
                    puzzle[currentGuardPos.y][currentGuardPos.x] = '.';
                    return null;
                }
            }
            case '<' -> {
                //is next position not blocked?
                if (currentGuardPos.x-1 >= 0 && puzzle[currentGuardPos.y][currentGuardPos.x-1] != '#') {
                    //move forward
                    puzzle[currentGuardPos.y][currentGuardPos.x - 1] = '<';
                    puzzle[currentGuardPos.y][currentGuardPos.x] = '.';
                    return new Coordinate(currentGuardPos.x - 1, currentGuardPos.y);
                } else if (currentGuardPos.x-1 >= 0 && puzzle[currentGuardPos.y][currentGuardPos.x-1] == '#'){
                    //turn right
                    puzzle[currentGuardPos.y][currentGuardPos.x] = '^';
                    return currentGuardPos;
                } else {
                    //move outside visible area
                    puzzle[currentGuardPos.y][currentGuardPos.x] = '.';
                    return null;
                }
            }
        }
        return null;
    }

    private static boolean isGuardStuckInLoopWithBarrier(Coordinate barrierPos) {
        puzzle[barrierPos.y][barrierPos.x] = '#';
        Coordinate currentGuardPosition = findGuard();
        visited.add(currentGuardPosition);
        int visitedEqualCount = 0;
        int lastVisitedCount;
        while (currentGuardPosition != null) {
            lastVisitedCount = visited.size();
            currentGuardPosition = moveGuard(currentGuardPosition);
            if (currentGuardPosition != null) {
                visited.add(currentGuardPosition);
                if (visited.size() == lastVisitedCount)
                    visitedEqualCount++;
                else
                    visitedEqualCount = 0;
                if (visitedEqualCount > 10000)
                    return true;
            }
        }
        return false;
    }

    public static void solvePartTwo() {
        System.out.println("This will take some seconds...");
        int possibleBarrierPosCount = 0;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if (puzzle[i][j] != '#' && puzzle[i][j] != '^') {
                    boolean stuckWithBarrier = isGuardStuckInLoopWithBarrier(new Coordinate(j, i));
                    splitInput();
                    visited.clear();
                    if (stuckWithBarrier)
                        possibleBarrierPosCount++;
                }
            }
        }
        System.out.println("SOLUTION DAY 6 PART 2: " + possibleBarrierPosCount);
    }

    public static void main(String[] args) {
        Day6.splitInput();
        Day6.solvePartOne();
        Day6.splitInput();
        Day6.solvePartTwo();
    }
}
