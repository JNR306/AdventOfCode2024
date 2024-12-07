import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void printArray(Object[] array) {
        for (Object element : array) {
            System.out.println(element);
        }
    }

    public static void printArray(int[] array) {
        for (int element : array) {
            System.out.println(element);
        }
    }

    public static void printArray(char[][] array) {
        for (char[] line : array) {
            for (char element : line) {
                System.out.print(element + " ");
            }
            System.out.print("\n");
        }
    }

    public static void printArray(int[][] array) {
        for (int[] line : array) {
            for (int element : line) {
                System.out.print(element + " ");
            }
            System.out.print("\n");
        }
    }

    public static <T> List<T> copyList(List<T> list) {
        return new ArrayList<>(list);
    }

    public static <T> List<T> copyListWithout(List<T> list, int indexOfElement) {
        List<T> copiedList = new ArrayList<>(list);
        copiedList.remove(indexOfElement);
        return copiedList;
    }

    public static <T> List<T> moveElementToEndInCopyOfList(List<T> list, int indexOfElement) {
        List<T> copiedList = new ArrayList<>(list);
        if (indexOfElement < list.size()) {
            copiedList.add(list.get(indexOfElement));
            copiedList.remove(indexOfElement);
        }
        return copiedList;
    }

    public static void printList(List<Object> list) {
        System.out.println(list);
    }

    public static void printListWithArray(List<int[]> list) {
        for (int[] element : list) {
            Utils.printArray(element);
        }
    }

    public static void sortArray(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    public static int containsCount(int[] array, int target) {
        int count = 0;
        for (int element : array) {
            if (element == target) {
                count++;
            }
        }
        return count;
    }
}
