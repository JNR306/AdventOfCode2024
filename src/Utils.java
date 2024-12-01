public class Utils {

    public static void printArray(String[] array) {
        for (String element : array) {
            System.out.println(element);
        }
    }

    public static void printArray(int[] array) {
        for (int element : array) {
            System.out.println(element);
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
