import input.Input;

import java.util.*;
import java.util.stream.Collectors;

public class Day8 {

    static char[][] map;
    static Set<Antinode> antinodes;
    static List<Antenna> antennas;
    static List<List<Antenna>> categorizedAntennas;

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

    public static class Vector extends Day8.Coordinate {
        public Vector(int x, int y) {
            super(x, y);
        }
    }

    public static class Antinode extends Day8.Coordinate {
        public Antinode(int x, int y) {
            super(x, y);
        }
    }

    public static class Antenna extends Day8.Coordinate {
        char frequency;

        public Antenna(char frequency, int x, int y) {
            super(x, y);
            this.frequency = frequency;
        }

        @Override
        public String toString() {
            return "frequency=" + frequency + ", x=" + x + ", y=" + y;
        }
    }

    public static void splitInput() {
        map = Arrays.stream(Input.INPUT_DAY_8.split("\n")).map(String::toCharArray).toArray(char[][]::new);
        antennas = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] != '.')
                    antennas.add(new Antenna(map[y][x], x, y));
            }
        }
        Map<Character, List<Antenna>> groupedAntennas = antennas.stream().collect(Collectors.groupingBy(antenna -> antenna.frequency));
        categorizedAntennas = new ArrayList<>(groupedAntennas.values());
        antinodes = new HashSet<>();
    }

    public static void solvePartOne() {
        for (List<Antenna> categorizedAntenna : categorizedAntennas) {
            for (int i = 0; i < categorizedAntenna.size()-1; i++) {
                Antenna firstAntenna = categorizedAntenna.get(i);
                for (int j = i + 1; j < categorizedAntenna.size(); j++) {
                    Antenna secondAntenna = categorizedAntenna.get(j);
                    Vector vec = new Vector(firstAntenna.x - secondAntenna.x, firstAntenna.y - secondAntenna.y);
                    Antinode firstAntinode = new Antinode(firstAntenna.x + vec.x, firstAntenna.y + vec.y);
                    Antinode secondAntinode = new Antinode(secondAntenna.x - vec.x, secondAntenna.y - vec.y);
                    if (firstAntinode.y < map.length && firstAntinode.y >= 0 && firstAntinode.x < map[0].length && firstAntinode.x >= 0)
                        antinodes.add(firstAntinode);
                    if (secondAntinode.y < map.length && secondAntinode.y >= 0 && secondAntinode.x < map[0].length && secondAntinode.x >= 0)
                        antinodes.add(secondAntinode);
                }
            }
        }
        System.out.println("SOLUTION DAY 8 PART 1: " + antinodes.size());
    }

    public static void solvePartTwo() {
        for (List<Antenna> categorizedAntenna : categorizedAntennas) {
            for (int i = 0; i < categorizedAntenna.size()-1; i++) {
                Antenna firstAntenna = categorizedAntenna.get(i);
                for (int j = i + 1; j < categorizedAntenna.size(); j++) {
                    Antenna secondAntenna = categorizedAntenna.get(j);
                    Vector vec = new Vector(firstAntenna.x - secondAntenna.x, firstAntenna.y - secondAntenna.y);
                    List<Antinode> firstAntinodes = new ArrayList<>();
                    List<Antinode> secondAntinodes = new ArrayList<>();
                    for (int k = 0; true; k++) {
                        Antinode newAntinode = new Antinode(firstAntenna.x + k*vec.x, firstAntenna.y + k*vec.y);
                        if (newAntinode.y < map.length && newAntinode.y >= 0 && newAntinode.x < map[0].length && newAntinode.x >= 0)
                            firstAntinodes.add(newAntinode);
                        else
                            break;
                    }
                    for (int k = 0; true; k++) {
                        Antinode newAntinode = new Antinode(secondAntenna.x - k*vec.x, secondAntenna.y - k*vec.y);
                        if (newAntinode.y < map.length && newAntinode.y >= 0 && newAntinode.x < map[0].length && newAntinode.x >= 0)
                            secondAntinodes.add(newAntinode);
                        else
                            break;
                    }
                    antinodes.addAll(firstAntinodes);
                    antinodes.addAll(secondAntinodes);
                }
            }
        }
        System.out.println("SOLUTION DAY 8 PART 2: " + antinodes.size());
    }

    public static void main(String[] args) {
        Day8.splitInput();
        Day8.solvePartOne();
        Day8.splitInput();
        Day8.solvePartTwo();
    }
}
