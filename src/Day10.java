import input.Input;

import java.util.*;

public class Day10 {

    static int[][] map;
    static List<Coordinate> trailheads;
    static Set<Coordinate> accessibleHikingTrailEndings;
    static List<Coordinate> notDistinctAccessibleHikingTrailEndings;

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
            Day10.Coordinate that = (Day10.Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return x + y;
        }

        @Override
        public String toString() {
            return "(x=" + x + ", y=" + y + ")";
        }
    }

    public static void splitInput() {
        String input = Input.INPUT_DAY_10;
        map = Arrays.stream(input.split("\n"))
                .map(s -> s.chars().mapToObj(c -> String.valueOf((char) c)).toArray())
                .map(a -> Arrays.stream(a).mapToInt(s -> Integer.parseInt((String) s)).toArray())
                .toArray(int[][]::new);
        trailheads = new ArrayList<>();
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[y][x] == 0)
                    trailheads.add(new Coordinate(x, y));
            }
        }
    }

    public static void solvePartOne() {
        int sumOfTrailheadScores = findTrailCount(true);
        System.out.println("SOLUTION DAY 10 PART 1: " + sumOfTrailheadScores);
    }

    private static int findTrailCount(boolean forDistinctTrailEndings) {
        int hikingTrailsCount = 0;
        for (Coordinate trailhead : trailheads) {
            boolean foundNewCoordinate = true;
            if (forDistinctTrailEndings)
                accessibleHikingTrailEndings = new HashSet<>();
            else
                notDistinctAccessibleHikingTrailEndings = new ArrayList<>();
            List<Coordinate> coordinatesToCheck = new ArrayList<>();
            coordinatesToCheck.add(trailhead);
            while (foundNewCoordinate) {
                foundNewCoordinate = false;
                List<Coordinate> newCoordinatesToCheck = new ArrayList<>();
                for (Coordinate coordinate : coordinatesToCheck) {
                    List<Coordinate> newAccessibleCoordinates = forDistinctTrailEndings ? checkCoordinate(coordinate, true) : checkCoordinate(coordinate, false);
                    if (!newAccessibleCoordinates.isEmpty())
                        foundNewCoordinate = true;
                    newCoordinatesToCheck.remove(coordinate);
                    newCoordinatesToCheck.addAll(newAccessibleCoordinates);
                }
                coordinatesToCheck = newCoordinatesToCheck;
            }
            hikingTrailsCount += forDistinctTrailEndings ? accessibleHikingTrailEndings.size() : notDistinctAccessibleHikingTrailEndings.size();
        }
        return hikingTrailsCount;
    }

    private static List<Coordinate> checkCoordinate(Coordinate coordinate, boolean forDistinctTrailEndings) {
        List<Coordinate> coordinatesThatShouldBeCheckedNext = new ArrayList<>();
        int heightCurrentCoordinate = map[coordinate.y][coordinate.x];
        if (heightCurrentCoordinate == 9) {
            if (forDistinctTrailEndings)
                accessibleHikingTrailEndings.add(coordinate);
            else
                notDistinctAccessibleHikingTrailEndings.add(coordinate);
            return coordinatesThatShouldBeCheckedNext;
        }
        if (coordinate.x - 1 >= 0 && map[coordinate.y][coordinate.x - 1] == heightCurrentCoordinate + 1)
            coordinatesThatShouldBeCheckedNext.add(new Coordinate(coordinate.x - 1, coordinate.y));
        if (coordinate.y - 1 >= 0 && map[coordinate.y - 1][coordinate.x] == heightCurrentCoordinate + 1)
            coordinatesThatShouldBeCheckedNext.add(new Coordinate(coordinate.x, coordinate.y - 1));
        if (coordinate.x + 1 < map[0].length && map[coordinate.y][coordinate.x + 1] == heightCurrentCoordinate + 1)
            coordinatesThatShouldBeCheckedNext.add(new Coordinate(coordinate.x + 1, coordinate.y));
        if (coordinate.y + 1 < map.length && map[coordinate.y + 1][coordinate.x] == heightCurrentCoordinate + 1)
            coordinatesThatShouldBeCheckedNext.add(new Coordinate(coordinate.x, coordinate.y + 1));
        return coordinatesThatShouldBeCheckedNext;
    }

    public static void solvePartTwo() {
        int sumOfTrailheadRatings = findTrailCount(false);
        System.out.println("SOLUTION DAY 10 PART 2: " + sumOfTrailheadRatings);
    }

    public static void main(String[] args) {
        Day10.splitInput();
        Day10.solvePartOne();
        Day10.splitInput();
        Day10.solvePartTwo();
    }
}