import input.Input;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day9 {

    static int[] diskMapReadOnly;
    static int[] diskMapWriteOnly;
    static int[] sizesOfFileBlocks;
    static int[] firstAppearance;

    public static void splitInput(boolean overwriteEverything) {
        if (overwriteEverything) {
            AtomicInteger idx = new AtomicInteger();
            diskMapReadOnly = Stream.of(Input.INPUT_DAY_9)
                    .flatMap(s -> s.chars().mapToObj(c -> String.valueOf((char) c))) //Stream<String>
                    .mapToInt(Integer::parseInt)
                    .mapToObj(i -> {
                        int[] array = new int[i]; //generate an array of size i
                        if (idx.get() % 2 == 0)
                            Arrays.fill(array, idx.get() / 2); //fill it with idx/2 if even
                        else
                            Arrays.fill(array, -1); //fill it with null if odd (-1 should represent null in this entire program)
                        idx.incrementAndGet();
                        return Arrays.stream(array); //Stream<Integer>
                    })
                    .flatMapToInt(i -> i)
                    .toArray();
            firstAppearance = new int[diskMapReadOnly.length / 2];
            int lastFoundFileId = -1;
            for (int i = 0; i < diskMapReadOnly.length; i++) {
                if (diskMapReadOnly[i] != -1 && diskMapReadOnly[i] != lastFoundFileId) {
                    lastFoundFileId = diskMapReadOnly[i];
                    firstAppearance[lastFoundFileId] = i;
                }
            }
        }
        int[] inputDigits = Stream.of(Input.INPUT_DAY_9)
                .flatMap(s -> s.chars().mapToObj(c -> String.valueOf((char) c))) //Stream<String>
                .mapToInt(Integer::parseInt)
                .toArray();
        diskMapWriteOnly = diskMapReadOnly.clone();
        sizesOfFileBlocks = new int[(inputDigits.length + 1) / 2];
        for (int i = 0; i < inputDigits.length; i++) {
            if (i % 2 == 0)
                sizesOfFileBlocks[i / 2] = inputDigits[i];
        }
    }

    public static void solvePartOne() {
        for (int i = 0; i < diskMapReadOnly.length; i++) {
            if (diskMapReadOnly[i] == -1) {
                fillWithFile(i);
            }
        }
        for (int i = 0; i < diskMapReadOnly.length; i++) {
            if (diskMapReadOnly[i] != -1 && sizesOfFileBlocks[diskMapReadOnly[i]] == 0) {
                diskMapWriteOnly[i] = -1;
            } else if (diskMapReadOnly[i] != -1) {
                sizesOfFileBlocks[diskMapReadOnly[i]]--;
            }
        }
        long checksum = 0L;
        for (int i = 0; i < diskMapWriteOnly.length; i++) {
            if (diskMapWriteOnly[i] != -1) {
                checksum += (long) i * diskMapWriteOnly[i];
            }
        }
        System.out.println("SOLUTION DAY 9 PART 1: " + checksum);
    }

    private static void fillWithFile(int index) {
        for (int i = sizesOfFileBlocks.length - 1; i >= 0; i--) {
            if (sizesOfFileBlocks[i] > 0 && index <= getIndexOfFirstAppearance(i)) {
                diskMapWriteOnly[index] = i;
                sizesOfFileBlocks[i]--;
                break;
            }
        }
    }

    public static void solvePartTwo() {
        int startIndexOfEmptySpace = -1;
        int endIndexOfEmptySpace;
        for (int i = 0; i < diskMapReadOnly.length; i++) {
            if (diskMapReadOnly[i] == -1 && startIndexOfEmptySpace == -1) {
                startIndexOfEmptySpace = i;
            } else if (diskMapReadOnly[i] != -1 && startIndexOfEmptySpace != -1) {
                endIndexOfEmptySpace = i;
                fillWithFileBlocks(startIndexOfEmptySpace, endIndexOfEmptySpace);
                startIndexOfEmptySpace = -1;
            }
        }
        for (int i = 0; i < diskMapReadOnly.length; i++) {
            if (diskMapReadOnly[i] != -1 && sizesOfFileBlocks[diskMapReadOnly[i]] == 0) {
                diskMapWriteOnly[i] = -1;
            }
        }
        long checksum = 0L;
        for (int i = 0; i < diskMapWriteOnly.length; i++) {
            if (diskMapWriteOnly[i] != -1) {
                checksum += (long) i * diskMapWriteOnly[i];
            }
        }
        System.out.println("SOLUTION DAY 9 PART 2: " + checksum);
    }

    private static void fillWithFileBlocks(int startIndex, int endIndex) {
        while (startIndex != endIndex) {
            boolean fileBlockMoved = false;
            for (int i = sizesOfFileBlocks.length - 1; i >= 0; i--) {
                if (sizesOfFileBlocks[i] != 0 && sizesOfFileBlocks[i] <= endIndex - startIndex && startIndex + sizesOfFileBlocks[i] <= getIndexOfFirstAppearance(i)) {
                    Arrays.fill(diskMapWriteOnly, startIndex, startIndex + sizesOfFileBlocks[i], i);
                    startIndex += sizesOfFileBlocks[i];
                    sizesOfFileBlocks[i] = 0;
                    fileBlockMoved = true;
                    break;
                }
            }
            if (!fileBlockMoved)
                break;
        }
    }

    private static int getIndexOfFirstAppearance(int x) {
        return firstAppearance[x];
    }

    public static void main(String[] args) {
        Day9.splitInput(true);
        Day9.solvePartOne();
        Day9.splitInput(false);
        Day9.solvePartTwo();
    }
}