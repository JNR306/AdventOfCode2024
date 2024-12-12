import input.Input;

import java.util.*;

public class Day12 {

    static char[][] plots;
    static List<Coordinate> alreadyChecked;
    static Set<Edge> edges;

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
            Day12.Coordinate that = (Day12.Coordinate) o;
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

    public static class Edge {

        //(x1,y1) is inside
        int x1;
        int y1;
        //(x2,y2) is outside
        int x2;
        int y2;

        public Edge(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Day12.Edge that = (Day12.Edge) o;
            return x1 == that.x1 && y1 == that.y1 && x2 == that.x2 && y2 == that.y2;
        }

        @Override
        public int hashCode() {
            return x1 + y1 + x2 + y2;
        }

        @Override
        public String toString() {
            return "(x=" + x1 + ", y=" + y1 + ") | (x=" + x2 + ", y=" + y2 + ")";
        }
    }

    private static class PlotCountEdgesCountTuple {
        int plotCount;
        int edgeCount;

        public PlotCountEdgesCountTuple(int plotCount, int edgeCount) {
            this.plotCount = plotCount;
            this.edgeCount = edgeCount;
        }
    }

    public static void splitInput() {
        String input = Input.INPUT_DAY_12;
        plots = Arrays.stream(input.split("\n")).map(String::toCharArray).toArray(char[][]::new);
        alreadyChecked = new ArrayList<>();
        edges = new HashSet<>();
    }

    private static Coordinate getFirstAppearanceOfAnyPlotType() {
        for (int y = 0; y < plots.length; y++) {
            for (int x = 0; x < plots[y].length; x++) {
                if (plots[y][x] != ' ') {
                    return new Coordinate(x, y);
                }
            }
        }
        return null;
    }

    private static PlotCountEdgesCountTuple checkPlotWithPerimeter(Coordinate coordinate, char plotType) {
        if (alreadyChecked.contains(coordinate))
            return new PlotCountEdgesCountTuple(0, 0);
        char currentPlotType = plots[coordinate.y][coordinate.x];
        PlotCountEdgesCountTuple res = new PlotCountEdgesCountTuple(0, 0);
        if (currentPlotType == plotType) {
            alreadyChecked.add(coordinate);
            res.plotCount++;
            res.edgeCount += 4;
            if (coordinate.y + 1 < plots.length && plots[coordinate.y + 1][coordinate.x] == currentPlotType) {
                res.edgeCount--;
                PlotCountEdgesCountTuple tempRes = checkPlotWithPerimeter(new Coordinate(coordinate.x, coordinate.y + 1), plotType);
                res.edgeCount += tempRes.edgeCount;
                res.plotCount += tempRes.plotCount;
            }
            if (coordinate.y - 1 >= 0 && plots[coordinate.y - 1][coordinate.x] == currentPlotType) {
                res.edgeCount--;
                PlotCountEdgesCountTuple tempRes = checkPlotWithPerimeter(new Coordinate(coordinate.x, coordinate.y - 1), plotType);
                res.edgeCount += tempRes.edgeCount;
                res.plotCount += tempRes.plotCount;
            }
            if (coordinate.x + 1 < plots[0].length && plots[coordinate.y][coordinate.x + 1] == plotType) {
                res.edgeCount--;
                PlotCountEdgesCountTuple tempRes = checkPlotWithPerimeter(new Coordinate(coordinate.x + 1, coordinate.y), plotType);
                res.edgeCount += tempRes.edgeCount;
                res.plotCount += tempRes.plotCount;
            }
            if (coordinate.x - 1 >= 0 && plots[coordinate.y][coordinate.x - 1] == plotType) {
                res.edgeCount--;
                PlotCountEdgesCountTuple tempRes = checkPlotWithPerimeter(new Coordinate(coordinate.x - 1, coordinate.y), plotType);
                res.edgeCount += tempRes.edgeCount;
                res.plotCount += tempRes.plotCount;
            }
        }
        return res;
    }

    public static void solvePartOne() {
        long totalCosts = 0L;
        Coordinate firstAppearance = getFirstAppearanceOfAnyPlotType();
        while (firstAppearance != null) {
            PlotCountEdgesCountTuple resOfRegion = checkPlotWithPerimeter(firstAppearance, plots[firstAppearance.y][firstAppearance.x]);
            totalCosts += (long) resOfRegion.edgeCount * resOfRegion.plotCount;
            for (Coordinate coordinate : alreadyChecked) {
                plots[coordinate.y][coordinate.x] = ' ';
            }
            alreadyChecked = new ArrayList<>();
            firstAppearance = getFirstAppearanceOfAnyPlotType();
        }
        System.out.println("SOLUTION DAY 12 PART 1: " + totalCosts);
    }

    private static PlotCountEdgesCountTuple checkPlotWithNumberOfSides(Coordinate coordinate, char plotType) {
        if (alreadyChecked.contains(coordinate))
            return new PlotCountEdgesCountTuple(0, 0);
        char currentPlotType = plots[coordinate.y][coordinate.x];
        PlotCountEdgesCountTuple res = new PlotCountEdgesCountTuple(0, 0);
        if (currentPlotType == plotType) {
            alreadyChecked.add(coordinate);
            res.plotCount++;
            if (coordinate.y + 1 < plots.length && plots[coordinate.y + 1][coordinate.x] == currentPlotType) {
                PlotCountEdgesCountTuple tempRes = checkPlotWithNumberOfSides(new Coordinate(coordinate.x, coordinate.y + 1), plotType);
                res.edgeCount += tempRes.edgeCount;
                res.plotCount += tempRes.plotCount;
            } else {
                edges.add(new Edge(coordinate.x, coordinate.y, coordinate.x, coordinate.y + 1));
                boolean foundEdgeNextToCurrent = edges.stream().anyMatch(edge -> edge.equals(new Edge(coordinate.x - 1, coordinate.y, coordinate.x - 1, coordinate.y + 1))
                        || edge.equals(new Edge(coordinate.x + 1, coordinate.y, coordinate.x + 1, coordinate.y + 1)));
                boolean foundBothEdgeNextToCurrent = edges.contains(new Edge(coordinate.x - 1, coordinate.y, coordinate.x - 1, coordinate.y + 1))
                        && edges.contains(new Edge(coordinate.x + 1, coordinate.y, coordinate.x + 1, coordinate.y + 1));
                res.edgeCount += foundEdgeNextToCurrent ? 0 : 1;
                res.edgeCount += foundBothEdgeNextToCurrent ? -1 : 0;
            }
            if (coordinate.y - 1 >= 0 && plots[coordinate.y - 1][coordinate.x] == currentPlotType) {
                PlotCountEdgesCountTuple tempRes = checkPlotWithNumberOfSides(new Coordinate(coordinate.x, coordinate.y - 1), plotType);
                res.edgeCount += tempRes.edgeCount;
                res.plotCount += tempRes.plotCount;
            } else {
                edges.add(new Edge(coordinate.x, coordinate.y, coordinate.x, coordinate.y - 1));
                boolean foundEdgeNextToCurrent = edges.stream().anyMatch(edge -> edge.equals(new Edge(coordinate.x - 1, coordinate.y, coordinate.x - 1, coordinate.y - 1))
                        || edge.equals(new Edge(coordinate.x + 1, coordinate.y, coordinate.x + 1, coordinate.y - 1)));
                boolean foundBothEdgeNextToCurrent = edges.contains(new Edge(coordinate.x - 1, coordinate.y, coordinate.x - 1, coordinate.y - 1))
                        && edges.contains(new Edge(coordinate.x + 1, coordinate.y, coordinate.x + 1, coordinate.y - 1));
                res.edgeCount += foundEdgeNextToCurrent ? 0 : 1;
                res.edgeCount += foundBothEdgeNextToCurrent ? -1 : 0;
            }
            if (coordinate.x + 1 < plots[0].length && plots[coordinate.y][coordinate.x + 1] == plotType) {
                PlotCountEdgesCountTuple tempRes = checkPlotWithNumberOfSides(new Coordinate(coordinate.x + 1, coordinate.y), plotType);
                res.edgeCount += tempRes.edgeCount;
                res.plotCount += tempRes.plotCount;
            } else {
                edges.add(new Edge(coordinate.x, coordinate.y, coordinate.x + 1, coordinate.y));
                boolean foundEdgeNextToCurrent = edges.stream().anyMatch(edge -> edge.equals(new Edge(coordinate.x, coordinate.y - 1, coordinate.x + 1, coordinate.y - 1))
                        || edge.equals(new Edge(coordinate.x, coordinate.y + 1, coordinate.x + 1, coordinate.y + 1)));
                boolean foundBothEdgeNextToCurrent = edges.contains(new Edge(coordinate.x, coordinate.y - 1, coordinate.x + 1, coordinate.y - 1))
                        && edges.contains(new Edge(coordinate.x, coordinate.y + 1, coordinate.x + 1, coordinate.y + 1));
                res.edgeCount += foundEdgeNextToCurrent ? 0 : 1;
                res.edgeCount += foundBothEdgeNextToCurrent ? -1 : 0;
            }
            if (coordinate.x - 1 >= 0 && plots[coordinate.y][coordinate.x - 1] == plotType) {
                PlotCountEdgesCountTuple tempRes = checkPlotWithNumberOfSides(new Coordinate(coordinate.x - 1, coordinate.y), plotType);
                res.edgeCount += tempRes.edgeCount;
                res.plotCount += tempRes.plotCount;
            } else {
                edges.add(new Edge(coordinate.x, coordinate.y, coordinate.x - 1, coordinate.y));
                boolean foundEdgeNextToCurrent = edges.stream().anyMatch(edge -> edge.equals(new Edge(coordinate.x, coordinate.y - 1, coordinate.x - 1, coordinate.y - 1))
                        || edge.equals(new Edge(coordinate.x, coordinate.y + 1, coordinate.x - 1, coordinate.y + 1)));
                boolean foundBothEdgeNextToCurrent = edges.contains(new Edge(coordinate.x, coordinate.y - 1, coordinate.x - 1, coordinate.y - 1))
                        && edges.contains(new Edge(coordinate.x, coordinate.y + 1, coordinate.x - 1, coordinate.y + 1));
                res.edgeCount += foundEdgeNextToCurrent ? 0 : 1;
                res.edgeCount += foundBothEdgeNextToCurrent ? -1 : 0;
            }
        }
        return res;
    }

    public static void solvePartTwo() {
        long totalCosts = 0L;
        Coordinate firstAppearance = getFirstAppearanceOfAnyPlotType();
        while (firstAppearance != null) {
            PlotCountEdgesCountTuple resOfRegion = checkPlotWithNumberOfSides(firstAppearance, plots[firstAppearance.y][firstAppearance.x]);
            totalCosts += (long) resOfRegion.edgeCount * resOfRegion.plotCount;
            for (Coordinate coordinate : alreadyChecked) {
                plots[coordinate.y][coordinate.x] = ' ';
            }
            alreadyChecked = new ArrayList<>();
            firstAppearance = getFirstAppearanceOfAnyPlotType();
            edges = new HashSet<>();
        }
        System.out.println("SOLUTION DAY 12 PART 2: " + totalCosts);
    }

    public static void main(String[] args) {
        Day12.splitInput();
        Day12.solvePartOne();
        Day12.splitInput();
        Day12.solvePartTwo();
    }
}