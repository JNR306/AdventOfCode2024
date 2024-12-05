import input.Input;

import java.util.ArrayList;
import java.util.List;

public class Day3 {

    static List<int[]> numbers = new ArrayList<>();

    public static void splitInputPartOne() {
        numbers = new ArrayList<>();
        char[] symbols = Input.INPUT_DAY_3.toCharArray();
        char last = ' ';
        int digit1 = 0;
        int digit2 = 0;
        for (char symbol : symbols) {
            if (last == ' ' && symbol == 'm')
                last = 'm';
            else if (last == 'm' && symbol == 'u')
                last = 'u';
            else if (last == 'u' && symbol == 'l')
                last = 'l';
            else if (last == 'l' && symbol == '(')
                last = '(';
            else if (last == '(' && Character.isDigit(symbol)) {
                last = 'D';
                digit1 = Integer.parseInt(String.valueOf(symbol));
            }
            else if (last == 'D' && Character.isDigit(symbol))
                digit1 = Integer.parseInt(digit1 + String.valueOf(symbol));
            else if (last == 'D' && symbol == ',')
                last = ',';
            else if (last == ',' && Character.isDigit(symbol)) {
                last = 'E';
                digit2 = Integer.parseInt(String.valueOf(symbol));
            }
            else if (last == 'E' && Character.isDigit(symbol))
                digit2 = Integer.parseInt(digit2 + String.valueOf(symbol));
            else if (last == 'E' && symbol == ')') {
                numbers.add(new int[]{digit1, digit2});
                digit1 = 0;
                digit2 = 0;
                last = ' ';
            }
            else {
                last = ' ';
            }
        }
    }

    public static void splitInputPartTwo() {
        numbers = new ArrayList<>();
        char[] symbols = Input.INPUT_DAY_3.toCharArray();
        char last = ' ';
        int digit1 = 0;
        int digit2 = 0;
        boolean enabled = true;
        for (int i = 0; i < symbols.length; i++) {
            if (last == ' ' && symbols[i] == 'm' && symbols.length > i + 3 && symbols[i + 1] == 'u' && symbols[i + 2] == 'l' && symbols[i + 3] == '(') {
                last = '(';
                i += 3;
            }
            else if (last == '(' && Character.isDigit(symbols[i])) {
                last = 'D';
                digit1 = Integer.parseInt(String.valueOf(symbols[i]));
            }
            else if (last == 'D' && Character.isDigit(symbols[i])) {
                digit1 = Integer.parseInt(digit1 + String.valueOf(symbols[i]));
            }
            else if (last == 'D' && symbols[i] == ',') {
                last = ',';
            }
            else if (last == ',' && Character.isDigit(symbols[i])) {
                last = 'E';
                digit2 = Integer.parseInt(String.valueOf(symbols[i]));
            }
            else if (last == 'E' && Character.isDigit(symbols[i])) {
                digit2 = Integer.parseInt(digit2 + String.valueOf(symbols[i]));
            }
            else if (last == ' ' && symbols[i] == 'd' && symbols.length > i + 3 && symbols[i+1] == 'o' && symbols[i+2] == '(' && symbols[i+3] == ')') {
                enabled = true;
                i += 3;
            }
            else if (last == ' ' && symbols[i] == 'd' && symbols.length > i + 6 && symbols[i+1] == 'o' && symbols[i+2] == 'n' && symbols[i+3] == '\'' && symbols[i+4] == 't' && symbols[i+5] == '(' && symbols[i+6] == ')') {
                enabled = false;
                i += 6;
            }
            else if (last == 'E' && symbols[i] == ')') {
                if (enabled) {
                    numbers.add(new int[]{digit1, digit2});
                }
                digit1 = 0;
                digit2 = 0;
                last = ' ';
            }
            else {
                last = ' ';
            }
        }
    }

    public static void solvePartOne() {
        int result = 0;
        for (int[] number : numbers) {
            result += number[0] * number[1];
        }
        System.out.println("SOLUTION DAY 3 PART 1: " + result);
    }

    public static void solvePartTwo() {
        int result = 0;
        for (int[] number : numbers) {
            result += number[0] * number[1];
        }
        System.out.println("SOLUTION DAY 3 PART 2: " + result);
    }

    public static void main(String[] args) {
        Day3.splitInputPartOne();
        Day3.solvePartOne();
        Day3.splitInputPartTwo();
        Day3.solvePartTwo();
    }
}
