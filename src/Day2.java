import input.Input;

import java.util.ArrayList;
import java.util.List;

public class Day2 {

    static List<List<Integer>> programs = new ArrayList<>();

    public static void splitInput() {
        programs = new ArrayList<>();
        String[] lines = Input.INPUT_DAY_2.split("\n");
        for (String line : lines) {
            String[] levels = line.split(" ");
            ArrayList<Integer> program = new ArrayList<>();
            for (String level : levels) {
                program.add(Integer.parseInt(level));
            }
            programs.add(program);
        }
    }

    public static void solvePartOne() {
        int safeCount = 0;
        for (List<Integer> program : programs) {
            if (checkProgram(program)) {
                safeCount++;
            }
        }
        System.out.println("SOLUTION DAY 2 PART 1: " + safeCount);
    }

    //returns if a program is safe
    private static boolean checkProgram(List<Integer> program) {
        boolean ascending = program.get(0) < program.get(1);
        for (int i = 0; i < program.size()-1; i++) {
            //check all conditions to find out if the program is not safe
            if ((ascending && program.get(i) >= program.get(i+1))
                    || (!ascending && program.get(i) <= program.get(i+1))
                    || (Math.abs(program.get(i)-program.get(i+1)) > 3)) {
                return false;
            }
        }
        return true;
    }

    public static void solvePartTwo() {
        int safeCount = 0;
        for (List<Integer> program : programs) {
            //check unmodified program
            if (checkProgram(program)) {
                safeCount++;
                continue;
            }
            //check modified programs
            for (int i = 0; i < program.size(); i++) {
                //remove level i from program
                List<Integer> newProgram = Utils.copyListWithout(program, i);
                if (checkProgram(newProgram)) {
                    safeCount++;
                    break;
                }
            }
        }
        System.out.println("SOLUTION DAY 2 PART 2: " + safeCount);
    }

    public static void main(String[] args) {
        Day2.splitInput();
        Day2.solvePartOne();
        Day2.solvePartTwo();
    }
}
