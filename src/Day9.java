import input.Input;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day9 {

    static Integer[] diskMapReadOnly;
    static Integer[] diskMapWriteOnly;
    static int[] sizesOfFileBlocks;
    static int[] firstAppearance;

    public static void splitInput() {
        int[] input = Stream.of(Input.INPUT_DAY_9)
                .flatMap(s -> s.chars().mapToObj(c -> String.valueOf((char) c))) //Stream<String>
                .mapToInt(Integer::parseInt)
                .toArray();
        sizesOfFileBlocks = new int[(input.length+1)/2];
        for (int i = 0; i < input.length; i++) {
            if (i % 2 == 0)
                sizesOfFileBlocks[i/2] = input[i];
        }
        AtomicInteger idx = new AtomicInteger();
        diskMapReadOnly = Stream.of(Input.INPUT_DAY_9)
                .flatMap(s -> s.chars().mapToObj(c -> String.valueOf((char) c))) //Stream<String>
                .mapToInt(Integer::parseInt)
                .mapToObj(i -> {
                    Integer[] array = new Integer[i]; //generate an array of size i
                    if (idx.get() % 2 == 0)
                        Arrays.fill(array, idx.get() / 2); //fill it with idx/2 if even
                    else
                        Arrays.fill(array, null); //fill it with null if odd
                    idx.incrementAndGet();
                    return Arrays.stream(array); //Stream<Integer>
                })
                .flatMap(i -> i)
                .toArray(Integer[]::new);
        diskMapWriteOnly = diskMapReadOnly.clone();
        firstAppearance = new int[diskMapReadOnly.length/2];
        for (int j = 0; j < diskMapReadOnly.length/2; j++) {
            for (int i = 0; i < diskMapReadOnly.length; i++) {
                if (diskMapReadOnly[i] != null && diskMapReadOnly[i] == j) {
                    firstAppearance[j] = i;
                }
            }
        }
    }

    public static void solvePartOne() {
        int neededIterationsCount = countEmptySpaces();
        for (int i = 0; i < neededIterationsCount; i++) {
            Integer removedFileId = getAndRemoveLastFile();
            addFile(removedFileId);
        }
        long checksum = 0L;
        for (int i = 0; i < diskMapWriteOnly.length; i++) {
            if (diskMapWriteOnly[i] != null) {
                checksum += (long) i * diskMapWriteOnly[i];
            }
        }
        System.out.println("SOLUTION DAY 9 PART 1: " + checksum);
    }

    private static void addFile(Integer fileId) {
        for (int i = 0; i < diskMapWriteOnly.length; i++) {
            if (diskMapWriteOnly[i] == null) {
                diskMapWriteOnly[i] = fileId;
                return;
            }
        }
    }

    private static Integer getAndRemoveLastFile() {
        for (int i = diskMapWriteOnly.length - 1; i >= 0; i--) {
            if (diskMapWriteOnly[i] != null) {
                Integer fileId = diskMapWriteOnly[i];
                diskMapWriteOnly[i] = null;
                return fileId;
            }
        }
        return null;
    }

    private static int countEmptySpaces() {
        int count = 0;
        for (Integer fileId : diskMapWriteOnly)
            if (fileId == null) count++;
        return count;
    }

    public static void solvePartTwo() {
        int startIndexOfEmptySpace = -1;
        int endIndexOfEmptySpace;
        for (int i = 0; i < diskMapReadOnly.length; i++) {
            if (diskMapReadOnly[i] == null && startIndexOfEmptySpace == -1) {
                startIndexOfEmptySpace = i;
            } else if (diskMapReadOnly[i] != null && startIndexOfEmptySpace != -1) {
                endIndexOfEmptySpace = i;
                fillWithFileBlocks(startIndexOfEmptySpace, endIndexOfEmptySpace);
                startIndexOfEmptySpace = -1;
            }
        }
        for (int i = 0; i < diskMapReadOnly.length; i++) {
            if (diskMapReadOnly[i] != null && sizesOfFileBlocks[diskMapReadOnly[i]] == 0) {
                diskMapWriteOnly[i] = null;
            }
        }
        long checksum = 0L;
        for (int i = 0; i < diskMapWriteOnly.length; i++) {
            if (diskMapWriteOnly[i] != null) {
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
        Day9.splitInput();
        Day9.solvePartOne();
        Day9.splitInput();
        Day9.solvePartTwo();
    }
}